package tech.streamlinehealth.esims.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.auth.LoginActivity;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;

public class CreateUserActivity extends AppCompatActivity {

    EditText user_first_name, user_last_name, user_phone_number,
            user_email, user_username, user_password, user_secret_pin;
    HelperFunctions helperFunctions;
    RadioButton is_user_admin_yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_first_name = findViewById(R.id.user_first_name);
        user_last_name = findViewById(R.id.user_last_name);
        user_phone_number = findViewById(R.id.user_phone_number);
        user_email = findViewById(R.id.user_email);
        user_username = findViewById(R.id.user_username);
        user_password = findViewById(R.id.user_password);
        user_secret_pin = findViewById(R.id.user_secret_pin);
        is_user_admin_yes = findViewById(R.id.is_user_admin_yes);

        helperFunctions = new HelperFunctions(this);

    }

    public void registerUser(View view){
        String user_first_name_string = user_first_name.getText().toString();
        String user_last_name_string = user_last_name.getText().toString();
        String user_username_string = user_username.getText().toString();
        String user_password_string = user_password.getText().toString();
        String user_secret_pin_string = user_secret_pin.getText().toString();
        String user_is_admin = "0";

        if (is_user_admin_yes.isChecked()){
            user_is_admin = "1";
        }

        if(user_first_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the first name");
            return;
        }

        if(user_last_name_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the last name");
            return;
        }

        if(user_username_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the username");
            return;
        }

        if(user_password_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the password");
            return;
        }

        if(user_secret_pin_string.isEmpty()){
            helperFunctions.genericDialog("Please fill in the pin");
            return;
        }

        new DataRouter(this).addUser(user_first_name_string, user_last_name_string, user_phone_number.getText().toString(),
                user_email.getText().toString(), user_username_string, user_password_string, user_secret_pin_string, user_is_admin);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
