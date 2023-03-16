package tech.streamlinehealth.esims.ui.analysis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;

public class AnalysisFragment extends Fragment {
    DataRouter router;
    ArrayList<Integer> patient_days_list, admissions_list;
    ArrayList<String> chart_labels;
    RequestQueue requestQueue;
    HelperFunctions helperFunctions;
    TextView wk1_label, wk2_label, wk3_label, wk4_label;
    LinearLayout linearLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_analysis, container, false);
        
        router = new DataRouter(this.getActivity());
        requestQueue = Volley.newRequestQueue(this.requireActivity());
        helperFunctions = new HelperFunctions(requireActivity());

        linearLayout = root.findViewById(R.id.tableview);

        wk1_label = root.findViewById(R.id.wk1_label);
        wk2_label = root.findViewById(R.id.wk2_label);
        wk3_label = root.findViewById(R.id.wk3_label);
        wk4_label = root.findViewById(R.id.wk4_label);

        getWeeklyAdmissions();
        return root;
    }

    public void getWeeklyAdmissions() {
        String url = router.ip_address + "sims_reports/api/prevalence_summary";

        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray patient_discharged_array = response.getJSONArray("patient_days_array");
                JSONArray patient_died_array = response.getJSONArray("admissions_array");
                JSONArray chart_label_array = response.getJSONArray("chart_labels");

                chart_labels = new ArrayList<>();
                for (int i = 0; i < chart_label_array.length(); i++) {
                    chart_labels.add((String) chart_label_array.get(i));
                }
                wk1_label.setText(getString(R.string.wk1) + chart_labels.get(0));
                wk2_label.setText(getString(R.string.wk2)+ chart_labels.get(1));
                wk3_label.setText(getString(R.string.wk3)+ chart_labels.get(2));
                wk4_label.setText(getString(R.string.wk4)+ chart_labels.get(3));

                patient_days_list = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(patient_discharged_array, patient_days_list);
                admissions_list = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(patient_died_array, admissions_list);

                TableLayout table = new TableLayout(requireActivity());

                TableRow table_header = new TableRow(requireActivity());
                TableRow tableRow = new TableRow(requireActivity());
                TextView title_cell = new TextView(requireActivity());
                TableRow tableRow1 = new TableRow(requireActivity());
                TextView title_cell1 = new TextView(requireActivity());

                helperFunctions.createHeaderTableRow(table, table_header, requireActivity());
                table_header.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.colorPrimary));
                helperFunctions.createTableRow(table, tableRow,title_cell, patient_days_list, requireActivity(), "Admissions");
                tableRow.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRow(table, tableRow1,title_cell1, admissions_list, requireActivity(), "Patient days on ward");
                tableRow1.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                linearLayout.addView(table);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);
    }
}
