package utez.edu.mx.cleancheck.controller.building;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.building.dto.BuildingDto;
import utez.edu.mx.cleancheck.model.building.Building;
import utez.edu.mx.cleancheck.service.building.BuildingService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@Controller
@RequestMapping("/api-clean/building")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class BuildingController {

        private final BuildingService service;

        @PostMapping("/create")
        public ResponseEntity<ApiResponse<Building>> create (@Valid @RequestBody BuildingDto building) {
            try {
                ApiResponse<Building> response = service.create(building);
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
        public ResponseEntity<ApiResponse<List<Building>>> getAll () {
            try {
                ApiResponse<List<Building>> response = service.findAll();
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

        @PutMapping("/update")
        public ResponseEntity<ApiResponse<Building>> update (@Valid @RequestBody BuildingDto building) {
            try {
                ApiResponse<Building> response = service.update(building);
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
        public ResponseEntity<ApiResponse<Building>> getById (@PathVariable("id") String id) {
            try {
                ApiResponse<Building> response = service.findById(id);
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

        @GetMapping("/getByName/{name}")
        public ResponseEntity<ApiResponse<Building>> getByName (@PathVariable("name") String name) {
            try {
                ApiResponse<Building> response = service.findByName(name);
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

        @DeleteMapping("/delete")
        public ResponseEntity<ApiResponse<Building>> delete (@Valid @RequestBody BuildingDto building) {
            try {
                ApiResponse<Building> response = service.delete(building);
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
