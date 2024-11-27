package utez.edu.mx.cleancheck.model.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail (String email);

    @Query(value = "SELECT COUNT(id) FROM users", nativeQuery = true)
    long countUsers();

    @Query(value = "SELECT u FROM User u WHERE UPPER(u.name) LIKE UPPER(?1)")
    List<User> searchByPaginationName(String value, Pageable offset);

    @Query(value = "SELECT u.id, u.name, u.status, u.blocked, u.createdAt, u.email, u.role FROM User u WHERE UPPER(u.email) LIKE UPPER(?1)")
    List<User> searchByPaginationEmail(String value, Pageable offset);

    @Query(value = "SELECT u.id, u.name, u.status, u.blocked, u.createdAt, u.email, u.role FROM User u WHERE UPPER(u.role.name) LIKE UPPER(?1)")
    List<User> searchByPaginationRole(String value, Pageable offset);

}
