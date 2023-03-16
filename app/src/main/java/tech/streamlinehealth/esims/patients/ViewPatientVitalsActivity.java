package tech.streamlinehealth.esims.patients;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.PatientVitalsAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.PatientVitals;
import tech.streamlinehealth.esims.patients.agewell.ChooseTestActivity;
import tech.streamlinehealth.esims.reports.PatientVitalsReportActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ViewPatientVitalsActivity extends AppCompatActivity {

    String patient_id, episode_id, avpu_text;
    DataRouter router;
    FloatingActionButton fab_add_vitals;
    HelperFunctions helperFunctions;
    PatientVitalsAdapter mAdapter;
    final Calendar myCalendar = Calendar.getInstance();
    RelativeLayout view_vitals_trends;
    PreferenceManager prefs;
    private FirebaseAnalytics firebaseAnalytics;
    long start_time = 0;
    boolean is_timer_off = false; // this will track whether our timer has been turned off
    EditText systolic_bp, diastolic_bp, heart_rate, resp_rate, temperature, oxygen_saturation, vitals_timestamp, avpu;
    View add_vitals_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_vitals);

        start_time = System.currentTimeMillis();


        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        prefs = new PreferenceManager(this);

        view_vitals_trends = findViewById(R.id.view_vitals_trends);

        view_vitals_trends.setOnClickListener(v -> {
            Intent action_intent = new Intent(ViewPatientVitalsActivity.this, PatientVitalsReportActivity.class);
            action_intent.putExtra("patient_id", patient_id);
            ViewPatientVitalsActivity.this.startActivity(action_intent);
        });

        patient_id = prefs.getPatientId();
        episode_id = prefs.getEpisodeId();

        getPatientDetails();

        getVitals();

        fab_add_vitals = findViewById(R.id.fab_add_vitals);

        fab_add_vitals.setOnClickListener(view -> {
            if (prefs.getEpisodeDischargeStatus().equals("0")) {
                addPatientVitals();
            } else {
                helperFunctions.genericDialog("Patient was discharged. To edit, please undo the discharge");
            }
        });

        LayoutInflater inflater = getLayoutInflater();
        add_vitals_view = inflater.inflate(R.layout.layout_add_vitals_dialog, null);

        systolic_bp = add_vitals_view.findViewById(R.id.systolic_bp);
        diastolic_bp = add_vitals_view.findViewById(R.id.diastolic_bp);
        heart_rate = add_vitals_view.findViewById(R.id.heart_rate);
        resp_rate = add_vitals_view.findViewById(R.id.resp_rate);
        temperature = add_vitals_view.findViewById(R.id.temperature);
        oxygen_saturation  = add_vitals_view.findViewById(R.id.oxygen_saturation);
        vitals_timestamp  = add_vitals_view.findViewById(R.id.vitals_timestamp);
        avpu  = add_vitals_view.findViewById(R.id.avpu);

        if (prefs.isAgewellActive()) {
            prefs.setAgewellActive(false);

            systolic_bp.setText(prefs.getAgewellSBP());
            diastolic_bp.setText(prefs.getAgewellDBP());
            heart_rate.setText(prefs.getAgewellPulse());
            temperature.setText(prefs.getAgewellTemp());
            oxygen_saturation.setText(prefs.getAgewellOxygen());

            Toast.makeText(this, "Vitals have been loaded", Toast.LENGTH_SHORT).show();

            addPatientVitals();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Add Vitals");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ViewPatientVitalsActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" + response.getString("date_of_birth") + "), " + response.getString("gender");
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

    public void getVitals(){
        String url = router.ip_address + "sims_patients/get_patient_vitals/" + patient_id + "/" + episode_id;

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {

                    ArrayList<PatientVitals> vitalsList = new ArrayList<>();

                    for (int i = response.length()-1; i >= 0; i--) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            vitalsList.add(new PatientVitals( jsonobject.getInt("id"), jsonobject.getInt("systolic_bp"), jsonobject.getInt("diastolic_bp"), jsonobject.getInt("heart_rate"), jsonobject.getInt("respiratory_rate"), jsonobject.getInt("temperature"), jsonobject.getInt("oxygen_saturation"), jsonobject.getString("avpu"), jsonobject.getString("timestamp"), (jsonobject.getString("first_name") + " " + jsonobject.getString("last_name"))));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mAdapter = new PatientVitalsAdapter(vitalsList);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewPatientVitalsActivity.this);

                    RecyclerView recyclerView = ViewPatientVitalsActivity.this.findViewById(R.id.recycler_view_patient_vitals);

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
                    //
                });

        Volley.newRequestQueue(this).add(request);
    }

    public void addPatientVitals(){
        final long[] vitals_timestamp_long = {System.currentTimeMillis()};

        final DatePickerDialog.OnDateSetListener date = (view12, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(ViewPatientVitalsActivity.this, (timePicker, hourOfDay, minutes) -> {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minutes);
                myCalendar.set(Calendar.SECOND, 0);

                String myFormat = "dd MMMM yyyy 'at' HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                vitals_timestamp.setText(sdf.format(myCalendar.getTime()));
                vitals_timestamp_long[0] = myCalendar.getTimeInMillis();
            }, 0, 0, false).show();
        };

        vitals_timestamp.setFocusable(false);
        vitals_timestamp.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ViewPatientVitalsActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        avpu.setFocusable(false);
        avpu.setOnClickListener(view1 -> {
            String[] mStringArray = {"Alert","Responds to Voice","Responds to Pain", "Unresponsive"};

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("Choose Option");

            builder.setItems(mStringArray, (dialogInterface, i) -> {
                avpu_text = mStringArray[i];
                avpu.setText(mStringArray[i]);
            });

            // create and show the alert dialog
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setView(add_vitals_view);

        builder.setCancelable(false)
                .setNeutralButton("Cancel", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Measure With Vitals Device",
                        (dialog, id) -> {
                            startActivity(new Intent(this, ChooseTestActivity.class));
                        })
                .setNegativeButton("Save",
                        (dialog, id) -> {
                            if(systolic_bp.getText().toString().isEmpty()){
                                router.save_task_completion_rate("add_vitals", "0", "Did not fill in the Systolic BP");
                                helperFunctions.genericDialog("Please fill in the Systolic BP");
                                return;
                            }

                            if(diastolic_bp.getText().toString().isEmpty()){
                                router.save_task_completion_rate("add_vitals", "0", "Did not fill in the Diastolic BP");
                                helperFunctions.genericDialog("Please fill in the Diastolic BP");
                                return;
                            }

                            if(heart_rate.getText().toString().isEmpty()){
                                router.save_task_completion_rate("add_vitals", "0", "Did not fill in the heart rate");
                                helperFunctions.genericDialog("Please fill in the heart rate");
                                return;
                            }

                            if(resp_rate.getText().toString().isEmpty()){
                                router.save_task_completion_rate("add_vitals", "0", "Did not fill in the respiration rate");
                                helperFunctions.genericDialog("Please fill in the respiration rate");
                                return;
                            }

                            if(temperature.getText().toString().isEmpty()){
                                router.save_task_completion_rate("add_vitals", "0", "Did not fill in the temperature");
                                helperFunctions.genericDialog("Please fill in the temperature");
                                return;
                            }

                            if(oxygen_saturation.getText().toString().isEmpty()){
                                router.save_task_completion_rate("add_vitals", "0", "Did not fill in the oxygen saturation");
                                helperFunctions.genericDialog("Please fill in the oxygen saturation");
                                return;
                            }


                            int systolic_bp_int = Integer.parseInt(systolic_bp.getText().toString());
                            int diastolic_bp_int = Integer.parseInt(diastolic_bp.getText().toString());
                            int heart_rate_int = Integer.parseInt(heart_rate.getText().toString());
                            int resp_rate_int = Integer.parseInt(resp_rate.getText().toString());
                            double temperature_int = Double.parseDouble(temperature.getText().toString());
                            int oxygen_saturation_int = Integer.parseInt(oxygen_saturation.getText().toString());

                            if (avpu_text == null) {
                                avpu_text = "None";
                            }

                            is_timer_off = true;
                            router.save_data_entry_time("add_vitals", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                            router.addVitals(patient_id, episode_id, systolic_bp_int, diastolic_bp_int, heart_rate_int, resp_rate_int, temperature_int, oxygen_saturation_int, avpu_text, vitals_timestamp_long[0]);

                            // refresh the layout
                            ViewPatientVitalsActivity.this.getVitals();
                        });

        final Dialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (!is_timer_off && start_time != 0) {
            // here we just check if the timer has been turned off by the login button
            // to indicate that this is a bounce, we use zero instead of the current milli time
            is_timer_off = true;
            router.save_data_entry_time("add_vitals", String.valueOf(start_time), "0");
        }
        super.onDestroy();
    }
}
