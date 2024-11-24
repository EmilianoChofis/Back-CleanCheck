package utez.edu.mx.cleancheck.service.floor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.floor.dto.FloorDto;
import utez.edu.mx.cleancheck.model.building.Building;
import utez.edu.mx.cleancheck.model.building.BuildingRepository;
import utez.edu.mx.cleancheck.model.floor.Floor;
import utez.edu.mx.cleancheck.model.floor.FloorRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FloorService {

    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Floor> create(FloorDto floor) {

        Building foundBuilding = buildingRepository.findById(floor.getBuildingId()).orElse(null);
        if (foundBuilding == null) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }

        Floor newFloor = new Floor();
        String id = UUID.randomUUID().toString();
        newFloor.setId(id);
        newFloor.setName(floor.getName());
        newFloor.setBuilding(foundBuilding);
        Floor saveFloor = floorRepository.save(newFloor);
        return new ApiResponse<>(
                saveFloor, false, 200, "Piso registrado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Floor>> findAll() {
        List<Floor> floors = floorRepository.findAll();
        if (floors.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay pisos registrados"
            );
        }
        return new ApiResponse<>(
                floors, false, 200, "Pisos encontrados"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Floor> findById(FloorDto dto) {
        Floor foundFloor = floorRepository.findById(dto.getId()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    foundFloor, true, 400, "El piso ingresado no esta registrado"
            );
        }

        return new ApiResponse<>(
                foundFloor, false, 200, "Piso encontrado"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Floor> findByName(String name) {
        Floor foundFloor = floorRepository.findByName(name).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    foundFloor, true, 400, "El piso ingresado no esta registrado"
            );
        }
        return new ApiResponse<>(
                foundFloor, false, 200, "Piso encontrado"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Floor> update(FloorDto floor) {

        Floor foundFloor = floorRepository.findById(floor.getId()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    foundFloor, true, 400, "El piso ingresado no esta registrado"
            );
        }

        Building foundBuilding = buildingRepository.findById(floor.getBuildingId()).orElse(null);
        if (foundBuilding == null) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }

        foundFloor.setName(floor.getName());
        foundFloor.setBuilding(foundBuilding);
        Floor saveFloor = floorRepository.save(foundFloor);
        return new ApiResponse<>(
                saveFloor, false, 200, "Piso actualizado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Floor> delete(FloorDto floor) {
        Floor foundFloor = floorRepository.findByName(floor.getName()).orElse(null);
        if (foundFloor == null) {
            return new ApiResponse<>(
                    foundFloor, true, 400, "El piso ingresado no esta registrado"
            );
        }
        floorRepository.delete(foundFloor);
        return new ApiResponse<>(
                foundFloor, false, 200, "Piso eliminado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Floor>> findByBuildingId(FloorDto dto) {

        Optional<Building> foundBuilding = buildingRepository.findById(dto.getBuildingId());
        if (foundBuilding.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }

        List<Floor> floors = floorRepository.findByBuildingId(dto.getBuildingId());

        return new ApiResponse<>(
                floors, false, 200, "Pisos encontrados"
        );
    }
}
