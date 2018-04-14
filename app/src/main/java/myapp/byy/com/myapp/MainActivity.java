package myapp.byy.com.myapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.inspector.protocol.module.Database;
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
import com.myapp.atys.MeActivity;
import com.myapp.atys.StudentListActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends BaseActivity {
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this,LoginActivity2.class);
        startActivity(i);


        finish();//退出

    }*/

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

        Intent i = new Intent(this, LoginActivity2.class);
       startActivity(i);
     //  setContentView(R.layout.activity_main);////////////////////////////

        initView();
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
                    case R.id.rb_table:
                        changeFragment(TableFragment.class.getName());
                        break;
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

    void initView() {

        nickNameText = findViewById(R.id.nav_nickname);
        avatarImage = findViewById(R.id.nav_avatar);
        aCache = ACache.get(MainActivity.this);
    }

    void setListeners() {

        // 监听按下头像事件
        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });

        // 头像工具类回调
        photoUtil.setOnPhotoResultListener(new PhotoUtil.OnPhotoResultListener() {
            // 当选择图片或者拍摄图片拿到结果之后
            @Override
            public void onPhotoResult(final Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    final Bitmap bitmap = PhotoUtil.decodeUriAsBitmap(MainActivity.this, uri);
                    // 上传头像
                    if (UserManager.getCurrentUser().isVisitor()) {// 游客不进行服务器端头像存储
                        avatarImage.setImageBitmap(bitmap);
                        return;
                    }
                    HttpUtil.uploadImage(Consts.URL_UploadImg, bitmap, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            CommonResponse res = new CommonResponse(response.body().string());
                            String resCode = res.getResCode();
                            String resMsg = res.getResMsg();
                            // 上传成功
                            if (resCode.equals(Consts.SUCCESSCODE_UPLOADIMG)) {
                                saveAvatar(bitmap);
                            }
                            showResponse(resMsg);
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            showResponse("Network ERROR!");
                        }
                    });
                }
            }

            @Override
            public void onPhotoCancel() {
            }
        });
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
            } else if (tag.equals(TableFragment.class.getName())) {
                fragment = TableFragment.newInstance();
            } else if (tag.equals(MeFragment.class.getName())) {
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


    void showPhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.uploadAvatar));
        final String[] choices = {getResources().getString(R.string.takePhoto), getResources().getString(R.string.chooseFromGallery)};
        // 设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:// 相机
                        photoUtil.takePicture(MainActivity.this);
                        break;
                    case 1:// 本地相册
                        photoUtil.selectPicture(MainActivity.this);
                        break;
                }
            }
        });
        builder.show();
    }

    private void showResponse(final String msg) {
        Log.e("MainActivity", msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAvatar(final Bitmap bitmap) {
        // 设置头像
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avatarImage.setImageBitmap(bitmap);
            }
        });
        // 保存头像到本地数据库
        User user = UserManager.getCurrentUser();
        user.setAvatarImage(PhotoUtil.bitmap2Bytes(bitmap));
        user.save();
        // 写入缓存
        aCache.put("avatar", bitmap);
    }

    private void loadAvatar(String resCode, String imgStr) {
        if (resCode.equals(Consts.SUCCESSCODE_DOWNLOADIMG) && !imgStr.equals("null") && !imgStr.equals("")) {
//            showResponse("头像："+imgStr);
            // 获取头像成功
            byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    avatarImage.setImageBitmap(bitmap);
                }
            });
        } else {
//            showResponse("使用默认头像");
            // 使用默认头像
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    avatarImage.setImageResource(R.drawable.icon_avatar);
                }
            });
        }
    }

    void initData() {
        User user = UserManager.getCurrentUser();
        // 初始化昵称
        String nickname = user.getNickname();
        if (user.isVisitor()) {
            if (nickname != null) {
                user.setNickname(nickname);
                user.save();
            } else {
                nickname = "游客";
            }
        } else {
            if (nickname != null || !(nickname = server.getNickname()).equals("null")) {
                Log.e("INIT", nickname);
                user.setNickname(nickname);
                user.save();
            } else {
                nickname = "UserName";
            }
        }
        nickNameText.setText(nickname);

        // 加载头像顺序：缓存->本地数据库->服务器
        Bitmap bitmap;
        if ((bitmap = aCache.getAsBitmap("avatar")) == null) {// cache加载失败
            // 数据库加载失败则从服务器加载（仅非游客有效）
            if (user.isVisitor()) {
                if (user.getAvatarImage() != null) {
                    // 数据库加载
                    bitmap = BitmapFactory.decodeByteArray(user.getAvatarImage(), 0,
                            user.getAvatarImage().length);
                    avatarImage.setImageBitmap(bitmap);
                } else {
                    avatarImage.setImageResource(R.drawable.icon_avatar);// 默认头像
                }
                return;
            }
            // 非游客
            if (user.getAvatarImage() == null) {
                CommonRequest request = new CommonRequest();
                request.addRequestParam("account", user.getAccount());
                HttpUtil.sendPost(Consts.URL_DownloadImg, request.getJsonStr(), new okhttp3.Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        CommonResponse res = new CommonResponse(response.body().string());
                        String resCode = res.getResCode();
                        String resMsg = res.getResMsg();
                        HashMap<String, String> property = res.getPropertyMap();
                        String imgStr = property.get("avatar");
                        loadAvatar(resCode, imgStr);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        showResponse("Network ERROR");
                    }
                });
            } else {// 数据库加载
//                showResponse("数据库加载头像");
                bitmap = BitmapFactory.decodeByteArray(user.getAvatarImage(), 0,
                        user.getAvatarImage().length);
                avatarImage.setImageBitmap(bitmap);
            }
        } else {
//            showResponse("缓存加载头像");
            avatarImage.setImageBitmap(bitmap);
        }
    }
}
