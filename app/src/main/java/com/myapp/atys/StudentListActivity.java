package com.myapp.atys;

/**
 * Created by 540 on 2018/3/14.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.myapp.Data.Student;
//import AddStudentActivity;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;
import com.myapp.db.TableContanst;

import myapp.byy.com.myapp.R;

public class StudentListActivity extends ListActivity implements
        OnClickListener, OnItemClickListener, OnItemLongClickListener {

    private static final String TAG = "TestSQLite";
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private List<Long> list;
    // private RelativeLayout relativeLayout;
    private Button searchButton;

    private ImageView back;
    private ImageView add;

    private LinearLayout layout;
    private DataDao dao;
    private Student student;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_student);////////////////////////
        Log.e(TAG, "onCreate");
        list = new ArrayList<Long>();
        student = new Student();
        dao = new DataDao(new DBOpenHelper(this));
        searchButton = (Button) findViewById(R.id.bn_search_id);

        back = (ImageView) findViewById(R.id.back);
        add = (ImageView) findViewById(R.id.add);

        layout = (LinearLayout) findViewById(R.id.showLiner);
        //  relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_Class);
        listView = getListView();

        // 为按键设置监听
        searchButton.setOnClickListener(this);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentListActivity.this, AddStudentActivity.class));
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
            startActivity(new Intent(this, StudentSearch.class));
        }

    }

    // 创建菜单
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this); //getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    // 对菜单中的按钮添加响应时间
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        student = (Student) listView.getTag();
        Log.v(TAG, "TestSQLite++++student+" + listView.getTag() + "");
        final long student_id = student.getId();
        Intent intent = new Intent();
        Log.v(TAG, "TestSQLite+++++++id" + student_id);
        switch (item_id) {
            // 删除
            case R.id.delete:
                deleteStudentInformation(student_id);
                break;
            case R.id.look:
                // 查看学生信息
                Log.v(TAG, "TestSQLite+++++++look" + student + "");
                intent.putExtra("student", student);
                intent.setClass(this, ShowStudentActivity.class);
                this.startActivity(intent);
                break;
            case R.id.write:
                // 修改学生信息
                intent.putExtra("student", student);
                intent.setClass(this, AddStudentActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Student student = (Student) dao.getStudentFromView(view, id);
        listView.setTag(student);
        registerForContextMenu(listView);
        return false;
    }

    // 点击一条记录是触发的事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        student = dao.getStudentFromView(view, id);
        Log.e(TAG, "student*****" + dao.getStudentFromView(view, id));
        Intent intent = new Intent();
        intent.putExtra("student", student);
        intent.setClass(this, ShowStudentActivity.class);
        this.startActivity(intent);

    }

    // 自定义一个加载数据库中的全部记录到当前页面的无参方法
    public void load() {
        DBOpenHelper studentDBHelper = new DBOpenHelper(
                StudentListActivity.this);
        SQLiteDatabase database = studentDBHelper.getWritableDatabase();
        cursor = database.query(TableContanst.STUDENT_TABLE, null, null, null,
                null, null, TableContanst.StudentColumns.MODIFY_TIME + " desc");
        startManagingCursor(cursor);
        adapter = new SimpleCursorAdapter(this, R.layout.student_list_item,
                cursor, new String[]{TableContanst.StudentColumns.ID,
                TableContanst.StudentColumns.NAME,
                TableContanst.StudentColumns.AGE,
                TableContanst.StudentColumns.SEX,
                TableContanst.StudentColumns.LIKES,
                TableContanst.StudentColumns.PHONE_NUMBER,
                TableContanst.StudentColumns.TRAIN_DATE}, new int[]{
                R.id.tv_stu_id, R.id.tv_stu_name, R.id.tv_stu_age,
                R.id.tv_stu_sex, R.id.tv_stu_likes, R.id.tv_stu_phone,
                R.id.tv_stu_traindate});
        listView.setAdapter(adapter);
    }


    // 自定义一个利用对话框形式进行数据的删除

    private void deleteStudentInformation(final long delete_id) {
        // 利用对话框的形式删除数据
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("学员信息删除")
                .setMessage("确定删除所选记录?")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int raws = dao.deleteStudentById(delete_id);
                        layout.setVisibility(View.GONE);
                        load();
                        if (raws > 0) {
                            Toast.makeText(StudentListActivity.this, "删除成功!",
                                    Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(StudentListActivity.this, "删除失败!",
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