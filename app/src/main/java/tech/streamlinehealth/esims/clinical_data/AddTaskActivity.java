package tech.streamlinehealth.esims.clinical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class AddTaskActivity extends AppCompatActivity {

    EditText task_name, task_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        task_name = findViewById(R.id.task_name);
        task_description = findViewById(R.id.task_description);
    }

    public void save_task(View view){
        HelperFunctions helperFunctions = new HelperFunctions(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String task_name_string = task_name.getText().toString();
        String task_description_string = task_description.getText().toString();

        if(task_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the task name");
            return;
        }

        helperFunctions.genericProgressBar("Adding new task...");

        String url = new DataRouter(this).ip_address + "sims_clinical_data/add_nasa_tlx_task";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")){
                helperFunctions.genericDialog("Task not created. Please try again");
            } else {
                Toast.makeText(this, "Task created successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, ViewTasksActivity.class));
            }
        }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Task not created. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("name", task_name_string);
                data.put("task_description", task_description_string);
                data.put("created_by", new PreferenceManager(AddTaskActivity.this).getUserId());
                return data;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}