package com.myapp.Activity;


import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.BmobData.MyUser;
import com.myapp.Fragment.MeFragment;
import com.myapp.Util.CameraUtil;
import com.myapp.Util.CustomDialog;
import com.myapp.Util.L;
import com.myapp.Util.StaticClass;
import com.myapp.Util.UtilTools;
import com.myapp.atys.BaseActivity;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import myapp.byy.com.myapp.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class CInfoActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;


    private ImageView back;
    private EditText et_desc;
    private Button btn_update_ok;
    private Button btn_update_no;
    private CircleImageView profile_image;
    private Dialog dialog;
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    public static File tempFile;
    public static final int REQUEST_CODE_WRITE = 9;
    public static final int REQUEST_CODE_CAMERA = 10;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cinfo);
        initView();
    }

    private void initView() {
        btn_exit_user = (Button) findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);


        back = (ImageView) findViewById(R.id.back);
        et_desc = (EditText) findViewById(R.id.et_desc);
        btn_update_ok = (Button) findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);
        btn_update_no = (Button) findViewById(R.id.btn_update_no);
        btn_update_no.setOnClickListener(this);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);


        //参数分别是   上下文，宽，高，内容布局，样式，居中显示，动画样式
        dialog = new CustomDialog(CInfoActivity.this, MATCH_PARENT, MATCH_PARENT, R.layout.dialog_photo, R.style.Theme_Dialog, Gravity.CENTER);
        //屏幕外点击无效
        dialog.setCancelable(false);

        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //设置默认不可以输入
        setEnabled(false);
        //设置具体的值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        if (userInfo != null) {
            et_username.setText(userInfo.getUsername());

            et_desc.setText(userInfo.getDesc());
        }


    }

    //控制是否可以显示
    private void setEnabled(boolean is) {

        et_username.setEnabled(is);

        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(CInfoActivity.this, CLoginActivity.class));
                finish();
                break;
            case R.id.edit_user:
                //设置显示
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                btn_update_no.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //拿到输入框的值
                String username = et_username.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(username)) {
                    //3.更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);

                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没留下！");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                //    btn_update_no.setVisibility(View.GONE);

                                UtilTools.showShrotToast(CInfoActivity.this, "修改成功");

                            } else {
                                UtilTools.showShrotToast(CInfoActivity.this, "修改失败");
                            }
                        }
                    });

                } else {
                    UtilTools.showShrotToast(CInfoActivity.this, "输入框不能为空");
                }
                break;
            case R.id.btn_update_no:
                setEnabled(false);
                btn_update_ok.setVisibility(View.GONE);
                btn_update_no.setVisibility(View.GONE);
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                //相机
                toCamera();
                break;
            case R.id.btn_picture:
                //相册
                toPicture();
                break;
        }
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, StaticClass.PICTURE_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相机
    private void toCamera() {

        permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE);
        CameraUtil.openCamera(CInfoActivity.this);
        dialog.dismiss();
    }

    public void permission(String permision, int code) {
        String[] permissions = {permision};
        //验证是否许可权限
        if (ContextCompat.checkSelfPermission(CInfoActivity.this, permision) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(permissions, code);
            }
        } else {
            if (code == REQUEST_CODE_WRITE) {
                permission(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA);
            }
            if (code == REQUEST_CODE_CAMERA) {
                //申请权限
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //判断内存卡是否可用,可以的话进行储存
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
                startActivityForResult(intent, StaticClass.CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                    permission(Manifest.permission.CAMERA, REQUEST_CODE_CAMERA);
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    UtilTools.showShrotToast(CInfoActivity.this, "没有权限不能打开相机");
                }
                return;
            }

            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //判断内存卡是否可用,可以的话进行储存
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
                    startActivityForResult(intent, StaticClass.CAMERA_REQUEST_CODE);
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    UtilTools.showShrotToast(CInfoActivity.this, "没有权限不能打开相机");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != this.RESULT_CANCELED) {
            switch (requestCode) {
                //相册
                case StaticClass.PICTURE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机
                case StaticClass.CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME);
                    L.e(Uri.fromFile(tempFile).toString());
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case StaticClass.RESULT_REQUEST_CODE:
                    //有可能舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //设置了图片，就删除原先的
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;

            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //宽高
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, StaticClass.RESULT_REQUEST_CODE);
    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(CInfoActivity.this, profile_image);

    }

}
