package tech.streamlinehealth.esims.patients;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tech.streamlinehealth.esims.R;

import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class EditPatientVitalsActivity extends AppCompatActivity {
    EditText ward_name;

    EditText systolic_bp, diastolic_bp, heart_rate, resp_rate, temperature, oxygen_saturation, avpu, timestamp;
    String patient_id, episode_id;
    String id, sys_bp, dias_bp, hrt_rate, rep_rate, temp, oxy_sat, avp, timeStamp;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vitals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceManager = new PreferenceManager(this);
        patient_id = new PreferenceManager(this).getPatientId();
        episode_id = new PreferenceManager(this).getEpisodeId();

        ward_name = findViewById(R.id.ward_name);

        systolic_bp = findViewById(R.id.systolic_bp);
        diastolic_bp = findViewById(R.id.diastolic_bp);
        heart_rate = findViewById(R.id.heart_rate);
        resp_rate = findViewById(R.id.resp_rate);
        temperature = findViewById(R.id.temperature);
        oxygen_saturation  = findViewById(R.id.oxygen_saturation);
        timestamp = findViewById(R.id.timestamp);
        avpu = findViewById(R.id.avpu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            id = extras.getString("id", "0");
            sys_bp = extras.getString("systolic_bp", "");
            dias_bp = extras.getString("diastolic_bp", "");
            hrt_rate = extras.getString("heart_rate", "");
            rep_rate = extras.getString("resp_rate", "");
            temp = extras.getString("temperature", "");
            oxy_sat = extras.getString("oxygen_sat", "");
            timeStamp = extras.getString("timestamp", "");
            avp = extras.getString("avpu", "");
            systolic_bp.setText(sys_bp);
            diastolic_bp.setText(dias_bp);
            heart_rate.setText(hrt_rate);
            resp_rate.setText(rep_rate);
            temperature.setText(temp);
            oxygen_saturation.setText(oxy_sat);
            avpu.setText(avp);
            timestamp.setText(timeStamp);
        }
        else {
            onBackPressed();
        }
    }

    public void edit_vitals(View view){
        HelperFunctions helperFunctions = new HelperFunctions(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String sys_bp = systolic_bp.getText().toString();
        String dias_bp = diastolic_bp.getText().toString();
        String hrt_bp = heart_rate.getText().toString();
        String resp_bp = resp_rate.getText().toString();
        String temp = temperature.getText().toString();
        String oxy_sat = oxygen_saturation.getText().toString();
        String avpu_ = avpu.getText().toString();
        String time_stamp = timestamp.getText().toString();

        if(sys_bp.isEmpty()){
            helperFunctions.genericDialog("Please fill in the systolic blood pressure");
            return;
        }

        if(dias_bp.isEmpty()){
            helperFunctions.genericDialog("Please fill in the diastolic blood pressure");
            return;
        }

        if(hrt_bp.isEmpty()){
            helperFunctions.genericDialog("Please fill in the heart rate");
            return;
        }
        if(resp_bp.isEmpty()){
            helperFunctions.genericDialog("Please fill in the respiratoty rate");
            return;
        }
        if(temp.isEmpty()){
            helperFunctions.genericDialog("Please fill in the temperature ");
            return;
        }
        if(oxy_sat.isEmpty()){
            helperFunctions.genericDialog("Please fill in the Oxygen Saturation");
            return;
        }
        if(avpu_.isEmpty()){
            helperFunctions.genericDialog("Please fill in the Avpu");
            return;
        }

        if(time_stamp.isEmpty()){
            helperFunctions.genericDialog("Please fill in the timestamp");
            return;
        }

        helperFunctions.genericProgressBar("Editing vitals...");

        String url = new DataRouter(this).ip_address + "sims_patients/edit_vitals";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();
            if (response.equals("0")){
                helperFunctions.genericDialog("Vitals not edited. Please try again");
            } else {
                Toast.makeText(this, "Vitals edited successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, ViewPatientVitalsActivity.class));
            }
        }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Vitals not edited. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", id);
                data.put("sbp", sys_bp);
                data.put("dbp", dias_bp);
                data.put("hr", hrt_bp);
                data.put("rr", resp_bp);
                data.put("temp", temp);
                data.put("spo2", oxy_sat);
                data.put("avpu", avpu_);
                data.put("timestamp", time_stamp);
                data.put("user_id", preferenceManager.getUserId());
                return data;
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(MyStringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
