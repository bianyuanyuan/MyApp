package com.myapp.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myapp.Data.Class;
import com.myapp.db.TableContanst;

import myapp.byy.com.myapp.R;

public class ShowClassActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_info);
        Intent intent = getIntent();
        Class classroom = (Class) intent.getSerializableExtra(TableContanst.CLASS_TABLE);
        ((TextView) findViewById(R.id.tv_cr_info_id)).setText(classroom.getId() + "");
        ((TextView) findViewById(R.id.tv_cr_info_name)).setText(classroom.getName());
        ((TextView) findViewById(R.id.tv_cr_info_position)).setText(classroom.getPosition());
        ((TextView) findViewById(R.id.tv_cr_info_contain)).setText(classroom.getContain() + "");
    }

    public void goBack(View view) {
        finish();
    }
}