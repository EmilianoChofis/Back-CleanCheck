package utez.edu.mx.cleancheck.model.building;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.floor.Floor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
@Data
@NoArgsConstructor

public class Building {
    @Id
    private String id;

    private String name;

    private int number;

    @OneToMany(mappedBy = "buildingId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Floor> floors = new ArrayList<>();
}
