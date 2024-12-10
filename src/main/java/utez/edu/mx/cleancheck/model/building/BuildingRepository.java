package utez.edu.mx.cleancheck.model.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, String> {
    Optional<Building> findByNameIgnoreCase(String name);
    List<Building> findAllByStatus(Boolean status);
    @Query("SELECT COALESCE(MAX(b.number), 0) + 1 FROM Building b")
    int findNextBuildingNumber();
    Boolean existsByName(String name);


}
