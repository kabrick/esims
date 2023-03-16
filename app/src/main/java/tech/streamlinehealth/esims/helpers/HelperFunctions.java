package tech.streamlinehealth.esims.helpers;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.streamlinehealth.esims.R;

public class HelperFunctions {

    private final Context context;
    private static ProgressDialog progressDialog;
    private final PreferenceManager preferenceManager;

    public HelperFunctions(Context context) {
        this.context = context;
        preferenceManager = new PreferenceManager(context);
    }

    public Map<Integer, String> emergency_signs_prompts = createMap();
    public String age_well_tk = "";

    private static Map<Integer, String> createMap() {
        Map<Integer,String> myMap = new HashMap<>();
        myMap.put(4, "Check oxygen saturation");
        myMap.put(1, "Treat choking patient");
        myMap.put(2, "Give 1:1000 epinephrine (adrenaline) IM – 0.5 ml if 50 kg or above, 0.4 ml if 40 kg, 0.3 if 30 kg");
        myMap.put(3, "Manage airway");
        myMap.put(16, "Give 5 litres of oxygen (10-15 litres/min if critically ill");
        myMap.put(7, "Help patient assume position of comfort");
        myMap.put(5, "Assist Ventilation with bag valve mask");
        myMap.put(6, "Give naloxone for opiate overdose");
        myMap.put(8, "Give salbutamol");
        myMap.put(18, "Keep warm (cover)");
        myMap.put(17, "Insert IV, give 1 litre bolus crystalloid (LR or NS) then reassess (see give fluids rapidly)");
        myMap.put(19, "Give 1:1000 epinephrine (adrenaline) IM – 0.5 ml if 50 kg or above, 0.4 ml if 40 kg, 0.3 if 30 kg");
        myMap.put(20, "Check AVPU (GCS in trauma)");
        myMap.put(21, "Check serum glucose");
        myMap.put(22, "Check for head trauma");
        myMap.put(23, "Call for help but do not leave patient alone");
        myMap.put(24, "Give naloxone for opiate overdose");
        myMap.put(25, "Give glucose (if blood glucose is low or unknown).");
        myMap.put(26, "Give thiamine and glucose");
        myMap.put(27, "Check (then monitor and record) level of consciousness on AVPU scale");
        myMap.put(9, "Give diazepam IV or rectally");
        myMap.put(11, "Consider Checking and recording SBP, pulse, RR, temperature if not yet done");
        myMap.put(12, "Continue to monitor airway, breathing, circulation.");
        myMap.put(13, "Give second dose diazepam (unless pregnant/post-partum)");
        myMap.put(14, "Consult district clinician to start phenytoin");
        myMap.put(34, "Nothing by mouth (NPO)");
        myMap.put(33, "Give IV fluids");
        myMap.put(29, "Call for help; send blood for type and cross match");
        myMap.put(31, "Empirical antibiotics IV/IM");
        myMap.put(30, "Treat pain");
        myMap.put(36, "Give IV antibiotics (call clinician to do LP first if can do within 15 minutes)");
        myMap.put(37, "Give appropriate IV or IM antimalarials if in malaria endemic area or travel and RDT or blood smear positive");
        myMap.put(38, "Give aspirin (300 mg, chewed)");
        myMap.put(39, "Give oxygen");
        myMap.put(40, "Insert IV – if no signs of shock, give fluids slowly at a keep-open rate");
        myMap.put(41, "Give morphine for pain");
        myMap.put(42, "Do ECG. Call district clinician for help");
        myMap.put(43, "Manage airway");
        myMap.put(44, "Consider inhalational burn");
        myMap.put(46, "Insert IV; give fluids rapidly");
        myMap.put(47, "Treat pain");
        myMap.put(48, "Apply clean sterile bandages");
        myMap.put(52, "Immobilize extremity");
        myMap.put(51, "Insert IV; give fluids rapidly");
        myMap.put(49, "Treat pain");
        return myMap;
    }

    public String[] wards = {"Children", "Medical Upper","Unknown"};

