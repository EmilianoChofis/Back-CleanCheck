package utez.edu.mx.cleancheck.model.room;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.cleancheck.model.floor.Floor;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findByName (String name);
    Optional<Room> findByIdentifier (String identifier);
    List<Room> findByFloorId (String floorId);


    List<Room> findByStatus(RoomState status);
}
