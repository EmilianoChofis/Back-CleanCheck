package utez.edu.mx.cleancheck.controller.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utez.edu.mx.cleancheck.model.dashboard.DashboardBuilding;
import utez.edu.mx.cleancheck.service.dashboard.DashboardService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@Controller
@RequestMapping("/api-clean/dashboard")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @Transactional(readOnly = true)
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<DashboardBuilding>>> getAll() {
        try {
            ApiResponse<List<DashboardBuilding>> response = service.findAll();
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
