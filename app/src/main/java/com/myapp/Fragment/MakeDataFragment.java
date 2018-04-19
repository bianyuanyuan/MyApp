package com.myapp.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.Coach;
import com.myapp.atys.AddCoachActivity;
import com.myapp.atys.ShowCoachActivity;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapp.byy.com.myapp.R;

public class MakeDataFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,AbsListView.OnScrollListener{

    private ImageView image;
    private boolean temp=true;  //
    View view2;
    private List<Coach> list;
    private DataDao dao;
    private Coach coach;
    private Long coach_id;
    private String type;
    private Map<String, String> getNumberInfo = new HashMap<String, String>();
    private Map<String, String> getCoachInfo = new HashMap<String, String>();
    private ListView listview;
    private List<Map<String, Object>> dataList;

    public MakeDataFragment() {
    }

    public static MakeDataFragment newInstance() {
        MakeDataFragment fragment = new MakeDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_make_date,container,false);
        view2 = inflater.inflate(R.layout.fragment_make_date, null);

        initlist(view,inflater, container);

        return view;
       // return inflater.inflate(R.layout.fragment_make_date, container, false);
    }



    private void initlist(View view,LayoutInflater inflater,ViewGroup container) {
          listview = (ListView) view.findViewById(R.id.coachList);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.make_data_item, new String[]{"pic", "text"}, new int[]{R.id.tv_co_image, R.id.tv_co_name});
        listview.setAdapter(simpleAdapter);

        listview.setOnItemClickListener(this);
        listview.setOnScrollListener(this);
    }

    private List<? extends Map<String,?>> getData() {

      dataList = new ArrayList<Map<String, Object>>();
        coach= new Coach(1, "李永",31,"男","12011441144",2,200,"游泳");
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pic", R.drawable.icon_customer);
            map.put("text", coach.getName());
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account:
                Data();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
               Toast.makeText(getActivity(), "进入个人中心", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("coach", coach);
                intent.setClass(getActivity(), ShowCoachActivity.class);
                this.startActivity(intent);
                break;
            case 1:
              //  Toast.makeText(getActivity(), "进行宝贝中心", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void Data() {
        if(temp) {
            image.setSelected(temp);
            temp =false;
        }
        else{
            image.setSelected(temp);
            temp = true;
        }
    }
}
