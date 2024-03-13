package ACT.project.NeonetalMonitor.Controller;

import ACT.project.NeonetalMonitor.Entity.Admin;
import ACT.project.NeonetalMonitor.Entity.Appointment;
import ACT.project.NeonetalMonitor.Entity.DoctorReport;
import ACT.project.NeonetalMonitor.Service.AdminServiceImplementation;
import ACT.project.NeonetalMonitor.Service.DoctorReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/doctorReport")
public class DoctorReportController {
   private DoctorReportService doctorReportService;
   private AdminServiceImplementation adminServiceImplementation;

   @Autowired
   public DoctorReportController (DoctorReportService doctorReportService, AdminServiceImplementation adminServiceImplementation) {
       this.doctorReportService = doctorReportService;
       this.adminServiceImplementation = adminServiceImplementation;
   }
    @GetMapping("/report-form")
    public String showReportForm(Model model) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String email = principal.getName();
        Admin admin = adminServiceImplementation.findByEmail(email);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();

        String log = now.toString();

        admin.setLastseen(log);

        adminServiceImplementation.save(admin);

        model.addAttribute("name",admin.getFirstName());

        model.addAttribute("email",admin.getEmail());


        model.addAttribute("user",admin.getFirstName()+" "+admin.getLastName());

        DoctorReport obj = new DoctorReport();

        obj.setDoctorName(admin.getFirstName() + " " + admin.getLastName());

        obj.setReportDate(log);

        System.out.println(obj);
        model.addAttribute("doctorReport", obj);
        return "/doctor/submitReport";
    }
    @PostMapping("/save-report")
    public String saveReport(@ModelAttribute("doctorReport") DoctorReport report) {
        // You can add additional logic here, like setting the doctor name, report date, etc.
        doctorReportService.saveDoctorReport(report);
        return "redirect:/doctor/index";
    }
}
