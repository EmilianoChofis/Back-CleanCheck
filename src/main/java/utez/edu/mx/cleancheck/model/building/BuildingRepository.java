package utez.edu.mx.cleancheck.model.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, String> {
    Optional<Building> findByName(String name);
    Optional<Building> findById(String id);
    Optional<Building> findByNameAndIdNot(String name, String id);
    Optional<Building> findByNameIgnoreCase(String name);
    @Query("SELECT COALESCE(MAX(b.number), 0) + 1 FROM Building b")
    int findNextBuildingNumber();

    @Query("SELECT b FROM Building b WHERE b.status = true ORDER BY b.number ASC")
    List<Building> findAllActiveBuildings();


}
