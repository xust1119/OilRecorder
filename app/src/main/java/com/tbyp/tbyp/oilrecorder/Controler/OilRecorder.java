package com.tbyp.tbyp.oilrecorder.Controler;

import android.content.Context;

import com.tbyp.tbyp.oilrecorder.DB.OilRecorderDB;
import com.tbyp.tbyp.oilrecorder.Model.OilRecord;

public class OilRecorder {
    public static OilRecorder recorder;
    private OilRecorderDB db;

    private OilRecorder(Context context){
        db = OilRecorderDB.getInstance(context);
    }

    public static OilRecorder getInstance(Context context){
        if(recorder == null){
            recorder = new OilRecorder(context);
        }
        return recorder;
    }

    public void addRecord(OilRecord record){
        db.saveRecord(record);
    }

}
