package utez.edu.mx.cleancheck.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utez.edu.mx.cleancheck.controller.report.dto.ReportDto;
import utez.edu.mx.cleancheck.model.image.Image;
import utez.edu.mx.cleancheck.model.report.Report;
import utez.edu.mx.cleancheck.model.report.ReportRepository;
import utez.edu.mx.cleancheck.model.report.ReportState;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomRepository;
import utez.edu.mx.cleancheck.model.user.User;
import utez.edu.mx.cleancheck.model.user.UserRepository;
import utez.edu.mx.cleancheck.service.image.ImageService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ReportService {

    private final ReportRepository reportRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ApiResponse<Report> create (ReportDto report) {
        User foundUser = userRepository.findById(report.getUserId()).orElse(null);
        if (foundUser == null) {
            return new ApiResponse<>(
                    null, true, 404, "El usuario no existe"
            );
        }
        Room foundRoom = roomRepository.findById(report.getRoomId()).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 404, "La habitación no existe"
            );
        }
        Report newReport = new Report();
        String newId = UUID.randomUUID().toString();
        newReport.setId(newId);
        newReport.setDescription(report.getDescription());
        newReport.setUserId(foundUser);
        newReport.setRoomId(foundRoom);
        newReport.setStatus(ReportState.PENDING);
        Report saveReport = reportRepository.save(newReport);
        List<Image> images = imageService.uploadImages(report.getFiles(), newReport);
        saveReport.setImages(images);
        return new ApiResponse<>(
                saveReport, false, HttpStatus.OK.value(), "Reporte registrado correctamente"
        );
    }
}