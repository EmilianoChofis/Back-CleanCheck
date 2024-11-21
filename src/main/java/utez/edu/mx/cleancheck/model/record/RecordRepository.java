package utez.edu.mx.cleancheck.model.record;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utez.edu.mx.cleancheck.model.room.Room;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, String> {
    List<Record> findByRoomId(Room room);

    @Query(value = "SELECT COUNT(id) FROM records ", nativeQuery = true)
    long registersCount();

    @Query(value = "SELECT r FROM Record r WHERE UPPER(r.room.name) LIKE UPPER(?1)")
    List<Record> searchByPaginationRoom(String value, Pageable offset);

    @Query(value = "SELECT r FROM Record r WHERE UPPER(r.user.name) LIKE UPPER(?1)")
    List<Record> searchByPaginationUser(String value, Pageable offset);

    @Query(value = "SELECT r FROM Record r WHERE UPPER(r.newState) LIKE UPPER(?1)")
    List<Record> searchByPaginationNewState(String value, Pageable offset);

    @Query(value = "SELECT r FROM Record r WHERE UPPER(r.previousState) LIKE UPPER(?1)")
    List<Record> searchByPaginationPreviousState(String value, Pageable offset);


  }
