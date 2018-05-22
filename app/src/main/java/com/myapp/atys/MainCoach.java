package com.myapp.atys;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.myapp.Activity.CInfoActivity;
import com.myapp.Activity.SeeStudent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myapp.byy.com.myapp.R;

public class MainCoach extends BaseActivity {


    private ImageView back;
    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private String[] type = {"查看学员信息", "发布课程信息", "查看个人信息"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main);
        // setTitle("教练端主页");
        initView();

        for (int i = 0; i < type.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type", type[i]);
            list.add(hashMap);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        simpleAdapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        autoStartActivity(SeeStudent.class);//如何只显示预约本教练的学员
                        break;
                    case 1:

                        autoStartActivity(AddCoachActivity.class);//如何限制只发布一次
                        break;
                    case 2:
                        autoStartActivity(CInfoActivity.class);
                        break;
                }
            }
        });


    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        back = (ImageView) findViewById(R.id.back);
        list = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.listitem_main, new String[]{"type"}, new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);

    }

}

