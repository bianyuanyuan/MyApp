package com.myapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.myapp.Data.Coach;
import com.myapp.atys.BaseActivity;
import com.myapp.atys.ChooseCoachActivity;
import com.myapp.atys.CoachListActivity;
import com.myapp.atys.ShowCoachActivity;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapp.byy.com.myapp.R;

public class MakeDataActiviry extends BaseActivity {
    private ImageView image;
    private DBOpenHelper helper;
    private List<Coach> list;
    private Coach coach;
    private DataDao dao;
    private ListView listview;
    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_make_date);

        //创建或打开数据库
        dao = new DataDao(new DBOpenHelper(this, "manager.db", null, 1));
        initlist();
    }

    private void initlist() {
        listview = (ListView) findViewById(R.id.coachList);

        //获取到集合数据
        final List<Coach> persons = dao.getAllcoachinfo();

   /*     helper= new DBOpenHelper(getActivity(),"manager.db",null,2);

        SQLiteDatabase dd=helper.getReadableDatabase();
        List<Coach> persons =new ArrayList<>();
        //扫描数据库,将数据库信息放入list
           Cursor cursor = dd.rawQuery("select * from coach",null);
          while (cursor.moveToNext()){
              String name = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.NAME));
              int age = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.AGE));
              String sex = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.SEX));
              String phone_number = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.PHONE_NUMBER));
              int teach_year = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.TEACH_YEAR));
              int charge = cursor.getInt(cursor.getColumnIndex(TableContanst.CoachColumns.CHARGE));
              String teach_course = cursor.getString(cursor.getColumnIndex(TableContanst.CoachColumns.TEACH_COURSE));

              Coach coach = new Coach( name, age, sex, phone_number, teach_year, charge, teach_course);
              persons.add(coach);
             }*/
        //获取ListView,并通过Adapter把studentlist的信息显示到ListView

        // List<Coach> persons = DataSupport.findAll(Coach.class);//使用LitePal操作数据库，有问题：每次点击事件会重新建立新的数据


        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (Coach person : persons) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("pic", R.drawable.icon_customer);
            item.put("name", person.getName());
            item.put("course",person.getTeach_course());
            data.add(item);
        }


        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
                R.layout.make_data_item,
                new String[]{"pic", "name"},
                new int[]{R.id.tv_co_image, R.id.tv_co_name});
        //实现列表的显示
        listview.setAdapter(simpleAdapter);
        //条目点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                HashMap<String, Object> d = (HashMap<String, Object>) listView.getItemAtPosition(position);
                String personid = d.get("name").toString();
                String course=d.get("course").toString();
                Toast.makeText(getApplication(), personid, Toast.LENGTH_SHORT).show();

                   Intent i=new Intent(MakeDataActiviry.this,ChooseCoachActivity.class);
                    i.putExtra("name",personid);
                i.putExtra("course",course);
                    startActivity(i);

                //autoStartActivity(CoachListActivity.class);
            }
        });

    }
}
