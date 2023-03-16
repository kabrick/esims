package tech.streamlinehealth.esims.clinical_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.OccupationAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.models.Occupation;

public class ViewOccupationsActivity extends AppCompatActivity {

    HelperFunctions helperFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_occupations);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helperFunctions = new HelperFunctions(this);

        fetchOccupations();
    }

    private void fetchOccupations() {
        helperFunctions.genericProgressBar("Fetching occupations...");
        String url = new DataRouter(this).ip_address + "sims_clinical_data/view_occupations";

        ArrayList<Occupation> occupations = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    helperFunctions.stopProgressBar();
                    JSONObject obj;

                    for(int i = 0; i < response.length(); i++){

                        try {
                            obj = response.getJSONObject(i);
                            occupations.add(new Occupation(obj.getString("id"), obj.getString("name")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //continue to display
                    RecyclerView recyclerView = findViewById(R.id.view_occupations_recycler);

                    OccupationAdapter mAdapter = new OccupationAdapter(ViewOccupationsActivity.this, occupations);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewOccupationsActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Something went wrong. Please try again later");
        });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
