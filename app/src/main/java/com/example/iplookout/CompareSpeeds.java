package com.example.iplookout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompareSpeeds extends Fragment {
    public static final String SHARED_PREF_FILE="SpeedsPref";
String date;
String connection;
String speed;
TextView tv;

    public CompareSpeeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View v= inflater.inflate(R.layout.fragment_compare_speeds, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences(
                SHARED_PREF_FILE, Context.MODE_PRIVATE);
        date = prefs.getString("DATE", "");
        connection = prefs.getString("CONNECTION", "");
        speed = prefs.getString("SPEED", "");

        tv=v.findViewById(R.id.tv_compare);
        tv.append("DATE: "+date+" ");
        tv.append("CONNECTION: "+connection+" ");
        tv.append("SPEED: "+speed+" ");
        tv.append("\n");

       return v;
    }

}
