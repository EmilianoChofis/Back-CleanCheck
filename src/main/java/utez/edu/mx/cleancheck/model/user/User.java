package utez.edu.mx.cleancheck.model.user;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.model.report.Report;
import utez.edu.mx.cleancheck.model.role.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor

public class User {
    @Id
    private String id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @Column(insertable = false)
    @ColumnDefault("true")
    private Boolean status;

    @Column(insertable = false)
    @ColumnDefault("true")
    private Boolean blocked;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Report> reports = new ArrayList<>();

}
