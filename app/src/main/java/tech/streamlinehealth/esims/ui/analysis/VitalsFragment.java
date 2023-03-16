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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;


public class VitalsFragment extends Fragment {
    DataRouter router;
    ArrayList<Float> vitals_measured_list, avpu_days_list, temp_days_list, hrt_rate_days_list,
            systolic_days_list, resp_rate_days_list, oxy_sat_days_list;
    ArrayList<String> chart_labels_vital_signs;
    LineChart chart_vital_signs;
    RequestQueue requestQueue;
    HelperFunctions helperFunctions;
    TextView wk1_label, wk2_label, wk3_label, wk4_label;
    LinearLayout linearLayout;

    public VitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vitals, container, false);

        router = new DataRouter(this.getActivity());
        requestQueue = Volley.newRequestQueue(this.requireActivity());
        helperFunctions = new HelperFunctions(requireActivity());

        vitals_measured_list = new ArrayList<>();
        avpu_days_list = new ArrayList<>();
        temp_days_list = new ArrayList<>();
        hrt_rate_days_list = new ArrayList<>();
        systolic_days_list = new ArrayList<>();
        resp_rate_days_list = new ArrayList<>();
        oxy_sat_days_list = new ArrayList<>();
        chart_labels_vital_signs = new ArrayList<>();
        chart_vital_signs = view.findViewById(R.id.chart_vital_signs);

        linearLayout = view.findViewById(R.id.tableview);

        wk1_label = view.findViewById(R.id.wk1_label);
        wk2_label = view.findViewById(R.id.wk2_label);
        wk3_label = view.findViewById(R.id.wk3_label);
        wk4_label = view.findViewById(R.id.wk4_label);

        getVitalsLineGraphData();
        return view;
    }

    public void getVitalsLineGraphData() {
        String url = router.ip_address + "sims_reports/api/daily_vital_signs_monitoring";
        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                helperFunctions.getJsonArray(response, "vitals_measured_array", vitals_measured_list);
                helperFunctions.getJsonArray(response, "avpu_days_array", avpu_days_list);
                helperFunctions.getJsonArrayWithStringArrayList(response, "chart_labels", chart_labels_vital_signs);
                helperFunctions.getJsonArray(response, "temperature_days_array", temp_days_list);
                helperFunctions.getJsonArray(response, "heart_rate_days_array", hrt_rate_days_list);
                helperFunctions.getJsonArray(response, "systolic_bp_days_array", systolic_days_list);
                helperFunctions.getJsonArray(response, "respiratory_rate_days_array", resp_rate_days_list);
                helperFunctions.getJsonArray(response, "oxygen_saturation_days_array", oxy_sat_days_list);
                plotGraph(vitals_measured_list, avpu_days_list, temp_days_list, hrt_rate_days_list,
                        systolic_days_list, resp_rate_days_list, oxy_sat_days_list);

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
                TableRow tableRow5 = new TableRow(requireActivity());
                TextView title_cell5 = new TextView(requireActivity());
                TableRow tableRow6 = new TableRow(requireActivity());
                TextView title_cell6 = new TextView(requireActivity());

                wk1_label.setText(getString(R.string.wk1) + chart_labels_vital_signs.get(0));
                wk2_label.setText(getString(R.string.wk2)+ chart_labels_vital_signs.get(1));
                wk3_label.setText(getString(R.string.wk3)+ chart_labels_vital_signs.get(2));
                wk4_label.setText(getString(R.string.wk4)+ chart_labels_vital_signs.get(3));

                helperFunctions.createHeaderTableRow(table, table_header, requireActivity());
                table_header.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.colorPrimary));
                helperFunctions.createTableRowVitals(table, tableRow,title_cell, vitals_measured_list, requireActivity(), "3+ vital signs monitored");
                tableRow.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRowVitals(table, tableRow1,title_cell1, avpu_days_list, requireActivity(), "AVPU");
                tableRow1.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                helperFunctions.createTableRowVitals(table, tableRow2,title_cell2, temp_days_list, requireActivity(), "Temperature");
                tableRow2.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRowVitals(table, tableRow3,title_cell3, hrt_rate_days_list, requireActivity(), "Heart Rate");
                tableRow3.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                helperFunctions.createTableRowVitals(table, tableRow4,title_cell4, systolic_days_list, requireActivity(), "BP");
                tableRow4.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));
                helperFunctions.createTableRowVitals(table, tableRow5,title_cell5, resp_rate_days_list, requireActivity(), "Respiratory Rate");
                tableRow5.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_grey2));
                helperFunctions.createTableRowVitals(table, tableRow6,title_cell6, oxy_sat_days_list, requireActivity(), "Sp02");
                tableRow6.setBackgroundColor(ContextCompat.getColor(requireActivity(),R.color.color_blue));

                linearLayout.addView(table);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);
    }

    public void plotGraph(ArrayList<Float> vitals, ArrayList<Float> avpu, ArrayList<Float> temp,
                          ArrayList<Float> hrt_rate, ArrayList<Float> systolic, ArrayList<Float> resp_rate
            , ArrayList<Float> oxy_sat) {
        List<Entry> vitals_entries = new ArrayList<>();
        List<Entry> avpu_entries = new ArrayList<>();
        List<Entry> temp_entries = new ArrayList<>();
        List<Entry> hrt_entries = new ArrayList<>();
        List<Entry> systolic_entries = new ArrayList<>();
        List<Entry> resp_rate_entries = new ArrayList<>();
        List<Entry> oxy_sat_entries = new ArrayList<>();

        helperFunctions.addEntries(vitals, vitals_entries);
        helperFunctions.addEntries(avpu, avpu_entries);
        helperFunctions.addEntries(temp, temp_entries);
        helperFunctions.addEntries(hrt_rate, hrt_entries);
        helperFunctions.addEntries(systolic, systolic_entries);
        helperFunctions.addEntries(resp_rate, resp_rate_entries);
        helperFunctions.addEntries(oxy_sat, oxy_sat_entries);

        LineDataSet dataSet1 = new LineDataSet(vitals_entries, "3+ VS");
        LineDataSet dataSet2 = new LineDataSet(avpu_entries, "AVPU");
        LineDataSet dataSet3 = new LineDataSet(temp_entries, "Temperature");
        LineDataSet dataSet4 = new LineDataSet(hrt_entries, "Heart Rate");
        LineDataSet dataSet5 = new LineDataSet(systolic_entries, "BP");
        LineDataSet dataSet6 = new LineDataSet(resp_rate_entries, "Respiratory Rate");
        LineDataSet dataSet7 = new LineDataSet(oxy_sat_entries, "Sp02");

        helperFunctions.decorateDataSet(dataSet1, R.color.vitals_measured_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet2, R.color.avpu_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet3, R.color.temp_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet4, R.color.hrt_rate_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet5, R.color.systolic_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet6, R.color.resp_rate_color, requireActivity());
        helperFunctions.decorateDataSet(dataSet7, R.color.oxy_sat_color, requireActivity());

        Description desc = chart_vital_signs.getDescription();
        desc.setEnabled(false);


        ArrayList<String> chart_labels = new ArrayList<>(Arrays.asList("WK1", "WK2", "WK3", "WK4"));
        XAxis xAxis = chart_vital_signs.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(chart_labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(7, true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Legend l = chart_vital_signs.getLegend();
        l.setFormSize(10f);
        l.setTextSize(12f);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(5f);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);
        dataSets.add(dataSet4);
        dataSets.add(dataSet5);
        dataSets.add(dataSet6);
        dataSets.add(dataSet7);

        LineData lineData = new LineData(dataSets);
        chart_vital_signs.setData(lineData);
        chart_vital_signs.setVisibleXRangeMaximum(10);
        chart_vital_signs.setExtraOffsets(0f, 5f, 20f, 30f);
        chart_vital_signs.setPinchZoom(true);
        chart_vital_signs.invalidate();
    }

}