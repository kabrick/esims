package tech.streamlinehealth.esims.models;

public class CompletedAction {

    private String action;
    private String timestamp;

    public CompletedAction(String action, String timestamp) {
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
