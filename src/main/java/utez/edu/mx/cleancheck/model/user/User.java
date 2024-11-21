package utez.edu.mx.cleancheck.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.model.report.Report;
import utez.edu.mx.cleancheck.model.role.Role;

import java.time.LocalDateTime;
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

    private String password;

    @Column(name = "created_at", insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;

    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean blocked;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Record> records;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Report> reports;

}
