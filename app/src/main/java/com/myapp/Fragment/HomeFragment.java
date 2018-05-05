package com.myapp.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.myapp.Data.Course;
import com.myapp.atys.ShowCourseAty;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myapp.byy.com.myapp.R;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private DataDao dao;
    private ListView listview;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //创建或打开数据库
        dao = new DataDao(new DBOpenHelper(getActivity(), "manager.db", null, 1));
        initlist(view, inflater, container);

        return view;
    }

    private void initlist(View view, LayoutInflater inflater, ViewGroup container) {
        listview = (ListView) view.findViewById(R.id.courseList);

        //获取到集合数据
        List<Course> courses = dao.getAllcourseinfo();
        //获取ListView,并通过Adapter把list的信息显示到ListView

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (Course course : courses) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("pic", R.drawable.logo);
            item.put("name", course.getName());

            item.put("course", course);
            data.add(item);
        }


        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), data, R.layout.show_course_item, new String[]{"pic", "name"}, new int[]{R.id.tv_cs_image, R.id.tv_cs_name});
        //实现列表的显示
        listview.setAdapter(simpleAdapter);
        //条目点击事件
        listview.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) parent;
        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
        String coursename = data.get("name").toString();

        Course course = (Course) data.get("course");
        Toast.makeText(getActivity(), coursename, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getActivity(), ShowCourseAty.class);
        i.putExtra("course", course);
        startActivity(i);
    }
}
