package utez.edu.mx.cleancheck.controller.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.report.dto.ReportDto;
import utez.edu.mx.cleancheck.model.report.Report;
import utez.edu.mx.cleancheck.service.report.ReportService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@Controller
@RequestMapping("/api-clean/report")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor

public class ReportController {

    private final ReportService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Report>> create(@RequestBody ReportDto report) {
        try {
            ApiResponse<Report> response = service.create(report);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Report>>> getAll() {
        try {
            ApiResponse<List<Report>> response = service.getAll();
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<Report>> getById(@PathVariable String id) {
        try {
            ApiResponse<Report> response = service.getById(id);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }



}
