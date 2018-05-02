package com.myapp.atys;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import myapp.byy.com.myapp.R;

public class ClassSearch extends BaseActivity implements View.OnClickListener {
    private EditText nameText;
    private Button button;
    private Button reButton;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private DataDao dao;
    private Button returnButton;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_class);
        nameText = (EditText) findViewById(R.id.et_cr_srarch);
        layout=(LinearLayout) findViewById(R.id.linersearch);
        button = (Button) findViewById(R.id.bn_cr_sure_search);
        reButton = (Button) findViewById(R.id.bn_cr_return);
        listView = (ListView) findViewById(R.id.searchListView);
        returnButton = (Button) findViewById(R.id.return_id);
        dao = new DataDao(new DBOpenHelper(this));


        reButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            reButton.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            nameText.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            String name = nameText.getText().toString();
            cursor = dao.findClass(name);
            if (!cursor.moveToFirst()) {
                Toast.makeText(this, "没有所查场地信息！", Toast.LENGTH_SHORT).show();
            } else
                //如果有所查询的信息，则将查询结果显示出来
                adapter = new SimpleCursorAdapter(this, R.layout.find_class_list_item,
                        cursor, new String[] { TableContanst.ClassColumns.ID,
                        TableContanst.ClassColumns.NAME,
                        TableContanst.ClassColumns.POSITION,
                        TableContanst.ClassColumns.CONTAIN,},
                        new int[] {
                                R.id.tv_cr_id,
                                R.id.tv_cr_name,
                                R.id.tv_cr_position,
                                R.id.tv_cr_contain,});
            listView.setAdapter(adapter);
        }else if(v==reButton|v==returnButton){
            finish();
        }
    }
}