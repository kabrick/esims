package tech.streamlinehealth.esims.patients;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.PatientsAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.Patient;

public class ViewPatientsActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    HelperFunctions helperFunctions;
    PatientsAdapter mAdapter;
    ArrayList<Patient> patients;
    DataRouter router;
    RecyclerView recyclerView;
    TextView no_active_patients;
    SearchView searchView;
    private FirebaseAnalytics firebaseAnalytics;
    long start_time = 0;
    boolean is_timer_off, viewing_deleted_patients = false; // this will track whether our timer has been turned off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);

        start_time = System.currentTimeMillis();

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferenceManager = new PreferenceManager(this);
        helperFunctions = new HelperFunctions(this);

        no_active_patients = findViewById(R.id.no_active_patients);

        patients = new ArrayList<>();

        mAdapter = new PatientsAdapter(ViewPatientsActivity.this, patients);

        searchView = findViewById(R.id.search_active_patients);

        searchView.setQueryHint("Search patients...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                is_timer_off = true;
                router.save_data_entry_time("search_patient_details", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        recyclerView = findViewById(R.id.recycler_active_patients);

        router = new DataRouter(this);

        getPatients("sims_patients/view_active_patients/0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Search and view Patients");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ViewPatientsActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    public void getPatients(String route){
        helperFunctions.genericProgressBar("Fetching patients...");

        String url = router.ip_address + route;

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {

                    helperFunctions.stopProgressBar();

                    if(response.length() < 1){
                        searchView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        no_active_patients.setVisibility(View.VISIBLE);
                    } else {
                        searchView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        no_active_patients.setVisibility(View.GONE);
                    }

                    patients.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            patients.add(new Patient(jsonobject.getString("id"), jsonobject.getString("name"), jsonobject.getString("number"), jsonobject.getString("ward"),
                                    jsonobject.getString("admission_timestamp"), jsonobject.getString("triage_grade"), jsonobject.getString("gender"), jsonobject.getString("date_of_birth"),
                                    jsonobject.getString("discharge_status"), jsonobject.getString("discharge_timestamp"), viewing_deleted_patients));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // insert patients into list
                    mAdapter.notifyDataSetChanged();

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewPatientsActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again.");
                });

        Volley.newRequestQueue(this).add(request);
        is_timer_off = true;
        router.save_data_entry_time("search_patient_details", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_patients, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.action_deleted_patients) {
            viewing_deleted_patients = false;
        }

        switch (item.getItemId()){
            case R.id.action_active_patients:
                getPatients("sims_patients/view_active_patients/0");
                break;
            case R.id.action_discharged_patients:
                getPatients("sims_patients/view_discharged_patients");
                break;
            case R.id.action_deleted_patients:
                viewing_deleted_patients = true;
                getPatients("sims_patients/view_deactivated_patients");
                break;
            case R.id.action_choose_ward:
                helperFunctions.genericProgressBar("Fetching wards listing...");
                String url = router.ip_address + "sims_clinical_data/view_wards";

                JsonArrayRequest request = new JsonArrayRequest(url,
                        response -> {
                            helperFunctions.stopProgressBar();
                            ArrayList<String> wards_array = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    wards_array.add(response.getJSONObject(i).getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            String[] mStringArray = Arrays.copyOf(wards_array.toArray(), wards_array.size(), String[].class);

                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Choose Ward");

                            builder.setItems(mStringArray, (dialogInterface, i) -> {
                                getPatients("sims_patients/view_active_patients/" + mStringArray[i]);
                            });

                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again later");
                });

                Volley.newRequestQueue(this).add(request);
                break;
            case R.id.action_choose_severity:
                String[] mStringArray1 = {"Triage Not Performed", "Emergency Signs Found","Priority Signs Found","Non-Priority Patient"};

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Choose severity");

                builder1.setItems(mStringArray1, (dialogInterface, i) -> {
                    if (i == 0){
                        getPatients("sims_patients/view_active_patients_severity/0");
                    } else if (i == 1){
                        getPatients("sims_patients/view_active_patients_severity/1");
                    } else if (i == 2){
                        getPatients("sims_patients/view_active_patients_severity/2");
                    } else if (i == 3){
                        getPatients("sims_patients/view_active_patients_severity/3");
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            case R.id.action_choose_sex:
                String[] mStringArray2 = {"Female", "Male"};

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Choose gender");

                builder2.setItems(mStringArray2, (dialogInterface, i) -> {
                    if (i == 0){
                        getPatients("sims_patients/view_patients_gender/0");
                    } else if (i == 1){
                        getPatients("sims_patients/view_patients_gender/1");
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case R.id.action_choose_doctor:
                helperFunctions.genericProgressBar("Fetching active users...");
                String url1 = router.ip_address + "sims_patients/view_active_users";

                JsonArrayRequest request1 = new JsonArrayRequest(url1,
                        response -> {
                            helperFunctions.stopProgressBar();
                            ArrayList<String> user_array = new ArrayList<>();
                            ArrayList<String> user_id_array = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonobject = response.getJSONObject(i);
                                    user_array.add(jsonobject.getString("first_name") + " " + jsonobject.getString("last_name"));
                                    user_id_array.add(jsonobject.getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            String[] mStringArray3 = Arrays.copyOf(user_array.toArray(), user_array.size(), String[].class);

                            AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                            builder3.setTitle("Choose Doctor");

                            builder3.setItems(mStringArray3, (dialogInterface, i) -> {
                                getPatients("sims_patients/view_patients_user/" + Integer.parseInt(user_id_array.get(i)));
                            });

                            // create and show the alert dialog
                            AlertDialog dialog3 = builder3.create();
                            dialog3.show();
                        }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again later");
                });

                Volley.newRequestQueue(this).add(request1);
                break;
            default:
                //
                break;
        }

        return super.onOptionsItemSelected(item);
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
            router.save_data_entry_time("search_patient_details", String.valueOf(start_time), "0");
        }else{
            is_timer_off = true;
            router.save_data_entry_time("search_patient_details", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
        }
        super.onDestroy();
    }
}

