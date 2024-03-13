package ACT.project.NeonetalMonitor.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "doctorreport")
public class DoctorReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "report_date")
    private String reportDate;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "report_content", length = 10000)
    private String reportContent;

    @Column(name = "allowed_user")
    @NotEmpty(message = "Please provide User that view this video")
    private String allowedUser;

    public int getId() {
        return id;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getReportContent() {
        return reportContent;
    }

    public String getAllowedUser() {
        return allowedUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public void setAllowedUser(String allowedUser) {
        this.allowedUser = allowedUser;
    }


}
