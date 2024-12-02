package utez.edu.mx.cleancheck.controller.floor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FloorDto {

    @NotBlank(message = "El nombre del piso es requerido")
    private String name;

    @NotBlank(message = "El id del edificio es requerido")
    private String buildingId;

}
