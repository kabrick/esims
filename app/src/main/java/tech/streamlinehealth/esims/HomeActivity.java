package tech.streamlinehealth.esims;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.concurrent.TimeUnit;

import tech.streamlinehealth.esims.auth.LoginActivity;
import tech.streamlinehealth.esims.clinical_data.ViewClinicalDataActivity;
import tech.streamlinehealth.esims.helpers.FetchPatientActionsWorker;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.workload_assessment.ViewTasksListsActivity;
import tech.streamlinehealth.esims.patients.AddPatientActivity;
import tech.streamlinehealth.esims.patients.ViewPatientsActivity;
import tech.streamlinehealth.esims.users.DisclaimerActivity;
import tech.streamlinehealth.esims.users.ViewUsersActivity;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    PreferenceManager preferenceManager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        preferenceManager = new PreferenceManager(this);
        String user = preferenceManager.getUserName();
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.user_name);
        nav_user.setText(user);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // set up the worker to check for notifications regularly
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("fetch_push_notifications",
                ExistingPeriodicWorkPolicy.KEEP,
                new PeriodicWorkRequest.Builder(FetchPatientActionsWorker.class, 10,
                        TimeUnit.MINUTES, 5, TimeUnit.MINUTES).build());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_disclaimer, R.id.nav_account, R.id.nav_settings, R.id.nav_analysis, R.id.nav_vital_signs
                , R.id.nav_severe_illness, R.id.nav_severe_illness_mgt, R.id.nav_patient_outcomes, R.id.nav_workload_assessment)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Setting logout action
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_disclaimer).setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(this, DisclaimerActivity.class));
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_workload_assessment).setOnMenuItemClickListener(menuItem -> {
            startActivity(new Intent(this, ViewTasksListsActivity.class));
            return true;
        });

        Bundle bundle = new Bundle();
        String id = "1";
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        String name = "Main Screen" ;
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout() {
        preferenceManager.setSignedIn(false);
        preferenceManager.setUserId("");
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    public void dashboard_new_patient(View view){
        startActivity(new Intent(this, AddPatientActivity.class));
    }

    public void dashboard_search_patients(View view){
        startActivity(new Intent(this, ViewPatientsActivity.class));
    }

    public void dashboard_clinical_data(View view){
        startActivity(new Intent(this, ViewClinicalDataActivity.class));
    }

    public void dashboard_users(View view){
        startActivity(new Intent(this, ViewUsersActivity.class));
    }


}

