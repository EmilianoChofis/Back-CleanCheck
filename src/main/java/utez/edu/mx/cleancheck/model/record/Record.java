package utez.edu.mx.cleancheck.model.record;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomState;
import utez.edu.mx.cleancheck.model.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "records")
@Data
@NoArgsConstructor
public class Record {

    @Id
    private String id;

    @CreatedDate
    @Column(name = "created_at", insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private RoomState previousState;

    @Enumerated(EnumType.STRING)
    private RoomState newState;

    @ManyToOne
    @JsonIgnoreProperties({"records", "reports"})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties({"records", "reports"})
    @JoinColumn(name = "room_id")
    private Room room;
}
