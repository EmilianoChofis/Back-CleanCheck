package utez.edu.mx.cleancheck.controller.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.cleancheck.controller.room.dto.RoomDto;
import utez.edu.mx.cleancheck.controller.room.dto.RoomUpdateDto;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.service.room.RoomService;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api-clean/room")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class RoomController {

    private final RoomService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Room>> create(@Validated @RequestBody RoomDto room) {
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

    @PostMapping("/createList")
    public ResponseEntity<ApiResponse<List<Room>>> createList(@Validated @RequestBody List<RoomDto> rooms) {
        try {
            ApiResponse<List<Room>> response = service.createList(rooms);
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
            ApiResponse<List<Room>> response = service.getAll();
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

    @GetMapping("/getAllOccupied")
    public ResponseEntity<ApiResponse<List<Room>>> getAllOccupied() {
        try {
            ApiResponse<List<Room>> response = service.getAllOccupied();
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

    @GetMapping("/getAllUnoccupied")
    public ResponseEntity<ApiResponse<List<Room>>> getAllUnoccupied() {
        try {
            ApiResponse<List<Room>> response = service.getAllUnoccupied();
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

    @GetMapping("/getAllClean")
    public ResponseEntity<ApiResponse<List<Room>>> getAllClean() {
        try {
            ApiResponse<List<Room>> response = service.getAllClean();
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

    @GetMapping("/getAllChecked")
    public ResponseEntity<ApiResponse<List<Room>>> getAllChecked() {
        try {
            ApiResponse<List<Room>> response = service.getAllChecked();
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

    @GetMapping("/getAllInMaintenance")
    public ResponseEntity<ApiResponse<List<Room>>> getAllInMaintenance() {
        try {
            ApiResponse<List<Room>> response = service.getAllInMaintenance();
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

    @GetMapping("/getById/{roomId}")
    public ResponseEntity<ApiResponse<Room>> getById(@PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.getById(roomId);
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
            ApiResponse<Room> response = service.getByName(name);
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

    @PostMapping("/getAllByFloor/{floorId}")
    public ResponseEntity<ApiResponse<List<Room>>> getAllByFloor(@Validated @PathVariable("floorId") String floorid) {
        try {
            ApiResponse<List<Room>> response = service.getAllByFloor(floorid);
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

    @PostMapping("/getByBuilding/{buildingId}")
    public ResponseEntity<ApiResponse<List<Room>>> findByBuilding(@Validated @PathVariable("buildingId") String buildingId) {
        try {
            ApiResponse<List<Room>> response = service.getAllByBuilding(buildingId);
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


//    @PostMapping("/getByStatusAndBuilding")
//    public ResponseEntity<ApiResponse<Building>> findByStatusAndBuilding(@Validated @RequestBody RoomDto room) {
//        try {
//            ApiResponse<Building> response = service.findByStatusAndBuilding(room);
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

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Room>> update(@RequestBody RoomUpdateDto room) {
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

    @PutMapping("/changeStatusOccupied/{roomId}")
    public ResponseEntity<ApiResponse<Room>> changeStatusOccupied(@PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.changeStatusOccupied(roomId);
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

    @PutMapping("/changeStatusUnoccupied/{roomId}")
    public ResponseEntity<ApiResponse<Room>> changeStatusUnoccupied(@PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.changeStatusUnoccupied(roomId);
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

    @PutMapping("/changeStatusClean/{roomId}")
    public ResponseEntity<ApiResponse<Room>> changeStatusClean(@PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.changeStatusClean(roomId);
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

    @PutMapping("/changeStatusChecked/{roomId}")
    public ResponseEntity<ApiResponse<Room>> changeStatusChecked(@PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.changeStatusChecked(roomId);
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

    @PutMapping("/changeStatusInMaintenance/{roomId}")
    public ResponseEntity<ApiResponse<Room>> changeStatusInMaintenance(@PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.changeStatusInMaintenance(roomId);
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

//    @PutMapping("/change-status")
//    public ResponseEntity<ApiResponse<Room>> changeStatus(@Validated({RoomDto.ChangeStatus.class}) @RequestBody RoomDto room) {
//        try {
//            ApiResponse<Room> response = service.changeState(room);
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

    @DeleteMapping("/deleteById/{roomId}")
    public ResponseEntity<ApiResponse<Room>> delete(@Validated @PathVariable("roomId") String roomId) {
        try {
            ApiResponse<Room> response = service.deleteById(roomId);
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

    @DeleteMapping("/deleteByIdentifier/{identifier}")
    public ResponseEntity<ApiResponse<Room>> deleteByIdentifier(@Validated @PathVariable("identifier") String identifier) {
        try {
            ApiResponse<Room> response = service.deleteByIdentifier(identifier);
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
