package utez.edu.mx.cleancheck.model.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, String> {
    Optional<Building> findByName(String name);
    Optional<Building> findById(String id);
    Optional<Building> findByNameAndIdNot(String name, String id);
    Optional<Building> findByNameIgnoreCase(String name);
    @Query("SELECT COALESCE(MAX(b.number), 0) + 1 FROM Building b")
    int findNextBuildingNumber();


}
