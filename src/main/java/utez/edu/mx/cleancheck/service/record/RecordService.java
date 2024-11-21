package utez.edu.mx.cleancheck.service.record;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.cleancheck.controller.record.dto.RecordDto;
import utez.edu.mx.cleancheck.model.record.RecordRepository;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomRepository;
import utez.edu.mx.cleancheck.model.room.RoomState;
import utez.edu.mx.cleancheck.model.user.UserRepository;
import utez.edu.mx.cleancheck.utils.ApiResponse;
import utez.edu.mx.cleancheck.model.record.Record;
import utez.edu.mx.cleancheck.utils.PaginationDto;

import java.util.List;
import java.util.UUID;

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
        if (!roomRepository.existsById(recordDto.getRoom().getId())) {
            return new ApiResponse<>(
                    null, true, 400, "La habitacion ingresada no esta registrada"
            );
        }
        if (!userRepository.existsById(recordDto.getUser().getId())) {
            return new ApiResponse<>(
                    null, true, 400, "El usuario ingresado no esta registrado"
            );
        }

        boolean isValidPrevState = false;
        boolean isValidNewState = false;

        for (RoomState state : RoomState.values()) {
            if (state.equals(recordDto.getNewState())) {
                isValidNewState = true;
                break;
            }
        }

        for (RoomState state : RoomState.values()) {
            if (state.equals(recordDto.getPreviousState())) {
                isValidPrevState = true;
                break;
            }
        }

        if (!isValidNewState || !isValidPrevState) {
            return new ApiResponse<>(
                    null, true, 400, "El estado ingresado no es valido"
            );
        }


        Record newRecord = new Record();
        String id = UUID.randomUUID().toString();
        newRecord.setId(id);
        newRecord.setPreviousState(recordDto.getPreviousState());
        newRecord.setNewState(recordDto.getNewState());
        newRecord.setRoom(recordDto.getRoom());
        newRecord.setUser(recordDto.getUser());
        return new ApiResponse<>(
                recordRepository.save(newRecord), false, 200, "Registro creado correctamente"
        );
    }

    //get by room
    @Transactional(readOnly = true)
    public ApiResponse<List<Record>> findByRoom(PaginationDto paginationDto) {
        if (paginationDto.getPaginationType().getFilter() == null || paginationDto.getPaginationType().getFilter().isEmpty() ||
                paginationDto.getPaginationType().getSortBy() == null || paginationDto.getPaginationType().getSortBy().isEmpty() ||
                paginationDto.getPaginationType().getOrder() == null || paginationDto.getPaginationType().getOrder().isEmpty()
        ) {
            return new ApiResponse<>(
                    null, true, 400, "Faltan parametros"
            );
        }

        if (!paginationDto.getPaginationType().getFilter().equals("room") && !paginationDto.getPaginationType().getFilter().equals("user") && !paginationDto.getPaginationType().getFilter().equals("newState") && !paginationDto.getPaginationType().getFilter().equals("previousState") || !paginationDto.getPaginationType().getSortBy().equals("room") && !paginationDto.getPaginationType().getSortBy().equals("user") && !paginationDto.getPaginationType().getSortBy().equals("newState") && !paginationDto.getPaginationType().getSortBy().equals("previousState")){
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
        long totalRegisters = recordRepository.registersCount();
        List<Record> records;

        switch (paginationDto.getPaginationType().getFilter()) {
            case "room":
                records = recordRepository.searchByPaginationRoom(
                        paginationDto.getValue(),
                        PageRequest.of(paginationDto.getPaginationType().getPage(),
                                paginationDto.getPaginationType().getLimit(),
                                paginationDto.getPaginationType().getOrder().equalsIgnoreCase("ASC")
                                        ? Sort.by(paginationDto.getPaginationType().getSortBy()).ascending()
                                        : Sort.by(paginationDto.getPaginationType().getSortBy()).descending())
                );
                break;

            case "user":
                records = recordRepository.searchByPaginationUser(
                        paginationDto.getValue(),
                        PageRequest.of(paginationDto.getPaginationType().getPage(),
                                paginationDto.getPaginationType().getLimit(),
                                paginationDto.getPaginationType().getOrder().equalsIgnoreCase("ASC")
                                        ? Sort.by(paginationDto.getPaginationType().getSortBy()).ascending()
                                        : Sort.by(paginationDto.getPaginationType().getSortBy()).descending())
                );
                break;

            case "newState":
                records = recordRepository.searchByPaginationNewState(
                        paginationDto.getValue(),
                        PageRequest.of(paginationDto.getPaginationType().getPage(),
                                paginationDto.getPaginationType().getLimit(),
                                paginationDto.getPaginationType().getOrder().equalsIgnoreCase("ASC")
                                        ? Sort.by(paginationDto.getPaginationType().getSortBy()).ascending()
                                        : Sort.by(paginationDto.getPaginationType().getSortBy()).descending())
                );
                break;

            case "previousState":
                records = recordRepository.searchByPaginationPreviousState(
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

        return new ApiResponse<>(
                records, false, 200, "Registros encontrados"
        );
    }

}
