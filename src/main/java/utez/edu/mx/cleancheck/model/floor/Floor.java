package utez.edu.mx.cleancheck.model.floor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.building.Building;
import utez.edu.mx.cleancheck.model.room.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "floors")
@Data
@NoArgsConstructor

public class Floor {
    @Id
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;

    @Column(name = "created_at", insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();

}
