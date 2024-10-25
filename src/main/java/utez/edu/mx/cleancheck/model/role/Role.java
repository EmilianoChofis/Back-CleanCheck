package utez.edu.mx.cleancheck.model.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.cleancheck.model.user.User;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor

public class Role {

    @Id
    private String id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> user;

}
