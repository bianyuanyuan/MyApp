package com.myapp.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.Class;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.io.Serializable;

import myapp.byy.com.myapp.R;


public class AddClassActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AddClassActivity";
    private TextView idText;
    private EditText nameText;
    private EditText positionText;
    private EditText containText;

    private Button restoreButton;
    private Button resetButton;
    private Long class_id;
    private DataDao dao;//数据库操作
    private boolean isAdd = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);//////////////something wrong
        idText = (TextView) findViewById(R.id.tv_cr_id);
        nameText = (EditText) findViewById(R.id.et_cr_name);
        positionText = (EditText) findViewById(R.id.et_cr_position);
        containText = (EditText) findViewById(R.id.et_cr_contain);
        restoreButton = (Button) findViewById(R.id.btn_cr_save);
        resetButton = (Button) findViewById(R.id.btn_cr_clear);

        dao = new DataDao(new DBOpenHelper(this)); // 设置监听 78
        restoreButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        checkIsAddClass();
    }

    // 检查此时Activity是否用于添加场地信息
    private void checkIsAddClass() {
        Intent intent = getIntent();
        Serializable serial = intent.getSerializableExtra(TableContanst.CLASS_TABLE);
        if (serial == null) {
            isAdd = true;
        } else {
            isAdd = false;
            Class cr = (Class) serial;
            showEditUI(cr);
        }
    }

    //显示教练信息更新UI
    private void showEditUI(Class classroom) {
        // 先将Class携带的数据还原到classroom的每一个属性中去
        class_id = classroom.getId();
        String name = classroom.getName();
        String position = classroom.getPosition();
        int contain = classroom.getContain();
        // 还原数据
        idText.setText(class_id + "");
        nameText.setText(name + "");
        positionText.setText(position + "");
        containText.setText(contain + "");

        setTitle("场地信息更新");
        restoreButton.setText("更新");
    }

    public void onClick(View v) {
        // 收集数据
        if (v == restoreButton) {
            if (!checkUIInput()) {// 界面输入验证
                return;
            }
            Class aClass= getClassFromUI();
            if (isAdd) {
                long id = dao.addClass(aClass);
                dao.closeDB();
                if (id > 0) {
                    Toast.makeText(this, "保存成功， ID=" + id, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "保存失败，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            } else if (!isAdd) {
                long id = dao.addClass(aClass);
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
        positionText.setText("");
        containText.setText("");
    }

    //      收集界面输入的数据，并将封装成Class对象
    private Class getClassFromUI() {
        String name = nameText.getText().toString();
        String position = positionText.getText().toString();
        int contain= Integer.parseInt(containText.getText().toString());
        Class cr = new Class(name, position,contain);
        if (!isAdd) {
            cr.setId(Integer.parseInt(idText.getText().toString()));
            dao.deleteClassById(class_id);
        }
        return cr;
    }


    //验证用户是否按要求输入了数据
    private boolean checkUIInput() { // name
        String name = nameText.getText().toString();
        String message = null;
        View invadView = null;
        if (name.trim().length() == 0) {
            message = "请输入场地名！";
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
