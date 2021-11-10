package com.yudha29.fanPhotoBook.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.yudha29.fanPhotoBook.R;
import com.yudha29.fanPhotoBook.activities.DetailActivity;
import com.yudha29.fanPhotoBook.databinding.BottomSheetDialogBinding;
import com.yudha29.fanPhotoBook.helpers.ArtistDBHelper;
import com.yudha29.fanPhotoBook.models.Artist;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private ArrayList<Artist> artists; // Artist list data
    private Context context; // Current activity

    public ArtistAdapter(ArrayList<Artist> artists, Context context) {
        this.context = context;
        this.artists = artists;
    }

    // Function to update the array data
    public void updateData(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistAdapter.ViewHolder(
                LayoutInflater.from(this.context)
                        .inflate(R.layout.artist_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.title.setText(artist.getName());
        Picasso.get().load(artist.getProfileImage()).into(holder.image);

        // Set the image on click listener
        holder.image.setOnClickListener(v -> {
            // Navigate into the detail activity when image clicked
            Intent intent = new Intent(context, DetailActivity.class);
            // Pass the artist id into detail activity
            intent.putExtra("id", String.valueOf(artist.getId()));
            context.startActivity(intent);
        });

        // Set long on click listener
        holder.image.setOnLongClickListener(v -> {
            // Set the bottom sheet dialog
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

            TextView DeleteButton = bottomSheetDialog.findViewById(R.id.DeleteAction);

            if (DeleteButton != null) {
                // Set the delete button on click listener
                DeleteButton.setOnClickListener(view -> {
                    // remove the artist data
                    removeArtist(String.valueOf(artist.getId()));
                    // hide the bottom sheet dialog
                    bottomSheetDialog.hide();
                });
            }

            // Show the dialog when the image on hold click
            bottomSheetDialog.show();
            return true;
        });
    }

    // Function to remove an artist
    public void removeArtist(String id) {
        // Initialize the database helper
        ArtistDBHelper artistDBHelper = new ArtistDBHelper(context);

        // Delete the artist with specified id
        artistDBHelper.delete(id);
        // Update the recycler view data
        this.updateData(artistDBHelper.getAll());
        // Tell the recycler view to update the UI
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;

        public ViewHolder(@NonNull View item) {
            super(item);

            // Set the title element
            title = item.findViewById(R.id.artist_title);
            // Set the artist image element
            image = item.findViewById(R.id.artist_image);
        }
    }
}
