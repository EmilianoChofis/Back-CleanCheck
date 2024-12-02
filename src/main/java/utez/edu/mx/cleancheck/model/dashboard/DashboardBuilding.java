package utez.edu.mx.cleancheck.model.dashboard;

import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.building.Building;

@Data
@NoArgsConstructor
public class DashboardBuilding {
    private Building building;
    private int cleanedRooms;
    private int dirtyRooms;
    private int reportedRooms;
    private int disabledRooms;
    private int occupiedRooms;
    private int checkedRooms;
    private int inMaintenanceRooms;
    private int totalRooms;
}
