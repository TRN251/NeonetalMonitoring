package ACT.project.NeonetalMonitor.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "doctorsOrderSheet")
public class DoctorsOrderSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PatientsName")
    private String patientsName;

    @Column(name = "DoctorsName")
    private String doctorsName;

    @Column(name = "MedicationSubscribed")
    private String medicationSubscribed;

    @Column(name = "Dosage")
    private double Dosage;

    @Column(name = "RecomendedVitalSignMeas")
    private String vital;

    @Column(name = "Frequency")
    private int frequency;

/*    @Column(name = "allowed_user")
    @NotEmpty(message = "Please provide User that view this video")
    private String allowedUser;*/

    @Column(name = "report_date")
    private String reportDate;

    @Column(name = "message", length = 10000)
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorsName() {
        return doctorsName;
    }

    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName;
    }

    public String getMedicationSubscribed() {
        return medicationSubscribed;
    }

    public void setMedicationSubscribed(String medicationSubscribed) {
        medicationSubscribed = medicationSubscribed;
    }

    public double getDosage() {
        return Dosage;
    }

    public void setDosage(double dosage) {
        Dosage = dosage;
    }

    public String getPatientsName() {
        return patientsName;
    }

    public void setPatientsName(String patientsName) {
        patientsName = patientsName;
    }

    public String getVital() {
        return vital;
    }

    public void setVital(String vital) {
        this.vital = vital;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

   /* public String getAllowedUser() {
        return allowedUser;
    }

    public void setAllowedUser(String allowedUser) {
        this.allowedUser = allowedUser;
    }*/

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
