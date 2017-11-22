package com.example.david.david_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dedvg on 20-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    private static final String TAG ="TodoDatabase";
    private static TodoDatabase instance = null;
    private static final String TABLE_NAME ="todos";
    private static final String COL1 ="_id";
    private static final String COL2 ="title";
    private static final String COL3 ="completed";


    private TodoDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + COL2 + " TEXT," + COL3 + " BIT);";

        db.execSQL(create_table);
    }
    public boolean add_data(String Item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, Item);

        System.out.println( TAG +  " adding " + Item + " to " + TABLE_NAME);

        long Result = db.insert(TABLE_NAME, null, contentValues);
        // Result is -1 if inserted incorrect
        if (Result == -1){
            System.out.println("hieeeerr");
            return false;
        }
        else {
            System.out.println("daaar");

            return true;
        }
    }
    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context);
        }
        return instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor get_id (String selected){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " +  TABLE_NAME + " WHERE " + COL2 + " = '" + selected + "';";
        System.out.println(query);


        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void deleteItem(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" +  id + "' AND " + COL2 + " = '" + name + "'; ";
        System.out.println(query);
        db.execSQL(query);
    }

}
