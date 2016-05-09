
package com.tbyp.tbyp.oilrecorder.Utils;

import com.tbyp.tbyp.oilrecorder.Model.OilRecord;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    private static DecimalFormat df = new DecimalFormat("#.00");

    public static boolean isBigerThan(float f1, float f2){
        return Float.floatToIntBits(f1) > Float.floatToIntBits(f2);
    }

    public static  boolean isEqule(float f1, float f2){
        return Float.floatToIntBits(f1) == Float.floatToIntBits(f2);
    }

    public static float getAverageOilWear(float oilNum, float driveKm){
        if(isEqule(driveKm, 0f)){
            return 0;
        }
        return Float.parseFloat(df.format((oilNum / (driveKm / 100))));
    }

    public static float getAverageOilWear(float oilNum, float lastTimeKm, float currentKm){
        return getAverageOilWear(oilNum, getDriveKm(lastTimeKm, currentKm));
    }

    public static  float getAverageCostPerKm(float cost, float driveKm){
        if(isEqule(driveKm, 0f)){
            return 0;
        }
        return Float.parseFloat(df.format((cost) / driveKm));
    }

    public static  float getAverageCostPerKm(float cost, float lastTimeKm, float currentKm){
        return getAverageCostPerKm(cost, getDriveKm(lastTimeKm, currentKm));
    }

    public static float getDriveKm(float lastTimeKm, float currentKm){
        return Math.abs(lastTimeKm - currentKm);
    }

    public static float getLastTimeAverageOilWear(List<OilRecord> records){
        if(records.size() <= 1){
            return 0;
        }

        Collections.sort(records);
        OilRecord lastRecord = records.get(records.size() - 1);
        OilRecord lastRecord2 = records.get(records.size() - 2);

        return getAverageOilWear(lastRecord.getNumberOfOil(),
                lastRecord2.getKilometer(),
                lastRecord.getKilometer());
    }

    public static String getTodayDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(c.getTime());
    }

    public static Map<String, String> getAllTotalInfo(List<OilRecord> records){
        float totalOilNumber = 0f;
        float totalKM = 0f;
        float totalCost = 0f;
        float lastTimeOilWear = 0f;
        String startDate = "";
        String endDate = "";
        HashMap<String, String> infos = new HashMap<String, String>();


        if(records.size() > 0){
            Collections.sort(records);

            OilRecord maxKMRecord = Collections.max(records), minKMRecord = Collections.min(records);
            totalKM = maxKMRecord.getKilometer() - minKMRecord.getKilometer();
            endDate = maxKMRecord.getDate();
            startDate = minKMRecord.getDate();

            for(OilRecord record : records){
                totalOilNumber += record.getNumberOfOil();
                totalCost += record.getCost();
            }

            lastTimeOilWear = getLastTimeAverageOilWear(records);
        }

        infos.put("startDate", startDate);
        infos.put("endDate", endDate);
        infos.put("totalCost", Float.toString(totalCost));
        infos.put("totalKM", Float.toString(totalKM));
        infos.put("totalOilNumber", Float.toString(totalOilNumber));
        infos.put("curOilWear", Float.toString(lastTimeOilWear));
        return infos;
    }


}
