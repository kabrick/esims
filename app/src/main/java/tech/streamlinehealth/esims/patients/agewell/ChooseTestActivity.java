package tech.streamlinehealth.esims.patients.agewell;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.AgeWellBT;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.AgeWellData;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.data.BleDevice;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.exception.BleException;
import com.healthcubed.ezdx.agewelllib.bluetoothHandler.model.Messaging;
import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.patients.ViewPatientVitalsActivity;

import java.util.ArrayList;
import java.util.List;

public class ChooseTestActivity extends AppCompatActivity implements AgeWellData.OnBluetoothScanCallback, BTAdapter.ItemClickListener {
    Button blood_pressure_btn, pulse_oximetry_btn, connect_device_btn, temperature_btn;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private BluetoothAdapter mBluetoothAdapter;
    BTAdapter adapter;
    private boolean mScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);

        blood_pressure_btn = findViewById(R.id.blood_pressure_btn);
        pulse_oximetry_btn = findViewById(R.id.pulse_oximetry_btn);
        connect_device_btn = findViewById(R.id.connect_device_btn);
        temperature_btn = findViewById(R.id.temperature_btn);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BTAdapter bTAdapter = new BTAdapter(this, new ArrayList());
        adapter = bTAdapter;
        bTAdapter.setClickListener(this);
        recyclerView.setAdapter(this.adapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        connect_device_btn.setOnClickListener(view -> {
            if (arePermissionsClear()) {
                blood_pressure_btn.setVisibility(View.VISIBLE);
                pulse_oximetry_btn.setVisibility(View.VISIBLE);
                temperature_btn.setVisibility(View.VISIBLE);
                connect_device_btn.setVisibility(View.GONE);
            }
        });

        if (arePermissionsClear()) {
            blood_pressure_btn.setVisibility(View.VISIBLE);
            pulse_oximetry_btn.setVisibility(View.VISIBLE);
            temperature_btn.setVisibility(View.VISIBLE);
            connect_device_btn.setVisibility(View.GONE);
        }

        blood_pressure_btn.setOnClickListener(view -> startActivity(new Intent(this, BloodPressureActivity.class)));

        pulse_oximetry_btn.setOnClickListener(view -> startActivity(new Intent(this, PulseOximetryActivity.class)));

        temperature_btn.setOnClickListener(view -> startActivity(new Intent(this, TemperatureActivity.class)));

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                } else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
    }

    private boolean arePermissionsClear() {
        if (initPermission()) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 101);
            } else if (!AgeWellBT.isLocationEnabled(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Please enable location to connect.", Toast.LENGTH_LONG).show();
                startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            } else if (AgeWellBT.isAgeWellConnected().equals(Messaging.CONNECTED)) {
                return true;
            } else {
                startStopScan();
                return false;
            }
        }

        return false;
    }

    public boolean initPermission() {
        String[] permissions = {"android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
        List<String> mPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != 0) {
                mPermissionList.add(permission);
            }
        }
        if (mPermissionList.size() <= 0) {
            return true;
        }
        Toast.makeText(this, "Please Allow the permission to continue", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this, permissions, 100);
        return false;
    }

    public void onDeviceDiscovered(BleDevice device, int rssi) {
        if (adapter.getDevices().indexOf(device) < 0) {
            adapter.getDevices().add(device);
            BTAdapter bTAdapter = adapter;
            bTAdapter.notifyItemInserted(bTAdapter.getDevices().size() - 1);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void onStartScan() {
        mScanning = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onStopScan() {
        mScanning = false;
        progressBar.setVisibility(View.GONE);
    }

    public void startStopScan() {
        if (!mScanning) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            AgeWellBT.startBTScanning(this);
        } else {
            AgeWellBT.stopBTScanning();
        }
    }

    public void onItemClick(BleDevice device) {
        AgeWellBT.startConnection(device, this);
    }

    public void onConnectStarted(BleDevice device) {
        Toast.makeText(this, "Connecting please wait", Toast.LENGTH_SHORT).show();
    }

    public void onConnectFailed(BleDevice device, BleException e) {
        Toast.makeText(this, "Unable to connect", Toast.LENGTH_SHORT).show();
    }

    public void onConnectSuccess(BleDevice device) {
        Toast.makeText(this, "Connected successfully", Toast.LENGTH_SHORT).show();
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        connect_device_btn.callOnClick();
    }

    public void onDisconnected(BleDevice device) {
        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
    }

    public void onDestroy() {
        AgeWellBT.stopBTScanning();
        super.onDestroy();
    }

    public void back_to_patient(View view) {
        new PreferenceManager(this).setAgewellActive(true);
        startActivity(new Intent(this, ViewPatientVitalsActivity.class));
    }
}