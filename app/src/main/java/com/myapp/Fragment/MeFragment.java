package com.myapp.Fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.myapp.Data.User;
import com.myapp.Util.ACache;
import com.myapp.Util.ActivityController;
import com.myapp.Util.CommonRequest;
import com.myapp.Util.CommonResponse;
import com.myapp.Util.Consts;
import com.myapp.Util.HttpUtil;
import com.myapp.Util.PhotoUtil;
import com.myapp.Util.Server;
import com.myapp.Util.SharedPreferencesUtil;
import com.myapp.Util.UserManager;
import com.myapp.Util.Util;
import com.myapp.atys.LoginActivity2;
import com.myapp.atys.ModifyPwdActivity;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import myapp.byy.com.myapp.MainActivity;
import myapp.byy.com.myapp.R;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 个人中心
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private CircleImageView avatarImage;
    private TextView accountText;
    private TextView nickname;
    private Button nicknameBtn;
    private Button pwdBtn;

    private Button exitBtn;
    private ACache aCache;
    private PhotoUtil photoUtil = new PhotoUtil();
    private Server server = new Server(getActivity());

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view, inflater, container);
        setListeners();
        initData();
        return view;
    }

    void initView(View view, LayoutInflater inflater, ViewGroup container) {
        aCache = ACache.get(getActivity());

        avatarImage = view.findViewById(R.id.center_avatar);
        accountText = view.findViewById(R.id.center_account);
        nickname=view.findViewById(R.id.nav_nickname);
        nicknameBtn = view.findViewById(R.id.center_nickname_btn);
        pwdBtn = view.findViewById(R.id.center_pwd_btn);
        exitBtn = view.findViewById(R.id.center_exit_btn);
        //   collapsingToolbar = findViewById(R.id.collapsing_toolbar);

    }


    void setListeners() {
        // 更换头像
        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });

        // 修改昵称
        nicknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View nicknameView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edittext, null);
                final EditText nicknameEdit = nicknameView.findViewById(R.id.edit);
                // 弹昵称修改框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(nicknameView);
                builder.setTitle(getResources().getString(R.string.Nickname));
                builder.setNegativeButton(getResources().getString(R.string.cancel), null);
                builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 保存昵称
                        String nickname = nicknameEdit.getText().toString();
                        User user = UserManager.getCurrentUser();
                        user.setNickname(nickname);
                        user.save();
                        if (!user.isVisitor())
                            server.setNickname(nickname);
                        // 更改显示
                        //       collapsingToolbar.setTitle(nickname);
                        getActivity().setTitle(nickname);
                        getActivity().setTitle(nickname);
                        Util.makeToast(getActivity(), getResources().getString(R.string.modify_success));
                        // 通知MainActivity更新昵称
                        Message message = new Message();
                        message.what = 2;
                        message.obj = nickname;
                        //handler.sendMessage(message);
                    }
                });
                builder.show();
            }
        });

        // 修改密码
        pwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = UserManager.getCurrentUser();
                if (!user.isVisitor()) {
                    Intent i = new Intent(getActivity(), ModifyPwdActivity.class);
                    startActivity(i);
                    // autoStartActivity(ModifyPwdActivity.class);
                } else {
                    showResponse("游客不开放此功能");
                }

            }
        });
        // 更换头像
        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });

        // 退出登录
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtil spu = new SharedPreferencesUtil(getActivity());
                spu.setParam("isAutoLogin", false);
                ActivityController.finishAll(LoginActivity2.class);
                ActivityController.clearAcache();
                UserManager.clear();
            }
        });

        // 头像工具类回调
        photoUtil.setOnPhotoResultListener(new PhotoUtil.OnPhotoResultListener() {
            // 当选择图片或者拍摄图片拿到结果之后
            @Override
            public void onPhotoResult(final Uri uri) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    final Bitmap bitmap = PhotoUtil.decodeUriAsBitmap(getActivity(), uri);
                    if (UserManager.getCurrentUser().isVisitor()) {// 游客不进行服务器端头像存储
                        avatarImage.setImageBitmap(bitmap);
                        return;
                    }
                    // 上传头像
                    HttpUtil.uploadImage(Consts.URL_UploadImg, bitmap, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            CommonResponse res = new CommonResponse(response.body().string());
                            String resCode = res.getResCode();
                            String resMsg = res.getResMsg();
                            // 上传成功
                            if (resCode.equals(Consts.SUCCESSCODE_UPLOADIMG)) {
                                saveAvatar(bitmap);
                                // 通知MainActivity更新头像
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                //     handler.sendMessage(msg);
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

    void initData() {

        // 初始化昵称
        User user = UserManager.getCurrentUser();
        if (user.getNickname() != null)
            //    collapsingToolbar.setTitle(user.getNickname());
            getActivity().setTitle(user.getNickname());
        else
            //    collapsingToolbar.setTitle(getResources().getString(R.string.Center));
            getActivity().setTitle(getResources().getString(R.string.Center));
        // 初始化用户名
        accountText.setText(user.getAccount());


        // 初始化头像：缓存->本地数据库->服务器
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
                bitmap = BitmapFactory.decodeByteArray(user.getAvatarImage(), 0,
                        user.getAvatarImage().length);
                avatarImage.setImageBitmap(bitmap);
            }
        } else {
            avatarImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtil.INTENT_CROP:
            case PhotoUtil.INTENT_TAKE:
            case PhotoUtil.INTENT_SELECT:
                photoUtil.onActivityResult(getActivity(), requestCode, resultCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void showPhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("上传头像");
        final String[] choices = {"拍照", "从相册选择"};
        // 设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:// 相机
                        photoUtil.takePicture(getActivity());
                        break;
                    case 1:// 本地相册
                        photoUtil.selectPicture(getActivity());
                        break;
                }
            }
        });
        builder.show();
    }

    private void saveAvatar(final Bitmap bitmap) {
        // 设置头像
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avatarImage.setImageBitmap(bitmap);
            }
        });
        // 保存头像到本地数据库
        User user = UserManager.getCurrentUser();
        user.setAvatarImage(PhotoUtil.bitmap2Bytes(bitmap));
        user.save();
    }

    private void loadAvatar(String resCode, String imgStr) {
        if (resCode.equals(Consts.SUCCESSCODE_DOWNLOADIMG) && !imgStr.equals("null") && !imgStr.equals("")) {
            // 获取头像成功
            byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    avatarImage.setImageBitmap(bitmap);
                }
            });
        } else {
            // 使用默认头像
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    avatarImage.setImageResource(R.drawable.icon_avatar);
                }
            });
        }
    }

    private void showResponse(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //  case R.id.datebutton:
            //      break;
        }
    }

}
