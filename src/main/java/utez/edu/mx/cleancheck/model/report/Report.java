package utez.edu.mx.cleancheck.model.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import utez.edu.mx.cleancheck.model.image.Image;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor

public class Report {
    @Id
    private String id;

    private String description;

    @CreatedDate
    @Column(name = "created_at", insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reportId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room roomId;



}
