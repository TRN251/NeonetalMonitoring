package ACT.project.NeonetalMonitor.Service;

import ACT.project.NeonetalMonitor.Entity.NurseReports;
import ACT.project.NeonetalMonitor.Repository.NurseReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NurseReportServices {
    NurseReportRepository nurseReportRepository;

    @Autowired
    NurseReportServices(NurseReportRepository nurseReportRepository) {
        this.nurseReportRepository = nurseReportRepository;
    }

    public void saveNurseReport(NurseReports report) {
        nurseReportRepository.save(report);
    }

    public List<NurseReports> getAllNurseReprot() {
        return nurseReportRepository.findAll();
    }

   /* public List<Integer> getReportIdforUsers(String username) {
        List<NurseReports> nurseReport = nurseReportRepository.findNurseReportbyAllowedUser(username);
        List<Integer> ReportIds = new ArrayList<>();
        for (NurseReports DReports : doctorReports) {
            ReportIds.add(DReports.getId());
        }
        return ReportIds;
    }*/
}
