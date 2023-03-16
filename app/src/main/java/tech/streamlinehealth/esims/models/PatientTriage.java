package tech.streamlinehealth.esims.models;

public class PatientTriage {
    private String grade, checks, timestamp;

    public PatientTriage(String grade, String checks, String timestamp) {
        this.grade = grade;
        this.checks = checks;
        this.timestamp = timestamp;
    }

    public String getGrade() {
        return grade;
    }

    public String getChecks() {
        return checks;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
