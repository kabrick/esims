package tech.streamlinehealth.esims.patients.agewell;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class BloodPressureActivity extends AppCompatActivity implements onPackageReceivedListener {
    HelperFunctions helperFunctions;
    TextView bp_status, bp_results;
    Button save_results;
    int systolic_bp, diastolic_bp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        bp_status = findViewById(R.id.bp_status);
        bp_results = findViewById(R.id.bp_results);
        save_results = findViewById(R.id.save_results);

        helperFunctions = new HelperFunctions(this);

        new AgeWellData(this).start();

        AgeWellBT.authenticate(helperFunctions.age_well_tk);
        AgeWellBT.startBloodPressure();

        save_results.setOnClickListener(view -> {
            new PreferenceManager(this).setAgewellSBP(String.valueOf(systolic_bp));
            new PreferenceManager(this).setAgewellDBP(String.valueOf(diastolic_bp));
            startActivity(new Intent(this, ChooseTestActivity.class));
        });
    }

    public void onNIBPReceived(final NIBP nibp) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run() {
                try {
                    Log.e("an error:", String.valueOf(nibp.getStatus()));
                    switch (nibp.getStatus()) {
                        case 0:
                            bp_status.setText("Test Finished");
                            systolic_bp = nibp.getHighPressure();
                            diastolic_bp = nibp.getLowPressure();
                            AgeWellBT.stopBloodPressure();
                            save_results.setVisibility(View.VISIBLE);
                            return;
                        case 4:
                            bp_status.setText("Analyzing");
                            return;
                        case 8:
                            bp_status.setText("Test Stopped");
                            return;
                        case 12:
                            bp_status.setText("Probe is not connected to device");
                            return;
                        case 16:
                            bp_status.setText("Cuff is too loose or unattached");
                            return;
                        case 20:
                            bp_status.setText("Time out");
                            return;
                        case 24:
                        case 28:
                            bp_status.setText("Error occurred");
                            return;
                        case 32:
                            bp_status.setText("Result out of range");
                            return;
                        case 36:
                        case 40:
                            bp_status.setText("Initializing");
                            return;
                        default:
                    }
                } catch (Exception ignore) {
                    Log.e("an error:", "an error occurred");
                }
            }
        });
    }

    public void onSpO2Received(final SpO2 spo2) {}

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

    public void onBatteryStatusReceived(final BatteryStatus batteryStatus) {}
}