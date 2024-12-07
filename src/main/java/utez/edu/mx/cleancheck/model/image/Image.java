package utez.edu.mx.cleancheck.model.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("images")
    @JoinColumn(name = "report_id")
    private Report report;
}
