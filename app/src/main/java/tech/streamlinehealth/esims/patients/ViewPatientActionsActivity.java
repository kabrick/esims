package tech.streamlinehealth.esims.patients;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ViewPatientActionsActivity extends AppCompatActivity {

    String patient_id;
    DataRouter router;
    HelperFunctions helperFunctions;
    String[] actions_data;
    LinearLayout checks_layout;
    TextView no_vitals_recorded;
    LinearLayout actions1, actions2, actions3, actions4, actions5, actions6, actions7, actions8, actions9,
            actions10, actions11, actions12, actions13, actions14, actions15, actions16, actions17, actions18, actions19, actions20, actions21;
    TextView actions1_textview, actions2_textview, actions3_textview, actions4_textview, actions5_textview, actions6_textview, actions7_textview, actions8_textview, actions9_textview,
            actions10_textview, actions11_textview, actions12_textview, actions13_textview, actions14_textview, textview_1;
    CheckBox actions1_checkbox, actions2_checkbox, actions3_checkbox, actions4_checkbox, actions5_checkbox, actions6_checkbox, actions7_checkbox, actions8_checkbox, actions9_checkbox,
            actions10_checkbox, actions11_checkbox, actions12_checkbox, actions13_checkbox, actions14_checkbox;
    String[] action_status;

    ImageView caution, caution1, caution2, caution3, caution4, caution5, caution6, caution7, caution8, caution9, caution10, caution11, caution12, caution13, caution14, caution15, caution16,
            caution17, caution18, caution19, caution20;

    TableRow row_action, row_action1, row_action2, row_action3, row_action4, row_action5, row_action6, row_action7, row_action8, row_action9, row_action10, row_action11, row_action12, row_action13,
            row_action14, row_action15, row_action16, row_action17, row_action18, row_action19, row_action20;

    RadioButton radio_action_yes, radio_action_no, radio_action_not, radio_action1_yes, radio_action1_no, radio_action1_not, radio_action2_yes, radio_action2_no, radio_action2_not,
            radio_action3_yes, radio_action3_no, radio_action3_not, radio_action4_yes, radio_action4_no, radio_action4_not, radio_action5_yes, radio_action5_no, radio_action5_not,
            radio_action6_yes, radio_action6_no, radio_action6_not, radio_action7_yes, radio_action7_no, radio_action7_not, radio_action8_yes, radio_action8_no, radio_action8_not, radio_action9_yes, radio_action9_no, radio_action9_not, radio_action10_yes, radio_action10_no, radio_action10_not, radio_action11_yes, radio_action11_no, radio_action11_not,
            radio_action12_yes, radio_action12_no, radio_action12_not, radio_action13_yes, radio_action13_no, radio_action13_not, radio_action14_yes, radio_action14_no, radio_action14_not, radio_action15_yes, radio_action15_no, radio_action15_not, radio_action16_yes, radio_action16_no, radio_action16_not, radio_action17_yes, radio_action17_no, radio_action17_not,
            radio_action18_yes, radio_action18_no, radio_action18_not, radio_action19_yes, radio_action19_no, radio_action19_not, radio_action20_yes, radio_action20_no, radio_action20_not;
    private FirebaseAnalytics firebaseAnalytics;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_actions);

        router = new DataRouter(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        helperFunctions = new HelperFunctions(this);
        checks_layout = findViewById(R.id.checks_layout);
        no_vitals_recorded = findViewById(R.id.no_vitals_recorded);
        patient_id = new PreferenceManager(this).getPatientId();

        actions1 = findViewById(R.id.actions1);
        actions2 = findViewById(R.id.actions2);
        actions3 = findViewById(R.id.actions3);
        actions4 = findViewById(R.id.actions4);
        actions5 = findViewById(R.id.actions5);
        actions6 = findViewById(R.id.actions6);
        actions7 = findViewById(R.id.actions7);
        actions8 = findViewById(R.id.actions8);
        actions9 = findViewById(R.id.actions9);
        actions10 = findViewById(R.id.actions10);
        actions11 = findViewById(R.id.actions11);
        actions12 = findViewById(R.id.actions12);
        actions13 = findViewById(R.id.actions13);
        actions14 = findViewById(R.id.actions14);

        actions1_textview = findViewById(R.id.actions1_textview);
        actions2_textview = findViewById(R.id.actions2_textview);
        actions3_textview = findViewById(R.id.actions3_textview);
        actions4_textview = findViewById(R.id.actions4_textview);
        actions5_textview = findViewById(R.id.actions5_textview);
        actions6_textview = findViewById(R.id.actions6_textview);
        actions7_textview = findViewById(R.id.actions7_textview);
        actions8_textview = findViewById(R.id.actions8_textview);
        actions9_textview = findViewById(R.id.actions9_textview);
        actions10_textview = findViewById(R.id.actions10_textview);
        actions11_textview = findViewById(R.id.actions11_textview);
        actions12_textview = findViewById(R.id.actions12_textview);
        actions13_textview = findViewById(R.id.actions13_textview);
        actions14_textview = findViewById(R.id.actions14_textview);

        actions1_checkbox = findViewById(R.id.actions1_checkbox);
        actions2_checkbox = findViewById(R.id.actions2_checkbox);
        actions3_checkbox = findViewById(R.id.actions3_checkbox);
        actions4_checkbox = findViewById(R.id.actions4_checkbox);
        actions5_checkbox = findViewById(R.id.actions5_checkbox);
        actions6_checkbox = findViewById(R.id.actions6_checkbox);
        actions7_checkbox = findViewById(R.id.actions7_checkbox);
        actions8_checkbox = findViewById(R.id.actions8_checkbox);
        actions9_checkbox = findViewById(R.id.actions9_checkbox);
        actions10_checkbox = findViewById(R.id.actions10_checkbox);
        actions11_checkbox = findViewById(R.id.actions11_checkbox);
        actions12_checkbox = findViewById(R.id.actions12_checkbox);
        actions13_checkbox = findViewById(R.id.actions13_checkbox);
        actions14_checkbox = findViewById(R.id.actions14_checkbox);

        row_action = findViewById(R.id.row_action);
        row_action1 = findViewById(R.id.row_action1);
        row_action2 = findViewById(R.id.row_action2);
        row_action3 = findViewById(R.id.row_action3);
        row_action4 = findViewById(R.id.row_action4);
        row_action5 = findViewById(R.id.row_action5);
        row_action6 = findViewById(R.id.row_action6);
        row_action7 = findViewById(R.id.row_action7);
        row_action8 = findViewById(R.id.row_action8);
        row_action9 = findViewById(R.id.row_action9);
        row_action10 = findViewById(R.id.row_action10);
        row_action11 = findViewById(R.id.row_action11);
        row_action12 = findViewById(R.id.row_action12);
        row_action13 = findViewById(R.id.row_action13);
        row_action14 = findViewById(R.id.row_action14);
        row_action15 = findViewById(R.id.row_action15);
        row_action16 = findViewById(R.id.row_action16);
        row_action17 = findViewById(R.id.row_action17);
        row_action18 = findViewById(R.id.row_action18);
        row_action19 = findViewById(R.id.row_action19);
        row_action20 = findViewById(R.id.row_action20);

        radio_action_yes = findViewById(R.id.radio_action_yes);
        radio_action_no = findViewById(R.id.radio_action_no);
        radio_action_not = findViewById(R.id.radio_action_not);
        radio_action1_yes = findViewById(R.id.radio_action1_yes);
        radio_action1_no = findViewById(R.id.radio_action1_no);
        radio_action1_not = findViewById(R.id.radio_action1_not);
        radio_action2_yes = findViewById(R.id.radio_action2_yes);
        radio_action2_no = findViewById(R.id.radio_action2_no);
        radio_action2_not = findViewById(R.id.radio_action2_not);
        radio_action3_yes = findViewById(R.id.radio_action3_yes);
        radio_action3_no = findViewById(R.id.radio_action3_no);
        radio_action3_not = findViewById(R.id.radio_action3_not);
        radio_action4_yes = findViewById(R.id.radio_action4_yes);
        radio_action4_no = findViewById(R.id.radio_action4_no);
        radio_action4_not = findViewById(R.id.radio_action4_not);
        radio_action5_yes = findViewById(R.id.radio_action5_yes);
        radio_action5_no = findViewById(R.id.radio_action5_no);
        radio_action5_not = findViewById(R.id.radio_action5_not);
        radio_action6_yes = findViewById(R.id.radio_action6_yes);
        radio_action6_no = findViewById(R.id.radio_action6_no);
        radio_action6_not = findViewById(R.id.radio_action6_not);
        radio_action7_yes = findViewById(R.id.radio_action7_yes);
        radio_action7_no = findViewById(R.id.radio_action7_no);
        radio_action7_not = findViewById(R.id.radio_action7_not);
        radio_action8_yes = findViewById(R.id.radio_action8_yes);
        radio_action8_no = findViewById(R.id.radio_action8_no);
        radio_action8_not = findViewById(R.id.radio_action8_not);
        radio_action9_yes = findViewById(R.id.radio_action9_yes);
        radio_action9_no = findViewById(R.id.radio_action9_no);
        radio_action9_not = findViewById(R.id.radio_action9_not);
        radio_action10_yes = findViewById(R.id.radio_action10_yes);
        radio_action10_no = findViewById(R.id.radio_action10_no);
        radio_action10_not = findViewById(R.id.radio_action10_not);
        radio_action11_yes = findViewById(R.id.radio_action11_yes);
        radio_action11_no = findViewById(R.id.radio_action11_no);
        radio_action11_not = findViewById(R.id.radio_action11_not);
        radio_action12_yes = findViewById(R.id.radio_action12_yes);
        radio_action12_no = findViewById(R.id.radio_action12_no);
        radio_action12_not = findViewById(R.id.radio_action12_not);
        radio_action13_yes = findViewById(R.id.radio_action13_yes);
        radio_action13_no = findViewById(R.id.radio_action13_no);
        radio_action13_not = findViewById(R.id.radio_action13_not);
        radio_action14_yes = findViewById(R.id.radio_action14_yes);
        radio_action14_no = findViewById(R.id.radio_action14_no);
        radio_action14_not = findViewById(R.id.radio_action14_not);
        radio_action15_yes = findViewById(R.id.radio_action15_yes);
        radio_action15_no = findViewById(R.id.radio_action15_no);
        radio_action15_not = findViewById(R.id.radio_action15_not);
        radio_action16_yes = findViewById(R.id.radio_action16_yes);
        radio_action16_no = findViewById(R.id.radio_action16_no);
        radio_action16_not = findViewById(R.id.radio_action16_not);
        radio_action17_yes = findViewById(R.id.radio_action17_yes);
        radio_action17_no = findViewById(R.id.radio_action17_no);
        radio_action17_not = findViewById(R.id.radio_action17_not);
        radio_action18_yes = findViewById(R.id.radio_action18_yes);
        radio_action18_no = findViewById(R.id.radio_action18_no);
        radio_action18_not = findViewById(R.id.radio_action18_not);
        radio_action19_yes = findViewById(R.id.radio_action19_yes);
        radio_action19_no = findViewById(R.id.radio_action19_no);
        radio_action19_not = findViewById(R.id.radio_action19_not);
        radio_action20_yes = findViewById(R.id.radio_action20_yes);
        radio_action20_no = findViewById(R.id.radio_action20_no);
        radio_action20_not = findViewById(R.id.radio_action20_not);

        caution = findViewById(R.id.caution);
        caution1 = findViewById(R.id.caution1);
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


        actions1_checkbox.setOnClickListener(v -> {
            if (actions1_checkbox.isChecked()) {
                caution.setImageResource(R.drawable.ic_done);
                actions1.setBackgroundResource(R.color.lighter_green);
                actions1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution.setImageResource(R.drawable.ic_error2);
                actions1.setBackgroundResource(R.color.beige_red);
                actions1_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions1_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions2_checkbox.setOnClickListener(v -> {
            if (actions2_checkbox.isChecked()) {
                caution1.setImageResource(R.drawable.ic_done);
                actions2.setBackgroundResource(R.color.lighter_green);
                actions2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution1.setImageResource(R.drawable.ic_error2);
                actions2.setBackgroundResource(R.color.beige_red);
                actions2_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions2_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions3_checkbox.setOnClickListener(v -> {
            if (actions3_checkbox.isChecked()) {
                caution2.setImageResource(R.drawable.ic_done);
                actions3.setBackgroundResource(R.color.lighter_green);
                actions3_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions3_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution2.setImageResource(R.drawable.ic_error2);
                actions3.setBackgroundResource(R.color.beige_red);
                actions3_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions3_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions4_checkbox.setOnClickListener(v -> {
            if (actions4_checkbox.isChecked()) {
                caution3.setImageResource(R.drawable.ic_done);
                actions4.setBackgroundResource(R.color.lighter_green);
                actions4_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions4_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions4_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution3.setImageResource(R.drawable.ic_error2);
                actions4.setBackgroundResource(R.color.beige_red);
                actions4_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions4_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions4_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions5_checkbox.setOnClickListener(v -> {
            if (actions5_checkbox.isChecked()) {
                caution4.setImageResource(R.drawable.ic_done);
                actions5.setBackgroundResource(R.color.lighter_green);
                actions5_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions5_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions5_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution4.setImageResource(R.drawable.ic_error2);
                actions5.setBackgroundResource(R.color.beige_red);
                actions5_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions5_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions5_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions6_checkbox.setOnClickListener(v -> {
            if (actions6_checkbox.isChecked()) {
                caution5.setImageResource(R.drawable.ic_done);
                actions6.setBackgroundResource(R.color.lighter_green);
                actions6_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions6_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions6_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution5.setImageResource(R.drawable.ic_error2);
                actions6.setBackgroundResource(R.color.beige_red);
                actions6_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions6_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions6_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions7_checkbox.setOnClickListener(v -> {
            if (actions7_checkbox.isChecked()) {
                caution6.setImageResource(R.drawable.ic_done);
                actions7.setBackgroundResource(R.color.lighter_green);
                actions7_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions7_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution6.setImageResource(R.drawable.ic_error2);
                actions7.setBackgroundResource(R.color.beige_red);
                actions7_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions7_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions8_checkbox.setOnClickListener(v -> {
            if (actions8_checkbox.isChecked()) {
                caution7.setImageResource(R.drawable.ic_done);
                actions8.setBackgroundResource(R.color.lighter_green);
                actions8_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions8_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution7.setImageResource(R.drawable.ic_error2);
                actions8.setBackgroundResource(R.color.beige_red);
                actions8_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions8_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions9_checkbox.setOnClickListener(v -> {
            if (actions9_checkbox.isChecked()) {
                caution8.setImageResource(R.drawable.ic_done);
                actions9.setBackgroundResource(R.color.lighter_green);
                actions9_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions9_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution8.setImageResource(R.drawable.ic_error2);
                actions9.setBackgroundResource(R.color.beige_red);
                actions9_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions9_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions10_checkbox.setOnClickListener(v -> {
            if (actions10_checkbox.isChecked()) {
                caution9.setImageResource(R.drawable.ic_done);
                actions10.setBackgroundResource(R.color.lighter_green);
                actions10_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions10_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions10_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution9.setImageResource(R.drawable.ic_error2);
                actions10.setBackgroundResource(R.color.beige_red);
                actions10_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions10_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions10_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions11_checkbox.setOnClickListener(v -> {
            if (actions11_checkbox.isChecked()) {
                caution10.setImageResource(R.drawable.ic_done);
                actions11.setBackgroundResource(R.color.lighter_green);
                actions11_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions11_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution10.setImageResource(R.drawable.ic_error2);
                actions11.setBackgroundResource(R.color.beige_red);
                actions11_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions11_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions12_checkbox.setOnClickListener(v -> {
            if (actions12_checkbox.isChecked()) {
                caution11.setImageResource(R.drawable.ic_done);
                actions12.setBackgroundResource(R.color.lighter_green);
                actions12_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions12_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution11.setImageResource(R.drawable.ic_error2);
                actions12.setBackgroundResource(R.color.beige_red);
                actions12_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions12_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions13_checkbox.setOnClickListener(v -> {
            if (actions13_checkbox.isChecked()) {
                caution12.setImageResource(R.drawable.ic_done);
                actions13.setBackgroundResource(R.color.lighter_green);
                actions13_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions13_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution12.setImageResource(R.drawable.ic_error2);
                actions13.setBackgroundResource(R.color.beige_red);
                actions13_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions13_checkbox.setText("Done");
            }

            saveData(false);
        });

        actions14_checkbox.setOnClickListener(v -> {
            if (actions14_checkbox.isChecked()) {
                caution13.setImageResource(R.drawable.ic_done);
                actions14.setBackgroundResource(R.color.lighter_green);
                actions14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                actions14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(System.currentTimeMillis())));
            } else {
                caution13.setImageResource(R.drawable.ic_error2);
                actions14.setBackgroundResource(R.color.beige_red);
                actions14_textview.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.red));
                actions14_checkbox.setText("Done");
            }

            saveData(false);
        });

        getPatientDetails();

        setActionsFragments();

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Triage ABCD");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ViewPatientActionsActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void getPatientDetails() {
        final TextView patient_info_text = findViewById(R.id.patient_info_text);

        String network_address = router.ip_address + "sims_patients/get_patient_details/" + patient_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {
                    try {
                        String text = helperFunctions.capitalize_first_letter(response.getString("name")) + " (" + response.getString("date_of_birth") + "), " + response.getString("gender");
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

    private void setActionsFragments() {
        String network_address = router.ip_address + "sims_patients/get_patient_actions/" + patient_id + "/" + new PreferenceManager(this).getEpisodeId();

        @SuppressLint({"SimpleDateFormat", "SetTextI18n"}) JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {
                    try {
                        if (!response.getString("id").equals("0")) {
                            actions_data = new String[]{response.getString("action_status"), response.getString("action_ids"), response.getString("id")};

                            //int severe_illness = Integer.parseInt(actions[1]);
                            action_status = actions_data[0].split(",");
                            String[] action_ids = actions_data[1].split(",");

                            List<String> action_ids_list = Arrays.asList(action_ids);

                            if (action_ids_list.contains("1")) {
                                row_action.setVisibility(View.VISIBLE);
                                if (!action_status[0].equals("1")) {
                                    radio_action_yes.setChecked(true);
                                    actions1.setVisibility(View.VISIBLE);
                                    caution.setImageResource(R.drawable.ic_done);
                                    actions1.setBackgroundResource(R.color.lighter_green);
                                    actions1_checkbox.setChecked(true);
                                    actions1_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions1_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions1_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[0]))));
                                } else if (action_status[0].equals("1")) {
                                    radio_action_no.setChecked(true);
                                    actions1.setVisibility(View.GONE);
                                } else if (action_status[0].equals("3")) {
                                    radio_action_not.setChecked(true);
                                    actions1.setVisibility(View.GONE);
                                }

                            }

                            if (action_ids_list.contains("2")) {
                                row_action1.setVisibility(View.VISIBLE);
                                if (action_status[1].equals("2")) {
                                    radio_action1_yes.setChecked(true);
                                    actions2.setVisibility(View.VISIBLE);
                                    caution1.setImageResource(R.drawable.ic_done);
                                    actions2.setBackgroundResource(R.color.lighter_green);
                                    actions2_checkbox.setChecked(true);
                                    actions2_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions2_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions2_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[1]))));
                                } else if (action_status[1].equals("1")) {
                                    radio_action1_no.setChecked(true);
                                    actions2.setVisibility(View.GONE);
                                } else if (action_status[1].equals("3")) {
                                    radio_action1_not.setChecked(true);
                                    actions2.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("3")) {
                                row_action2.setVisibility(View.VISIBLE);
                                if (action_status[2].equals("2")) {
                                    radio_action2_yes.setChecked(true);
                                    actions3.setVisibility(View.VISIBLE);
                                    caution2.setImageResource(R.drawable.ic_done);
                                    actions3.setBackgroundResource(R.color.lighter_green);
                                    actions3_checkbox.setChecked(true);
                                    actions3_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions3_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions3_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[2]))));
                                } else if (action_status[2].equals("1")) {
                                    radio_action2_no.setChecked(true);
                                    actions3.setVisibility(View.GONE);
                                } else if (action_status[2].equals("3")) {
                                    radio_action2_not.setChecked(true);
                                    actions3.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("4")) {
                                row_action3.setVisibility(View.VISIBLE);

                                if (action_status[3].equals("2")) {
                                    radio_action3_yes.setChecked(true);
                                    actions4.setVisibility(View.VISIBLE);
                                    caution3.setImageResource(R.drawable.ic_done);
                                    actions4.setBackgroundResource(R.color.lighter_green);
                                    actions4_checkbox.setChecked(true);
                                    actions4_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions4_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions4_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[3]))));
                                } else if (action_status[3].equals("1")) {
                                    radio_action3_no.setChecked(true);
                                    actions4.setVisibility(View.GONE);
                                } else if (action_status[3].equals("3")) {
                                    radio_action3_not.setChecked(true);
                                    actions4.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("5")) {
                                row_action4.setVisibility(View.VISIBLE);

                                if (action_status[4].equals("2")) {
                                    radio_action4_yes.setChecked(true);
                                    actions5.setVisibility(View.VISIBLE);
                                    caution4.setImageResource(R.drawable.ic_done);
                                    actions5.setBackgroundResource(R.color.lighter_green);
                                    actions5_checkbox.setChecked(true);
                                    actions5_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions5_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions5_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[4]))));
                                } else if (action_status[4].equals("1")) {
                                    radio_action4_no.setChecked(true);
                                    actions5.setVisibility(View.GONE);
                                } else if (action_status[4].equals("3")) {
                                    radio_action4_not.setChecked(true);
                                    actions5.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("6")) {
                                row_action5.setVisibility(View.VISIBLE);

                                if (action_status[5].equals("2")) {
                                    radio_action5_yes.setChecked(true);
                                    actions6.setVisibility(View.VISIBLE);
                                    caution5.setImageResource(R.drawable.ic_done);
                                    actions6.setBackgroundResource(R.color.lighter_green);
                                    actions6_checkbox.setChecked(true);
                                    actions6_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions6_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions6_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[5]))));
                                } else if (action_status[5].equals("1")) {
                                    radio_action5_no.setChecked(true);
                                    actions6.setVisibility(View.GONE);
                                } else if (action_status[5].equals("3")) {
                                    radio_action5_not.setChecked(true);
                                    actions6.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("7")) {
                                row_action6.setVisibility(View.VISIBLE);

                                if (action_status[6].equals("2")) {
                                    radio_action6_yes.setChecked(true);
                                    actions7.setVisibility(View.VISIBLE);
                                    caution6.setImageResource(R.drawable.ic_done);
                                    actions7.setBackgroundResource(R.color.lighter_green);
                                    actions7_checkbox.setChecked(true);
                                    actions7_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions7_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions7_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[6]))));
                                } else if (action_status[6].equals("1")) {
                                    radio_action6_no.setChecked(true);
                                    actions7.setVisibility(View.GONE);
                                } else if (action_status[6].equals("3")) {
                                    radio_action6_not.setChecked(true);
                                    actions7.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("8")) {
                                row_action7.setVisibility(View.VISIBLE);

                                if (action_status[7].equals("2")) {
                                    radio_action7_yes.setChecked(true);
                                    actions8.setVisibility(View.VISIBLE);
                                    caution7.setImageResource(R.drawable.ic_done);
                                    actions8.setBackgroundResource(R.color.lighter_green);
                                    actions8_checkbox.setChecked(true);
                                    actions8_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions8_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions8_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[7]))));
                                } else if (action_status[7].equals("1")) {
                                    radio_action7_no.setChecked(true);
                                    actions8.setVisibility(View.GONE);
                                } else if (action_status[7].equals("3")) {
                                    radio_action7_not.setChecked(true);
                                    actions8.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("9")) {
                                row_action8.setVisibility(View.VISIBLE);

                                if (action_status[8].equals("2")) {
                                    radio_action8_yes.setChecked(true);
                                    actions9.setVisibility(View.VISIBLE);
                                    caution8.setImageResource(R.drawable.ic_done);
                                    actions9.setBackgroundResource(R.color.lighter_green);
                                    actions9_checkbox.setChecked(true);
                                    actions9_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions9_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions9_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[8]))));
                                } else if (action_status[8].equals("1")) {
                                    radio_action8_no.setChecked(true);
                                    actions9.setVisibility(View.GONE);
                                } else if (action_status[8].equals("3")) {
                                    radio_action8_not.setChecked(true);
                                    actions9.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("10")) {
                                row_action9.setVisibility(View.VISIBLE);

                                if (action_status[9].equals("2")) {
                                    radio_action9_yes.setChecked(true);
                                    actions10.setVisibility(View.VISIBLE);
                                    caution9.setImageResource(R.drawable.ic_done);
                                    actions10.setBackgroundResource(R.color.lighter_green);
                                    actions10_checkbox.setChecked(true);
                                    actions10_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions10_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions10_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[9]))));
                                } else if (action_status[9].equals("1")) {
                                    radio_action9_no.setChecked(true);
                                    actions10.setVisibility(View.GONE);
                                } else if (action_status[9].equals("3")) {
                                    radio_action9_not.setChecked(true);
                                    actions10.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("11")) {
                                row_action8.setVisibility(View.VISIBLE);

                                if (action_status[10].equals("2")) {
                                    radio_action10_yes.setChecked(true);
                                    actions11.setVisibility(View.VISIBLE);
                                    caution10.setImageResource(R.drawable.ic_done);
                                    actions11.setBackgroundResource(R.color.lighter_green);
                                    actions11_checkbox.setChecked(true);
                                    actions11_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions11_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions11_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[10]))));
                                } else if (action_status[10].equals("1")) {
                                    radio_action10_no.setChecked(true);
                                    actions11.setVisibility(View.GONE);
                                } else if (action_status[10].equals("3")) {
                                    radio_action10_not.setChecked(true);
                                    actions11.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("12")) {
                                row_action9.setVisibility(View.VISIBLE);

                                if (action_status[11].equals("2")) {
                                    radio_action11_yes.setChecked(true);
                                    actions12.setVisibility(View.VISIBLE);
                                    caution11.setImageResource(R.drawable.ic_done);
                                    actions12.setBackgroundResource(R.color.lighter_green);
                                    actions12_checkbox.setChecked(true);
                                    actions12_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions12_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions12_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[11]))));
                                } else if (action_status[11].equals("1")) {
                                    radio_action11_no.setChecked(true);
                                    actions12.setVisibility(View.GONE);
                                } else if (action_status[11].equals("3")) {
                                    radio_action11_not.setChecked(true);
                                    actions12.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("13")) {
                                row_action10.setVisibility(View.VISIBLE);

                                if (action_status[12].equals("2")) {
                                    radio_action12_yes.setChecked(true);
                                    actions13.setVisibility(View.VISIBLE);
                                    caution12.setImageResource(R.drawable.ic_done);
                                    actions13.setBackgroundResource(R.color.lighter_green);
                                    actions13_checkbox.setChecked(true);
                                    actions13_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions13_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions13_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[12]))));
                                } else if (action_status[12].equals("1")) {
                                    radio_action12_no.setChecked(true);
                                    actions13.setVisibility(View.GONE);
                                } else if (action_status[12].equals("3")) {
                                    radio_action12_not.setChecked(true);
                                    actions13.setVisibility(View.GONE);
                                }
                            }

                            if (action_ids_list.contains("14")) {
                                row_action11.setVisibility(View.VISIBLE);
                                if (!action_status[13].equals("0")) {
                                    actions14.setBackgroundResource(R.color.lighter_green);
                                    actions14_checkbox.setChecked(true);
                                    actions14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[13]))));
                                }

                                if (action_status[13].equals("2")) {
                                    radio_action13_yes.setChecked(true);
                                    actions14.setVisibility(View.VISIBLE);
                                    caution13.setImageResource(R.drawable.ic_done);
                                    actions14.setBackgroundResource(R.color.lighter_green);
                                    actions14_checkbox.setChecked(true);
                                    actions14_textview.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions14_checkbox.setTextColor(ContextCompat.getColor(this, R.color.green));
                                    actions14_checkbox.setText("Done: " + new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date(Long.parseLong(action_status[13]))));
                                } else if (action_status[13].equals("1")) {
                                    radio_action13_no.setChecked(true);
                                    actions14.setVisibility(View.GONE);
                                } else if (action_status[13].equals("3")) {
                                    radio_action13_not.setChecked(true);
                                    actions14.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            no_vitals_recorded.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //
        });

        Volley.newRequestQueue(this).add(request);
    }

    public void saveData(View view) {
        saveData(true);
    }

    public void saveData(boolean redirect_to_patient_home) {
        String action_status_string = Arrays.toString(action_status).replaceAll("[\\[.\\].\\s+]", "");

        new DataRouter(this).editActions(Integer.parseInt(actions_data[2]), action_status_string, redirect_to_patient_home);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_action_yes:
                if (checked)
                    actions1.setVisibility(View.VISIBLE);
                action_status[0] = "2";
                break;
            case R.id.radio_action_no:
                if (checked)
                    actions1.setVisibility(View.GONE);
                action_status[0] = "1";
                break;
            case R.id.radio_action_not:
                if (checked)
                    actions1.setVisibility(View.GONE);
                action_status[0] = "3";
                break;
            case R.id.radio_action1_yes:
                if (checked)
                    actions2.setVisibility(View.VISIBLE);
                action_status[1] = "2";
                break;
            case R.id.radio_action1_no:
                actions2.setVisibility(View.GONE);
                action_status[1] = "1";
                break;
            case R.id.radio_action1_not:
                if (checked)
                    actions2.setVisibility(View.GONE);
                action_status[1] = "3";
                break;
            case R.id.radio_action2_yes:
                if (checked)
                    actions3.setVisibility(View.VISIBLE);
                action_status[2] = "2";
                break;
            case R.id.radio_action2_no:
                if (checked)
                    action_status[2] = "1";
                actions3.setVisibility(View.GONE);
                break;
            case R.id.radio_action2_not:
                if (checked)
                    actions2.setVisibility(View.GONE);
                action_status[2] = "3";
                break;

            case R.id.radio_action3_yes:
                if (checked)
                    action_status[3] = "2";
                actions4.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_action3_no:
                if (checked)
                    action_status[3] = "1";
                actions4.setVisibility(View.GONE);
                break;
            case R.id.radio_action3_not:
                if (checked)
                    action_status[3] = "3";
                actions4.setVisibility(View.GONE);
                break;

            case R.id.radio_action4_yes:
                if (checked)
                    action_status[4] = "2";
                actions5.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_action4_no:
                if (checked)
                    actions5.setVisibility(View.GONE);
                action_status[4] = "1";
                break;
            case R.id.radio_action4_not:
                if (checked)
                    actions5.setVisibility(View.GONE);
                action_status[4] = "3";
                break;

            case R.id.radio_action5_yes:
                if (checked)
                    actions6.setVisibility(View.VISIBLE);
                action_status[5] = "2";
                break;
            case R.id.radio_action5_no:
                if (checked)
                    actions6.setVisibility(View.VISIBLE);
                action_status[5] = "1";
                break;
            case R.id.radio_action5_not:
                if (checked)
                    actions6.setVisibility(View.GONE);
                action_status[5] = "3";
                break;

            case R.id.radio_action6_yes:
                if (checked)
                    actions7.setVisibility(View.VISIBLE);
                action_status[6] = "2";
                break;
            case R.id.radio_action6_no:
                if (checked)
                    actions7.setVisibility(View.GONE);
                action_status[6] = "1";
                break;
            case R.id.radio_action6_not:
                if (checked)
                    actions7.setVisibility(View.GONE);
                action_status[6] = "3";
                break;

            case R.id.radio_action7_yes:
                if (checked)
                    actions8.setVisibility(View.VISIBLE);
                action_status[7] = "2";
                break;
            case R.id.radio_action7_no:
                if (checked)
                    actions8.setVisibility(View.GONE);
                action_status[7] = "1";
                break;
            case R.id.radio_action7_not:
                if (checked)
                    action_status[7] = "3";
                actions8.setVisibility(View.GONE);
                break;

            case R.id.radio_action8_yes:
                if (checked)
                    actions9.setVisibility(View.VISIBLE);
                action_status[8] = "2";
                break;
            case R.id.radio_action8_no:
                if (checked)
                    actions9.setVisibility(View.GONE);
                action_status[8] = "1";
                break;
            case R.id.radio_action8_not:
                if (checked)
                    actions9.setVisibility(View.GONE);
                action_status[8] = "3";
                break;

            case R.id.radio_action9_yes:
                if (checked)
                    actions10.setVisibility(View.VISIBLE);
                action_status[9] = "2";
                break;
            case R.id.radio_action9_no:
                if (checked)
                    actions10.setVisibility(View.GONE);
                action_status[9] = "1";
                break;
            case R.id.radio_action9_not:
                if (checked)
                    actions10.setVisibility(View.GONE);
                action_status[9] = "3";
                break;
            case R.id.radio_action10_yes:
                if (checked)
                    actions11.setVisibility(View.VISIBLE);
                action_status[10] = "2";
                break;
            case R.id.radio_action10_no:
                if (checked)
                    actions11.setVisibility(View.GONE);
                action_status[10] = "1";
                break;
            case R.id.radio_action10_not:
                if (checked)
                    actions11.setVisibility(View.GONE);
                action_status[10] = "3";
                break;
            case R.id.radio_action11_yes:
                if (checked)
                    actions12.setVisibility(View.VISIBLE);
                action_status[11] = "2";
                break;
            case R.id.radio_action11_no:
                if (checked)
                    actions12.setVisibility(View.GONE);
                action_status[11] = "1";
                break;
            case R.id.radio_action11_not:
                if (checked)
                    actions12.setVisibility(View.GONE);
                action_status[11] = "3";
                break;
            case R.id.radio_action12_yes:
                if (checked)
                    actions13.setVisibility(View.VISIBLE);
                action_status[12] = "2";
                break;
            case R.id.radio_action12_no:
                if (checked)
                    actions13.setVisibility(View.GONE);
                action_status[12] = "1";
                break;
            case R.id.radio_action12_not:
                if (checked)
                    actions13.setVisibility(View.GONE);
                action_status[12] = "3";
                break;
            case R.id.radio_action13_yes:
                if (checked)
                    actions14.setVisibility(View.VISIBLE);
                action_status[13] = "2";
                break;
            case R.id.radio_action13_no:
                if (checked)
                    actions14.setVisibility(View.GONE);
                action_status[13] = "1";
                break;
            case R.id.radio_action13_not:
                if (checked)
                    actions14.setVisibility(View.GONE);
                action_status[13] = "3";
                break;
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
