package com.tbyp.tbyp.oilrecorder.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tbyp.tbyp.oilrecorder.Model.Info;
import com.tbyp.tbyp.oilrecorder.Model.OilRecord;

import java.util.ArrayList;
import java.util.List;

public class OilRecorderDB {
    public static final String NAME = "oil_recorder";
    public static final int VERSION = 1;
    public static final String RECORD_TABLE = "record";

    private static volatile OilRecorderDB oilRecorderDB;

    private SQLiteDatabase db;

    private OilRecorderDB(Context context){
        OilDataBaseHelper helper = new OilDataBaseHelper(context, NAME, null, VERSION);
        db = helper.getWritableDatabase();
    }

    public static OilRecorderDB getInstance(Context context){
        if(oilRecorderDB == null){
            synchronized (OilRecorderDB.class){
                if(oilRecorderDB == null){
                    oilRecorderDB = new OilRecorderDB(context);
                }
            }
        }

        return oilRecorderDB;
    }

    public void saveRecord(OilRecord record){
        ContentValues values = new ContentValues();
        values.put("date", record.getDate());
        values.put("cost", record.getCost());
        values.put("oil_number",record.getNumberOfOil());
        values.put("kilometer", record.getKilometer());

        db.insert(RECORD_TABLE, null, values);
    }

    public float getLastTimeKm(){
        Cursor c = db.query(RECORD_TABLE, null, null, null, null, null, "date DESC");
        if(c.moveToFirst()){
            return c.getFloat(c.getColumnIndex("kilometer"));
        }
        return 0f;
    }


    public List<OilRecord> loadRecords(String nameOfOrder){
        ArrayList<OilRecord> records = new ArrayList<OilRecord>();
        Cursor cursor = db.query(RECORD_TABLE, null, null, null, null, null, nameOfOrder);

        if(cursor.moveToFirst()){
            do{
                String date = cursor.getString(cursor.getColumnIndex("date"));
                float cost = cursor.getFloat(cursor.getColumnIndex("cost"));
                float oilNumber = cursor.getFloat(cursor.getColumnIndex("oil_number"));
                float kilometer = cursor.getFloat(cursor.getColumnIndex("kilometer"));

                records.add(new OilRecord(oilNumber,cost,kilometer,date));

            }while(cursor.moveToNext());
        }

        return records;
    }
}
