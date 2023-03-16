package tech.streamlinehealth.esims.helpers;

import android.app.Application;

import com.healthcubed.ezdx.agewelllib.bluetoothHandler.AgeWellBT;

public class Sims extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AgeWellBT.initBT(this);
    }
}
