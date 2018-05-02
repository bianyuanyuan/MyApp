package com.myapp.atys;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.myapp.Fragment.MakeDataActiviry;

import myapp.byy.com.myapp.R;

public class WelcomeAcitvity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
              // startActivity(new Intent(getApplicationContext(), MakeDataActiviry.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

}
