package utez.edu.mx.cleancheck.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import utez.edu.mx.cleancheck.model.image.Image;
import utez.edu.mx.cleancheck.model.image.ImageRepository;
import utez.edu.mx.cleancheck.model.report.Report;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ImageService {

    private final S3Client s3Client;
    private final ImageRepository imageRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<Image> uploadImages(List<String> files, Report report) {
        return files.stream().map(file -> {
            String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();
                PutObjectResponse response = s3Client.putObject(putObjectRequest, (Path) file.getInputStream());

                Image image = new Image();
                image.setId(UUID.randomUUID().toString());
                image.setUrl("https://" + bucketName + ".s3.amazonaws.com/" + key);
                image.setReportId(report);
                return imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("Fallo la subida del archivo al S3", e);
            }
        }).collect(Collectors.toList());
    }
}
