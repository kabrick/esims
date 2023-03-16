package tech.streamlinehealth.esims.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

import tech.streamlinehealth.esims.HomeActivity;
import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.users.CreateUserActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    String username_string;
    HelperFunctions helperFunctions;
    PreferenceManager preferenceManager;
    private FirebaseAnalytics firebaseAnalytics;
    DataRouter dataRouter;
    long start_time = 0;
    private static final String TAG = "LoginActivity";
    boolean is_timer_off = false; // this will track whether our timer has been turned off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataRouter = new DataRouter(this);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        preferenceManager = new PreferenceManager(this);
        helperFunctions = new HelperFunctions(this);

        if(preferenceManager.isSignedIn()){
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            start_time = System.currentTimeMillis();
        }

        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
    }

    @Override
    protected void onDestroy() {
        if (!is_timer_off && start_time != 0) {
            // here we just check if the timer has been turned off by the login button
            // to indicate that this is a bounce, we use zero instead of the current milli time
            is_timer_off = true;
            dataRouter.save_data_entry_time("login", String.valueOf(start_time), "0");
        }

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Login");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoginActivity");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    public void loginUser(View view){
        username_string = username.getText().toString();
        String password_string = password.getText().toString();

        if(username_string.isEmpty()){
            dataRouter.save_task_completion_rate("login", "0", "Did not fill in username");
            helperFunctions.genericDialog("Please fill in the username");
            return;
        }

        if(password_string.isEmpty()){
            dataRouter.save_task_completion_rate("login", "0", "Did not fill in password");
            helperFunctions.genericDialog("Please fill in the password");
            return;
        }

        // since the user has clicked a button, we assume they have entered data so that is the data entry time
        is_timer_off = true;

        dataRouter.userSignIn(username_string, password_string, String.valueOf(start_time), String.valueOf(System.currentTimeMillis()));
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "login");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    public void forgotPassword(View view){
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }
}

