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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;


public class SevereIllnessMgtFragment extends Fragment {
    DataRouter router;
    ArrayList<Float> identified_illness_manage_list, identified_illness_arrayList;
    ArrayList<String> chart_labels_severe_illness;
    LineChart chart_severe_illness_mgt;
    RequestQueue requestQueue;
    HelperFunctions helperFunctions;
    TextView wk1_label, wk2_label, wk3_label, wk4_label;
    LinearLayout linearLayout;

    public SevereIllnessMgtFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_severe_illness_mgt, container, false);

        router = new DataRouter(this.getActivity());
        requestQueue = Volley.newRequestQueue(this.requireActivity());
        helperFunctions = new HelperFunctions(requireActivity());

        identified_illness_manage_list = new ArrayList<>();
        identified_illness_arrayList = new ArrayList<>();
        chart_labels_severe_illness = new ArrayList<>();
        chart_severe_illness_mgt = view.findViewById(R.id.chart_severe_illness);
        getSevereIllnessLineGraphData();
        linearLayout = view.findViewById(R.id.tableview);

        wk1_label = view.findViewById(R.id.wk1_label);
        wk2_label = view.findViewById(R.id.wk2_label);
        wk3_label = view.findViewById(R.id.wk3_label);
        wk4_label = view.findViewById(R.id.wk4_label);

        return view;
    }

    public void getSevereIllnessLineGraphData() {
        String url = router.ip_address + "sims_reports/api/severe_illness_management";

        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject statics_obj = response.getJSONObject("statistics");
                JSONArray convulsing_array = statics_obj.getJSONArray("convulsing_array");
                JSONArray altered_consciousness = statics_obj.getJSONArray("altered_consiousness_array");
                JSONArray shock_array = statics_obj.getJSONArray("shock_array");
                JSONArray septic_shock_array = statics_obj.getJSONArray("septic_shock_array");
                JSONArray severe_respiratory_distress = statics_obj.getJSONArray("severe_respiratory_distress_array");
                JSONArray chart_label = statics_obj.getJSONArray("chart_labels");

                ArrayList<String> chart_label_arrayList = new ArrayList<>();
                for (int i = 0; i < chart_label.length(); i++) {
                    chart_label_arrayList.add((String) chart_label.get(i));
                }
                wk1_label.setText(getString(R.string.wk1) + chart_label_arrayList.get(0));
                wk2_label.setText(getString(R.string.wk2)+ chart_label_arrayList.get(1));
                wk3_label.setText(getString(R.string.wk3)+ chart_label_arrayList.get(2));
                wk4_label.setText(getString(R.string.wk4)+ chart_label_arrayList.get(3));

                ArrayList<Integer> convulsing_arrayList = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(convulsing_array, convulsing_arrayList);
                ArrayList<Integer> altered_consciousness_arrayList = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(altered_consciousness, altered_consciousness_arrayList);
                ArrayList<Integer> shock_arrayList = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(shock_array, shock_arrayList);
                ArrayList<Integer> septic_shock_arrayList = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(septic_shock_array, septic_shock_arrayList);
                ArrayList<Integer> severe_respiratory_distress_arrayList = new ArrayList<>();
                helperFunctions.JSONArrayToArrayList(severe_respiratory_distress, severe_respiratory_distress_arrayList);


                TableLayout table = new TableLayout(requireActivity());

                TableRow table_header = new TableRow(requireActivity());
                TableRow tableRow = new TableRow(requireActivity());
                TextView title_cell = new TextView(requireActivity());
                TableRow tableRow1 = new TableRow(requireActivity());
                TextView title_cell1 = new TextView(requireActivity());
                TableRow tableRow2 = new TableRow(requireActivity());
                TextView title_cell2 = new TextView(requireActivity());
                TableRow tableRow3 = new TableRow(requireActivity());
                TextView title_cell3 = new TextView(requireActivity());
                TableRow tableRow4 = new TableRow(requireActivity());
                TextView title_cell4 = new TextView(requireActivity());

                helperFunctions.createHeaderTableRow(table, table_header, requireActivity());
                table_header.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.colorPrimary));
                helperFunctions.createTableRow(table, tableRow,title_cell, convulsing_arrayList, requireActivity(), "Convulsing");
                tableRow.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRow(table, tableRow1,title_cell1, altered_consciousness_arrayList, requireActivity(), "Altered Conscious");
                tableRow1.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                helperFunctions.createTableRow(table, tableRow2,title_cell2, shock_arrayList, requireActivity(), "Shock");
                tableRow2.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRow(table, tableRow3,title_cell3, septic_shock_arrayList, requireActivity(), "Septic Shock");
                tableRow3.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                helperFunctions.createTableRow(table, tableRow4,title_cell4, septic_shock_arrayList, requireActivity(), "Severe Resp Distress");
                tableRow4.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                linearLayout.addView(table);




                JSONObject graph_obj = response.getJSONObject("graphs");
                helperFunctions.getJsonArray(graph_obj, "identified_illness_manage_array", identified_illness_manage_list);
                helperFunctions.getJsonArray(graph_obj, "identified_illness_array", identified_illness_arrayList);
                helperFunctions.getJsonArrayWithStringArrayList(graph_obj, "chart_labels", chart_labels_severe_illness);
                plotGraphSevereIllness(identified_illness_manage_list, identified_illness_arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);
    }

    public void plotGraphSevereIllness(ArrayList<Float> manage_illness, ArrayList<Float> identified_illness) {
        List<Entry> manage_illness_entries = new ArrayList<>();
        List<Entry> identified_illness_entries = new ArrayList<>();

        helperFunctions.addEntries(manage_illness, manage_illness_entries);
        helperFunctions.addEntries(identified_illness, identified_illness_entries);

        LineDataSet dataSet1 = new LineDataSet(manage_illness_entries, "Severe illness episodes managed by recommendations");
        LineDataSet dataSet2 = new LineDataSet(identified_illness_entries, "Patient days with identified severe illness episode");

        helperFunctions.decorateDataSet(dataSet1, R.color.vitals_measured_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet2, R.color.avpu_color, requireActivity());

        Description desc = chart_severe_illness_mgt.getDescription();
        desc.setEnabled(false);

        ArrayList<String> chart_labels = new ArrayList<>(Arrays.asList("WK1", "WK2", "WK3", "WK4"));
        XAxis xAxis = chart_severe_illness_mgt.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(chart_labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(7, true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Legend l = chart_severe_illness_mgt.getLegend();
        l.setFormSize(10f);
        l.setTextSize(12f);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);

        LineData lineData = new LineData(dataSets);
        chart_severe_illness_mgt.setData(lineData);
        chart_severe_illness_mgt.setVisibleXRangeMaximum(10);
        chart_severe_illness_mgt.setExtraOffsets(0f, 5f, 20f, 30f);
        chart_severe_illness_mgt.setPinchZoom(true);
        chart_severe_illness_mgt.invalidate();
    }

    public void setTableData(ArrayList<Float> arrayList, TextView textView){
        for(Float i : arrayList){
            textView.setText(String.valueOf(i));
        }
    }
}