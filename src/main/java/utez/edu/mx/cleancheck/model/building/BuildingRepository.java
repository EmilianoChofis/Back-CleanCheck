package utez.edu.mx.cleancheck.model.building;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, String> {
    Optional<Building> findByName(String name);
}
