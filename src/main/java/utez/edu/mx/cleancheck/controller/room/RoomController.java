package utez.edu.mx.cleancheck.controller.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.room.dto.RoomDto;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.service.room.RoomService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@Controller
@RequestMapping("/api-clean/room")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class RoomController {

    private final RoomService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Room>> create(@Validated({RoomDto.Create.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<Room> response = service.create(room);
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
    public ResponseEntity<ApiResponse<List<Room>>> getAll() {
        try {
            ApiResponse<List<Room>> response = service.findAll();
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
    public ResponseEntity<ApiResponse<Room>> getById(@PathVariable String id) {
        try {
            ApiResponse<Room> response = service.findById(id);
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
    public ResponseEntity<ApiResponse<Room>> getByName(@PathVariable String name) {
        try {
            ApiResponse<Room> response = service.findByName(name);
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

    @PostMapping("/getByFloor")
    public ResponseEntity<ApiResponse<List<Room>>> findByFloor(@Validated({RoomDto.FindByFloor.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<List<Room>> response = service.findByFloor(room);
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

    @PostMapping("/getByStatus")
    public ResponseEntity<ApiResponse<List<Room>>> findByStatus(@Validated({RoomDto.FindByStatus.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<List<Room>> response = service.findByStatus(room);
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
    public ResponseEntity<ApiResponse<Room>> update(@Validated({RoomDto.Update.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<Room> response = service.update(room);
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
    public ResponseEntity<ApiResponse<Room>> delete(@Validated({RoomDto.Delete.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<Room> response = service.delete(room);
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

    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse<Room>> changeStatus(@Validated({RoomDto.ChangeStatus.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<Room> response = service.changeState(room);
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

    @PostMapping("/create-list")
    public ResponseEntity<ApiResponse<List<Room>>> createList(@Validated({RoomDto.CreateList.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<List<Room>> response = service.createList(room);
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

    @PostMapping("/getByBuilding")
    public ResponseEntity<ApiResponse<List<Room>>> findByBuilding(@Validated({RoomDto.FindByBuilding.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<List<Room>> response = service.findByBuilding(room);
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

    @PutMapping("/change-room-status")
    public ResponseEntity<ApiResponse<Room>> changeRoomStatus(@Validated({RoomDto.ChangeRoomStatus.class}) @RequestBody RoomDto room) {
        try {
            ApiResponse<Room> response = service.changeRoomStatus(room);
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
