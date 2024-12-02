package utez.edu.mx.cleancheck.controller.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class UpdateUserDto {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotBlank(message = "El correo electronico paterno es requerido")
    @Email(message = "El correo electronico no es valido")
    private String email;

    @NotBlank(message = "El id del rol es requerido")
    private String roleId;
}
