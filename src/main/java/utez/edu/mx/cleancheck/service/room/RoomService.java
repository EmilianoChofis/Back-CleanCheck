package utez.edu.mx.cleancheck.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.room.dto.RoomDto;
import utez.edu.mx.cleancheck.model.building.Building;
import utez.edu.mx.cleancheck.model.building.BuildingRepository;
import utez.edu.mx.cleancheck.model.floor.Floor;
import utez.edu.mx.cleancheck.model.floor.FloorRepository;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomRepository;
import utez.edu.mx.cleancheck.model.room.RoomState;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> create(RoomDto room) {
        Room foundRoom = roomRepository.findByName(room.getName()).orElse(null);
        if (foundRoom != null) {
            return new ApiResponse<>(
                    foundRoom, true, 400, "La habitacion ingresada ya esta registrada"
            );
        }
        Floor foundFloor = floorRepository.findById(room.getFloorId()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    null, true, 400, "El piso ingresado no esta registrado"
            );
        }
        Room newRoom = new Room();
        String id = UUID.randomUUID().toString();
        newRoom.setId(id);
        newRoom.setName(room.getName());
        newRoom.setFloor(foundFloor);
        newRoom.setIdentifier(room.getIdentifier());
        newRoom.setStatus(RoomState.CHECKED);
        newRoom.setRoomStatus(true);
        Room saveRoom = roomRepository.save(newRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion registrada correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> findAll() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones registradas"
            );
        }
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> findByFloor(RoomDto dto) {
        List<Room> rooms = roomRepository.findByFloorId(dto.getFloorId());
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> findByStatus(RoomDto dto) {

        List<Room> rooms;

        if (dto.getStatus() == null) {
            rooms = roomRepository.findAll();
        } else {

            boolean isValid = isValidState(dto.getStatus());

            if (!isValid) {
                return new ApiResponse<>(
                        null, true, 400, "El estado de la habitacion ingresado no es valido"
                );
            }

            rooms = roomRepository.findByStatus(dto.getStatus());
        }

        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> findByStatusAndBuilding(RoomDto dto) {

        Optional<Building> building = buildingRepository.findById(dto.getBuildingId());
        if (building.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }

        List<Room> rooms;

        if (dto.getStatus() == null) {
            rooms = roomRepository.findByBuildingId(building.get().getId());
        } else {

            boolean isValid = isValidState(dto.getStatus());

            if (!isValid) {
                return new ApiResponse<>(
                        null, true, 400, "El estado de la habitacion ingresado no es valido"
                );
            }

            rooms = roomRepository.findByStatusAndBuilding(dto.getStatus(), building.get().getId());
        }

        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Room> findById(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    foundRoom, true, 404, "La habitacion ingresada no esta registrada"
            );
        }
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion encontrada"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Room> findByName(String name) {
        Room foundRoom = roomRepository.findByName(name).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    foundRoom, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion encontrada"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> update(RoomDto room) {

        Room foundRoom = roomRepository.findById(room.getId()).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    foundRoom, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        Floor foundFloor = floorRepository.findById(room.getFloorId()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    null, true, 400, "El piso ingresado no esta registrado"
            );
        }

        foundRoom.setName(room.getName());
        foundRoom.setIdentifier(room.getIdentifier());
        foundRoom.setFloor(foundFloor);
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion actualizada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> delete(RoomDto room) {
        Room foundRoom = roomRepository.findByIdentifier(room.getIdentifier()).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    foundRoom, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        roomRepository.delete(foundRoom);
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion eliminada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> changeState(RoomDto room) {
        Room foundRoom = roomRepository.findById(room.getId()).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    foundRoom, true, 400, "La habitacion ingresada no esta registrada"
            );
        }

        boolean isValid = false;

        for (RoomState state : RoomState.values()) {
            if (state.equals(room.getNewStatus())) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            return new ApiResponse<>(
                    foundRoom, true, 400, "El estado de la habitacion ingresado no es valido"
            );
        }

        foundRoom.setStatus(room.getNewStatus());
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Estado de la habitacion actualizado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<List<Room>> createList(RoomDto room) {
        List<Room> rooms = room.getRooms();
        List<Room> registeredRooms = new ArrayList<>();
        for (Room r : rooms) {
            Floor foundFloor = floorRepository.findById(r.getFloor().getId()).orElse(null);
            if (foundFloor == null) {
                return new ApiResponse<>(
                        null, true, 400, "El piso ingresado no esta registrado"
                );
            }
            Room newRoom = new Room();
            String id = UUID.randomUUID().toString();
            newRoom.setId(id);
            newRoom.setName(r.getName());
            newRoom.setFloor(foundFloor);
            newRoom.setIdentifier(r.getIdentifier());
            newRoom.setStatus(RoomState.CHECKED);
            Room saveRoom = roomRepository.save(newRoom);
            registeredRooms.add(saveRoom);
        }
        return new ApiResponse<>(
                registeredRooms, false, 200, "Habitaciones registradas correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> findByBuilding(RoomDto dto) {
        Optional<Building> building = buildingRepository.findById(dto.getBuildingId());
        if (building.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }

        List<Room> rooms = roomRepository.findByBuildingId(dto.getBuildingId());

        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> changeRoomStatus(RoomDto dto) {
        Room foundRoom = roomRepository.findById(dto.getId()).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        foundRoom.setRoomStatus(!foundRoom.getRoomStatus());
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Estado de la habitacion actualizado correctamente"
        );
    }

    private boolean isValidState(RoomState state) {
        for (RoomState s : RoomState.values()) {
            if (s.equals(state)) {
                return true;
            }
        }
        return false;
    }


}
