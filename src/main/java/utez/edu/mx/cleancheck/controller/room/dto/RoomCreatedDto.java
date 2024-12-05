package utez.edu.mx.cleancheck.controller.room.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class RoomCreatedDto {
    @NotBlank(message = "El identificador no puede estar vacio")
    private String identifier;
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @NotBlank(message = "El id del piso no puede estar vacio")
    private String floorId;
}
