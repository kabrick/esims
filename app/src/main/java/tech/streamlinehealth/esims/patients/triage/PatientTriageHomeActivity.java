package tech.streamlinehealth.esims.patients.triage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.transition.TransitionManager;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.patients.PatientHomeActivity;

public class PatientTriageHomeActivity extends AppCompatActivity {

    LinearLayout dropdown_view_one, dropdown_view_two, dropdown_view_three, dropdown_view_four, dropdown_view_five,
            action_prompt1, action_prompt2, action_prompt3, action_prompt4, action_prompt5, action_prompt6, action_prompt7,
            action_prompt16, action_prompt17, action_prompt19, action_prompt8,action_prompt_new_17, action_prompt18, action_prompt_new, action_prompt_new1, action_prompt_25,
            action_prompt9, action_prompt11, action_prompt12, action_prompt20, action_prompt21, action_prompt22, action_prompt23, action_prompt24, action_prompt26,
            action_prompt27, action_prompt29, action_prompt30, action_prompt31, action_prompt33, action_prompt34, action_prompt36, action_prompt37, action_prompt38, action_prompt39,
            action_prompt40, action_prompt41, action_prompt42, action_prompt43, action_prompt44, action_prompt46, action_prompt47,
            action_prompt48, action_prompt49, action_prompt51, action_prompt52, action_prompt13, action_prompt14;
    TextView action_prompt1_textview, action_prompt2_textview, action_prompt3_textview, action_prompt4_textview, action_prompt5_textview, action_prompt6_textview, action_prompt7_textview,
            action_prompt9_textview, action_prompt11_textview, action_prompt12_textview, action_prompt20_textview, action_prompt21_textview, action_prompt22_textview, action_prompt23_textview, action_prompt24_textview, action_prompt26_textview,
            action_prompt27_textview, action_prompt29_textview, action_prompt30_textview, action_prompt31_textview, action_prompt33_textview, action_prompt34_textview, action_prompt36_textview, action_prompt37_textview, action_prompt38_textview, action_prompt39_textview,
            action_prompt40_textview, action_prompt41_textview, action_prompt42_textview, action_prompt43_textview, action_prompt44_textview, action_prompt46_textview, action_prompt47_textview,
            action_prompt48_textview, action_prompt49_textview, action_prompt51_textview, action_prompt52_textview, action_prompt16_textview, action_prompt17_textview, action_prompt_new_17_textview, action_prompt19_textview, action_prompt8_textview,
            action_prompt13_textview, action_prompt14_textview, prompt_label, label, label2, label3,label4, section_one_header, section_two_header, section_three_header, action_prompt18_textview, action_prompt_new_textview, action_prompt_new1_textview
            , action_prompt25_textview;
    CheckBox action_prompt1_checkbox, action_prompt2_checkbox, action_prompt3_checkbox, action_prompt4_checkbox, action_prompt5_checkbox, action_prompt6_checkbox, action_prompt7_checkbox,
            action_prompt9_checkbox, action_prompt11_checkbox, action_prompt12_checkbox, action_prompt20_checkbox, action_prompt21_checkbox, action_prompt22_checkbox, action_prompt23_checkbox, action_prompt24_checkbox, action_prompt26_checkbox,
            action_prompt27_checkbox, action_prompt29_checkbox, action_prompt30_checkbox, action_prompt31_checkbox, action_prompt33_checkbox, action_prompt34_checkbox, action_prompt36_checkbox, action_prompt37_checkbox, action_prompt38_checkbox, action_prompt39_checkbox,
            action_prompt40_checkbox, action_prompt41_checkbox, action_prompt42_checkbox, action_prompt43_checkbox, action_prompt44_checkbox, action_prompt46_checkbox, action_prompt47_checkbox,
            action_prompt48_checkbox, action_prompt49_checkbox, action_prompt51_checkbox, action_prompt16_checkbox, action_prompt17_checkbox, action_prompt_new_17_checkbox, action_prompt19_checkbox, action_prompt8_checkbox,
            action_prompt13_checkbox, action_prompt14_checkbox, action_prompt18_checkbox, action_prompt_new_checkbox, action_prompt_new1_checkbox , action_prompt25_checkbox, action_prompt52_checkbox;
    HelperFunctions helperFunctions;
    DataRouter router;
    String patient_id, episode_id, patient_pregnant;
    String[] checks = new String[25];
    String[] priority_signs_checks = new String[15];
    String[] emergency_prompts = new String[62];
    CheckBox emergency_check1, emergency_check2, emergency_check3, emergency_check_none,
            emergency_check4, emergency_check5, emergency_check6, emergency_check7, emergency_check_none_two,
            emergency_check8, emergency_check9, emergency_check10, emergency_check_none_three, emergency_check_none_four,
            emergency_check11, emergency_check12, emergency_check13, emergency_check14, emergency_check15, emergency_check16, emergency_check17, emergency_check18, emergency_check19,
            priority_signs1, priority_signs2, priority_signs3, priority_signs4, priority_signs5,
            priority_signs6, priority_signs7, priority_signs8, priority_signs9, priority_signs10, priority_signs11,
            priority_signs12, priority_signs13, priority_signs14, priority_signs15, trauma_check_airway,
            trauma_check_circulation, trauma_check_disability, trauma_check_expose1, trauma_check_expose2, trauma_check_expose3;
    BottomSheetBehavior bottomSheetBehavior, bottomSheetBehaviorTrauma;
    boolean isBottomSheetUp = false;
    boolean isBottomSheetUpTrauma = false;
    boolean isRequestFromPatientHome = false;
    TableRow row1, row2, row5, row6, row8, row10, row13, row15, row16, row18, row19,
            row24, row25, row26, row28, row29, row32, row33, row35, row39, row45, row50, row52;
    View row_line5, row_line6, row_line8, row_line15, row_line16, row_line18, row_line19,row_line24, row_line25, row_line27, row_line28, row_line29,
            row_line31, row_line32, row_line34,row_line38, row_line44, row_line49;
    RadioButton radio1_yes, radio1_no, radio1_not, radio2_yes, radio2_no, radio2_not,
            radio5_yes, radio5_no, radio5_not, radio6_yes, radio6_no, radio6_not, radio8_yes, radio8_no, radio8_not,
            radio10_yes, radio10_no, radio10_not, radio13_yes, radio13_no, radio13_not, radio15_yes, radio15_no, radio15_not, radio29_yes, radio29_no, radio29_not,
            radio16_yes, radio16_no, radio16_not, radio17_yes, radio17_no, radio17_not, radio18_yes, radio18_no, radio18_not, radio19_yes, radio19_no, radio19_not,
            radio24_yes, radio24_no, radio24_not, radio25_yes, radio25_no, radio25_not, radio26_yes, radio26_no, radio26_not, radio28_yes, radio28_no, radio28_not,
            radio32_yes, radio32_no, radio32_not, radio33_yes, radio33_no, radio33_not, radio35_yes, radio35_no, radio35_not, radio39_yes, radio39_no, radio39_not,
            radio45_yes, radio45_no, radio45_not, radio50_yes, radio50_no, radio50_not, radio52_yes, radio52_no, radio52_not;
    int emergency_prompts_counter = 0;
    int numberPatientTraumaPresent = 0;
    Set<String> emergency_actions = new HashSet<>();
    Set<String> trauma_actions = new HashSet<>();
    View emergency_signs_number_one_trauma_line, emergency_signs_number_two_trauma_line,
            emergency_signs_number_three_trauma_line, emergency_signs_number_four_trauma_line;
    ImageView emergency_signs_number_one_trauma_image, emergency_signs_number_two_trauma_image,
            emergency_signs_number_three_trauma_image, emergency_signs_number_four_trauma_image,
            emergency_signs_number_one, emergency_signs_number_two, emergency_signs_number_three, emergency_signs_number_four;

    // create new map to store the completed_at timestamps for the emergency_actions
    HashMap<String, Long> emergency_actions_timestamps_map = new HashMap<>();
    HashMap<String, Long> emergency_actions_triggered_timestamps_map = new HashMap<>();
    HashMap<String, Long> trauma_timestamps_map = new HashMap<>();

    LinearLayout action_trauma, action_trauma1, action_trauma2, action_trauma7, action_trauma8, action_trauma9,
            action_trauma10, action_trauma11, action_trauma12, action_trauma13, action_trauma14, action_trauma15, action_trauma16,
            action_trauma17, action_trauma18, action_trauma19, action_trauma20, action_trauma21, action_trauma22, action_trauma23,
            action_trauma24, action_trauma25, action_trauma26, action_trauma27, action_trauma28, action_trauma29;
    TextView action_trauma_textview, action_trauma1_textview, action_trauma2_textview, action_trauma7_textview,
            action_trauma8_textview, action_trauma9_textview, action_trauma10_textview, action_trauma11_textview, action_trauma12_textview,
            action_trauma13_textview, action_trauma14_textview, action_trauma15_textview, action_trauma16_textview, action_trauma17_textview,
            action_trauma18_textview, action_trauma19_textview, action_trauma20_textview, action_trauma21_textview, action_trauma22_textview,
            action_trauma23_textview, action_trauma24_textview, action_trauma25_textview, action_trauma26_textview, action_trauma27_textview, action_trauma28_textview, action_trauma29_textview;
    CheckBox action_trauma_checkbox, action_trauma1_checkbox, action_trauma2_checkbox, action_trauma7_checkbox,
            action_trauma8_checkbox, action_trauma9_checkbox, action_trauma10_checkbox, action_trauma11_checkbox, action_trauma12_checkbox,
            action_trauma13_checkbox, action_trauma14_checkbox, action_trauma15_checkbox, action_trauma16_checkbox, action_trauma17_checkbox,
            action_trauma18_checkbox, action_trauma19_checkbox, action_trauma20_checkbox, action_trauma21_checkbox, action_trauma22_checkbox,
            action_trauma23_checkbox, action_trauma24_checkbox, action_trauma25_checkbox, action_trauma26_checkbox, action_trauma27_checkbox, action_trauma28_checkbox, action_trauma29_checkbox;
    View row_line_trauma1, row_line_trauma2, row_line_trauma3, row_line_trauma4,
            row_line_trauma5, row_line_trauma11, row_line_trauma12,
            row_line_trauma23, row_line_trauma26, row_line_trauma28;
    TableRow row_trauma2, row_trauma3, row_trauma4, row_trauma5, row_trauma6, row_trauma12, row_trauma13, row_trauma23, row_trauma24,
            row_trauma27, row_trauma29;
    RadioButton radio_trauma2_yes, radio_trauma2_no, radio_trauma2_not, radio_trauma3_yes, radio_trauma3_no, radio_trauma3_not,
            radio_trauma4_yes, radio_trauma4_no, radio_trauma4_not, radio_trauma5_yes, radio_trauma5_no, radio_trauma5_not,
            radio_trauma6_yes, radio_trauma6_no, radio_trauma6_not, radio_trauma12_yes, radio_trauma12_no, radio_trauma12_not,
            radio_trauma13_yes, radio_trauma13_no, radio_trauma13_not, radio_trauma23_yes, radio_trauma23_no, radio_trauma23_not,
            radio_trauma24_yes, radio_trauma24_no, radio_trauma24_not, radio_trauma27_yes, radio_trauma27_no, radio_trauma27_not,
            radio_trauma29_yes, radio_trauma29_no, radio_trauma29_not;
    ExtendedFloatingActionButton fab_review_triage;
    String systolic_bp = "";
    String diastolic_bp = "";
    String heart_rate = "";
    String resp_rate = "";
    String temperature = "";
    String oxygen_saturation  = "";
    String avpu  = "";
    CardView parent_trans_five;
    LinearLayout section_prompt, section_prompt1, section_prompt2, section_prompt3;
    TextView section_prompt_textview, section_prompt1_textview, section_prompt2_textview, section_prompt3_textview;
    CheckBox section_prompt_checkbox, section_prompt1_checkbox, section_prompt2_checkbox, section_prompt3_checkbox;

    ImageView caution, caution1, caution2, caution3, caution4, caution5, caution6, caution7, caution8, caution9,
    caution10, caution11, caution12, caution13, caution14, caution15, caution16, caution17, caution18,
            caution19, caution20, caution21, caution22, caution23, caution24, caution25, caution26,
            caution_1, caution_2, caution_3, caution_4, caution_5, caution_6, caution_7, caution_8
            , caution_9, caution_10, caution_new_10, caution_11, caution_12, caution_13, caution_14, caution_15, caution_16
            ,caution_17, caution_18, caution_19, caution_20, caution_21, caution_22, caution_23, caution_24
            ,caution_25, caution_26, caution_27, caution_28, caution_29, caution_30, caution_31,caution_32,caution_33,
            caution_34, caution_35, caution_36, caution_37, caution_38, caution_39, section_prompt_imageview, section_prompt1_imageview, section_prompt2_imageview,
            section_prompt3_imageview, section_prompt4_imageview, section_prompt5_imageview, caution_new, caution_new1, caution_new3, caution_40, caution_41;

