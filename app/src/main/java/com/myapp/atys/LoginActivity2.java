package com.myapp.atys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.Data.User;
import com.myapp.Util.CommonRequest;
import com.myapp.Util.CommonResponse;
import com.myapp.Util.Consts;
import com.myapp.Util.HttpUtil;
import com.myapp.Util.SharedPreferencesUtil;
import com.myapp.Util.UserManager;
import com.myapp.Util.Util;
import com.myapp.dao.DataDao;
import com.myapp.db.DBOpenHelper;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import myapp.byy.com.myapp.MainActivity;
import myapp.byy.com.myapp.R;
import okhttp3.Call;
import okhttp3.Response;

/**
 * login
 */
public class LoginActivity2 extends BaseActivity {
    private ProgressBar progressBar;//进度条
    private Button loginBtn;//登录
    private Button registerBtn;//注册
    private EditText accountText;//账号
    private EditText passwordText;//密码
    private CheckBox isRememberPwd;//记住密码
    private CheckBox isAutoLogin;//自动登录
    private TextView visitorText;//游客登录
    private DataDao dao;//数据库操作

    private RadioButton rbt_user;
    private RadioButton rbt_coach;

    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initComponents();//初始化组件
        setListeners();//设置响应

        /*
        *自动 填充
         */
        SharedPreferencesUtil spu = new SharedPreferencesUtil(this);
        Boolean isRemember = (Boolean) spu.getParam("isRememberPwd", false);
        Boolean isAutoLogin = (Boolean) spu.getParam("isAutoLogin", false);
        // SharedPreference获取用户账号密码，存在则填充
        String account = (String) spu.getParam("account", "");
        String pwd = (String) spu.getParam("pwd", "");
        if (!account.equals("") && !pwd.equals("")) {
            if (isRemember) {
                accountText.setText(account);
                passwordText.setText(pwd);
                isRememberPwd.setChecked(true);
            }
            if (isAutoLogin)
                Login();
        }
    }

    void initComponents() {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        accountText = (EditText) findViewById(R.id.account);
        passwordText = (EditText) findViewById(R.id.password);
        isRememberPwd = (CheckBox) findViewById(R.id.login_remember);
        isAutoLogin = (CheckBox) findViewById(R.id.login_auto);
        visitorText = (TextView) findViewById(R.id.visitor);

        loginBtn = (Button) findViewById(R.id.login);
        registerBtn = (Button) findViewById(R.id.register);

        rbt_user = (RadioButton) findViewById(R.id.rbt_user);
    //    rbt_user.setChecked(true);
        rbt_coach = (RadioButton) findViewById(R.id.rbt_coach);

        dao = new DataDao(new DBOpenHelper(this)); // 设置监听 78
       // LitePal.getDatabase();// 建立数据库

        UserManager.clear();
    }

    void setListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//登录
                Login();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity2.this, RegisterActivity.class);//注册
                startActivity(intent);
            }
        });

        visitorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 若已有游客账号则以游客身份登录，不存在则新建游客账号
                User visitor = DataSupport.where("isVisitor = ?", "1")
                        .findFirst(User.class);
                if (visitor == null) {
                    visitor = new User();
                    visitor.setAccount("Visitor");
                    visitor.setPassword("Visitor");
                    visitor.setVisitor(true);
                    visitor.save();
                }
                UserManager.setCurrentUser(visitor);
                autoStartActivity(MainActivity.class);//////游客进入的活动
            }
        });
    }

    /**
     * POST方式Login
     */
    private void Login() {
        // 创建请求体对象
        CommonRequest request = new CommonRequest();

        // 前端参数校验，防SQL注入
        account = Util.StringHandle(accountText.getText().toString());
        password = Util.StringHandle(passwordText.getText().toString());

        // 检查数据格式是否正确
        String resMsg = checkDataValid(account, password);
        if (!resMsg.equals("")) {
            showResponse(resMsg);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);// 显示进度条
        OptionHandle(account, password);// 处理自动登录及记住密码

        // 填充参数
        request.addRequestParam("account", account);
        request.addRequestParam("pwd", password);


        // POST请求
        HttpUtil.sendPost(Consts.URL_Login, request.getJsonStr(), new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CommonResponse res = new CommonResponse(response.body().string());
                String resCode = res.getResCode();
                String resMsg = res.getResMsg();
                // 登录成功
                if (resCode.equals(Consts.SUCCESSCODE_LOGIN)) {
                    // 查找本地数据库中是否已存在当前用户,不存在则新建用户并写入
                    User user = DataSupport.where("account=?", account).findFirst(User.class);
                    if (user == null) {
                        user = new User();
                        user.setAccount(account);
                        user.setPassword(password);
                        user.setVisitor(false);
                        user.save();
                    }
                    UserManager.setCurrentUser(user);// 设置当前用户
                    if (rbt_coach.isChecked()) {
                        // go(AdminMainActivity.class);
                        autoStartActivity(MainMenuActivity.class);
                    }else if (rbt_user.isChecked()) {
                        //  go(MainActivity.class);
                        autoStartActivity(MainActivity.class);
                    }
                    //    autoStartActivity(MainMenuActivity.class);///////////进入主界面活动//////////////
                }
                showResponse(resMsg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showResponse("Network ERROR");
            }
        });
    }

    private String checkDataValid(String account, String pwd) {
        if (TextUtils.isEmpty(account) | TextUtils.isEmpty(pwd))
            return getResources().getString(R.string.null_hint);//账号或密码不能为空
        if (account.length() != 11 && !account.contains("@"))
            return getResources().getString(R.string.account_invalid_hint);//用户名不是有效的手机号或邮箱
        return "";
    }

    private void showResponse(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity2.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void OptionHandle(String account, String pwd) {
        SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
        SharedPreferencesUtil spu = new SharedPreferencesUtil(this);
        if (isRememberPwd.isChecked()) {
            editor.putBoolean("isRememberPwd", true);
            // 保存账号密码
            spu.setParam("account", account);
            spu.setParam("pwd", pwd);
        } else {
            editor.putBoolean("isRememberPwd", false);
        }
        if (isAutoLogin.isChecked()) {
            editor.putBoolean("isAutoLogin", true);
        } else {
            editor.putBoolean("isAutoLogin", false);
        }
        editor.apply();
    }
}