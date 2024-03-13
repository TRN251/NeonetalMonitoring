package ACT.project.NeonetalMonitor.Repository;

import ACT.project.NeonetalMonitor.Entity.DoctorReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorReportRepository extends JpaRepository<DoctorReport, Integer> {
  List<DoctorReport> findDoctorReportByAllowedUser(String Username);

}
