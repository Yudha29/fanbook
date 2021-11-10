package com.yudha29.fanPhotoBook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.yudha29.fanPhotoBook.adapters.ArtistAdapter;
import com.yudha29.fanPhotoBook.databinding.ActivityMainBinding;
import com.yudha29.fanPhotoBook.helpers.ArtistDBHelper;
import com.yudha29.fanPhotoBook.models.Artist;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; // View binding object
    private ArrayList<Artist> artists; // Array to save artists from database
    private RecyclerView artistRV; // Artist recycler view adapter
    private ArtistAdapter artistAdapter; // Artist recycler view element
    private ArtistDBHelper artistDBHelper; // Database helper for artist table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout into binding (view binding)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database helper
        artistDBHelper = new ArtistDBHelper(this);

        // Get all artists data
        artists = artistDBHelper.getAll();

        // Connect the adapter with the artist data
        artistAdapter = new ArtistAdapter(artists, this);
        // Get the recycler view element
        artistRV = binding.artistsRv;
        // Set the recycler view layout, I choose the linear layout manager to render vertically
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        artistRV.setLayoutManager(linearLayoutManager);
        // Set the recyclerview adapter
        artistRV.setAdapter(artistAdapter);

        // Set the floating button on click listener
        binding.addFab.setOnClickListener(v -> {
            // Redirect user into add artist activity
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
    }

    // This function ensure the activity data refreshed
    @Override
    protected void onRestart() {
        super.onRestart();

        // Get the latest artists data
        artists = artistDBHelper.getAll();
        // Update the data
        artistAdapter.updateData(artists);
        artistAdapter.notifyDataSetChanged();
    }
}