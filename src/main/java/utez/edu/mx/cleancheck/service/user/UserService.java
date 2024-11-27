package utez.edu.mx.cleancheck.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.model.user.User;
import utez.edu.mx.cleancheck.model.user.UserRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import utez.edu.mx.cleancheck.utils.PaginationDto;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail (String email) {
        return repository.findByEmail(email);
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
}
