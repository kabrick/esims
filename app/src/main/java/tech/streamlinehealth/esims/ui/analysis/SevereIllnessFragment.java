package tech.streamlinehealth.esims.ui.analysis;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;


public class SevereIllnessFragment extends Fragment {
    DataRouter router;
    ArrayList<Float> identified_illness_list, patient_days_list;
    ArrayList<String> chart_labels_for_illness;
    BarChart chart_illness;
    RequestQueue requestQueue;
    HelperFunctions helperFunctions;
    TextView wk1_label, wk2_label, wk3_label, wk4_label;
    LinearLayout linearLayout;

    public SevereIllnessFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_severe_illness, container, false);

        router = new DataRouter(this.getActivity());
        requestQueue = Volley.newRequestQueue(this.requireActivity());
        helperFunctions = new HelperFunctions(requireActivity());

        identified_illness_list = new ArrayList<>();
        patient_days_list = new ArrayList<>();
        chart_labels_for_illness = new ArrayList<>();
        chart_illness = view.findViewById(R.id.chart_illness);

        linearLayout = view.findViewById(R.id.tableview);

        wk1_label = view.findViewById(R.id.wk1_label);
        wk2_label = view.findViewById(R.id.wk2_label);
        wk3_label = view.findViewById(R.id.wk3_label);
        wk4_label = view.findViewById(R.id.wk4_label);

        getBarChartData();

        return view;
    }

    public void getBarChartData() {
        String url = router.ip_address + "sims_reports/api/severe_illness_prevalence";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject graph_obj = response.getJSONObject("graphs");
                    helperFunctions.getJsonArray(graph_obj, "identified_illness_array", identified_illness_list);
                    helperFunctions.getJsonArray(graph_obj, "patient_days_array", patient_days_list);
                    helperFunctions.getJsonArrayWithStringArrayList(graph_obj, "chart_labels", chart_labels_for_illness);
                    plotGraph(identified_illness_list, patient_days_list);

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);
    }

    public void plotGraph(ArrayList<Float> illness_list, ArrayList<Float> patient_days) {

        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();

        for (int i = 0; i < illness_list.size(); i++) {
            entriesGroup1.add(new BarEntry(i, illness_list.get(i)));
            entriesGroup2.add(new BarEntry(i, patient_days.get(i)));
        }

        BarDataSet set1 = new BarDataSet(entriesGroup1, "Patient days with identified severe illness episode");
        BarDataSet set2 = new BarDataSet(entriesGroup2, "Patient days on the ward");
        set2.setColor(Color.MAGENTA);

        Description desc = chart_illness.getDescription();
        desc.setEnabled(false);

        Legend l = chart_illness.getLegend();
        l.setFormSize(10f);
        l.setTextSize(12f);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        float groupSpace = 0.06f;
        float barSpace = 0.02f;
        float barWidth = 0.45f;

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth);
        chart_illness.setData(data);

        ArrayList<String> chart_labels = new ArrayList<>(Arrays.asList("WK1", "WK2", "WK3", "WK4"));
        XAxis xAxis = chart_illness.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(chart_labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        xAxis.setXOffset(10);
        xAxis.setYOffset(2f);
        xAxis.setXOffset(10f);
        xAxis.setSpaceMin(2f);
        xAxis.setDrawLabels(true);
        YAxis leftAxis = chart_illness.getAxisLeft();
        YAxis rightAxis = chart_illness.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        chart_illness.animate();
        chart_illness.setVisibleXRangeMinimum(4);
        chart_illness.setDragEnabled(true);
        chart_illness.groupBars(0, groupSpace, barSpace);
        chart_illness.setVisibleXRangeMaximum(10);
        chart_illness.setExtraOffsets(0f, 5f, 20f, 30f);
        chart_illness.invalidate();
    }



}