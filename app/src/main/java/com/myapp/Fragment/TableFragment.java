package com.myapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Test.Course;
import com.myapp.Test.DatabaseHelper;

import java.util.ArrayList;

import myapp.byy.com.myapp.R;

@SuppressLint("NewApi")
public class TableFragment extends Fragment implements View.OnClickListener {
    //星期几
    private RelativeLayout day;

    //SQLite Helper类
    private DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "database.db", null, 1);

    int currentCoursesNumber = 0;
    int maxCoursesNumber = 0;


    public TableFragment() {
    }

    public static TableFragment newInstance() {
        TableFragment fragment = new TableFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        //从数据库读取数据
     //   loadData();

        return view;
    }


    //从数据库加载数据
    private void loadData() {
        ArrayList<Course> courseList = new ArrayList<>(); //课程列表
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from courses", null);
        if (cursor.moveToFirst()) {
            do {
                courseList.add(new Course(
                        cursor.getString(cursor.getColumnIndex("course_name")),
                        cursor.getString(cursor.getColumnIndex("teacher")),
                        cursor.getString(cursor.getColumnIndex("class_room")),
                        cursor.getInt(cursor.getColumnIndex("day")),
                        cursor.getInt(cursor.getColumnIndex("class_start")),
                        cursor.getInt(cursor.getColumnIndex("class_end"))));
            } while (cursor.moveToNext());
        }
        cursor.close();

        //使用从数据库读取出来的课程信息来加载课程表视图
        for (Course course : courseList) {
            createLeftView(course);
            createCourseView(course);
        }
    }

    //保存数据到数据库
    private void saveData(Course course) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("insert into courses(course_name, teacher, class_room, day, class_start, class_end) " +
                        "values(?, ?, ?, ?, ?, ?)",
                new String[]{course.getCourseName(),
                        course.getTeacher(),
                        course.getClassRoom(),
                        course.getDay() + "", course.getStart() + "", course.getEnd() + ""});
    }

    //创建课程节数视图
    private void createLeftView(Course course) {
        int len = course.getEnd();
        if (len > maxCoursesNumber) {
            for (int i = 0; i < len - maxCoursesNumber; i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.left_view, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, 180);
                view.setLayoutParams(params);

                TextView text = view.findViewById(R.id.class_number_text);
                text.setText(String.valueOf(++currentCoursesNumber));

                LinearLayout leftViewLayout = getActivity().findViewById(R.id.left_view_layout);
                leftViewLayout.addView(view);
            }
        }
        maxCoursesNumber = len;
    }

    //创建课程视图
    private void createCourseView(final Course course) {
        int height = 180;
        int integer = course.getDay();
        if ((integer < 1 || integer > 7) || course.getStart() > course.getEnd())
            Toast.makeText(getActivity(), "星期几没写对,或课程结束时间比开始时间早", Toast.LENGTH_LONG).show();
        else {
            switch (integer) {
                case 1:
                    day = getActivity().findViewById(R.id.monday);
                    break;
                case 2:
                    day = getActivity().findViewById(R.id.tuesday);
                    break;
                case 3:
                    day = getActivity().findViewById(R.id.wednesday);
                    break;
                case 4:
                    day = getActivity().findViewById(R.id.thursday);
                    break;
                case 5:
                    day = getActivity().findViewById(R.id.friday);
                    break;
                case 6:
                    day = getActivity().findViewById(R.id.saturday);
                    break;
                case 7:
                    day = getActivity().findViewById(R.id.weekday);
                    break;
            }
            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.course_card, null); //加载单个课程布局
            view.setY(height * (course.getStart() - 1)); //设置开始高度,即第几节课开始
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, (course.getEnd() - course.getStart() + 1) * height - 8); //设置布局高度,即跨多少节课
            view.setLayoutParams(params);
            TextView text = view.findViewById(R.id.text_view);
            text.setText(course.getCourseName() + "\n" + course.getTeacher() + "\n" + course.getClassRoom()); //显示课程名
            day.addView(view);
            //长按删除课程
          /*  view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    view.setVisibility(View.GONE);//先隐藏
                    day.removeView(view);//再移除课程视图
                    SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                    sqLiteDatabase.execSQL("delete from courses where course_name = ?", new String[]{course.getCourseName()});
                    return true;
                }
            });*/
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0 && data != null) {
            Course course = (Course) data.getSerializableExtra("course");
            //创建课程表左边视图(节数)
            createLeftView(course);
            //创建课程表视图
            createCourseView(course);
            //存储数据到数据库
            saveData(course);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_courses:
          /*      Intent intent = new Intent(getActivity(), AddCourseAty.class);
                startActivityForResult(intent, 0);*/
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
    }
}