    public String[] outcomes = {"Discharged", "Died"};

    public String[] actions = {"Diazepam given",
            "Oxygen started",
            "Oxygen saturation measured",
            "Recovery position",
            "Glucose checked and given",
            "Sp02 measured",
            "IV started",
            "If wheezing, salbutamol given",
            "Broad Spectrum antibiotics",
            "If malaria possible, antimalarials given",
            "Antibiotics given",
            "3 litres IV fluids",
            "Rapid fluids started",
            "1000 ml fluid bolus",
            "Treat choking patient",
            "Give 1:1000 epinephrine (adrenaline) IM – 0.5 ml if 50 kg or above, 0.4 ml if 40 kg, 0.3 if 30 kg",
            "Assist Ventilation with bag valve mask",
            "Assist Ventilation and give naloxone",
            "Give salbutamol",
            "Give 5 litres of oxygen",
            "Insert IV, give 1 litre bolus crystalloid (LR or NS) then reassess (see give fluids rapidly. Keep warm (cover)"
    };

    public String[] districts = {"Rukungiri",
            "Kanungu", "Kabale", "Mbarara", "Bushenyi",
            "Ntungamo", "Kibale", "Isingiro", "Mitooma",
            "Kamwenge", "Rubirizi", "Kirihura", "Sheema",
            "Lyantonde", "Wakiso", "Ttitwe", "Buhweju",
            "Kyegegwa", "Kisoro", "Lwatonde", "Mubende",
            "Sembabule", "Rubanda", "Ibanda", "Hamurwa",
            "Kagadi", "Kamwengye", "Rakai", "Kabarole",
            "Kyenjojo", "Kampala", "Lira", "Rwengo",
            "Lwengo", "Kyankwanzi", "kiruhura", "Kakumiro",
            "Busia", "Tororo"};

