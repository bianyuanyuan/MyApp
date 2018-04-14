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
        values.put(TableContanst.CoachColumns.PHONE_NUMBER, c.getPhoneNumber());
        values.put(TableContanst.CoachColumns.TEACH_YEAR, c.getTeach_year());
        values.put(TableContanst.CoachColumns.TEACH_COURSE, c.getTeach_course());
        values.put(TableContanst.CoachColumns.CHARGE, c.getCharge());
        return dbHelper.getWritableDatabase().insert(TableContanst.COACH_TABLE, null, values);
    }

    // 添加一个Class对象数据到数据库表
    public long addClass(Class cr) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.ClassColumns.NAME, cr.getName());
        values.put(TableContanst.ClassColumns.POSITION, cr.getPosition());
        values.put(TableContanst.ClassColumns.CONTAIN, cr.getContain());
        return dbHelper.getWritableDatabase().insert(TableContanst.CLASS_TABLE, null, values);
    }

    // 添加一个Course对象数据到数据库表
    public long addCourse(Course cs) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.CourseColumns.NAME, cs.getName());
        values.put(TableContanst.CourseColumns.COACH_ID, cs.getCoach_id());
        values.put(TableContanst.CourseColumns.TIMESWEEK, cs.getTimesweek());
        values.put(TableContanst.CourseColumns.TIMES, cs.getTimes());
        values.put(TableContanst.CourseColumns.PRICE, cs.getPrice());
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

    // 删除一个id所对应的数据库表coach的记录
    public int deleteCoachById(long id) {
        return dbHelper.getWritableDatabase().delete(TableContanst.COACH_TABLE,
                TableContanst.CoachColumns.ID + "=?", new String[]{id + ""});
    }

    // 删除一个id所对应的数据库表Class的记录
    public int deleteClassById(long id) {
        return dbHelper.getWritableDatabase().delete(TableContanst.CLASS_TABLE,
                TableContanst.ClassColumns.ID + "=?", new String[]{id + ""});
    }

    // 删除一个id所对应的数据库表Course的记录
    public int deleteCourseById(long id) {
        return dbHelper.getWritableDatabase().delete(TableContanst.COURSE_TABLE,
                TableContanst.CourseColumns.ID + "=?", new String[]{id + ""});
    }

    // 删除一个id所对应的数据库表TimeTable的记录
    public int deleteTimeTableById(long id) {
        return dbHelper.getWritableDatabase().delete(TableContanst.TIMETABLE_TABLE,
                TableContanst.TimeTableColumns.ID + "=?", new String[]{id + ""});
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

    // 更新一个id所对应数据库表course的记录
    public int updateCoach(Coach c) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.CoachColumns.NAME, c.getName());
        values.put(TableContanst.CoachColumns.AGE, c.getAge());
        values.put(TableContanst.CoachColumns.SEX, c.getSex());
        values.put(TableContanst.CoachColumns.PHONE_NUMBER, c.getPhoneNumber());
        values.put(TableContanst.CoachColumns.TEACH_YEAR, c.getTeach_year());
        values.put(TableContanst.CoachColumns.TEACH_COURSE, c.getTeach_course());
        values.put(TableContanst.CoachColumns.CHARGE, c.getCharge());
        return dbHelper.getWritableDatabase().update(TableContanst.COACH_TABLE, values,
                TableContanst.CoachColumns.ID + "=?", new String[]{c.getId() + ""});
    }

    // 更新一个id所对应数据库表Class的记录
    public long updateClass(Class cr) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.ClassColumns.NAME, cr.getName());
        values.put(TableContanst.ClassColumns.POSITION, cr.getPosition());
        values.put(TableContanst.ClassColumns.CONTAIN, cr.getContain());
        return dbHelper.getWritableDatabase().update(TableContanst.CLASS_TABLE, values,
                TableContanst.ClassColumns.ID + "=?", new String[]{cr.getId() + ""});
    }

    // 更新一个id所对应数据库表Course的记录
    public int updateCourse(Course cs) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.CourseColumns.NAME, cs.getName());
        values.put(TableContanst.CourseColumns.COACH_ID, cs.getCoach_id());
        values.put(TableContanst.CourseColumns.TIMESWEEK, cs.getTimesweek());
        values.put(TableContanst.CourseColumns.TIMES, cs.getTimes());
        values.put(TableContanst.CourseColumns.PRICE, cs.getPrice());
        return dbHelper.getWritableDatabase().update(TableContanst.COURSE_TABLE, values,
                TableContanst.CourseColumns.ID + "=?", new String[]{cs.getId() + ""});
    }

    // 更新一个id所对应数据库表TimeTable的记录
    public int updateTimeTable(TimeTable tt) {
        ContentValues values = new ContentValues();
        values.put(TableContanst.TimeTableColumns.TIME, tt.getTime());
        values.put(TableContanst.TimeTableColumns.WEEK, tt.getWeek());
        return dbHelper.getWritableDatabase().update(TableContanst.TIMETABLE_TABLE, values,
                TableContanst.TimeTableColumns.ID + "=?", new String[]{tt.getId() + ""});
    }


    // 查询所有student的记录
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

    // 查询所有coach的记录
    public List<Map<String, Object>> getAllCoachs() {
        //id desc
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.COACH_TABLE, null, null, null,
                null, null, TableContanst.CoachColumns.ID + " desc");
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>(8);
            long id = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.ID));
            map.put(TableContanst.CoachColumns.ID, id);

            String name = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.NAME));
            map.put(TableContanst.CoachColumns.NAME, name);

            int age = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.AGE));
            map.put(TableContanst.CoachColumns.AGE, age);

            String sex = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.SEX));
            map.put(TableContanst.CoachColumns.SEX, sex);

            String phone_number = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.PHONE_NUMBER));
            map.put(TableContanst.CoachColumns.PHONE_NUMBER, phone_number);

            int teach_year = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.TEACH_YEAR));
            map.put(TableContanst.CoachColumns.TEACH_YEAR, teach_year);

            String teach_course = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.TEACH_COURSE));
            map.put(TableContanst.CoachColumns.TEACH_COURSE, teach_course);

            int charge = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.CHARGE));
            map.put(TableContanst.CoachColumns.CHARGE, charge);
            data.add(map);
        }
        return data;
    }

    // 查询所有class的记录
    public List<Map<String, Object>> getAllClasses() {
        //id desc
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.CLASS_TABLE, null, null, null,
                null, null, TableContanst.ClassColumns.ID + " desc");
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>(4);
            long id = cursor.getInt(cursor.getColumnIndex(TableContanst.ClassColumns.ID));
            map.put(TableContanst.ClassColumns.ID, id);

            String name = cursor.getString(cursor.getColumnIndex(TableContanst.ClassColumns.NAME));
            map.put(TableContanst.ClassColumns.NAME, name);

            String position= cursor.getString(cursor.getColumnIndex(TableContanst.ClassColumns.POSITION));
            map.put(TableContanst.ClassColumns.POSITION,position);

            int contain = cursor.getInt(cursor.getColumnIndex(TableContanst.ClassColumns.CONTAIN));
            map.put(TableContanst.ClassColumns.CONTAIN, contain);
            data.add(map);
        }
        return data;
    }

    // 查询所有course的记录
    public List<Map<String, Object>> getAllCoursees() {
        //id desc
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.COURSE_TABLE, null, null, null,
                null, null, TableContanst.CourseColumns.ID + " desc");
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>(6);
            long id = cursor.getInt(cursor.getColumnIndex(TableContanst.CourseColumns.ID));
            map.put(TableContanst.CourseColumns.ID, id);

            String name = cursor.getString(cursor.getColumnIndex(TableContanst.CourseColumns.NAME));
            map.put(TableContanst.CourseColumns.NAME, name);

            long coach_id = cursor.getInt(cursor.getColumnIndex(TableContanst.CourseColumns.COACH_ID));
            map.put(TableContanst.CourseColumns.COACH_ID, coach_id);


            long times = cursor.getInt(cursor.getColumnIndex(TableContanst.CourseColumns.TIMES));
            map.put(TableContanst.CourseColumns.TIMES, times);

            long timesweek = cursor.getInt(cursor.getColumnIndex(TableContanst.CourseColumns.TIMESWEEK));
            map.put(TableContanst.CourseColumns.TIMESWEEK, timesweek);

            long price = cursor.getInt(cursor.getColumnIndex(TableContanst.CourseColumns.PRICE));
            map.put(TableContanst.CourseColumns.PRICE, price);
        }
        return data;
    }

    // 查询所有TimeTable的记录
    public List<Map<String, Object>> getAllTimeTables() {
        //id desc
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.TIMETABLE_TABLE, null, null, null,
                null, null, TableContanst.TimeTableColumns.ID + " desc");
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>(3);
            long id = cursor.getInt(cursor.getColumnIndex(TableContanst.TimeTableColumns.ID));
            map.put(TableContanst.TimeTableColumns.ID, id);

            String week = cursor.getString(cursor.getColumnIndex(TableContanst.TimeTableColumns.WEEK));
            map.put(TableContanst.TimeTableColumns.WEEK, week);

            String time = cursor.getString(cursor.getColumnIndex(TableContanst.TimeTableColumns.TIME));
            map.put(TableContanst.TimeTableColumns.TIME, time);
        }
        return data;
    }

    //模糊查询一条记录
    public Cursor findStudent(String name) {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.STUDENT_TABLE, null, "name like ?",
                new String[]{"%" + name + "%"}, null, null, null, null);
        return cursor;
    }

    public Cursor findCoach(String name) {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.COACH_TABLE, null, "name like ?",
                new String[]{"%" + name + "%"}, null, null, null, null);
        return cursor;
    }

    public Cursor findClass(String name) {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.COACH_TABLE, null, "name like ?",
                new String[]{"%" + name + "%"}, null, null, null, null);
        return cursor;
    }

    public Cursor findTimeTable(String name) {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.TIMETABLE_TABLE, null, "name like ?",
                new String[]{"%" + name + "%"}, null, null, null, null);
        return cursor;
    }

    public Cursor findCourse(String name) {
        Cursor cursor = dbHelper.getWritableDatabase().query(TableContanst.COURSE_TABLE, null, "name like ?",
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

    //自定义的方法通过View和Id得到一个coach对象
    public Coach getCoachFromView(View view, long id) {
        TextView nameView = (TextView) view.findViewById(R.id.tv_co_name);
        TextView ageView = (TextView) view.findViewById(R.id.tv_co_age);
        TextView sexView = (TextView) view.findViewById(R.id.tv_co_sex);
        TextView phoneView = (TextView) view.findViewById(R.id.tv_co_phone);
        TextView teachyearView = (TextView) view.findViewById(R.id.tv_co_teach_year);
        TextView chargeView = (TextView) view.findViewById(R.id.tv_co_charge);
        TextView teachcourseView = (TextView) view.findViewById(R.id.tv_co_teach_course);

        String name = nameView.getText().toString();
        int age = Integer.parseInt(ageView.getText().toString());
        String sex = sexView.getText().toString();
        String phone = phoneView.getText().toString();
        int teachyear = Integer.parseInt(teachyearView.getText().toString());
        int charge = Integer.parseInt(chargeView.getText().toString());
        String teachcourse = teachcourseView.getText().toString();
        Coach coach = new Coach(id, name, age, sex, phone, teachyear, charge, teachcourse);
        return coach;
    }

    //自定义的方法通过View和Id得到一个class对象
    public Class getClassFromView(View view, long id) {
        TextView nameView = (TextView) view.findViewById(R.id.tv_cr_name);
        TextView positionView = (TextView) view.findViewById(R.id.tv_cr_position);
        TextView containView = (TextView) view.findViewById(R.id.tv_cr_contain);

        String name = nameView.getText().toString();
        String position = positionView.getText().toString();
        int concain = Integer.parseInt(containView.getText().toString());
        Class cr = new Class(id, name, position, concain);
        return cr;
    }

    //自定义的方法通过View和Id得到一个course对象
    public Course getCourseFromView(View view, long id) {
        TextView nameView = (TextView) view.findViewById(R.id.tv_cs_name);
        TextView coachidView = (TextView) view.findViewById(R.id.tv_cs_coach_id);
        TextView timesView = (TextView) view.findViewById(R.id.tv_cs_times);
        TextView timesweekView = (TextView) view.findViewById(R.id.tv_cs_timesweek);
        TextView priceView = (TextView) view.findViewById(R.id.tv_cs_price);

        String name = nameView.getText().toString();
        int coachid = Integer.parseInt(coachidView.getText().toString());
        int times = Integer.parseInt(timesView.getText().toString());
        int timesweek = Integer.parseInt(timesweekView.getText().toString());
        int price = Integer.parseInt(priceView.getText().toString());
        Course course = new Course(id, name, coachid, times, timesweek, price);
        return course;
    }

    //自定义的方法通过View和Id得到一个timetable对象
    public TimeTable getTimeTableFromView(View view, long id) {
        TextView timeView = (TextView) view.findViewById(R.id.tv_tt_time);
        TextView weekView = (TextView) view.findViewById(R.id.tv_tt_week);

        String time = timeView.getText().toString();
        String week = weekView.getText().toString();
        TimeTable tb = new TimeTable(id, time, week);
        return tb;
    }

}