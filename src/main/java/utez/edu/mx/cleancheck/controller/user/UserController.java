package utez.edu.mx.cleancheck.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.user.dto.UserDto;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.model.user.User;
import utez.edu.mx.cleancheck.service.user.UserService;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import utez.edu.mx.cleancheck.utils.PaginationDto;

import java.util.List;

@RestController
@RequestMapping("/api-clean/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/getAll")
    public ResponseEntity<ApiResponse<List<User>>> getByRoom(@Validated({PaginationDto.StateGet.class}) @RequestBody PaginationDto paginationDto) {
        try {
            ApiResponse<List<User>> response = service.getAllPagination(paginationDto);
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
    public ResponseEntity<ApiResponse<User>> update(@Validated({UserDto.Update.class}) @RequestBody UserDto user) {
        try {
            ApiResponse<User> response = service.update(user);
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
