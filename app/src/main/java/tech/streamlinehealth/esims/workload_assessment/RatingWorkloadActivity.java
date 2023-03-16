package tech.streamlinehealth.esims.workload_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tech.streamlinehealth.esims.HomeActivity;
import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class RatingWorkloadActivity extends AppCompatActivity {

    String task_id, task_name = "";

    int mental_score, physical_score, temporal_score, performance_score, effort_score, frustration_score = 0;
    int mental_tally, physical_tally, temporal_tally, performance_tally, effort_tally, frustration_tally = 0;
    double mental_weight, physical_weight, temporal_weight, performance_weight, effort_weight, frustration_weight = 0;
    boolean group1, group2, group3, group4, group5, group6, group7, group8, group9, group10, group11, group12,
        group13, group14, group15 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_workload);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            task_id = extras.getString("id", "1");
            task_name = extras.getString("name", "");
            mental_score = extras.getInt("mental_score", 0);
            physical_score = extras.getInt("physical_score", 0);
            temporal_score = extras.getInt("temporal_score", 0);
            performance_score = extras.getInt("performance_score", 0);
            effort_score = extras.getInt("effort_score", 0);
            frustration_score = extras.getInt("frustration_score", 0);
        }
        else {
            onBackPressed();
        }
    }

    public void addMental(View view) {
        mental_tally++;

        String tag = view.getTag().toString();

        switch (tag) {
            case "12":
                if (group12) {
                    // reduce the other one
                    frustration_tally--;
                } else {
                    group12 = true;
                }
                break;
            case "10":
                if (group10) {
                    // reduce the other one
                    temporal_tally--;
                } else {
                    group10 = true;
                }
                break;
            case "5":
                if (group5) {
                    // reduce the other one
                    effort_tally--;
                } else {
                    group5 = true;
                }
                break;
            case "3":
                if (group3) {
                    // reduce the other one
                    performance_tally--;
                } else {
                    group3 = true;
                }
                break;
            case "2":
                if (group2) {
                    // reduce the other one
                    physical_tally--;
                } else {
                    group2 = true;
                }
                break;
        }
    }

    public void addPhysical(View view) {
        physical_tally++;

        String tag = view.getTag().toString();

        switch (tag) {
            case "15":
                if (group15) {
                    // reduce the other one
                    temporal_tally--;
                } else {
                    group15 = true;
                }
                break;
            case "14":
                if (group14) {
                    // reduce the other one
                    performance_tally--;
                } else {
                    group14 = true;
                }
                break;
            case "9":
                if (group9) {
                    // reduce the other one
                    effort_tally--;
                } else {
                    group9 = true;
                }
                break;
            case "8":
                if (group8) {
                    // reduce the other one
                    frustration_tally--;
                } else {
                    group8 = true;
                }
                break;
            case "2":
                if (group2) {
                    // reduce the other one
                    mental_tally--;
                } else {
                    group2 = true;
                }
                break;
        }
    }

    public void addPerformance(View view) {
        performance_tally++;

        String tag = view.getTag().toString();

        switch (tag) {
            case "14":
                if (group4) {
                    // reduce the other one
                    physical_tally--;
                } else {
                    group4 = true;
                }
                break;
            case "6":
                if (group6) {
                    // reduce the other one
                    effort_tally--;
                } else {
                    group6 = true;
                }
                break;
            case "4":
                if (group4) {
                    // reduce the other one
                    frustration_tally--;
                } else {
                    group4 = true;
                }
                break;
            case "3":
                if (group3) {
                    // reduce the other one
                    mental_tally--;
                } else {
                    group3 = true;
                }
                break;
            case "1":
                if (group1) {
                    // reduce the other one
                    temporal_tally--;
                } else {
                    group1 = true;
                }
                break;
        }
    }

    public void addTemporal(View view) {
        temporal_tally++;

        String tag = view.getTag().toString();

        switch (tag) {
            case "15":
                if (group15) {
                    // reduce the other one
                    physical_tally--;
                } else {
                    group15 = true;
                }
                break;
            case "13":
                if (group13) {
                    // reduce the other one
                    frustration_tally--;
                } else {
                    group13 = true;
                }
                break;
            case "10":
                if (group10) {
                    // reduce the other one
                    mental_tally--;
                } else {
                    group10 = true;
                }
                break;
            case "7":
                if (group7) {
                    // reduce the other one
                    effort_tally--;
                } else {
                    group7 = true;
                }
                break;
            case "1":
                if (group1) {
                    // reduce the other one
                    performance_tally--;
                } else {
                    group1 = true;
                }
                break;
        }
    }

    public void addEffort(View view) {
        effort_tally++;

        String tag = view.getTag().toString();

        switch (tag) {
            case "11":
                if (group11) {
                    // reduce the other one
                    frustration_tally--;
                } else {
                    group11 = true;
                }
                break;
            case "9":
                if (group9) {
                    // reduce the other one
                    physical_tally--;
                } else {
                    group9 = true;
                }
                break;
            case "7":
                if (group7) {
                    // reduce the other one
                    temporal_tally--;
                } else {
                    group7 = true;
                }
                break;
            case "6":
                if (group6) {
                    // reduce the other one
                    performance_tally--;
                } else {
                    group6 = true;
                }
                break;
            case "5":
                if (group5) {
                    // reduce the other one
                    mental_tally--;
                } else {
                    group5 = true;
                }
                break;
        }
    }

    public void addFrustration(View view) {
        frustration_tally++;

        String tag = view.getTag().toString();

        switch (tag) {
            case "13":
                if (group13) {
                    // reduce the other one
                    temporal_tally--;
                } else {
                    group13 = true;
                }
                break;
            case "12":
                if (group12) {
                    // reduce the other one
                    mental_tally--;
                } else {
                    group12 = true;
                }
                break;
            case "11":
                if (group11) {
                    // reduce the other one
                    effort_tally--;
                } else {
                    group11 = true;
                }
                break;
            case "8":
                if (group8) {
                    // reduce the other one
                    physical_tally--;
                } else {
                    group8 = true;
                }
                break;
            case "4":
                if (group4) {
                    // reduce the other one
                    performance_tally--;
                } else {
                    group4 = true;
                }
                break;
        }
    }

    public void save_ratings(View view) {
        HelperFunctions helperFunctions = new HelperFunctions(this);

        // calculate the weights
        mental_weight = mental_tally / 15.0;
        physical_weight = physical_tally / 15.0;
        temporal_weight = temporal_tally / 15.0;
        performance_weight = performance_tally / 15.0;
        effort_weight = effort_tally / 15.0;
        frustration_weight = frustration_tally / 15.0;

        double overall_score = (mental_score * mental_weight) + (physical_score * physical_weight) + (temporal_score * temporal_weight) +
                (performance_score * performance_weight) + (effort_score * effort_weight) + (frustration_score * frustration_weight);

        String all_ratings = mental_score + "," + physical_score + "," + temporal_score + "," +
                performance_score + "," + effort_score + "," + frustration_score;

        String all_tallies = mental_tally + "," + physical_tally + "," + temporal_tally + "," +
                performance_tally + "," + effort_tally + "," + frustration_tally;

        String all_weights = mental_weight + "," + physical_weight + "," + temporal_weight + "," +
                performance_weight + "," + effort_weight + "," + frustration_weight;

        helperFunctions.genericProgressBar("Saving workload assessment...");

        String url = new DataRouter(this).ip_address + "sims_usage_reports/save_workload_assessment";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")){
                helperFunctions.genericDialog("Workload assessment not saved. Please try again");
            } else {
                Toast.makeText(this, "Workload assessment saved successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomeActivity.class));
            }
        }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Workload assessment not saved. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", new PreferenceManager(RatingWorkloadActivity.this).getUserId());
                data.put("task_id", task_id);
                data.put("weight", all_weights);
                data.put("tally", all_tallies);
                data.put("ratings", all_ratings);
                data.put("overall_score", String.valueOf(overall_score));
                return data;
            }
        };

        Volley.newRequestQueue(this).add(MyStringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}