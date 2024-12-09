package utez.edu.mx.cleancheck.model.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findAllByStatus(ReportState status);
}
