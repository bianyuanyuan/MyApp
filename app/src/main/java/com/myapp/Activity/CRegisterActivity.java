package com.myapp.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.myapp.BmobData.User;
import com.myapp.Util.UtilTools;
import com.myapp.atys.BaseActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import myapp.byy.com.myapp.R;

public class CRegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backed;
    private EditText et_user, et_desc, et_pass, et_password;
    private Button btn_reister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cregister);

        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);

        et_desc = (EditText) findViewById(R.id.et_desc);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_reister = (Button) findViewById(R.id.btnRegistered);

        backed = (ImageView) findViewById(R.id.backed);

        backed.setOnClickListener(this);
        btn_reister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backed:
                finish();
                break;//设置返回键监听
            case R.id.btnRegistered:
                //获取用户名， .trim()方法是去空格
                String name = et_user.getText().toString().trim();

                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String passwrod = et_password.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(passwrod)) {
                    //判断密码是否一致
                    if (pass.equals(passwrod)) {
                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = "这个人很懒，什么都没有留下。";
                        }

                        //注册
                        User user = new User();
                        user.setUsername(name);
                        user.setPassword(pass);

                        user.setDesc(desc);

                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User myUser, BmobException e) {
                                if (e == null) {
                                    UtilTools.showShrotToast(getApplicationContext(), "注册成功");
                                    finish();
                                } else {
                                    UtilTools.showShrotToast(getApplicationContext(), "注册失败" + e.toString());
                                }
                            }
                        });
                    } else {
                        et_password.setError("两次输入的密码不一致！");
                    }


                } else {
                    UtilTools.showShrotToast(this, "输入框不能为空！");
                }
                break;
        }

    }
}
