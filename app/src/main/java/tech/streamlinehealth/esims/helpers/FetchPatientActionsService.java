package tech.streamlinehealth.esims.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import tech.streamlinehealth.esims.HomeActivity;
import tech.streamlinehealth.esims.R;

public class FetchPatientActionsService extends Service {
    public static final String CHANNEL_ID = "push_notification";

    @Override
    public void onCreate() {
        String url = new DataRouter(this).ip_address + "sims_users/fetch_patient_actions_by_doctor/" + new PreferenceManager(this).getUserId();

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            String text = "Hello, patient " + jsonobject.getString("patient_name") + " has " +
                                    jsonobject.getString("actions_required") + " actions pending";
                            new HelperFunctions(this).displayNotification(this, "Patient actions", text);
                            stopSelf();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            stopSelf();
                        }
                    }
                }, error -> stopSelf());

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Esims")
                .setContentText("Checking for new notifications...")
                .setSmallIcon(R.drawable.esims_logo)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Push Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
