package utez.edu.mx.cleancheck.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.room.dto.RoomDto;
import utez.edu.mx.cleancheck.controller.room.dto.RoomUpdateDto;
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
        Floor foundFloor = floorRepository.findById(room.getFloorId()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    null, true, 400, "El piso ingresado no esta registrado"
            );
        }
        if (roomRepository.existsByNameAndFloorId(room.getName(), room.getFloorId())) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion con el nombre" + room.getName() + " ya esta registrada en el piso"
            );
        }

        if (roomRepository.existsByIdentifierAndFloorId(room.getIdentifier(), room.getFloorId())) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion con el identificador " + room.getIdentifier() + " ya esta registrada"
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

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<List<Room>> createList(List<RoomDto> rooms) {
        List<Room> registeredRooms = new ArrayList<>();
        for (RoomDto r : rooms) {
            Floor foundFloor = floorRepository.findById(r.getFloorId()).orElse(null);
            if (foundFloor == null) {
                return new ApiResponse<>(
                        null, true, 400, "El piso ingresado no esta registrado"
                );
            }
            if (roomRepository.existsByNameAndFloorId(r.getName(), r.getFloorId())) {
                return new ApiResponse<>(
                        null, true, 400, "La habitacion con el nombre" + r.getName() + " ya esta registrada en el piso"
                );
            }
            if (roomRepository.existsByIdentifierAndFloorId(r.getIdentifier(), r.getFloorId())) {
                return new ApiResponse<>(
                        null, true, 400, "La habitacion con el identificador " + r.getIdentifier() + " ya esta registrada"
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
    public ApiResponse<List<Room>> getAll() {
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
    public ApiResponse<Room> getById(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 404, "La habitacion ingresada no esta registrada"
            );
        }
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion encontrada"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Room> getByName(String name) {
        Room foundRoom = roomRepository.findByName(name).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion encontrada"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllByFloor(String floorId) {
        Floor floor = floorRepository.findById(floorId).orElse(null);
        if (floor == null) {
            return new ApiResponse<>(
                    null, true, 400, "El piso ingresado no esta registrado"
            );
        }
        List<Room> rooms = roomRepository.findByFloorId(floorId);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones registradas en el piso"
            );
        }
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllByBuilding(String buildingId) {
        Building building = buildingRepository.findById(buildingId).orElse(null);
        if (building == null) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }
        List<Room> rooms = roomRepository.findByBuildingId(buildingId);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones registradas en el edificio"
            );
        }
        rooms.sort((room1, room2) -> {
            String floorName1 = room1.getFloor().getName().replaceAll("\\D+", "");
            String floorName2 = room2.getFloor().getName().replaceAll("\\D+", "");
            int floorComparison = Integer.compare(Integer.parseInt(floorName1), Integer.parseInt(floorName2));
            if (floorComparison != 0) {
                return floorComparison;
            }
            String roomId1 = room1.getIdentifier().replaceAll("\\D+", "");
            String roomId2 = room2.getIdentifier().replaceAll("\\D+", "");
            return Integer.compare(Integer.parseInt(roomId1), Integer.parseInt(roomId2));
        });
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllOccupied() {
        List<Room> rooms = roomRepository.findByStatus(RoomState.OCCUPIED);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones ocupadas"
            );
        }
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllUnoccupied() {
        List<Room> rooms = roomRepository.findByStatus(RoomState.UNOCCUPIED);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones desocupadas"
            );
        }
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllClean() {
        List<Room> rooms = roomRepository.findByStatus(RoomState.CLEAN);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones limpias"
            );
        }
        rooms.sort((room1, room2) -> {
            String floorName1 = room1.getFloor().getName().replaceAll("\\D+", "");
            String floorName2 = room2.getFloor().getName().replaceAll("\\D+", "");
            int floorComparison = Integer.compare(Integer.parseInt(floorName1), Integer.parseInt(floorName2));
            if (floorComparison != 0) {
                return floorComparison;
            }
            String roomId1 = room1.getIdentifier().replaceAll("\\D+", "");
            String roomId2 = room2.getIdentifier().replaceAll("\\D+", "");
            return Integer.compare(Integer.parseInt(roomId1), Integer.parseInt(roomId2));
        });
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllChecked() {
        List<Room> rooms = roomRepository.findByStatus(RoomState.CHECKED);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones verificadas"
            );
        }
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Room>> getAllInMaintenance() {
        List<Room> rooms = roomRepository.findByStatus(RoomState.IN_MAINTENANCE);
        if (rooms.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay habitaciones en mantenimiento"
            );
        }
        return new ApiResponse<>(
                rooms, false, 200, "Habitaciones encontradas"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> update(RoomUpdateDto room) {

        Room foundRoom = roomRepository.findById(room.getRoomId()).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        Floor foundFloor = floorRepository.findById(room.getFloorId()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    null, true, 400, "El piso ingresado no esta registrado"
            );
        }

        if (roomRepository.existsByNameAndFloorId(room.getName(), room.getFloorId())) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion con el nombre" + room.getName() + " ya esta registrada en el piso"
            );
        }

        if (roomRepository.existsByIdentifierAndFloorId(room.getIdentifier(), room.getFloorId())) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion con el identificador " + room.getIdentifier() + " ya esta registrada"
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
    public ApiResponse<Room> changeStatusOccupied(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        foundRoom.setStatus(RoomState.OCCUPIED);
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion actualizada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> changeStatusUnoccupied(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        foundRoom.setStatus(RoomState.UNOCCUPIED);
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion actualizada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> changeStatusClean(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        foundRoom.setStatus(RoomState.CLEAN);
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion actualizada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> changeStatusChecked(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        foundRoom.setStatus(RoomState.CHECKED);
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion actualizada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> changeStatusInMaintenance(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        foundRoom.setStatus(RoomState.IN_MAINTENANCE);
        Room saveRoom = roomRepository.save(foundRoom);
        return new ApiResponse<>(
                saveRoom, false, 200, "Habitacion actualizada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> deleteById(String id) {
        Room foundRoom = roomRepository.findById(id).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        roomRepository.delete(foundRoom);
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion eliminada correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Room> deleteByIdentifier(String identifier) {
        Room foundRoom = roomRepository.findByIdentifier(identifier).orElse(null);
        if (foundRoom == null) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        roomRepository.delete(foundRoom);
        return new ApiResponse<>(
                foundRoom, false, 200, "Habitacion eliminada correctamente"
        );
    }

}
