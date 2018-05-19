package com.myapp.atys;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.myapp.Data.Class;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import java.util.ArrayList;
import java.util.List;

import myapp.byy.com.myapp.R;

public class ClassListActivity extends ListActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "TestSQLite";
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private List<Long> list;

    private ImageView back;
    private ImageView add;
    private Button searchButton;
    private LinearLayout layout;
    private DataDao dao;
    private Class classroom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_class);////////////////////////
        Log.e(TAG, "onCreate");
        list = new ArrayList<Long>();
        classroom = new Class();

        dao = new DataDao(new DBOpenHelper(this));
        searchButton = (Button) findViewById(R.id.bn_search_id);

        back = (ImageView) findViewById(R.id.back);
        add = (ImageView) findViewById(R.id.add);
        layout = (LinearLayout) findViewById(R.id.showLiner_class);
        listView = getListView();

        // 为按键设置监听
        searchButton.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassListActivity.this, AddClassActivity.class));
            }
        });

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setOnCreateContextMenuListener(this);

    }

    // 调用load()方法将数据库中的所有记录显示在当前页面
    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    public void onClick(View v) {
        if (v == searchButton) {
            // 跳转到查询界面
            startActivity(new Intent(this, ClassSearch.class));
        }
    }

    // 创建菜单
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this); //getMenuInflater();
        inflater.inflate(R.menu.menu_class, menu);
    }

    // 对菜单中的按钮添加响应时间
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        classroom = (Class) listView.getTag();
        Log.v(TAG, "TestSQLite++++class+" + listView.getTag() + "");
        final long class_id = classroom.getId();
        Intent intent = new Intent();
        Log.v(TAG, "TestSQLite+++++++id" + class_id);
        switch (item_id) {
            // 删除
            case R.id.delete:
                deleteClassInformation(class_id);
                break;
            case R.id.look:
                // 查看场地信息
                Log.v(TAG, "TestSQLite+++++++look" + classroom + "");
                intent.putExtra("class", classroom);
                intent.setClass(this, ShowClassActivity.class);
                this.startActivity(intent);
                break;
            case R.id.write:
                // 修改场地信息
                intent.putExtra("class", classroom);
                intent.setClass(this, AddClassActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Class aClass = (Class) dao.getClassFromView(view, id);
        listView.setTag(aClass);
        registerForContextMenu(listView);
        return false;
    }

    // 点击一条记录是触发的事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        classroom = dao.getClassFromView(view, id);
        Log.e(TAG, "classroom*****" + dao.getClassFromView(view, id));
        Intent intent = new Intent();
        intent.putExtra("classroom", classroom);
        intent.setClass(this, ShowClassActivity.class);/////////////something wrong
        this.startActivity(intent);
    }


    // 自定义一个加载数据库中的全部记录到当前页面的无参方法
    public void load() {
        DBOpenHelper DBHelper = new DBOpenHelper(
                ClassListActivity.this);
        SQLiteDatabase database = DBHelper.getWritableDatabase();
        cursor = database.query(TableContanst.CLASS_TABLE, null, null, null,
                null, null, TableContanst.ClassColumns.ID + " desc");
        startManagingCursor(cursor);
        adapter = new SimpleCursorAdapter(this, R.layout.class_list_item,
                cursor, new String[]{TableContanst.ClassColumns.ID,
                TableContanst.ClassColumns.NAME,
                TableContanst.ClassColumns.POSITION,
                TableContanst.ClassColumns.CONTAIN,}, new int[]{
                R.id.tv_cr_id, R.id.tv_cr_name, R.id.tv_cr_position, R.id.tv_cr_contain});
        listView.setAdapter(adapter);
    }


    // 自定义一个利用对话框形式进行数据的删除

    private void deleteClassInformation(final long delete_id) {
        // 利用对话框的形式删除数据
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("场地信息删除")
                .setMessage("确定删除所选记录?")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int raws = dao.deleteClassById(delete_id);
                        layout.setVisibility(View.GONE);

                        load();
                        if (raws > 0) {
                            Toast.makeText(ClassListActivity.this, "删除成功!",
                                    Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(ClassListActivity.this, "删除失败!",
                                    Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}