package com.example.david.david_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabase extends SQLiteOpenHelper {

    private static TodoDatabase instance = null;

    private static final String TAG ="TodoDatabase";
    private static final String TABLE_NAME ="todos";
    private static final String COL1 ="_id";
    private static final String COL2 ="title";
    private static final String COL3 ="completed";

    private TodoDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " BIT);";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insert(String Item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, Item);
        contentValues.put(COL3, 0);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            System.out.println("hieeeerr");
            return false;
        }
        else {
            System.out.println("daar");
            return true;
        }


    }

    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context);
        }
        return instance;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String all = "SELECT * FROM " + TABLE_NAME;
        Cursor entries = db.rawQuery(all, null);
        return entries;
    }
    public Cursor get_id (String selected){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " +  TABLE_NAME + " WHERE " + COL2 + " = '" + selected + "';";
        System.out.println(query);


        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void check(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = " + "(CASE " + COL3 + " WHEN 1 THEN 0 ELSE 1 END) WHERE " + COL1 + " = '" + id + "';";
        db.execSQL(query);
    }

    public void deleteItem(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" +  id + "';";
        System.out.println(query);
        db.execSQL(query);
    }
}