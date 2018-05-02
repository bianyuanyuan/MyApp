package com.myapp.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.Class;
import com.myapp.Data.Course;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.io.Serializable;

import myapp.byy.com.myapp.R;

public class AddCourseActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AddCourseActivity";
    private TextView idText;
    private EditText nameText;
    private EditText timesText;
    private EditText timesweekText;
    private EditText priceText;

    private Button restoreButton;
    private Button resetButton;
    private Long course_id;
    private DataDao dao;//数据库操作
    private boolean isAdd = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);//////////////something wrong
        idText = (TextView) findViewById(R.id.tv_cs_id);
        nameText = (EditText) findViewById(R.id.et_cs_name);
        timesText = (EditText) findViewById(R.id.et_cs_times);
        timesweekText = (EditText) findViewById(R.id.et_cs_timesweek);
        priceText = (EditText) findViewById(R.id.et_cs_price);
        restoreButton = (Button) findViewById(R.id.btn_cs_save);
        resetButton = (Button) findViewById(R.id.btn_cs_clear);

        dao = new DataDao(new DBOpenHelper(this)); // 设置监听 78
        restoreButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        checkIsAddCourse();
    }

    // 检查此时Activity是否用于添加课程信息
    private void checkIsAddCourse() {
        Intent intent = getIntent();
        Serializable serial = intent.getSerializableExtra(TableContanst.COURSE_TABLE);
        if (serial == null) {
            isAdd = true;
        } else {
            isAdd = false;
            Course cs = (Course) serial;
            showEditUI(cs);
        }
    }

    //显示课程信息更新UI
    private void showEditUI(Course course) {
        // 先将Course携带的数据还原到course的每一个属性中去
        course_id = course.getId();
        String name = course.getName();
        long times = course.getTimes();
        long timesweek = course.getTimesweek();
        long price = course.getPrice();
        // 还原数据
        idText.setText(course_id + "");
        nameText.setText(name + "");
        timesText.setText(times + "");
        timesweekText.setText(timesweek + "");
        priceText.setText(price + "");

        setTitle("课程信息更新");
        restoreButton.setText("更新");
    }

    public void onClick(View v) {
        // 收集数据
        if (v == restoreButton) {
            if (!checkUIInput()) {// 界面输入验证
                return;
            }
            Course course = getCourseFromUI();
            if (isAdd) {
                long id = dao.addCourse(course);
                dao.closeDB();
                if (id > 0) {
                    Toast.makeText(this, "保存成功， ID=" + id, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "保存失败，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            } else if (!isAdd) {
                long id = dao.addCourse(course);
                dao.closeDB();
                if (id > 0) {
                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "更新失败，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v == resetButton) {
            clearUIData();
        }
    }

    // 清空界面的数据
    private void clearUIData() {
        nameText.setText("");
        timesText.setText("");
        timesweekText.setText("");
        priceText.setText("");
    }

    //      收集界面输入的数据，并将封装成Course对象
    private Course getCourseFromUI() {
        String name = nameText.getText().toString();
        long times = Integer.parseInt(timesText.getText().toString());
        long timesweek = Integer.parseInt(timesweekText.getText().toString());
        long price = Integer.parseInt(priceText.getText().toString());
        Course cs = new Course(name, times, timesweek, price);
        if (!isAdd) {
            cs.setId(Integer.parseInt(idText.getText().toString()));
            dao.deleteCourseById(course_id);
        }
        return cs;
    }


    //验证用户是否按要求输入了数据
    private boolean checkUIInput() { // name
        String name = nameText.getText().toString();
        String message = null;
        View invadView = null;
        if (name.trim().length() == 0) {
            message = "请输入课程名！";
            invadView = nameText;
        }
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (invadView != null)
                invadView.requestFocus();
            return false;
        }
        return true;
    }
}

