package ACT.project.NeonetalMonitor.Repository;

import ACT.project.NeonetalMonitor.Entity.DoctorReport;
import ACT.project.NeonetalMonitor.Entity.DoctorsOrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("DoctorsOrderSheetRepository")
public interface DoctorOrderSheetRepository extends JpaRepository<DoctorsOrderSheet, Integer> {

}
