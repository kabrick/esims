package tech.streamlinehealth.esims.patients.triage;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class PatientTriageDetailsActivity extends AppCompatActivity {

    String grade, checks;
    TextView emergency_signs1, emergency_signs2, emergency_signs3, no_airway, emergency_signs4, emergency_signs5,
            emergency_signs6, emergency_signs7, no_circulation, emergency_signs8, emergency_signs9,
            emergency_signs10, no_disability, emergency_signs11, emergency_signs12, emergency_signs13, emergency_signs14,
            emergency_signs15, emergency_signs16, emergency_signs17, emergency_signs18, no_evaluation;
    TextView priority_signs0, priority_signs1, priority_signs2, priority_signs3, priority_signs4, priority_signs5,
            priority_signs6, priority_signs7, priority_signs8, priority_signs9,
            priority_signs10, priority_signs11, priority_signs12, priority_signs13, priority_signs14,
            priority_signs15;
    DataRouter router;
    LinearLayout emergency_signs_details, priority_signs_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_triage_details);

        router = new DataRouter(this);

        getPatientDetails();

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            grade = extras.getString("grade", "1");
            checks = extras.getString("checks", "1");
        } else {
            onBackPressed();
        }

        emergency_signs_details = findViewById(R.id.emergency_signs_details);
        no_airway = findViewById(R.id.no_airway);
        no_circulation = findViewById(R.id.no_circulation);
        no_disability = findViewById(R.id.no_disability);
        no_evaluation = findViewById(R.id.no_evaluation);
        emergency_signs1 = findViewById(R.id.emergency_signs1);
        emergency_signs2 = findViewById(R.id.emergency_signs2);
        emergency_signs3 = findViewById(R.id.emergency_signs3);
        emergency_signs4 = findViewById(R.id.emergency_signs4);
        emergency_signs5 = findViewById(R.id.emergency_signs5);
        emergency_signs6 = findViewById(R.id.emergency_signs6);
        emergency_signs7 = findViewById(R.id.emergency_signs7);
        emergency_signs8 = findViewById(R.id.emergency_signs8);
        emergency_signs9 = findViewById(R.id.emergency_signs9);
        emergency_signs10 = findViewById(R.id.emergency_signs10);
        emergency_signs11 = findViewById(R.id.emergency_signs11);
        emergency_signs12 = findViewById(R.id.emergency_signs12);
        emergency_signs13 = findViewById(R.id.emergency_signs13);
        emergency_signs14 = findViewById(R.id.emergency_signs14);
        emergency_signs15 = findViewById(R.id.emergency_signs15);
        emergency_signs16 = findViewById(R.id.emergency_signs16);
        emergency_signs17 = findViewById(R.id.emergency_signs17);
        emergency_signs18 = findViewById(R.id.emergency_signs18);

        priority_signs_details = findViewById(R.id.priority_signs_details);
        priority_signs0 = findViewById(R.id.priority_signs0);
        priority_signs1 = findViewById(R.id.priority_signs1);
        priority_signs2 = findViewById(R.id.priority_signs2);
        priority_signs3 = findViewById(R.id.priority_signs3);
        priority_signs4 = findViewById(R.id.priority_signs4);
        priority_signs5 = findViewById(R.id.priority_signs5);
        priority_signs6 = findViewById(R.id.priority_signs6);
        priority_signs7 = findViewById(R.id.priority_signs7);
        priority_signs8 = findViewById(R.id.priority_signs8);
        priority_signs9 = findViewById(R.id.priority_signs9);
        priority_signs10 = findViewById(R.id.priority_signs10);
        priority_signs11 = findViewById(R.id.priority_signs11);
        priority_signs12 = findViewById(R.id.priority_signs12);
        priority_signs13 = findViewById(R.id.priority_signs13);
        priority_signs14 = findViewById(R.id.priority_signs14);
        priority_signs15 = findViewById(R.id.priority_signs15);

        // turn the checks into an array
        String[] checks_array = checks.split(",");

        if (grade.equals("1")){
            // emergency signs
            emergency_signs_details.setVisibility(View.VISIBLE);

            if (checks_array.length > 0){
                // variable to check if the categorized signs are available
                boolean available = false;

                if (checks_array[0].equals("1")){
                    emergency_signs1.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[1].equals("1")){
                    emergency_signs2.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[2].equals("1")){
                    emergency_signs3.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (!available){
                    no_airway.setVisibility(View.VISIBLE);
                }

                available = false;

                if (checks_array[3].equals("1")){
                    emergency_signs4.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[4].equals("1")){
                    emergency_signs5.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[5].equals("1")){
                    emergency_signs6.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[6].equals("1")){
                    emergency_signs7.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (!available){
                    no_circulation.setVisibility(View.VISIBLE);
                }

                available = false;

                if (checks_array[7].equals("1")){
                    emergency_signs8.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[8].equals("1")){
                    emergency_signs9.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[9].equals("1")){
                    emergency_signs10.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (!available){
                    no_disability.setVisibility(View.VISIBLE);
                }

                available = false;

                if (checks_array[10].equals("1")){
                    emergency_signs11.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[11].equals("1")){
                    emergency_signs12.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[12].equals("1")){
                    emergency_signs13.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[13].equals("1")){
                    emergency_signs14.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[14].equals("1")){
                    emergency_signs15.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[15].equals("1")){
                    emergency_signs16.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[16].equals("1")){
                    emergency_signs17.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (checks_array[17].equals("1")){
                    emergency_signs18.setVisibility(View.VISIBLE);
                    available = true;
                }

                if (!available){
                    no_evaluation.setVisibility(View.VISIBLE);
                }
            } else {
                no_disability.setVisibility(View.VISIBLE);
                no_airway.setVisibility(View.VISIBLE);
                no_circulation.setVisibility(View.VISIBLE);
                no_evaluation.setVisibility(View.VISIBLE);
            }
        } else if (grade.equals("2")){
            // priority signs
            priority_signs_details.setVisibility(View.VISIBLE);

            if (checks_array.length > 0){
                if (checks_array[0].equals("1")){
                    priority_signs1.setVisibility(View.VISIBLE);
                }

                if (checks_array[1].equals("1")){
                    priority_signs2.setVisibility(View.VISIBLE);
                }

                if (checks_array[2].equals("1")){
                    priority_signs3.setVisibility(View.VISIBLE);
                }

                if (checks_array[3].equals("1")){
                    priority_signs4.setVisibility(View.VISIBLE);
                }

                if (checks_array[4].equals("1")){
                    priority_signs5.setVisibility(View.VISIBLE);
                }

                if (checks_array[5].equals("1")){
                    priority_signs6.setVisibility(View.VISIBLE);
                }

                if (checks_array[6].equals("1")){
                    priority_signs7.setVisibility(View.VISIBLE);
                }

                if (checks_array[7].equals("1")){
                    priority_signs8.setVisibility(View.VISIBLE);
                }

                if (checks_array[8].equals("1")){
                    priority_signs9.setVisibility(View.VISIBLE);
                }

                if (checks_array[9].equals("1")){
                    priority_signs10.setVisibility(View.VISIBLE);
                }

                if (checks_array[10].equals("1")){
                    priority_signs11.setVisibility(View.VISIBLE);
                }

                if (checks_array[11].equals("1")){
                    priority_signs12.setVisibility(View.VISIBLE);
                }

                if (checks_array[12].equals("1")){
                    priority_signs13.setVisibility(View.VISIBLE);
                }

                if (checks_array[13].equals("1")){
                    priority_signs14.setVisibility(View.VISIBLE);
                }

                if (checks_array[14].equals("1")){
                    priority_signs15.setVisibility(View.VISIBLE);
                }
            } else {
                priority_signs0.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);
        String patient_id = new PreferenceManager(this).getPatientId();

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {

                        HelperFunctions helperFunctions = new HelperFunctions(PatientTriageDetailsActivity.this);

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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
