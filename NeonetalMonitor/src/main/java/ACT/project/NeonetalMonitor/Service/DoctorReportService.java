package ACT.project.NeonetalMonitor.Service;

import ACT.project.NeonetalMonitor.Entity.DoctorReport;
import ACT.project.NeonetalMonitor.Entity.Video;
import ACT.project.NeonetalMonitor.Repository.DoctorReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("doctorReportService")
public class DoctorReportService {
    DoctorReportRepository doctorReport;

    @Autowired
    DoctorReportService(DoctorReportRepository doctorReport) {
        this.doctorReport = doctorReport;
    }

    public void saveDoctorReport(DoctorReport report) {
        doctorReport.save(report);
    }

    public List<DoctorReport> getAlldoctorReports() {
        return doctorReport.findAll();
    }

    public List<Integer> getReportIdforUsers(String username) {
        List<DoctorReport> doctorReports = doctorReport.findDoctorReportByAllowedUser(username);
        List<Integer> ReportIds = new ArrayList<>();
        for (DoctorReport DReports : doctorReports) {
            ReportIds.add(DReports.getId());
        }
        return ReportIds;
    }


    public List<DoctorReport> findDoctorReportByAllowedUser(String email) {
        return doctorReport.findDoctorReportByAllowedUser(email);
    }
}
