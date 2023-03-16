package tech.streamlinehealth.esims.patients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.CompletedActionsAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.CompletedAction;

public class CompletedActionsTimelineActivity extends AppCompatActivity {

    String actions_required_string, actions_status_string, patient_id;
    RecyclerView recyclerView;
    CompletedActionsAdapter mAdapter;
    ArrayList<CompletedAction> actions;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_actions_timeline);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actions = new ArrayList<>();

        mAdapter = new CompletedActionsAdapter(actions);
        recyclerView = findViewById(R.id.recycler_completed_actions);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] actions_required_arr = extras.getString("actions_required_string", "").split(",");
            String[] actions_status_arr = extras.getString("actions_status_string", "").split(",");

            // create a hashmap to store the data
            HashMap<String, String> actions_map = new HashMap<>();

            // iterate through the actions and place them in the hashmap
            for (int i = 0; i < actions_required_arr.length; i++) {
                actions_map.put(actions_status_arr[i], actions_required_arr[i]);
            }

            // sort the hashmap into a tree
            SortedSet<String> actions_set = new TreeSet<>(actions_map.keySet());

            // iterate through the tree and add values to the adapter
            for (String key : actions_set) {
                if (!key.equals("0")) {
                    @SuppressLint("SimpleDateFormat") String timeAgo = new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").
                            format(new java.util.Date (Long.parseLong(key)));
                    String action = new HelperFunctions(this).emergency_signs_prompts.get(Integer.parseInt(Objects.requireNonNull(actions_map.get(key))));
                    actions.add(new CompletedAction(action, timeAgo));
                }
            }

            mAdapter.notifyDataSetChanged();

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }

        patient_id = new PreferenceManager(this).getPatientId();
        getPatientDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Record Actions Taken");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CompletedActionsTimelineActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = new DataRouter(this).ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        HelperFunctions helperFunctions = new HelperFunctions(this);

                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" +  response.getString("gender") + ") " + response.getString("date_of_birth");
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
        onBackPressed();
        return true;
    }
}