package tech.streamlinehealth.esims.models;

public class PatientEpisode {
    private final String id, ward, admission_diagnosis, triage_grade, admission_timestamp, discharge_status;

    public PatientEpisode(String id, String ward, String admission_diagnosis, String triage_grade, String admission_timestamp, String discharge_status) {
        this.id = id;
        this.ward = ward;
        this.admission_diagnosis = admission_diagnosis;
        this.triage_grade = triage_grade;
        this.admission_timestamp = admission_timestamp;
        this.discharge_status = discharge_status;
    }

    public String getId() {
        return id;
    }

    public String getWard() {
        return ward;
    }

    public String getAdmissionDiagnosis() {
        return admission_diagnosis;
    }

    public String getTriageGrade() {
        return triage_grade;
    }

    public String getAdmissionTimestamp() {
        return admission_timestamp;
    }

    public String getDischargeStatus(){
        return discharge_status;
    }
}
