package tech.streamlinehealth.esims.patients;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.PatientEpisodeAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.PatientEpisode;

public class PatientEpisodesActivity extends AppCompatActivity {

    String patient_id;
    DataRouter router;
    HelperFunctions helperFunctions;
    FloatingActionButton fab_add_episode;
    PatientEpisodeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_episodes);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);

        patient_id = new PreferenceManager(this).getPatientId();

        fab_add_episode = findViewById(R.id.fab_add_episode);

        fab_add_episode.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(PatientEpisodesActivity.this);
            alert.setMessage("Are you sure you want to create a new episode for this patient?").setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(PatientEpisodesActivity.this, AddPatientEpisodeActivity.class))).setNegativeButton("Cancel", null).show();
        });

        getPatientDetails();

        getEpisodes();
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" +  response.getString("gender") + ") " + response.getString("date_of_birth") ;
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

    public void getEpisodes(){
        String url = router.ip_address + "sims_patients/view_patients_episodes/" + patient_id;

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {

                    ArrayList<PatientEpisode> episodesList = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            episodesList.add(new PatientEpisode(jsonobject.getString("id"), jsonobject.getString("ward"), jsonobject.getString("admission_diagnosis"), jsonobject.getString("triage_grade"), jsonobject.getString("admission_timestamp"), jsonobject.getString("discharge_status")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mAdapter = new PatientEpisodeAdapter(episodesList, PatientEpisodesActivity.this);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PatientEpisodesActivity.this);

                    RecyclerView recyclerView = findViewById(R.id.recycler_view_patient_episodes);

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
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
}
