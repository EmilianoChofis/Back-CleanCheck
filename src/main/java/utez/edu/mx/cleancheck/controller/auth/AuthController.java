package utez.edu.mx.cleancheck.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.auth.dto.SignDto;
import utez.edu.mx.cleancheck.controller.auth.dto.SignedDto;
import utez.edu.mx.cleancheck.controller.user.dto.UserDto;
import utez.edu.mx.cleancheck.model.user.User;
import utez.edu.mx.cleancheck.service.auth.AuthService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

@RestController
@RequestMapping("/api-clean/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService service;

    @PostMapping("/createManager")
    public ResponseEntity<ApiResponse<User>> createManager(@Valid @RequestBody UserDto user) {
        try {
            ApiResponse<User> response = service.createManager(user);
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

    @PostMapping("/createReceptionist")
    public ResponseEntity<ApiResponse<User>> createReceptionist(@Valid @RequestBody UserDto user) {
        try {
            ApiResponse<User> response = service.createReceptionist(user);
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

    @PostMapping("/createMaid")
    public ResponseEntity<ApiResponse<User>> createMaid(@Valid @RequestBody UserDto user) {
        try {
            ApiResponse<User> response = service.createMaid(user);
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

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<SignedDto>> signIn(@Valid @RequestBody SignDto user) {
        try {
            ApiResponse<SignedDto> response = service.signIn(user);
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
