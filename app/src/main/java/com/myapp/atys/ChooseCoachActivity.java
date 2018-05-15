package com.myapp.atys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.Coach;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapp.byy.com.myapp.R;

public class ChooseCoachActivity extends BaseActivity{
    private Button choose;
    private Button choose2;
    private DBOpenHelper helper;
    private AlertDialog.Builder dialog = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_coach);

        //创建或打开数据库
        helper = new DBOpenHelper(this, "manager.db", null, 1);


        Intent intent = getIntent();
        choose = findViewById(R.id.choose);
        choose2 = findViewById(R.id.choose2);
        final Coach coach = (Coach) intent.getSerializableExtra("coach");
      //  final String coachid = intent.getStringExtra("coachid");

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
                //提示对话框
                dialog = new AlertDialog.Builder(ChooseCoachActivity.this);
                dialog.setTitle("温馨提示");
                dialog.setMessage("是否确定要预约2:00-4:00?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.execSQL("insert into select_table (userid,coachid, t1,t2) " +
                                        "values(?, ?, ?, ?)",
                                new String[]{"", String.valueOf(coach.getId()), "2:00-4:00", null});
                        db.close();
                        Toast.makeText(ChooseCoachActivity.this, "预约成功", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();

                //   Intent i=new Intent(ChooseCoachActivity.this,WelcomeAcitvity.class);//////////////////
                //   startActivity(i);

            }
        });

        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //提示对话框
                dialog = new AlertDialog.Builder(ChooseCoachActivity.this);
                dialog.setTitle("温馨提示");
                dialog.setMessage("是否确定要预约4:00-6:00?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.execSQL("insert into select_table (userid,coachid, t1,t2) " +
                                        "values(?, ?, ?, ?)",
                                new String[]{"", String.valueOf(coach.getId()), null, "4:00-6:00"});
                        db.close();
                        Toast.makeText(ChooseCoachActivity.this, "预约成功", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
                //   Intent i=new Intent(ChooseCoachActivity.this,WelcomeAcitvity.class);//////////////////
                //   startActivity(i);


            }
        });


    }

}