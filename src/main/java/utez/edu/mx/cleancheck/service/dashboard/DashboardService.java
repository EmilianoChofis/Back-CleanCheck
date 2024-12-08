package utez.edu.mx.cleancheck.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.model.building.Building;
import utez.edu.mx.cleancheck.model.building.BuildingRepository;
import utez.edu.mx.cleancheck.model.dashboard.DashboardBuilding;
import utez.edu.mx.cleancheck.model.room.RoomRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardService {


    private final BuildingRepository buildingRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public ApiResponse<List<DashboardBuilding>> findAll() {

        List<Building> activeBuilding = buildingRepository.findAllByStatus(true);
        List<DashboardBuilding> dashboardBuildings = new ArrayList<>();

        for (Building building : activeBuilding) {
            DashboardBuilding dashboardBuilding = new DashboardBuilding();

            int cleanedRooms = roomRepository.findCleanedRoomsByBuildingId(building.getId());
            int dirtyRooms = roomRepository.findDirtyRoomsByBuildingId(building.getId());
            int reportedRooms = roomRepository.findReportedRoomsByBuildingId(building.getId());
            int disabledRooms = roomRepository.countByStatus(false, building.getId());
            int totalRooms = roomRepository.totalRoomsByBuildingId(building.getId());
            int occupiedRooms = roomRepository.findOccupiedRoomsByBuildingId(building.getId());
            int checkedRooms = roomRepository.findCheckRoomsByBuildingId(building.getId());
            int inMaintenanceRooms = roomRepository.findInMaintenanceRoomsByBuildingId(building.getId());

            dashboardBuilding.setBuilding(building);
            dashboardBuilding.setCleanedRooms(cleanedRooms);
            dashboardBuilding.setDirtyRooms(dirtyRooms);
            dashboardBuilding.setReportedRooms(reportedRooms);
            dashboardBuilding.setDisabledRooms(disabledRooms);
            dashboardBuilding.setTotalRooms(totalRooms);
            dashboardBuilding.setOccupiedRooms(occupiedRooms);
            dashboardBuilding.setCheckedRooms(checkedRooms);
            dashboardBuilding.setInMaintenanceRooms(inMaintenanceRooms);

            dashboardBuildings.add(dashboardBuilding);
        }

        return new ApiResponse<>(dashboardBuildings, false, 200, "Edificios encontrados");


    }

}
