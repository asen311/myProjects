package com.cdkeyesdwe.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asen on 11/10/2016.
 */
public class DataBaseManagerLoad extends SQLiteOpenHelper {

    private static final String DATABASE=  "DataBaseForPeople";
    private static final String TABLE_LOAD_RECORDS=  "Records";
    private static final int DATABASE_VERSION=1;

    private static final String KEY_ID ="id";
    private static final String KEY_LEVEL = "level";


    public DataBaseManagerLoad(Context context){

        super(context,DATABASE,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable= "Create table " +TABLE_LOAD_RECORDS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"+KEY_LEVEL+" INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_LOAD_RECORDS);
        onCreate(sqLiteDatabase);
    }

    public void onSaveNewLoadInformation(int level){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_LOAD_RECORDS + " Where id=1");
        ContentValues values = new ContentValues();
        values.put(KEY_ID,1);
        values.put(KEY_LEVEL,level);

        db.insert(TABLE_LOAD_RECORDS,null,values);

        db.close();
    }


    public int getFinalSaveRecord(){
        SQLiteDatabase db = this.getWritableDatabase();
int level=0;
        String query = "Select * from " + TABLE_LOAD_RECORDS;

        Cursor cursor = db.rawQuery(query,null);


         if( cursor.moveToFirst() && cursor!=null) {
             level = Integer.parseInt(cursor.getString(1).toString());
         }else{
             cursor.close();
             return 0;
         }

        cursor.close();

       db.close();

        return level;
    }
}
