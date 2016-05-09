package com.tbyp.tbyp.oilrecorder.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tbyp.tbyp.oilrecorder.Model.Info;
import com.tbyp.tbyp.oilrecorder.R;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ADD_DATA_CODE = 1;
    private EditText mStartDate,mEndDate,mTotalOil,mTotalOilCost,mTotalKM,mOilWear,mCurOilWear,mTotalAverageCost;
    private Button mMoreInfoBtn, mAddDataBtn;

    private void init(){
        mStartDate = (EditText)findViewById(R.id.startDateEditText);
        mEndDate = (EditText)findViewById(R.id.endDateEditText);
        mTotalOil = (EditText)findViewById(R.id.totalOilEditText);
        mTotalOilCost = (EditText)findViewById(R.id.totalOilCostEditText);
        mTotalKM = (EditText)findViewById(R.id.totalKMEditText);
        mOilWear = (EditText)findViewById(R.id.oilWearEditText);
        mCurOilWear = (EditText)findViewById(R.id.curOilWearEditText);
        mTotalAverageCost = (EditText)findViewById(R.id.averageCostEditText);


        mMoreInfoBtn = (Button)findViewById(R.id.moreInfoBtn);
        mAddDataBtn = (Button)findViewById(R.id.addDataBtn);
        mMoreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DatasActivity.class);
                startActivity(i);
            }
        });

        mAddDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddDataActivity.class);
                startActivityForResult(i, REQUEST_ADD_DATA_CODE);
            }
        });
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != RESULT_CANCELED){
            switch (requestCode){
                case REQUEST_ADD_DATA_CODE:
                    if(data.getBooleanExtra("isAddData", false)){
                        showInfo();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        showInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showInfo(){
        Info info = Info.getInstance(this);
        if(info != null){
            mStartDate.setText(info.getmStartDate());
            mEndDate.setText(info.getmEndDate());
            mTotalOil.setText(""+info.getmTotalOil());
            mTotalOilCost.setText(""+info.getmTotalOilCost());
            mTotalKM.setText(""+info.getmTotalKm());
            mOilWear.setText(""+info.getmOilWear());
            mCurOilWear.setText(""+info.getmCurOilWear());
            mTotalAverageCost.setText(""+info.getmAverageCost());
        }
        else{
            Toast.makeText(this, "Info is null~", Toast.LENGTH_SHORT).show();
        }
    }
}
