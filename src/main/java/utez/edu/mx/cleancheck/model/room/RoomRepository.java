package utez.edu.mx.cleancheck.model.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findByName (String name);
    Optional<Room> findByIdentifier (String identifier);
    List<Room> findByFloorId (String floorId);
    List<Room> findByStatus(RoomState status);

    @Query("SELECT r FROM Room r WHERE r.status = ?1 AND r.floor.building.id = ?2")
    List<Room> findByStatusAndBuilding(RoomState status, String buildingId);

    @Query("SELECT r FROM Room r WHERE r.floor.building.id = ?1")
    List<Room> findByBuildingId(String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.floor.building.id = ?1 AND r.status = 'CLEAN'")
    int findCleanedRoomsByBuildingId(String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.floor.building.id = ?1 AND r.status = 'UNOCCUPIED'")
    int findDirtyRoomsByBuildingId(String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.floor.building.id = ?1 AND r.status = 'OCCUPIED'")
    int findOccupiedRoomsByBuildingId(String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.floor.building.id = ?1 AND r.status = 'CHECKED'")
    int findCheckRoomsByBuildingId(String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.floor.building.id = ?1 AND r.status = 'IN_MAINTENANCE'")
    int findInMaintenanceRoomsByBuildingId(String buildingId);

    @Query(value = "SELECT COUNT(ro.id) FROM rooms ro\n" +
            "    INNER JOIN reports r ON ro.id = r.room_id\n" +
            "    INNER JOIN floors f ON ro.floor_id = f.id\n" +
            "    INNER JOIN buildings b ON f.building_id = b.id\n" +
            "    WHERE (r.status = 'PENDING' OR r.status = 'IN_PROGRESS')\n" +
            "    AND b.id = ?1", nativeQuery = true)
    int findReportedRoomsByBuildingId(String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.roomStatus = ?1 AND r.floor.building.id = ?2")
    int countByStatus(boolean status, String buildingId);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.floor.building.id = ?1")
    int totalRoomsByBuildingId(String buildingId);

    Boolean existsByNameAndFloorId(String name, String floorId);
    Boolean existsByIdentifierAndFloorId(String identifier, String floorId);
}
