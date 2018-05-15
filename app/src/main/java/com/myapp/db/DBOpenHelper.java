package com.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import myapp.byy.com.myapp.R;

/**
 * Created by 540 on 2018/4/8.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBOpenHelper";
    public static final String DB_NAME = "manager.db";
    public static final int VERSION = 1;    //构造方法
    ContentValues values = new ContentValues();

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context) {

        this(context, DB_NAME, null, VERSION);
    }


    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "DBOpenHelper onCreate");
        //创建学员表
        db.execSQL("create table if not exists " + TableContanst.STUDENT_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "name varchar(20)," +
                "age Integer, " +
                "sex varchar(2)," +
                "likes varchar(60)," +
                " phone_number varchar(11)," +
                "train_date date, " +
                "modify_time DATETIME" +
                ")");
        values.put("name", "王纷纷");
        values.put("age", 15);
        values.put("sex", "女");
        values.put("likes", "唱歌");
        db.insert("student", null, values);//插入第一条数据
        values.clear();
        //创建教练表
        db.execSQL("create table if not exists " + TableContanst.COACH_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "name varchar(20)," +
                "age Integer, " +
                "sex varchar(2), " +
                "phone_number varchar(11)," +
                "teach_year Integer," +
                "charge Integer," +
                "teach_course varchar(20)," +
                "time_two varchar(10)," +
                "time_four varchar(10)" +
                ")");

        values.put("name", "王伟");
        values.put("age", 30);
        values.put("sex", "男");
        values.put("phone_number", "13012345678");
        values.put("teach_year", 2);
        values.put("charge", 220);
        values.put("teach_course", "游泳");
        values.put("time_two", "2:00-4:00");
        values.put("time_four", "4:00-6:00");

        db.insert("coach", null, values);//插入第一条数据
        values.clear();

        values.put("name", "李婷");
        values.put("age", 25);
        values.put("sex", "女");
        values.put("phone_number", "13011111111");
        values.put("teach_year", 1);
        values.put("charge", 200);
        values.put("teach_course", "游泳");
        values.put("time_two", "2:00-4:00");
        values.put("time_four", "4:00-6:00");
        db.insert("coach", null, values);//插入第二条数据
        values.clear();

        //用户表

        db.execSQL("create table if not exists " + TableContanst.USER +
                "(_id Integer primary key AUTOINCREMENT," +
                "nickname varchar(20)," +
                "account varchar(20), " +
                "password varchar(20)," +
                "isVisitor boolean" +
                ")");
       db.execSQL("insert into user (nickname,account, password, isVisitor) values(?, ?, ?, ?)",
                new String[]{"文文", "13012341234", "123", "0"});


        //选课表
        db.execSQL("create table if not exists " + TableContanst.SELECT_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "userid Integer, " +
                "coachid Integer, " +
                "t1 varchar(20)," +
                "t2 varchar(20)," +
                " FOREIGN KEY (userid ) REFERENCES user(_id)," +
                " FOREIGN KEY (coachid ) REFERENCES coach(_id)" +
                ")");


        //创建场地表
        db.execSQL("create table if not exists " + TableContanst.CLASS_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "name varchar(20), " +
                "position varchar(40)," +
                "contain Integer" +
                ")");

        //创建课程
        db.execSQL("create table if not exists " + TableContanst.COURSE_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "name varchar(20)," +
                "times Integer," +
                "timesweek Integer," +
                "price Integer" +
                ")");

        //创建时间表
        db.execSQL("create table if not exists " + TableContanst.TIMETABLE_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "periods varchar(20)," +
                "week varchar(20)," +
                "time Integer," +
                "t_start varchar(10)," +
                "t_end varchar(10)" +
                ")");

        //创建教练时间分配表
        db.execSQL("create table if not exists " + TableContanst.COACH_TIME_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "periods varchar(20)," +
                "coach_id Integer" +
                ")");

        //创建场地时间分配表
        db.execSQL("create table if not exists " + TableContanst.CLASS_TIME_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "periods varchar(20)," +
                "class_id Integer" +
                ")");

        //创建场地课程表
        db.execSQL("create table if not exists " + TableContanst.CLASS_COURSE_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "class_id Integer," +
                "course_id Integer" +
                ")");

        //创建学员课程表
        db.execSQL("create table if not exists " + TableContanst.STUDENT_COURSE_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "coach_name varchar(20)," +
                "course_name varchar(20)" +
                ")");
    }


    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "onUpgrade");
        db.execSQL("drop table if exists TableContanst.STUDENT_TABLE");
        db.execSQL("drop table if exists TableContanst.COACH_TABLE");
        db.execSQL("drop table if exists TableContanst.CLASS_TABLE ");
        db.execSQL("drop table if exists TableContanst.COURSE_TABLE");
        db.execSQL("drop table if exists TableContanst.TIMETABLE_TABLE ");
        db.execSQL("drop table if exists TableContanst.COACH_TIME_TABLE ");
        db.execSQL("drop table if exists TableContanst.CLASS_TIME_TABLE");
        db.execSQL("drop table if exists TableContanst.CLASS_COURSE_TABLE ");
        db.execSQL("drop table if exists TableContanst.STUDENT_COURSE_TABLE ");
        db.execSQL("drop table if exists TableContanst.SELECT_TABLE ");
        db.execSQL("drop table if exists TableContanst.USER ");
        onCreate(db);
    }
}