    public void genericDialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setMessage(message).setPositiveButton("Okay", (dialogInterface, i) -> {
            //
        }).show();
    }

    public void genericProgressBar(String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void stopProgressBar(){
        progressDialog.cancel();
    }

    public void displayNotification(Context mContext, String title, String text){
        Notification notification = new NotificationCompat.Builder(mContext, "esims")
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.esims_logo)
                .setColor(mContext.getColor(R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        // create the notification channel
        int id = preferenceManager.getNotificationCounter();
        preferenceManager.setNotificationCounter(id + 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("esims", "esims_notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(id, notification);
        } else {
            // for lower builds
            NotificationManagerCompat manager = NotificationManagerCompat.from(mContext.getApplicationContext());
            manager.notify(id, notification);
        }
    }

    public String capitalize_first_letter(String string){
        StringBuilder result = new StringBuilder(string.length());
        String[] words = string.split(" ");

        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return result.toString();
    }

    /**
     * retrieves a JsonArray, iterate it and add it to a Float ArrayList
     *
     * @param response  response object in Json
     * @param value     name of field in json
     * @param arrayList Float ArrayList
     * @throws JSONException Exception thrown
     */
    public void getJsonArray(JSONObject response, String value, ArrayList<Float> arrayList) throws JSONException {
        JSONArray jsonArray = response.getJSONArray(value);
        for (int i = 0; i < jsonArray.length(); i++) {
            int a = jsonArray.getInt(i);
            arrayList.add((float) a);
        }
    }

    /**
     * retrieves a JsonArray, iterate it and add it to a String ArrayList
     *
     * @param response  response object in Json
     * @param value     name of field in json
     * @param arrayList string ArrayList
     * @throws JSONException Exception thrown
     */
    public void getJsonArrayWithStringArrayList(JSONObject response, String value, ArrayList<String> arrayList) throws JSONException {
        JSONArray jsonArray = response.getJSONArray(value);
        for (int i = 0; i < jsonArray.length(); i++) {
            String a = jsonArray.getString(i);
            arrayList.add(a);
        }
    }

    /**
     * adds an entry through iterating arrayList
     *
     * @param arrayList arrayList
     * @param entries   entry list object
     */
    public void addEntries(ArrayList<Float> arrayList, List<Entry> entries) {
        for (int i = 0; i < arrayList.size(); i++) {
            entries.add(new Entry(i, arrayList.get(i)));
        }

    }

    /**
     * decorates specified dataSet
     *
     * @param dataSet dataSet given
     * @param color   color to be set
     */
    public void decorateDataSet(LineDataSet dataSet, @ColorRes int color, Context context ) {
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setDrawValues(false);
        dataSet.setCircleRadius(5);
        dataSet.setCircleHoleRadius(1);
        dataSet.setColor(ContextCompat.getColor(context, color));

    }

    /**
     * converts jsonArray to Arraylist
     *
     * @param jsonArray jsonArray to convert
     * @param arrayList Arraylist created
     * @throws JSONException type of exception thrown
     */
    public void JSONArrayToArrayList(JSONArray jsonArray,  ArrayList<Integer> arrayList) throws JSONException {
        if(!(jsonArray == null)) {
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add((Integer) jsonArray.get(i));
            }
        }
    }

    /**
     * add table row
     *
     * @param tableLayout layout for the table
     * @param tableRow table row to be added
     * @param cellTitle row title
     * @param arrayList arrayList to use for populating the table row
     * @param context context of the Activity
     * @param title value of row title
     */
    public void createTableRow(TableLayout tableLayout, TableRow tableRow, TextView cellTitle,
                               ArrayList<Integer> arrayList
                              ,Context context, String title){
        cellTitle.setText(title);
        cellTitle.setPadding(6, 4, 6, 4);
        tableRow.addView(cellTitle);
        for(int j=0; j < arrayList.size(); j++){
            TextView cell = new TextView(context);
            cell.setText(String.valueOf(arrayList.get(j)));
            cell.setPadding(6, 4, 6, 4);
            cell.setGravity(Gravity.CENTER);
            tableRow.addView(cell);
        }
        tableLayout.addView(tableRow);
    }

    /**
     * add table row
     *
     * @param tableLayout layout for the table
     * @param tableRow table row to be added
     * @param cellTitle row title
     * @param arrayList arrayList to use for populating the table row
     * @param context context of the Activity
     * @param title value of row title
     */
    public void createTableRowVitals(TableLayout tableLayout, TableRow tableRow, TextView cellTitle,
                               ArrayList<Float> arrayList
            ,Context context, String title){
        cellTitle.setText(title);
        cellTitle.setPadding(6, 4, 6, 4);
        tableRow.addView(cellTitle);
        for(int j=0; j < arrayList.size(); j++){
            TextView cell = new TextView(context);
            cell.setText(String.valueOf(arrayList.get(j)));
            cell.setPadding(6, 4, 6, 4);
            cell.setGravity(Gravity.CENTER);
            tableRow.addView(cell);
        }
        tableLayout.addView(tableRow);
    }


    /**
     * create a header row
     *
     * @param tableLayout layout of the row
     * @param tableRow header row
     * @param context context of the activity
     */
    public void createHeaderTableRow(TableLayout tableLayout, TableRow tableRow, Context context){
        TextView cell1 = new TextView(context);
        cell1.setText("");
        tableRow.addView(cell1);
        TextView cell2 = new TextView(context);
        cell2.setPadding(6, 4, 6, 4);
        cell2.setText(context.getString(R.string.wk1_label));
        tableRow.addView(cell2);
        TextView cell3 = new TextView(context);
        cell3.setText(context.getString(R.string.wk2_label));
        cell3.setPadding(6, 4, 6, 4);
        tableRow.addView(cell3);
        TextView cell4 = new TextView(context);
        cell4.setText(context.getString(R.string.wk3_label));
        cell4.setPadding(6, 4, 6, 4);
        tableRow.addView(cell4);
        TextView cell5 = new TextView(context);
        cell5.setText(context.getString(R.string.wk4_label));
        cell5.setPadding(6, 4, 6, 4);
        tableRow.addView(cell5);
        tableLayout.addView(tableRow);

    }
}
