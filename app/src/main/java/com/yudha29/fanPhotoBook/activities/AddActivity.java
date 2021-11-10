package com.yudha29.fanPhotoBook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.yudha29.fanPhotoBook.databinding.ActivityAddBinding;
import com.yudha29.fanPhotoBook.helpers.ArtistDBHelper;
import com.yudha29.fanPhotoBook.models.Artist;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding binding; // View binding object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout into binding (view binding)
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the back button on click listener
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        // Set the add button on click listener
        binding.AddButton.setOnClickListener(v -> addArtist());
    }

    // Function to add new artist
    private void addArtist() {
        // Database helper for artist table
        ArtistDBHelper artistDBHelper = new ArtistDBHelper(this);

        // Fetch the data from user input
        String name = binding.NameEdit.getText().toString(); // get name
        String photo = binding.PhotoEdit.getText().toString(); // get photo
        String debutDate = binding.DebutDateEdit.getText().toString(); // get debut date
        String description = binding.DecsriptionEdit.getText().toString(); // get description

        // Check is all field empty
        if (name.equals("") || photo.equals("") || debutDate.equals("") || description.equals("")) {
            // If field empty then show the message into user
            Toast.makeText(AddActivity.this, "Mohon isi semua field", Toast.LENGTH_SHORT).show();
        } else {
            // If all field filled then create the Artist model
            Artist artist = new Artist(name, photo, debutDate, description);
            // Add the artist data into table
            artistDBHelper.add(artist);
            // finish the activity
            finish();
        }
    }
}