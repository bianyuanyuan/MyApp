package com.myapp.db;

/**
 * Created by 540 on 2018/3/14.
 */

public final class TableContanst {
    public static final String STUDENT_TABLE = "student";//学员
    public static final String COACH_TABLE = "coach";//教练
    public static final String CLASS_TABLE = "class";//场地
    public static final String COURSE_TABLE = "course";//课程
    public static final String TIMETABLE_TABLE = "timetable";//时间表
    public static final String COACH_TIME_TABLE = "coach_timetable";//教练时间分配表
    public static final String CLASS_TIME_TABLE = "course_timetable";//场地时间分配表
    public static final String CLASS_COURSE_TABLE = "class_timetable";//场地课程表
    public static final String STUDENT_COURSE_TABLE = "sc_timetable";//学员课程表

    public static final String SELECT_TABLE = "select_table";//选课表
    public static final String USER="user";//用户表

    public static final class SelectColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String SEX = "sex";
        public static final String LIKES = "likes";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String TRAIN_DATE = "train_date";
        public static final String MODIFY_TIME = "modify_time";
    }

    public static final class UserColumns {
        public static final String ID = "_id";
        public static final String NICKNAME = "nickname";
       // public static final String AVATARIMAGE= "avatarimage";
        public static final String ACCOUNT= "account";
        public static final String PSW = "password";
        public static final String ISVISITOR= "isvisitor";
    }

    public static final class StudentColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String SEX = "sex";
        public static final String LIKES = "likes";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String TRAIN_DATE = "train_date";
        public static final String MODIFY_TIME = "modify_time";
    }

    public static final class CoachColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String SEX = "sex";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String TEACH_YEAR = "teach_year";
        public static final String CHARGE = "charge";
        public static final String TEACH_COURSE = "teach_course";
        public static final String TIME_TWO="time_two";
        public static final String TIME_FOUR="time_four";
    }

    public static final class ClassColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String POSITION = "position";
        public static final String CONTAIN = "contain";
    }

    public static final class CourseColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String TIMESWEEK = "timesweek";
        public static final String TIMES = "times";
        public static final String PRICE = "price";
    }

    public static final class TimeTableColumns {
        public static final String ID = "_id";
        public static final String PERIODS = "periods";//时间段
        public static final String WEEK = "week";//星期
        public static final String TIME = "time";//节数
        public static final String T_START = "t_start";//开始时间
        public static final String T_END = "t_end";//结束时间
    }

    public static final class CoachTimeTableColumns {
        public static final String ID = "_id";
        public static final String PERIODS = "periods";//时间段
        public static final String COACH_ID = "coach_id";
    }

    public static final class ClassTimeTableColumns {
        public static final String ID = "_id";
        public static final String PERIODS = "periods";//时间段
        public static final String CLASS_ID = "class_id";
    }

    public static final class ClassCourseTableColumns {
        public static final String ID = "_id";
        public static final String CLASS_ID = "class_id";
        public static final String COURSE_ID = "course_id";

    }

    public static final class StudentCourseTableColumns {
        public static final String ID = "_id";
        public static final String COACH_NAME = "coach_name";
        public static final String COURSE_NAME = "course_name";
    }
}