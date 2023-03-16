package tech.streamlinehealth.esims.patients;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddPatientActivity extends AppCompatActivity {

    EditText patient_pregnant, study_no, add_patient_timestamp, referred_from_name,
            ward, referred_from, weeks_pregnant, age_range;
    TextView weeks_pregnant_label, question_pregnant_label, referred_from_name_label;
    String patient_pregnant_text, ward_text, referred_from_text, age_range_text;
    final Calendar myCalendar = Calendar.getInstance();
    HelperFunctions helperFunctions;
    String gender_string;
    RadioGroup gender;
    long timestamp_long = System.currentTimeMillis();
    boolean referred_from_name_required = false;
    long start_time = 0;
    boolean is_timer_off = false; // this will track whether our timer has been turned off
    DataRouter dataRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        start_time = System.currentTimeMillis();

        dataRouter = new DataRouter(this);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helperFunctions = new HelperFunctions(this);

        study_no = findViewById(R.id.study_no);

        age_range = findViewById(R.id.age_range);
        age_range.setFocusable(false);
        age_range.setOnClickListener(view -> {
            String[] mStringArray = {"0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "80-90", "90+"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Age Group");

            builder.setItems(mStringArray, (dialogInterface, i) -> {
                age_range_text = mStringArray[i];
                age_range.setText(mStringArray[i]);
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        referred_from = findViewById(R.id.referred_from);
        referred_from_name = findViewById(R.id.referred_from_name);
        referred_from_name_label = findViewById(R.id.referred_from_name_label);
        referred_from.setFocusable(false);
        referred_from.setOnClickListener(view -> {
            String[] mStringArray = {"Self", "Hospital", "Clinic", "Other"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Option");

            builder.setItems(mStringArray, (dialogInterface, i) -> {
                referred_from_text = mStringArray[i];
                referred_from.setText(mStringArray[i]);

                if (i == 0){
                    referred_from_name.setVisibility(View.GONE);
                    referred_from_name_label.setVisibility(View.GONE);
                    referred_from_name_required = false;
                } else {
                    referred_from_name.setVisibility(View.VISIBLE);
                    referred_from_name_label.setVisibility(View.VISIBLE);
                    referred_from_name_required = true;
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        weeks_pregnant_label = findViewById(R.id.weeks_pregnant_label);
        weeks_pregnant = findViewById(R.id.weeks_pregnant);
        question_pregnant_label = findViewById(R.id.qn_is_patient_pregnant);
        patient_pregnant = findViewById(R.id.patient_pregnant);
        patient_pregnant.setFocusable(false);
        patient_pregnant.setOnClickListener(view -> {
            String[] mStringArray = {"Yes","No","Don't Know"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Option");

            builder.setItems(mStringArray, (dialogInterface, i) -> {
                patient_pregnant_text = mStringArray[i];
                patient_pregnant.setText(mStringArray[i]);
                if(i == 0){
                    weeks_pregnant.setVisibility(View.VISIBLE);
                    weeks_pregnant_label.setVisibility(View.VISIBLE);
                } else {
                    weeks_pregnant.setVisibility(View.GONE);
                    weeks_pregnant_label.setVisibility(View.GONE);
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        gender = findViewById(R.id.gender);
        gender.setOnCheckedChangeListener((radioGroup, checked_id) -> {
            if(checked_id == R.id.gender_female){
                gender_string = "Female";
                patient_pregnant.setVisibility(View.VISIBLE);
                question_pregnant_label.setVisibility(View.VISIBLE);
            } else if(checked_id == R.id.gender_male){
                gender_string = "Male";
                patient_pregnant.setVisibility(View.GONE);
                question_pregnant_label.setVisibility(View.GONE);
                weeks_pregnant.setVisibility(View.GONE);
            }
        });

        ward = findViewById(R.id.ward);
        ward.setFocusable(false);

        // fetch data for the occupations and wards drop down
        fetchRemoteData();

        final DatePickerDialog.OnDateSetListener date_admission = (view12, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(AddPatientActivity.this, (timePicker, hourOfDay, minutes) -> {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minutes);
                myCalendar.set(Calendar.SECOND, 0);

                String myFormat = "dd MMMM yyyy 'at' HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                add_patient_timestamp.setText(sdf.format(myCalendar.getTime()));
                timestamp_long = myCalendar.getTimeInMillis();

                myCalendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                myCalendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            }, 0, 0, true).show();
        };

        add_patient_timestamp = findViewById(R.id.add_patient_timestamp);
        add_patient_timestamp.setFocusable(false);
        add_patient_timestamp.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddPatientActivity.this, date_admission, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }

    @Override
    protected void onDestroy() {
        if (!is_timer_off && start_time != 0) {
            // here we just check if the timer has been turned off by the login button
            // to indicate that this is a bounce, we use zero instead of the current milli time
            is_timer_off = true;
            dataRouter.save_data_entry_time("login", String.valueOf(start_time), "0");
        }
        super.onDestroy();
    }

    public void fetchRemoteData(){
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void registerPatient(View view){
        String study_no_string = study_no.getText().toString();
        String age_range_string = age_range.getText().toString();
        String weeks_pregnant_string = weeks_pregnant.getText().toString();
        String referred_from_name_string = referred_from_name.getText().toString();

        if(study_no_string.isEmpty()){
            dataRouter.save_task_completion_rate("add_patient", "0", "Did not fill in study number");
            helperFunctions.genericDialog("Please fill in the patient study number");
            return;
        }

        if(age_range_string.isEmpty()){
            dataRouter.save_task_completion_rate("add_patient", "0", "Did not select the patient age range");
            helperFunctions.genericDialog("Please select the patient age range");
            return;
        }

        if (referred_from_name_string.isEmpty()) {
            if (referred_from_name_required) {
                dataRouter.save_task_completion_rate("add_patient", "0", "Did not fill in name of referral");
                helperFunctions.genericDialog("Please fill in the name of referral");
                return;
            } else {
                referred_from_name_string = "";
            }
        }

        if(weeks_pregnant_string.isEmpty()){
            weeks_pregnant_string = "0";
        }

        if (patient_pregnant_text == null) {
            patient_pregnant_text = "No";
        }

        if(ward_text == null){
            dataRouter.save_task_completion_rate("add_patient", "0", "Did not choose ward");
            ward_text = "N/A";
            return;
        }

        if(referred_from_text == null){
            referred_from_text = "Self";
        }

        is_timer_off = true;
        dataRouter.save_data_entry_time("add_patient", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));

        dataRouter.addPatient(study_no_string, age_range_string, gender_string, patient_pregnant_text, ward_text, referred_from_text, timestamp_long, weeks_pregnant_string, referred_from_name_string);
    }


    public void onClickCheckBox(View view) {
        /*boolean checked = ((CheckBox) view).isChecked();
        LinearLayout linearLayout = findViewById(R.id.other_patient_details);
        if(view.getId() == R.id.if_too_sick){
            if(checked){
                linearLayout.setVisibility(View.INVISIBLE);
            }else{
                linearLayout.setVisibility(View.VISIBLE);
            }
        }*/
    }
}
