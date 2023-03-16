package tech.streamlinehealth.esims.clinical_data;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class EditTaskActivity extends AppCompatActivity {

    EditText task_name, task_description;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        task_name = findViewById(R.id.task_name);
        task_description = findViewById(R.id.task_description);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            id = extras.getString("id", "1");
            name = extras.getString("name", "");
            task_name.setText(extras.getString("name", ""));
        }
        else {
            onBackPressed();
        }
    }

    public void register_task(View view){
        HelperFunctions helperFunctions = new HelperFunctions(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String task_name_string = task_name.getText().toString();
        String task_description_string = task_description.getText().toString();

        if(task_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the task name");
            return;
        }

        helperFunctions.genericProgressBar("Editing task...");

        String url = new DataRouter(this).ip_address + "sims_clinical_data/edit_nasa_tlx_task";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")){
                helperFunctions.genericDialog("Task not edited. Please try again");
            } else {
                Toast.makeText(this, "Task edited successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, ViewTasksActivity.class));
            }
        }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Task not edited. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", id);
                data.put("name", task_name_string);
                data.put("task_description", task_description_string);
                data.put("created_by", new PreferenceManager(EditTaskActivity.this).getUserId());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(){
        HelperFunctions helperFunctions = new HelperFunctions(this);

        AlertDialog.Builder alert1 = new AlertDialog.Builder(this);

        alert1.setMessage("Are you sure you want to delete " + name + "?")
                .setPositiveButton("Yes", (dialogInterface1, i1) -> {
                    helperFunctions.genericProgressBar("Deleting task..");
                    String network_address = new DataRouter(this).ip_address + "sims_clinical_data/delete_nasa_tlx_task/" + id;

                    // Request a string response from the provided URL
                    StringRequest request = new StringRequest(network_address,
                            response -> {
                                helperFunctions.stopProgressBar();

                                if(response.equals("1")){

                                    AlertDialog.Builder alert = new AlertDialog.Builder(this);

                                    alert.setMessage("Task has been deleted").setPositiveButton("Okay", (dialogInterface11, i11) -> {
                                        Intent delete_intent = new Intent(this, ViewTasksActivity.class);
                                        this.startActivity(delete_intent);
                                    }).show();
                                } else {
                                    Toast.makeText(this, "Unable to delete the task. Please try again", Toast.LENGTH_LONG).show();
                                }

                            }, error -> {
                        helperFunctions.stopProgressBar();

                        if (error instanceof TimeoutError || error instanceof NetworkError) {
                            helperFunctions.genericDialog("Something went wrong. Please make sure you are connected to a working internet connection.");
                        } else {
                            helperFunctions.genericDialog("Something went wrong. Please try again later");
                        }
                    });

                    Volley.newRequestQueue(this).add(request);
                }).show();
    }
}