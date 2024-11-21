package utez.edu.mx.cleancheck.controller.record;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.cleancheck.controller.record.dto.RecordDto;
import utez.edu.mx.cleancheck.service.record.RecordService;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.utils.PaginationDto;

import java.util.List;

@RestController
@RequestMapping("/api-clean/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    //insert
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Record>> create(@Validated({RecordDto.Insert.class}) @RequestBody RecordDto recordDto) {
        try {
            ApiResponse<Record> response = recordService.insert(recordDto);
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

    //get by room
    @PostMapping("/get-by-room")
    public ResponseEntity<ApiResponse<List<Record>>> getByRoom(@Validated({PaginationDto.StateGet.class}) @RequestBody PaginationDto paginationDto) {
        try {
            ApiResponse<List<Record>> response = recordService.findByRoom(paginationDto);
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
