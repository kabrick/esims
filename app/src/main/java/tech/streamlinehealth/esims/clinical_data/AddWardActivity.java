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

public class AddWardActivity extends AppCompatActivity {

    EditText ward_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ward);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ward_name = findViewById(R.id.ward_name);
    }

    public void save_ward(View view){
        HelperFunctions helperFunctions = new HelperFunctions(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String ward_name_string = ward_name.getText().toString();

        if(ward_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the ward name");
            return;
        }

        helperFunctions.genericProgressBar("Adding new ward...");

        String url = new DataRouter(this).ip_address + "sims_clinical_data/add_ward";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")){
                helperFunctions.genericDialog("Ward not created. Please try again");
            } else {
                Toast.makeText(this, "Ward created successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, ViewWardsActivity.class));
            }
        }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Ward not created. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("name", ward_name_string);
                data.put("created_by", new PreferenceManager(AddWardActivity.this).getUserId());
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
