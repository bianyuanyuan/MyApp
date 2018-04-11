package com.myapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.myapp.Data.Coach;
import com.myapp.Data.Class;
import com.myapp.Data.Course;
import com.myapp.Data.TimeTable;
import com.myapp.Data.Student;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapp.byy.com.myapp.R;

/**
 * 数据库方法封装，创建表，删除表，数据（增删改查）..
 * Created by 540 on 2018/4/6.
 */

public class DataDao {
    private DBOpenHelper dbHelper;
    private Cursor cursor;

    public DataDao(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    public DataDao(DBOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void dropTable(String taleName) {//删除表
        dbHelper.getWritableDatabase().execSQL(
                "DROP TABLE IF EXISTS " + taleName);
    }

    public void closeDatabase(String DatabaseName) {//关闭数据库
        dbHelper.getWritableDatabase().close();
    }

    // 添加一个Student对象数据到数据库表
    public long addStudent(Student s) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.StudentColumns.NAME, s.getName());
        values.put(TableContanst.StudentColumns.AGE, s.getAge());
        values.put(TableContanst.StudentColumns.SEX, s.getSex());
        values.put(TableContanst.StudentColumns.LIKES, s.getLike());
        values.put(TableContanst.StudentColumns.PHONE_NUMBER, s.getPhoneNumber());
        values.put(TableContanst.StudentColumns.TRAIN_DATE, s.getTrainDate());
        values.put(TableContanst.StudentColumns.MODIFY_TIME, s.getModifyDateTime());
        return dbHelper.getWritableDatabase().insert(TableContanst.STUDENT_TABLE, null, values);
    }

