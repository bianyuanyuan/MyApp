package com.myapp.atys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.Data.Coach;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapp.byy.com.myapp.R;

public class ChooseCoachActivity extends BaseActivity {
    private Button choose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_coach);
        Intent intent = getIntent();

        choose = findViewById(R.id.choose);
        Coach coach = (Coach) intent.getSerializableExtra("coach");
        ((TextView) findViewById(R.id.tv_co_info_name)).setText(coach.getName());
        ((TextView) findViewById(R.id.tv_co_info_age)).setText(coach.getAge() + "");
        ((TextView) findViewById(R.id.tv_co_info_sex)).setText(coach.getSex());
        ((TextView) findViewById(R.id.tv_co_info_phone)).setText(coach.getPhoneNumber());
        ((TextView) findViewById(R.id.tv_co_info_tyear)).setText(coach.getTeach_year() + "");
        ((TextView) findViewById(R.id.tv_co_info_charge)).setText(coach.getCharge() + "");
        ((TextView) findViewById(R.id.tv_co_info_tcourse)).setText(coach.getTeach_course());

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChooseCoachActivity.this,WelcomeAcitvity.class);//////////////////
                startActivity(i);
            }
        });

    }

}