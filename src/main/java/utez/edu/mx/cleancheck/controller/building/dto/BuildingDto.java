package utez.edu.mx.cleancheck.controller.building.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BuildingDto {

    @NotBlank(message = "El nombre del edificio es requerido")
    private String name;

}
