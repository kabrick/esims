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

public class AddOccupationActivity extends AppCompatActivity {

    EditText occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_occupation);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        occupation = findViewById(R.id.occupation);
    }

    public void save_occupation(View view){
        HelperFunctions helperFunctions = new HelperFunctions(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String occupation_string = occupation.getText().toString();

        if(occupation_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the occupation");
            return;
        }

        helperFunctions.genericProgressBar("Adding new occupation...");

        String url = new DataRouter(this).ip_address + "sims_clinical_data/add_occupation";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, response -> {
            helperFunctions.stopProgressBar();

            if (response.equals("0")){
                helperFunctions.genericDialog("Occupation not created. Please try again");
            } else {
                Toast.makeText(this, "Occupation created successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, ViewOccupationsActivity.class));
            }
        }, error -> {
            helperFunctions.stopProgressBar();
            helperFunctions.genericDialog("Occupation not created. Please try again");
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("name", occupation_string);
                data.put("created_by", new PreferenceManager(AddOccupationActivity.this).getUserId());
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
