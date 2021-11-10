package com.yudha29.fanPhotoBook.adapters;

import android.content.Context;
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
import com.yudha29.fanPhotoBook.helpers.PhotoDBHelper;
import com.yudha29.fanPhotoBook.models.Photo;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private ArrayList<Photo> photos; // Photo list data
    private Context context; // Current activity

    public PhotoAdapter(ArrayList<Photo> photos, Context context) {
        this.context = context;
        this.photos = photos;
    }

    // Function to update the array data
    public void updateData(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoAdapter.ViewHolder(
                LayoutInflater.from(this.context)
                        .inflate(R.layout.photo_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        Picasso.get().load(photo.getUrl()).into(holder.image);

        // Set long on click listener
        holder.image.setOnLongClickListener(v -> {
            // Set the bottom sheet dialog
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

            TextView DeleteButton = bottomSheetDialog.findViewById(R.id.DeleteAction);

            if (DeleteButton != null) {
                // Set the delete button on click listener
                DeleteButton.setOnClickListener(view -> {
                    // remove the photo data
                    removePhoto(String.valueOf(photo.getId()), photo.getArtistId());
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
    public void removePhoto(String id, String artistId) {
        // Initialize the database helper
        PhotoDBHelper photoDBHelper = new PhotoDBHelper(context);

        // Delete the artist with specified id
        photoDBHelper.delete(id);
        // Update the recycler view data
        this.updateData(photoDBHelper.getByArtistId(artistId));
        // Tell the recycler view to update the UI
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;

        public ViewHolder(@NonNull View item) {
            super(item);

            // Set the image element
            image = item.findViewById(R.id.artist_image);
        }
    }
}
