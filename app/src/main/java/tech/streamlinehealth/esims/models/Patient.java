package tech.streamlinehealth.esims.models;

public class Patient {

    private String id;
    private String name;
    private String number;
    private String ward;
    private String timestamp;
    private String triage_grade;
    private String gender;
    private String date_of_birth;
    private String discharge_status;
    private String discharge_timestamp;
    private boolean is_deleted;

    public Patient(String id, String name, String number, String ward, String timestamp,
                   String triage_grade, String gender, String date_of_birth, String discharge_status,
                   String discharge_timestamp, boolean is_deleted) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.ward = ward;
        this.timestamp = timestamp;
        this.triage_grade = triage_grade;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.discharge_status = discharge_status;
        this.discharge_timestamp = discharge_timestamp;
        this.is_deleted = is_deleted;
    }

    public String getDischarge_timestamp() {
        return discharge_timestamp;
    }

    public void setDischarge_timestamp(String discharge_timestamp) {
        this.discharge_timestamp = discharge_timestamp;
    }

    public String getDischarge_status() {
        return discharge_status;
    }

    public void setDischarge_status(String discharge_status) {
        this.discharge_status = discharge_status;
    }

    public String getTriageGrade() {
        return triage_grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public boolean isDeleted() {
        return is_deleted;
    }
}
