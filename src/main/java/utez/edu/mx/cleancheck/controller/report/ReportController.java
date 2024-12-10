package utez.edu.mx.cleancheck.controller.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.report.dto.ReportDto;
import utez.edu.mx.cleancheck.model.report.Report;
import utez.edu.mx.cleancheck.service.report.ReportService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@RestController
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

    @GetMapping("/getAllPending")
    public ResponseEntity<ApiResponse<List<Report>>> getAllPending() {
        try {
            ApiResponse<List<Report>> response = service.getAllPending();
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

    @GetMapping("/getAllInProgress")
    public ResponseEntity<ApiResponse<List<Report>>> getAllInProgress() {
        try {
            ApiResponse<List<Report>> response = service.getAllInProgress();
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

    @GetMapping("/getAllFinished")
    public ResponseEntity<ApiResponse<List<Report>>> getAllFinished() {
        try {
            ApiResponse<List<Report>> response = service.getAllFinished();
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



    @PutMapping(("updateStatusIn/{id}"))
    public ResponseEntity<ApiResponse<Report>> changeInProgress(@PathVariable String id) {
        try {
            ApiResponse<Report> response = service.UpdateStatusIn(id);
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

    @PutMapping(("updateStatusFinish/{id}"))
    public ResponseEntity<ApiResponse<Report>> changeFinished(@PathVariable String id) {
        try {
            ApiResponse<Report> response = service.UpdateStatusFinish(id);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Report>> delete(@PathVariable String id) {
        try {
            ApiResponse<Report> response = service.delete(id);
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
