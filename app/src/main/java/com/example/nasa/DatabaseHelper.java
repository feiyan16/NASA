package com.example.nasa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;

// CRUD -> Create, Read, Update, and Delete
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "database_name";

    // Table Names
    private static final String DB_TABLE = "table_image";

    // column names
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "image_title";
    private static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            "_id integer primary key autoincrement, " +
            KEY_TITLE + " TEXT, " +
            KEY_IMAGE + " BLOB);";


    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create table when database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    // drops table if a upgrade occurs
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }

    // drops table if a downgrade occurs
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // function to return the rows in table
    public ArrayList<Nasa> readEntries() {

        // arraylist to store each row
        ArrayList<Nasa> NASA = new ArrayList<>();

        // assign database to be this database
        SQLiteDatabase database = this.getWritableDatabase();

        // sql query statement to select all rows
        final String selectQuery = "SELECT  * FROM " + DB_TABLE;

        // cursor to select all queries
        Cursor cursor = database.rawQuery(selectQuery, null);

        // loop through all queries and store them in arraylist
        while (cursor.moveToNext()) {
            Nasa nasa = new Nasa();
            nasa.id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            nasa.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
            nasa.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_IMAGE)));
            NASA.add(nasa);
        }

        return NASA;
    }

    // function to add an entry into the database
    public void addEntry(Nasa nasa) throws SQLiteException {

        // assign database to be this database
        SQLiteDatabase database = this.getWritableDatabase();

        // create content value object to put values into
        ContentValues cv = new  ContentValues();
        cv.put(KEY_TITLE, nasa.getTitle());
        cv.put(KEY_IMAGE, nasa.getImage());

        // insert values into the database and assign id
        long rowID = database.insert(DB_TABLE, null, cv);
        nasa.id = (int) rowID;
    }

    // to update an entry
    public void updateEntry(Nasa nasa) {

        // array to hold arguments for update query
        String[] id = new String[1];

        // store the id of the drawing to be update in to the argument array
        id[0] = Integer.toString(nasa.id);

        // assign database to be this database
        SQLiteDatabase database = this.getWritableDatabase();

        // create the updated values
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, nasa.getTitle());
        cv.put(KEY_IMAGE, nasa.getImage());

        // update the database query with the updated values
        database.update(DB_TABLE, cv, "_id = ?", id);
//        database.delete(DB_TABLE, "_id = ?", id);
    }
}
