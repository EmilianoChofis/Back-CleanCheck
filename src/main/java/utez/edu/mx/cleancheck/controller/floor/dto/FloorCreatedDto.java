package utez.edu.mx.cleancheck.controller.floor.dto;

import lombok.Data;
import utez.edu.mx.cleancheck.model.building.Building;

@Data

public class FloorCreatedDto {

    String id;
    String name;
    Building building;
}