    private static final String TAG = "PatientTriageHomeActivi";
    long start_time = 0, start_time_airway_and_breathing = 0, start_time_circulation = 0, start_time_disability = 0, start_time_expose = 0;
    LinearLayout circulation;
    boolean airway_start_status = false, circulation_start_status = false, disability_start_status = false, expose_start_status = false ;


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_triage_home);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isRequestFromPatientHome = extras.getBoolean("isRequestFromPatientHome", false);
        }

        // initial fill arrays with 0 value
        Arrays.fill(checks, "0");
        Arrays.fill(priority_signs_checks, "0");
        Arrays.fill(emergency_prompts, "0");

        router = new DataRouter(this);
        helperFunctions = new HelperFunctions(this);

        patient_id = new PreferenceManager(this).getPatientId();
        episode_id = new PreferenceManager(this).getEpisodeId();

        parent_trans_five = findViewById(R.id.parent_trans_five);

        getPatientVitals();

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
        bottomSheetBehaviorTrauma = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayoutTrauma));

        fab_review_triage = findViewById(R.id.fab_review_triage);

        fab_review_triage.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(PatientTriageHomeActivity.this);
            alert.setMessage("This action will create a new triage record. Continue?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String network_address = router.ip_address + "sims_patients/create_triage_record/" + patient_id + "/" + episode_id;

                    helperFunctions.genericProgressBar("Preparing to review triage...");

                    StringRequest request = new StringRequest(Request.Method.GET, network_address,
                            response -> {
                                helperFunctions.stopProgressBar();
                                if (!response.equals("0")){
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            }, error -> {
                        helperFunctions.stopProgressBar();
                        helperFunctions.genericDialog("Something went wrong. Please try again later.");
                    });

                    Volley.newRequestQueue(PatientTriageHomeActivity.this).add(request);
                }
            }).setNegativeButton("Cancel", null).show();
        });

        emergency_signs_number_one = findViewById(R.id.emergency_signs_number_one);
        emergency_signs_number_two = findViewById(R.id.emergency_signs_number_two);
        emergency_signs_number_three = findViewById(R.id.emergency_signs_number_three);
        emergency_signs_number_four = findViewById(R.id.emergency_signs_number_four);
        emergency_signs_number_one_trauma_line = findViewById(R.id.emergency_signs_number_one_trauma_line);
        emergency_signs_number_two_trauma_line = findViewById(R.id.emergency_signs_number_two_trauma_line);
        emergency_signs_number_three_trauma_line = findViewById(R.id.emergency_signs_number_three_trauma_line);
        emergency_signs_number_four_trauma_line = findViewById(R.id.emergency_signs_number_four_trauma_line);
        emergency_signs_number_one_trauma_image = findViewById(R.id.emergency_signs_number_one_trauma_image);
        emergency_signs_number_two_trauma_image = findViewById(R.id.emergency_signs_number_two_trauma_image);
        emergency_signs_number_three_trauma_image = findViewById(R.id.emergency_signs_number_three_trauma_image);
        emergency_signs_number_four_trauma_image = findViewById(R.id.emergency_signs_number_four_trauma_image);

        findViews();

        emergency_actions_timestamps_map.put("1", (long) 0);
        emergency_actions_timestamps_map.put("2", (long) 0);
        emergency_actions_timestamps_map.put("3", (long) 0);
        emergency_actions_timestamps_map.put("4", (long) 0);
        emergency_actions_timestamps_map.put("5", (long) 0);
        emergency_actions_timestamps_map.put("6", (long) 0);
        emergency_actions_timestamps_map.put("7", (long) 0);
        emergency_actions_timestamps_map.put("8", (long) 0);
        emergency_actions_timestamps_map.put("9", (long) 0);
        emergency_actions_timestamps_map.put("10", (long) 0);
        emergency_actions_timestamps_map.put("11", (long) 0);
        emergency_actions_timestamps_map.put("12", (long) 0);
        emergency_actions_timestamps_map.put("13", (long) 0);
        emergency_actions_timestamps_map.put("14", (long) 0);
        emergency_actions_timestamps_map.put("15", (long) 0);
        emergency_actions_timestamps_map.put("16", (long) 0);
        emergency_actions_timestamps_map.put("17", (long) 0);
        emergency_actions_timestamps_map.put("18", (long) 0);
        emergency_actions_timestamps_map.put("19", (long) 0);
        emergency_actions_timestamps_map.put("20", (long) 0);
        emergency_actions_timestamps_map.put("21", (long) 0);
        emergency_actions_timestamps_map.put("22", (long) 0);
        emergency_actions_timestamps_map.put("23", (long) 0);
        emergency_actions_timestamps_map.put("24", (long) 0);
        emergency_actions_timestamps_map.put("26", (long) 0);
        emergency_actions_timestamps_map.put("27", (long) 0);
        emergency_actions_timestamps_map.put("29", (long) 0);
        emergency_actions_timestamps_map.put("30", (long) 0);
        emergency_actions_timestamps_map.put("31", (long) 0);
        emergency_actions_timestamps_map.put("32", (long) 0);
        emergency_actions_timestamps_map.put("33", (long) 0);
        emergency_actions_timestamps_map.put("34", (long) 0);
        emergency_actions_timestamps_map.put("36", (long) 0);
        emergency_actions_timestamps_map.put("37", (long) 0);
        emergency_actions_timestamps_map.put("38", (long) 0);
        emergency_actions_timestamps_map.put("39", (long) 0);
        emergency_actions_timestamps_map.put("40", (long) 0);
        emergency_actions_timestamps_map.put("41", (long) 0);
        emergency_actions_timestamps_map.put("42", (long) 0);
        emergency_actions_timestamps_map.put("43", (long) 0);
        emergency_actions_timestamps_map.put("44", (long) 0);
        emergency_actions_timestamps_map.put("46", (long) 0);
        emergency_actions_timestamps_map.put("47", (long) 0);
        emergency_actions_timestamps_map.put("48", (long) 0);
        emergency_actions_timestamps_map.put("49", (long) 0);
        emergency_actions_timestamps_map.put("51", (long) 0);
        emergency_actions_timestamps_map.put("52", (long) 0);
        emergency_actions_timestamps_map.put("53", (long) 0);
        emergency_actions_timestamps_map.put("54", (long) 0);
        emergency_actions_timestamps_map.put("55", (long) 0);
        emergency_actions_timestamps_map.put("56", (long) 0);
        emergency_actions_timestamps_map.put("57", (long) 0);
        emergency_actions_timestamps_map.put("58", (long) 0);
        emergency_actions_timestamps_map.put("59", (long) 0);

        emergency_actions_triggered_timestamps_map.put("1", (long) 0);
        emergency_actions_triggered_timestamps_map.put("2", (long) 0);
        emergency_actions_triggered_timestamps_map.put("3", (long) 0);
        emergency_actions_triggered_timestamps_map.put("4", (long) 0);
        emergency_actions_triggered_timestamps_map.put("5", (long) 0);
        emergency_actions_triggered_timestamps_map.put("6", (long) 0);
        emergency_actions_triggered_timestamps_map.put("7", (long) 0);
        emergency_actions_triggered_timestamps_map.put("8", (long) 0);
        emergency_actions_triggered_timestamps_map.put("9", (long) 0);
        emergency_actions_triggered_timestamps_map.put("10", (long) 0);
        emergency_actions_triggered_timestamps_map.put("11", (long) 0);
        emergency_actions_triggered_timestamps_map.put("12", (long) 0);
        emergency_actions_triggered_timestamps_map.put("13", (long) 0);
        emergency_actions_triggered_timestamps_map.put("14", (long) 0);
        emergency_actions_triggered_timestamps_map.put("15", (long) 0);
        emergency_actions_triggered_timestamps_map.put("16", (long) 0);
        emergency_actions_triggered_timestamps_map.put("17", (long) 0);
        emergency_actions_triggered_timestamps_map.put("18", (long) 0);
        emergency_actions_triggered_timestamps_map.put("19", (long) 0);
        emergency_actions_triggered_timestamps_map.put("20", (long) 0);
        emergency_actions_triggered_timestamps_map.put("21", (long) 0);
        emergency_actions_triggered_timestamps_map.put("22", (long) 0);
        emergency_actions_triggered_timestamps_map.put("23", (long) 0);
        emergency_actions_triggered_timestamps_map.put("24", (long) 0);
        emergency_actions_triggered_timestamps_map.put("26", (long) 0);
        emergency_actions_triggered_timestamps_map.put("27", (long) 0);
        emergency_actions_triggered_timestamps_map.put("29", (long) 0);
        emergency_actions_triggered_timestamps_map.put("30", (long) 0);
        emergency_actions_triggered_timestamps_map.put("31", (long) 0);
        emergency_actions_triggered_timestamps_map.put("32", (long) 0);
        emergency_actions_triggered_timestamps_map.put("33", (long) 0);
        emergency_actions_triggered_timestamps_map.put("34", (long) 0);
        emergency_actions_triggered_timestamps_map.put("36", (long) 0);
        emergency_actions_triggered_timestamps_map.put("37", (long) 0);
        emergency_actions_triggered_timestamps_map.put("38", (long) 0);
        emergency_actions_triggered_timestamps_map.put("39", (long) 0);
        emergency_actions_triggered_timestamps_map.put("40", (long) 0);
        emergency_actions_triggered_timestamps_map.put("41", (long) 0);
        emergency_actions_triggered_timestamps_map.put("42", (long) 0);
        emergency_actions_triggered_timestamps_map.put("43", (long) 0);
        emergency_actions_triggered_timestamps_map.put("44", (long) 0);
        emergency_actions_triggered_timestamps_map.put("46", (long) 0);
        emergency_actions_triggered_timestamps_map.put("47", (long) 0);
        emergency_actions_triggered_timestamps_map.put("48", (long) 0);
        emergency_actions_triggered_timestamps_map.put("49", (long) 0);
        emergency_actions_triggered_timestamps_map.put("51", (long) 0);
        emergency_actions_triggered_timestamps_map.put("52", (long) 0);
        emergency_actions_triggered_timestamps_map.put("53", (long) 0);
        emergency_actions_triggered_timestamps_map.put("54", (long) 0);
        emergency_actions_triggered_timestamps_map.put("55", (long) 0);
        emergency_actions_triggered_timestamps_map.put("56", (long) 0);
        emergency_actions_triggered_timestamps_map.put("57", (long) 0);
        emergency_actions_triggered_timestamps_map.put("58", (long) 0);
        emergency_actions_triggered_timestamps_map.put("59", (long) 0);

        trauma_timestamps_map.put("0", (long) 0);
        trauma_timestamps_map.put("1", (long) 0);
        trauma_timestamps_map.put("2", (long) 0);
        trauma_timestamps_map.put("7", (long) 0);
        trauma_timestamps_map.put("8", (long) 0);
        trauma_timestamps_map.put("9", (long) 0);
        trauma_timestamps_map.put("10", (long) 0);
        trauma_timestamps_map.put("11", (long) 0);
        trauma_timestamps_map.put("12", (long) 0);
        trauma_timestamps_map.put("13", (long) 0);
        trauma_timestamps_map.put("14", (long) 0);
        trauma_timestamps_map.put("15", (long) 0);
        trauma_timestamps_map.put("16", (long) 0);
        trauma_timestamps_map.put("17", (long) 0);
        trauma_timestamps_map.put("18", (long) 0);
        trauma_timestamps_map.put("19", (long) 0);
        trauma_timestamps_map.put("20", (long) 0);
        trauma_timestamps_map.put("21", (long) 0);
        trauma_timestamps_map.put("22", (long) 0);
        trauma_timestamps_map.put("23", (long) 0);
        trauma_timestamps_map.put("24", (long) 0);
        trauma_timestamps_map.put("25", (long) 0);
        trauma_timestamps_map.put("26", (long) 0);
        trauma_timestamps_map.put("27", (long) 0);
        trauma_timestamps_map.put("28", (long) 0);
        trauma_timestamps_map.put("29", (long) 0);

        caution1 = findViewById(R.id.caution1);
        caution_1 = findViewById(R.id.caution_1);
        caution_2 = findViewById(R.id.caution_2);
        caution_3 = findViewById(R.id.caution_3);
        caution_4 = findViewById(R.id.caution_4);
        caution_5 = findViewById(R.id.caution_5);
        caution_6 = findViewById(R.id.caution_6);
        caution_7 = findViewById(R.id.caution_7);
        caution_8 = findViewById(R.id.caution_8);
        caution_9 = findViewById(R.id.caution_9);
        caution_10 = findViewById(R.id.caution_10);
        caution_new_10 = findViewById(R.id.caution_new_10);
        caution_11 = findViewById(R.id.caution_11);
        caution_12 = findViewById(R.id.caution_12);
        caution_13 = findViewById(R.id.caution_13);
        caution_14 = findViewById(R.id.caution_14);
        caution_15 = findViewById(R.id.caution_15);
        caution_16 = findViewById(R.id.caution_16);
        caution_17 = findViewById(R.id.caution_17);
        caution_18 = findViewById(R.id.caution_18);
        caution_19 = findViewById(R.id.caution_19);
        caution_20 = findViewById(R.id.caution_20);
        caution_21 = findViewById(R.id.caution_21);
        caution_22 = findViewById(R.id.caution_22);
        caution_23 = findViewById(R.id.caution_23);
        caution_24 = findViewById(R.id.caution_24);
        caution_25 = findViewById(R.id.caution_25);
        caution_26 = findViewById(R.id.caution_26);
        caution_27 = findViewById(R.id.caution_27);
        caution_28 = findViewById(R.id.caution_28);
        caution_29 = findViewById(R.id.caution_29);
        caution_30 = findViewById(R.id.caution_30);
        caution_31 = findViewById(R.id.caution_31);
        caution_32 = findViewById(R.id.caution_32);
        caution_33 = findViewById(R.id.caution_33);
        caution_34 = findViewById(R.id.caution_34);
        caution_35 = findViewById(R.id.caution_35);
        caution_36 = findViewById(R.id.caution_36);
        caution_37 = findViewById(R.id.caution_37);
        caution_38 = findViewById(R.id.caution_38);
        caution_39 = findViewById(R.id.caution_39);
        caution_40 = findViewById(R.id.caution_40);
        caution_41 = findViewById(R.id.caution_41);
        section_prompt_imageview = findViewById(R.id.section_prompt_imageview);
        section_prompt1_imageview = findViewById(R.id.section_prompt1_imageview);
        section_prompt2_imageview = findViewById(R.id.section_prompt2_imageview);
        section_prompt3_imageview = findViewById(R.id.section_prompt3_imageview);
        circulation = findViewById(R.id.circulation);


        action_prompt1_checkbox.setOnClickListener(v -> {
            if (action_prompt1_checkbox.isChecked()) {
                caution_4.setImageResource(R.drawable.ic_done);
                action_prompt1.setBackgroundResource(R.color.lighter_green);
                action_prompt1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("1", System.currentTimeMillis());
                action_prompt1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_4.setImageResource(R.drawable.ic_error2);
                action_prompt1.setBackgroundResource(R.color.beige_red);
                action_prompt1_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("1", (long) 0);
                action_prompt1_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt2_checkbox.setOnClickListener(v -> {
            if (action_prompt2_checkbox.isChecked()) {
                caution_5.setImageResource(R.drawable.ic_done);
                action_prompt2.setBackgroundResource(R.color.lighter_green);
                action_prompt2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("2", System.currentTimeMillis());
                action_prompt2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_5.setImageResource(R.drawable.ic_error2);
                action_prompt2.setBackgroundResource(R.color.beige_red);
                action_prompt2_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("2", (long) 0);
                action_prompt2_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt3_checkbox.setOnClickListener(v -> {
            if (action_prompt3_checkbox.isChecked()) {
                caution_1.setImageResource(R.drawable.ic_done);
                action_prompt3.setBackgroundResource(R.color.lighter_green);
                action_prompt3_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("3", System.currentTimeMillis());
                action_prompt3_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_1.setImageResource(R.drawable.ic_error2);
                action_prompt3.setBackgroundResource(R.color.beige_red);
                action_prompt3_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("3", (long) 0);
                action_prompt3_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt4_checkbox.setOnClickListener(v -> {
            if (action_prompt4_checkbox.isChecked()) {
                caution_2.setImageResource(R.drawable.ic_done);
                action_prompt4.setBackgroundResource(R.color.lighter_green);
                action_prompt4_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt4_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("4", System.currentTimeMillis());
                action_prompt4_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_2.setImageResource(R.drawable.ic_error2);
                action_prompt4.setBackgroundResource(R.color.beige_red);
                action_prompt4_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt4_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("4", (long) 0);
                action_prompt4_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt5_checkbox.setOnClickListener(v -> {
            if (action_prompt5_checkbox.isChecked()) {
                caution_6.setImageResource(R.drawable.ic_done);
                action_prompt5.setBackgroundResource(R.color.lighter_green);
                action_prompt5_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt5_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("5", System.currentTimeMillis());
                action_prompt5_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_6.setImageResource(R.drawable.ic_error2);
                action_prompt5.setBackgroundResource(R.color.beige_red);
                action_prompt5_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt5_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("5", (long) 0);
                action_prompt5_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt6_checkbox.setOnClickListener(v -> {
            if (action_prompt6_checkbox.isChecked()) {
                caution_7.setImageResource(R.drawable.ic_done);
                action_prompt6.setBackgroundResource(R.color.lighter_green);
                action_prompt6_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt6_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("6", System.currentTimeMillis());
                action_prompt6_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_7.setImageResource(R.drawable.ic_error2);
                action_prompt6.setBackgroundResource(R.color.beige_red);
                action_prompt6_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt6_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("6", (long) 0);
                action_prompt6_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt7_checkbox.setOnClickListener(v -> {
            if (action_prompt7_checkbox.isChecked()) {
                caution_3.setImageResource(R.drawable.ic_done);
                action_prompt7.setBackgroundResource(R.color.lighter_green);
                action_prompt7_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("7", System.currentTimeMillis());
                action_prompt7_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_3.setImageResource(R.drawable.ic_error2);
                action_prompt7.setBackgroundResource(R.color.beige_red);
                action_prompt7_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("7", (long) 0);
                action_prompt7_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt8_checkbox.setOnClickListener(v -> {
            if (action_prompt8_checkbox.isChecked()) {
                caution_8.setImageResource(R.drawable.ic_done);
                action_prompt8.setBackgroundResource(R.color.lighter_green);
                action_prompt8_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("8", System.currentTimeMillis());
                action_prompt8_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_8.setImageResource(R.drawable.ic_error2);
                action_prompt8.setBackgroundResource(R.color.beige_red);
                action_prompt8_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("8", (long) 0);
                action_prompt8_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt9_checkbox.setOnClickListener(v -> {
            if (action_prompt9_checkbox.isChecked()) {
                caution_12.setImageResource(R.drawable.ic_done);
                action_prompt9.setBackgroundResource(R.color.lighter_green);
                action_prompt9_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("9", System.currentTimeMillis());

                action_prompt9_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_12.setImageResource(R.drawable.ic_error2);
                action_prompt9.setBackgroundResource(R.color.beige_red);
                action_prompt9_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("9", (long) 0);
                action_prompt9_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt11_checkbox.setOnClickListener(v -> {
            if (action_prompt11_checkbox.isChecked()) {
                caution_13.setImageResource(R.drawable.ic_done);
                action_prompt11.setBackgroundResource(R.color.lighter_green);
                action_prompt11_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("11", System.currentTimeMillis());
                action_prompt11_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_13.setImageResource(R.drawable.ic_error2);
                action_prompt11.setBackgroundResource(R.color.beige_red);
                action_prompt11_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("11", (long) 0);
                action_prompt11_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt12_checkbox.setOnClickListener(v -> {
            if (action_prompt12_checkbox.isChecked()) {
                caution_14.setImageResource(R.drawable.ic_done);
                action_prompt12.setBackgroundResource(R.color.lighter_green);
                action_prompt12_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("12", System.currentTimeMillis());
                action_prompt12_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_14.setImageResource(R.drawable.ic_error2);
                action_prompt12.setBackgroundResource(R.color.beige_red);
                action_prompt12_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("12", (long) 0);
                action_prompt12_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt13_checkbox.setOnClickListener(v -> {
            if (action_prompt13_checkbox.isChecked()) {
                action_prompt13.setBackgroundResource(R.color.lighter_green);
                action_prompt13_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("13", System.currentTimeMillis());
                action_prompt13_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_prompt13.setBackgroundResource(R.color.beige_red);
                action_prompt13_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("13", (long) 0);
                action_prompt13_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt14_checkbox.setOnClickListener(v -> {
            if (action_prompt14_checkbox.isChecked()) {
                action_prompt14.setBackgroundResource(R.color.lighter_green);
                action_prompt14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("14", System.currentTimeMillis());
                action_prompt14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_prompt14.setBackgroundResource(R.color.beige_red);
                action_prompt14_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("14", (long) 0);
                action_prompt14_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt16_checkbox.setOnClickListener(v -> {
            if (action_prompt16_checkbox.isChecked()) {
                caution_9.setImageResource(R.drawable.ic_done);
                action_prompt16.setBackgroundResource(R.color.lighter_green);
                action_prompt16_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt16_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("16", System.currentTimeMillis());
                action_prompt16_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_9.setImageResource(R.drawable.ic_error2);
                action_prompt16.setBackgroundResource(R.color.beige_red);
                action_prompt16_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt16_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("16", (long) 0);
                action_prompt16_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt17_checkbox.setOnClickListener(v -> {
            if (action_prompt17_checkbox.isChecked()) {
                caution_10.setImageResource(R.drawable.ic_done);
                action_prompt17.setBackgroundResource(R.color.lighter_green);
                action_prompt17_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("17", System.currentTimeMillis());
                action_prompt17_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_10.setImageResource(R.drawable.ic_error2);
                action_prompt17.setBackgroundResource(R.color.beige_red);
                action_prompt17_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("17", (long) 0);
                action_prompt17_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt_new_17_checkbox.setOnClickListener(v -> {
            if (action_prompt_new_17_checkbox.isChecked()) {
                caution_new_10.setImageResource(R.drawable.ic_done);
                action_prompt_new_17.setBackgroundResource(R.color.lighter_green);
                action_prompt_new_17_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt_new_17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("18", System.currentTimeMillis());
                action_prompt_new_17_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_new_10.setImageResource(R.drawable.ic_error2);
                action_prompt_new_17.setBackgroundResource(R.color.beige_red);
                action_prompt_new_17_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt_new_17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("18", (long) 0);
                action_prompt_new_17_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt25_checkbox.setOnClickListener(v -> {
            if (action_prompt25_checkbox.isChecked()) {
                caution_new3.setImageResource(R.drawable.ic_done);
                action_prompt_25.setBackgroundResource(R.color.lighter_green);
                action_prompt25_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt25_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("59", System.currentTimeMillis());
                action_prompt25_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_new3.setImageResource(R.drawable.ic_error2);
                action_prompt_25.setBackgroundResource(R.color.beige_red);
                action_prompt25_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt25_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("32", (long) 0);
                action_prompt25_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt_new_checkbox.setOnClickListener(v -> {
            if (action_prompt_new_checkbox.isChecked()) {
                caution_new.setImageResource(R.drawable.ic_done);
                action_prompt_new.setBackgroundResource(R.color.lighter_green);
                action_prompt_new_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt_new_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("57", System.currentTimeMillis());
                action_prompt_new_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_new.setImageResource(R.drawable.ic_error2);
                action_prompt_new.setBackgroundResource(R.color.beige_red);
                action_prompt_new_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt_new_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("57", (long) 0);
                action_prompt_new_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt_new1_checkbox.setOnClickListener(v -> {
            if (action_prompt_new1_checkbox.isChecked()) {
                caution_new1.setImageResource(R.drawable.ic_done);
                action_prompt_new1.setBackgroundResource(R.color.lighter_green);
                action_prompt_new1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt_new1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("58", System.currentTimeMillis());
                action_prompt_new1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_new1.setImageResource(R.drawable.ic_error2);
                action_prompt_new1.setBackgroundResource(R.color.beige_red);
                action_prompt_new1_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt_new1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("31", (long) 0);
                action_prompt_new1_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });


        action_prompt18_checkbox.setOnClickListener(v -> {
            if (action_prompt18_checkbox.isChecked()) {
                caution1.setImageResource(R.drawable.ic_done);
                action_prompt18.setBackgroundResource(R.color.lighter_green);
                action_prompt18_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt18_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("15", System.currentTimeMillis());
                action_prompt18_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution1.setImageResource(R.drawable.ic_error2);
                action_prompt18.setBackgroundResource(R.color.beige_red);
                action_prompt18_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt18_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("15", (long) 0);
                action_prompt18_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });


        action_prompt19_checkbox.setOnClickListener(v -> {
            if (action_prompt19_checkbox.isChecked()) {
                caution_11.setImageResource(R.drawable.ic_done);
                action_prompt19.setBackgroundResource(R.color.lighter_green);
                action_prompt19_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt19_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("19", System.currentTimeMillis());
                action_prompt19_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_11.setImageResource(R.drawable.ic_error2);
                action_prompt19.setBackgroundResource(R.color.beige_red);
                action_prompt19_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt19_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("19", (long) 0);
                action_prompt19_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt20_checkbox.setOnClickListener(v -> {
            if (action_prompt20_checkbox.isChecked()) {
                caution_15.setImageResource(R.drawable.ic_done);
                action_prompt20.setBackgroundResource(R.color.lighter_green);
                action_prompt20_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt20_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("20", System.currentTimeMillis());
                action_prompt20_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_15.setImageResource(R.drawable.ic_error2);
                action_prompt20.setBackgroundResource(R.color.beige_red);
                action_prompt20_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt20_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("20", (long) 0);
                action_prompt20_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt21_checkbox.setOnClickListener(v -> {
            if (action_prompt21_checkbox.isChecked()) {
                caution_16.setImageResource(R.drawable.ic_done);
                action_prompt21.setBackgroundResource(R.color.lighter_green);
                action_prompt21_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt21_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("21", System.currentTimeMillis());
                action_prompt21_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_16.setImageResource(R.drawable.ic_error2);
                action_prompt21.setBackgroundResource(R.color.beige_red);
                action_prompt21_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt21_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("21", (long) 0);
                action_prompt21_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt22_checkbox.setOnClickListener(v -> {
            if (action_prompt22_checkbox.isChecked()) {
                caution_17.setImageResource(R.drawable.ic_done);
                action_prompt22.setBackgroundResource(R.color.lighter_green);
                action_prompt22_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt22_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("22", System.currentTimeMillis());
                action_prompt22_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_17.setImageResource(R.drawable.ic_error2);
                action_prompt22.setBackgroundResource(R.color.beige_red);
                action_prompt22_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt22_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("22", (long) 0);
                action_prompt22_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt23_checkbox.setOnClickListener(v -> {
            if (action_prompt23_checkbox.isChecked()) {
                caution_18.setImageResource(R.drawable.ic_done);
                action_prompt23.setBackgroundResource(R.color.lighter_green);
                action_prompt23_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt23_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("23", System.currentTimeMillis());
                action_prompt23_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_18.setImageResource(R.drawable.ic_error2);
                action_prompt23.setBackgroundResource(R.color.beige_red);
                action_prompt23_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt23_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("23", (long) 0);
                action_prompt23_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt24_checkbox.setOnClickListener(v -> {
            if (action_prompt24_checkbox.isChecked()) {
                caution_36.setImageResource(R.drawable.ic_done);
                action_prompt24.setBackgroundResource(R.color.lighter_green);
                action_prompt24_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt24_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("24", System.currentTimeMillis());
                action_prompt24_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_36.setImageResource(R.drawable.ic_error2);
                action_prompt24.setBackgroundResource(R.color.beige_red);
                action_prompt24_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt24_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("24", (long) 0);
                action_prompt24_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt26_checkbox.setOnClickListener(v -> {
            if (action_prompt26_checkbox.isChecked()) {
                caution_38.setImageResource(R.drawable.ic_done);
                action_prompt26.setBackgroundResource(R.color.lighter_green);
                action_prompt26_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt26_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("26", System.currentTimeMillis());
                action_prompt26_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_38.setImageResource(R.drawable.ic_error2);
                action_prompt26.setBackgroundResource(R.color.beige_red);
                action_prompt26_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt26_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("26", (long) 0);
                action_prompt26_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt27_checkbox.setOnClickListener(v -> {
            if (action_prompt27_checkbox.isChecked()) {
                caution_19.setImageResource(R.drawable.ic_done);
                action_prompt27.setBackgroundResource(R.color.lighter_green);
                action_prompt27_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt27_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("27", System.currentTimeMillis());
                action_prompt27_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_19.setImageResource(R.drawable.ic_error2);
                action_prompt27.setBackgroundResource(R.color.beige_red);
                action_prompt27_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt27_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("27", (long) 0);
                action_prompt27_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt29_checkbox.setOnClickListener(v -> {
            if (action_prompt29_checkbox.isChecked()) {
                caution_39.setImageResource(R.drawable.ic_done);
                action_prompt29.setBackgroundResource(R.color.lighter_green);
                action_prompt29_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt29_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("29", System.currentTimeMillis());
                action_prompt29_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_39.setImageResource(R.drawable.ic_error2);
                action_prompt29.setBackgroundResource(R.color.beige_red);
                action_prompt29_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt29_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("29", (long) 0);
                action_prompt29_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt30_checkbox.setOnClickListener(v -> {
            if (action_prompt30_checkbox.isChecked()) {
                caution_20.setImageResource(R.drawable.ic_done);
                action_prompt30.setBackgroundResource(R.color.lighter_green);
                action_prompt30_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt30_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("30", System.currentTimeMillis());
                action_prompt30_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_20.setImageResource(R.drawable.ic_error2);
                action_prompt30.setBackgroundResource(R.color.beige_red);
                action_prompt30_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt30_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("30", (long) 0);
                action_prompt30_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt31_checkbox.setOnClickListener(v -> {
            if (action_prompt31_checkbox.isChecked()) {
                action_prompt31.setBackgroundResource(R.color.lighter_green);
                action_prompt31_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt31_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("31", System.currentTimeMillis());
                action_prompt31_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_prompt31.setBackgroundResource(R.color.beige_red);
                action_prompt31_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt31_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("31", (long) 0);
                action_prompt31_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt33_checkbox.setOnClickListener(v -> {
            if (action_prompt33_checkbox.isChecked()) {
                caution_40.setImageResource(R.drawable.ic_done);
                action_prompt33.setBackgroundResource(R.color.lighter_green);
                action_prompt33_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt33_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("33", System.currentTimeMillis());
                action_prompt33_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_40.setImageResource(R.drawable.ic_error2);
                action_prompt33.setBackgroundResource(R.color.beige_red);
                action_prompt33_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt33_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("33", (long) 0);
                action_prompt33_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt34_checkbox.setOnClickListener(v -> {
            if (action_prompt34_checkbox.isChecked()) {
                caution_22.setImageResource(R.drawable.ic_done);
                action_prompt34.setBackgroundResource(R.color.lighter_green);
                action_prompt34_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt34_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("34", System.currentTimeMillis());
                action_prompt34_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_22.setImageResource(R.drawable.ic_error2);
                action_prompt34.setBackgroundResource(R.color.beige_red);
                action_prompt34_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt34_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("34", (long) 0);
                action_prompt34_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt36_checkbox.setOnClickListener(v -> {
            if (action_prompt36_checkbox.isChecked()) {
                caution_23.setImageResource(R.drawable.ic_done);
                action_prompt36.setBackgroundResource(R.color.lighter_green);
                action_prompt36_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt36_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("36", System.currentTimeMillis());
                action_prompt36_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_23.setImageResource(R.drawable.ic_error2);
                action_prompt36.setBackgroundResource(R.color.beige_red);
                action_prompt36_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt36_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("36", (long) 0);
                action_prompt36_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt37_checkbox.setOnClickListener(v -> {
            if (action_prompt37_checkbox.isChecked()) {
                caution_24.setImageResource(R.drawable.ic_done);
                action_prompt37.setBackgroundResource(R.color.lighter_green);
                action_prompt37_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt37_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("37", System.currentTimeMillis());
                action_prompt37_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_24.setImageResource(R.drawable.ic_error2);
                action_prompt37.setBackgroundResource(R.color.beige_red);
                action_prompt37_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt37_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("37", (long) 0);
                action_prompt37_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt38_checkbox.setOnClickListener(v -> {
            if (action_prompt38_checkbox.isChecked()) {
                caution_25.setImageResource(R.drawable.ic_done);
                action_prompt38.setBackgroundResource(R.color.lighter_green);
                action_prompt38_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt38_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("38", System.currentTimeMillis());
                action_prompt38_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_25.setImageResource(R.drawable.ic_error2);
                action_prompt38.setBackgroundResource(R.color.beige_red);
                action_prompt38_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt38_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("38", (long) 0);
                action_prompt38_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt39_checkbox.setOnClickListener(v -> {
            if (action_prompt39_checkbox.isChecked()) {
                caution_37.setImageResource(R.drawable.ic_done);
                action_prompt39.setBackgroundResource(R.color.lighter_green);
                action_prompt39_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt39_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("39", System.currentTimeMillis());
                action_prompt39_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_37.setImageResource(R.drawable.ic_error2);
                action_prompt39.setBackgroundResource(R.color.beige_red);
                action_prompt39_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt39_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("39", (long) 0);
                action_prompt39_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt40_checkbox.setOnClickListener(v -> {
            if (action_prompt40_checkbox.isChecked()) {
                caution_26.setImageResource(R.drawable.ic_done);
                action_prompt40.setBackgroundResource(R.color.lighter_green);
                action_prompt40_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt40_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("40", System.currentTimeMillis());
                action_prompt40_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_26.setImageResource(R.drawable.ic_error2);
                action_prompt40.setBackgroundResource(R.color.beige_red);
                action_prompt40_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt40_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("40", (long) 0);
                action_prompt40_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt41_checkbox.setOnClickListener(v -> {
            if (action_prompt41_checkbox.isChecked()) {
                caution_27.setImageResource(R.drawable.ic_done);
                action_prompt41.setBackgroundResource(R.color.lighter_green);
                action_prompt41_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt41_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("41", System.currentTimeMillis());
                action_prompt41_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_27.setImageResource(R.drawable.ic_error2);
                action_prompt41.setBackgroundResource(R.color.beige_red);
                action_prompt41_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt41_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("41", (long) 0);
                action_prompt41_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt42_checkbox.setOnClickListener(v -> {
            if (action_prompt42_checkbox.isChecked()) {
                caution_28.setImageResource(R.drawable.ic_done);
                action_prompt42.setBackgroundResource(R.color.lighter_green);
                action_prompt42_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt42_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("42", System.currentTimeMillis());
                action_prompt42_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_28.setImageResource(R.drawable.ic_error2);
                action_prompt42.setBackgroundResource(R.color.beige_red);
                action_prompt42_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt42_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("42", (long) 0);
                action_prompt42_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt43_checkbox.setOnClickListener(v -> {
            if (action_prompt43_checkbox.isChecked()) {
                caution_29.setImageResource(R.drawable.ic_done);
                action_prompt43.setBackgroundResource(R.color.lighter_green);
                action_prompt43_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt43_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("43", System.currentTimeMillis());
                action_prompt43_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_29.setImageResource(R.drawable.ic_error2);
                action_prompt43.setBackgroundResource(R.color.beige_red);
                action_prompt43_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt43_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("43", (long) 0);
                action_prompt43_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt44_checkbox.setOnClickListener(v -> {
            if (action_prompt44_checkbox.isChecked()) {
                caution_30.setImageResource(R.drawable.ic_done);
                action_prompt44.setBackgroundResource(R.color.lighter_green);
                action_prompt44_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt44_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("44", System.currentTimeMillis());
                action_prompt44_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_30.setImageResource(R.drawable.ic_error2);
                action_prompt44.setBackgroundResource(R.color.beige_red);
                action_prompt44_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt44_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("44", (long) 0);
                action_prompt44_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt46_checkbox.setOnClickListener(v -> {
            if (action_prompt46_checkbox.isChecked()) {
                caution_31.setImageResource(R.drawable.ic_done);
                action_prompt46.setBackgroundResource(R.color.lighter_green);
                action_prompt46_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt46_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("46", System.currentTimeMillis());
                action_prompt46_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_31.setImageResource(R.drawable.ic_error2);
                action_prompt46.setBackgroundResource(R.color.beige_red);
                action_prompt46_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt46_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("46", (long) 0);
                action_prompt46_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt47_checkbox.setOnClickListener(v -> {
            if (action_prompt47_checkbox.isChecked()) {
                caution_32.setImageResource(R.drawable.ic_done);
                action_prompt47.setBackgroundResource(R.color.lighter_green);
                action_prompt47_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt47_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("47", System.currentTimeMillis());
                action_prompt47_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_32.setImageResource(R.drawable.ic_error2);
                action_prompt47.setBackgroundResource(R.color.beige_red);
                action_prompt47_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt47_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("47", (long) 0);
                action_prompt47_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt48_checkbox.setOnClickListener(v -> {
            if (action_prompt48_checkbox.isChecked()) {
                caution_33.setImageResource(R.drawable.ic_done);
                action_prompt48.setBackgroundResource(R.color.lighter_green);
                action_prompt48_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt48_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("48", System.currentTimeMillis());
                action_prompt48_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_33.setImageResource(R.drawable.ic_error2);
                action_prompt48.setBackgroundResource(R.color.beige_red);
                action_prompt48_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt48_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("48", (long) 0);
                action_prompt48_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt49_checkbox.setOnClickListener(v -> {
            if (action_prompt49_checkbox.isChecked()) {
                caution_34.setImageResource(R.drawable.ic_done);
                action_prompt49.setBackgroundResource(R.color.lighter_green);
                action_prompt49_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt49_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("49", System.currentTimeMillis());
                action_prompt49_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_34.setImageResource(R.drawable.ic_error2);
                action_prompt49.setBackgroundResource(R.color.beige_red);
                action_prompt49_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt49_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("49", (long) 0);
                action_prompt49_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt51_checkbox.setOnClickListener(v -> {
            if (action_prompt51_checkbox.isChecked()) {
                caution_35.setImageResource(R.drawable.ic_done);
                action_prompt51.setBackgroundResource(R.color.lighter_green);
                action_prompt51_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt51_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("51", System.currentTimeMillis());
                action_prompt51_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_35.setImageResource(R.drawable.ic_error2);
                action_prompt51.setBackgroundResource(R.color.beige_red);
                action_prompt51_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt51_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("51", (long) 0);
                action_prompt51_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_prompt52_checkbox.setOnClickListener(v -> {
            if (action_prompt52_checkbox.isChecked()) {
                caution_41.setImageResource(R.drawable.ic_done);
                action_prompt52.setBackgroundResource(R.color.lighter_green);
                action_prompt52_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_prompt52_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("56", System.currentTimeMillis());
                action_prompt52_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                caution_41.setImageResource(R.drawable.ic_error2);
                action_prompt52.setBackgroundResource(R.color.beige_red);
                action_prompt52_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_prompt52_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("56", (long) 0);
                action_prompt52_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        section_prompt_checkbox.setOnClickListener(v -> {
            if (section_prompt_checkbox.isChecked()) {
                section_prompt_imageview.setImageResource(R.drawable.ic_done);
                section_prompt.setBackgroundResource(R.color.lighter_green);
                section_prompt_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                section_prompt_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("52", System.currentTimeMillis());
                section_prompt_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                section_prompt_imageview.setImageResource(R.drawable.ic_error2);
                section_prompt.setBackgroundResource(R.color.beige_red);
                section_prompt_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                section_prompt_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("52", (long) 0);
                section_prompt_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        section_prompt1_checkbox.setOnClickListener(v -> {
            if (section_prompt1_checkbox.isChecked()) {
                section_prompt1_imageview.setImageResource(R.drawable.ic_done);
                section_prompt1.setBackgroundResource(R.color.lighter_green);
                section_prompt1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                section_prompt1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("53", System.currentTimeMillis());
                section_prompt1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                section_prompt1_imageview.setImageResource(R.drawable.ic_error2);
                section_prompt1.setBackgroundResource(R.color.beige_red);
                section_prompt1_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                section_prompt1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("53", (long) 0);
                section_prompt1_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        section_prompt2_checkbox.setOnClickListener(v -> {
            if (section_prompt2_checkbox.isChecked()) {
                section_prompt2_imageview.setImageResource(R.drawable.ic_done);
                section_prompt2.setBackgroundResource(R.color.lighter_green);
                section_prompt2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                section_prompt2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("54", System.currentTimeMillis());
                section_prompt2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                section_prompt2_imageview.setImageResource(R.drawable.ic_error2);
                section_prompt2.setBackgroundResource(R.color.beige_red);
                section_prompt2_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                section_prompt2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("54", (long) 0);
                section_prompt2_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        section_prompt3_checkbox.setOnClickListener(v -> {
            if (section_prompt3_checkbox.isChecked()) {
                section_prompt3_imageview.setImageResource(R.drawable.ic_done);
                section_prompt3.setBackgroundResource(R.color.lighter_green);
                section_prompt3_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                section_prompt3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                emergency_actions_timestamps_map.replace("55", System.currentTimeMillis());
                section_prompt3_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                section_prompt3_imageview.setImageResource(R.drawable.ic_error2);
                section_prompt3.setBackgroundResource(R.color.beige_red);
                section_prompt3_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                section_prompt3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                emergency_actions_timestamps_map.replace("55", (long) 0);
                section_prompt3_checkbox.setText("Done");
            }

            saveTriageDetails(false);
        });

        action_trauma_checkbox.setOnClickListener(v -> {
            if (action_trauma_checkbox.isChecked()) {
                caution.setImageResource(R.drawable.ic_done);
                action_trauma.setBackgroundResource(R.color.lighter_green);
                action_trauma_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("0", System.currentTimeMillis());
                action_trauma_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma.setBackgroundResource(R.color.beige_red);
                action_trauma_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("0", (long) 0);
                action_trauma_checkbox.setText("Done");
                caution.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma1_checkbox.setOnClickListener(v -> {
            if (action_trauma1_checkbox.isChecked()) {
                caution2.setImageResource(R.drawable.ic_done);
                action_trauma1.setBackgroundResource(R.color.lighter_green);
                action_trauma1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("1", System.currentTimeMillis());
                action_trauma1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma1.setBackgroundResource(R.color.beige_red);
                action_trauma1_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("1", (long) 0);
                action_trauma1_checkbox.setText("Done");
                caution2.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma2_checkbox.setOnClickListener(v -> {
            if (action_trauma2_checkbox.isChecked()) {
                caution3.setImageResource(R.drawable.ic_done);
                action_trauma2.setBackgroundResource(R.color.lighter_green);
                action_trauma2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("2", System.currentTimeMillis());
                action_trauma2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma2.setBackgroundResource(R.color.beige_red);
                action_trauma2_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("2", (long) 0);
                action_trauma2_checkbox.setText("Done");
                caution3.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma7_checkbox.setOnClickListener(v -> {
            if (action_trauma7_checkbox.isChecked()) {
                caution4.setImageResource(R.drawable.ic_done);
                action_trauma7.setBackgroundResource(R.color.lighter_green);
                action_trauma7_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("7", System.currentTimeMillis());
                action_trauma7_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma7.setBackgroundResource(R.color.beige_red);
                action_trauma7_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("7", (long) 0);
                action_trauma7_checkbox.setText("Done");
                caution4.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma8_checkbox.setOnClickListener(v -> {
            if (action_trauma8_checkbox.isChecked()) {
                caution5.setImageResource(R.drawable.ic_done);
                action_trauma8.setBackgroundResource(R.color.lighter_green);
                action_trauma8_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("8", System.currentTimeMillis());
                action_trauma8_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma8.setBackgroundResource(R.color.beige_red);
                action_trauma8_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("8", (long) 0);
                action_trauma8_checkbox.setText("Done");
                caution5.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma9_checkbox.setOnClickListener(v -> {
            if (action_trauma9_checkbox.isChecked()) {
                caution6.setImageResource(R.drawable.ic_done);
                action_trauma9.setBackgroundResource(R.color.lighter_green);
                action_trauma9_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("9", System.currentTimeMillis());
                action_trauma9_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma9.setBackgroundResource(R.color.beige_red);
                action_trauma9_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("9", (long) 0);
                action_trauma9_checkbox.setText("Done");
                caution6.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma10_checkbox.setOnClickListener(v -> {
            if (action_trauma10_checkbox.isChecked()) {
                caution7.setImageResource(R.drawable.ic_done);
                action_trauma10.setBackgroundResource(R.color.lighter_green);
                action_trauma10_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma10_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("10", System.currentTimeMillis());
                action_trauma10_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma10.setBackgroundResource(R.color.beige_red);
                action_trauma10_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma10_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("10", (long) 0);
                action_trauma10_checkbox.setText("Done");
                caution7.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma11_checkbox.setOnClickListener(v -> {
            if (action_trauma11_checkbox.isChecked()) {
                caution8.setImageResource(R.drawable.ic_done);
                action_trauma11.setBackgroundResource(R.color.lighter_green);
                action_trauma11_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("11", System.currentTimeMillis());
                action_trauma11_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma11.setBackgroundResource(R.color.beige_red);
                action_trauma11_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("11", (long) 0);
                action_trauma11_checkbox.setText("Done");
                caution8.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma12_checkbox.setOnClickListener(v -> {
            if (action_trauma12_checkbox.isChecked()) {
                caution9.setImageResource(R.drawable.ic_done);
                action_trauma12.setBackgroundResource(R.color.lighter_green);
                action_trauma12_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("12", System.currentTimeMillis());
                action_trauma12_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma12.setBackgroundResource(R.color.beige_red);
                action_trauma12_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("12", (long) 0);
                action_trauma12_checkbox.setText("Done");
                caution9.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma13_checkbox.setOnClickListener(v -> {
            if (action_trauma13_checkbox.isChecked()) {
                caution10.setImageResource(R.drawable.ic_done);
                action_trauma13.setBackgroundResource(R.color.lighter_green);
                action_trauma13_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("13", System.currentTimeMillis());
                action_trauma13_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma13.setBackgroundResource(R.color.beige_red);
                action_trauma13_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("13", (long) 0);
                action_trauma13_checkbox.setText("Done");
                caution10.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma14_checkbox.setOnClickListener(v -> {
            if (action_trauma14_checkbox.isChecked()) {
                caution11.setImageResource(R.drawable.ic_done);
                action_trauma14.setBackgroundResource(R.color.lighter_green);
                action_trauma14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("14", System.currentTimeMillis());
                action_trauma14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma14.setBackgroundResource(R.color.beige_red);
                action_trauma14_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("14", (long) 0);
                action_trauma14_checkbox.setText("Done");
                caution11.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma15_checkbox.setOnClickListener(v -> {
            if (action_trauma15_checkbox.isChecked()) {
                caution12.setImageResource(R.drawable.ic_done);
                action_trauma15.setBackgroundResource(R.color.lighter_green);
                action_trauma15_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma15_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("15", System.currentTimeMillis());
                action_trauma15_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma15.setBackgroundResource(R.color.beige_red);
                action_trauma15_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma15_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("15", (long) 0);
                action_trauma15_checkbox.setText("Done");
                caution12.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma16_checkbox.setOnClickListener(v -> {
            if (action_trauma16_checkbox.isChecked()) {
                caution13.setImageResource(R.drawable.ic_done);
                action_trauma16.setBackgroundResource(R.color.lighter_green);
                action_trauma16_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma16_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("16", System.currentTimeMillis());
                action_trauma16_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma16.setBackgroundResource(R.color.beige_red);
                action_trauma16_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma16_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("16", (long) 0);
                action_trauma16_checkbox.setText("Done");
                caution13.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma17_checkbox.setOnClickListener(v -> {
            if (action_trauma17_checkbox.isChecked()) {
                caution14.setImageResource(R.drawable.ic_done);
                action_trauma17.setBackgroundResource(R.color.lighter_green);
                action_trauma17_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("17", System.currentTimeMillis());
                action_trauma17_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma17.setBackgroundResource(R.color.beige_red);
                action_trauma17_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("17", (long) 0);
                action_trauma17_checkbox.setText("Done");
                caution14.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma18_checkbox.setOnClickListener(v -> {
            if (action_trauma18_checkbox.isChecked()) {
                caution15.setImageResource(R.drawable.ic_done);
                action_trauma18.setBackgroundResource(R.color.lighter_green);
                action_trauma18_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma18_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("18", System.currentTimeMillis());
                action_trauma18_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma18.setBackgroundResource(R.color.beige_red);
                action_trauma18_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma18_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("18", (long) 0);
                action_trauma18_checkbox.setText("Done");
                caution15.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma19_checkbox.setOnClickListener(v -> {
            if (action_trauma19_checkbox.isChecked()) {
                caution16.setImageResource(R.drawable.ic_done);
                action_trauma19.setBackgroundResource(R.color.lighter_green);
                action_trauma19_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma19_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("19", System.currentTimeMillis());
                action_trauma19_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma19.setBackgroundResource(R.color.beige_red);
                action_trauma19_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma19_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("19", (long) 0);
                action_trauma19_checkbox.setText("Done");
                caution16.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma20_checkbox.setOnClickListener(v -> {
            if (action_trauma20_checkbox.isChecked()) {
                caution17.setImageResource(R.drawable.ic_done);
                action_trauma20.setBackgroundResource(R.color.lighter_green);
                action_trauma20_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma20_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("20", System.currentTimeMillis());
                action_trauma20_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma20.setBackgroundResource(R.color.beige_red);
                action_trauma20_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma20_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("20", (long) 0);
                action_trauma20_checkbox.setText("Done");
                caution17.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma21_checkbox.setOnClickListener(v -> {
            if (action_trauma21_checkbox.isChecked()) {
                caution18.setImageResource(R.drawable.ic_done);
                action_trauma21.setBackgroundResource(R.color.lighter_green);
                action_trauma21_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma21_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("21", System.currentTimeMillis());
                action_trauma21_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma21.setBackgroundResource(R.color.beige_red);
                action_trauma21_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma21_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("21", (long) 0);
                action_trauma21_checkbox.setText("Done");
                caution18.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma22_checkbox.setOnClickListener(v -> {
            if (action_trauma22_checkbox.isChecked()) {
                caution19.setImageResource(R.drawable.ic_done);
                action_trauma22.setBackgroundResource(R.color.lighter_green);
                action_trauma22_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma22_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("22", System.currentTimeMillis());
                action_trauma22_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma22.setBackgroundResource(R.color.beige_red);
                action_trauma22_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma22_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("22", (long) 0);
                action_trauma22_checkbox.setText("Done");
                caution19.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma23_checkbox.setOnClickListener(v -> {
            if (action_trauma23_checkbox.isChecked()) {
                caution20.setImageResource(R.drawable.ic_done);
                action_trauma23.setBackgroundResource(R.color.lighter_green);
                action_trauma23_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma23_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("23", System.currentTimeMillis());
                action_trauma23_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma23.setBackgroundResource(R.color.beige_red);
                action_trauma23_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma23_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("23", (long) 0);
                action_trauma23_checkbox.setText("Done");
                caution20.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma24_checkbox.setOnClickListener(v -> {
            if (action_trauma24_checkbox.isChecked()) {
                caution21.setImageResource(R.drawable.ic_done);
                action_trauma24.setBackgroundResource(R.color.lighter_green);
                action_trauma24_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma24_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("24", System.currentTimeMillis());
                action_trauma24_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma24.setBackgroundResource(R.color.beige_red);
                action_trauma24_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma24_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("24", (long) 0);
                action_trauma24_checkbox.setText("Done");
                caution21.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma25_checkbox.setOnClickListener(v -> {
            if (action_trauma25_checkbox.isChecked()) {
                caution22.setImageResource(R.drawable.ic_done);
                action_trauma25.setBackgroundResource(R.color.lighter_green);
                action_trauma25_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma25_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("25", System.currentTimeMillis());
                action_trauma25_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma25.setBackgroundResource(R.color.beige_red);
                action_trauma25_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma25_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("25", (long) 0);
                action_trauma25_checkbox.setText("Done");
                caution22.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma26_checkbox.setOnClickListener(v -> {
            if (action_trauma26_checkbox.isChecked()) {
                caution23.setImageResource(R.drawable.ic_done);
                action_trauma26.setBackgroundResource(R.color.lighter_green);
                action_trauma26_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma26_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("26", System.currentTimeMillis());
                action_trauma26_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma26.setBackgroundResource(R.color.beige_red);
                action_trauma26_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma26_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("26", (long) 0);
                action_trauma26_checkbox.setText("Done");
                caution23.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma27_checkbox.setOnClickListener(v -> {
            if (action_trauma27_checkbox.isChecked()) {
                caution24.setImageResource(R.drawable.ic_done);
                action_trauma27.setBackgroundResource(R.color.lighter_green);
                action_trauma27_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma27_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("27", System.currentTimeMillis());
                action_trauma27_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma27.setBackgroundResource(R.color.beige_red);
                action_trauma27_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma27_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("27", (long) 0);
                action_trauma27_checkbox.setText("Done");
                caution24.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma28_checkbox.setOnClickListener(v -> {
            if (action_trauma28_checkbox.isChecked()) {
                caution25.setImageResource(R.drawable.ic_done);
                action_trauma28.setBackgroundResource(R.color.lighter_green);
                action_trauma28_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma28_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("28", System.currentTimeMillis());
                action_trauma28_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma28.setBackgroundResource(R.color.beige_red);
                action_trauma28_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma28_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("28", (long) 0);
                action_trauma28_checkbox.setText("Done");
                caution25.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        action_trauma29_checkbox.setOnClickListener(v -> {
            if (action_trauma29_checkbox.isChecked()) {
                caution26.setImageResource(R.drawable.ic_done);
                action_trauma29.setBackgroundResource(R.color.lighter_green);
                action_trauma29_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                action_trauma29_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                trauma_timestamps_map.replace("29", System.currentTimeMillis());
                action_trauma29_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (System.currentTimeMillis())));
            } else {
                action_trauma29.setBackgroundResource(R.color.beige_red);
                action_trauma29_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                action_trauma29_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                trauma_timestamps_map.replace("29", (long) 0);
                action_trauma29_checkbox.setText("Done");
                caution26.setImageResource(R.drawable.ic_error2);
            }

            saveTriageDetails(false);
        });

        getPreviousTriageDetailsEmergency();

        emergency_check1.setOnClickListener(v -> {
            if (emergency_check1.isChecked()) {
                updateChecks(0, "1");
                emergency_check_none.setChecked(false);
            } else {
                updateChecks(0, "0");
            }
        });

        emergency_check2.setOnClickListener(v -> {
            if (emergency_check2.isChecked()) {
                updateChecks(1, "1");
                emergency_check_none.setChecked(false);
            } else {
                updateChecks(1, "0");
            }
        });

        emergency_check3.setOnClickListener(v -> {
            if (emergency_check3.isChecked()) {
                updateChecks(2, "1");
                emergency_check_none.setChecked(false);
            } else {
                updateChecks(2, "0");
            }
        });

        emergency_check_none.setOnClickListener(v -> {
            if (emergency_check_none.isChecked()) {
                emergency_check1.setChecked(false);
                emergency_check2.setChecked(false);
                emergency_check3.setChecked(false);
                trauma_check_airway.setChecked(false);
                updateChecks(0, "0");
                updateChecks(1, "0");
                updateChecks(2, "0");
                updateChecks(18, "0");
            }
        });

        emergency_check4.setOnClickListener(v -> {
            if (emergency_check4.isChecked()) {
                updateChecks(3, "1");
                emergency_check_none_two.setChecked(false);
            } else {
                updateChecks(3, "0");
            }
        });

        emergency_check5.setOnClickListener(v -> {
            if (emergency_check5.isChecked()) {
                updateChecks(4, "1");
                emergency_check_none_two.setChecked(false);
            } else {
                updateChecks(4, "0");
            }
        });

        emergency_check6.setOnClickListener(v -> {
            if (emergency_check6.isChecked()) {
                updateChecks(5, "1");
                emergency_check_none_two.setChecked(false);
            } else {
                updateChecks(5, "0");
            }
        });

//        emergency_check7.setOnClickListener(v -> {
//            if (emergency_check7.isChecked()) {
//                updateChecks(6, "1");
//                emergency_check_none_two.setChecked(false);
//            } else {
//                updateChecks(6, "0");
//            }
//        });

        emergency_check_none_two.setOnClickListener(v -> {
            if (emergency_check_none_two.isChecked()) {
                emergency_check4.setChecked(false);
                emergency_check5.setChecked(false);
                emergency_check6.setChecked(false);
//                emergency_check7.setChecked(false);
                trauma_check_circulation.setChecked(false);
                updateChecks(3, "0");
                updateChecks(4, "0");
                updateChecks(5, "0");
                updateChecks(6, "0");
                updateChecks(19, "0");
            }
        });

        emergency_check8.setOnClickListener(v -> {
            if (emergency_check8.isChecked()) {
                updateChecks(7, "1");
                emergency_check_none_three.setChecked(false);
            } else {
                updateChecks(7, "0");
            }
        });

        emergency_check9.setOnClickListener(v -> {
            if (emergency_check9.isChecked()) {
                start_time = System.currentTimeMillis();
                updateChecks(8, "1");
                emergency_check_none_three.setChecked(false);
            } else {
                updateChecks(8, "0");
            }
        });

        emergency_check10.setOnClickListener(v -> {
            if (emergency_check10.isChecked()) {
                updateChecks(9, "1");
                emergency_check_none_three.setChecked(false);
            } else {
                updateChecks(9, "0");
            }
        });

        emergency_check_none_three.setOnClickListener(v -> {
            if (emergency_check_none_three.isChecked()){
                emergency_check8.setChecked(false);
                emergency_check9.setChecked(false);
                emergency_check10.setChecked(false);
                trauma_check_disability.setChecked(false);
                updateChecks(7, "0");
                updateChecks(8, "0");
                updateChecks(9, "0");
                updateChecks(20, "0");
            }
        });

        emergency_check11.setOnClickListener(v -> {
            if (emergency_check11.isChecked()) {
                updateChecks(10, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(10, "0");
            }
        });

        emergency_check12.setOnClickListener(v -> {
            if (emergency_check12.isChecked()) {
                updateChecks(11, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(11, "0");
            }
        });

        emergency_check13.setOnClickListener(v -> {
            if (emergency_check13.isChecked()) {
                updateChecks(12, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(12, "0");
            }
        });

        emergency_check14.setOnClickListener(v -> {
            if (emergency_check14.isChecked()) {
                updateChecks(13, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(13, "0");
            }
        });

        emergency_check15.setOnClickListener(v -> {
            if (emergency_check15.isChecked()) {
                updateChecks(14, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(14, "0");
            }
        });

        emergency_check16.setOnClickListener(v -> {
            if (emergency_check16.isChecked()) {
                updateChecks(15, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(15, "0");
            }
        });

        emergency_check17.setOnClickListener(v -> {
            if (emergency_check17.isChecked()) {
                updateChecks(16, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(16, "0");
            }
        });

        emergency_check18.setOnClickListener(v -> {
            if (emergency_check18.isChecked()) {
                updateChecks(17, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(17, "0");
            }
        });

        emergency_check19.setOnClickListener(v -> {
            if (emergency_check19.isChecked()) {
                updateChecks(24, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(24, "0");
            }
        });

        emergency_check_none_four.setOnClickListener(v -> {
            if (emergency_check_none_four.isChecked()) {
                emergency_check11.setChecked(false);
                emergency_check12.setChecked(false);
                emergency_check13.setChecked(false);
                emergency_check14.setChecked(false);
                emergency_check15.setChecked(false);
                emergency_check16.setChecked(false);
                emergency_check17.setChecked(false);
                emergency_check18.setChecked(false);
                emergency_check19.setChecked(false);

                trauma_check_expose1.setChecked(false);
                trauma_check_expose2.setChecked(false);
                trauma_check_expose3.setChecked(false);
                updateChecks(10, "0");
                updateChecks(11, "0");
                updateChecks(12, "0");
                updateChecks(13, "0");
                updateChecks(14, "0");
                updateChecks(15, "0");
                updateChecks(16, "0");
                updateChecks(17, "0");
                updateChecks(21, "0");
                updateChecks(22, "0");
                updateChecks(23, "0");
                updateChecks(24, "0");
            }
        });

        trauma_check_airway.setOnClickListener(v -> {
            if (trauma_check_airway.isChecked()) {
                updateChecks(18, "1");
                emergency_check_none.setChecked(false);
            } else {
                updateChecks(18, "0");
            }
        });

        trauma_check_circulation.setOnClickListener(v -> {
            if (trauma_check_circulation.isChecked()) {
                updateChecks(19, "1");
                emergency_check_none_two.setChecked(false);
            } else {
                updateChecks(19, "0");
            }
        });

        trauma_check_disability.setOnClickListener(v -> {
            if (trauma_check_disability.isChecked()) {
                updateChecks(20, "1");
                emergency_check_none_three.setChecked(false);
            } else {
                updateChecks(20, "0");
            }
        });

        trauma_check_expose1.setOnClickListener(v -> {
            if (trauma_check_expose1.isChecked()) {
                updateChecks(21, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(21, "0");
            }
        });

        trauma_check_expose2.setOnClickListener(v -> {
            if (trauma_check_expose2.isChecked()) {
                updateChecks(22, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(22, "0");
            }
        });

        trauma_check_expose3.setOnClickListener(v -> {
            if (trauma_check_expose3.isChecked()) {
                updateChecks(23, "1");
                emergency_check_none_four.setChecked(false);
            } else {
                updateChecks(23, "0");
            }
        });

        priority_signs1.setOnClickListener(v -> {
            if (priority_signs1.isChecked()) {
                updatePriorityChecks(0, "1");
            } else {
                updatePriorityChecks(0, "0");
            }
        });

        priority_signs2.setOnClickListener(v -> {
            if (priority_signs2.isChecked()) {
                updatePriorityChecks(1, "1");
            } else {
                updatePriorityChecks(1, "0");
            }
        });

        priority_signs3.setOnClickListener(v -> {
            if (priority_signs3.isChecked()) {
                updatePriorityChecks(2, "1");
            } else {
                updatePriorityChecks(2, "0");
            }
        });

        priority_signs4.setOnClickListener(v -> {
            if (priority_signs4.isChecked()) {
                updatePriorityChecks(3, "1");
            } else {
                updatePriorityChecks(3, "0");
            }
        });

        priority_signs5.setOnClickListener(v -> {
            if (priority_signs5.isChecked()) {
                updatePriorityChecks(4, "1");
            } else {
                updatePriorityChecks(4, "0");
            }
        });

        priority_signs6.setOnClickListener(v -> {
            if (priority_signs6.isChecked()) {
                updatePriorityChecks(5, "1");
            } else {
                updatePriorityChecks(5, "0");
            }
        });

        priority_signs7.setOnClickListener(v -> {
            if (priority_signs7.isChecked()) {
                updatePriorityChecks(6, "1");
            } else {
                updatePriorityChecks(6, "0");
            }
        });

        priority_signs8.setOnClickListener(v -> {
            if (priority_signs8.isChecked()) {
                updatePriorityChecks(7, "1");
            } else {
                updatePriorityChecks(7, "0");
            }
        });

        priority_signs9.setOnClickListener(v -> {
            if (priority_signs9.isChecked()) {
                updatePriorityChecks(8, "1");
            } else {
                updatePriorityChecks(8, "0");
            }
        });

        priority_signs10.setOnClickListener(v -> {
            if (priority_signs10.isChecked()) {
                updatePriorityChecks(9, "1");
            } else {
                updatePriorityChecks(9, "0");
            }
        });

        priority_signs11.setOnClickListener(v -> {
            if (priority_signs11.isChecked()) {
                updatePriorityChecks(10, "1");
            } else {
                updatePriorityChecks(10, "0");
            }
        });

        priority_signs12.setOnClickListener(v -> {
            if (priority_signs12.isChecked()) {
                updatePriorityChecks(11, "1");
            } else {
                updatePriorityChecks(11, "0");
            }
        });

        priority_signs13.setOnClickListener(v -> {
            if (priority_signs13.isChecked()) {
                updatePriorityChecks(12, "1");
            } else {
                updatePriorityChecks(12, "0");
            }
        });

        priority_signs14.setOnClickListener(v -> {
            if (priority_signs14.isChecked()) {
                updatePriorityChecks(13, "1");
            } else {
                updatePriorityChecks(13, "0");
            }
        });

        priority_signs15.setOnClickListener(v -> {
            if (priority_signs15.isChecked()) {
                updatePriorityChecks(14, "1");
            } else {
                updatePriorityChecks(14, "0");
            }
        });

        getPatientDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_triage_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_vitals) {
            addVitals();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addVitals(View view){
        addVitals();
    }

    public void addVitals(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_vitals_triage_dialog, null);

        final EditText systolic_bp_view = view.findViewById(R.id.systolic_bp);
        final EditText diastolic_bp_view = view.findViewById(R.id.diastolic_bp);
        final EditText heart_rate_view = view.findViewById(R.id.heart_rate);
        final EditText resp_rate_view = view.findViewById(R.id.resp_rate);
        final EditText temperature_view = view.findViewById(R.id.temperature);
        final EditText oxygen_saturation_view  = view.findViewById(R.id.oxygen_saturation);
        final EditText avpu_view  = view.findViewById(R.id.avpu);

        systolic_bp_view.setText(systolic_bp);
        diastolic_bp_view.setText(diastolic_bp);
        heart_rate_view.setText(heart_rate);
        resp_rate_view.setText(resp_rate);
        temperature_view.setText(temperature);
        oxygen_saturation_view.setText(oxygen_saturation);
        avpu_view.setText(avpu);

        avpu_view.setFocusable(false);
        avpu_view.setOnClickListener(view1 -> {
            String[] mStringArray = {"Alert","Responds to Voice","Responds to Pain", "Unresponsive"};

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("Choose Option");

            builder.setItems(mStringArray, (dialogInterface, i) -> {
                avpu = mStringArray[i];
                avpu_view.setText(mStringArray[i]);
            });

            // create and show the alert dialog
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setView(view);

        builder.setCancelable(false)
                .setNeutralButton("Clear", null)
                .setPositiveButton("Save", null)
                .setNegativeButton("Close", (dialog, id) -> dialog.cancel());

        final AlertDialog dialog = builder.create();

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {

            if (systolic_bp_view.getText().toString().isEmpty() || heart_rate_view.getText().toString().isEmpty()) {
                androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(PatientTriageHomeActivity.this);
                alertDialog.setTitle("Missing values");
                alertDialog.setMessage("You have not recorded any vitals for SBP or pulse. Do you want to record them now?");
                alertDialog.setCancelable(false)
                        .setPositiveButton("Yes",
                                (dialog1, id) -> dialog1.dismiss())
                        .setNegativeButton("No",
                                (dialog1, id) -> {
                                    systolic_bp = systolic_bp_view.getText().toString();
                                    diastolic_bp = diastolic_bp_view.getText().toString();
                                    heart_rate = heart_rate_view.getText().toString();
                                    resp_rate = resp_rate_view.getText().toString();
                                    temperature = temperature_view.getText().toString();
                                    oxygen_saturation  = oxygen_saturation_view.getText().toString();
                                    avpu  = avpu_view.getText().toString();

                                    saveVitals("0");

                                    displayVitalsTaken();

                                    dialog.cancel();
                                });
                alertDialog.create();
                alertDialog.show();
            } else {
                systolic_bp = systolic_bp_view.getText().toString();
                diastolic_bp = diastolic_bp_view.getText().toString();
                heart_rate = heart_rate_view.getText().toString();
                resp_rate = resp_rate_view.getText().toString();
                temperature = temperature_view.getText().toString();
                oxygen_saturation  = oxygen_saturation_view.getText().toString();
                avpu  = avpu_view.getText().toString();

                saveVitals("0");

                displayVitalsTaken();

                dialog.cancel();
            }
        });

        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        neutralButton.setOnClickListener(v -> {
            // clear the given selections
            systolic_bp_view.setText("");
            diastolic_bp_view.setText("");
            heart_rate_view.setText("");
            resp_rate_view.setText("");
            temperature_view.setText("");
            oxygen_saturation_view.setText("");
            avpu_view.setText("");

            systolic_bp = "";
            diastolic_bp = "";
            heart_rate = "";
            resp_rate = "";
            temperature = "";
            oxygen_saturation  = "";
            avpu  = "";

            displayVitalsTaken();
        });
    }

    public void saveVitals(String is_complete) {
        new DataRouter(this).updateVitals(systolic_bp, diastolic_bp,
                heart_rate, resp_rate, temperature, oxygen_saturation, avpu, is_complete);
    }

    public void getPatientVitals(){
        String url = router.ip_address + "sims_patients/get_patient_vitals/" + patient_id + "/" + episode_id;

        @SuppressLint("SetTextI18n") JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    if (response.length() > 0){
                        JSONObject jsonobject;
                        try {
                            // fetch the latest results
                            jsonobject = response.getJSONObject(response.length() - 1);
                            systolic_bp = jsonobject.isNull("systolic_bp") ? "" : String.valueOf(jsonobject.getInt("systolic_bp"));
                            diastolic_bp = jsonobject.isNull("diastolic_bp") ? "" : String.valueOf(jsonobject.getInt("diastolic_bp"));
                            heart_rate = jsonobject.isNull("heart_rate") ? "" : String.valueOf(jsonobject.getInt("heart_rate"));
                            resp_rate = jsonobject.isNull("respiratory_rate") ? "" : String.valueOf(jsonobject.getInt("respiratory_rate"));
                            temperature = jsonobject.isNull("temperature") ? "" : String.valueOf(jsonobject.getInt("temperature"));
                            oxygen_saturation = jsonobject.isNull("oxygen_saturation") ? "" : String.valueOf(jsonobject.getInt("oxygen_saturation"));
                            avpu = jsonobject.isNull("avpu") ? "" : jsonobject.getString("avpu");
                            displayVitalsTaken();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    @SuppressLint("SetTextI18n")
    public void displayVitalsTaken() {
        TextView pinpoint_pupils_rr_and_spo2_naloxone = findViewById(R.id.pinpoint_pupils_rr_and_spo2_naloxone);
        TextView sbp_pulse_heavy_bleeding = findViewById(R.id.sbp_pulse_heavy_bleeding);
        TextView spo2_oxygen = findViewById(R.id.spo2_oxygen);
        TextView spo2_oxygen1 = findViewById(R.id.spo2_oxygen1);
        TextView spo2_oxygen2 = findViewById(R.id.spo2_oxygen2);
        TextView spo2_oxygen3 = findViewById(R.id.spo2_oxygen3);
        TextView avpu_measured = findViewById(R.id.avpu_measured);
        TextView low_sbp = findViewById(R.id.low_sbp);
        TextView oxygen_spo2_resp_distress = findViewById(R.id.oxygen_spo2_resp_distress);
        TextView check_sbp_pulse = findViewById(R.id.check_sbp_pulse);

        if (!systolic_bp.equals("") && !heart_rate.equals("")){
            check_sbp_pulse.setText("SBP is " + systolic_bp + " and Pulse is " + heart_rate);
            check_sbp_pulse.setVisibility(View.VISIBLE);

            if (checks[3].equals("1") || checks[4].equals("1") || checks[5].equals("1") ||
                    checks[6].equals("1")) {
                sbp_pulse_heavy_bleeding.setText("SBP is " + systolic_bp + " and Pulse is " + heart_rate);
                sbp_pulse_heavy_bleeding.setVisibility(View.GONE);
            }

            if (Integer.parseInt(systolic_bp) < 90 || Integer.parseInt(heart_rate) > 110) {
                // patient in shock
                trauma_check_circulation.performClick();
            }
        } else {
            sbp_pulse_heavy_bleeding.setText("");
            sbp_pulse_heavy_bleeding.setVisibility(View.GONE);
            check_sbp_pulse.setText("");
            check_sbp_pulse.setVisibility(View.GONE);
        }

        if (!resp_rate.equals("") && !oxygen_saturation.equals("")){
            if (checks[7].equals("1") || checks[8].equals("1") || checks[9].equals("1")) {
                pinpoint_pupils_rr_and_spo2_naloxone.setText("RR is " + resp_rate + " and SpO2 is " + oxygen_saturation);
                pinpoint_pupils_rr_and_spo2_naloxone.setVisibility(View.VISIBLE);
            }
        } else {
            pinpoint_pupils_rr_and_spo2_naloxone.setText("");
            pinpoint_pupils_rr_and_spo2_naloxone.setVisibility(View.GONE);
        }

        if (!oxygen_saturation.equals("")){
            if (checks[10].equals("1") || checks[11].equals("1")) {
                spo2_oxygen.setText("SpO2 is " + oxygen_saturation);
                spo2_oxygen.setVisibility(View.VISIBLE);
            }

            if (checks[15].equals("1")){
                spo2_oxygen1.setText("SpO2 is " + oxygen_saturation);
                spo2_oxygen1.setVisibility(View.VISIBLE);
            }

            if (checks[16].equals("1")){
                spo2_oxygen2.setText("SpO2 is " + oxygen_saturation);
                spo2_oxygen2.setVisibility(View.VISIBLE);
            }

            if (checks[17].equals("1")){
                spo2_oxygen3.setText("SpO2 is " + oxygen_saturation);
                spo2_oxygen3.setVisibility(View.VISIBLE);
            }

            oxygen_spo2_resp_distress.setText("SpO2 is " + oxygen_saturation);
            oxygen_spo2_resp_distress.setVisibility(View.VISIBLE);
        } else {
            spo2_oxygen.setText("");
            spo2_oxygen.setVisibility(View.GONE);
            spo2_oxygen1.setText("");
            spo2_oxygen1.setVisibility(View.GONE);
            spo2_oxygen2.setText("");
            spo2_oxygen2.setVisibility(View.GONE);
            spo2_oxygen3.setText("");
            spo2_oxygen3.setVisibility(View.GONE);
            oxygen_spo2_resp_distress.setText("");
            oxygen_spo2_resp_distress.setVisibility(View.GONE);
        }

        if(!avpu.equals("")){
            action_prompt27_checkbox.performClick();
            avpu_measured.setText("Measured AVPU: " + avpu);
            avpu_measured.setVisibility(View.VISIBLE);
        } else {
            avpu_measured.setText("");
            avpu_measured.setVisibility(View.GONE);
        }

        if (!systolic_bp.equals("")){
            if (checks[18].equals("1")){
                low_sbp.setText("SBP is " + systolic_bp);
                low_sbp.setVisibility(View.VISIBLE);
            }
        } else {
            low_sbp.setText("");
            low_sbp.setVisibility(View.GONE);
        }
    }

    public void findViews() {
        action_trauma = findViewById(R.id.action_trauma);
        action_trauma1 = findViewById(R.id.action_trauma1);
        action_trauma2 = findViewById(R.id.action_trauma2);
        action_trauma7 = findViewById(R.id.action_trauma7);
        action_trauma8 = findViewById(R.id.action_trauma8);
        action_trauma9 = findViewById(R.id.action_trauma9);
        action_trauma10 = findViewById(R.id.action_trauma10);
        action_trauma11 = findViewById(R.id.action_trauma11);
        action_trauma12 = findViewById(R.id.action_trauma12);
        action_trauma13 = findViewById(R.id.action_trauma13);
        action_trauma14 = findViewById(R.id.action_trauma14);
        action_trauma15 = findViewById(R.id.action_trauma15);
        action_trauma16 = findViewById(R.id.action_trauma16);
        action_trauma17 = findViewById(R.id.action_trauma17);
        action_trauma18 = findViewById(R.id.action_trauma18);
        action_trauma19 = findViewById(R.id.action_trauma19);
        action_trauma20 = findViewById(R.id.action_trauma20);
        action_trauma21 = findViewById(R.id.action_trauma21);
        action_trauma22 = findViewById(R.id.action_trauma22);
        action_trauma23 = findViewById(R.id.action_trauma23);
        action_trauma29 = findViewById(R.id.action_trauma29);
        action_trauma28 = findViewById(R.id.action_trauma28);
        action_trauma27 = findViewById(R.id.action_trauma27);
        action_trauma26 = findViewById(R.id.action_trauma26);
        action_trauma25 = findViewById(R.id.action_trauma25);
        action_trauma24 = findViewById(R.id.action_trauma24);
        action_trauma_textview = findViewById(R.id.action_trauma_textview);
        action_trauma1_textview = findViewById(R.id.action_trauma1_textview);
        action_trauma2_textview = findViewById(R.id.action_trauma2_textview);
        action_trauma7_textview = findViewById(R.id.action_trauma7_textview);
        action_trauma8_textview = findViewById(R.id.action_trauma8_textview);
        action_trauma9_textview = findViewById(R.id.action_trauma9_textview);
        action_trauma10_textview = findViewById(R.id.action_trauma10_textview);
        action_trauma11_textview = findViewById(R.id.action_trauma11_textview);
        action_trauma12_textview = findViewById(R.id.action_trauma12_textview);
        action_trauma13_textview = findViewById(R.id.action_trauma13_textview);
        action_trauma14_textview = findViewById(R.id.action_trauma14_textview);
        action_trauma15_textview = findViewById(R.id.action_trauma15_textview);
        action_trauma16_textview = findViewById(R.id.action_trauma16_textview);
        action_trauma17_textview = findViewById(R.id.action_trauma17_textview);
        action_trauma18_textview = findViewById(R.id.action_trauma18_textview);
        action_trauma19_textview = findViewById(R.id.action_trauma19_textview);
        action_trauma20_textview = findViewById(R.id.action_trauma20_textview);
        action_trauma21_textview = findViewById(R.id.action_trauma21_textview);
        action_trauma22_textview = findViewById(R.id.action_trauma22_textview);
        action_trauma23_textview = findViewById(R.id.action_trauma23_textview);
        action_trauma24_textview = findViewById(R.id.action_trauma24_textview);
        action_trauma25_textview = findViewById(R.id.action_trauma25_textview);
        action_trauma26_textview = findViewById(R.id.action_trauma26_textview);
        action_trauma27_textview = findViewById(R.id.action_trauma27_textview);
        action_trauma28_textview = findViewById(R.id.action_trauma28_textview);
        action_trauma29_textview = findViewById(R.id.action_trauma29_textview);
        action_trauma_checkbox = findViewById(R.id.action_trauma_checkbox);
        action_trauma1_checkbox = findViewById(R.id.action_trauma1_checkbox);
        action_trauma2_checkbox = findViewById(R.id.action_trauma2_checkbox);
        action_trauma7_checkbox = findViewById(R.id.action_trauma7_checkbox);
        action_trauma8_checkbox = findViewById(R.id.action_trauma8_checkbox);
        action_trauma9_checkbox = findViewById(R.id.action_trauma9_checkbox);
        action_trauma10_checkbox = findViewById(R.id.action_trauma10_checkbox);
        action_trauma11_checkbox = findViewById(R.id.action_trauma11_checkbox);
        action_trauma12_checkbox = findViewById(R.id.action_trauma12_checkbox);
        action_trauma13_checkbox = findViewById(R.id.action_trauma13_checkbox);
        action_trauma14_checkbox = findViewById(R.id.action_trauma14_checkbox);
        action_trauma15_checkbox = findViewById(R.id.action_trauma15_checkbox);
        action_trauma16_checkbox = findViewById(R.id.action_trauma16_checkbox);
        action_trauma17_checkbox = findViewById(R.id.action_trauma17_checkbox);
        action_trauma18_checkbox = findViewById(R.id.action_trauma18_checkbox);
        action_trauma19_checkbox = findViewById(R.id.action_trauma19_checkbox);
        action_trauma20_checkbox = findViewById(R.id.action_trauma20_checkbox);
        action_trauma21_checkbox = findViewById(R.id.action_trauma21_checkbox);
        action_trauma22_checkbox = findViewById(R.id.action_trauma22_checkbox);
        action_trauma23_checkbox = findViewById(R.id.action_trauma23_checkbox);
        action_trauma24_checkbox = findViewById(R.id.action_trauma24_checkbox);
        action_trauma25_checkbox = findViewById(R.id.action_trauma25_checkbox);
        action_trauma26_checkbox = findViewById(R.id.action_trauma26_checkbox);
        action_trauma27_checkbox = findViewById(R.id.action_trauma27_checkbox);
        action_trauma28_checkbox = findViewById(R.id.action_trauma28_checkbox);
        action_trauma29_checkbox = findViewById(R.id.action_trauma29_checkbox);
        row_line_trauma1 = findViewById(R.id.row_line_trauma1);
        row_line_trauma2 = findViewById(R.id.row_line_trauma2);
        row_line_trauma3 = findViewById(R.id.row_line_trauma3);
        row_line_trauma4 = findViewById(R.id.row_line_trauma4);
        row_line_trauma5 = findViewById(R.id.row_line_trauma5);
        row_line_trauma11 = findViewById(R.id.row_line_trauma11);
        row_line_trauma12 = findViewById(R.id.row_line_trauma12);
        row_line_trauma23 = findViewById(R.id.row_line_trauma23);
        row_line_trauma26 = findViewById(R.id.row_line_trauma26);
        row_line_trauma28 = findViewById(R.id.row_line_trauma28);
        row_trauma2 = findViewById(R.id.row_trauma2);
        row_trauma3 = findViewById(R.id.row_trauma3);
        row_trauma4 = findViewById(R.id.row_trauma4);
        row_trauma5 = findViewById(R.id.row_trauma5);
        row_trauma6 = findViewById(R.id.row_trauma6);
        row_trauma12 = findViewById(R.id.row_trauma12);
        row_trauma13 = findViewById(R.id.row_trauma13);
        row_trauma23 = findViewById(R.id.row_trauma23);
        row_trauma24 = findViewById(R.id.row_trauma24);
        row_trauma27 = findViewById(R.id.row_trauma27);
        row_trauma29 = findViewById(R.id.row_trauma29);
        radio_trauma2_yes = findViewById(R.id.radio_trauma2_yes);
        radio_trauma2_no = findViewById(R.id.radio_trauma2_no);
        radio_trauma2_not = findViewById(R.id.radio_trauma2_not);
        radio_trauma3_yes = findViewById(R.id.radio_trauma3_yes);
        radio_trauma3_no = findViewById(R.id.radio_trauma3_no);
        radio_trauma3_not = findViewById(R.id.radio_trauma3_not);
        radio_trauma4_yes = findViewById(R.id.radio_trauma4_yes);
        radio_trauma4_no = findViewById(R.id.radio_trauma4_no);
        radio_trauma4_not = findViewById(R.id.radio_trauma4_not);
        radio_trauma5_yes = findViewById(R.id.radio_trauma5_yes);
        radio_trauma5_no = findViewById(R.id.radio_trauma5_no);
        radio_trauma5_not = findViewById(R.id.radio_trauma5_not);
        radio_trauma6_yes = findViewById(R.id.radio_trauma6_yes);
        radio_trauma6_no = findViewById(R.id.radio_trauma6_no);
        radio_trauma6_not = findViewById(R.id.radio_trauma6_not);
        radio_trauma12_yes = findViewById(R.id.radio_trauma12_yes);
        radio_trauma12_no = findViewById(R.id.radio_trauma12_no);
        radio_trauma12_not = findViewById(R.id.radio_trauma12_not);
        radio_trauma13_yes = findViewById(R.id.radio_trauma13_yes);
        radio_trauma13_no = findViewById(R.id.radio_trauma13_no);
        radio_trauma13_not = findViewById(R.id.radio_trauma13_not);
        radio_trauma23_yes = findViewById(R.id.radio_trauma23_yes);
        radio_trauma23_no = findViewById(R.id.radio_trauma23_no);
        radio_trauma23_not = findViewById(R.id.radio_trauma23_not);
        radio_trauma24_yes = findViewById(R.id.radio_trauma24_yes);
        radio_trauma24_no = findViewById(R.id.radio_trauma24_no);
        radio_trauma24_not = findViewById(R.id.radio_trauma24_not);
        radio_trauma27_yes = findViewById(R.id.radio_trauma27_yes);
        radio_trauma27_no = findViewById(R.id.radio_trauma27_no);
        radio_trauma27_not = findViewById(R.id.radio_trauma27_not);
        radio_trauma29_yes = findViewById(R.id.radio_trauma29_yes);
        radio_trauma29_no = findViewById(R.id.radio_trauma29_no);
        radio_trauma29_not = findViewById(R.id.radio_trauma29_not);

        section_prompt = findViewById(R.id.section_prompt);
        section_prompt1 = findViewById(R.id.section_prompt1);
        section_prompt2 = findViewById(R.id.section_prompt2);
        section_prompt3 = findViewById(R.id.section_prompt3);

        section_prompt_textview = findViewById(R.id.section_prompt_textview);
        section_prompt1_textview = findViewById(R.id.section_prompt1_textview);
        section_prompt2_textview = findViewById(R.id.section_prompt2_textview);
        section_prompt3_textview = findViewById(R.id.section_prompt3_textview);

        section_prompt_checkbox = findViewById(R.id.section_prompt_checkbox);
        section_prompt1_checkbox = findViewById(R.id.section_prompt1_checkbox);
        section_prompt2_checkbox = findViewById(R.id.section_prompt2_checkbox);
        section_prompt3_checkbox = findViewById(R.id.section_prompt3_checkbox);

        action_prompt1 = findViewById(R.id.action_prompt1);
        action_prompt2 = findViewById(R.id.action_prompt2);
        action_prompt3 = findViewById(R.id.action_prompt3);
        action_prompt4 = findViewById(R.id.action_prompt4);
        action_prompt5 = findViewById(R.id.action_prompt5);
        action_prompt6 = findViewById(R.id.action_prompt6);
        action_prompt7 = findViewById(R.id.action_prompt7);
        action_prompt8 = findViewById(R.id.action_prompt8);
        action_prompt9 = findViewById(R.id.action_prompt9);
        action_prompt11 = findViewById(R.id.action_prompt11);
        action_prompt12 = findViewById(R.id.action_prompt12);
        action_prompt13 = findViewById(R.id.action_prompt13);
        action_prompt14 = findViewById(R.id.action_prompt14);
        action_prompt16 = findViewById(R.id.action_prompt16);
        action_prompt17 = findViewById(R.id.action_prompt17);
        action_prompt_new_17 = findViewById(R.id.action_prompt_new_17);
        action_prompt_new = findViewById(R.id.action_prompt_new);
        action_prompt_new1 = findViewById(R.id.action_prompt_new1);
        action_prompt_25 = findViewById(R.id.action_prompt25);
        action_prompt18 = findViewById(R.id.action_prompt18);
        action_prompt19 = findViewById(R.id.action_prompt19);
        action_prompt20 = findViewById(R.id.action_prompt20);
        action_prompt21 = findViewById(R.id.action_prompt21);
        action_prompt22 = findViewById(R.id.action_prompt22);
        action_prompt23 = findViewById(R.id.action_prompt23);
        action_prompt24 = findViewById(R.id.action_prompt24);
        action_prompt26 = findViewById(R.id.action_prompt26);
        action_prompt27 = findViewById(R.id.action_prompt27);
        action_prompt29 = findViewById(R.id.action_prompt29);
        action_prompt30 = findViewById(R.id.action_prompt30);
        action_prompt31 = findViewById(R.id.action_prompt31);
        action_prompt33 = findViewById(R.id.action_prompt33);
        action_prompt34 = findViewById(R.id.action_prompt34);
        action_prompt36 = findViewById(R.id.action_prompt36);
        action_prompt37 = findViewById(R.id.action_prompt37);
        action_prompt38 = findViewById(R.id.action_prompt38);
        action_prompt39 = findViewById(R.id.action_prompt39);
        action_prompt40 = findViewById(R.id.action_prompt40);
        action_prompt41 = findViewById(R.id.action_prompt41);
        action_prompt42 = findViewById(R.id.action_prompt42);
        action_prompt43 = findViewById(R.id.action_prompt43);
        action_prompt44 = findViewById(R.id.action_prompt44);
        action_prompt46 = findViewById(R.id.action_prompt46);
        action_prompt47 = findViewById(R.id.action_prompt47);
        action_prompt48 = findViewById(R.id.action_prompt48);
        action_prompt49 = findViewById(R.id.action_prompt49);
        action_prompt51 = findViewById(R.id.action_prompt51);
        action_prompt52 = findViewById(R.id.action_prompt52);

        action_prompt1_textview = findViewById(R.id.action_prompt1_textview);
        action_prompt2_textview = findViewById(R.id.action_prompt2_textview);
        action_prompt3_textview = findViewById(R.id.action_prompt3_textview);
        action_prompt4_textview = findViewById(R.id.action_prompt4_textview);
        action_prompt5_textview = findViewById(R.id.action_prompt5_textview);
        action_prompt6_textview = findViewById(R.id.action_prompt6_textview);
        action_prompt7_textview = findViewById(R.id.action_prompt7_textview);
        action_prompt8_textview = findViewById(R.id.action_prompt8_textview);
        action_prompt9_textview = findViewById(R.id.action_prompt9_textview);
        action_prompt11_textview = findViewById(R.id.action_prompt11_textview);
        action_prompt12_textview = findViewById(R.id.action_prompt12_textview);
        action_prompt13_textview = findViewById(R.id.action_prompt13_textview);
        action_prompt14_textview = findViewById(R.id.action_prompt14_textview);
        action_prompt16_textview = findViewById(R.id.action_prompt16_textview);
        action_prompt17_textview = findViewById(R.id.action_prompt17_textview);
        action_prompt18_textview = findViewById(R.id.action_prompt18_textview);
        action_prompt_new_17_textview = findViewById(R.id.action_prompt_new_17_textview);
        action_prompt_new_textview = findViewById(R.id.action_prompt_new_textview);
        action_prompt_new1_textview = findViewById(R.id.action_prompt_new1_textview);
        action_prompt25_textview = findViewById(R.id.action_prompt25_textview);
        action_prompt19_textview = findViewById(R.id.action_prompt19_textview);
        action_prompt20_textview = findViewById(R.id.action_prompt20_textview);
        action_prompt21_textview = findViewById(R.id.action_prompt21_textview);
        action_prompt22_textview = findViewById(R.id.action_prompt22_textview);
        action_prompt23_textview = findViewById(R.id.action_prompt23_textview);
        action_prompt24_textview = findViewById(R.id.action_prompt24_textview);
        action_prompt26_textview = findViewById(R.id.action_prompt26_textview);
        action_prompt27_textview = findViewById(R.id.action_prompt27_textview);
        action_prompt29_textview = findViewById(R.id.action_prompt29_textview);
        action_prompt30_textview = findViewById(R.id.action_prompt30_textview);
        action_prompt31_textview = findViewById(R.id.action_prompt31_textview);
        action_prompt33_textview = findViewById(R.id.action_prompt33_textview);
        action_prompt34_textview = findViewById(R.id.action_prompt34_textview);
        action_prompt36_textview = findViewById(R.id.action_prompt36_textview);
        action_prompt37_textview = findViewById(R.id.action_prompt37_textview);
        action_prompt38_textview = findViewById(R.id.action_prompt38_textview);
        action_prompt39_textview = findViewById(R.id.action_prompt39_textview);
        action_prompt40_textview = findViewById(R.id.action_prompt40_textview);
        action_prompt41_textview = findViewById(R.id.action_prompt41_textview);
        action_prompt42_textview = findViewById(R.id.action_prompt42_textview);
        action_prompt43_textview = findViewById(R.id.action_prompt43_textview);
        action_prompt44_textview = findViewById(R.id.action_prompt44_textview);
        action_prompt46_textview = findViewById(R.id.action_prompt46_textview);
        action_prompt47_textview = findViewById(R.id.action_prompt47_textview);
        action_prompt48_textview = findViewById(R.id.action_prompt48_textview);
        action_prompt49_textview = findViewById(R.id.action_prompt49_textview);
        action_prompt51_textview = findViewById(R.id.action_prompt51_textview);
        action_prompt52_textview = findViewById(R.id.action_prompt52_textview);

        action_prompt1_checkbox = findViewById(R.id.action_prompt1_checkbox);
        action_prompt2_checkbox = findViewById(R.id.action_prompt2_checkbox);
        action_prompt3_checkbox = findViewById(R.id.action_prompt3_checkbox);
        action_prompt4_checkbox = findViewById(R.id.action_prompt4_checkbox);
        action_prompt5_checkbox = findViewById(R.id.action_prompt5_checkbox);
        action_prompt6_checkbox = findViewById(R.id.action_prompt6_checkbox);
        action_prompt7_checkbox = findViewById(R.id.action_prompt7_checkbox);
        action_prompt8_checkbox = findViewById(R.id.action_prompt8_checkbox);
        action_prompt9_checkbox = findViewById(R.id.action_prompt9_checkbox);
        action_prompt11_checkbox = findViewById(R.id.action_prompt11_checkbox);
        action_prompt12_checkbox = findViewById(R.id.action_prompt12_checkbox);
        action_prompt13_checkbox = findViewById(R.id.action_prompt13_checkbox);
        action_prompt14_checkbox = findViewById(R.id.action_prompt14_checkbox);
        action_prompt16_checkbox = findViewById(R.id.action_prompt16_checkbox);
        action_prompt17_checkbox = findViewById(R.id.action_prompt17_checkbox);
        action_prompt_new_17_checkbox = findViewById(R.id.action_prompt_new_17_checkbox);
        action_prompt18_checkbox = findViewById(R.id.action_prompt18_checkbox);
        action_prompt18 = findViewById(R.id.action_prompt18);
        action_prompt_new_checkbox = findViewById(R.id.action_prompt_new_checkbox);
        action_prompt_new1_checkbox = findViewById(R.id.action_prompt_new1_checkbox);
        action_prompt25_checkbox = findViewById(R.id.action_prompt25_checkbox);
        action_prompt19_checkbox = findViewById(R.id.action_prompt19_checkbox);
        action_prompt20_checkbox = findViewById(R.id.action_prompt20_checkbox);
        action_prompt21_checkbox = findViewById(R.id.action_prompt21_checkbox);
        action_prompt22_checkbox = findViewById(R.id.action_prompt22_checkbox);
        action_prompt23_checkbox = findViewById(R.id.action_prompt23_checkbox);
        action_prompt24_checkbox = findViewById(R.id.action_prompt24_checkbox);
        action_prompt26_checkbox = findViewById(R.id.action_prompt26_checkbox);
        action_prompt27_checkbox = findViewById(R.id.action_prompt27_checkbox);
        action_prompt29_checkbox = findViewById(R.id.action_prompt29_checkbox);
        action_prompt30_checkbox = findViewById(R.id.action_prompt30_checkbox);
        action_prompt31_checkbox = findViewById(R.id.action_prompt31_checkbox);
        action_prompt33_checkbox = findViewById(R.id.action_prompt33_checkbox);
        action_prompt34_checkbox = findViewById(R.id.action_prompt34_checkbox);
        action_prompt36_checkbox = findViewById(R.id.action_prompt36_checkbox);
        action_prompt37_checkbox = findViewById(R.id.action_prompt37_checkbox);
        action_prompt38_checkbox = findViewById(R.id.action_prompt38_checkbox);
        action_prompt39_checkbox = findViewById(R.id.action_prompt39_checkbox);
        action_prompt40_checkbox = findViewById(R.id.action_prompt40_checkbox);
        action_prompt41_checkbox = findViewById(R.id.action_prompt41_checkbox);
        action_prompt42_checkbox = findViewById(R.id.action_prompt42_checkbox);
        action_prompt43_checkbox = findViewById(R.id.action_prompt43_checkbox);
        action_prompt44_checkbox = findViewById(R.id.action_prompt44_checkbox);
        action_prompt46_checkbox = findViewById(R.id.action_prompt46_checkbox);
        action_prompt47_checkbox = findViewById(R.id.action_prompt47_checkbox);
        action_prompt48_checkbox = findViewById(R.id.action_prompt48_checkbox);
        action_prompt49_checkbox = findViewById(R.id.action_prompt49_checkbox);
        action_prompt51_checkbox = findViewById(R.id.action_prompt51_checkbox);
        action_prompt52_checkbox = findViewById(R.id.action_prompt52_checkbox);

        radio1_yes = findViewById(R.id.radio1_yes);
        radio1_no = findViewById(R.id.radio1_no);
        radio1_not = findViewById(R.id.radio1_not);
        radio2_yes = findViewById(R.id.radio2_yes);
        radio2_no = findViewById(R.id.radio2_no);
        radio2_not = findViewById(R.id.radio2_not);
        radio5_yes = findViewById(R.id.radio5_yes);
        radio5_no = findViewById(R.id.radio5_no);
        radio5_not = findViewById(R.id.radio5_not);
        radio6_yes = findViewById(R.id.radio6_yes);
        radio6_no = findViewById(R.id.radio6_no);
        radio6_not = findViewById(R.id.radio6_not);
        radio8_yes = findViewById(R.id.radio8_yes);
        radio8_no = findViewById(R.id.radio8_no);
        radio8_not = findViewById(R.id.radio8_not);
        radio10_yes = findViewById(R.id.radio10_yes);
        radio10_no = findViewById(R.id.radio10_no);
        radio10_not = findViewById(R.id.radio10_not);
        radio13_yes = findViewById(R.id.radio13_yes);
        radio13_no = findViewById(R.id.radio13_no);
        radio13_not = findViewById(R.id.radio13_not);
        radio15_yes = findViewById(R.id.radio15_yes);
        radio15_no = findViewById(R.id.radio15_no);
        radio15_not = findViewById(R.id.radio15_not);
        radio16_yes = findViewById(R.id.radio16_yes);
        radio16_no = findViewById(R.id.radio16_no);
        radio16_not = findViewById(R.id.radio16_not);
        radio18_yes = findViewById(R.id.radio18_yes);
        radio18_no = findViewById(R.id.radio18_no);
        radio18_not = findViewById(R.id.radio18_not);
        radio19_yes = findViewById(R.id.radio19_yes);
        radio19_no = findViewById(R.id.radio19_no);
        radio19_not = findViewById(R.id.radio19_not);
        radio24_yes = findViewById(R.id.radio24_yes);
        radio24_no = findViewById(R.id.radio24_no);
        radio24_not = findViewById(R.id.radio24_not);
        radio25_yes = findViewById(R.id.radio25_yes);
        radio25_no = findViewById(R.id.radio25_no);
        radio25_not = findViewById(R.id.radio25_not);
        radio26_yes = findViewById(R.id.radio26_yes);
        radio26_no = findViewById(R.id.radio26_no);
        radio26_not = findViewById(R.id.radio26_not);
        radio28_yes = findViewById(R.id.radio28_yes);
        radio28_no = findViewById(R.id.radio28_no);
        radio28_not = findViewById(R.id.radio28_not);
        radio29_yes = findViewById(R.id.radio29_yes);
        radio29_no = findViewById(R.id.radio29_no);
        radio29_not = findViewById(R.id.radio29_not);
        radio32_yes = findViewById(R.id.radio32_yes);
        radio32_no = findViewById(R.id.radio32_no);
        radio32_not = findViewById(R.id.radio32_not);
        radio33_yes = findViewById(R.id.radio33_yes);
        radio33_no = findViewById(R.id.radio33_no);
        radio33_not = findViewById(R.id.radio33_not);
        radio35_yes = findViewById(R.id.radio35_yes);
        radio35_no = findViewById(R.id.radio35_no);
        radio35_not = findViewById(R.id.radio35_not);
        radio39_yes = findViewById(R.id.radio39_yes);
        radio39_no = findViewById(R.id.radio39_no);
        radio39_not = findViewById(R.id.radio39_not);
        radio45_yes = findViewById(R.id.radio45_yes);
        radio45_no = findViewById(R.id.radio45_no);
        radio45_not = findViewById(R.id.radio45_not);
        radio50_yes = findViewById(R.id.radio50_yes);
        radio50_no = findViewById(R.id.radio50_no);
        radio50_not = findViewById(R.id.radio50_not);

        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row5 = findViewById(R.id.row5);
        row6 = findViewById(R.id.row6);
        row8 = findViewById(R.id.row8);
        row10 = findViewById(R.id.row10);
        row13 = findViewById(R.id.row13);
        row15 = findViewById(R.id.row15);
        row16 = findViewById(R.id.row16);
        row18 = findViewById(R.id.row18);
        row19 = findViewById(R.id.row19);
        row24 = findViewById(R.id.row24);
        row25 = findViewById(R.id.row25);
        row26 = findViewById(R.id.row26);
        row28 = findViewById(R.id.row28);
        row29 = findViewById(R.id.row29);
        row32 = findViewById(R.id.row32);
        row33 = findViewById(R.id.row33);
        row35 = findViewById(R.id.row35);
        row39 = findViewById(R.id.row39);
        row45 = findViewById(R.id.row45);
        row50 = findViewById(R.id.row50);
        row52 = findViewById(R.id.row52);

        row_line5 = findViewById(R.id.row_line5);
        row_line6 = findViewById(R.id.row_line6);
        row_line8 = findViewById(R.id.row_line8);
        row_line15 = findViewById(R.id.row_line15);
        row_line16 = findViewById(R.id.row_line16);
        row_line18 = findViewById(R.id.row_line18);
        row_line19 = findViewById(R.id.row_line19);
        row_line24 = findViewById(R.id.row_line24);
        row_line25 = findViewById(R.id.row_line25);
        row_line27 = findViewById(R.id.row_line27);
        row_line28 = findViewById(R.id.row_line28);
        row_line29 = findViewById(R.id.row_line29);
        row_line31 = findViewById(R.id.row_line31);
        row_line32 = findViewById(R.id.row_line32);
        row_line34 = findViewById(R.id.row_line34);
        row_line38 = findViewById(R.id.row_line38);
        row_line44 = findViewById(R.id.row_line44);
        row_line49 = findViewById(R.id.row_line49);


        dropdown_view_one = findViewById(R.id.dropdown_view);
        dropdown_view_two = findViewById(R.id.dropdown_view_circulation);
        dropdown_view_three = findViewById(R.id.dropdown_view_disability);
        dropdown_view_four = findViewById(R.id.dropdown_view_life_threats);
        dropdown_view_five = findViewById(R.id.dropdown_view_priority_signs);

        emergency_check1 = findViewById(R.id.emergency_check1);
        emergency_check2 = findViewById(R.id.emergency_check2);
        emergency_check3 = findViewById(R.id.emergency_check3);
        emergency_check_none = findViewById(R.id.emergency_check_none);
        emergency_check4 = findViewById(R.id.emergency_check4);
        emergency_check5 = findViewById(R.id.emergency_check5);
        emergency_check6 = findViewById(R.id.emergency_check6);
//        emergency_check7 = findViewById(R.id.emergency_check7);
        emergency_check_none_two = findViewById(R.id.emergency_check_none_two);
        emergency_check8 = findViewById(R.id.emergency_check8);
        emergency_check9 = findViewById(R.id.emergency_check9);
        emergency_check10 = findViewById(R.id.emergency_check10);
        emergency_check11 = findViewById(R.id.emergency_check11);
        emergency_check12 = findViewById(R.id.emergency_check12);
        emergency_check13 = findViewById(R.id.emergency_check13);
        emergency_check14 = findViewById(R.id.emergency_check14);
        emergency_check15 = findViewById(R.id.emergency_check15);
        emergency_check16 = findViewById(R.id.emergency_check16);
        emergency_check17 = findViewById(R.id.emergency_check17);
        emergency_check18 = findViewById(R.id.emergency_check18);
        emergency_check19 = findViewById(R.id.emergency_check19);
        emergency_check_none_three = findViewById(R.id.emergency_check_none_three);

        emergency_check_none_four = findViewById(R.id.emergency_check_none_four);
        trauma_check_airway = findViewById(R.id.trauma_check_airway);
        trauma_check_circulation = findViewById(R.id.trauma_check_circulation);
        trauma_check_disability = findViewById(R.id.trauma_check_disability);
        trauma_check_expose1 = findViewById(R.id.trauma_check_expose1);
        trauma_check_expose2 = findViewById(R.id.trauma_check_expose2);
        trauma_check_expose3 = findViewById(R.id.trauma_check_expose3);

        priority_signs1 = findViewById(R.id.priority_signs1);
        priority_signs2 = findViewById(R.id.priority_signs2);
        priority_signs3 = findViewById(R.id.priority_signs3);
        priority_signs4 = findViewById(R.id.priority_signs4);
        priority_signs5 = findViewById(R.id.priority_signs5);
        priority_signs6 = findViewById(R.id.priority_signs6);
        priority_signs7 = findViewById(R.id.priority_signs7);
        priority_signs8 = findViewById(R.id.priority_signs8);
        priority_signs9 = findViewById(R.id.priority_signs9);
        priority_signs10 = findViewById(R.id.priority_signs10);
        priority_signs11 = findViewById(R.id.priority_signs11);
        priority_signs12 = findViewById(R.id.priority_signs12);
        priority_signs13 = findViewById(R.id.priority_signs13);
        priority_signs14 = findViewById(R.id.priority_signs14);
        priority_signs15 = findViewById(R.id.priority_signs15);

        caution = findViewById(R.id.caution);
        caution2 = findViewById(R.id.caution2);
        caution3 = findViewById(R.id.caution3);
        caution4 = findViewById(R.id.caution4);
        caution5 = findViewById(R.id.caution5);
        caution6 = findViewById(R.id.caution6);
        caution7 = findViewById(R.id.caution7);
        caution8 = findViewById(R.id.caution8);
        caution9 = findViewById(R.id.caution9);
        caution10 = findViewById(R.id.caution10);
        caution11 = findViewById(R.id.caution11);
        caution12 = findViewById(R.id.caution12);
        caution13 = findViewById(R.id.caution13);
        caution14 = findViewById(R.id.caution14);
        caution15 = findViewById(R.id.caution15);
        caution16 = findViewById(R.id.caution16);
        caution17 = findViewById(R.id.caution17);
        caution18 = findViewById(R.id.caution18);
        caution19 = findViewById(R.id.caution19);
        caution20 = findViewById(R.id.caution20);
        caution21 = findViewById(R.id.caution21);
        caution22 = findViewById(R.id.caution22);
        caution23 = findViewById(R.id.caution23);
        caution24 = findViewById(R.id.caution24);
        caution25 = findViewById(R.id.caution25);
        caution26 = findViewById(R.id.caution26);
        caution_new = findViewById(R.id.caution_new);
        caution_new1 = findViewById(R.id.caution_new1);
        caution_new3 = findViewById(R.id.caution_new3);

        prompt_label = findViewById(R.id.label_prompt);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (isBottomSheetUp) {
            isBottomSheetUp = false;
            Objects.requireNonNull(getSupportActionBar()).setTitle("Patient Triage");
            bottomSheetBehavior.setPeekHeight(0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            fab_review_triage.setVisibility(View.VISIBLE);
        } else if (isBottomSheetUpTrauma) {
            isBottomSheetUpTrauma = false;
            Objects.requireNonNull(getSupportActionBar()).setTitle("Patient Triage");
            bottomSheetBehaviorTrauma.setPeekHeight(0);
            bottomSheetBehaviorTrauma.setState(BottomSheetBehavior.STATE_COLLAPSED);
            fab_review_triage.setVisibility(View.VISIBLE);
        } else {
            startActivity(new Intent(this, PatientHomeActivity.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isBottomSheetUp) {
            isBottomSheetUp = false;
            Objects.requireNonNull(getSupportActionBar()).setTitle("Patient Triage");
            bottomSheetBehavior.setPeekHeight(0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            fab_review_triage.setVisibility(View.VISIBLE);
        } else if (isBottomSheetUpTrauma) {
            isBottomSheetUpTrauma = false;
            Objects.requireNonNull(getSupportActionBar()).setTitle("Patient Triage");
            bottomSheetBehaviorTrauma.setPeekHeight(0);
            bottomSheetBehaviorTrauma.setState(BottomSheetBehavior.STATE_COLLAPSED);
            fab_review_triage.setVisibility(View.VISIBLE);
        } else {
            startActivity(new Intent(this, PatientHomeActivity.class));
        }
    }

    private void getPreviousTriageDetailsEmergency(){
        String network_address = router.ip_address + "sims_patients/get_patient_latest_triage/" + patient_id + "/" + episode_id + "/1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        if (response.getInt("available") == 1){
                            String[] checks_array = response.getString("checks").split(",");
                            String[] checks_status_array = response.getString("checks_status").split(",");

                            emergency_prompts = checks_status_array;

                            if(checks_array.length == 25){
                                checks = checks_array;
                            }

                            for (int i = 0; i < checks_status_array.length; i++){
                                setCheckedStatus(String.valueOf(i), Integer.parseInt(checks_status_array[i]));
                            }

                            if (!response.getString("actions_required").equals("") && !response.getString("actions_status").equals("")){
                                setActionsStatus(response.getString("actions_required"), response.getString("actions_status"));
                            }

                            if (!response.getString("trauma_actions_required").equals("") && !response.getString("trauma_actions_status").equals("")){
                                setActionsStatusTrauma(response.getString("trauma_actions_required"), response.getString("trauma_actions_status"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getPreviousTriageDetailsPriority();
                }, error -> Toast.makeText(this, "Failed to get previous triage data", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }

    private void getPreviousTriageDetailsPriority(){
        String network_address = router.ip_address + "sims_patients/get_patient_latest_triage/" + patient_id + "/" + episode_id + "/2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        if (response.getInt("available") == 1){
                            String[] checks_array = response.getString("checks").split(",");

                            if(checks_array.length == 15){
                                priority_signs_checks = checks_array;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    updateCheckboxes();
                }, error -> Toast.makeText(this, "Failed to get previous triage data", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void setActionsStatus(String actions_required, String actions_status) {
        String[] actions_required_array = actions_required.split(",");
        String[] actions_status_array = actions_status.split(",");

        for (int i = 0; i < actions_required_array.length; i++) {
            emergency_actions_timestamps_map.replace(actions_required_array[i], Long.parseLong(actions_status_array[i]));

            // if statement here to switch through the actions
            switch (actions_required_array[i]) {
                case "1":
                    if (!actions_status_array[i].equals("0")) {
                        caution_4.setImageResource(R.drawable.ic_done);
                        action_prompt1.setBackgroundResource(R.color.lighter_green);
                        action_prompt1_checkbox.setChecked(true);
                        action_prompt1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "2":
                    if (!actions_status_array[i].equals("0")) {
                        caution_5.setImageResource(R.drawable.ic_done);
                        action_prompt2.setBackgroundResource(R.color.lighter_green);
                        action_prompt2_checkbox.setChecked(true);
                        action_prompt2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "3":
                    if (!actions_status_array[i].equals("0")) {
                        caution_1.setImageResource(R.drawable.ic_done);
                        action_prompt3.setBackgroundResource(R.color.lighter_green);
                        action_prompt3_checkbox.setChecked(true);
                        action_prompt3_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt3_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "4":
                    if (!actions_status_array[i].equals("0")) {
                        caution_2.setImageResource(R.drawable.ic_done);
                        action_prompt4.setBackgroundResource(R.color.lighter_green);
                        action_prompt4_checkbox.setChecked(true);
                        action_prompt4_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt4_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt4_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "5":
                    if (!actions_status_array[i].equals("0")) {
                        caution_6.setImageResource(R.drawable.ic_done);
                        action_prompt5.setBackgroundResource(R.color.lighter_green);
                        action_prompt5_checkbox.setChecked(true);
                        action_prompt5_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt5_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt5_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "6":
                    if (!actions_status_array[i].equals("0")) {
                        caution_7.setImageResource(R.drawable.ic_done);
                        action_prompt6.setBackgroundResource(R.color.lighter_green);
                        action_prompt6_checkbox.setChecked(true);
                        action_prompt6_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt6_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt6_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "7":
                    if (!actions_status_array[i].equals("0")) {
                        caution_3.setImageResource(R.drawable.ic_done);
                        action_prompt7.setBackgroundResource(R.color.lighter_green);
                        action_prompt7_checkbox.setChecked(true);
                        action_prompt7_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt7_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "8":
                    if (!actions_status_array[i].equals("0")) {
                        caution_8.setImageResource(R.drawable.ic_done);
                        action_prompt8.setBackgroundResource(R.color.lighter_green);
                        action_prompt8_checkbox.setChecked(true);
                        action_prompt8_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt8_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "9":
                    if (!actions_status_array[i].equals("0")) {
                        caution_12.setImageResource(R.drawable.ic_done);
                        action_prompt9.setBackgroundResource(R.color.lighter_green);
                        action_prompt9_checkbox.setChecked(true);
                        action_prompt9_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt9_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "11":
                    if (!actions_status_array[i].equals("0")) {
                        caution_13.setImageResource(R.drawable.ic_done);
                        action_prompt11.setBackgroundResource(R.color.lighter_green);
                        action_prompt11_checkbox.setChecked(true);
                        action_prompt11_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt11_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "12":
                    if (!actions_status_array[i].equals("0")) {
                        caution_14.setImageResource(R.drawable.ic_done);
                        action_prompt12.setBackgroundResource(R.color.lighter_green);
                        action_prompt12_checkbox.setChecked(true);
                        action_prompt12_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt12_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "13":
                    if (!actions_status_array[i].equals("0")) {
                        action_prompt13.setBackgroundResource(R.color.lighter_green);
                        action_prompt13_checkbox.setChecked(true);
                        action_prompt13_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt13_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "14":
                    if (!actions_status_array[i].equals("0")) {
                        action_prompt14.setBackgroundResource(R.color.lighter_green);
                        action_prompt14_checkbox.setChecked(true);
                        action_prompt14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

                case "15":
                    if (!actions_status_array[i].equals("0")) {
                        caution1.setImageResource(R.drawable.ic_done);
                        action_prompt18.setBackgroundResource(R.color.lighter_green);
                        action_prompt18_checkbox.setChecked(true);
                        action_prompt18_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt18_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt18_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "16":
                    if (!actions_status_array[i].equals("0")) {
                        caution_9.setImageResource(R.drawable.ic_done);
                        action_prompt16.setBackgroundResource(R.color.lighter_green);
                        action_prompt16_checkbox.setChecked(true);
                        action_prompt16_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt16_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt16_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "17":
                    if (!actions_status_array[i].equals("0")) {
                        caution_10.setImageResource(R.drawable.ic_done);
                        action_prompt17.setBackgroundResource(R.color.lighter_green);
                        action_prompt17_checkbox.setChecked(true);
                        action_prompt17_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt17_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

                case "18":
                    if (!actions_status_array[i].equals("0")) {
                        caution_new_10.setImageResource(R.drawable.ic_done);
                        action_prompt_new_17.setBackgroundResource(R.color.lighter_green);
                        action_prompt_new_17_checkbox.setChecked(true);
                        action_prompt_new_17_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt_new_17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt_new_17_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

                case "19":
                    if (!actions_status_array[i].equals("0")) {
                        caution_11.setImageResource(R.drawable.ic_done);
                        action_prompt19.setBackgroundResource(R.color.lighter_green);
                        action_prompt19_checkbox.setChecked(true);
                        action_prompt19_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt19_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt19_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "20":
                    if (!actions_status_array[i].equals("0")) {
                        caution_15.setImageResource(R.drawable.ic_done);
                        action_prompt20.setBackgroundResource(R.color.lighter_green);
                        action_prompt20_checkbox.setChecked(true);
                        action_prompt20_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt20_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt20_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "21":
                    if (!actions_status_array[i].equals("0")) {
                        caution_16.setImageResource(R.drawable.ic_done);
                        action_prompt21.setBackgroundResource(R.color.lighter_green);
                        action_prompt21_checkbox.setChecked(true);
                        action_prompt21_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt21_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt21_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "22":
                    if (!actions_status_array[i].equals("0")) {
                        caution_17.setImageResource(R.drawable.ic_done);
                        action_prompt22.setBackgroundResource(R.color.lighter_green);
                        action_prompt22_checkbox.setChecked(true);
                        action_prompt22_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt22_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt22_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "23":
                    if (!actions_status_array[i].equals("0")) {
                        caution_18.setImageResource(R.drawable.ic_done);
                        action_prompt23.setBackgroundResource(R.color.lighter_green);
                        action_prompt23_checkbox.setChecked(true);
                        action_prompt23_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt23_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt23_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "24":
                    if (!actions_status_array[i].equals("0")) {
                        caution_36.setImageResource(R.drawable.ic_done);
                        action_prompt24.setBackgroundResource(R.color.lighter_green);
                        action_prompt24_checkbox.setChecked(true);
                        action_prompt24_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt24_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt24_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "26":
                    if (!actions_status_array[i].equals("0")) {
                        caution_38.setImageResource(R.drawable.ic_done);
                        action_prompt26.setBackgroundResource(R.color.lighter_green);
                        action_prompt26_checkbox.setChecked(true);
                        action_prompt26_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt26_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt26_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "27":
                    if (!actions_status_array[i].equals("0")) {
                        caution_19.setImageResource(R.drawable.ic_done);
                        action_prompt27.setBackgroundResource(R.color.lighter_green);
                        action_prompt27_checkbox.setChecked(true);
                        action_prompt27_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt27_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt27_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "29":
                    if (!actions_status_array[i].equals("0")) {
                        caution_39.setImageResource(R.drawable.ic_done);
                        action_prompt29.setBackgroundResource(R.color.lighter_green);
                        action_prompt29_checkbox.setChecked(true);
                        action_prompt29_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt29_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt29_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "30":
                    if (!actions_status_array[i].equals("0")) {
                        caution_20.setImageResource(R.drawable.ic_done);
                        action_prompt30.setBackgroundResource(R.color.lighter_green);
                        action_prompt30_checkbox.setChecked(true);
                        action_prompt30_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt30_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt30_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "31":
                    if (!actions_status_array[i].equals("0")) {
                        caution_21.setImageResource(R.drawable.ic_done);
                        action_prompt31.setBackgroundResource(R.color.lighter_green);
                        action_prompt31_checkbox.setChecked(true);
                        action_prompt31_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt31_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt31_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "33":
                    if (!actions_status_array[i].equals("0")) {
                        caution_40.setImageResource(R.drawable.ic_done);
                        action_prompt33.setBackgroundResource(R.color.lighter_green);
                        action_prompt33_checkbox.setChecked(true);
                        action_prompt33_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt33_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt33_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "34":
                    if (!actions_status_array[i].equals("0")) {
                        caution_22.setImageResource(R.drawable.ic_done);
                        action_prompt34.setBackgroundResource(R.color.lighter_green);
                        action_prompt34_checkbox.setChecked(true);
                        action_prompt34_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt34_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt34_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "36":
                    if (!actions_status_array[i].equals("0")) {
                        caution_23.setImageResource(R.drawable.ic_done);
                        action_prompt36.setBackgroundResource(R.color.lighter_green);
                        action_prompt36_checkbox.setChecked(true);
                        action_prompt36_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt36_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt36_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "37":
                    if (!actions_status_array[i].equals("0")) {
                        caution_24.setImageResource(R.drawable.ic_done);
                        action_prompt37.setBackgroundResource(R.color.lighter_green);
                        action_prompt37_checkbox.setChecked(true);
                        action_prompt37_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt37_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt37_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "38":
                    if (!actions_status_array[i].equals("0")) {
                        caution_25.setImageResource(R.drawable.ic_done);
                        action_prompt38.setBackgroundResource(R.color.lighter_green);
                        action_prompt38_checkbox.setChecked(true);
                        action_prompt38_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt38_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt38_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "39":
                    if (!actions_status_array[i].equals("0")) {
                        caution_37.setImageResource(R.drawable.ic_done);
                        action_prompt39.setBackgroundResource(R.color.lighter_green);
                        action_prompt39_checkbox.setChecked(true);
                        action_prompt39_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt39_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt39_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "40":
                    if (!actions_status_array[i].equals("0")) {
                        caution_26.setImageResource(R.drawable.ic_done);
                        action_prompt40.setBackgroundResource(R.color.lighter_green);
                        action_prompt40_checkbox.setChecked(true);
                        action_prompt40_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt40_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt40_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "41":
                    if (!actions_status_array[i].equals("0")) {
                        caution_27.setImageResource(R.drawable.ic_done);
                        action_prompt41.setBackgroundResource(R.color.lighter_green);
                        action_prompt41_checkbox.setChecked(true);
                        action_prompt41_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt41_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt41_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "42":
                    if (!actions_status_array[i].equals("0")) {
                        caution_28.setImageResource(R.drawable.ic_done);
                        action_prompt42.setBackgroundResource(R.color.lighter_green);
                        action_prompt42_checkbox.setChecked(true);
                        action_prompt42_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt42_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt42_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "43":
                    if (!actions_status_array[i].equals("0")) {
                        caution_29.setImageResource(R.drawable.ic_done);
                        action_prompt43.setBackgroundResource(R.color.lighter_green);
                        action_prompt43_checkbox.setChecked(true);
                        action_prompt43_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt43_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt43_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "44":
                    if (!actions_status_array[i].equals("0")) {
                        caution_30.setImageResource(R.drawable.ic_done);
                        action_prompt44.setBackgroundResource(R.color.lighter_green);
                        action_prompt44_checkbox.setChecked(true);
                        action_prompt44_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt44_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt44_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "46":
                    if (!actions_status_array[i].equals("0")) {
                        caution_31.setImageResource(R.drawable.ic_done);
                        action_prompt46.setBackgroundResource(R.color.lighter_green);
                        action_prompt46_checkbox.setChecked(true);
                        action_prompt46_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt46_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt46_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "47":
                    if (!actions_status_array[i].equals("0")) {
                        caution_32.setImageResource(R.drawable.ic_done);
                        action_prompt47.setBackgroundResource(R.color.lighter_green);
                        action_prompt47_checkbox.setChecked(true);
                        action_prompt47_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt47_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt47_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "48":
                    if (!actions_status_array[i].equals("0")) {
                        caution_33.setImageResource(R.drawable.ic_done);
                        action_prompt48.setBackgroundResource(R.color.lighter_green);
                        action_prompt48_checkbox.setChecked(true);
                        action_prompt48_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt48_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt48_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "49":
                    if (!actions_status_array[i].equals("0")) {
                        caution_34.setImageResource(R.drawable.ic_done);
                        action_prompt49.setBackgroundResource(R.color.lighter_green);
                        action_prompt49_checkbox.setChecked(true);
                        action_prompt49_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt49_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt49_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "51":
                    if (!actions_status_array[i].equals("0")) {
                        caution_35.setImageResource(R.drawable.ic_done);
                        action_prompt51.setBackgroundResource(R.color.lighter_green);
                        action_prompt51_checkbox.setChecked(true);
                        action_prompt51_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt51_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt51_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "52":
                    if (!actions_status_array[i].equals("0")) {
                        section_prompt_imageview.setImageResource(R.drawable.ic_done);
                        section_prompt.setBackgroundResource(R.color.lighter_green);
                        section_prompt_checkbox.setChecked(true);
                        section_prompt_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "53":
                    if (!actions_status_array[i].equals("0")) {
                        section_prompt1_imageview.setImageResource(R.drawable.ic_done);
                        section_prompt1.setBackgroundResource(R.color.lighter_green);
                        section_prompt1_checkbox.setChecked(true);
                        section_prompt1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "54":
                    if (!actions_status_array[i].equals("0")) {
                        section_prompt2_imageview.setImageResource(R.drawable.ic_done);
                        section_prompt2.setBackgroundResource(R.color.lighter_green);
                        section_prompt2_checkbox.setChecked(true);
                        section_prompt2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "55":
                    if (!actions_status_array[i].equals("0")) {
                        section_prompt3_imageview.setImageResource(R.drawable.ic_done);
                        section_prompt3.setBackgroundResource(R.color.lighter_green);
                        section_prompt3_checkbox.setChecked(true);
                        section_prompt3_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        section_prompt3_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "56":
                    if (!actions_status_array[i].equals("0")) {
                        caution_41.setImageResource(R.drawable.ic_done);
                        action_prompt52.setBackgroundResource(R.color.lighter_green);
                        action_prompt52_checkbox.setChecked(true);
                        action_prompt52_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt52_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt52_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "57":
                    if (!actions_status_array[i].equals("0")) {
                        caution_new.setImageResource(R.drawable.ic_done);
                        action_prompt_new.setBackgroundResource(R.color.lighter_green);
                        action_prompt_new_checkbox.setChecked(true);
                        action_prompt_new_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt_new_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt_new_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

                case "58":
                    if (!actions_status_array[i].equals("0")) {
                        caution_new1.setImageResource(R.drawable.ic_done);
                        action_prompt_new1.setBackgroundResource(R.color.lighter_green);
                        action_prompt_new1_checkbox.setChecked(true);
                        action_prompt_new1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt_new1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt_new1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

                case "59":
                    if (!actions_status_array[i].equals("0")) {
                        caution_new3.setImageResource(R.drawable.ic_done);
                        action_prompt_25.setBackgroundResource(R.color.lighter_green);
                        action_prompt25_checkbox.setChecked(true);
                        action_prompt25_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt25_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_prompt25_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

            }
        }
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void setActionsStatusTrauma(String actions_required, String actions_status) {
        String[] actions_required_array = actions_required.split(",");
        String[] actions_status_array = actions_status.split(",");

        for (int i = 0; i < actions_required_array.length; i++) {
            trauma_timestamps_map.replace(actions_required_array[i], Long.parseLong(actions_status_array[i]));

            // if statement here to switch through the actions
            switch (actions_required_array[i]) {
                case "0":
                    if (!actions_status_array[i].equals("0")) {
                        action_trauma.setBackgroundResource(R.color.lighter_green);
                        caution.setImageResource(R.drawable.ic_done);
                        action_trauma_checkbox.setChecked(true);
                        action_trauma_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "1":
                    if (!actions_status_array[i].equals("0")) {
                        action_trauma1.setBackgroundResource(R.color.lighter_green);
                        action_trauma1_checkbox.setChecked(true);
                        action_trauma1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        caution2.setImageResource(R.drawable.ic_done);
                        action_trauma1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "2":
                    if (!actions_status_array[i].equals("0")) {
                        caution3.setImageResource(R.drawable.ic_done);
                        action_trauma2.setBackgroundResource(R.color.lighter_green);
                        action_trauma2_checkbox.setChecked(true);
                        action_trauma2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "7":
                    if (!actions_status_array[i].equals("0")) {
                        caution4.setImageResource(R.drawable.ic_done);
                        action_trauma7.setBackgroundResource(R.color.lighter_green);
                        action_trauma7_checkbox.setChecked(true);
                        action_trauma7_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma7_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "8":
                    if (!actions_status_array[i].equals("0")) {
                        caution5.setImageResource(R.drawable.ic_done);
                        action_trauma8.setBackgroundResource(R.color.lighter_green);
                        action_trauma8_checkbox.setChecked(true);
                        action_trauma8_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma8_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "9":
                    if (!actions_status_array[i].equals("0")) {
                        caution6.setImageResource(R.drawable.ic_done);
                        action_trauma9.setBackgroundResource(R.color.lighter_green);
                        action_trauma9_checkbox.setChecked(true);
                        action_trauma9_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma9_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "10":
                    if (!actions_status_array[i].equals("0")) {
                        caution7.setImageResource(R.drawable.ic_done);
                        action_trauma10.setBackgroundResource(R.color.lighter_green);
                        action_trauma10_checkbox.setChecked(true);
                        action_trauma10_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma10_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma10_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "11":
                    if (!actions_status_array[i].equals("0")) {
                        caution8.setImageResource(R.drawable.ic_done);
                        action_trauma11.setBackgroundResource(R.color.lighter_green);
                        action_trauma11_checkbox.setChecked(true);
                        action_trauma11_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma11_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "12":
                    if (!actions_status_array[i].equals("0")) {
                        caution9.setImageResource(R.drawable.ic_done);
                        action_trauma12.setBackgroundResource(R.color.lighter_green);
                        action_trauma12_checkbox.setChecked(true);
                        action_trauma12_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma12_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "13":
                    if (!actions_status_array[i].equals("0")) {
                        caution10.setImageResource(R.drawable.ic_done);
                        action_trauma13.setBackgroundResource(R.color.lighter_green);
                        action_trauma13_checkbox.setChecked(true);
                        action_trauma13_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma13_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "14":
                    if (!actions_status_array[i].equals("0")) {
                        caution11.setImageResource(R.drawable.ic_done);
                        action_trauma14.setBackgroundResource(R.color.lighter_green);
                        action_trauma14_checkbox.setChecked(true);
                        action_trauma14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "15":
                    if (!actions_status_array[i].equals("0")) {
                        caution12.setImageResource(R.drawable.ic_done);
                        action_trauma15.setBackgroundResource(R.color.lighter_green);
                        action_trauma15_checkbox.setChecked(true);
                        action_trauma15_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma15_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma15_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "16":
                    if (!actions_status_array[i].equals("0")) {
                        caution13.setImageResource(R.drawable.ic_done);
                        action_trauma16.setBackgroundResource(R.color.lighter_green);
                        action_trauma16_checkbox.setChecked(true);
                        action_trauma16_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma16_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma16_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "17":
                    if (!actions_status_array[i].equals("0")) {
                        caution14.setImageResource(R.drawable.ic_done);
                        action_trauma17.setBackgroundResource(R.color.lighter_green);
                        action_trauma17_checkbox.setChecked(true);
                        action_trauma17_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma17_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma17_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "18":
                    if (!actions_status_array[i].equals("0")) {
                        caution15.setImageResource(R.drawable.ic_done);
                        action_trauma18.setBackgroundResource(R.color.lighter_green);
                        action_trauma18_checkbox.setChecked(true);
                        action_trauma18_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma18_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma18_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "19":
                    if (!actions_status_array[i].equals("0")) {
                        caution16.setImageResource(R.drawable.ic_done);
                        action_trauma19.setBackgroundResource(R.color.lighter_green);
                        action_trauma19_checkbox.setChecked(true);
                        action_trauma19_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma19_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma19_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "20":
                    if (!actions_status_array[i].equals("0")) {
                        caution17.setImageResource(R.drawable.ic_done);
                        action_trauma20.setBackgroundResource(R.color.lighter_green);
                        action_trauma20_checkbox.setChecked(true);
                        action_trauma20_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma20_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma20_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "21":
                    if (!actions_status_array[i].equals("0")) {
                        caution18.setImageResource(R.drawable.ic_done);
                        action_trauma21.setBackgroundResource(R.color.lighter_green);
                        action_trauma21_checkbox.setChecked(true);
                        action_trauma21_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma21_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma21_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "22":
                    if (!actions_status_array[i].equals("0")) {
                        caution19.setImageResource(R.drawable.ic_done);
                        action_trauma22.setBackgroundResource(R.color.lighter_green);
                        action_trauma22_checkbox.setChecked(true);
                        action_trauma22_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma22_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma22_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "23":
                    if (!actions_status_array[i].equals("0")) {
                        caution20.setImageResource(R.drawable.ic_done);
                        action_trauma23.setBackgroundResource(R.color.lighter_green);
                        action_trauma23_checkbox.setChecked(true);
                        action_trauma23_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma23_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma23_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "24":
                    if (!actions_status_array[i].equals("0")) {
                        caution21.setImageResource(R.drawable.ic_done);
                        action_trauma24.setBackgroundResource(R.color.lighter_green);
                        action_trauma24_checkbox.setChecked(true);
                        action_trauma24_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma24_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma24_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "25":
                    if (!actions_status_array[i].equals("0")) {
                        caution22.setImageResource(R.drawable.ic_done);
                        action_trauma25.setBackgroundResource(R.color.lighter_green);
                        action_trauma25_checkbox.setChecked(true);
                        action_trauma25_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma25_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma25_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "26":
                    if (!actions_status_array[i].equals("0")) {
                        caution23.setImageResource(R.drawable.ic_done);
                        action_trauma26.setBackgroundResource(R.color.lighter_green);
                        action_trauma26_checkbox.setChecked(true);
                        action_trauma26_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma26_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma26_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "27":
                    if (!actions_status_array[i].equals("0")) {
                        caution24.setImageResource(R.drawable.ic_done);
                        action_trauma27.setBackgroundResource(R.color.lighter_green);
                        action_trauma27_checkbox.setChecked(true);
                        action_trauma27_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma27_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma27_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "28":
                    if (!actions_status_array[i].equals("0")) {
                        caution25.setImageResource(R.drawable.ic_done);
                        action_trauma28.setBackgroundResource(R.color.lighter_green);
                        action_trauma28_checkbox.setChecked(true);
                        action_trauma28_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma28_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma28_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;
                case "29":
                    if (!actions_status_array[i].equals("0")) {
                        caution26.setImageResource(R.drawable.ic_done);
                        action_trauma29.setBackgroundResource(R.color.lighter_green);
                        action_trauma29_checkbox.setChecked(true);
                        action_trauma29_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma29_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                        action_trauma29_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(actions_status_array[i]))));
                    }
                    break;

            }
        }
    }

    private void setCheckedStatus(String index, int value){
        switch (index){
            case "0":
                if (value == 2){
                    radio1_yes.setChecked(true);
                    action_prompt1.setVisibility(View.VISIBLE);
                    emergency_actions.add("1");
                } else if (value == 1) {
                    radio1_no.setChecked(true);
                } else if (value == 3) {
                    radio1_not.setChecked(true);
                }
                break;
            case "1":
                if (value == 2){
                    radio2_yes.setChecked(true);
                    action_prompt2.setVisibility(View.VISIBLE);
                    emergency_actions.add("2");
                } else if (value == 1) {
                    radio2_no.setChecked(true);
                } else if (value == 3) {
                    radio2_not.setChecked(true);
                }
                break;
            case "4":
                if (value == 2){
                    radio5_yes.setChecked(true);
                    action_prompt5.setVisibility(View.VISIBLE);
                    emergency_actions.add("5");
                } else if (value == 1) {
                    radio5_no.setChecked(true);
                } else if (value == 3) {
                    radio5_not.setChecked(true);
                }
                break;
            case "5":
                if (value == 2){
                    radio6_yes.setChecked(true);
                    action_prompt6.setVisibility(View.VISIBLE);
                    emergency_actions.add("6");
                } else if (value == 1) {
                    radio6_no.setChecked(true);
                } else if (value == 3) {
                    radio6_not.setChecked(true);
                }
                break;
            case "7":
                if (value == 2){
                    radio8_yes.setChecked(true);
                    action_prompt8.setVisibility(View.VISIBLE);
                    emergency_actions.add("8");
                } else if (value == 1) {
                    radio8_no.setChecked(true);
                } else if (value == 3) {
                    radio8_not.setChecked(true);
                }
                break;
            case "9":
                if (value == 2){
                    radio10_yes.setChecked(true);
                } else if (value == 1) {
                    radio10_no.setChecked(true);
                } else if (value == 3) {
                    radio10_not.setChecked(true);
                }
                break;
            case "12":
                if (value == 2){
                    radio13_yes.setChecked(true);

                    action_prompt12.setVisibility(View.VISIBLE);
                    emergency_actions.add("12");
                    action_prompt13.setVisibility(View.VISIBLE);
                    emergency_actions.add("13");
                    action_prompt14.setVisibility(View.VISIBLE);
                    emergency_actions.add("14");
                } else if (value == 1) {
                    radio13_no.setChecked(true);
                } else if (value == 3) {
                    radio13_not.setChecked(true);
                }
                break;
            case "14":
                if (value == 2){
                    radio15_yes.setChecked(true);
//                    row16.setVisibility(View.VISIBLE);
//                    row_line16.setVisibility(View.VISIBLE);
                    row19.setVisibility(View.VISIBLE);
                    row_line19.setVisibility(View.VISIBLE);
                    action_prompt17.setVisibility(View.VISIBLE);
                    action_prompt_new_17.setVisibility(View.VISIBLE);
                    action_prompt18.setVisibility(View.VISIBLE);
                    emergency_actions.add("17");
//                    emergency_actions.add("18");
                } else if (value == 1) {
                    radio15_no.setChecked(true);
                } else if (value == 3) {
                    radio15_not.setChecked(true);
                }
                break;
            case "15":
                if (value == 2){
                    radio16_yes.setChecked(true);
                    action_prompt16.setVisibility(View.VISIBLE);
                    emergency_actions.add("16");
                } else if (value == 1) {
                    radio16_no.setChecked(true);
                } else if (value == 3) {
                    radio16_not.setChecked(true);
                }
                break;
            case "16":
                if (value == 2){
                    radio17_yes.setChecked(true);
                } else if (value == 1) {
                    radio17_no.setChecked(true);
                } else if (value == 3) {
                    radio17_not.setChecked(true);
                }
                break;
            case "17":
                if (value == 2){
                    radio18_yes.setChecked(true);
                } else if (value == 1) {
                    radio18_no.setChecked(true);
                } else if (value == 3) {
                    radio18_not.setChecked(true);
                }
                break;
            case "18":
                if (value == 2){
                    radio19_yes.setChecked(true);
                    action_prompt19.setVisibility(View.VISIBLE);
                    emergency_actions.add("19");
                } else if (value == 1) {
                    radio19_no.setChecked(true);
                } else if (value == 3) {
                    radio19_not.setChecked(true);
                }
                break;
            case "23":
                if (value == 2){
                    radio24_yes.setChecked(true);
                    action_prompt24.setVisibility(View.VISIBLE);
                    emergency_actions.add("24");
                } else if (value == 1) {
                    radio24_no.setChecked(true);
                } else if (value == 3) {
                    radio24_not.setChecked(true);
                }
                break;
            case "24":
                if (value == 2){
                    radio25_yes.setChecked(true);
                } else if (value == 1) {
                    radio25_no.setChecked(true);
                } else if (value == 3) {
                    radio25_not.setChecked(true);
                }
                break;
            case "25":
                if (value == 2){
                    radio26_yes.setChecked(true);
                    action_prompt26.setVisibility(View.VISIBLE);
                    emergency_actions.add("26");
                } else if (value == 1) {
                    radio26_no.setChecked(true);
                } else if (value == 3) {
                    radio26_not.setChecked(true);
                }
                break;
            case "27":
                if (value == 2){
                    radio28_yes.setChecked(true);
                } else if (value == 1) {
                    radio28_no.setChecked(true);
                } else if (value == 3) {
                    radio28_not.setChecked(true);
                }
                break;
            case "28":
                if (value == 2){
                    radio29_yes.setChecked(true);
                } else if (value == 1) {
                    radio29_no.setChecked(true);
                } else if (value == 3) {
                    radio29_not.setChecked(true);
                }
                break;
            case "31":
                if (value == 2){
                    radio32_yes.setChecked(true);
                } else if (value == 1) {
                    radio32_no.setChecked(true);
                } else if (value == 3) {
                    radio32_not.setChecked(true);
                }
                break;
            case "32":
                if (value == 2){
                    radio33_yes.setChecked(true);
                } else if (value == 1) {
                    radio33_no.setChecked(true);
                } else if (value == 3) {
                    radio33_not.setChecked(true);
                }
                break;
            case "34":
                if (value == 2){
                    radio35_yes.setChecked(true);
                } else if (value == 1) {
                    radio35_no.setChecked(true);
                } else if (value == 3) {
                    radio35_not.setChecked(true);
                }
                break;
            case "38":
                if (value == 2){
                    radio39_yes.setChecked(true);
                    action_prompt39.setVisibility(View.VISIBLE);
                    emergency_actions.add("39");
                } else if (value == 1) {
                    radio39_no.setChecked(true);
                } else if (value == 3) {
                    radio39_not.setChecked(true);
                }
                break;
            case "44":
                if (value == 2){
                    radio45_yes.setChecked(true);
                } else if (value == 1) {
                    radio45_no.setChecked(true);
                } else if (value == 3) {
                    radio45_not.setChecked(true);
                }
                break;
            case "49":
                if (value == 2){
                    radio50_yes.setChecked(true);
                } else if (value == 1) {
                    radio50_no.setChecked(true);
                } else if (value == 3) {
                    radio50_not.setChecked(true);
                }
                break;
            case "51":
                if (value == 2){
                    radio_trauma2_yes.setChecked(true);
                    action_trauma2.setVisibility(View.VISIBLE);
                    trauma_actions.add("2");
                } else if (value == 1) {
                    radio_trauma2_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma2_not.setChecked(true);
                }
                break;
            case "52":
                if (value == 2){
                    radio_trauma3_yes.setChecked(true);
                    action_trauma7.setVisibility(View.VISIBLE);
                    trauma_actions.add("7");
                } else if (value == 1) {
                    radio_trauma3_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma3_not.setChecked(true);
                }
                break;
            case "53":
                if (value == 2){
                    radio_trauma4_yes.setChecked(true);
                    action_trauma7.setVisibility(View.VISIBLE);
                    trauma_actions.add("7");
                } else if (value == 1) {
                    radio_trauma4_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma4_not.setChecked(true);
                }
                break;
            case "54":
                if (value == 2){
                    radio_trauma5_yes.setChecked(true);
                    action_trauma7.setVisibility(View.VISIBLE);
                    trauma_actions.add("7");
                } else if (value == 1) {
                    radio_trauma5_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma5_not.setChecked(true);
                }
                break;
            case "55":
                if (value == 2){
                    radio_trauma6_yes.setChecked(true);
                    action_trauma7.setVisibility(View.VISIBLE);
                    trauma_actions.add("7");
                } else if (value == 1) {
                    radio_trauma6_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma6_not.setChecked(true);
                }
                break;
            case "56":
                if (value == 2){
                    radio_trauma12_yes.setChecked(true);
                    action_trauma12.setVisibility(View.VISIBLE);
                    trauma_actions.add("12");
                } else if (value == 1) {
                    radio_trauma12_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma12_not.setChecked(true);
                }
                break;
            case "57":
                if (value == 2){
                    radio_trauma13_yes.setChecked(true);
                    action_trauma13.setVisibility(View.VISIBLE);
                    trauma_actions.add("13");
                    action_trauma14.setVisibility(View.VISIBLE);
                    trauma_actions.add("14");
                    action_trauma15.setVisibility(View.VISIBLE);
                    trauma_actions.add("15");
                    action_trauma16.setVisibility(View.VISIBLE);
                    trauma_actions.add("16");
                } else if (value == 1) {
                    radio_trauma13_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma13_not.setChecked(true);
                }
                break;
            case "58":
                if (value == 2){
                    radio_trauma23_yes.setChecked(true);
                    action_trauma23.setVisibility(View.VISIBLE);
                    trauma_actions.add("23");
                } else if (value == 1) {
                    radio_trauma23_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma23_not.setChecked(true);
                }
                break;
            case "59":
                if (value == 2){
                    radio_trauma24_yes.setChecked(true);
                    action_trauma24.setVisibility(View.VISIBLE);
                    trauma_actions.add("24");
                    action_trauma25.setVisibility(View.VISIBLE);
                    trauma_actions.add("25");
                    action_trauma26.setVisibility(View.VISIBLE);
                    trauma_actions.add("26");
                } else if (value == 1) {
                    radio_trauma24_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma24_not.setChecked(true);
                }
                break;
            case "60":
                if (value == 2){
                    radio_trauma27_yes.setChecked(true);
                    action_trauma27.setVisibility(View.VISIBLE);
                    trauma_actions.add("27");
                    action_trauma28.setVisibility(View.VISIBLE);
                    trauma_actions.add("28");
                } else if (value == 1) {
                    radio_trauma27_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma27_not.setChecked(true);
                }
                break;
            case "61":
                if (value == 2){
                    radio_trauma29_yes.setChecked(true);
                    action_trauma29.setVisibility(View.VISIBLE);
                    trauma_actions.add("29");
                } else if (value == 1) {
                    radio_trauma29_no.setChecked(true);
                } else if (value == 3) {
                    radio_trauma29_not.setChecked(true);
                }
                break;
        }
    }

    private void updateCheckboxes(){
        emergency_check1.setChecked(getChecks(0));
        emergency_check2.setChecked(getChecks(1));
        emergency_check3.setChecked(getChecks(2));
        emergency_check_none.setChecked(!(emergency_check1.isChecked() || emergency_check2.isChecked() ||
                emergency_check3.isChecked() || trauma_check_airway.isChecked()));
        emergency_check4.setChecked(getChecks(3));
        emergency_check5.setChecked(getChecks(4));
        emergency_check6.setChecked(getChecks(5));
//        emergency_check7.setChecked(getChecks(6));
        emergency_check_none_two.setChecked(!(emergency_check6.isChecked() ||
                emergency_check4.isChecked() || emergency_check5.isChecked() || trauma_check_circulation.isChecked()));
        emergency_check8.setChecked(getChecks(7));
        emergency_check9.setChecked(getChecks(8));
        emergency_check10.setChecked(getChecks(9));
        emergency_check_none_three.setChecked(!(emergency_check8.isChecked() || emergency_check9.isChecked() ||
                emergency_check10.isChecked() || trauma_check_disability.isChecked()));

        emergency_check11.setChecked(getChecks(10));
        emergency_check12.setChecked(getChecks(11));
        emergency_check13.setChecked(getChecks(12));
        emergency_check14.setChecked(getChecks(13));
        emergency_check15.setChecked(getChecks(14));
        emergency_check16.setChecked(getChecks(15));
        emergency_check17.setChecked(getChecks(16));
        emergency_check18.setChecked(getChecks(17));
        emergency_check18.setChecked(getChecks(24));
        emergency_check_none_four.setChecked(!(emergency_check11.isChecked() || emergency_check12.isChecked() ||
                emergency_check13.isChecked() || emergency_check14.isChecked() || emergency_check15.isChecked() ||
                emergency_check16.isChecked() || emergency_check17.isChecked() || emergency_check18.isChecked() || emergency_check19.isChecked() ||
                trauma_check_expose1.isChecked() || trauma_check_expose2.isChecked() || trauma_check_expose3.isChecked()));

        // trauma checkboxes
        trauma_check_airway.setChecked(getChecks(18));
        trauma_check_circulation.setChecked(getChecks(19));
        trauma_check_disability.setChecked(getChecks(20));
        trauma_check_expose1.setChecked(getChecks(21));
        trauma_check_expose2.setChecked(getChecks(22));
        trauma_check_expose3.setChecked(getChecks(23));

        priority_signs1.setChecked(getPriorityChecks(0));
        priority_signs2.setChecked(getPriorityChecks(1));
        priority_signs3.setChecked(getPriorityChecks(2));
        priority_signs4.setChecked(getPriorityChecks(3));
        priority_signs5.setChecked(getPriorityChecks(4));
        priority_signs6.setChecked(getPriorityChecks(5));
        priority_signs7.setChecked(getPriorityChecks(6));
        priority_signs8.setChecked(getPriorityChecks(7));
        priority_signs9.setChecked(getPriorityChecks(8));
        priority_signs10.setChecked(getPriorityChecks(9));
        priority_signs11.setChecked(getPriorityChecks(10));
        priority_signs12.setChecked(getPriorityChecks(11));
        priority_signs13.setChecked(getPriorityChecks(12));
        priority_signs14.setChecked(getPriorityChecks(13));
        priority_signs15.setChecked(getPriorityChecks(14));

        refreshEmergencyPromptsCounters();
    }

    public boolean getChecks(int position){
        return checks[position].equals("1");
    }

    public boolean getPriorityChecks(int position){
        return priority_signs_checks[position].equals("1");
    }

    public void updateChecks(int position, String value){
        checks[position] = value;
        refreshEmergencyPromptsCounters();
        saveTriageDetails(false);
    }

    public void updatePriorityChecks(int position, String value){
        priority_signs_checks[position] = value;
    }

    public void transitionLayoutOne(View view){
        if(airway_start_status){
            long end_time_airway = System.currentTimeMillis();
            Log.d(TAG, "transitionLayoutTwo: end time airway "+ end_time_airway);
            long reponse_time_airway = end_time_airway - start_time_airway_and_breathing;
            Log.d(TAG, "transitionLayoutTwo: response time for airway " + reponse_time_airway/1000.0);
        }
        section_prompt.setVisibility(View.VISIBLE);
        if (dropdown_view_one.getVisibility() == View.VISIBLE){
            dropdown_view_one.setVisibility(View.GONE);
        } else {
            // disappear the rest
            dropdown_view_five.setVisibility(View.GONE);
            dropdown_view_two.setVisibility(View.GONE);
            dropdown_view_three.setVisibility(View.GONE);
            dropdown_view_four.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(findViewById(R.id.parent_trans_one));
            dropdown_view_one.setVisibility(View.VISIBLE);
        }
    }
    public void tap(View view){
        section_prompt.setVisibility(View.VISIBLE);
    }
    public void transitionLayoutTwo(View view){
        if(circulation_start_status){
            long end_time_circulation = System.currentTimeMillis();
            Log.d(TAG, "transitionLayoutTwo: end time circulation "+ end_time_circulation);
            long reponse_time_circulation = end_time_circulation - start_time_circulation;
            Log.d(TAG, "transitionLayoutTwo: response time for circulation " + reponse_time_circulation/1000.0);
        }
        if (dropdown_view_two.getVisibility() == View.VISIBLE){
            dropdown_view_two.setVisibility(View.GONE);
        } else {
            // disappear the rest
            dropdown_view_one.setVisibility(View.GONE);
            dropdown_view_five.setVisibility(View.GONE);
            dropdown_view_three.setVisibility(View.GONE);
            dropdown_view_four.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(findViewById(R.id.parent_trans_two));
            dropdown_view_two.setVisibility(View.VISIBLE);
        }
    }

    public void transitionLayoutThree(View view){
        if(disability_start_status){
            long end_time_disability = System.currentTimeMillis();
            Log.d(TAG, "transitionLayoutTwo: end time disability "+ end_time_disability);
            long reponse_time_disability = end_time_disability - start_time_disability;
            Log.d(TAG, "transitionLayoutTwo: response time for disability " + reponse_time_disability/1000.0);
        }
        if (dropdown_view_three.getVisibility() == View.VISIBLE){
            dropdown_view_three.setVisibility(View.GONE);
        } else {
            // disappear the rest
            dropdown_view_one.setVisibility(View.GONE);
            dropdown_view_two.setVisibility(View.GONE);
            dropdown_view_five.setVisibility(View.GONE);
            dropdown_view_four.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(findViewById(R.id.parent_trans_three));
            dropdown_view_three.setVisibility(View.VISIBLE);
        }
    }

    public void transitionLayoutFour(View view){
        if(expose_start_status){
            long end_time_expose = System.currentTimeMillis();
            Log.d(TAG, "transitionLayoutTwo: end time expose "+ end_time_expose);
            long reponse_time_expose = end_time_expose - start_time_expose;
            Log.d(TAG, "transitionLayoutTwo: response time for expose " + reponse_time_expose/1000.0);
        }
        if (dropdown_view_four.getVisibility() == View.VISIBLE){
            dropdown_view_four.setVisibility(View.GONE);
        } else {
            // disappear the rest
            dropdown_view_one.setVisibility(View.GONE);
            dropdown_view_two.setVisibility(View.GONE);
            dropdown_view_three.setVisibility(View.GONE);
            dropdown_view_five.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(findViewById(R.id.parent_trans_four));
            dropdown_view_four.setVisibility(View.VISIBLE);
        }
    }

    public void transitionLayoutFive(View view){
        if (dropdown_view_five.getVisibility() == View.VISIBLE){
            dropdown_view_five.setVisibility(View.GONE);
        } else {
            // disappear the rest
            dropdown_view_one.setVisibility(View.GONE);
            dropdown_view_two.setVisibility(View.GONE);
            dropdown_view_three.setVisibility(View.GONE);
            dropdown_view_four.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(findViewById(R.id.parent_trans_five));
            dropdown_view_five.setVisibility(View.VISIBLE);
        }
    }

    public void saveTriageDetails(View view) {
        saveTriageDetails(true);
        saveVitals("1");
    }

    public void saveTriageDetails(boolean redirect_to_patient_home) {
        String emergency_checks_string = Arrays.toString(checks).replaceAll("[\\[.\\].\\s+]", "");
        String priority_checks_string = Arrays.toString(priority_signs_checks).replaceAll("[\\[.\\].\\s+]", "");
        String emergency_prompts_string = Arrays.toString(emergency_prompts).replaceAll("[\\[.\\].\\s+]", "");
        String emergency_actions_string = emergency_actions.toString().replaceAll("\\[|\\]|\\s", "");
        String[] emergency_actions_timestamps_array = new String[emergency_actions.size()];
        String[] emergency_actions_triggered_timestamps_array = new String[emergency_actions.size()];
        String trauma_actions_string = trauma_actions.toString().replaceAll("\\[|\\]|\\s", "");
        String[] trauma_actions_timestamps_array = new String[trauma_actions.size()];
        int counter = 0;

        for (String key: emergency_actions){
            emergency_actions_timestamps_array[counter] = Long.toString(emergency_actions_timestamps_map.get(key));
            emergency_actions_triggered_timestamps_array[counter] = Long.toString(emergency_actions_triggered_timestamps_map.get(key));
            counter++;
        }

        counter = 0;

        for (String key: trauma_actions){
            trauma_actions_timestamps_array[counter] = Long.toString(trauma_timestamps_map.get(key));
            counter++;
        }

        String emergency_actions_timestamps_string = Arrays.toString(emergency_actions_timestamps_array).replaceAll("\\[|\\]|\\s", "");
        String emergency_actions_triggered_timestamps_string = Arrays.toString(emergency_actions_triggered_timestamps_array).replaceAll("\\[|\\]|\\s", "");
        String trauma_actions_timestamps_array_string = Arrays.toString(trauma_actions_timestamps_array).replaceAll("\\[|\\]|\\s", "");

        if (priority_checks_string.contains("1")){
            router.addTriageDetails(patient_id, episode_id, "2", priority_checks_string, "", "", "", "", System.currentTimeMillis(), "", "", redirect_to_patient_home);
        }

        // check if the emergency string contains any 1s (checked items)
        if (emergency_checks_string.contains("1")){
            router.addTriageDetails(patient_id, episode_id, "1", emergency_checks_string, emergency_prompts_string, emergency_actions_string, emergency_actions_timestamps_string, emergency_actions_triggered_timestamps_string, System.currentTimeMillis(), trauma_actions_string, trauma_actions_timestamps_array_string, redirect_to_patient_home);
        }
    }

    public void refreshEmergencyPromptsCounters(){
        section_one_header = findViewById(R.id.section_one_header);
        section_two_header = findViewById(R.id.section_two_header);
        section_three_header = findViewById(R.id.section_three_header);
        TextView section_four_header = findViewById(R.id.section_four_header);
        label = findViewById(R.id.label);
        label2 = findViewById(R.id.label2);
        label3 = findViewById(R.id.label3);
        label4 = findViewById(R.id.label4);

        TextView section_one_header_trauma = findViewById(R.id.section_one_header_trauma);
        TextView section_two_header_trauma = findViewById(R.id.section_two_header_trauma);
        TextView section_three_header_trauma = findViewById(R.id.section_three_header_trauma);
        TextView section_four_header_trauma = findViewById(R.id.section_four_header_trauma);

        // hide all views
        action_prompt3.setVisibility(View.GONE);
        action_prompt4.setVisibility(View.GONE);
        action_prompt7.setVisibility(View.GONE);
        section_prompt.setVisibility(View.GONE);
        section_prompt1.setVisibility(View.GONE);
        section_one_header.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
        emergency_signs_number_one.setVisibility(View.GONE);
        prompt_label.setVisibility(View.GONE);
        row1.setVisibility(View.GONE);
        row2.setVisibility(View.GONE);
        section_one_header.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
        prompt_label.setVisibility(View.GONE);
        emergency_signs_number_one.setVisibility(View.GONE);
        row1.setVisibility(View.GONE);
        row2.setVisibility(View.GONE);
        row5.setVisibility(View.GONE);
        row6.setVisibility(View.GONE);
        row8.setVisibility(View.GONE);
        row_line5.setVisibility(View.GONE);
        row_line6.setVisibility(View.GONE);
        row_line8.setVisibility(View.GONE);
        section_two_header.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);
        emergency_signs_number_two.setVisibility(View.GONE);
        section_prompt2.setVisibility(View.GONE);
        row15.setVisibility(View.GONE);
        row18.setVisibility(View.GONE);
        row_line18.setVisibility(View.GONE);
        row_line15.setVisibility(View.GONE);
//        row16.setVisibility(View.GONE);
        row19.setVisibility(View.GONE);
        section_three_header.setVisibility(View.GONE);
        label3.setVisibility(View.GONE);
        emergency_signs_number_three.setVisibility(View.GONE);
        section_prompt3.setVisibility(View.GONE);
        action_prompt20.setVisibility(View.GONE);
        action_prompt21.setVisibility(View.GONE);
        action_prompt22.setVisibility(View.GONE);
        action_prompt23.setVisibility(View.GONE);
        action_prompt27.setVisibility(View.GONE);
        row24.setVisibility(View.GONE);
        row25.setVisibility(View.GONE);
        row52.setVisibility(View.GONE);
        row26.setVisibility(View.GONE);
        row_line24.setVisibility(View.GONE);
        row_line25.setVisibility(View.GONE);
        row_line27.setVisibility(View.GONE);
        section_three_header.setVisibility(View.GONE);
        label3.setVisibility(View.GONE);
        emergency_signs_number_three.setVisibility(View.GONE);
        action_prompt9.setVisibility(View.GONE);
        action_prompt11.setVisibility(View.GONE);
        row13.setVisibility(View.GONE);
        row10.setVisibility(View.GONE);
        section_four_header.setVisibility(View.GONE);
        emergency_signs_number_four.setVisibility(View.GONE);
        action_prompt30.setVisibility(View.GONE);
        action_prompt31.setVisibility(View.GONE);
        action_prompt33.setVisibility(View.GONE);
        action_prompt34.setVisibility(View.GONE);
        row29.setVisibility(View.GONE);
        row32.setVisibility(View.GONE);
        row28.setVisibility(View.GONE);
        row_line28.setVisibility(View.GONE);
        row_line29.setVisibility(View.GONE);
        row_line31.setVisibility(View.GONE);
        row_line32.setVisibility(View.GONE);
        section_four_header.setVisibility(View.GONE);
        emergency_signs_number_four.setVisibility(View.GONE);
        action_prompt36.setVisibility(View.GONE);
        action_prompt37.setVisibility(View.GONE);
        row35.setVisibility(View.GONE);
        section_four_header.setVisibility(View.GONE);
        emergency_signs_number_four.setVisibility(View.GONE);
        action_prompt38.setVisibility(View.GONE);
        action_prompt40.setVisibility(View.GONE);
        action_prompt41.setVisibility(View.GONE);
        action_prompt42.setVisibility(View.GONE);
        row39.setVisibility(View.GONE);
        row_line38.setVisibility(View.GONE);
        section_four_header.setVisibility(View.GONE);
        emergency_signs_number_four.setVisibility(View.GONE);
        action_prompt43.setVisibility(View.GONE);
        action_prompt44.setVisibility(View.GONE);
        action_prompt46.setVisibility(View.GONE);
        action_prompt47.setVisibility(View.GONE);
        action_prompt48.setVisibility(View.GONE);
        row45.setVisibility(View.GONE);
        row_line44.setVisibility(View.GONE);
        section_four_header.setVisibility(View.GONE);
        emergency_signs_number_four.setVisibility(View.GONE);
        action_prompt49.setVisibility(View.GONE);
        action_prompt51.setVisibility(View.GONE);
        action_prompt52.setVisibility(View.GONE);
        action_prompt39.setVisibility(View.GONE);
        row50.setVisibility(View.GONE);
        row_line49.setVisibility(View.GONE);
        section_one_header_trauma.setVisibility(View.GONE);
        emergency_signs_number_one_trauma_line.setVisibility(View.GONE);
        emergency_signs_number_one_trauma_image.setVisibility(View.GONE);
        action_trauma.setVisibility(View.GONE);
        action_trauma1.setVisibility(View.GONE);
        row_line_trauma1.setVisibility(View.GONE);
        row_trauma2.setVisibility(View.GONE);
        row_line_trauma2.setVisibility(View.GONE);
        row_trauma3.setVisibility(View.GONE);
        row_line_trauma3.setVisibility(View.GONE);
        row_trauma4.setVisibility(View.GONE);
        row_line_trauma4.setVisibility(View.GONE);
        row_trauma5.setVisibility(View.GONE);
        row_line_trauma5.setVisibility(View.GONE);
        row_trauma6.setVisibility(View.GONE);
        section_two_header_trauma.setVisibility(View.GONE);
        emergency_signs_number_two_trauma_line.setVisibility(View.GONE);
        emergency_signs_number_two_trauma_image.setVisibility(View.GONE);
        action_trauma8.setVisibility(View.GONE);
        action_trauma9.setVisibility(View.GONE);
        action_trauma10.setVisibility(View.GONE);
        action_trauma11.setVisibility(View.GONE);
        row_line_trauma11.setVisibility(View.GONE);
        row_trauma12.setVisibility(View.GONE);
        row_line_trauma12.setVisibility(View.GONE);
        row_trauma13.setVisibility(View.GONE);
        section_three_header_trauma.setVisibility(View.GONE);
        emergency_signs_number_three_trauma_line.setVisibility(View.GONE);
        emergency_signs_number_three_trauma_image.setVisibility(View.GONE);
        action_trauma17.setVisibility(View.GONE);
        action_trauma18.setVisibility(View.GONE);
        action_trauma19.setVisibility(View.GONE);
        action_trauma20.setVisibility(View.GONE);
        action_trauma21.setVisibility(View.GONE);
        action_trauma22.setVisibility(View.GONE);
        section_four_header_trauma.setVisibility(View.GONE);
        emergency_signs_number_four_trauma_line.setVisibility(View.GONE);
        emergency_signs_number_four_trauma_image.setVisibility(View.GONE);
        row_trauma23.setVisibility(View.GONE);
        row_line_trauma23.setVisibility(View.GONE);
        row_trauma24.setVisibility(View.GONE);
        row_line_trauma26.setVisibility(View.GONE);
        section_four_header_trauma.setVisibility(View.GONE);
        emergency_signs_number_four_trauma_line.setVisibility(View.GONE);
        emergency_signs_number_four_trauma_image.setVisibility(View.GONE);
        row_trauma27.setVisibility(View.GONE);
        row_line_trauma28.setVisibility(View.GONE);
        section_four_header_trauma.setVisibility(View.GONE);
        emergency_signs_number_four_trauma_line.setVisibility(View.GONE);
        emergency_signs_number_four_trauma_image.setVisibility(View.GONE);
        row_trauma29.setVisibility(View.GONE);

        emergency_prompts_counter = 0;
        numberPatientTraumaPresent = 0;

        if (checks[0].equals("1") || checks[1].equals("1") || checks[2].equals("1")) {
            // add actions related to the triage signs
            emergency_signs_number_one.setVisibility(View.VISIBLE);
            action_prompt3.setVisibility(View.VISIBLE);
            section_prompt1.setVisibility(View.VISIBLE);
            emergency_actions.add("3");
            prompt_label.setVisibility(View.VISIBLE);
            action_prompt4.setVisibility(View.VISIBLE);
            emergency_actions.add("4");
            action_prompt7.setVisibility(View.VISIBLE);
            action_prompt16.setVisibility(View.VISIBLE);
            emergency_actions.add("16");
            emergency_actions.add("7");
            emergency_actions.add("52");
            emergency_actions.add("53");
            row5.setVisibility(View.VISIBLE);
            row6.setVisibility(View.VISIBLE);
            row8.setVisibility(View.VISIBLE);

            row_line5.setVisibility(View.VISIBLE);
            row_line6.setVisibility(View.VISIBLE);
            row_line8.setVisibility(View.VISIBLE);

            emergency_actions_triggered_timestamps_map.replace("3", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("4", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("7", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("16", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("52", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("53", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("3", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("4", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("7", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("16", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("52", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("53", (long) 0);
        }

        // TODO fix this where some of the values entered --emergency_actions.add("52");-- are not removed when unticked
        // TODO investigate this next week

        if (checks[0].equals("1")){
            section_one_header.setVisibility(View.VISIBLE);
            section_prompt.setVisibility(View.VISIBLE);
            emergency_signs_number_one.setVisibility(View.VISIBLE);
            prompt_label.setVisibility(View.VISIBLE);
            row1.setVisibility(View.VISIBLE);
            row2.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 2;
        }


        if (checks[1].equals("1") || checks[2].equals("1")){
            section_one_header.setVisibility(View.VISIBLE);
            emergency_signs_number_one.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 3;
        }

        if (checks[3].equals("1") || checks[4].equals("1") || checks[5].equals("1") ||
                checks[6].equals("1")){

            section_two_header.setVisibility(View.VISIBLE);
            label2.setVisibility(View.GONE);
            emergency_signs_number_two.setVisibility(View.VISIBLE);
            action_prompt_new_17.setVisibility(View.VISIBLE);
            emergency_actions.add("18");
            action_prompt18.setVisibility(View.VISIBLE);
            emergency_actions.add("15");
            section_prompt2.setVisibility(View.VISIBLE);
            emergency_actions.add("54");
            emergency_prompts_counter += 1;
            row15.setVisibility(View.VISIBLE);

            if (patient_pregnant.equals("Yes")){
                emergency_prompts_counter += 1;
                row18.setVisibility(View.VISIBLE);
                row_line18.setVisibility(View.VISIBLE);
            }

            row_line15.setVisibility(View.VISIBLE);

            emergency_actions_triggered_timestamps_map.replace("18", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("15", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("54", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("18", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("15", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("54", (long) 0);
        }

        if (checks[7].equals("1") || checks[8].equals("1") || checks[9].equals("1")){
//            row from section four
//            row16.setVisibility(View.GONE);
            row19.setVisibility(View.GONE);

            emergency_prompts_counter += 3;

            section_three_header.setVisibility(View.VISIBLE);
            emergency_signs_number_three.setVisibility(View.VISIBLE);
            section_prompt3.setVisibility(View.VISIBLE);
            emergency_actions.add("55");
            action_prompt_new.setVisibility(View.VISIBLE);
            emergency_actions.add("57");

            action_prompt20.setVisibility(View.VISIBLE);
            emergency_actions.add("20");
            action_prompt21.setVisibility(View.VISIBLE);
            emergency_actions.add("21");
            action_prompt22.setVisibility(View.GONE);
            action_prompt_new1.setVisibility(View.VISIBLE);
            emergency_actions.add("58");
            action_prompt_25.setVisibility(View.VISIBLE);
            emergency_actions.add("59");
            emergency_actions.add("22");
            action_prompt23.setVisibility(View.VISIBLE);
            emergency_actions.add("23");
            action_prompt27.setVisibility(View.VISIBLE);
            emergency_actions.add("27");

            row24.setVisibility(View.VISIBLE);

            row26.setVisibility(View.VISIBLE);
            row_line24.setVisibility(View.VISIBLE);
            row_line25.setVisibility(View.VISIBLE);
            row_line27.setVisibility(View.VISIBLE);

            emergency_actions_triggered_timestamps_map.replace("20", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("21", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("22", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("23", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("27", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("55", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("57", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("58", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("59", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("20", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("21", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("22", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("23", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("27", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("55", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("57", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("58", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("59", (long) 0);
        }

        if (checks[8].equals("1")){
            section_three_header.setVisibility(View.VISIBLE);
            label3.setVisibility(View.GONE);
            emergency_signs_number_three.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 1;

            action_prompt9.setVisibility(View.VISIBLE);
            emergency_actions.add("9");
            action_prompt11.setVisibility(View.VISIBLE);
            emergency_actions.add("11");
            label4.setVisibility(View.VISIBLE);
            row13.setVisibility(View.VISIBLE);

            if (patient_pregnant.equals("Yes")){
                emergency_prompts_counter += 1;
                row10.setVisibility(View.VISIBLE);
            }

            emergency_actions_triggered_timestamps_map.replace("9", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("11", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("9", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("11", (long) 0);
        }

        if (checks[10].equals("1") || checks[11].equals("1") || checks[24].equals("1")){
            section_four_header.setVisibility(View.VISIBLE);
            emergency_signs_number_four.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 3;

            action_prompt30.setVisibility(View.VISIBLE);
            emergency_actions.add("30");
            action_prompt31.setVisibility(View.VISIBLE);
            emergency_actions.add("31");
            action_prompt33.setVisibility(View.VISIBLE);
            emergency_actions.add("33");
            action_prompt34.setVisibility(View.VISIBLE);
            emergency_actions.add("34");

            row29.setVisibility(View.VISIBLE);
            row32.setVisibility(View.VISIBLE);

            if (patient_pregnant.equals("Yes")){
                emergency_prompts_counter += 1;
                row28.setVisibility(View.VISIBLE);
                row_line28.setVisibility(View.VISIBLE);
            }

            row_line29.setVisibility(View.VISIBLE);
            row_line31.setVisibility(View.VISIBLE);
            row_line32.setVisibility(View.VISIBLE);

            emergency_actions_triggered_timestamps_map.replace("30", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("31", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("33", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("34", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("30", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("31", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("33", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("34", (long) 0);
        }

        if (checks[12].equals("1") || checks[13].equals("1") || checks[14].equals("1")){
            emergency_prompts_counter += 1;
            section_four_header.setVisibility(View.VISIBLE);
            emergency_signs_number_four.setVisibility(View.VISIBLE);

            action_prompt36.setVisibility(View.VISIBLE);
            emergency_actions.add("36");
            action_prompt37.setVisibility(View.VISIBLE);
            emergency_actions.add("37");

            row35.setVisibility(View.VISIBLE);

            emergency_actions_triggered_timestamps_map.replace("36", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("37", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("36", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("37", (long) 0);
        }

        if (checks[15].equals("1")){
            section_four_header.setVisibility(View.VISIBLE);
            emergency_signs_number_four.setVisibility(View.VISIBLE);

            action_prompt38.setVisibility(View.VISIBLE);
            emergency_actions.add("38");
            action_prompt40.setVisibility(View.VISIBLE);
            emergency_actions.add("40");
            action_prompt41.setVisibility(View.VISIBLE);
            emergency_actions.add("41");
            action_prompt42.setVisibility(View.VISIBLE);
            emergency_actions.add("42");

            row39.setVisibility(View.VISIBLE);

            row_line38.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 1;

            emergency_actions_triggered_timestamps_map.replace("38", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("40", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("41", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("42", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("38", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("40", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("41", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("42", (long) 0);
        }

        if (checks[16].equals("1")){
            section_four_header.setVisibility(View.VISIBLE);
            emergency_signs_number_four.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 1;

            action_prompt43.setVisibility(View.VISIBLE);
            emergency_actions.add("43");
            action_prompt44.setVisibility(View.VISIBLE);
            emergency_actions.add("44");
            action_prompt46.setVisibility(View.VISIBLE);
            emergency_actions.add("46");
            action_prompt47.setVisibility(View.VISIBLE);
            emergency_actions.add("47");
            action_prompt48.setVisibility(View.VISIBLE);
            emergency_actions.add("48");

            //row45.setVisibility(View.VISIBLE);
            row39.setVisibility(View.VISIBLE);
            row_line44.setVisibility(View.VISIBLE);

            emergency_actions_triggered_timestamps_map.replace("43", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("44", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("46", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("47", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("48", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("43", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("44", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("46", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("47", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("48", (long) 0);
        }

        if (checks[17].equals("1")){
            section_four_header.setVisibility(View.VISIBLE);
            emergency_signs_number_four.setVisibility(View.VISIBLE);

            action_prompt49.setVisibility(View.VISIBLE);
            emergency_actions.add("49");
            action_prompt51.setVisibility(View.VISIBLE);
            emergency_actions.add("51");
            action_prompt52.setVisibility(View.VISIBLE);
            emergency_actions.add("56");

            //row50.setVisibility(View.VISIBLE);
            row39.setVisibility(View.VISIBLE);

            row_line49.setVisibility(View.VISIBLE);
            emergency_prompts_counter += 1;

            emergency_actions_triggered_timestamps_map.replace("49", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("51", System.currentTimeMillis());
            emergency_actions_triggered_timestamps_map.replace("56", System.currentTimeMillis());
        } else {
            emergency_actions_triggered_timestamps_map.replace("49", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("51", (long) 0);
            emergency_actions_triggered_timestamps_map.replace("56", (long) 0);
        }

        if (checks[18].equals("1")){
            section_one_header_trauma.setVisibility(View.VISIBLE);
            emergency_signs_number_one_trauma_line.setVisibility(View.VISIBLE);
            emergency_signs_number_one_trauma_image.setVisibility(View.VISIBLE);
            numberPatientTraumaPresent += 1;

            action_trauma.setVisibility(View.VISIBLE);
            trauma_actions.add("0");
            action_trauma1.setVisibility(View.VISIBLE);
            trauma_actions.add("1");
            row_line_trauma1.setVisibility(View.VISIBLE);
            row_trauma2.setVisibility(View.VISIBLE);
            row_line_trauma2.setVisibility(View.VISIBLE);
            row_trauma3.setVisibility(View.VISIBLE);
            row_line_trauma3.setVisibility(View.VISIBLE);
            row_trauma4.setVisibility(View.VISIBLE);
            row_line_trauma4.setVisibility(View.VISIBLE);
            row_trauma5.setVisibility(View.VISIBLE);
            row_line_trauma5.setVisibility(View.VISIBLE);
            row_trauma6.setVisibility(View.VISIBLE);
        }

        if (checks[19].equals("1")){
            section_two_header_trauma.setVisibility(View.VISIBLE);
            emergency_signs_number_two_trauma_line.setVisibility(View.VISIBLE);
            emergency_signs_number_two_trauma_image.setVisibility(View.VISIBLE);
            numberPatientTraumaPresent += 1;

            action_trauma8.setVisibility(View.VISIBLE);
            trauma_actions.add("8");
            action_trauma9.setVisibility(View.VISIBLE);
            trauma_actions.add("9");
            action_trauma10.setVisibility(View.VISIBLE);
            trauma_actions.add("10");
            action_trauma11.setVisibility(View.VISIBLE);
            trauma_actions.add("11");
            row_line_trauma11.setVisibility(View.VISIBLE);
            row_trauma12.setVisibility(View.VISIBLE);
            row_line_trauma12.setVisibility(View.VISIBLE);
            row_trauma13.setVisibility(View.VISIBLE);
        }

        if (checks[20].equals("1")){
            section_three_header_trauma.setVisibility(View.VISIBLE);
            emergency_signs_number_three_trauma_line.setVisibility(View.VISIBLE);
            emergency_signs_number_three_trauma_image.setVisibility(View.VISIBLE);
            numberPatientTraumaPresent += 1;

            action_trauma17.setVisibility(View.VISIBLE);
            trauma_actions.add("17");
            action_trauma18.setVisibility(View.VISIBLE);
            trauma_actions.add("18");
            action_trauma19.setVisibility(View.VISIBLE);
            trauma_actions.add("19");
            action_trauma20.setVisibility(View.VISIBLE);
            trauma_actions.add("20");
            action_trauma21.setVisibility(View.VISIBLE);
            trauma_actions.add("21");
            action_trauma22.setVisibility(View.VISIBLE);
            trauma_actions.add("22");
        }

        if (checks[21].equals("1")){
            section_four_header_trauma.setVisibility(View.VISIBLE);
            emergency_signs_number_four_trauma_line.setVisibility(View.VISIBLE);
            emergency_signs_number_four_trauma_image.setVisibility(View.VISIBLE);
            numberPatientTraumaPresent += 1;

            row_trauma23.setVisibility(View.VISIBLE);
            row_line_trauma23.setVisibility(View.VISIBLE);
            row_trauma24.setVisibility(View.VISIBLE);
            row_line_trauma26.setVisibility(View.VISIBLE);
        }

        if (checks[22].equals("1")){
            section_four_header_trauma.setVisibility(View.VISIBLE);
            emergency_signs_number_four_trauma_line.setVisibility(View.VISIBLE);
            emergency_signs_number_four_trauma_image.setVisibility(View.VISIBLE);
            numberPatientTraumaPresent += 1;

            row_trauma27.setVisibility(View.VISIBLE);
            row_line_trauma28.setVisibility(View.VISIBLE);
        }

        if (checks[23].equals("1")){
            section_four_header_trauma.setVisibility(View.VISIBLE);
            emergency_signs_number_four_trauma_line.setVisibility(View.VISIBLE);
            emergency_signs_number_four_trauma_image.setVisibility(View.VISIBLE);
            numberPatientTraumaPresent += 1;

            row_trauma29.setVisibility(View.VISIBLE);
        }

        // check if this was a request from patient home for emergency signs
        if (isRequestFromPatientHome){
            displayPromptsBottomSheet();
            isRequestFromPatientHome = false;
        }

        // if emergency signs are available don't show priority signs
        if(emergency_check_none.isChecked() && emergency_check_none_two.isChecked() &&
                emergency_check_none_three.isChecked() && emergency_check_none_four.isChecked()) {
            parent_trans_five.setVisibility(View.VISIBLE);
        } else {
            parent_trans_five.setVisibility(View.GONE);
        }
    }

    public void displayPromptsBottomSheet(View view) {
        String tag = view.getTag().toString();

        TableLayout section_one = findViewById(R.id.section_one);
        TableLayout section_two = findViewById(R.id.section_two);
        TableLayout section_three = findViewById(R.id.section_three);
        TableLayout section_four = findViewById(R.id.section_four);

        switch (tag){
            case "1":
                start_time_airway_and_breathing = System.currentTimeMillis();
                Log.d(TAG, "displayPromptsBottomSheet: start time airway "+ start_time_airway_and_breathing);
                airway_start_status = true;
                section_one.setVisibility(View.VISIBLE);
                section_two.setVisibility(View.GONE);
                section_three.setVisibility(View.GONE);
                section_four.setVisibility(View.GONE);
                break;
            case "2":
                start_time_circulation = System.currentTimeMillis();
                Log.d(TAG, "displayPromptsBottomSheet: circulation "+ start_time_circulation);
                circulation_start_status = true;
                section_two.setVisibility(View.VISIBLE);
                section_one.setVisibility(View.GONE);
                section_three.setVisibility(View.GONE);
                section_four.setVisibility(View.GONE);
                break;
            case "3":
                start_time_disability = System.currentTimeMillis();
                Log.d(TAG, "displayPromptsBottomSheet: disability "+ start_time_disability);
                disability_start_status = true;
                section_three.setVisibility(View.VISIBLE);
                section_one.setVisibility(View.GONE);
                section_two.setVisibility(View.GONE);
                section_four.setVisibility(View.GONE);
                break;
            case "4":
                start_time_expose = System.currentTimeMillis();
                Log.d(TAG, "displayPromptsBottomSheet: disability "+ start_time_expose);
                expose_start_status = true;
                section_four.setVisibility(View.VISIBLE);
                section_one.setVisibility(View.GONE);
                section_two.setVisibility(View.GONE);
                section_three.setVisibility(View.GONE);
                break;
            default:
                section_one.setVisibility(View.GONE);
                section_two.setVisibility(View.GONE);
                section_three.setVisibility(View.GONE);
                section_four.setVisibility(View.GONE);
                break;
        }

        displayPromptsBottomSheet();
    }

    public void displayPromptsBottomSheet() {
        if (emergency_prompts_counter > 0) {
            isBottomSheetUp = true;
            fab_review_triage.setVisibility(View.GONE);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Emergency Prompts");
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void displayTraumaBottomSheet(View view) {
        if (numberPatientTraumaPresent > 0) {
            isBottomSheetUpTrauma = true;
            fab_review_triage.setVisibility(View.GONE);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Trauma Prompts");
            bottomSheetBehaviorTrauma.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehaviorTrauma.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void getPatientDetails(){
        final TextView patient_info_text_bottom_sheet = findViewById(R.id.patient_info_text_bottom_sheet);
        final TextView patient_info_text_bottom_sheet_trauma = findViewById(R.id.patient_info_text_bottom_sheet_trauma);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {

                        patient_pregnant = response.getString("patient_pregnant");

                        String pregnancy_weeks = "";

                        if (patient_pregnant.equals("Yes")) {
                            pregnancy_weeks = ". " + response.getString("weeks_pregnant") + " weeks pregnant";
                        }

                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" + response.getString("date_of_birth") + "), " + response.getString("gender") + pregnancy_weeks;
                        patient_info_text_bottom_sheet.setText(text);
                        patient_info_text_bottom_sheet_trauma.setText(text);

                        // change profile for pregnancy
                        if (response.getString("patient_pregnant").equals("Yes")) {
                            ImageView patient_profile_picture = findViewById(R.id.patient_profile_picture_triage);
                            patient_profile_picture.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pregnant));
                        }

                        // change profile for pregnancy
                        if (response.getString("patient_pregnant").equals("Yes")) {
                            ImageView patient_profile_picture = findViewById(R.id.patient_profile_picture_triage_trauma);
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

    public void saveSelectedPromptOption(View view) {
        String[] tag_array = view.getTag().toString().split(",");

        emergency_prompts[(Integer.parseInt(tag_array[0]) - 1)] = tag_array[1];

        switch (tag_array[0]) {
            case "1":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt1.setVisibility(View.VISIBLE);
                        emergency_actions.add("1");
                        emergency_actions_triggered_timestamps_map.replace("1", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt1.setVisibility(View.GONE);
                        emergency_actions.remove("1");
                        emergency_actions_triggered_timestamps_map.replace("1", (long)0);
                        break;
                }
                break;
            case "2":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt2.setVisibility(View.VISIBLE);
                        emergency_actions.add("2");
                        emergency_actions_triggered_timestamps_map.replace("2", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt2.setVisibility(View.GONE);
                        emergency_actions.remove("2");
                        emergency_actions_triggered_timestamps_map.replace("2", (long)0);
                        break;
                }
                break;
            case "5":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt5.setVisibility(View.VISIBLE);
                        emergency_actions.add("5");
                        emergency_actions_triggered_timestamps_map.replace("5", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt5.setVisibility(View.GONE);
                        emergency_actions.remove("5");
                        emergency_actions_triggered_timestamps_map.replace("5", (long)0);
                        break;
                }
                break;
            case "6":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt6.setVisibility(View.VISIBLE);
                        emergency_actions.add("6");
                        emergency_actions_triggered_timestamps_map.replace("6", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt6.setVisibility(View.GONE);
                        emergency_actions.remove("6");
                        emergency_actions_triggered_timestamps_map.replace("6", (long)0);
                        break;
                }
                break;
            case "8":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt8.setVisibility(View.VISIBLE);
                        emergency_actions.add("8");
                        emergency_actions_triggered_timestamps_map.replace("8", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt8.setVisibility(View.GONE);
                        emergency_actions.remove("8");
                        emergency_actions_triggered_timestamps_map.replace("8", (long)0);
                        break;
                }
                break;
            case "13":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt12.setVisibility(View.VISIBLE);
                        emergency_actions.add("12");
                        emergency_actions_triggered_timestamps_map.replace("12", System.currentTimeMillis());
                        action_prompt13.setVisibility(View.VISIBLE);
                        emergency_actions.add("13");
                        emergency_actions_triggered_timestamps_map.replace("13", System.currentTimeMillis());
                        action_prompt14.setVisibility(View.VISIBLE);
                        emergency_actions.add("14");
                        emergency_actions_triggered_timestamps_map.replace("14", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt12.setVisibility(View.GONE);
                        emergency_actions.remove("12");
                        emergency_actions_triggered_timestamps_map.replace("12", (long)0);
                        action_prompt13.setVisibility(View.GONE);
                        emergency_actions.remove("13");
                        emergency_actions_triggered_timestamps_map.replace("13", (long)0);
                        action_prompt14.setVisibility(View.GONE);
                        emergency_actions.remove("14");
                        emergency_actions_triggered_timestamps_map.replace("14", (long)0);
                        break;
                }
                break;
            case "19":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt19.setVisibility(View.VISIBLE);
                        emergency_actions.add("19");
                        emergency_actions_triggered_timestamps_map.replace("19", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt19.setVisibility(View.GONE);
                        emergency_actions.remove("19");
                        emergency_actions_triggered_timestamps_map.replace("19", (long)0);
                        break;
                }
                break;
            case "24":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt24.setVisibility(View.VISIBLE);
                        emergency_actions.add("24");
                        emergency_actions_triggered_timestamps_map.replace("24", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt24.setVisibility(View.GONE);
                        emergency_actions.remove("24");
                        emergency_actions_triggered_timestamps_map.replace("24", (long)0);
                        break;
                }
                break;
            case "26":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt26.setVisibility(View.VISIBLE);
                        emergency_actions.add("26");
                        emergency_actions_triggered_timestamps_map.replace("26", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt26.setVisibility(View.GONE);
                        emergency_actions.remove("26");
                        emergency_actions_triggered_timestamps_map.replace("26", (long)0);
                        break;
                }
                break;
            case "29":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt29.setVisibility(View.VISIBLE);
                        emergency_actions.add("29");
                        emergency_actions_triggered_timestamps_map.replace("29", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt29.setVisibility(View.GONE);
                        emergency_actions.remove("29");
                        emergency_actions_triggered_timestamps_map.replace("29", (long)0);
                        break;
                }
                break;
            case "39":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt39.setVisibility(View.VISIBLE);
                        emergency_actions.add("39");
                        emergency_actions_triggered_timestamps_map.replace("39", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt39.setVisibility(View.GONE);
                        emergency_actions.remove("39");
                        emergency_actions_triggered_timestamps_map.replace("39", (long)0);
                        break;
                }
                break;
            case "15":
                switch (tag_array[1]) {
                    case "2":
//                        row16.setVisibility(View.VISIBLE);
//                        row_line16.setVisibility(View.VISIBLE);
                        row19.setVisibility(View.VISIBLE);
                        row_line19.setVisibility(View.VISIBLE);
                        action_prompt17.setVisibility(View.VISIBLE);
                        action_prompt18.setVisibility(View.VISIBLE);
                        action_prompt_new_17.setVisibility(View.VISIBLE);
                        emergency_actions.add("17");
                        emergency_actions_triggered_timestamps_map.replace("17", System.currentTimeMillis());
                      
                        break;
                    case "1":
                    case "3":
//                        row16.setVisibility(View.GONE);
//                        row_line16.setVisibility(View.GONE);
                        row19.setVisibility(View.GONE);
                        row_line19.setVisibility(View.GONE);
                        action_prompt16.setVisibility(View.GONE);
                        action_prompt17.setVisibility(View.GONE);
                        action_prompt18.setVisibility(View.GONE);
                        action_prompt_new_17.setVisibility(View.GONE);
                        emergency_actions.remove("16");
                        emergency_actions.remove("17");
                        emergency_actions_triggered_timestamps_map.replace("16", (long)0);
                        emergency_actions_triggered_timestamps_map.replace("17", (long)0);

                        break;
                }
                break;
            case "16":
                switch (tag_array[1]) {
                    case "2":
                        action_prompt16.setVisibility(View.VISIBLE);
                        emergency_actions.add("16");
                        emergency_actions_triggered_timestamps_map.replace("16", System.currentTimeMillis());
                        break;
                    case "1":
                    case "3":
                        action_prompt16.setVisibility(View.GONE);
                        emergency_actions.remove("16");
                        emergency_actions_triggered_timestamps_map.replace("16", (long)0);
                        break;
                }
                break;
            case "52":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma2.setVisibility(View.VISIBLE);
                        trauma_actions.add("2");
                        break;
                    case "1":
                    case "3":
                        action_trauma2.setVisibility(View.GONE);
                        trauma_actions.remove("2");
                        break;
                }
                break;
            case "53":
            case "54":
            case "55":
            case "56":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma7.setVisibility(View.VISIBLE);
                        trauma_actions.add("7");
                        break;
                    case "1":
                    case "3":
                        action_trauma7.setVisibility(View.GONE);
                        trauma_actions.remove("7");
                        break;
                }
                break;
            case "57":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma12.setVisibility(View.VISIBLE);
                        trauma_actions.add("12");
                        break;
                    case "1":
                    case "3":
                        action_trauma12.setVisibility(View.GONE);
                        trauma_actions.remove("12");
                        break;
                }
                break;
            case "58":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma13.setVisibility(View.VISIBLE);
                        trauma_actions.add("13");
                        action_trauma14.setVisibility(View.VISIBLE);
                        trauma_actions.add("14");
                        action_trauma15.setVisibility(View.VISIBLE);
                        trauma_actions.add("15");
                        action_trauma16.setVisibility(View.VISIBLE);
                        trauma_actions.add("16");
                        break;
                    case "1":
                    case "3":
                        action_trauma13.setVisibility(View.GONE);
                        trauma_actions.remove("13");
                        action_trauma14.setVisibility(View.GONE);
                        trauma_actions.remove("14");
                        action_trauma15.setVisibility(View.GONE);
                        trauma_actions.remove("15");
                        action_trauma16.setVisibility(View.GONE);
                        trauma_actions.remove("16");
                        break;
                }
                break;
            case "59":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma23.setVisibility(View.VISIBLE);
                        trauma_actions.add("23");
                        break;
                    case "1":
                    case "3":
                        action_trauma23.setVisibility(View.GONE);
                        trauma_actions.remove("23");
                        break;
                }
                break;
            case "60":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma24.setVisibility(View.VISIBLE);
                        trauma_actions.add("24");
                        action_trauma25.setVisibility(View.VISIBLE);
                        trauma_actions.add("25");
                        action_trauma26.setVisibility(View.VISIBLE);
                        trauma_actions.add("26");
                        break;
                    case "1":
                    case "3":
                        action_trauma24.setVisibility(View.GONE);
                        trauma_actions.remove("24");
                        action_trauma25.setVisibility(View.GONE);
                        trauma_actions.remove("25");
                        action_trauma26.setVisibility(View.GONE);
                        trauma_actions.remove("26");
                        break;
                }
                break;
            case "61":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma27.setVisibility(View.VISIBLE);
                        trauma_actions.add("27");
                        action_trauma28.setVisibility(View.VISIBLE);
                        trauma_actions.add("28");
                        break;
                    case "1":
                    case "3":
                        action_trauma27.setVisibility(View.GONE);
                        trauma_actions.remove("27");
                        action_trauma28.setVisibility(View.GONE);
                        trauma_actions.remove("28");
                        break;
                }
                break;
            case "62":
                switch (tag_array[1]) {
                    case "2":
                        action_trauma29.setVisibility(View.VISIBLE);
                        trauma_actions.add("29");
                        break;
                    case "1":
                    case "3":
                        action_trauma29.setVisibility(View.GONE);
                        trauma_actions.remove("29");
                        break;
                }
                break;
        }
    }

}
