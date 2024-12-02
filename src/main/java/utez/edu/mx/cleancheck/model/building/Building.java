package utez.edu.mx.cleancheck.model.building;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import utez.edu.mx.cleancheck.model.floor.Floor;

import java.time.LocalDateTime;
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

    @ColumnDefault("true")
    @Column(insertable = false)
    private Boolean status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Floor> floors = new ArrayList<>();
}
