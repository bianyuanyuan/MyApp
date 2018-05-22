package com.myapp.Activity;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.myapp.Data.Student;
import com.myapp.atys.ShowStudentActivity;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.util.ArrayList;
import java.util.List;

import myapp.byy.com.myapp.R;

public class SeeStudent  extends ListActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener{

    private static final String TAG = "TestSQLite";
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private List<Long> list;


    private ImageView back;


    private LinearLayout layout;
    private DataDao dao;
    private Student student;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_student);
        Log.e(TAG, "onCreate");
        list = new ArrayList<Long>();
        student = new Student();
        dao = new DataDao(new DBOpenHelper(this));


        back = (ImageView) findViewById(R.id.back);


        layout = (LinearLayout) findViewById(R.id.showLiner);

        listView = getListView();

        // 为按键设置监听

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listView.setOnItemClickListener(this);

        listView.setOnCreateContextMenuListener(this);

    }

    // 调用load()方法将数据库中的所有记录显示在当前页面
    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    public void onClick(View v) {

    }

    // 点击一条记录是触发的事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        student = dao.getStudentFromView(view, id);
        Log.e(TAG, "student*****" + dao.getStudentFromView(view, id));
        Intent intent = new Intent();
        intent.putExtra("student", student);
        intent.setClass(this, ShowStudentActivity.class);
        this.startActivity(intent);

    }

    // 自定义一个加载数据库中的全部记录到当前页面的无参方法
    public void load() {
        DBOpenHelper studentDBHelper = new DBOpenHelper(
                SeeStudent.this);
        SQLiteDatabase database = studentDBHelper.getWritableDatabase();
        cursor = database.query(TableContanst.STUDENT_TABLE, null, null, null,
                null, null, TableContanst.StudentColumns.ID + " desc");
        startManagingCursor(cursor);
        adapter = new SimpleCursorAdapter(this, R.layout.student_list_item,
                cursor, new String[]{TableContanst.StudentColumns.ID,
                TableContanst.StudentColumns.NAME,
                TableContanst.StudentColumns.AGE,
                TableContanst.StudentColumns.SEX,
                TableContanst.StudentColumns.LIKES,
                TableContanst.StudentColumns.PHONE_NUMBER,
                TableContanst.StudentColumns.TRAIN_DATE}, new int[]{
                R.id.tv_stu_id, R.id.tv_stu_name, R.id.tv_stu_age,
                R.id.tv_stu_sex, R.id.tv_stu_likes, R.id.tv_stu_phone,
                R.id.tv_stu_traindate});
        listView.setAdapter(adapter);
    }

}
