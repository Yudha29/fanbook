package com.yudha29.fanPhotoBook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yudha29.fanPhotoBook.databinding.ActivityRegisterBinding;
import com.yudha29.fanPhotoBook.helpers.UserDBHelper;
import com.yudha29.fanPhotoBook.models.User;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding; // View binding object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout into binding (view binding)
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize the database helper
        UserDBHelper userDBHelper = new UserDBHelper(this);

        // Set login link on click listener
        binding.LoginLink.setOnClickListener(v -> {
            // Create an intent to redirect user into login activity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            // Start the login activity
            startActivity(intent);
            // Close the register login
            RegisterActivity.this.finish();
        });

        // Set the login button on click listener
        binding.RegisterButton.setOnClickListener(v -> {
            // Get the user's firstname, lastname, username, and password
            String firstName = binding.FirstNameEdit.getText().toString();
            String lastName = binding.LastNameEdit.getText().toString();
            String username = binding.UsernameEdit.getText().toString();
            String password = binding.PasswordEdit.getText().toString();

            // Create the user model
            User user = new User(firstName, lastName, username, password);
            // Save the user model into database
            userDBHelper.register(user);

            // Redirect user to main activity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            RegisterActivity.this.finish();
        });
    }
}