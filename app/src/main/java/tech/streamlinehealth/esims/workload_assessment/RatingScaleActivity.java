package tech.streamlinehealth.esims.workload_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.HelperFunctions;

public class RatingScaleActivity extends AppCompatActivity {

    SeekBar mental_seekbar, physical_seekbar, temporal_seekbar, performance_seekbar, effort_seekbar, frustration_seekbar;
    TextView mental_score_percentage, physical_score_percentage, temporal_score_percentage, performance_score_percentage,
            effort_score_percentage, frustration_score_percentage;

    int mental_score, physical_score, temporal_score, performance_score, effort_score, frustration_score = 0;
    int step = 5;

    HelperFunctions helperFunctions;

    String task_id, task_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_scale);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            task_id = extras.getString("id", "1");
            task_name = extras.getString("name", "");
        }
        else {
            onBackPressed();
        }

        helperFunctions = new HelperFunctions(this);

        mental_seekbar = findViewById(R.id.mental_seekbar);
        mental_score_percentage = findViewById(R.id.mental_score_percentage);
        physical_seekbar = findViewById(R.id.physical_seekbar);
        physical_score_percentage = findViewById(R.id.physical_score_percentage);
        temporal_seekbar = findViewById(R.id.temporal_seekbar);
        temporal_score_percentage = findViewById(R.id.temporal_score_percentage);
        performance_seekbar = findViewById(R.id.performance_seekbar);
        performance_score_percentage = findViewById(R.id.performance_score_percentage);
        effort_seekbar = findViewById(R.id.effort_seekbar);
        effort_score_percentage = findViewById(R.id.effort_score_percentage);
        frustration_seekbar = findViewById(R.id.frustration_seekbar);
        frustration_score_percentage = findViewById(R.id.frustration_score_percentage);

        mental_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean progressChangeInitiatedByUser) {
                if ((progress % step) != 0) {
                    // not divisible by 5 hence go to nearest 5 figure
                    progress = (progress / step) * step;
                    mental_seekbar.setProgress(progress);
                }

                mental_score_percentage.setText("Score: " + progress);
                mental_score = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        physical_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean progressChangeInitiatedByUser) {
                if ((progress % step) != 0) {
                    // not divisible by 5 hence go to nearest 5 figure
                    progress = (progress / step) * step;
                    physical_seekbar.setProgress(progress);
                }

                physical_score_percentage.setText("Score: " + progress);
                physical_score = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        temporal_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean progressChangeInitiatedByUser) {
                if ((progress % step) != 0) {
                    // not divisible by 5 hence go to nearest 5 figure
                    progress = (progress / step) * step;
                    temporal_seekbar.setProgress(progress);
                }

                temporal_score_percentage.setText("Score: " + progress);
                temporal_score = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        performance_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean progressChangeInitiatedByUser) {
                if ((progress % step) != 0) {
                    // not divisible by 5 hence go to nearest 5 figure
                    progress = (progress / step) * step;
                    performance_seekbar.setProgress(progress);
                }

                performance_score_percentage.setText("Score: " + progress);
                performance_score = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        effort_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean progressChangeInitiatedByUser) {
                if ((progress % step) != 0) {
                    // not divisible by 5 hence go to nearest 5 figure
                    progress = (progress / step) * step;
                    effort_seekbar.setProgress(progress);
                }

                effort_score_percentage.setText("Score: " + progress);
                effort_score = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        frustration_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean progressChangeInitiatedByUser) {
                if ((progress % step) != 0) {
                    // not divisible by 5 hence go to nearest 5 figure
                    progress = (progress / step) * step;
                    frustration_seekbar.setProgress(progress);
                }

                frustration_score_percentage.setText("Score: " + progress);
                frustration_score = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void save_ratings(View view) {
        if (mental_score == 0 && physical_score == 0 && temporal_score == 0 && effort_score == 0) {
            helperFunctions.genericDialog("Please make sure you rate your task experiences");
            return;
        }

        Intent intent = new Intent(this, RatingWorkloadActivity.class);
        intent.putExtra("id", task_id);
        intent.putExtra("name", task_name);
        intent.putExtra("mental_score", mental_score);
        intent.putExtra("physical_score", physical_score);
        intent.putExtra("temporal_score", temporal_score);
        intent.putExtra("performance_score", performance_score);
        intent.putExtra("effort_score", effort_score);
        intent.putExtra("frustration_score", frustration_score);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}