    // 添加一个Coach对象数据到数据库表
    public long addCoach(Coach c) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.CoachColumns.NAME, c.getName());
        values.put(TableContanst.CoachColumns.AGE, c.getAge());
        values.put(TableContanst.CoachColumns.SEX, c.getSex());
        values.put(TableContanst.CoachColumns.LIKES, c.getLike());
        values.put(TableContanst.CoachColumns.PHONE_NUMBER, c.getPhoneNumber());
        return dbHelper.getWritableDatabase().insert(TableContanst.COACH_TABLE, null, values);
    }

    // 添加一个Class对象数据到数据库表
    public long addClass(Class cr) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.ClassColumns.NAME, cr.getName());
        values.put(TableContanst.ClassColumns.NUMBER, cr.getNumber());
        return dbHelper.getWritableDatabase().insert(TableContanst.CLASS_TABLE, null, values);
    }

    // 添加一个Course对象数据到数据库表
    public long addCourse(Course cs) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.CourseColumns.NAME, cs.getName());
        values.put(TableContanst.CourseColumns.STUDENT_ID, cs.getStudent_id());
        values.put(TableContanst.CourseColumns.COACH_ID, cs.getCoach_id());
        values.put(TableContanst.CourseColumns.TIMESWEEK, cs.getTimesweek());
        values.put(TableContanst.CourseColumns.TIMES, cs.getTimes());
        return dbHelper.getWritableDatabase().insert(TableContanst.COURSE_TABLE, null, values);
    }

    // 添加一个TimeTable对象数据到数据库表
    public long addTimeTable(TimeTable tt) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.TimeTableColumns.TIME, tt.getTime());
        values.put(TableContanst.TimeTableColumns.WEEK, tt.getWeek());
        return dbHelper.getWritableDatabase().insert(TableContanst.TIMETABLE_TABLE, null, values);
    }

    // 删除一个id所对应的数据库表student的记录
    public int deleteStudentById(long id) {
        return dbHelper.getWritableDatabase().delete(TableContanst.STUDENT_TABLE,
                TableContanst.StudentColumns.ID + "=?", new String[]{id + ""});
    }

    // 更新一个id所对应数据库表student的记录
    public int updateStudent(Student s) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.StudentColumns.NAME, s.getName());
        values.put(TableContanst.StudentColumns.AGE, s.getAge());
        values.put(TableContanst.StudentColumns.SEX, s.getSex());
        values.put(TableContanst.StudentColumns.LIKES, s.getLike());
        values.put(TableContanst.StudentColumns.PHONE_NUMBER, s.getPhoneNumber());
        values.put(TableContanst.StudentColumns.TRAIN_DATE, s.getTrainDate());
        values.put(TableContanst.StudentColumns.MODIFY_TIME, s.getModifyDateTime());
        return dbHelper.getWritableDatabase().update(TableContanst.STUDENT_TABLE, values,
                TableContanst.StudentColumns.ID + "=?", new String[]{s.getId() + ""});
    }

    // 查询所有的记录
    public List<Map<String, Object>> getAllStudents() {
        //modify_time desc
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.STUDENT_TABLE, null, null, null,
                null, null, TableContanst.StudentColumns.MODIFY_TIME + " desc");
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>(8);
            long id = cursor.getInt(cursor.getColumnIndex(TableContanst.StudentColumns.ID));
            map.put(TableContanst.StudentColumns.ID, id);

            String name = cursor.getString(cursor.getColumnIndex(TableContanst.StudentColumns.NAME));
            map.put(TableContanst.StudentColumns.NAME, name);

            int age = cursor.getInt(cursor.getColumnIndex(TableContanst.StudentColumns.AGE));
            map.put(TableContanst.StudentColumns.AGE, age);

            String sex = cursor.getString(cursor.getColumnIndex(TableContanst.StudentColumns.SEX));
            map.put(TableContanst.StudentColumns.SEX, sex);

            String likes = cursor.getString(cursor.getColumnIndex(TableContanst.StudentColumns.LIKES));
            map.put(TableContanst.StudentColumns.LIKES, likes);

            String phone_number = cursor.getString(cursor.getColumnIndex(TableContanst.StudentColumns.PHONE_NUMBER));
            map.put(TableContanst.StudentColumns.PHONE_NUMBER, phone_number);

            String train_date = cursor.getString(cursor.getColumnIndex(TableContanst.StudentColumns.TRAIN_DATE));
            map.put(TableContanst.StudentColumns.TRAIN_DATE, train_date);

            String modify_time = cursor.getString(cursor.getColumnIndex(TableContanst.StudentColumns.MODIFY_TIME));
            map.put(TableContanst.StudentColumns.MODIFY_TIME, modify_time);
            data.add(map);
        }
        return data;
    }

    //模糊查询一条记录
    public Cursor findStudent(String name) {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.STUDENT_TABLE, null, "name like ?",
                new String[]{"%" + name + "%"}, null, null, null, null);
        return cursor;
    }

    //按姓名进行排序
    public Cursor sortByName() {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.STUDENT_TABLE, null, null,
                null, null, null, TableContanst.StudentColumns.NAME);
        return cursor;
    }

    //按入学日期进行排序
    public Cursor sortByTrainDate() {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.STUDENT_TABLE, null, null,
                null, null, null, TableContanst.StudentColumns.TRAIN_DATE);
        return cursor;
    }

    //按学号进行排序
    public Cursor sortByID() {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.STUDENT_TABLE, null, null,
                null, null, null, TableContanst.StudentColumns.ID);
        return cursor;
    }

    public void closeDB() {
        dbHelper.close();
    }

    //自定义的方法通过View和Id得到一个student对象
    public Student getStudentFromView(View view, long id) {
        TextView nameView = (TextView) view.findViewById(R.id.tv_stu_name);
        TextView ageView = (TextView) view.findViewById(R.id.tv_stu_age);
        TextView sexView = (TextView) view.findViewById(R.id.tv_stu_sex);
        TextView likeView = (TextView) view.findViewById(R.id.tv_stu_likes);
        TextView phoneView = (TextView) view.findViewById(R.id.tv_stu_phone);
        TextView dataView = (TextView) view.findViewById(R.id.tv_stu_traindate);

        String name = nameView.getText().toString();
        int age = Integer.parseInt(ageView.getText().toString());
        String sex = sexView.getText().toString();
        String like = likeView.getText().toString();
        String phone = phoneView.getText().toString();
        String data = dataView.getText().toString();
        Student student = new Student(id, name, age, sex, like, phone, data, null);
        return student;
    }
}