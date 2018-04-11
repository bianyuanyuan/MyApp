package com.myapp.db;

/**
 * Created by 540 on 2018/3/14.
 */

public final class TableContanst {
    public static final String STUDENT_TABLE = "student";//学员
    public static final String COACH_TABLE = "coach";//教练
    public static final String CLASS_TABLE = "class";//场地
    public static final String COURSE_TABLE = "course";//课程
    public static final String TIMETABLE_TABLE = "timetable";//教练课程表

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
        public static final String LIKES = "likes";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String MODIFY_TIME = "modify_time";
    }

    public static final class ClassColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String NUMBER = "number";

    }

    public static final class CourseColumns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String STUDENT_ID = "student_id";
        public static final String COACH_ID = "coach_id";
        public static final String TIMESWEEK = "timesweek";
        public static final String TIMES = "times";
    }

    public static final class TimeTableColumns {
        public static final String ID = "_id";
        public static final String WEEK = "week";
        public static final String TIME = "time";
    }

}