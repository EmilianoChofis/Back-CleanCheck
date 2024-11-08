package utez.edu.mx.cleancheck.service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.role.dto.RoleDto;
import utez.edu.mx.cleancheck.model.role.Role;
import utez.edu.mx.cleancheck.model.role.RoleRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;

import java.sql.SQLException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class RoleService {

    private final RoleRepository repository;

    @Value("${receptionist.name}")
    private String receptionistName;
    @Value("${maid.name}")
    private String maidName;
    @Value("${manager.name}")
    private String managerName;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> create (RoleDto role) {
        Role foundRole = repository.findByName(role.getName()).orElse(null);
        if (foundRole != null) {
            return new ApiResponse<>(
                    foundRole, true, HttpStatus.BAD_REQUEST.value(), "El rol ingresado ya esta registrado"
            );
        }
        Role newRole = new Role();
        String id = UUID.randomUUID().toString();
        newRole.setId(id);
        newRole.setName(role.getName());
        newRole.setDescription(role.getDescription());
        Role saveRole = repository.save(newRole);
        return new ApiResponse<>(
                saveRole, false, HttpStatus.OK.value(), "Rol registrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> createManager (Role role) {
        Role foundRole = repository.findByName(managerName).orElse(null);
        if (foundRole != null) {
            return new ApiResponse<>(
                    foundRole, true, HttpStatus.FOUND.value(), "El rol manager ya se encuentra registrado"
            );
        }
        String id = UUID.randomUUID().toString();
        role.setId(id);
        Role newRole = repository.save(role);
        return new ApiResponse<>(
                newRole, false, HttpStatus.OK.value(), "Rol de manager registrado correctamente!"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> createReceptionist (Role role) {
        Role foundRole = repository.findByName(receptionistName).orElse(null);
        if (foundRole != null) {
            return new ApiResponse<>(
                    foundRole, true, HttpStatus.FOUND.value(), "El rol de recepcionista ya se encuentra registrado"
            );
        }
        String id = UUID.randomUUID().toString();
        role.setId(id);
        Role newRole = repository.save(role);
        return new ApiResponse<>(
                newRole, false, HttpStatus.OK.value(), "Rol de recepcionista registrado correctamente!"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> createMaid (Role role) {
        Role foundRole = repository.findByName(maidName).orElse(null);
        if (foundRole != null) {
            return new ApiResponse<>(
                    foundRole, true, HttpStatus.FOUND.value(), "El rol de mucama ya se encuentra registrado"
            );
        }
        String id = UUID.randomUUID().toString();
        role.setId(id);
        Role newRole = repository.save(role);
        return new ApiResponse<>(
                newRole, false, HttpStatus.OK.value(), "Rol de mucama registrado correctamente!"
        );
    }
}
