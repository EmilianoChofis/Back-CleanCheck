package utez.edu.mx.cleancheck.model.record;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.cleancheck.model.room.Room;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, String> {
    List<Record> findByRoomId(Room room);
  }
