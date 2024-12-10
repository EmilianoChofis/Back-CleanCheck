package utez.edu.mx.cleancheck.controller.floor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class FloorUpdateDto {
    @NotBlank(message = "El id no puede estar vacio")
    String floorId;

    @NotBlank(message = "El nombre no puede estar vacio")
    String name;

    @NotBlank(message = "El id del edificio no puede estar vacio")
    String buildingId;
}
