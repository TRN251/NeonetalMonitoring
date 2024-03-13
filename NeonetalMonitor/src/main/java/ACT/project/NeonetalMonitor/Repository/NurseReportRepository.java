package ACT.project.NeonetalMonitor.Repository;

import ACT.project.NeonetalMonitor.Entity.NurseReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("NurserReportRepository")
public interface NurseReportRepository extends JpaRepository<NurseReports, Integer> {
}
