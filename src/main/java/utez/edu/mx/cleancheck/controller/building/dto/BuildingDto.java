package utez.edu.mx.cleancheck.controller.building.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuildingDto {

    @NotNull(groups = {UpdateBuilding.class, DeleteBuilding.class, ChangeStatus.class}, message = "El id del edificio es requerido")
    private String id;

    @NotNull(groups = CreateBuilding.class)
    private String name;

    @NotNull(groups = CreateBuilding.class)
    private int number;

    public interface CreateBuilding {
    }

    public interface UpdateBuilding {
    }

    public interface DeleteBuilding {
    }

    public interface ChangeStatus{}

}
