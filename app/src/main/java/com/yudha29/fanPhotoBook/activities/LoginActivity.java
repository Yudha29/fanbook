package com.yudha29.fanPhotoBook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.yudha29.fanPhotoBook.databinding.ActivityLoginBinding;
import com.yudha29.fanPhotoBook.helpers.UserDBHelper;
import com.yudha29.fanPhotoBook.models.User;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding; // View binding object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout into binding (view binding)
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize the database helper
        UserDBHelper userDBHelper = new UserDBHelper(this);

        // Set the login button on click listener
        binding.LoginButton.setOnClickListener(v -> {
            // Get the user's username and password
            String username = binding.UsernameEdit.getText().toString();
            String password = binding.PasswordEdit.getText().toString();

            // Authenticate the username and password
            User user =  userDBHelper.authenticate(username, password);

            if (user != null) {
                // If user is found then redirect user to main activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                LoginActivity.this.finish();
            } else {
                // If user not found tell user that the username/password wrong
                Toast.makeText(LoginActivity.this, "Username/Password salah", Toast.LENGTH_SHORT).show();
            }
        });

        // Set register link on click listener
        binding.RegisterLink.setOnClickListener(v -> {
            // Create an intent to redirect user into register activity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            // Start the register activity
            startActivity(intent);
            // Close the activity login
            LoginActivity.this.finish();
        });
    }
}