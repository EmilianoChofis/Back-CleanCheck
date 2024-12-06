package utez.edu.mx.cleancheck.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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


    public ApiResponse<List<Report>> getAll() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 404, "No hay reportes registrados"
            );
        }
        return new ApiResponse<>(
                reports, false, HttpStatus.OK.value(), "Reportes encontrados"
        );
    }

    public ApiResponse<Report> getById(String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return new ApiResponse<>(
                    null, true, 404, "El reporte no existe"
            );
        }
        return new ApiResponse<>(
                report, false, HttpStatus.OK.value(), "Reporte encontrado"
        );
    }

    public ApiResponse<Report> UpdateStatusIn (String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return new ApiResponse<>(
                    null, true, 404, "El reporte no existe"
            );
        }
        report.setStatus(ReportState.IN_PROGRESS);
        Report updatedReport = reportRepository.save(report);
        return new ApiResponse<>(
                updatedReport, false, HttpStatus.OK.value(), "Reporte actualizado correctamente"
        );
    }

    public ApiResponse<Report> UpdateStatusFinish (String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return new ApiResponse<>(
                    null, true, 404, "El reporte no existe"
            );
        }
        report.setStatus(ReportState.FINISHED);
        Report updatedReport = reportRepository.save(report);
        return new ApiResponse<>(
                updatedReport, false, HttpStatus.OK.value(), "Reporte actualizado correctamente"
        );
    }

    public ApiResponse<Report> delete (String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return new ApiResponse<>(
                    null, true, 404, "El reporte no existe"
            );
        }
        reportRepository.delete(report);
        return new ApiResponse<>(
                report, false, HttpStatus.OK.value(), "Reporte eliminado correctamente"
        );
    }
}