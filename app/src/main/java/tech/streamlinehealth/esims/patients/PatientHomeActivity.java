package tech.streamlinehealth.esims.patients;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tech.streamlinehealth.esims.HomeActivity;
import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.fragments.TriageViewFragment;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.patients.triage.PatientTriageHomeActivity;
import tech.streamlinehealth.esims.reports.PatientVitalsReportActivity;

public class PatientHomeActivity extends AppCompatActivity {

    String patient_id, episode_id,
            gender, triage_id, actions_required_string, actions_status_string;
    TextView patient_home_triage, patient_home_vitals, patient_home_actions;
    DataRouter router;
    TextView patient_prompt, patient_prompt_complete_actions;
    HelperFunctions helperFunctions;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patient_id = new PreferenceManager(this).getPatientId();
        episode_id = new PreferenceManager(this).getEpisodeId();

        patient_prompt = findViewById(R.id.patient_prompt);
        patient_prompt_complete_actions = findViewById(R.id.patient_prompt_complete_actions);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);
        preferenceManager = new PreferenceManager(this);

        patient_home_triage = findViewById(R.id.patient_home_triage);
        patient_home_vitals = findViewById(R.id.patient_home_vitals);
        patient_home_actions = findViewById(R.id.patient_home_actions);

        if (preferenceManager.getEpisodeDischargeStatus().equals("1")) {
            patient_home_triage.setVisibility(View.GONE);
            patient_home_actions.setVisibility(View.GONE);
        }

        patient_home_triage.setOnClickListener(v -> startActivity(new Intent(PatientHomeActivity.this, PatientTriageHomeActivity.class)));

        patient_home_vitals.setOnClickListener(v -> startActivity(new Intent(PatientHomeActivity.this, ViewPatientVitalsActivity.class)));

        patient_home_actions.setOnClickListener(v -> startActivity(new Intent(PatientHomeActivity.this, ViewPatientActionsActivity.class)));

        getPatientDetails();
        getPatientVitals();
        getPatientEpisodeDetails();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.patient_home_fragment, new TriageViewFragment()).commit();

        patient_prompt.setOnClickListener(v -> {
            Intent intent = new Intent(PatientHomeActivity.this, PatientTriageHomeActivity.class);
            intent.putExtra("isRequestFromPatientHome", true);
            startActivity(intent);
        });

        patient_prompt_complete_actions.setOnClickListener(v -> {
            Intent intent = new Intent(PatientHomeActivity.this, CompletedActionsTimelineActivity.class);
            intent.putExtra("actions_required_string", actions_required_string);
            intent.putExtra("actions_status_string", actions_status_string);
            startActivity(intent);
        });
    }

    public void viewVitalsGraph(View view) {
        Intent action_intent = new Intent(PatientHomeActivity.this, PatientVitalsReportActivity.class);
        action_intent.putExtra("patient_id", patient_id);
        startActivity(action_intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_home, menu);

        if (preferenceManager.getEpisodeDischargeStatus().equals("1")) {
            menu.findItem(R.id.action_assign_patient).setVisible(false);
            menu.findItem(R.id.action_transfer_patient).setVisible(false);
            menu.findItem(R.id.action_discharge_patient).setVisible(false);
            //menu.findItem(R.id.action_discharge_patient).setTitle("Undo Discharge");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_dashboard:
                startActivity(new Intent(PatientHomeActivity.this, HomeActivity.class));
                break;
            case R.id.action_assign_patient:
                helperFunctions.genericProgressBar("Fetching patient information...");

                // check if patient has already been assigned a doctor
                String network_address = router.ip_address + "sims_patients/is_doctor_assigned/" + episode_id;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                        response -> {
                            helperFunctions.stopProgressBar();

                            try {
                                if (response.getString("status").equals("0")){
                                    // patient has no doctor assigned so go ahead and assign a new one
                                    startActivity(new Intent(PatientHomeActivity.this, AssignPatientActivity.class));
                                } else {
                                    // patient has an assigned doctor
                                    AlertDialog.Builder alert = new AlertDialog.Builder(PatientHomeActivity.this);
                                    String id = response.getString("id");

                                    String message = "Patient has been assigned to " + response.getString("name") + ". Do you want to reassign them?";

                                    alert.setMessage(message).setPositiveButton("Yes", (dialogInterface, i) -> {
                                        // reassignment
                                        Intent intent = new Intent(PatientHomeActivity.this, ReassignActivity.class);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                    }).setNegativeButton("Cancel", null).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again later.");
                });

                Volley.newRequestQueue(this).add(request);
                break;
            case R.id.action_discharge_patient:
                AlertDialog.Builder alert = new AlertDialog.Builder(PatientHomeActivity.this);

                alert.setMessage("Are you sure you want to discharge this patient?").setPositiveButton("Yes", (dialogInterface, i) -> {

                    AlertDialog.Builder builder_discharge = new AlertDialog.Builder(PatientHomeActivity.this);
                    builder_discharge.setTitle("Choose outcome");

                    builder_discharge.setItems(helperFunctions.outcomes, (dialogInterface_discharge, position_discharge) -> {
                        helperFunctions.genericProgressBar("Discharging patient...");

                        String url = router.ip_address + "sims_patients/discharge_patient";

                        StringRequest discharge_request = new StringRequest(Request.Method.POST, url, response -> {
                            helperFunctions.stopProgressBar();
                            if(!response.equals("0")){
                                AlertDialog.Builder alert12 = new AlertDialog.Builder(PatientHomeActivity.this);

                                alert12.setMessage("Patient has been discharged").setPositiveButton("Okay", (dialogInterface12, i1) -> {
                                    finish();
                                    startActivity(new Intent(PatientHomeActivity.this, ViewPatientsActivity.class));
                                }).setNegativeButton("Cancel", null).show();
                            } else {
                                helperFunctions.genericDialog("Patient discharge failed. Please try again");
                            }
                        }, error -> {
                            // This code is executed if there is an error.
                            helperFunctions.stopProgressBar();
                            helperFunctions.genericDialog("Patient discharge failed. Please try again");
                        }) {
                            protected Map<String, String> getParams() {
                                Map<String, String> data = new HashMap<>();
                                data.put("patient_id", patient_id);
                                data.put("episode_id", episode_id);
                                data.put("timestamp", String.valueOf(System.currentTimeMillis()));
                                data.put("in_charge", preferenceManager.getUserId());
                                data.put("outcome_id", String.valueOf(position_discharge + 1));
                                return data;
                            }
                        };

                        Volley.newRequestQueue(PatientHomeActivity.this).add(discharge_request);
                    });

                    // create and show the alert dialog
                    AlertDialog dialog_discharge = builder_discharge.create();
                    dialog_discharge.show();
                }).setNegativeButton("Cancel", null).show();
                break;
            case R.id.action_deactivate_patient:
                AlertDialog.Builder alert1 = new AlertDialog.Builder(PatientHomeActivity.this);

                alert1.setMessage("Are you sure you want to deactivate this patient?").setPositiveButton("Yes", (dialogInterface, i) -> {
                    helperFunctions.genericProgressBar("Deactivating patient...");

                    // check if patient has already been assigned a doctor
                    String network_address1 = router.ip_address + "sims_patients/deactivate_patient/" + patient_id;

                    StringRequest request1 = new StringRequest(Request.Method.GET, network_address1,
                            response -> {
                                helperFunctions.stopProgressBar();

                                if (!response.equals("0")){
                                    // patient has an assigned doctor
                                    AlertDialog.Builder alert22 = new AlertDialog.Builder(PatientHomeActivity.this);

                                    alert22.setMessage("Patient has been deactivated").setPositiveButton("Okay", (dialogInterface13, i12) -> {
                                        finish();
                                        startActivity(new Intent(PatientHomeActivity.this, ViewPatientsActivity.class));
                                    }).setNegativeButton("Cancel", null).show();
                                }
                            }, error -> {
                        helperFunctions.stopProgressBar();
                        helperFunctions.genericDialog("Something went wrong. Please try again later.");
                    });

                    Volley.newRequestQueue(PatientHomeActivity.this).add(request1);
                }).setNegativeButton("Cancel", null).show();
                break;
            case R.id.action_details_patient:
                Intent patient_details_intent = new Intent(PatientHomeActivity.this, ViewPatientDetailsActivity.class);
                startActivity(patient_details_intent);
                break;
            case R.id.action_transfer_patient:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(PatientHomeActivity.this);

                alert2.setMessage("Are you sure you want to transfer this patient?").setPositiveButton("Yes", (dialogInterface, i) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PatientHomeActivity.this);
                    builder.setTitle("Choose ward");

                    builder.setItems(helperFunctions.wards, (dialogInterface1, position) -> {
                        String url = router.ip_address + "sims_patients/reassign_patient_ward";

                        helperFunctions.genericProgressBar("Ward transfer in progress...");

                        StringRequest ward_transfer_request = new StringRequest(Request.Method.POST, url, response -> {
                            helperFunctions.stopProgressBar();
                            if(response.equals("1")){
                                AlertDialog.Builder alert3 = new AlertDialog.Builder(PatientHomeActivity.this);

                                alert3.setMessage("Patient ward transfer complete").setPositiveButton("Okay", (dialogInterface2, i13) -> {
                                    //
                                }).show();
                            } else {
                                helperFunctions.genericDialog("Patient ward transfer failed. Please try again");
                            }
                        }, error -> {
                            // This code is executed if there is an error.
                            helperFunctions.stopProgressBar();
                            helperFunctions.genericDialog("Patient ward transfer failed. Please try again");
                        }) {
                            protected Map<String, String> getParams() {
                                Map<String, String> data = new HashMap<>();
                                data.put("patient_id", patient_id);
                                data.put("episode_id", preferenceManager.getEpisodeId());
                                data.put("ward", helperFunctions.wards[position]);
                                data.put("created_by", preferenceManager.getUserId());
                                return data;
                            }
                        };

                        Volley.newRequestQueue(PatientHomeActivity.this).add(ward_transfer_request);
                    });

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }).setNegativeButton("Cancel", null).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {

                        gender = response.getString("gender");
                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" +  response.getString("gender") + ") " + response.getString("date_of_birth");
                        patient_info_text.setText(text);

                        // change profile for pregnancy
                        if (response.getString("patient_pregnant").equals("Yes")) {
                            ImageView patient_profile_picture = findViewById(R.id.patient_profile_picture);
                            patient_profile_picture.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pregnant));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void getPatientEpisodeDetails(){
        String network_address = router.ip_address + "sims_patients/get_episode_details/" + episode_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        if ("1".equals(response.getString("triage_grade"))) {
                            getPatientTriage();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void getPatientTriage(){
        String network_address = router.ip_address + "sims_patients/get_patient_latest_triage/" + patient_id + "/" + episode_id + "/1";

        @SuppressLint("SetTextI18n") JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        triage_id = response.getString("id");
                        actions_required_string = response.getString("actions_required");
                        actions_status_string = response.getString("actions_status");
                        String[] action_status_array = response.getString("actions_status").split(",");

                        if (response.getString("available").equals("1")) {
                            int number_of_zeros = 0;

                            for (String value: action_status_array){
                                if (value.equals("0")) {
                                    number_of_zeros++;
                                }
                            }

                            if (action_status_array.length > 0 && !action_status_array[0].equals("") && preferenceManager.getEpisodeDischargeStatus().equals("0")){
                                if(number_of_zeros > 0) {
                                    patient_prompt.setText(number_of_zeros + " action(s) required");
                                } else {
                                    patient_prompt.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_view_green));
                                    patient_prompt.setText("All actions from complete");
                                }

                                int number_of_completed_actions = action_status_array.length - number_of_zeros;

                                if (number_of_completed_actions > 0) {
                                    patient_prompt_complete_actions.setText(number_of_completed_actions + " actions complete");
                                    patient_prompt_complete_actions.setVisibility(View.VISIBLE);
                                }

                                patient_prompt.setVisibility(View.VISIBLE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    public void getPatientVitals(){
        String url = router.ip_address + "sims_patients/get_patient_vitals/" + patient_id + "/" + episode_id;

        @SuppressLint("SetTextI18n") JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    TextView blood_pressure = findViewById(R.id.blood_pressure);
                    TextView heart_rate = findViewById(R.id.heart_rate);
                    TextView resp_rate = findViewById(R.id.resp_rate);
                    TextView temp = findViewById(R.id.temp);
                    TextView spo2 = findViewById(R.id.spo2);
                    TextView avpu = findViewById(R.id.avpu);
                    LinearLayout blood_pressure_container = findViewById(R.id.blood_pressure_container);
                    LinearLayout heart_rate_container = findViewById(R.id.heart_rate_container);
                    LinearLayout resp_rate_container = findViewById(R.id.resp_rate_container);
                    LinearLayout temp_container = findViewById(R.id.temp_container);
                    LinearLayout spo2_container = findViewById(R.id.spo2_container);
                    LinearLayout avpu_container = findViewById(R.id.avpu_container);

                    if (response.length() > 0){
                        JSONObject jsonobject;
                        try {
                            // fetch the latest results
                            jsonobject = response.getJSONObject(response.length() - 1);

                            // set the normal ranges
                            int systolic_bp_int = jsonobject.getInt("systolic_bp");
                            if (systolic_bp_int > 110 && systolic_bp_int < 220) {
                                blood_pressure_container.setBackgroundResource(R.color.green);
                            } else if(systolic_bp_int > 100 && systolic_bp_int < 111) {
                                blood_pressure_container.setBackgroundResource(R.color.yellow);
                            } else if(systolic_bp_int > 90 && systolic_bp_int < 101) {
                                blood_pressure_container.setBackgroundResource(R.color.orange);
                            } else {
                                blood_pressure_container.setBackgroundResource(R.color.red);
                            }

                            int heart_rate_int = jsonobject.getInt("heart_rate");
                            if (heart_rate_int > 50 && heart_rate_int < 91) {
                                heart_rate_container.setBackgroundResource(R.color.green);
                            } else if(heart_rate_int > 40 && heart_rate_int < 51) {
                                heart_rate_container.setBackgroundResource(R.color.yellow);
                            } else if(heart_rate_int > 90 && heart_rate_int < 111) {
                                heart_rate_container.setBackgroundResource(R.color.yellow);
                            } else if(heart_rate_int > 110 && heart_rate_int < 131) {
                                heart_rate_container.setBackgroundResource(R.color.orange);
                            } else {
                                heart_rate_container.setBackgroundResource(R.color.red);
                            }

                            int resp_rate_int = jsonobject.getInt("respiratory_rate");
                            if (resp_rate_int > 11 && resp_rate_int < 21) {
                                resp_rate_container.setBackgroundResource(R.color.green);
                            } else if(resp_rate_int > 8 && resp_rate_int < 12) {
                                resp_rate_container.setBackgroundResource(R.color.yellow);
                            } else if(resp_rate_int > 20 && resp_rate_int < 25) {
                                resp_rate_container.setBackgroundResource(R.color.orange);
                            } else {
                                resp_rate_container.setBackgroundResource(R.color.red);
                            }

                            double temp_int = jsonobject.getDouble("temperature");
                            if (temp_int > 36.0 && temp_int < 38.1) {
                                temp_container.setBackgroundResource(R.color.green);
                            } else if(temp_int > 35.0 && temp_int < 36.1) {
                                temp_container.setBackgroundResource(R.color.yellow);
                            } else if(temp_int > 38.0 && temp_int < 39.1) {
                                temp_container.setBackgroundResource(R.color.orange);
                            } else {
                                temp_container.setBackgroundResource(R.color.red);
                            }

                            if (jsonobject.getInt("oxygen_saturation") > 89) {
                                spo2_container.setBackgroundResource(R.color.green);
                            } else {
                                spo2_container.setBackgroundResource(R.color.red);
                            }

                            if (jsonobject.getString("avpu").equals("Alert")) {
                                avpu_container.setBackgroundResource(R.color.green);
                            } else {
                                avpu_container.setBackgroundResource(R.color.red);
                            }

                            // set the values
                            blood_pressure.setText(jsonobject.getInt("systolic_bp") + "/" + jsonobject.getInt("diastolic_bp"));
                            heart_rate.setText(String.valueOf(jsonobject.getInt("heart_rate")));
                            resp_rate.setText(String.valueOf(jsonobject.getInt("respiratory_rate")));
                            temp.setText(String.valueOf(jsonobject.getInt("temperature")));
                            spo2.setText(jsonobject.getInt("oxygen_saturation") + " %");
                            avpu.setText(jsonobject.getString("avpu"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        blood_pressure.setText("N/A");
                        heart_rate.setText("N/A");
                        resp_rate.setText("N/A");
                        temp.setText("N/A");
                        spo2.setText("N/A");
                        avpu.setText("N/A");
                    }

                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

