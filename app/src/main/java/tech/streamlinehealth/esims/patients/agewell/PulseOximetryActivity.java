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

import java.util.ArrayList;
import java.util.List;

public class PulseOximetryActivity extends AppCompatActivity implements onPackageReceivedListener {
    HelperFunctions helperFunctions;
    TextView po_status, po_results;
    List<Integer> pulse_rates = new ArrayList<>();
    List<Integer> oxy_sats = new ArrayList<>();
    int required_time = 15;
    Button save_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_oximetry);

        po_status = findViewById(R.id.po_status);
        po_results = findViewById(R.id.po_results);
        save_results = findViewById(R.id.save_results);

        helperFunctions = new HelperFunctions(this);

        new AgeWellData(this).start();

        AgeWellBT.authenticate(helperFunctions.age_well_tk);
        AgeWellBT.startPulseOximetry();

        save_results.setOnClickListener(view -> {
            int pulse = 0;

            for (Integer value : pulse_rates) {
                pulse += value;
            }

            pulse = pulse / pulse_rates.size();

            int oxy_sat = 0;

            for (Integer value : oxy_sats) {
                oxy_sat += value;
            }

            oxy_sat = oxy_sat / pulse_rates.size();

            new PreferenceManager(this).setAgewellPulse(String.valueOf(pulse));
            new PreferenceManager(this).setAgewellOxygen(String.valueOf(oxy_sat));
            startActivity(new Intent(this, ChooseTestActivity.class));
        });
    }

    public void onSpO2Received(final SpO2 spo2) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run() {
                int status = spo2.getStatus();
                if (status == 0) {
                    pulse_rates.add(spo2.getPulseRate());
                    oxy_sats.add(spo2.getSpO2());

                    if (required_time > 0) {
                        po_status.setText("Status: Test Running. Please remain still for " + required_time);
                        required_time -= 1;
                    } else {
                        //test finished
                        po_status.setText("Status: Test Complete.");
                        AgeWellBT.stopPulseOximetry();
                        save_results.setVisibility(View.VISIBLE);
                    }
                } else if (status == 1) {
                    po_status.setText("Status: Connect sensor probe to device");
                } else if (status == 2) {
                    po_status.setText("Status: Place finger in the probe");
                } else if (status == 3) {
                    po_status.setText("Status: Searching pulse signal");
                } else if (status == 4) {
                    po_status.setText("Status: Test time out");
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

    public void onECGWaveReceived(int dat) {}

    public void onBGReceived(BG bg) {}

    public void onECGReceived(ECG ecg) {}

    public void onTempReceived(Temp temp) {}

    public void onNIBPReceived(NIBP nibp) {}

    public void onBatteryStatusReceived(final BatteryStatus batteryStatus) {}
}