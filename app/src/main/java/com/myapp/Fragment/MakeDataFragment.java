package com.myapp.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import myapp.byy.com.myapp.R;

public class MakeDataFragment extends Fragment {

    public MakeDataFragment() {

    }

    public static MakeDataFragment newInstance() {
        MakeDataFragment fragment = new MakeDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_make_date, container, false);
    }

}
