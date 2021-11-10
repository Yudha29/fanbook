package com.yudha29.fanPhotoBook.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.yudha29.fanPhotoBook.models.User;

public class UserDBHelper extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "beverse";

    // Table name
    private final String TABLE_NAME = "users";

    // Table columns
    private final String COLUMN_ID = "id";
    private final String COLUMN_USERNAME = "username";
    private final String COLUMN_FISRT_NAME = "first_name";
    private final String COLUMN_LAST_NAME = "last_name";
    private final String COLUMN_PASSWORD = "password";

    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query to create users table
        String createQuery = "CREATE TABLE " + TABLE_NAME +
                " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_FISRT_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT )";

        db.execSQL(createQuery);
    }

    // Recreate table on table version upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db.execSQL(dropQuery);
        onCreate(db);
    }

    // Function to register a new user
    public void register(User user) {
        // get the writeable database;
        SQLiteDatabase db = getWritableDatabase();

        // Instance the content values and put the user data
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_FISRT_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // Save the user data into database
        db.insert(TABLE_NAME, null, values);
    }

    // Function to authenticate user
    public User authenticate(String username, String password) {
        // Get readable database
        SQLiteDatabase db = getReadableDatabase();

        // Choose column to select
        String[] columns = new String[]{COLUMN_ID, COLUMN_FISRT_NAME, COLUMN_LAST_NAME, COLUMN_USERNAME};

        // Set the where clause
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = new String[]{username, password};

        // Fetch the user data
        Cursor cursor = db.query(
                TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // if the user data exists
        if  (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            // If exists create the user instance
            String _username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FISRT_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));

            return new User(firstName, lastName, _username, null);
        }

        // If user does not exists return false
        return null;
    }
}
