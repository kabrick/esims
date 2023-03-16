package tech.streamlinehealth.esims.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "sims_preferences";
    private static final String IS_SIGNED_IN = "logged_in";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_IS_ADMIN = "is_admin";
    private static final String PATIENT_ID = "patient_id";
    private static final String EPISODE_ID = "episode_id";
    private static final String EPISODE_DISCHARGE_STATUS = "episode_discharge_status";
    private static final String NOTIFICATION_COUNTER = "notification_counter";
    private static final String QUICK_CHECK_REVIEW = "quick_check_review";
    private static final String AGEWELL_SBP = "agewell_sbp";
    private static final String AGEWELL_DBP = "agewell_dbp";
    private static final String AGEWELL_PULSE = "agewell_pulse";
    private static final String AGEWELL_OXYGEN = "agewell_oxygen";
    private static final String AGEWELL_TEMP = "agewell_temp";
    private static final String IS_AGEWELL_ACTIVE = "is_agewell_active";

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    /**
     * Class constructor to set privacy mode and create new editor
     *
     * @param context application context
     *                <p>
     *                suppress for adding commit to created prefs editor
     */
    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context) {
        int PRIVATE_MODE = 0;
        this.pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public boolean isSignedIn() {
        return this.pref.getBoolean(IS_SIGNED_IN, false);
    }

    public void setSignedIn(boolean isSignedIn) {
        this.editor.putBoolean(IS_SIGNED_IN, isSignedIn);
        this.editor.commit();
    }

    public String getUserId() {
        return this.pref.getString(USER_ID, "");
    }

    public void setUserId(String id) {
        this.editor.putString(USER_ID, id);
        this.editor.commit();
    }

    public String getUserName() {
        return this.pref.getString(USER_NAME, "");
    }

    public void setUserName(String name) {
        this.editor.putString(USER_NAME, name);
        this.editor.commit();
    }

    public boolean isUserAdmin() {
        return this.pref.getBoolean(USER_IS_ADMIN, false);
    }

    public void setUserAdmin(boolean admin) {
        this.editor.putBoolean(USER_IS_ADMIN, admin);
        this.editor.commit();
    }

    public String getPatientId() {
        return this.pref.getString(PATIENT_ID, "");
    }

    public void setPatientId(String id) {
        this.editor.putString(PATIENT_ID, id);
        this.editor.commit();
    }

    public String getEpisodeId() {
        return this.pref.getString(EPISODE_ID, "");
    }

    public void setEpisodeId(String id) {
        this.editor.putString(EPISODE_ID, id);
        this.editor.commit();
    }

    int getNotificationCounter() {
        return this.pref.getInt(NOTIFICATION_COUNTER, 0);
    }

    void setNotificationCounter(int counter) {
        this.editor.putInt(NOTIFICATION_COUNTER, counter);
        this.editor.commit();
    }

    public boolean isQuickCheckReview(){
        return this.pref.getBoolean(QUICK_CHECK_REVIEW, false);
    }

    public void setQuickCheckReview(boolean isQuickCheckReview){
        this.editor.putBoolean(QUICK_CHECK_REVIEW, isQuickCheckReview);
        this.editor.commit();
    }

    public String getEpisodeDischargeStatus(){
        return this.pref.getString(EPISODE_DISCHARGE_STATUS, "0");
    }

    public void setEpisodeDischargeStatus(String status){
        this.editor.putString(EPISODE_DISCHARGE_STATUS, status);
        this.editor.commit();
    }

    public String getAgewellSBP() {
        return this.pref.getString(AGEWELL_SBP, "");
    }

    public void setAgewellSBP(String sbp) {
        this.editor.putString(AGEWELL_SBP, sbp);
        this.editor.commit();
    }

    public String getAgewellDBP() {
        return this.pref.getString(AGEWELL_DBP, "");
    }

    public void setAgewellDBP(String dbp) {
        this.editor.putString(AGEWELL_DBP, dbp);
        this.editor.commit();
    }

    public String getAgewellPulse() {
        return this.pref.getString(AGEWELL_PULSE, "");
    }

    public void setAgewellPulse(String pulse) {
        this.editor.putString(AGEWELL_PULSE, pulse);
        this.editor.commit();
    }

    public String getAgewellOxygen() {
        return this.pref.getString(AGEWELL_OXYGEN, "");
    }

    public void setAgewellOxygen(String oxygen) {
        this.editor.putString(AGEWELL_OXYGEN, oxygen);
        this.editor.commit();
    }

    public String getAgewellTemp() {
        return this.pref.getString(AGEWELL_TEMP, "");
    }

    public void setAgewellTemp(double temp) {
        this.editor.putString(AGEWELL_TEMP, String.valueOf(temp));
        this.editor.commit();
    }

    public boolean isAgewellActive() {
        return this.pref.getBoolean(IS_AGEWELL_ACTIVE, false);
    }

    public void setAgewellActive(boolean isAgewellActive) {
        this.editor.putBoolean(IS_AGEWELL_ACTIVE, isAgewellActive);
        this.editor.commit();
    }

}
