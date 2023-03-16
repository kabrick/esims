package tech.streamlinehealth.esims.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class ForgotPassword extends AppCompatActivity {
    EditText email, secretPin;
    HelperFunctions helperFunctions;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        helperFunctions = new HelperFunctions(this);
        preferenceManager = new PreferenceManager(this);

        email = findViewById(R.id.email);
        secretPin = findViewById(R.id.secret_pin);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveDetails(View view) {
        String email_ = email.getText().toString();
        String secretPin_ = secretPin.getText().toString();

        if (email_.isEmpty()) {
            helperFunctions.genericDialog("Please fill in the email");
            return;
        }

        if (secretPin_.isEmpty()) {
            helperFunctions.genericDialog("Please fill in the secret pin");
            return;
        }

        new DataRouter(this).forgotPassword(email_, secretPin_);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
        finish();
    }
}
