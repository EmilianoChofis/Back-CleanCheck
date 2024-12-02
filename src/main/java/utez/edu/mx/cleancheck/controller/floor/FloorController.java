package utez.edu.mx.cleancheck.controller.floor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.floor.dto.FloorCreatedDto;
import utez.edu.mx.cleancheck.controller.floor.dto.FloorDto;
import utez.edu.mx.cleancheck.model.floor.Floor;
import utez.edu.mx.cleancheck.service.floor.FloorService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@Controller
@RequestMapping("/api-clean/floor")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService service;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<FloorCreatedDto>> create(@Validated @RequestBody FloorDto floor) {
        try {
            ApiResponse<FloorCreatedDto> response = service.create(floor);
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

    @Transactional(readOnly = true)
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Floor>>> getAll() {
        try {
            ApiResponse<List<Floor>> response = service.findAll();
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

//    @Transactional(readOnly = true)
//    @PostMapping("/findById")
//    public ResponseEntity<ApiResponse<Floor>> findById(@Validated({FloorDto.FindById.class}) @RequestBody FloorDto dto) {
//        try {
//            ApiResponse<Floor> response = service.findById(dto);
//            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
//            return new ResponseEntity<>(
//                    response,
//                    statusCode
//            );
//        } catch (Exception e) {
//            return new ResponseEntity<>(
//                    new ApiResponse<>(
//                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

//    @Transactional(readOnly = true)
//    @PostMapping("/findByName")
//    public ResponseEntity<ApiResponse<Floor>> findByName(@Valid @RequestBody String name) {
//        try {
//            ApiResponse<Floor> response = service.findByName(name);
//            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
//            return new ResponseEntity<>(
//                    response,
//                    statusCode
//            );
//        } catch (Exception e) {
//            return new ResponseEntity<>(
//                    new ApiResponse<>(
//                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

//    @Transactional(rollbackFor = Exception.class)
//    @PutMapping("/update")
//    public ResponseEntity<ApiResponse<Floor>> update(@Validated({FloorDto.Update.class}) @RequestBody FloorDto floor) {
//        try {
//            ApiResponse<Floor> response = service.update(floor);
//            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
//            return new ResponseEntity<>(
//                    response,
//                    statusCode
//            );
//        } catch (Exception e) {
//            return new ResponseEntity<>(
//                    new ApiResponse<>(
//                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Floor>> delete(@Valid @RequestBody FloorDto floor) {
        try {
            ApiResponse<Floor> response = service.delete(floor);
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

//    @Transactional(readOnly = true)
//    @PostMapping("/getByBulding")
//    public ResponseEntity<ApiResponse<List<Floor>>> getByBuilding(@Validated({FloorDto.FindByBuildingId.class}) @RequestBody FloorDto dto) {
//        try {
//            ApiResponse<List<Floor>> response = service.findByBuildingId(dto);
//            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
//            return new ResponseEntity<>(
//                    response,
//                    statusCode
//            );
//        } catch (Exception e) {
//            return new ResponseEntity<>(
//                    new ApiResponse<>(
//                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

//    @PostMapping("/create-list")
//    public ResponseEntity<ApiResponse<List<Floor>>> createList(@Validated({FloorDto.CreateList.class}) @RequestBody FloorDto floor) {
//        try {
//            ApiResponse<List<Floor>> response = service.createList(floor);
//            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
//            return new ResponseEntity<>(
//                    response,
//                    statusCode
//            );
//        } catch (Exception e) {
//            return new ResponseEntity<>(
//                    new ApiResponse<>(
//                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
//                    ),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

//    @Transactional(rollbackFor = Exception.class)
//    @PutMapping("/change-status")
//    public ResponseEntity<ApiResponse<Floor>> changeStatus(@Validated({FloorDto.ChangeStatus.class}) @RequestBody FloorDto floor) {
//        try {
//            ApiResponse<Floor> response = service.changeStatus(floor);
//            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
//            return new ResponseEntity<>(
//                    response,
//                    statusCode
//            );
//        } catch (Exception e) {
//            return new ResponseEntity<>(
//                    new ApiResponse<>(
//                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
//                    ),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

}
