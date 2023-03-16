package tech.streamlinehealth.esims.patients;


import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.DiagnosesAdapter;
import tech.streamlinehealth.esims.models.Diagnoses;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectDiagnosisActivity extends AppCompatActivity implements DiagnosesAdapter.DiagnosesAdapterListener {

    private List<Diagnoses> diagnosisList;
    private DiagnosesAdapter mAdapter;
    String diagnosis_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_diagnosis);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            diagnosis_type = extras.getString("diagnosis", "1");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_select_diagnosis);
        diagnosisList = new ArrayList<>();
        mAdapter = new DiagnosesAdapter(diagnosisList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        SearchView searchView = findViewById(R.id.search_select_diagnosis);

        searchView.setQueryHint("Search diagnoses");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        getDiagnosisList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDiagnosisSelected(Diagnoses diagnosis) {
        Intent i = new Intent();
        i.putExtra("diagnosis", diagnosis.getName());
        i.putExtra("diagnosis_type", diagnosis_type);
        setResult(1, i);
        finish();
    }

    public void getDiagnosisList(){
        // clear the list
        diagnosisList.clear();

        // add items
        diagnosisList.add(new Diagnoses("Alcoholic intoxication"));
        diagnosisList.add(new Diagnoses("Anemia"));
        diagnosisList.add(new Diagnoses("Amoebiasis"));
        diagnosisList.add(new Diagnoses("Arthritis"));
        diagnosisList.add(new Diagnoses("Asthma"));
        diagnosisList.add(new Diagnoses("Cholecystitis"));
        diagnosisList.add(new Diagnoses("Cirrhosis"));
        diagnosisList.add(new Diagnoses("Colitis"));
        diagnosisList.add(new Diagnoses("COPD"));
        diagnosisList.add(new Diagnoses("Dehydration"));
        diagnosisList.add(new Diagnoses(" Diabetic crisis"));
        diagnosisList.add(new Diagnoses("Diverticulitis"));
        diagnosisList.add(new Diagnoses("Esophagitis"));
        diagnosisList.add(new Diagnoses("Gastritis"));
        diagnosisList.add(new Diagnoses("Gastroenteritis"));
        diagnosisList.add(new Diagnoses("Gastrointestinal bleeding"));
        diagnosisList.add(new Diagnoses("Heart Failure/congestive cardiac failure (CCF)"));
        diagnosisList.add(new Diagnoses("Hepatitis"));
        diagnosisList.add(new Diagnoses("Kidney failure"));
        diagnosisList.add(new Diagnoses("Liver cirrhosis"));
        diagnosisList.add(new Diagnoses("Lower respiratory tract infection (LRTI)"));
        diagnosisList.add(new Diagnoses("Malaria"));
        diagnosisList.add(new Diagnoses("Malignancy"));
        diagnosisList.add(new Diagnoses("Meningitis/Encephalitis"));
        diagnosisList.add(new Diagnoses("Myocardial infarction"));
        diagnosisList.add(new Diagnoses("Pancreatitis"));
        diagnosisList.add(new Diagnoses("Pharyngitis"));
        diagnosisList.add(new Diagnoses("Peptic ulcer disease"));
        diagnosisList.add(new Diagnoses("Pelvic inflammatory disease (PID)"));
        diagnosisList.add(new Diagnoses("Pneumonia"));
        diagnosisList.add(new Diagnoses("Seizures"));
        diagnosisList.add(new Diagnoses("Sepsis"));
        diagnosisList.add(new Diagnoses("Severe hypertension"));
        diagnosisList.add(new Diagnoses("Shock (cardiogenic)"));
        diagnosisList.add(new Diagnoses("Shock (hypovolemic)"));
        diagnosisList.add(new Diagnoses("Shock (septic)"));
        diagnosisList.add(new Diagnoses("Status epilepticus"));
        diagnosisList.add(new Diagnoses("Stroke"));
        diagnosisList.add(new Diagnoses("TB"));
        diagnosisList.add(new Diagnoses("Upper respiratory tract infection (URTI)"));
        diagnosisList.add(new Diagnoses("Urinary tract infection (UTI)"));

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
    }
}
