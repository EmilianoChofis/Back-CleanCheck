package utez.edu.mx.cleancheck.controller.report.dto;

import lombok.Data;

import java.util.List;

@Data

public class ReportDto {
    private String description;
    private String userId;
    private String roomId;
    private List<String> files;
}
