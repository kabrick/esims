package tech.streamlinehealth.esims.users;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.PatientsAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.models.Patient;

public class ViewUserDetailsActivity extends AppCompatActivity {

    TextView edit_user, delete_user, user_info_text;
    String user_id;
    HelperFunctions helperFunctions;
    DataRouter router;
    String first_name, last_name, username, phone, email, pin, is_admin;
    PatientsAdapter mAdapter;
    ArrayList<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            user_id = extras.getString("id", "1");
        } else {
            onBackPressed();
        }

        helperFunctions = new HelperFunctions(this);
        router = new DataRouter(this);

        patients = new ArrayList<>();

        mAdapter = new PatientsAdapter(this, patients);

        final RecyclerView recyclerView = findViewById(R.id.recycler_patients);

        DataRouter router = new DataRouter(this);

        String url = router.ip_address + "sims_patients/view_assigned_patients/" + user_id;

        JsonArrayRequest request1 = new JsonArrayRequest(url,
                response -> {

                    if(response.length() < 1){
                        TextView no_assigned_patients = findViewById(R.id.no_assigned_patients);
                        no_assigned_patients.setVisibility(View.VISIBLE);
                    }

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            patients.add(new Patient(jsonobject.getString("id"), jsonobject.getString("name"), jsonobject.getString("number"),
                                    jsonobject.getString("ward"), jsonobject.getString("admission_timestamp"), jsonobject.getString("triage_grade"),
                                    jsonobject.getString("gender"), jsonobject.getString("date_of_birth"), jsonobject.getString("discharge_status"), jsonobject.getString("discharge_timestamp"), false));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // insert patients into list
                    mAdapter.notifyDataSetChanged();

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewUserDetailsActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
                    //
                });

        Volley.newRequestQueue(this).add(request1);

        user_info_text = findViewById(R.id.user_info_text);

        helperFunctions.genericProgressBar("Fetching user information...");

        // check if patient has already been assigned a doctor
        String network_address = router.ip_address + "sims_patients/get_user_details/" + user_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {
                    helperFunctions.stopProgressBar();

                    try {
                        first_name = response.getString("first_name");
                        last_name = response.getString("last_name");
                        username = response.getString("username");
                        phone = response.getString("phone");
                        email = response.getString("email");
                        pin = response.getString("pin");
                        is_admin = response.getString("is_admin");

                        String user_info = first_name + " " + last_name + " (" + username + ")";

                        user_info_text.setText(user_info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again later.");
                });

        Volley.newRequestQueue(this).add(request);

        edit_user = findViewById(R.id.edit_user);
        delete_user = findViewById(R.id.delete_user);

        edit_user.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ViewUserDetailsActivity.this);
            alert.setMessage("Are you sure you want to edit this user").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent edit_intent  = new Intent(ViewUserDetailsActivity.this, EditUserActivity.class);
                    edit_intent.putExtra("first_name", first_name);
                    edit_intent.putExtra("last_name", last_name);
                    edit_intent.putExtra("username", username);
                    edit_intent.putExtra("phone", phone);
                    edit_intent.putExtra("email", email);
                    edit_intent.putExtra("pin", pin);
                    edit_intent.putExtra("id", user_id);
                    edit_intent.putExtra("is_admin", is_admin);
                    startActivity(edit_intent);
                }
            }).setNegativeButton("Cancel", null).show();
        });

        delete_user.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ViewUserDetailsActivity.this);
            alert.setMessage("Are you sure you want to delete this user").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // delete the user
                    helperFunctions.genericProgressBar("Deleting user...");

                    // check if patient has already been assigned a doctor
                    String network_address1 = router.ip_address + "sims_patients/delete_user/" + user_id;

                    StringRequest request2 = new StringRequest(Request.Method.GET, network_address1,
                            response -> {
                                helperFunctions.stopProgressBar();

                                if (!response.equals("0")){
                                    // patient has an assigned doctor
                                    AlertDialog.Builder alert1 = new AlertDialog.Builder(ViewUserDetailsActivity.this);

                                    alert1.setMessage("User has been deleted from the system").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface1, int i1) {
                                            startActivity(new Intent(ViewUserDetailsActivity.this, ViewUsersActivity.class));
                                        }
                                    }).setNegativeButton("Cancel", null).show();
                                }
                            }, error -> {
                                helperFunctions.stopProgressBar();
                                helperFunctions.genericDialog("Something went wrong. Please try again later.");
                            });

                    Volley.newRequestQueue(ViewUserDetailsActivity.this).add(request2);
                }
            }).setNegativeButton("Cancel", null).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
