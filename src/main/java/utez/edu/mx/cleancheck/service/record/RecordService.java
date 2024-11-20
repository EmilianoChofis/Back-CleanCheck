package utez.edu.mx.cleancheck.service.record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.record.dto.RecordDto;
import utez.edu.mx.cleancheck.model.record.RecordRepository;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomRepository;
import utez.edu.mx.cleancheck.model.user.UserRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import utez.edu.mx.cleancheck.model.record.Record;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    //create
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Record> insert(RecordDto recordDto) {
        if(!roomRepository.existsById(recordDto.getRoomId().getId())){
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        if(!userRepository.existsById(recordDto.getUserId().getId())){
            return new ApiResponse<>(
                    null, true, 400, "El usuario ingresado no esta registrado"
            );
        }
        Record newRecord = new Record();
        newRecord.setPreviousState(recordDto.getPreviousState());
        newRecord.setNewState(recordDto.getNewState());
        newRecord.setRoomId(recordDto.getRoomId());
        newRecord.setUserId(recordDto.getUserId());
        return new ApiResponse<>(
                recordRepository.save(newRecord), false, 200, "Registro creado correctamente"
        );
    }

    //get by room
    @Transactional(readOnly = true)
    public ApiResponse<List<Record>> findByRoom(Room room) {
        List<Record> records = recordRepository.findByRoomId(room);
        if (records.isEmpty()) {
            return new ApiResponse<>(
                    null, true, 400, "No hay registros"
            );
        }
        return new ApiResponse<>(
                records, false, 200, "Registros encontrados"
        );
    }

}
