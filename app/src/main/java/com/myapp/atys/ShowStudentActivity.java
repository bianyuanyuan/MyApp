package com.myapp.atys;

/**
 * Created by 540 on 2018/3/14.
 * 展示单条记录详细消息
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.myapp.Data.Student;
import com.myapp.db.TableContanst;
import myapp.byy.com.myapp.R;

public class ShowStudentActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);
        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra(TableContanst.STUDENT_TABLE);
        ((TextView)findViewById(R.id.tv_info_id)).setText(student.getId()+"");
        ((TextView)findViewById(R.id.tv_info_name)).setText(student.getName());
        ((TextView)findViewById(R.id.tv_info_age)).setText(student.getAge()+"");
        ((TextView)findViewById(R.id.tv_info_sex)).setText(student.getSex());
        ((TextView)findViewById(R.id.tv_info_likes)).setText(student.getLike());
        ((TextView)findViewById(R.id.tv_info_train_date)).setText(student.getTrainDate());
        ((TextView)findViewById(R.id.tv_info_phone)).setText(student.getPhoneNumber());
    }
    public void goBack(View view) {
        finish();
    }
}