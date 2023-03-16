package tech.streamlinehealth.esims.ui.analysis;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;

public class PatientOutcomesFragment extends Fragment {
    DataRouter router;
    ArrayList<Integer> patient_discharged_list, passed_on_patient_list;
    ArrayList<String> chart_labels;
    RequestQueue requestQueue;
    HelperFunctions helperFunctions;
    TextView wk1_label, wk2_label, wk3_label, wk4_label;
    LinearLayout linearLayout;

    public PatientOutcomesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_outcomes, container, false);
        router = new DataRouter(this.getActivity());
        requestQueue = Volley.newRequestQueue(this.requireActivity());
        helperFunctions = new HelperFunctions(requireActivity());

        linearLayout = view.findViewById(R.id.tableview);

        wk1_label = view.findViewById(R.id.wk1_label);
        wk2_label = view.findViewById(R.id.wk2_label);
        wk3_label = view.findViewById(R.id.wk3_label);
        wk4_label = view.findViewById(R.id.wk4_label);

        getPatientOutcomesData();
        return view;
    }

    public void getPatientOutcomesData() {
        String url = router.ip_address + "sims_reports/api/patient_outcomes";

        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray patient_discharged_array = response.getJSONArray("patient_discharged_array");
                JSONArray patient_died_array = response.getJSONArray("patient_died_array");
                JSONArray chart_label_array = response.getJSONArray("chart_labels");

                chart_labels = new ArrayList<>();
                for (int i = 0; i < chart_label_array.length(); i++) {
                    chart_labels.add((String) chart_label_array.get(i));
                }
                wk1_label.setText(getString(R.string.wk1) + chart_labels.get(0));
                wk2_label.setText(getString(R.string.wk2)+ chart_labels.get(1));
                wk3_label.setText(getString(R.string.wk3)+ chart_labels.get(2));
                wk4_label.setText(getString(R.string.wk4)+ chart_labels.get(3));

                patient_discharged_list = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(patient_discharged_array, patient_discharged_list);
                passed_on_patient_list = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(patient_died_array, passed_on_patient_list);

                TableLayout table = new TableLayout(requireActivity());

                TableRow table_header = new TableRow(requireActivity());
                TableRow tableRow = new TableRow(requireActivity());
                TextView title_cell = new TextView(requireActivity());
                TableRow tableRow1 = new TableRow(requireActivity());
                TextView title_cell1 = new TextView(requireActivity());

                helperFunctions.createHeaderTableRow(table, table_header, requireActivity());
                table_header.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.colorPrimary));
                helperFunctions.createTableRow(table, tableRow,title_cell, patient_discharged_list, requireActivity(), "Patients Discharged");
                tableRow.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRow(table, tableRow1,title_cell1, passed_on_patient_list, requireActivity(), "Patients Who Died");
                tableRow1.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                linearLayout.addView(table);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);
    }
}