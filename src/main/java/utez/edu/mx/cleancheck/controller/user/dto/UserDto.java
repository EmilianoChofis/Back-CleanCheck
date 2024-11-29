package utez.edu.mx.cleancheck.controller.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import utez.edu.mx.cleancheck.model.role.Role;

@Data
@AllArgsConstructor

public class UserDto {

    @NotNull(groups = {Update.class, ChangeStatus.class})
    private String id;
    @NotNull(groups = {Update.class})
    private String name;
    @NotNull(groups = {Update.class})
    private String email;
    private String password;
    @NotNull(groups = {Update.class})
    private Boolean status;
    @NotNull(groups = {Update.class})
    private Role role;

    public interface Update{}
    public interface ChangeStatus{}
}
