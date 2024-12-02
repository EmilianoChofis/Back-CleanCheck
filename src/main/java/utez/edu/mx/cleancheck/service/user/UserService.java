package utez.edu.mx.cleancheck.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utez.edu.mx.cleancheck.controller.user.dto.UpdateUserDto;
import utez.edu.mx.cleancheck.model.role.Role;
import utez.edu.mx.cleancheck.model.role.RoleRepository;
import utez.edu.mx.cleancheck.model.user.User;
import utez.edu.mx.cleancheck.model.user.UserRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import utez.edu.mx.cleancheck.utils.PaginationDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail (String email) {
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public ApiResponse<User> findById(String id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            return new ApiResponse<>(
                    null, true, 400, "El usuario ingresado no existe"
            );
        }

        return new ApiResponse<>(
                user, false, 200, "Usuario encontrado"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<User> findByEmailAll(String email) {
        User user = repository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ApiResponse<>(
                    null, true, 400, "El usuario ingresado no existe"
            );
        }

        return new ApiResponse<>(
                user, false, 200, "Usuario encontrado"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<User>> getAllPagination(PaginationDto paginationDto) {
        if (paginationDto.getPaginationType().getFilter() == null || paginationDto.getPaginationType().getFilter().isEmpty() ||
                paginationDto.getPaginationType().getSortBy() == null || paginationDto.getPaginationType().getSortBy().isEmpty() ||
                paginationDto.getPaginationType().getOrder() == null || paginationDto.getPaginationType().getOrder().isEmpty()
        ) {
            return new ApiResponse<>(
                    null, true, 400, "Faltan parametros"
            );
        }

        if (!paginationDto.getPaginationType().getFilter().equals("name") && !paginationDto.getPaginationType().getFilter().equals("email") && !paginationDto.getPaginationType().getFilter().equals("role.name") || !paginationDto.getPaginationType().getSortBy().equals("name") && !paginationDto.getPaginationType().getSortBy().equals("email") && !paginationDto.getPaginationType().getSortBy().equals("role.name")){
            return new ApiResponse<>(
                    null, true, 400, "El filtro ingresado no es valido"
            );
        }

        if (!paginationDto.getPaginationType().getOrder().equals("asc") && !paginationDto.getPaginationType().getOrder().equals("desc")) {
            return new ApiResponse<>(
                    null, true, 400, "El orden ingresado no es valido"
            );
        }

        paginationDto.setValue("%" + paginationDto.getValue() + "%");
        long totalRegisters = repository.countUsers();
        List<User> users;

        switch (paginationDto.getPaginationType().getFilter()) {
            case "name":
                users = repository.searchByPaginationName(
                        paginationDto.getValue(),
                        PageRequest.of(paginationDto.getPaginationType().getPage(),
                                paginationDto.getPaginationType().getLimit(),
                                paginationDto.getPaginationType().getOrder().equalsIgnoreCase("ASC")
                                        ? Sort.by(paginationDto.getPaginationType().getSortBy()).ascending()
                                        : Sort.by(paginationDto.getPaginationType().getSortBy()).descending())
                );
                break;

            case "email":
                users = repository.searchByPaginationEmail(
                        paginationDto.getValue(),
                        PageRequest.of(paginationDto.getPaginationType().getPage(),
                                paginationDto.getPaginationType().getLimit(),
                                paginationDto.getPaginationType().getOrder().equalsIgnoreCase("ASC")
                                        ? Sort.by(paginationDto.getPaginationType().getSortBy()).ascending()
                                        : Sort.by(paginationDto.getPaginationType().getSortBy()).descending())
                );
                break;

            case "newState":
                users = repository.searchByPaginationRole(
                        paginationDto.getValue(),
                        PageRequest.of(paginationDto.getPaginationType().getPage(),
                                paginationDto.getPaginationType().getLimit(),
                                paginationDto.getPaginationType().getOrder().equalsIgnoreCase("ASC")
                                        ? Sort.by(paginationDto.getPaginationType().getSortBy()).ascending()
                                        : Sort.by(paginationDto.getPaginationType().getSortBy()).descending())
                );
                break;

            default:
                return new ApiResponse<>(
                        null, true, 400, "El filtro ingresado no es valido"
                );
        }

        for (User user : users) {
            user.setPassword(null);
        }

        return new ApiResponse<>(
                users, false, 200, "Registros encontrados"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public ApiResponse<User> update (UpdateUserDto user) {
        User updateUser = repository.findById(user.getId()).orElse(null);
        if (updateUser == null) {
            return new ApiResponse<>(
                    null, true, 400, "El usuario ingresado no existe"
            );
        }

        Role roleFound = roleRepository.findById(user.getRoleId()).orElse(null);
        if (roleFound == null) {
            return new ApiResponse<>(
                    null, true, 400, "El rol ingresado no existe"
            );
        }

        if (!updateUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            return new ApiResponse<>(
                    null, true, 400, "El correo electronico ya esta registrado"
            );
        }
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setRole(roleFound);
        return new ApiResponse<>(
                repository.save(updateUser), false, 200, "Usuario actualizado correctamente"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public ApiResponse<User> changeStatus (String id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            return new ApiResponse<>(
                    null, true, 400, "El usuario ingresado no existe"
            );
        }
        user.setStatus(!user.getStatus());
        return new ApiResponse<>(
                repository.save(user), false, 200, "Usuario actualizado"
        );
    }

}
