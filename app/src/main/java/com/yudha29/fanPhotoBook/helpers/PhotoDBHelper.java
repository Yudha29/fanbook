package com.yudha29.fanPhotoBook.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.yudha29.fanPhotoBook.models.Photo;

import java.util.ArrayList;

public class PhotoDBHelper extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "beversev4";

    // Table name
    private final String TABLE_NAME = "photos";

    // Table columns
    private final String COLUMN_ID = "id";
    private final String COLUMN_ARTIST_ID = "artist_id";
    private final String COLUMN_URL = "url";

    public PhotoDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query to create artists table
        String createQuery = "CREATE TABLE " + TABLE_NAME +
                " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ARTIST_ID + " TEXT,"
                + COLUMN_URL + " TEXT)";

        db.execSQL(createQuery);
        db.execSQL("INSERT INTO " + TABLE_NAME + " (`artist_id`, `url`) VALUES ('2', 'https://cdn06.pramborsfm.com/storage/app/media/Prambors/Editorial/LISA%20BLACKPINK-20210910161532.jpg?tr=w-800')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (`artist_id`, `url`) VALUES ('2', 'https://asset.kompas.com/crops/PRcUU1eXOE3L1h3_66xXIjgcNjA=/0x0:0x0/750x500/data/photo/2021/07/08/60e6901a4992e.jpg')");
    }

    // Recreate table on table version upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(dropQuery);
        onCreate(db);
    }

    // Function to add the new photo
    public void add(Photo photo) {
        // Get writeable database
        SQLiteDatabase db = getWritableDatabase();

        // Instance the content values and put the user data
        ContentValues values = new ContentValues();
        values.put(COLUMN_ARTIST_ID, photo.getArtistId());
        values.put(COLUMN_URL, photo.getUrl());

        // Save the user data into database
        db.insert(TABLE_NAME, null, values);
    }

    // Function to get photo by artist id
    public ArrayList<Photo> getByArtistId(String id) {
        // Get readable database
        SQLiteDatabase db = getReadableDatabase();

        // set the where clause
        String selection = "artist_id = ?";
        String[] selectionArgs = new String[]{id};

        // execute the query to fetch photo by artist id
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null,null,null);

        // Loop the data and create the model instance
        ArrayList<Photo> photos = new ArrayList<Photo>() {};
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTIST_ID));
                String profileImage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL));

                // Create photo model instance from photo table data
                Photo photo = new Photo(name, profileImage);
                photo.setId(_id);

                // Add model into artists list
                photos.add(photo);
            } while (cursor.moveToNext());
        }

        // Return the generated model list
        return photos;
    }

    // Function to delete a data
    public void delete(String id) {
        // Get writeable database
        SQLiteDatabase db = getWritableDatabase();

        // Set the where clause for delete query
        String selection = "id = ?";
        String[] selectionArgs = new String[]{id};

        // Execute the delete statement
        db.delete(TABLE_NAME, selection, selectionArgs);
    }
}
