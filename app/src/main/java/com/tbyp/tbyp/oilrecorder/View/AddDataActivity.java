package com.tbyp.tbyp.oilrecorder.View;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tbyp.tbyp.oilrecorder.DB.OilRecorderDB;
import com.tbyp.tbyp.oilrecorder.Model.Info;
import com.tbyp.tbyp.oilrecorder.Model.OilRecord;
import com.tbyp.tbyp.oilrecorder.R;
import com.tbyp.tbyp.oilrecorder.Utils.Util;

public class AddDataActivity extends AppCompatActivity {

    private Button mSaveDataBtn;
    private EditText mTodayDateET, mCostET, mOilNumberET, mKmET,
            mLastTimeKM, mCurKM, mSubKm, mOilWear, mAverageCost;

    private boolean checkInput(){
        try {
            Float.parseFloat(mCostET.getText().toString());
            Float.parseFloat(mOilNumberET.getText().toString());
            Float.parseFloat(mKmET.getText().toString());
        }
        catch (NumberFormatException e){
            return false;
        }

        return true;
    }

    private void init(){
        mSaveDataBtn = (Button)findViewById(R.id.saveDataBtn);
        mTodayDateET = (EditText)findViewById(R.id.dataDateTextView);
        mCostET = (EditText)findViewById(R.id.currentCost);
        mOilNumberET = (EditText)findViewById(R.id.oilNumber);
        mKmET = (EditText)findViewById(R.id.curKm);
        mLastTimeKM = (EditText)findViewById(R.id.lastTimeKM);
        mCurKM = (EditText)findViewById(R.id.currentKM);
        mSubKm = (EditText)findViewById(R.id.driveKM);
        mOilWear = (EditText)findViewById(R.id.oilWear);
        mAverageCost = (EditText)findViewById(R.id.averageCost);

        mSaveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInput()) {
                    Toast.makeText(AddDataActivity.this, "检查输入！", Toast.LENGTH_SHORT).show();
                } else {
                    float lastTimeKm = Float.parseFloat(mLastTimeKM.getText().toString());
                    float currentKm = Float.parseFloat(mKmET.getText().toString());
                    float oilNumber = Float.parseFloat(mOilNumberET.getText().toString());
                    float oilCost = Float.parseFloat(mCostET.getText().toString());
                    String date = mTodayDateET.getText().toString();

                    float averageOilWear = Util.getAverageOilWear(oilNumber, lastTimeKm, currentKm);
                    float averageCost = Util.getAverageCostPerKm(oilCost, lastTimeKm, currentKm);
                    float driveKm = Util.getDriveKm(lastTimeKm, currentKm);

                    saveDataIntoDB(date, oilNumber, oilCost, currentKm);
                    clearData();

                    updateMoreInfo(currentKm, driveKm, averageOilWear, averageCost);

                    Info info = Info.getInstance(AddDataActivity.this);
                    info.update(null, date, driveKm, oilCost, oilNumber);

                    updateMainActivity(true);
                }
            }
        });

        mTodayDateET.setText(Util.getTodayDate());
        mLastTimeKM.setText(Float.toString(getLastTimeKm()));
    }

    private void clearData(){
        mTodayDateET.setText(Util.getTodayDate());
        mCostET.setText("");
        mOilNumberET.setText("");
        mKmET.setText("");
    }


    private float getLastTimeKm(){
        return OilRecorderDB.getInstance(this).getLastTimeKm();
    }

    private void saveDataIntoDB(String date, float oilNumber, float cost, float currentKm){
        OilRecord r = new OilRecord(oilNumber,cost,currentKm,date);
        Info.getInstance(this).addRecord(r);
        OilRecorderDB.getInstance(this).saveRecord(r);
        Toast.makeText(this, "添加数据成功!", Toast.LENGTH_SHORT).show();
    }

    private void updateMoreInfo(float curKm, float driveKm, float oilWear, float averageCost){
        mCurKM.setText(Float.toString(curKm));
        mSubKm.setText(Float.toString(driveKm));
        mOilWear.setText(Float.toString(oilWear));
        mAverageCost.setText(Float.toString(averageCost));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        init();
    }

    public void updateMainActivity(boolean isAdd){
        Intent i = new Intent();
        i.putExtra("isAdd", isAdd);
        setResult(RESULT_OK, i);
    }
}
