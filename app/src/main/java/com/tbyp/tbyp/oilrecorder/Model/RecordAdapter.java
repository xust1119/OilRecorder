package com.tbyp.tbyp.oilrecorder.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tbyp.tbyp.oilrecorder.R;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<OilRecord> {
    private int resourceId;
    public RecordAdapter(Context context, int resource, List<OilRecord> records) {
        super(context, resource, records);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OilRecord r = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            ViewHolder vh = new ViewHolder();

            vh.date = (TextView)convertView.findViewById(R.id.recordDate);
            vh.cost = (TextView)convertView.findViewById(R.id.recordCost);
            vh.oil = (TextView)convertView.findViewById(R.id.recordOil);

            convertView.setTag(vh);
        }

        ViewHolder vh = (ViewHolder)convertView.getTag();
        vh.date.setText(r.getDate());
        vh.cost.setText(""+r.getCost());
        vh.oil.setText(""+r.getNumberOfOil());

        return convertView;
    }

    class ViewHolder{
        TextView date;
        TextView cost;
        TextView oil;
    }
}
