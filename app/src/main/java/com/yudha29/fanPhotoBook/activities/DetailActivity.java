package com.yudha29.fanPhotoBook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.yudha29.fanPhotoBook.adapters.PhotoAdapter;
import com.yudha29.fanPhotoBook.databinding.ActivityDetailBinding;
import com.yudha29.fanPhotoBook.helpers.ArtistDBHelper;
import com.yudha29.fanPhotoBook.helpers.PhotoDBHelper;
import com.yudha29.fanPhotoBook.models.Artist;
import com.yudha29.fanPhotoBook.models.Photo;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private String id; // Artist id

    private ActivityDetailBinding binding; // View binding object
    private ArrayList<Photo> photos; // Array to save photo from database
    private PhotoAdapter photoAdapter; // Photo recycler view adapter
    private RecyclerView photoRV; // Photo recycler view element
    private PhotoDBHelper photoDBHelper; // Database helper for photo table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout into binding (view binding)
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database helper
        photoDBHelper = new PhotoDBHelper(this);

        id = getIntent().getStringExtra("id"); // get artist id from extra

        // Render the artist data
        renderArtist(id);
        // Render all artist's photo
        renderPhoto(id);

        // Set the back button action
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        // Set the add button on click listener
        binding.AddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddPhotoActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    // Function to render the artist data into view
    private void renderArtist(String artistId) {
        ArtistDBHelper artistDBHelper = new ArtistDBHelper(this);
        Artist artist = artistDBHelper.findById(artistId); // find the artist data in database

        Picasso.get().load(artist.getProfileImage()).into(binding.ArtistImage); // Load artist image
        binding.ArtistName.setText(artist.getName()); // Set the artist name text view
        binding.AboutTextView.setText(artist.getDescription()); // Set the artist about text view
        String debutText = "Debut pada " + artist.getDebutDate();
        binding.DebutTextView.setText(debutText); // Set the artist debut date text view
    }

    // Function to render artist's photo
    private void renderPhoto(String artistId) {
        // Get artist's photo from database
        photos = photoDBHelper.getByArtistId(artistId);

        // Connect the adapter with the photo data
        photoAdapter = new PhotoAdapter(photos, this);
        // Get the recycler view element
        photoRV = binding.photoRv;
        // Set the recycler view layout, I choose the grid layout manager to render
        // horizontal and vertically
        photoRV.setLayoutManager(new GridLayoutManager(this, 2));
        // Set the recyclerview adapter
        photoRV.setAdapter(photoAdapter);
    }

    // This function ensure the activity data refreshed
    @Override
    protected void onRestart() {
        super.onRestart();

        // Get the latest artists photo
        photos = photoDBHelper.getByArtistId(id);
        // Update the data
        photoAdapter.updateData(photos);
        photoAdapter.notifyDataSetChanged();
    }
}