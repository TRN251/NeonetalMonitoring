package ACT.project.NeonetalMonitor.Service;

import ACT.project.NeonetalMonitor.Entity.DoctorReport;
import ACT.project.NeonetalMonitor.Entity.DoctorsOrderSheet;
import ACT.project.NeonetalMonitor.Repository.DoctorOrderSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DoctorOrderSheetService {
    DoctorOrderSheetRepository drOrderSheet;

    @Autowired
    public DoctorOrderSheetService(DoctorOrderSheetRepository doctorOrderSheetRepository)
    {
        drOrderSheet = doctorOrderSheetRepository;
    }

    public void saveDoctorReport(DoctorsOrderSheet report) {
        drOrderSheet.save(report);
    }

    public List<DoctorsOrderSheet> getAlldoctorReports() {
        return drOrderSheet.findAll();
    }





}
