package tech.streamlinehealth.esims.patients;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class AddPatientEpisodeActivity extends AppCompatActivity {

    String patient_id, ward_text;
    EditText ward;
    TextView add_patient_timestamp;
    final Calendar myCalendar = Calendar.getInstance();
    HelperFunctions helperFunctions;
    DataRouter router;
    long timestamp_long = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_episode);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);

        patient_id = new PreferenceManager(this).getPatientId();

        ward = findViewById(R.id.ward);
        ward.setFocusable(false);
        fetchwards();

        final DatePickerDialog.OnDateSetListener date_admission = (view12, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(AddPatientEpisodeActivity.this, (timePicker, hourOfDay, minutes) -> {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minutes);
                myCalendar.set(Calendar.SECOND, 0);

                String myFormat = "dd MMMM yyyy 'at' HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                add_patient_timestamp.setText(sdf.format(myCalendar.getTime()));
                timestamp_long = myCalendar.getTimeInMillis();
            }, 0, 0, true).show();
        };

        add_patient_timestamp = findViewById(R.id.add_patient_timestamp);
        add_patient_timestamp.setFocusable(false);
        add_patient_timestamp.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddPatientEpisodeActivity.this, date_admission, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        getPatientDetails();
    }

    public void fetchwards(){
        JsonArrayRequest wards_request = new JsonArrayRequest(new DataRouter(this).ip_address + "sims_clinical_data/view_wards",
                response -> {
                    JSONObject obj;
                    ArrayList<String> wards_array = new ArrayList<>();

                    for(int i = 0; i < response.length(); i++){

                        try {
                            obj = response.getJSONObject(i);
                            wards_array.add(obj.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ward.setOnClickListener(view -> {
                        String[] mStringArray = Arrays.copyOf(wards_array.toArray(), wards_array.size(), String[].class);

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Choose Action");

                        builder.setItems(mStringArray, (dialogInterface, i) -> {
                            ward_text = mStringArray[i];
                            ward.setText(mStringArray[i]);
                        });

                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    });
                }, error -> Toast.makeText(this, "Unable to fetch wards. Please reload to try again.", Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(wards_request);
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" + response.getString("date_of_birth") + "), " + response.getString("gender");
                        patient_info_text.setText(text);
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
        onBackPressed();
        return true;
    }

    public void createEpisode(View view){
        if(ward_text == null){
            helperFunctions.genericDialog("Please choose a valid ward");
            return;
        }

        new DataRouter(this).addPatientEpisode(ward_text, timestamp_long, "Self");
    }
}
