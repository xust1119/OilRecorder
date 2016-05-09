package com.tbyp.tbyp.oilrecorder.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.tbyp.tbyp.oilrecorder.Model.Info;
import com.tbyp.tbyp.oilrecorder.Model.RecordAdapter;
import com.tbyp.tbyp.oilrecorder.R;

public class DatasActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas);

        listView = (ListView)findViewById(R.id.dataListView);
        RecordAdapter adapter = new RecordAdapter(this, R.layout.record_item, Info.getInstance(this).getRecords());
        listView.setAdapter(adapter);
    }

}
