package utez.edu.mx.cleancheck.model.floor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FloorRepository extends JpaRepository<Floor, String> {

    Optional<Floor> findByName(String name);
    Optional<Floor> findByNameIgnoreCase(String name);

    List<Floor> findByBuildingId(String buildingId);
}
