package ACT.project.NeonetalMonitor.Entity;


import javax.persistence.*;

@Entity
@Table(name = "nurses_Report")
public class NurseReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Nursename")
    private String Nursename;

    @Column(name = "PatientName")
    private String patientName;

    @Column(name = "ShortHistory" , length = 10000)
    private String shortHistory;

    @Column(name = "LabTesttaken")
    private String labTesttaken;

    @Column(name = "Result")
    private double result;

    @Column(name = "Frequency")
    private int frequency;

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

    public String getNursename() {
        return Nursename;
    }

    public void setNursename(String nursename) {
        Nursename = nursename;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public String getLabTesttaken() {
        return labTesttaken;
    }

    public void setLabTesttaken(String labTesttaken) {
        this.labTesttaken = labTesttaken;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getShortHistory() {
        return shortHistory;
    }

    public void setShortHistory(String shortHistory) {
        this.shortHistory = shortHistory;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

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
