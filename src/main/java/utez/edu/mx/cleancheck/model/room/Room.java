package utez.edu.mx.cleancheck.model.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.floor.Floor;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.model.report.Report;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor

public class Room {
    @Id
    private String id;

    private String identifier;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoomState status;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floorId;

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Record> records = new ArrayList<>();


}
