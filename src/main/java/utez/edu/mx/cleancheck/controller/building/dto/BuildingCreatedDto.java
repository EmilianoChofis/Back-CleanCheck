package utez.edu.mx.cleancheck.controller.building.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class BuildingCreatedDto {
    @NotBlank(message = "El id no puede estar vacio")
    String id;
    @NotBlank(message = "El nombre no puede estar vacio")
    String name;
    @NotNull(message = "El numero no puede estar vacio")
    int number;
}
