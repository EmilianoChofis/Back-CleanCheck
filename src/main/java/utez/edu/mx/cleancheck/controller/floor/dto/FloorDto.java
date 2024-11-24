package utez.edu.mx.cleancheck.controller.floor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FloorDto {

    @NotNull(groups = {Update.class, FindById.class}, message = "El id del piso es requerido")
    private String id;

    @NotNull(groups = {Create.class, Update.class}, message = "El nombre del piso es requerido")
    private String name;

    @NotNull(groups = {Create.class, Update.class, FindByBuildingId.class}, message = "El id del edificio es requerido")
    private String buildingId;

    public interface Create {
    }

    public interface Update {
    }

    public interface FindById{
    }

    public interface FindByBuildingId{
    }

}
