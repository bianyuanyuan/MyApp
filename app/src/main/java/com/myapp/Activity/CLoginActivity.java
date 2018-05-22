package com.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.myapp.BmobData.MyUser;
import com.myapp.BmobData.User;
import com.myapp.Util.CustomDialog;
import com.myapp.Util.ShareUtils;
import com.myapp.Util.StaticClass;
import com.myapp.Util.UtilTools;
import com.myapp.atys.BaseActivity;
import com.myapp.atys.MainCoach;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import myapp.byy.com.myapp.MainActivity;
import myapp.byy.com.myapp.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CLoginActivity extends BaseActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register;
    //登录按钮
    private Button btn_login;
    private EditText et_username,et_password;
    private AppCompatCheckBox keep_login;
    private CustomDialog dialog;

    private ImageView back;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clogin);

        initView();
    }

    private void initView() {

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        keep_login = (AppCompatCheckBox) findViewById(R.id.keep_login);

        back=(ImageView)findViewById(R.id.back);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //参数分别是   上下文，宽，高，内容布局，样式，居中显示，动画样式
        dialog = new CustomDialog(this,WRAP_CONTENT,WRAP_CONTENT,R.layout.dialog_loding,R.style.Theme_Dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态
        boolean isCheck =  ShareUtils.getBoolean(this, StaticClass.SHARE_KEEP_LOGIN,false);
        keep_login.setChecked(isCheck);
        if(isCheck){
            et_username.setText(ShareUtils.getString(this,StaticClass.SHARE_USERNAME,null));
            et_password.setText(ShareUtils.getString(this,StaticClass.SHARE_PASSWORD,null));
        }

    }

    private void keppLogin(){
        //保存状态
        ShareUtils.putBoolean(this,StaticClass.SHARE_KEEP_LOGIN,keep_login.isChecked());

        //是否记住密码
        if(keep_login.isChecked()){
            ShareUtils.putString(this,StaticClass.SHARE_USERNAME,et_username.getText().toString().trim());
            ShareUtils.putString(this,StaticClass.SHARE_PASSWORD,et_password.getText().toString().trim());
        }else {
            ShareUtils.delSingleShare(this,StaticClass.SHARE_USERNAME);
            ShareUtils.delSingleShare(this,StaticClass.SHARE_PASSWORD);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,CRegisterActivity.class));
                break;
            case R.id.btn_login:
                //1.获取数据框的值
                String name = et_username.getText().toString().trim();
                String password =  et_password.getText().toString().trim();
                //登录
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){
                    dialog.show();
                    final User user =  new User();
                    user.setUsername(name);
                    user.setPassword(password);

                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if(e==null){
                                keppLogin();
                            startActivity(new Intent(CLoginActivity.this, MainCoach.class));
                                finish();
                            }else {
                                UtilTools.showShrotToast(getApplicationContext(),"登陆失败");
                            }
                        }
                    });

                }else{
                    UtilTools.showShrotToast(this,"输入框不能为空");
                }

                break;
        }
    }
}
