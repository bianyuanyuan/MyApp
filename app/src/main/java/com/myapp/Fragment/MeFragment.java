package com.myapp.Fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


import com.myapp.Data.User;
import com.myapp.Util.ACache;
import com.myapp.Util.CommonRequest;
import com.myapp.Util.CommonResponse;
import com.myapp.Util.Consts;
import com.myapp.Util.HttpUtil;
import com.myapp.Util.PhotoUtil;
import com.myapp.Util.Server;
import com.myapp.Util.UserManager;

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
public class MeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {


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

        View view = inflater.inflate(R.layout.fragment_me,container,false);


        return view;
       // return inflater.inflate(R.layout.fragment_me, container, false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
          //  case R.id.datebutton:
          //      break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
