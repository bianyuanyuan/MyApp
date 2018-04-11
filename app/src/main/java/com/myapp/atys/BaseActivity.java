package com.myapp.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.myapp.Util.ActivityController;

/**
 * Created by 540 on 2018/4/8.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    void autoStartActivity(Class T) {
        Intent intent = new Intent(this, T);
        startActivity(intent);
    }
}
