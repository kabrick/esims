package tech.streamlinehealth.esims.helpers;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import tech.streamlinehealth.esims.HomeActivity;
import tech.streamlinehealth.esims.auth.LoginActivity;
import tech.streamlinehealth.esims.auth.ResetPassword;
import tech.streamlinehealth.esims.patients.PatientEpisodesActivity;
import tech.streamlinehealth.esims.patients.PatientHomeActivity;
import tech.streamlinehealth.esims.patients.ViewPatientsActivity;
import tech.streamlinehealth.esims.patients.triage.PatientTriageHomeActivity;
import tech.streamlinehealth.esims.users.ViewUsersActivity;

public class DataRouter {

    private final Context context;
    private final RequestQueue requestQueue;
    private int number_of_volley_requests = 0;
    public String ip_address = "";
    private final HelperFunctions helperFunctions;
    private final PreferenceManager preferenceManager;

    public DataRouter(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        helperFunctions = new HelperFunctions(context);
        preferenceManager = new PreferenceManager(context);
    }

    public void userSignIn(final String username, final String password, String start_time, String end_time) {
        helperFunctions.genericProgressBar("Signing you in...");

        String network_address = ip_address + "sims_patients/sign_in/" + username + "/" + password;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    helperFunctions.stopProgressBar();

                    try {
                        if (response.getString("status").equals("-1")) {
                            helperFunctions.genericDialog("Username does not exist in system");
                        } else if (response.getString("status").equals("0")) {
                            helperFunctions.genericDialog("Password does not match username");
                        } else {
                            save_task_completion_rate("login", "1", "success");
                            // set the prefs to logged in and capture the id
                            preferenceManager.setSignedIn(true);
                            preferenceManager.setUserId(response.getString("id"));
                            preferenceManager.setUserName(response.getString("name"));
                            preferenceManager.setUserAdmin(response.getString("is_admin").equals("1"));
                            context.startActivity(new Intent(context, HomeActivity.class));
                            save_data_entry_time("login", start_time, end_time);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            helperFunctions.stopProgressBar();

            if (error instanceof TimeoutError || error instanceof NetworkError) {
                helperFunctions.genericDialog("Something went wrong. Please make sure you are connected to a working internet connection.");
            } else {
                helperFunctions.genericDialog("Unable to sign you in. Please try again later");
            }
        });

        requestQueue.add(request);
    }

