package tech.streamlinehealth.esims.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.PatientTriageAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.PatientTriage;

public class TriageViewFragment extends Fragment {

    public TriageViewFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private TextView no_previous_triage_text;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_triage_view, container, false);

        no_previous_triage_text = view.findViewById(R.id.no_previous_triage_text);
        recyclerView = view.findViewById(R.id.patient_triage_recycler);
        DataRouter router = new DataRouter(getContext());

        String url = router.ip_address + "sims_patients/get_patient_triage/" + new PreferenceManager(getContext()).getPatientId() + "/" + new PreferenceManager(getContext()).getEpisodeId();

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    ArrayList<PatientTriage> triageList = new ArrayList<>();

                    if (response.length() < 1) {
                        no_previous_triage_text.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            triageList.add(new PatientTriage(jsonobject.getString("grade"), jsonobject.getString("checks"), jsonobject.getString("timestamp")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    PatientTriageAdapter mAdapter = new PatientTriageAdapter(triageList, TriageViewFragment.this.getContext());

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TriageViewFragment.this.getContext());

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }, error -> {
                    //
                });

        Volley.newRequestQueue(getContext()).add(request);
        return view;
    }

}
