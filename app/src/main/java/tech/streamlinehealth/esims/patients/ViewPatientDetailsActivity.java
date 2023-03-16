package tech.streamlinehealth.esims.patients;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class ViewPatientDetailsActivity extends AppCompatActivity {

    DataRouter router;
    HelperFunctions helperFunctions;
    Button edit_btn, save_changes, cancel_changes;
    TextView patient_names, date_of_birth, gender, ward, timestamp;
    EditText patient_names_edit, date_of_birth_edit;
    MaterialSpinner patient_pregnant_edit, ward_edit, gender_edit;
    private final int DIAGNOSIS_REQUEST_CODE = 1;
    final Calendar myCalendar = Calendar.getInstance();
    LinearLayout details_linear, edit_linear, btn;
    String patient_id;
    List<String> ward_array, patient_pregnant_array, gender_array;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_details);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);

        patient_id = new PreferenceManager(this).getPatientId();

        details_linear = findViewById(R.id.details_linear);
        edit_linear = findViewById(R.id.edit_linear);
        patient_names = findViewById(R.id.patient_names);
        date_of_birth = findViewById(R.id.date_of_birth);
        gender = findViewById(R.id.gender);
        ward = findViewById(R.id.ward);
        timestamp = findViewById(R.id.timestamp);
        edit_btn = findViewById(R.id.edit_btn);
        save_changes = findViewById(R.id.register_patient);
        cancel_changes = findViewById(R.id.cancel);
        btn = findViewById(R.id.btn);

        patient_names_edit = findViewById(R.id.patient_names_edit);

        ward_edit = findViewById(R.id.ward_edit);
        ward_array = new ArrayList<>(Arrays.asList("Children", "Medical Upper", "Unknown"));
        ward_edit.setItems(ward_array);

        patient_pregnant_edit = findViewById(R.id.patient_pregnant_edit);
        patient_pregnant_array = new ArrayList<>(Arrays.asList("Yes", "No", "Don't Know"));
        patient_pregnant_edit.setItems(patient_pregnant_array);

        gender_edit = findViewById(R.id.gender_edit);
        gender_array = new ArrayList<>(Arrays.asList("Female", "Male"));
        gender_edit.setItems(gender_array);
        gender_edit.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0) {
                patient_pregnant_edit.setVisibility(View.VISIBLE);
            }
        });

        date_of_birth_edit = findViewById(R.id.date_of_birth_edit);
        date_of_birth_edit.setFocusable(false);

        getPatientDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "View Patient Details");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ViewPatientDetailsActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }


    public void getPatientDetails() {
        helperFunctions.genericProgressBar("Fetching patient demographic...");
        String network_address = router.ip_address + "sims_patients/get_patient_details_full/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    helperFunctions.stopProgressBar();

                    try {
                        patient_names.setText(response.getString("name"));
                        date_of_birth.setText(response.getString("date_of_birth"));
                        gender.setText(response.getString("gender"));
                        ward.setText(response.getString("ward"));
                        date_of_birth_edit.setText(response.getString("date_of_birth"));
                        patient_names_edit.setText(response.getString("name"));
                        if(!response.getString("ward").equals("N/A")){
                            ward_edit.setSelectedIndex(ward_array.indexOf(response.getString("ward")));
                        }else{
                            ward_edit.setText("N/A");
                        }
                        if(!response.getString("patient_pregnant").equals("N/A")){
                            patient_pregnant_edit.setSelectedIndex(patient_pregnant_array.indexOf(response.getString("patient_pregnant")));
                        }else{
                            patient_pregnant_edit.setText("N/A");
                        }
                        if(!response.getString("gender").equals("N/A")){
                            gender_edit.setSelectedIndex(gender_array.indexOf(response.getString("gender")));
                        }else{
                            gender_edit.setText("N/A");
                        }
                        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                                Long.parseLong(response.getString("admission_timestamp")),
                                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

                        timestamp.setText(timeAgo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> helperFunctions.stopProgressBar());

        Volley.newRequestQueue(this).add(request);
    }

    public void editPatient(View view) {
        details_linear.setVisibility(View.GONE);
        edit_btn.setVisibility(View.GONE);
        edit_linear.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);

    }

    public void completeEdit(View view) {
        String patient_names_string = patient_names_edit.getText().toString();
        String date_of_birth_string = date_of_birth_edit.getText().toString();
        String ward_string = ward_edit.getText().toString();
        String patient_pregnant_string = patient_pregnant_edit.getText().toString();

        if (patient_names_string.isEmpty()) {
            helperFunctions.genericDialog("Please fill in the patient names");
            return;
        }

        if (ward_string.equals("Ward")) {
            helperFunctions.genericDialog("Please choose a valid ward");
            return;
        }

        if (patient_pregnant_string.equals("Is patient pregnant")) {
            patient_pregnant_string = "No";
        }

        DataRouter dataRouter = new DataRouter(this);

        dataRouter.editPatient(patient_names_string, date_of_birth_string,
                ward_string, patient_id, gender_edit.getText().toString(),
                patient_pregnant_string);
    }

    public void cancelEdit(View view) {
        details_linear.setVisibility(View.VISIBLE);
        edit_btn.setVisibility(View.VISIBLE);
        edit_linear.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
    }

    public void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        date_of_birth_edit.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
