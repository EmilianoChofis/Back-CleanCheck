package utez.edu.mx.cleancheck.service.building;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.building.dto.BuildingDto;
import utez.edu.mx.cleancheck.model.building.Building;
import utez.edu.mx.cleancheck.model.building.BuildingRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class BuildingService {

    private final BuildingRepository repository;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Building> create(BuildingDto building) {
        Building foundBuilding = repository.findByNameIgnoreCase(building.getName()).orElse(null);
        if (foundBuilding != null) {
            return new ApiResponse<>(
                    foundBuilding, true, 400, "El edificio ingresado ya esta registrado"
            );
        }
        Building newBuilding = new Building();
        String id = UUID.randomUUID().toString();
        newBuilding.setId(id);
        newBuilding.setName(building.getName());
        Building saveBuilding = repository.save(newBuilding);
        return new ApiResponse<>(
                saveBuilding, false, 200, "Edificio registrado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Building>> findAll() {
        List<Building> buildings = repository.findAll();
        if (buildings.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay edificios registrados"
            );
        }
        return new ApiResponse<>(
                buildings, false, 200, "Edificios encontrados"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Building> findById(String id) {
        Building foundBuilding = repository.findById(id).orElse(null);
        if (foundBuilding == null) {
            return new ApiResponse<>(
                    foundBuilding, true, 400, "El edificio ingresado no esta registrado"
            );
        }
        return new ApiResponse<>(
                foundBuilding, false, 200, "Edificio encontrado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<Building> findByName(String name) {
        Building foundBuilding = repository.findByNameIgnoreCase(name).orElse(null);
        if (foundBuilding == null) {
            return new ApiResponse<>(
                    foundBuilding, true, 400, "El edificio ingresado no esta registrado"
            );
        }
        return new ApiResponse<>(
                foundBuilding, false, 200, "Edificio encontrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Building> update(BuildingDto buildingDto) {

        Building building = repository.findById(buildingDto.getId()).orElse(null);

        if (building == null) {
            return new ApiResponse<>(
                    null, true, 404, "El edificio ingresado no esta registrado"
            );
        }

        Building foundBuilding = repository.findByNameAndIdNot(buildingDto.getName(), buildingDto.getId()).orElse(null);
        if (foundBuilding != null) {
            return new ApiResponse<>(
                    foundBuilding, true, 400, "El edificio ingresado ya esta registrado"
            );
        }

        building.setName(buildingDto.getName());
        building.setNumber(buildingDto.getNumber());
        Building saveBuilding = repository.save(building);
        return new ApiResponse<>(
                saveBuilding, false, 200, "Edificio actualizado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Building> delete(BuildingDto buildingDto) {
        Building building = repository.findById(buildingDto.getId()).orElse(null);

        if (building == null) {
            return new ApiResponse<>(
                    null, true, 404, "El edificio ingresado no esta registrado"
            );
        }

        repository.delete(building);
        return new ApiResponse<>(
                building, false, 200, "Edificio eliminado correctamente"
        );
    }
}
