package tech.streamlinehealth.esims.models;

public class PatientVitals {
    private int id;
    private int systolic_bp;
    private int diastolic_bp;
    private int heart_rate;
    private int resp_rate;
    private int temp;
    private int spo;
    private String avpu;
    private String timestamp;
    private String created_by;

    public PatientVitals( int id, int systolic_bp, int diastolic_bp, int heart_rate, int resp_rate, int temp, int spo, String avpu, String timestamp, String created_by) {
        this.id = id;
        this.systolic_bp = systolic_bp;
        this.diastolic_bp = diastolic_bp;
        this.heart_rate = heart_rate;
        this.resp_rate = resp_rate;
        this.temp = temp;
        this.spo = spo;
        this.avpu = avpu;
        this.timestamp = timestamp;
        this.created_by = created_by;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystolicBp() {
        return String.valueOf(systolic_bp);
    }

    public void setSystolicBp(int systolic_bp) {
        this.systolic_bp = systolic_bp;
    }

    public String getDiastolicBp() {
        return String.valueOf(diastolic_bp);
    }

    public void setDiastolicBp(int diastolic_bp) {
        this.diastolic_bp = diastolic_bp;
    }

    public String getHeartRate() {
        return String.valueOf(heart_rate);
    }

    public void setHeartRate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getRespRate() {
        return String.valueOf(resp_rate);
    }

    public void setRespRate(int resp_rate) {
        this.resp_rate = resp_rate;
    }

    public String getTemp() {
        return String.valueOf(temp);
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getSpo() {
        return String.valueOf(spo);
    }

    public void setSpo(int spo) {
        this.spo = spo;
    }

    public String getAvpu() {
        return avpu;
    }

    public void setAvpu(String avpu) {
        this.avpu = avpu;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreatedBy() {
        return created_by;
    }

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }
}
