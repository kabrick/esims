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
import java.util.List;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.UsersAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.Users;

public class AssignPatientActivity extends AppCompatActivity implements UsersAdapter.UsersAdapterListener {

    private List<Users> usersList;
    DataRouter router;
    private UsersAdapter mAdapter;
    RecyclerView recyclerView;
    HelperFunctions helperFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_patient);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_active_users);
        usersList = new ArrayList<>();
        mAdapter = new UsersAdapter(usersList, this);

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

        getPatientDetails();
        fetchUsers();
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + new PreferenceManager(AssignPatientActivity.this).getEpisodeId();

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

        String message = "Do you want to assign this patient to " + user.getFirstName() + " " + user.getLastName() + "?";

        alert.setMessage(message).setPositiveButton("Yes", (dialogInterface, i) -> {
            helperFunctions.genericProgressBar("Assigning patient to doctor...");

            // assign the user
            String network_address = router.ip_address + "sims_patients/assign_patient/" + new PreferenceManager(AssignPatientActivity.this).getPatientId() + "/" + new PreferenceManager(AssignPatientActivity.this).getEpisodeId() + "/" + user.getId();

            StringRequest request = new StringRequest(Request.Method.GET, network_address,
                    response -> {
                        helperFunctions.stopProgressBar();

                        if(response.equals("1")){
                            finish();
                            AlertDialog.Builder alert1 = new AlertDialog.Builder(AssignPatientActivity.this);

                            alert1.setMessage("Patient assignment complete").setPositiveButton("Okay", (dialogInterface1, i1) -> startActivity(new Intent(AssignPatientActivity.this, PatientHomeActivity.class))).show();
                        } else {
                            helperFunctions.genericDialog("Patient assignment failed. Please try again");
                        }
                    }, error -> {
                        helperFunctions.stopProgressBar();
                        helperFunctions.genericDialog("Patient assignment failed. Please try again");
                    });

            Volley.newRequestQueue(AssignPatientActivity.this).add(request);
        }).setNegativeButton("Cancel", null).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
