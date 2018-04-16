package com.myapp.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myapp.Data.Coach;
import com.myapp.db.TableContanst;

import myapp.byy.com.myapp.R;

public class ShowCoachActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_info);
        Intent intent = getIntent();
        Coach coach = (Coach) intent.getSerializableExtra(TableContanst.COACH_TABLE);
        ((TextView) findViewById(R.id.tv_co_info_id)).setText(coach.getId() + "");
        ((TextView) findViewById(R.id.tv_co_info_name)).setText(coach.getName());
        ((TextView) findViewById(R.id.tv_co_info_age)).setText(coach.getAge() + "");
        ((TextView) findViewById(R.id.tv_co_info_sex)).setText(coach.getSex());
        ((TextView) findViewById(R.id.tv_co_info_phone)).setText(coach.getPhoneNumber());
        ((TextView) findViewById(R.id.tv_co_info_tyear)).setText(coach.getTeach_year() + "");
        ((TextView) findViewById(R.id.tv_co_info_charge)).setText(coach.getCharge() + "");
        ((TextView) findViewById(R.id.tv_co_info_tcourse)).setText(coach.getTeach_course());
    }

    public void goBack(View view) {
        finish();
    }
}