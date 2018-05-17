package myapp.byy.com.myapp;

import android.app.Application;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        //Stetho.initializeWithDefaults(this);
      //  new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
    }
}
