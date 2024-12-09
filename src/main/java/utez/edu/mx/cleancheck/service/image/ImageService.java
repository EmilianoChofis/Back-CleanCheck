package utez.edu.mx.cleancheck.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import utez.edu.mx.cleancheck.model.image.Image;
import utez.edu.mx.cleancheck.model.image.ImageRepository;
import utez.edu.mx.cleancheck.model.report.Report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ImageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final ImageRepository imageRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<Image> uploadImages(List<String> base64Images, Report report) {
        return base64Images.stream().map(base64Image -> {
            String[] parts = base64Image.split(",");
            if (parts.length < 2) {
                throw new IllegalArgumentException("La base64 de la imagen no tiene el formato correcto");
            }
            String metadata = parts[0];
            String base64Data = parts[1];

            String extension = Optional.ofNullable(metadata.split("/")[1].split(";")[0])
                    .orElseThrow(() -> new IllegalArgumentException("No se pudo obtener la extension de la imagen"));
            String key = UUID.randomUUID() + "." + extension;

            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes)) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(metadata)
                        .build();
                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, decodedBytes.length));
                String url = s3Presigner.presignGetObject(builder -> builder
                        .getObjectRequest(r -> r.bucket(bucketName).key(key))
                        .signatureDuration(Duration.ofSeconds(60))
                ).url().toString();
                int longurl = url.length();
                System.out.println("longitud de la url: " + longurl);
                Image image = new Image();
                image.setId(UUID.randomUUID().toString());
                image.setKey(key);
                image.setUrl(url);
                image.setReport(report);
                return imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("Error en la subida de la imagen al S3", e);
            }
        }).collect(Collectors.toList());
    }

    public String getPresignedUrl(String key) {
        try {
            return s3Presigner.presignGetObject(builder -> builder
                            .getObjectRequest(r -> r.bucket(bucketName).key(key))
                            .signatureDuration(java.time.Duration.ofSeconds(15)))
                    .url().toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la URL prefirmada para la key: " + key, e);
        }
    }
}
