package tech.streamlinehealth.esims.workload_assessment;

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
import tech.streamlinehealth.esims.adapters.TaskAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.models.Task;

public class ViewTasksListsActivity extends AppCompatActivity {

    HelperFunctions helperFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks_lists);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helperFunctions = new HelperFunctions(this);

        fetchTasks();
    }

    private void fetchTasks() {
        helperFunctions.genericProgressBar("Fetching tasks...");
        String url = new DataRouter(this).ip_address + "sims_clinical_data/view_nasa_tlx_tasks";

        ArrayList<Task> tasks = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    helperFunctions.stopProgressBar();
                    JSONObject obj;

                    for(int i = 0; i < response.length(); i++){

                        try {
                            obj = response.getJSONObject(i);
                            tasks.add(new Task(obj.getString("id"), obj.getString("task_name"), obj.getString("task_description")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //continue to display
                    RecyclerView recyclerView = findViewById(R.id.view_tasks_recycler);

                    // insert patients into list
                    TaskAdapter mAdapter = new TaskAdapter(ViewTasksListsActivity.this, tasks, true);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewTasksListsActivity.this);
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