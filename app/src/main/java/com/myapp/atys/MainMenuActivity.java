package com.myapp.atys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Util.PublishDialog;

import myapp.byy.com.myapp.R;

public class MainMenuActivity extends BaseActivity {
    LinearLayout llBtnMenu;

    PublishDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("运动帮");
        setContentView(R.layout.main_menu);




        GridView gridview = (GridView) findViewById(R.id.gridview);

        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new RotateAnimation(30, 10);
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new ScaleAnimation(5, 0, 2, 0);
        animation.setDuration(300);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 1);

        gridview.setLayoutAnimation(controller);

        gridview.setAdapter(new ImageAdapter(this));


        //
        llBtnMenu = (LinearLayout) findViewById(R.id.llBtnMenu);
        llBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pDialog == null) {
                    pDialog = new PublishDialog(MainMenuActivity.this);
                    pDialog.setArticleBtnClickListener(new View.OnClickListener() {//新增学员

                        @Override
                        public void onClick(View v) {

                            autoStartActivity(AddStudentActivity.class);
                        }
                    });
                    pDialog.setMiniBlogBtnClickListener(new View.OnClickListener() {//新增教练

                        @Override
                        public void onClick(View v) {
                            autoStartActivity(AddCoachActivity.class);
                        }
                    });
                    pDialog.setPhotoBtnClickListener(new View.OnClickListener() {//新增场地

                        @Override
                        public void onClick(View v) {
                            autoStartActivity(AddClassActivity.class);
                        }
                    });
                    pDialog.setLetterBtnClickListener(new View.OnClickListener() {//新增课程

                        @Override
                        public void onClick(View v) {

                            autoStartActivity(AddCourseActivity.class);
                        }
                    });
                }
                pDialog.show();
            }
        });

    }

    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
        LayoutInflater inflater;
        // 上下文
        private Context mContext;
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.img1,
                R.drawable.coach,
                R.drawable.space,
                R.drawable.pool,
            /*    R.drawable.operateicon,*/
        };
        private String[] menuString = {
                "学员信息管理",
                "教练信息管理",
                "场地信息管理",
                "课程信息管理",
        };

        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }

        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }

        // 当前组件
        public Object getItem(int position) {
            return null;
        }

        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }

        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.gv_item, null);
            TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
            ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);

            tv.setText(menuString[position]);
            iv.setImageResource(mThumbIds[position]);
            switch (position) {
                case 0:
                    // 学员信息管理
                    view.setOnClickListener(studentInfoLinstener);
                    break;
                case 1:
                    // 教练信息管理
                    view.setOnClickListener(coachInfoLinstener);
                    break;
                case 2:
                    //场地信息管理
                    view.setOnClickListener(classInfoLinstener);
                    break;
                case 3:
                    //课程信息管理
                    view.setOnClickListener(courseInfoLinstener);
                    break;
                default:
                    break;
            }
            return view;
        }
    }

    OnClickListener studentInfoLinstener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            // 学员信息管理
            intent.setClass(MainMenuActivity.this, StudentListActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener coachInfoLinstener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            //教练信息管理
            intent.setClass(MainMenuActivity.this, CoachListActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener classInfoLinstener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            // 场地信息管理
            intent.setClass(MainMenuActivity.this, ClassListActivity.class);
            startActivity(intent);
        }
    };
    OnClickListener courseInfoLinstener = new OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            //课程信息管理
            intent.setClass(MainMenuActivity.this, CourseListActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "重新登入");
        menu.add(0, 2, 2, "退出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {//重新登入
            Intent intent = new Intent();
            intent.setClass(MainMenuActivity.this,
                    LoginActivity2.class);
            startActivity(intent);
        } else if (item.getItemId() == 2) {//退出
            System.exit(0);
        }
        return true;
    }
}
