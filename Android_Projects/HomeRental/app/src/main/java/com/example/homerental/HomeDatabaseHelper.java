package com.example.homerental;
import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class HomeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "homerental"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    HomeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Users ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "IMAGE_RESOURCE_ID INTEGER,"
                + "USERNAME TEXT,"
                + "EMAIL TEXT,"
                + "PHONE INTEGER,"
                + "DOB TEXT,"
                + "PASSWORD TEXT);"
        );


        db.execSQL("CREATE TABLE Homes ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "IMAGE_RESOURCE_ID TEXT,"
                + "TITLE TEXT,"
                + "DESCRIPTION TEXT,"
                + "PRICE REAL,"
                + "LOCATION TEXT,"
                + "USERID INTEGER);" // posted by
        );

        db.execSQL("CREATE TABLE Reviews ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "RATE INTEGER,"
                + "COMMENT TEXT,"
                + "HOMEID INTEGER," // reviewed home
                + "USERID INTEGERR);" // reviewed by
        );

        db.execSQL("CREATE TABLE Booking ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CHECKIN NUMERIC,"
                + "CHECKOUT NUMERIC,"
                + "HOMEID INTEGER," // booked home
                + "USERID INTEGERR);" // booked by
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Homes");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Reviews");
        db.execSQL("DROP TABLE IF EXISTS Booking");

        onCreate(db);

    }

    private static void post(SQLiteDatabase db,
                                    int resourceId,
                                    String title,
                                    String description,
                                    float price,
                                    String location,
                                    int userid
                                    ) {
        ContentValues homeValues = new ContentValues();
        homeValues.put("IMAGE_RESOURCE_ID", resourceId);
        homeValues.put("NAME", title);
        homeValues.put("DESCRIPTION", description);
        homeValues.put("PRICE", price);
        homeValues.put("LOCATION", location);
        homeValues.put("USERID", userid);

        db.insert("Homes",null,homeValues);
    }

    public Cursor getAllHomes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Homes", null);
    }

    public static void register(SQLiteDatabase db,
                                 int resourceId,
                                 String username,
                                 String email,
                                 int phone,
                                 String dob,
                                 String password) {

        ContentValues userValues = new ContentValues();
        userValues.put("IMAGE_RESOURCE_ID", resourceId);
        userValues.put("USERNAME", username);
        userValues.put("EMAIL", email);
        userValues.put("PHONE", phone);
        userValues.put("DOB", dob);
        userValues.put("PASSWORD", password);

        db.insert("Users", null, userValues);
    }
    // Method to check user credentials
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});
        if (cursor != null) {
            boolean exists = (cursor.getCount() > 0);
            cursor.close();
            return exists;
        }
        return false;
    }

    private static void leaveReview(SQLiteDatabase db,
                                    int rate,
                                    String comment,
                                    int homeid,
                                    int userid
    ) {

        ContentValues reviewValues = new ContentValues();
        reviewValues.put("RATE", rate);
        reviewValues.put("COMMENT", comment);
        reviewValues.put("HOMEID", homeid);
        reviewValues.put("USERID", userid);

        db.insert("Reviews",null,reviewValues);
    }

    public static long book(SQLiteDatabase db, int checkin, int checkout, int homeid, int userid) {
        ContentValues bookingValues = new ContentValues();
        bookingValues.put("CHECKIN", checkin);
        bookingValues.put("CHECKOUT", checkout);
        bookingValues.put("HOMEID", homeid);
        bookingValues.put("USERID", userid);

        db.insert("Booking", null, bookingValues);
        return 0;
    }

    public void deleteHome(String homeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define where clause
        String selection = "_id = ?";
        String[] selectionArgs = { homeId };

        // Execute deletion
        int deletedRows = db.delete("Homes", selection, selectionArgs);

        // Optionally, you can log or handle the result
        if (deletedRows > 0) {
            // Deleted successfully
        } else {
            // Handle deletion failure if needed
        }

        db.close();
    }
}