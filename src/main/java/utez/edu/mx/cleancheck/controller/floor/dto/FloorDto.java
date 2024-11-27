package utez.edu.mx.cleancheck.controller.floor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import utez.edu.mx.cleancheck.model.floor.Floor;

import java.util.List;

@Data
public class FloorDto {

    @NotNull(groups = {Update.class, FindById.class}, message = "El id del piso es requerido")
    private String id;

    @NotNull(groups = {Create.class, Update.class}, message = "El nombre del piso es requerido")
    private String name;

    @NotNull(groups = {Create.class, Update.class, FindByBuildingId.class}, message = "El id del edificio es requerido")
    private String buildingId;

    @NotNull(groups = {CreateList.class}, message = "La lista de pisos es requerida")
    List<Floor> floors;

    public interface Create {
    }

    public interface Update {
    }

    public interface FindById{
    }

    public interface FindByBuildingId{
    }

    public interface CreateList {
    }

}
