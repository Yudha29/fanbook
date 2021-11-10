package com.yudha29.fanPhotoBook.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.yudha29.fanPhotoBook.models.Artist;

import java.util.ArrayList;

public class ArtistDBHelper extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "beversev2";

    // Table name
    private final String TABLE_NAME = "artists";

    // Table columns
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_PROFILE_IMAGE = "profile_image";
    private final String COLUMN_DEBUT_DATE = "debut_date";
    private final String COLUMN_DESCRIPTION = "description";

    public ArtistDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query to create artists table
        String createQuery = "CREATE TABLE " + TABLE_NAME +
                " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PROFILE_IMAGE + " TEXT,"
                + COLUMN_DEBUT_DATE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";

        db.execSQL(createQuery);
        db.execSQL("INSERT INTO " + TABLE_NAME + " (`name`, `profile_image`, `debut_date`, `description`) VALUES ('BTS', 'https://akcdn.detik.net.id/visual/2021/04/23/bts-resmi-terpilih-jadi-brand-ambassador-louis-vuitton_169.jpeg?w=650', '2013  ', 'BTS, also known as the Bangtan Boys, is a South Korean boy band that was formed in 2010 and debuted in 2013 under Big Hit Entertainment')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (`name`, `profile_image`, `debut_date`, `description`) VALUES ('Black Pink', 'https://media.suara.com/pictures/653x366/2020/06/01/61973-blackpink-soompi.jpg', '2016', 'Blackpink is a South Korean girl group formed by YG Entertainment, consisting of members Jisoo, Jennie, Rosé, and Lisa')");
    }

    // Recreate table on table version upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(dropQuery);
        onCreate(db);
    }

    // Function to add the new photo
    public void add(Artist artist) {
        // Get writeable database
        SQLiteDatabase db = getWritableDatabase();

        // Instance the content values and put the user data
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, artist.getName());
        values.put(COLUMN_PROFILE_IMAGE, artist.getProfileImage());
        values.put(COLUMN_DEBUT_DATE, artist.getDebutDate());
        values.put(COLUMN_DESCRIPTION, artist.getDescription());

        // Save the user data into database
        db.insert(TABLE_NAME, null, values);
    }

    // Function to get photo by artist
    public ArrayList<Artist> getAll() {
        // Get readable database
        SQLiteDatabase db = getReadableDatabase();

        // execute the query to fetch artists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // Loop the data and create the model instance
        ArrayList<Artist> artists = new ArrayList<Artist>() {};
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String profileImage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE));
                String debutDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEBUT_DATE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

                // Create photo artist instance from artist table data
                Artist artist = new Artist(name, profileImage, debutDate, description);
                artist.setId(id);

                // Add model into artists list
                artists.add(artist);
            } while (cursor.moveToNext());
        }

        // Return the generated model list
        return artists;
    }

    // Function to get a single artist data
    public Artist findById(String id) {
        // Get readable database
        SQLiteDatabase db = getReadableDatabase();

        // set the where clause
        String selection = "id = ?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        // Check is user data exists
        if  (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String profileImage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE));
            String debutDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEBUT_DATE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

            // Create artist model instance and return the model
            return new Artist(name, profileImage, debutDate, description);
        }

        // If data does not found in database return null
        return null;
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
