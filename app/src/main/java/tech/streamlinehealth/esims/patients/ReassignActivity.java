package tech.streamlinehealth.esims.patients;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.UsersAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.Users;

public class ReassignActivity extends AppCompatActivity implements UsersAdapter.UsersAdapterListener {

    String patient_id, previous_doctor;
    private List<Users> usersList;
    DataRouter router;
    private UsersAdapter mAdapter;
    RecyclerView recyclerView;
    HelperFunctions helperFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reassign);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_active_users);
        usersList = new ArrayList<>();
        mAdapter = new UsersAdapter(usersList, this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            previous_doctor = extras.getString("id", "1");
        } else {
            onBackPressed();
        }

        helperFunctions = new HelperFunctions(this);

        router = new DataRouter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        SearchView searchView = findViewById(R.id.search_active_users);

        searchView.setQueryHint("Search users...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        patient_id = new PreferenceManager(this).getPatientId();

        getPatientDetails();
        fetchUsers();
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

    private void fetchUsers() {
        helperFunctions.genericProgressBar("Fetching active users...");
        String url = router.ip_address + "sims_patients/view_active_users";

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    helperFunctions.stopProgressBar();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            usersList.add(new Users(jsonobject.getString("id"), jsonobject.getString("first_name"), jsonobject.getString("last_name"), jsonobject.getString("phone")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // refreshing recycler view
                    mAdapter.notifyDataSetChanged();
                }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again later");
                });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onFormSelected(Users user) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        String message = "Do you want to reassign this patient to " + user.getFirstName() + " " + user.getLastName() + "?";

        alert.setMessage(message).setPositiveButton("Yes", (dialogInterface, i) -> {
            String url = router.ip_address + "sims_patients/reassign_patient";

            helperFunctions.genericProgressBar("Reassigning patient...");

            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                helperFunctions.stopProgressBar();
                if(response.equals("1")){
                    finish();
                    AlertDialog.Builder alert1 = new AlertDialog.Builder(ReassignActivity.this);

                    alert1.setMessage("Patient reassignment complete").setPositiveButton("Okay", (dialogInterface1, i1) -> startActivity(new Intent(ReassignActivity.this, PatientHomeActivity.class))).show();
                } else {
                    helperFunctions.genericDialog("Patient reassignment failed. Please try again");
                }
            }, error -> {
                // This code is executed if there is an error.
                helperFunctions.stopProgressBar();
                helperFunctions.genericDialog("Patient reassignment failed. Please try again");
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("patient_id", patient_id);
                    data.put("episode_id", new PreferenceManager(ReassignActivity.this).getEpisodeId());
                    data.put("previous_doctor", previous_doctor);
                    data.put("current_doctor", user.getId());
                    data.put("created_by", new PreferenceManager(ReassignActivity.this).getUserId());
                    return data;
                }
            };

            Volley.newRequestQueue(ReassignActivity.this).add(request);

        }).setNegativeButton("Cancel", null).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
