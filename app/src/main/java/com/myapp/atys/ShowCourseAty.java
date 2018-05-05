package com.myapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.myapp.Data.Course;
import myapp.byy.com.myapp.R;

public class ShowCourseAty extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_course_aty);
        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra("course");

        ((TextView) findViewById(R.id.tv_cs_info_name)).setText(course.getName());
        ((TextView) findViewById(R.id.tv_cs_info_times)).setText(course.getTimes() + "");
        ((TextView) findViewById(R.id.tv_cs_info_timesweek)).setText(course.getTimesweek() + "");
        ((TextView) findViewById(R.id.tv_cs_info_price)).setText(course.getPrice() + "");


    }

    public void goBack(View view) {
        finish();
    }
}
