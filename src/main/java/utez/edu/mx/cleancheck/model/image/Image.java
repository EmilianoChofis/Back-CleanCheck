package utez.edu.mx.cleancheck.model.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import utez.edu.mx.cleancheck.model.report.Report;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor

public class Image {

    @Id
    private String id;

    private String url;

    private String key;

    @CreationTimestamp
    @Column(name = "firmed_at", updatable = false)
    private LocalDateTime firmedAt;

    @ManyToOne
    @JsonIgnoreProperties("images")
    @JoinColumn(name = "report_id")
    private Report report;
}
