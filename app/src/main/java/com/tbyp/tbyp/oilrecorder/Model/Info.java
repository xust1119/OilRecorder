package com.tbyp.tbyp.oilrecorder.Model;

import android.content.Context;

import com.tbyp.tbyp.oilrecorder.DB.OilRecorderDB;
import com.tbyp.tbyp.oilrecorder.Utils.Util;

import java.util.List;
import java.util.Map;

public class Info {
    private String mStartDate;
    private String mEndDate;
    private float mTotalOil;
    private float mTotalOilCost;
    private float mTotalKm;
    private float mOilWear;
    private float mCurOilWear;
    private float mAverageCost;

    private static Info mInfo;
    private static OilRecorderDB db;



    private List<OilRecord> records;

    private Info(String startDate, String endDate,
                float totalOil, float totalOilCost,
                float totalKm, float oilWear,
                float averageCost, float curOilWear){
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mTotalOil = totalOil;
        this.mTotalKm = totalKm;
        this.mTotalOilCost = totalOilCost;
        this.mOilWear = oilWear;
        this.mCurOilWear = curOilWear;
        this.mAverageCost = averageCost;
    }

    public static Info getInstance(Context context){
        if(mInfo == null){
            db = OilRecorderDB.getInstance(context);
            List<OilRecord> records = db.loadRecords(null);

            Map<String, String> infoMap = Util.getAllTotalInfo(records);
            float oilNumber = Float.parseFloat(infoMap.get("totalOilNumber"));
            float totalKm = Float.parseFloat(infoMap.get("totalKM"));
            float totalCost = Float.parseFloat(infoMap.get("totalCost"));

            mInfo = new Info(infoMap.get("startDate"),
                    infoMap.get("endDate"), oilNumber, totalCost, totalKm,
                    Util.getAverageOilWear(oilNumber, totalKm),
                    Util.getAverageCostPerKm(totalCost, totalKm),
                    Float.parseFloat(infoMap.get("curOilWear")));

            mInfo.setRecords(records);
        }

        return mInfo;
    }

    public void update(String start, String end, float addKm, float addCost, float addOil){
        if(start != null){
            this.setmStartDate(start);
        }

        if(end != null){
            this.setmEndDate(end);
        }

        if(Util.isBigerThan(addKm, 0.0f)){
            float temp = this.getmTotalKm();
            this.setmTotalKm(temp + addKm);
        }

        if(Util.isBigerThan(addCost, 0.0f)){
            float temp = this.getmTotalOilCost();
            this.setmTotalOilCost(temp + addCost);
        }

        if(Util.isBigerThan(addOil, 0.0f)){
            float temp = this.getmTotalOil();
            this.setmTotalOil(temp + addOil);
        }
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public float getmTotalOil() {
        return mTotalOil;
    }

    public void setmTotalOil(float mTotalOil) {
        this.mTotalOil = mTotalOil;
        updateOilWearAndCost(this.mTotalOil, getmTotalOilCost(), getmTotalKm());
    }

    public float getmTotalOilCost() {
        return mTotalOilCost;
    }

    public void setmTotalOilCost(float mTotalOilCost) {
        this.mTotalOilCost = mTotalOilCost;

        updateOilWearAndCost(getmTotalOil(), this.mTotalOilCost, getmTotalKm());
    }

    public float getmTotalKm() {
        return mTotalKm;
    }

    public void setmTotalKm(float mTotalKm) {
        this.mTotalKm = mTotalKm;

        updateOilWearAndCost(getmTotalOil(), getmTotalOilCost(), this.mTotalKm);
    }

    private void updateOilWearAndCost(float oilNumber, float oilCost, float totalKm){
        this.mOilWear = Util.getAverageOilWear(oilNumber, 0, totalKm);
        this.mTotalOilCost = Util.getAverageCostPerKm(oilCost, 0, totalKm);
    }

    public float getmCurOilWear() {
        return mCurOilWear;
    }

    public void setmCurOilWear(float mCurOilWear) {
        this.mCurOilWear = mCurOilWear;
    }

    public float getmOilWear() {
        return mOilWear;
    }

    public float getmAverageCost() {
        return mAverageCost;
    }

    public List<OilRecord> getRecords() {
        return records;
    }

    public void setRecords(List<OilRecord> records) {
        this.records = records;
    }

    public void addRecord(OilRecord r){
        if(!records.contains(r)){
            this.records.add(r);
        }
    }
}
