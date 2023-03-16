package tech.streamlinehealth.esims.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class ResetPassword extends AppCompatActivity {
    PreferenceManager preferenceManager;
    String id;
    EditText password, confirm_password;
    HelperFunctions helperFunctions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        helperFunctions = new HelperFunctions(this);
        preferenceManager = new PreferenceManager(this);
        id = preferenceManager.getUserId();
        System.out.println("user is three " + id);

        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_pwd);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void resetPassword(View view) {
        String pwd = password.getText().toString();
        String confirm_pwd = confirm_password.getText().toString();

        if (pwd.isEmpty()) {
            helperFunctions.genericDialog("Please fill in the email");
            return;
        }

        if (confirm_pwd.isEmpty()) {
            helperFunctions.genericDialog("Please fill in the secret pin");
            return;
        }

        if (pwd.equals(confirm_pwd)) {
            new DataRouter(this).resetPassword(id, pwd);
        } else {
            helperFunctions.genericDialog("Passwords do not match");
            return;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ResetPassword.this, ForgotPassword.class));
        finish();
    }

}
