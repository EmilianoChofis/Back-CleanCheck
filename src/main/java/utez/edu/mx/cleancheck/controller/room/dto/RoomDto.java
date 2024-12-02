package utez.edu.mx.cleancheck.controller.room.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomState;

import java.util.List;

@Data
public class RoomDto {
    @NotBlank(groups = {Update.class, Delete.class, ChangeStatus.class, ChangeRoomStatus.class}, message = "El id de la habitación es requerido")
    private String id;
    @NotBlank(groups = {Create.class, Update.class}, message = "El identificador de la habitación es requerido")
    private String identifier;
    @NotBlank(groups = {Create.class, Update.class}, message = "El nombre de la habitación es requerido")
    private String name;
    @NotBlank(groups = {Create.class, Update.class, FindByFloor.class}, message = "El id del piso es requerido")
    private String floorId;
    @NotBlank(groups = {ChangeStatus.class}, message = "El nuevo estado de la habitación es requerido")
    private RoomState newStatus;
    @NotBlank(groups = {FindByStatus.class}, message = "El estado de la habitación es requerido")
    private RoomState status;
    @NotBlank(groups = {CreateList.class}, message = "La lista de habitaciones es requerida")
    List<Room> rooms;
    @NotBlank(groups = {FindByBuilding.class}, message = "El id del edificio es requerido")
    private String buildingId;

    public interface Create {}
    public interface Update {}
    public interface Delete {}
    public interface ChangeStatus {}
    public interface FindByFloor {}
    public interface FindByStatus {}
    public interface CreateList {}
    public interface FindByBuilding {}
    public interface ChangeRoomStatus{}
}
