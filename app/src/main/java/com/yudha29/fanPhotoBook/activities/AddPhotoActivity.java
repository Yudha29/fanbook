package com.yudha29.fanPhotoBook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.yudha29.fanPhotoBook.databinding.ActivityAddPhotoBinding;
import com.yudha29.fanPhotoBook.helpers.PhotoDBHelper;
import com.yudha29.fanPhotoBook.models.Photo;

public class AddPhotoActivity extends AppCompatActivity {
    private ActivityAddPhotoBinding binding; // View binding object
    private PhotoDBHelper photoDBHelper; // Database helper for photo table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout into binding (view binding)
        binding = ActivityAddPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        photoDBHelper = new PhotoDBHelper(this);

        // Set the back button on click listener
        binding.toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        // Set the add button on click listener
        binding.AddButton.setOnClickListener(v -> {
            addPhoto();
        });
    }

    // Function to add new artist
    private void addPhoto() {
        // Fetch the artist id from intent data
        String artistId = getIntent().getStringExtra("id");

        // Fetch the data from user input
        String url = binding.urlEdit.getText().toString();

        // Check is all field empty
        if (artistId.equals("") || url.equals("")) {
            // If field empty then show the message into user
            Toast.makeText(this,"Mohon isi semua field", Toast.LENGTH_SHORT).show();
        } else {
            // If all field filled then create the Photo model
            Photo photo = new Photo(artistId, url);
            // Add the photo data into table
            photoDBHelper.add(photo);
            // finish the activity
            finish();
        }
    }

}