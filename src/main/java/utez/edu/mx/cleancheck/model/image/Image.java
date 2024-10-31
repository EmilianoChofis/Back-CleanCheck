package utez.edu.mx.cleancheck.model.image;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.report.Report;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor

public class Image {

    @Id
    private String id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Report reportId;
}
