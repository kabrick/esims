package tech.streamlinehealth.esims.patients.agewell;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.healthcubed.ezdx.agewelllib.bluetoothHandler.AgeWellBT;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.AgeWellData;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.BG;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.BatteryStatus;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.ECG;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.NIBP;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.SpO2;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.Temp;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.onPackageReceivedListener;
import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class TemperatureActivity extends AppCompatActivity implements onPackageReceivedListener {
    HelperFunctions helperFunctions;
    TextView temp_status, temp_results;
    double temperature = 0;
    Button save_results, stop_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        temp_status = findViewById(R.id.temp_status);
        temp_results = findViewById(R.id.temp_results);
        save_results = findViewById(R.id.save_results);
        stop_scan = findViewById(R.id.stop_scan);

        helperFunctions = new HelperFunctions(this);

        new AgeWellData(this).start();

        AgeWellBT.authenticate(helperFunctions.age_well_tk);
        AgeWellBT.startTemperature();

        stop_scan.setOnClickListener(view -> {
            //test finished
            temp_status.setText("Status: Test Complete.");
            AgeWellBT.stopTemperature();
            save_results.setVisibility(View.VISIBLE);
            stop_scan.setVisibility(View.GONE);
        });

        save_results.setOnClickListener(view -> {
            new PreferenceManager(this).setAgewellTemp(temperature);
            startActivity(new Intent(this, ChooseTestActivity.class));
        });
    }

    public void onTempReceived(final Temp temp) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run() {
                int status = temp.getStatus();
                if (status == 0) {
                    temperature = temp.getTemperature();
                    temp_status.setText("Status: Test Running. Please remain still for 5 minutes. " +
                            "When they elapse please click the 'Stop Scan' button");
                } else if (status == 1) {
                    temp_status.setText("Status: Connect sensor probe to device");
                }
            }
        });
    }

    public void onDestroy() {
        AgeWellBT.stopPulseOximetry();
        super.onDestroy();
    }

    public void onDisconnected() {
        Toast.makeText(this, "Device disconnected", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onSpO2Received(final SpO2 spo2) {}

    public void onECGWaveReceived(int dat) {}

    public void onBGReceived(BG bg) {}

    public void onECGReceived(ECG ecg) {}

    public void onNIBPReceived(NIBP nibp) {}

    public void onBatteryStatusReceived(final BatteryStatus batteryStatus) {}

}