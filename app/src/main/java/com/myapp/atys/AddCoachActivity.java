package com.myapp.atys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.Coach;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.io.Serializable;

import myapp.byy.com.myapp.R;

/**
 * Created by 540 on 2018/4/11.
 */

public class AddCoachActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AddCoachActivity";
    private TextView idText;
    private EditText nameText;
    private EditText ageText;
    private EditText phoneText;
    private RadioGroup group;
    private RadioButton button1;
    private RadioButton button2;

    private EditText teachyearText;
    private EditText chargeText;
    private EditText teachcourseText;

    private Button restoreButton;
    private String sex;
    private Button resetButton;
    private Long coach_id;
    private DataDao dao;//数据库操作
    private boolean isAdd = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_coach);//////////////////////////////////////////something wrong
        idText = (TextView) findViewById(R.id.tv_co_id);
        nameText = (EditText) findViewById(R.id.et_co_name);
        ageText = (EditText) findViewById(R.id.et_co_age);
        button1 = (RadioButton) findViewById(R.id.rb_co_sex_female);
        button2 = (RadioButton) findViewById(R.id.rb_co_sex_male);
        phoneText = (EditText) findViewById(R.id.et_co_phone);
        group = (RadioGroup) findViewById(R.id.rg_co_sex);
        restoreButton = (Button) findViewById(R.id.btn_co_save);
        resetButton = (Button) findViewById(R.id.btn_co_clear);
        teachyearText = (EditText) findViewById(R.id.et_co_teach_year);
        chargeText = (EditText) findViewById(R.id.et_co_charge);
        teachcourseText = (EditText) findViewById(R.id.et_co_teach_course);

        dao = new DataDao(new DBOpenHelper(this)); // 设置监听 78
        restoreButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        checkIsAddCoach();
    }

    // 检查此时Activity是否用于添加教练信息
    private void checkIsAddCoach() {
        Intent intent = getIntent();
        Serializable serial = intent.getSerializableExtra(TableContanst.COACH_TABLE);
        if (serial == null) {
            isAdd = true;
        } else {
            isAdd = false;
            Coach c = (Coach) serial;
            showEditUI(c);
        }
    }

    //显示教练信息更新UI
    private void showEditUI(Coach coach) {
        // 先将Coach携带的数据还原到coach的每一个属性中去
        coach_id = coach.getId();
        String name1 = coach.getName();
        int age1 = coach.getAge();
        String phone1 = coach.getPhoneNumber();
        String sex1 = coach.getSex();
        if (sex1.toString().equals("男")) {
            button2.setChecked(true);
        } else if (sex1.toString().equals("女")) {
            button1.setChecked(true);
        }
        long teachyear = coach.getTeach_year();
        long charge = coach.getCharge();
        String teachcourse = coach.getTeach_course();

        // 还原数据
        idText.setText(coach_id + "");
        nameText.setText(name1 + "");
        ageText.setText(age1 + "");
        phoneText.setText(phone1 + "");
        teachyearText.setText(teachyear + "");
        chargeText.setText(charge + "");
        teachcourseText.setText(teachcourse + "");

        setTitle("教练信息更新");
        restoreButton.setText("更新");
    }

    public void onClick(View v) {
        // 收集数据
        if (v == restoreButton) {
            if (!checkUIInput()) {// 界面输入验证
                return;
            }
            Coach coach = getCoachFromUI();
            if (isAdd) {
                long id = dao.addCoach(coach);
                dao.closeDB();
                if (id > 0) {
                    Toast.makeText(this, "保存成功， ID=" + id, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "保存失败，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            } else if (!isAdd) {
                long id = dao.addCoach(coach);
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
        ageText.setText("");
        phoneText.setText("");
        teachyearText.setText("");
        chargeText.setText("");
        teachcourseText.setText("");
        group.clearCheck();
    }

    //      收集界面输入的数据，并将封装成Coach对象
    private Coach getCoachFromUI() {
        String name = nameText.getText().toString();
        int age = Integer.parseInt(ageText.getText().toString());
        String sex = ((RadioButton) findViewById(group
                .getCheckedRadioButtonId())).getText().toString();
        String phoneNumber = phoneText.getText().toString();
        long teachyear = Integer.parseInt(teachyearText.getText().toString());
        long charge = Integer.parseInt(chargeText.getText().toString());
        String teachcourse = teachcourseText.getText().toString();
        Coach c = new Coach(name, age, sex, phoneNumber, teachyear, charge, teachcourse);
        if (!isAdd) {
            c.setId(Integer.parseInt(idText.getText().toString()));
            dao.deleteCoachById(coach_id);
        }
        return c;
    }


    //验证用户是否按要求输入了数据
    private boolean checkUIInput() { // name, age, sex
        String name = nameText.getText().toString();
        String age = ageText.getText().toString();
        int id = group.getCheckedRadioButtonId();
        String message = null;
        View invadView = null;
        if (name.trim().length() == 0) {
            message = "请输入姓名！";
            invadView = nameText;
        } else if (age.trim().length() == 0) {
            message = "请输入年龄！";
            invadView = ageText;
        } else if (id == -1) {
            message = "请选择性别！";
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
