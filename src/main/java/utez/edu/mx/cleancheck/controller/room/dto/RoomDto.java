package utez.edu.mx.cleancheck.controller.room.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomDto {

    @NotBlank( message = "El identificador de la habitación es requerido")
    private String identifier;

    @NotBlank(message = "El nombre de la habitación es requerido")
    private String name;

    @NotBlank(message = "El id del piso es requerido")
    private String floorId;
}
