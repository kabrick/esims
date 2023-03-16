package tech.streamlinehealth.esims.reports;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class PatientVitalsReportActivity extends AppCompatActivity {

    String patient_id;
    DataRouter router;
    HelperFunctions helperFunctions;

    // create new temporary list values
    List<String> axis_data_sbp_list = new ArrayList<>();
    List<Integer> y_dxis_data_sbp_list = new ArrayList<>();
    List<String> axis_data_dbp_list = new ArrayList<>();
    List<Integer> y_dxis_data_dbp_list = new ArrayList<>();
    List<String> axis_data_heart_rate_list = new ArrayList<>();
    List<Integer> y_dxis_data_heart_rate_list = new ArrayList<>();
    List<String> axis_data_resp_rate_list = new ArrayList<>();
    List<Integer> y_dxis_data_resp_rate_list = new ArrayList<>();
    List<String> axis_data_temperature_list = new ArrayList<>();
    List<Integer> y_dxis_data_temperature_list = new ArrayList<>();
    List<String> axis_data_spo2_list = new ArrayList<>();
    List<Integer> y_dxis_data_spo2_list = new ArrayList<>();
    GraphView chart_blood_pressure, chart_view_heart_rate, chart_view_resp_rate,
            chart_view_temperature, chart_view_spo2;
    TextView vitals_report_label;

    BottomSheetBehavior bottomSheetBehavior;
    Paint paint = new Paint();
    private FirebaseAnalytics firebaseAnalytics;
    long start_time = 0;
    boolean is_timer_off = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_vitals_report);
        start_time = System.currentTimeMillis();

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        patient_id = new PreferenceManager(this).getPatientId();

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayoutVitalsGraph));

        chart_blood_pressure = findViewById(R.id.chart_blood_pressure);
        chart_view_heart_rate = findViewById(R.id.chart_heart_rate);
        chart_view_resp_rate = findViewById(R.id.chart_resp_rate);
        chart_view_temperature = findViewById(R.id.chart_temperature);
        chart_view_spo2 = findViewById(R.id.chart_spo2);
        vitals_report_label = findViewById(R.id.vitals_report_label);
        getPatientDetails();
        getVitals();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.parseColor("#008080"));
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "View Vitals Trends");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "PatientVitalsReportActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    public void getVitals(){
        start_time = System.currentTimeMillis();
        String url = router.ip_address + "sims_patients/get_patient_vitals/" + patient_id + "/" + new PreferenceManager(this).getEpisodeId();

        JsonArrayRequest request = new JsonArrayRequest(url, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonobject = response.getJSONObject(i);

                    @SuppressLint("SimpleDateFormat")
                    String timeAgo = new java.text.SimpleDateFormat("E, dd MMM yyyy HH:mm").format(new java.util.Date(Long.parseLong(jsonobject.getString("timestamp"))));

                    // create new temporary list values
                    axis_data_sbp_list.add(timeAgo);
                    y_dxis_data_sbp_list.add(jsonobject.getInt("systolic_bp"));
                    axis_data_dbp_list.add(timeAgo);
                    y_dxis_data_dbp_list.add(jsonobject.getInt("diastolic_bp"));
                    axis_data_heart_rate_list.add(timeAgo);
                    y_dxis_data_heart_rate_list.add(jsonobject.getInt("heart_rate"));
                    axis_data_resp_rate_list.add(timeAgo);
                    y_dxis_data_resp_rate_list.add(jsonobject.getInt("respiratory_rate"));
                    axis_data_temperature_list.add(timeAgo);
                    y_dxis_data_temperature_list.add(jsonobject.getInt("temperature"));
                    axis_data_spo2_list.add(timeAgo);
                    y_dxis_data_spo2_list.add(jsonobject.getInt("oxygen_saturation"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // convert the lists into arrays for the charts

            String[] axis_data_sbp = axis_data_sbp_list.toArray(new String[0]);
            int[] y_dxis_data_sbp = new int [y_dxis_data_sbp_list.size()];
            for(int i=0; i<y_dxis_data_sbp_list.size(); i++) {
                y_dxis_data_sbp[i] = Integer.parseInt(String.valueOf(y_dxis_data_sbp_list.get(i)));
            }
            String[] axis_data_dbp = axis_data_dbp_list.toArray(new String[0]);
            int[] y_dxis_data_dbp = new int [y_dxis_data_dbp_list.size()];
            for(int i=0; i<y_dxis_data_dbp_list.size(); i++) {
                y_dxis_data_dbp[i] = Integer.parseInt(String.valueOf(y_dxis_data_dbp_list.get(i)));
            }

            String[] axis_data_heart_rate = axis_data_heart_rate_list.toArray(new String[0]);
            int[] y_dxis_data_heart_rate = new int [y_dxis_data_heart_rate_list.size()];
            for(int i=0; i<y_dxis_data_heart_rate_list.size(); i++) {
                y_dxis_data_heart_rate[i] = Integer.parseInt(String.valueOf(y_dxis_data_heart_rate_list.get(i)));
            }

            String[] axis_data_resp_rate = axis_data_resp_rate_list.toArray(new String[0]);
            int[] y_dxis_data_resp_rate = new int [y_dxis_data_resp_rate_list.size()];
            for(int i=0; i<y_dxis_data_resp_rate_list.size(); i++) {
                y_dxis_data_resp_rate[i] = Integer.parseInt(String.valueOf(y_dxis_data_resp_rate_list.get(i)));
            }

            String[] axis_data_temperature = axis_data_temperature_list.toArray(new String[0]);
            int[] y_dxis_data_temperature = new int [y_dxis_data_temperature_list.size()];
            for(int i=0; i<y_dxis_data_temperature_list.size(); i++) {
                y_dxis_data_temperature[i] = Integer.parseInt(String.valueOf(y_dxis_data_temperature_list.get(i)));
            }

            String[] axis_data_spo2 = axis_data_spo2_list.toArray(new String[0]);
            int[] y_dxis_data_spo2 = new int [y_dxis_data_spo2_list.size()];
            for(int i=0; i<y_dxis_data_spo2_list.size(); i++) {
                y_dxis_data_spo2[i] = Integer.parseInt(String.valueOf(y_dxis_data_spo2_list.get(i)));
            }

            plotVitalsBloodPressure(chart_blood_pressure, axis_data_dbp, y_dxis_data_sbp, y_dxis_data_dbp);
            plotVitalsGraph(chart_view_heart_rate, axis_data_heart_rate, y_dxis_data_heart_rate, "Heart Rate", 70);
            plotVitalsGraph(chart_view_resp_rate, axis_data_resp_rate, y_dxis_data_resp_rate, "Resp Rate", 15);
            plotVitalsGraph(chart_view_temperature, axis_data_temperature, y_dxis_data_temperature, "Temperature", 37);
            plotVitalsGraph(chart_view_spo2, axis_data_spo2, y_dxis_data_spo2, "Oxygen Saturation", 98);

            show_graph(1);

        }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    public void plotVitalsBloodPressure(GraphView graph, String[] axisData, int[] yAxisData, int[] yAxisData2){
        start_time = System.currentTimeMillis();
        DataPoint[] data_point = new DataPoint[yAxisData.length];
        DataPoint[] data_point2 = new DataPoint[yAxisData2.length];
        DataPoint[] dataPointBaseline = new DataPoint[yAxisData.length];
        DataPoint[] dataPointBaseline2 = new DataPoint[yAxisData.length];

        for(int i = 0; i < axisData.length; i++){
            data_point[i] = new DataPoint(i, yAxisData[i]);
            dataPointBaseline[i] = new DataPoint(i, 115);
        }

        for(int i = 0; i < axisData.length; i++){
            data_point2[i] = new DataPoint(i, yAxisData2[i]);
            dataPointBaseline2[i] = new DataPoint(i, 70);
        }

        LineGraphSeries<DataPoint> series_baseline = new LineGraphSeries<>(dataPointBaseline);
        graph.addSeries(series_baseline);
        series_baseline.setDrawAsPath(true);
        series_baseline.setCustomPaint(paint);
        series_baseline.setDrawDataPoints(true);
        series_baseline.setTitle("Systolic Baseline");

        LineGraphSeries<DataPoint> series_baseline2 = new LineGraphSeries<>(dataPointBaseline2);
        graph.addSeries(series_baseline2);
        series_baseline2.setDrawAsPath(true);
        series_baseline2.setCustomPaint(paint);
        series_baseline2.setDrawDataPoints(true);
        series_baseline2.setTitle("Diastolic Baseline");

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data_point);
        graph.addSeries(series);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(data_point2);
        graph.addSeries(series2);

        // touch points
        series.setOnDataPointTapListener((series12, dataPoint) -> {
            int date = (int) dataPoint.getX();
            Toast.makeText(PatientVitalsReportActivity.this, "On " + axisData[date] + " value was " + dataPoint.getY(), Toast.LENGTH_LONG).show();
        });
        series.setDrawDataPoints(true);

        series2.setOnDataPointTapListener((series1, dataPoint) -> {
            int date = (int) dataPoint.getX();
            Toast.makeText(PatientVitalsReportActivity.this, "On " + axisData[date] + " value was " + dataPoint.getY(), Toast.LENGTH_LONG).show();
        });
        series2.setDrawDataPoints(true);

        // legend
        series.setTitle("Systolic Blood Pressure");
        series.setColor(Color.parseColor("#66ff66"));
        series2.setTitle("Diastolic Blood Pressure");
        series2.setColor(Color.parseColor("#ff0066"));
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
    }

    public void plotVitalsGraph(GraphView graph, String[] axisData, int[] yAxisData, String legend_title, int baseline){
        start_time = System.currentTimeMillis();
        DataPoint[] dataPoint = new DataPoint[yAxisData.length];
        DataPoint[] dataPointBaseline = new DataPoint[yAxisData.length];

        for(int i = 0; i < axisData.length; i++){
            dataPoint[i] = new DataPoint(i, yAxisData[i]);
            dataPointBaseline[i] = new DataPoint(i, baseline);
        }

        LineGraphSeries<DataPoint> series_baseline = new LineGraphSeries<>(dataPointBaseline);
        graph.addSeries(series_baseline);
        series_baseline.setDrawAsPath(true);
        series_baseline.setCustomPaint(paint);
        series_baseline.setDrawDataPoints(true);
        series_baseline.setTitle("Baseline");

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);
        graph.addSeries(series);

        // touch points
        series.setOnDataPointTapListener((series1, dataPoint1) -> {
            int date = (int) dataPoint1.getX();
            Toast.makeText(PatientVitalsReportActivity.this, "On " + axisData[date] + " value was " + dataPoint1.getY(), Toast.LENGTH_LONG).show();
        });
        series.setDrawDataPoints(true);

        // legend
        series.setTitle(legend_title);
        series.setColor(Color.parseColor("#66ff66"));
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void show_graph(int type){

        switch (type){
            case 1:
                chart_view_spo2.setVisibility(View.GONE);
                chart_view_resp_rate.setVisibility(View.GONE);
                chart_view_heart_rate.setVisibility(View.GONE);
                chart_view_temperature.setVisibility(View.GONE);

                chart_blood_pressure.setVisibility(View.VISIBLE);
                break;
            case 2:
                chart_view_spo2.setVisibility(View.GONE);
                chart_view_resp_rate.setVisibility(View.GONE);
                chart_blood_pressure.setVisibility(View.GONE);
                chart_view_temperature.setVisibility(View.GONE);

                chart_view_heart_rate.setVisibility(View.VISIBLE);
                break;
            case 3:
                chart_view_spo2.setVisibility(View.GONE);
                chart_view_heart_rate.setVisibility(View.GONE);
                chart_blood_pressure.setVisibility(View.GONE);
                chart_view_temperature.setVisibility(View.GONE);

                chart_view_resp_rate.setVisibility(View.VISIBLE);
                break;
            case 4:
                chart_view_resp_rate.setVisibility(View.GONE);
                chart_view_heart_rate.setVisibility(View.GONE);
                chart_blood_pressure.setVisibility(View.GONE);
                chart_view_temperature.setVisibility(View.GONE);

                chart_view_spo2.setVisibility(View.VISIBLE);
                break;
            case 0:
            default:
                chart_view_spo2.setVisibility(View.GONE);
                chart_view_resp_rate.setVisibility(View.GONE);
                chart_view_heart_rate.setVisibility(View.GONE);
                chart_blood_pressure.setVisibility(View.GONE);

                chart_view_temperature.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onClick(View view){
        // return bottomSheet to peekHeight
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        switch (view.getId()){
            case R.id.view_btn:
                show_graph(2);
                is_timer_off = true;
                router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                break;
            case R.id.view_btn2:
                show_graph(5);
                is_timer_off = true;
                router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                break;
            case R.id.view_btn3:
                show_graph(4);
                is_timer_off = true;
                router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                break;
            case R.id.view_btn4:
                show_graph(3);
                is_timer_off = true;
                router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                break;
            case R.id.view_btn5:
                show_graph(1);
                is_timer_off = true;
                router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    private void getPatientDetails(){
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" +  response.getString("gender") + ") " + response.getString("date_of_birth");
                        patient_info_text.setText(text);

                        // change profile for pregnancy
                        if (response.getString("patient_pregnant").equals("Yes")) {
                            ImageView patient_profile_picture = findViewById(R.id.patient_profile_picture);
                            patient_profile_picture.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pregnant));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    protected void onDestroy() {
        if (!is_timer_off && start_time != 0) {
            // here we just check if the timer has been turned off by the login button
            // to indicate that this is a bounce, we use zero instead of the current milli time
            is_timer_off = true;
            router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), "0");
        }else{
            is_timer_off = true;
            router.save_data_entry_time("view_vital_trends", String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
        }
        super.onDestroy();
    }
}