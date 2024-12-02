package utez.edu.mx.cleancheck.utils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class PaginationDto {
    private String value;
    @NotNull(groups = {StateGet.class})
    private PaginationType paginationType;

    @Override
    public String toString() {
        return "PaginationDto{" +
                "value='" + value + '\'' +
                ", paginationType=" + paginationType.toString() +
                '}';
    }

    public interface StateGet {
    }


}
