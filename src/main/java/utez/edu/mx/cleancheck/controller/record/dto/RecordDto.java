package utez.edu.mx.cleancheck.controller.record.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import utez.edu.mx.cleancheck.model.room.Room;
import utez.edu.mx.cleancheck.model.room.RoomState;
import utez.edu.mx.cleancheck.model.user.User;

@Data
public class RecordDto {
        @NotNull(groups = {RecordDto.Insert.class})
        private RoomState previousState;

        @NotNull(groups = {RecordDto.Insert.class})
        private RoomState newState;

        @NotNull(groups = {RecordDto.Insert.class})
        private User userId;

        @NotNull(groups = {RecordDto.Insert.class, RecordDto.GetByRoom.class})
        private Room roomId;

        public interface Insert {
        }

        public interface GetByRoom {
        }

    }
