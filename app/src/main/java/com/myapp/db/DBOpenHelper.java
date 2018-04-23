package com.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 540 on 2018/4/8.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBOpenHelper";
    public static final String DB_NAME = "manager.db";
    public static final int VERSION = 1;    //构造方法

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

        //创建教练表
        db.execSQL("create table if not exists " + TableContanst.COACH_TABLE +
                "(_id Integer primary key AUTOINCREMENT," +
                "name varchar(20)," +
                "age Integer, " +
                "sex varchar(2), " +
                "phone_number varchar(11)," +
                "teach_year Integer," +
                "charge Integer," +
                "teach_course varchar(20)" +
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
        onCreate(db);
    }
}
