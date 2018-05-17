package com.myapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;

import myapp.byy.com.myapp.R;

@SuppressLint("NewApi")
public class TableFragment extends Fragment implements View.OnClickListener {

    private DBOpenHelper helper;


    public TableFragment() {
    }

    public static TableFragment newInstance() {
        TableFragment fragment = new TableFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        //创建或打开数据库
        helper = new DBOpenHelper(getActivity(), "manager.db", null, 1);
        showRecord(view);

        return view;
    }


    void showRecord(View view) {

        //   ((EditText) view.findViewById(R.id.et_jiaolian)).setText();
        // ((EditText) view.findViewById(R.id.et_shijian)).setText();
        //((EditText) view.findViewById(R.id.et_kecheng)).setText();
        // ((EditText) view.findViewById(R.id.et_changdi)).setText();
    }

    @Override
    public void onClick(View v) {
    }
}

