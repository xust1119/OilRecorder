package com.tbyp.tbyp.oilrecorder.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OilDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final String CREATE_RECORD = "create table record ("
            + "date text primary key,"
            + "oil_number real,"
            + "cost real,"
            + "kilometer real)";

//    private static final String CREATE_TOTALINFO = "create table totalInfo ("
//            + "start_date text,"
//            + "end_date text,"
//            + "total_km text,"
//            + "total_oil_number text,"
//            + "total_cost text,"
//            + "current_oil_wera text)";

    public OilDataBaseHelper(Context context, String dataBaseName,
                             SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context, dataBaseName, cursorFactory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD);
//        db.execSQL(CREATE_TOTALINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
