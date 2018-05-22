package myapp.byy.com.myapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.User;
import com.myapp.Fragment.HomeFragment;
import com.myapp.Fragment.MakeDataFragment;
import com.myapp.Fragment.MeFragment;
import com.myapp.Fragment.TableFragment;
import com.myapp.Util.ACache;
import com.myapp.Util.CommonRequest;
import com.myapp.Util.CommonResponse;
import com.myapp.Util.Consts;
import com.myapp.Util.HttpUtil;
import com.myapp.Util.PhotoUtil;
import com.myapp.Util.Server;
import com.myapp.Util.UserManager;
import com.myapp.atys.BaseActivity;
import com.myapp.atys.LoginActivity2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends BaseActivity {


    private RadioGroup mRgTab;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private CircleImageView avatarImage;
    private TextView nickNameText;
    private PhotoUtil photoUtil = new PhotoUtil();
    private Server server = new Server(this);
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  LitePal.getDatabase();//创建"ManaerTest.db"
        //  Intent i = new Intent(this, LoginActivity2.class);//////进入教练端（管理员）///////
        //  startActivity(i);
        setContentView(R.layout.activity_main);////////学员端布局////////////////////

        //  initView();
        //初始化
        // setListeners();
        //   initData();
        mRgTab = (RadioGroup) findViewById(R.id.rg_main);
        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        changeFragment(HomeFragment.class.getName());
                        break;
                    case R.id.rb_make_date:
                        changeFragment(MakeDataFragment.class.getName());
                        break;
                    //      case R.id.rb_table:
                    //          changeFragment(TableFragment.class.getName());
                    //         break;
                    case R.id.rb_me:
                        changeFragment(MeFragment.class.getName());
                        break;
                }
            }
        });
        if (savedInstanceState == null) {
            changeFragment(HomeFragment.class.getName());
        }


    }

    /**
     * show target fragment
     *
     * @param tag
     */
    public void changeFragment(String tag) {
        hideFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            if (tag.equals(HomeFragment.class.getName())) {
                fragment = HomeFragment.newInstance();
            } else if (tag.equals(MakeDataFragment.class.getName())) {
                fragment = MakeDataFragment.newInstance();
            }// else if (tag.equals(TableFragment.class.getName())) {
              //  fragment = TableFragment.newInstance();
           // }
            else if (tag.equals(MeFragment.class.getName())) {
                fragment = MeFragment.newInstance();
            }
            mFragmentList.add(fragment);
            transaction.add(R.id.fl_container, fragment, fragment.getClass().getName());
        }
        transaction.commitAllowingStateLoss();

    }

    /**
     * hide all fragment
     */
    private void hideFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        for (Fragment f : mFragmentList) {
            ft.hide(f);
        }
        ft.commit();
    }
}
