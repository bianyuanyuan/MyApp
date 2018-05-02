package com.myapp.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myapp.Data.Course;
import com.myapp.db.TableContanst;

import myapp.byy.com.myapp.R;

public class ShowCourseActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_info);
        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra(TableContanst.COURSE_TABLE);
        ((TextView) findViewById(R.id.tv_cs_info_id)).setText(course.getId() + "");
        ((TextView) findViewById(R.id.tv_cs_info_name)).setText(course.getName());
        ((TextView) findViewById(R.id.tv_cs_info_times)).setText(course.getTimes() + "");
        ((TextView) findViewById(R.id.tv_cs_info_timesweek)).setText(course.getTimesweek() + "");
        ((TextView) findViewById(R.id.tv_cs_info_price)).setText(course.getPrice() + "");
    }

    public void goBack(View view) {
        finish();
    }
}
