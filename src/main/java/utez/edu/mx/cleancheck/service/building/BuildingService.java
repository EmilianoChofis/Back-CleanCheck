package utez.edu.mx.cleancheck.service.building;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.building.dto.BuildingCreatedDto;
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
    private final BuildingRepository buildingRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<BuildingCreatedDto> create (BuildingDto building) {
        Building foundBuilding = repository.findByNameIgnoreCase(building.getName()).orElse(null);
        if (foundBuilding != null) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado ya esta registrado"
            );
        }
        Building newBuilding = new Building();
        String id = UUID.randomUUID().toString();
        newBuilding.setId(id);
        newBuilding.setName(building.getName());
        newBuilding.setNumber(buildingRepository.findNextBuildingNumber());
        Building saveBuilding = repository.save(newBuilding);
        BuildingCreatedDto buildingCreated = new BuildingCreatedDto();
        BeanUtils.copyProperties(saveBuilding, buildingCreated);
        return new ApiResponse<>(
                buildingCreated, false, 200, "Edificio registrado correctamente"
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
                    null, true, 400, "El edificio ingresado no esta registrado"
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
                    null, true, 400, "El edificio ingresado no esta registrado"
            );
        }
        return new ApiResponse<>(
                foundBuilding, false, 200, "Edificio encontrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Building> update(BuildingCreatedDto building) {

        Building updateBuilding = repository.findById(building.getId()).orElse(null);
        if (building == null) {
            return new ApiResponse<>(
                    null, true, 404, "El edificio ingresado no esta registrado"
            );
        }
        if (!updateBuilding.getName().equals(building.getName()) && buildingRepository.existsByName(building.getName())) {
            return new ApiResponse<>(
                    null, true, 400, "El edificio ingresado ya esta registrado"
            );
        }
        updateBuilding.setName(building.getName());
        updateBuilding.setNumber(building.getNumber());
        Building saveBuilding = repository.save(updateBuilding);
        return new ApiResponse<>(
                saveBuilding, false, 200, "Edificio actualizado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Building> delete(String id) {
        Building building = repository.findById(id).orElse(null);

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

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Building> changeStatus(String id) {

        Building building = repository.findById(id).orElse(null);

        if (building == null) {
            return new ApiResponse<>(
                    null, true, 404, "El edificio ingresado no esta registrado"
            );
        }

        building.setStatus(!building.getStatus());
        Building saveBuilding = repository.save(building);
        return new ApiResponse<>(
                saveBuilding, false, 200, "Estado del edificio actualizado correctamente"
        );

    }
}