    public void addPatient(final String study_no, final String age_range, final String gender, final String patient_pregnant,
                           final String ward, final String referred_from, final long timestamp_long, final String weeks_pregnant, final String referred_from_name){

        helperFunctions.genericProgressBar("Adding new patient record...");

        String url = ip_address + "sims_patients/add_patients";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")) {
                helperFunctions.genericDialog("Patient record not created. Please try again");
            } else {
                save_task_completion_rate("add_patient", "1", "success");

                // display notification for new patient
                String[] results_string_array = response.split(",");
                String new_patient_number = "Patient record for " + study_no + " has been created";
                helperFunctions.displayNotification(context, "New Patient Record", new_patient_number);

                // register the patient as the current patient in the system
                preferenceManager.setPatientId(results_string_array[1]);
                preferenceManager.setEpisodeId(results_string_array[2]);

                Intent intent = new Intent(context, PatientTriageHomeActivity.class);
                context.startActivity(intent);
            }
        }, error -> {
            // This code is executed if there is an error
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Patient record not created. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("study_no", study_no);
                data.put("age_range", age_range);
                data.put("gender", gender);
                data.put("ward", ward);
                data.put("referred_from", referred_from);
                data.put("patient_pregnant", patient_pregnant);
                data.put("referred_from_name", referred_from_name);
                data.put("weeks_pregnant", weeks_pregnant);
                data.put("timestamp", Long.toString(timestamp_long));
                data.put("user_id", new PreferenceManager(context).getUserId());
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void editPatient(final String patient_names, final String date_of_birth,
                            final String ward, final String patient_id, final String gender, final String patient_pregnant) {

        helperFunctions.genericProgressBar("Editing patient records...");

        String url = ip_address + "sims_patients/edit_patients";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();
            Intent intent = new Intent(context, ViewPatientsActivity.class);
            context.startActivity(intent);
        }, error -> {
            // This code is executed if there is an error
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Patient record not edited. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("patient_names", patient_names);
                data.put("date_of_birth", date_of_birth);
                data.put("gender", gender);
                data.put("ward", ward);
                data.put("patient_pregnant", patient_pregnant);
                data.put("id", patient_id);
                data.put("episode_id", preferenceManager.getEpisodeId());
                return data;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    public void addVitals(final String patient_id, final String episode_id, final int sbp, final int dbp, final int hr, final int rr,
                          final double temp, final int spo2, final String avpu, final long timestamp_long) {

        String url = ip_address + "sims_patients/add_vitals";

        helperFunctions.genericProgressBar("Adding new patient vitals...");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            save_task_completion_rate("add_vitals", "1", "success");
            helperFunctions.stopProgressBar();
            Intent intent = new Intent(context, PatientHomeActivity.class);
            context.startActivity(intent);
        }, error -> {
            // This code is executed if there is an error.
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Patient vitals not added. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("patient_id", patient_id);
                data.put("episode_id", episode_id);
                data.put("user_id", preferenceManager.getUserId());
                data.put("sbp", String.valueOf(sbp));
                data.put("dbp", String.valueOf(dbp));
                data.put("hr", String.valueOf(hr));
                data.put("rr", String.valueOf(rr));
                data.put("temp", String.valueOf(temp));
                data.put("spo2", String.valueOf(spo2));
                data.put("avpu", avpu);
                data.put("timestamp", Long.toString(timestamp_long));
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void updateVitals(final String sbp, final String dbp, final String hr, final String rr,
                             final String temp, final String spo2, final String avpu, final String is_complete) {

        String url = ip_address + "sims_patients/update_vitals";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            //
        }, error -> {
            // This code is executed if there is an error.
            Toast.makeText(context, "Unable to save vitals. Please check your connection", Toast.LENGTH_SHORT).show();
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("patient_id", preferenceManager.getPatientId());
                data.put("episode_id", preferenceManager.getEpisodeId());
                data.put("user_id", preferenceManager.getUserId());
                data.put("sbp", sbp);
                data.put("dbp", dbp);
                data.put("hr", hr);
                data.put("rr", rr);
                data.put("temp", temp);
                data.put("spo2", spo2);
                data.put("avpu", avpu);
                data.put("is_complete", is_complete);
                data.put("timestamp", Long.toString(System.currentTimeMillis()));
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void editActions(final int id, final String actions, boolean redirect_to_patient_home) {
        String url = ip_address + "sims_patients/update_actions";

        if (redirect_to_patient_home) {
            helperFunctions.genericProgressBar("Applying patient actions taken...");
        }

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (redirect_to_patient_home) {
                Intent intent = new Intent(context, PatientHomeActivity.class);
                helperFunctions.stopProgressBar();
                context.startActivity(intent);
            }
        }, error -> {
            // This code is executed if there is an error.
            if (redirect_to_patient_home) {
                helperFunctions.stopProgressBar();
                helperFunctions.genericDialog("Patient actions not saved. Please try again");
            } else {
                Toast.makeText(context, "Unable to save at this moment. Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", String.valueOf(id));
                data.put("action_status", String.valueOf(actions));
                return data;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    public void addTriageDetails(final String patient_id, final String episode_id, final String grade, final String checks,
                                 final String checks_status, final String emergency_actions, final String emergency_actions_status, final String emergency_actions_triggered,
                                 final long timestamp_long, final String trauma_actions, final String trauma_actions_status, boolean redirect_to_patient_home) {
        String url = ip_address + "sims_patients/add_triage_details";

        if (redirect_to_patient_home) {
            Toast.makeText(context, "Saving triage details...", Toast.LENGTH_SHORT).show();
        }

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            number_of_volley_requests--;

            // check if the requests are done
            if (number_of_volley_requests == 0) {
                if (redirect_to_patient_home) {
                    Toast.makeText(context, "Triage details saved", Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context, PatientHomeActivity.class));
                }
            }
        }, error -> {
            // This code is executed if there is an error.
            number_of_volley_requests--;
            Toast.makeText(context, "Triage details not saved. Please check your connection", Toast.LENGTH_LONG).show();
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("checks", checks);
                data.put("checks_status", checks_status);
                data.put("actions_required", emergency_actions);
                data.put("actions_status", emergency_actions_status);
                data.put("actions_triggered", emergency_actions_triggered);
                data.put("grade", grade);
                data.put("patient_id", patient_id);
                data.put("episode_id", episode_id);
                data.put("user_id", preferenceManager.getUserId());
                data.put("timestamp", Long.toString(timestamp_long));
                data.put("trauma_actions_required", trauma_actions);
                data.put("trauma_actions_status", trauma_actions_status);
                return data;
            }
        };

        number_of_volley_requests++;
        requestQueue.add(MyStringRequest);
    }

    public void addUser(final String first_name, final String last_name, final String phone,
                        final String email, final String username, final String password, final String pin, final String is_admin){
        String url = ip_address + "sims_patients/add_user";

        helperFunctions.genericProgressBar("Creating new user...");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            switch (response) {
                case "0":
                    // username exists
                    helperFunctions.genericDialog("Username used already exists in the system");
                    break;
                case "1":
                    // email exists
                    helperFunctions.genericDialog("Email used already exists in the system");
                    break;
                case "2":
                    // success
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    alert.setMessage("User has been added to the system.").setPositiveButton("Okay", (dialogInterface, i) -> {
                        //context.startActivity(new Intent(context, ViewUsersActivity.class));
                        preferenceManager.setSignedIn(false);
                        preferenceManager.setUserId("");
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }).show();
                    break;
                case "3":
                    // fail
                    helperFunctions.genericDialog("User details not saved. Please try again");
                    break;
            }
        }, error -> {
            // This code is executed if there is an error.
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("User details not saved. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("first_name", first_name);
                data.put("last_name", last_name);
                data.put("phone", phone);
                data.put("email", email);
                data.put("username", username);
                data.put("password", password);
                data.put("pin", pin);
                data.put("user_id", preferenceManager.getUserId());
                data.put("is_admin", is_admin);
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void editUser(final String first_name, final String last_name, final String phone,
                        final String email, final String username, final String pin, final String id, final String is_admin){
        String url = ip_address + "sims_patients/edit_user";

        helperFunctions.genericProgressBar("Editing user details...");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            switch (response) {
                case "2":
                    // success
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    alert.setMessage("User details edited.").setPositiveButton("Okay", (dialogInterface, i) -> context.startActivity(new Intent(context, ViewUsersActivity.class))).show();
                    break;
                case "3":
                    // fail
                    helperFunctions.genericDialog("User details not saved. Please try again");
                    break;
            }
        }, error -> {
            // This code is executed if there is an error.
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("User details not saved. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("first_name", first_name);
                data.put("last_name", last_name);
                data.put("phone", phone);
                data.put("email", email);
                data.put("username", username);
                data.put("pin", pin);
                data.put("id", id);
                data.put("user_id", preferenceManager.getUserId());
                data.put("is_admin", is_admin);
                return data;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    public void addPatientEpisode(final String ward, final long timestamp, final String referred_from) {
        helperFunctions.genericProgressBar("Adding new patient episode...");

        String url = ip_address + "sims_patients/add_episode";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();
            Intent intent = new Intent(context, PatientEpisodesActivity.class);
            context.startActivity(intent);
        }, error -> {
            // This code is executed if there is an error
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Patient episode not created. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", preferenceManager.getUserId());
                data.put("patient_id", preferenceManager.getPatientId());
                data.put("ward", ward);
                data.put("referred_from", referred_from);
                data.put("timestamp", Long.toString(timestamp));
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void forgotPassword(final String email, final String pin){

        String url = ip_address + "sims_users/verify_user/" + email + "/" + pin;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {

                    try {
                        if (response.getString("valid_user").equals("0")) {
                            helperFunctions.genericDialog("Account does not exist in system");
                        } else if (response.getString("id").equals("0")) {
                            helperFunctions.genericDialog("User with this email does not exist in system");
                        } else {
                            // set the prefs to logged in and capture the id
                            preferenceManager.setUserId(response.getString("id"));
                            preferenceManager.setUserName(response.getString("username"));
                            Intent intent = new Intent(context, ResetPassword.class);
                            context.startActivity(intent);
                            System.out.println("response for forgot password is"+ response.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            helperFunctions.stopProgressBar();

            if (error instanceof TimeoutError || error instanceof NetworkError) {
                helperFunctions.genericDialog("Something went wrong. Please make sure you are connected to a working internet connection.");
            } else {
                helperFunctions.genericDialog("Unable to reset your password. Please try again later");
            }
        });

        requestQueue.add(request);
    }

    public void resetPassword(final String id, final String password){

        String url = ip_address + "sims_users/reset_password";

        helperFunctions.genericProgressBar("Resetting password...");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();
            try {
                if (response.equals("0")) {
                    helperFunctions.genericDialog("Account does not exist in system");
                } else if (response.equals("1")) {
                    helperFunctions.genericDialog("Password reset successful");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    helperFunctions.genericDialog("Password reset was unsuccessful");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            // This code is executed if there is an error.
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("There was a problem in resetting the password . Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", id);
                data.put("password", password);
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void changePassword(final String id, final String new_password, final String old_password) {

        String url = ip_address + "sims_users/update_password";

        helperFunctions.genericProgressBar("Changing password...");

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();
            try {
                if (response.equals("0")) {
                    helperFunctions.genericDialog("Account does not exist in system");
                } else if (response.equals("1")) {
                    helperFunctions.genericDialog("Current password is incorrect");
                } else if (response.equals("2")) {
                    helperFunctions.genericDialog("Password updated successfully");
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                } else {
                    helperFunctions.genericDialog("Password update not successful");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            // This code is executed if there is an error.
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("There was a problem in changing the password . Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", id);
                data.put("new_password", new_password);
                data.put("current_password", old_password);
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void save_task_completion_rate(final String category, final String is_success, final String comments){

        String url = ip_address + "sims_usage_reports/save_task_completion_rate";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {

            if (response.equals("0")) {
                // not success
            } else {
                // success
            }
        }, error -> {
            // This code is executed if there is an error
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("category", category);
                data.put("is_success", is_success);
                data.put("comments", comments);
                data.put("user_id", new PreferenceManager(context).getUserId());
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }

    public void save_data_entry_time(final String category, final String start_time, final String end_time){
        String url = ip_address + "sims_usage_reports/save_data_entry_time";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("0")) {
                // not success
            } else {
                // success
            }
        }, error -> {
            // This code is executed if there is an error
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("category", category);
                data.put("start_time", start_time);
                data.put("end_time", end_time);
                data.put("user_id", new PreferenceManager(context).getUserId());
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(MyStringRequest);
    }


}
