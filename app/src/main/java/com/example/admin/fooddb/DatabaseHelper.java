package com.example.admin.fooddb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.R.attr.id;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_NAME = "Foods";
    public static final String FOOD_NAME = "Name";
    public static final String FOOD_CALORIES = "Calories";
    public static final String FOOD_FAT = "Fat";
    public static final String FOOD_PROTEIN = "Protein";
    public static final String FOOD_IMAGE = "Picture";
    //put picture here
    public static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                FOOD_NAME + " TEXT PRIMARY KEY," +
                FOOD_CALORIES + " TEXT, " +
                FOOD_FAT + " TEXT," +
                FOOD_PROTEIN  + " TEXT," +
                FOOD_IMAGE + " BLOB" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveNewFoodEntry(foodEntry entry)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_NAME, entry.getName());
        contentValues.put(FOOD_CALORIES, entry.getCalories());
        contentValues.put(FOOD_FAT, entry.getFat());
        contentValues.put(FOOD_PROTEIN, entry.getProtein());
        contentValues.put(FOOD_IMAGE, entry.getPicture());
        long saved = database.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "saveNewFoodEntry: ");
        return saved;
    }

    public boolean checkIfEntryExits(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + FOOD_NAME + " = '" + name + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean updateEntry(foodEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FOOD_NAME, entry.getName());
        cv.put(FOOD_CALORIES, entry.getCalories());
        cv.put(FOOD_FAT, entry.getFat());
        cv.put(FOOD_PROTEIN, entry.getProtein());
        cv.put(FOOD_IMAGE, entry.getPicture());
        db.update(TABLE_NAME, cv, FOOD_NAME + " = '" + entry.getName() + "'", null);
        return true;
    }

    public Integer deleteEntry(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, FOOD_NAME + " = '" + name + "'" , null);
    }

    public ArrayList<foodEntry> getEntries(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<foodEntry> entryList = new ArrayList<>();

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                foodEntry entry = new foodEntry(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
                entryList.add(entry);
            }while(cursor.moveToNext());
        }
        return entryList;
    }

    public foodEntry getEntry(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //THIS IS A POTENTIAL LEAK
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + FOOD_NAME + " = '" + name + "'";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return new foodEntry(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getBlob(4));
    }
}