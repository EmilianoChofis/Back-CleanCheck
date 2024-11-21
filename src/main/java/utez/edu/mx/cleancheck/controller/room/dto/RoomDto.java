package utez.edu.mx.cleancheck.controller.room.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import utez.edu.mx.cleancheck.model.room.RoomState;

@Data
public class RoomDto {
    @NotNull(groups = {Update.class, Delete.class, ChangeStatus.class}, message = "El id de la habitación es requerido")
    private String id;
    @NotNull(groups = {Create.class, Update.class}, message = "El identificador de la habitación es requerido")
    private String identifier;
    @NotNull(groups = {Create.class, Update.class}, message = "El nombre de la habitación es requerido")
    private String name;
    @NotNull(groups = {Create.class, Update.class}, message = "El id del piso es requerido")
    private String floorId;
    @NotNull(groups = {ChangeStatus.class}, message = "El nuevo estado de la habitación es requerido")
    private RoomState newStatus;

    public interface Create {}

    public interface Update {}
    public interface Delete {}
    public interface ChangeStatus {}
}
