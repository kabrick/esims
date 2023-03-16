package tech.streamlinehealth.esims.clinical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;

public class ViewClinicalDataActivity extends AppCompatActivity {
    HelperFunctions helperFunctions;
    DataRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clinical_data);

        helperFunctions = new HelperFunctions(this);
        router = new DataRouter(this);
        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void add_ward(View view){
        startActivity(new Intent(this, AddWardActivity.class));
    }

    public void view_wards(View view){
        startActivity(new Intent(this, ViewWardsActivity.class));
    }

    public void add_occupation(View view){
        startActivity(new Intent(this, AddOccupationActivity.class));
    }

    public void view_occupations(View view){
        startActivity(new Intent(this, ViewOccupationsActivity.class));
    }

    public void add_task(View view){
        startActivity(new Intent(this, AddTaskActivity.class));
    }

    public void view_tasks(View view){
        startActivity(new Intent(this, ViewTasksActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
