package com.myapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myapp.Data.Coach;

import java.util.ArrayList;

import myapp.byy.com.myapp.R;

public class CoachAdapter extends ArrayAdapter<Coach> {

    private Context context;
    private ArrayList<Coach> allperson;


    public CoachAdapter(Context context, ArrayList<Coach> allperson) {
        super(context, R.layout.class_info, allperson);
        this.context = context;
        this.allperson = allperson;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            String coachName = allperson.get(position).getName();
            convertView = LayoutInflater.from(context).inflate(R.layout.coach_info, parent, false);


            TextView name = (TextView) convertView.findViewById(R.id.tv_co_info_name);
            name.setText(""+coachName);
        }
        return convertView;
    }
}

