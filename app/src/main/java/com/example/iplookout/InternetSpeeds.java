package com.example.iplookout;

import android.content.Context;

import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iplookout.ui.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class InternetSpeeds extends Fragment {

    TextView textView1;
    TextView textView2;
    public static final String SHARED_PREF_FILE="SpeedsPref";
    public InternetSpeeds() {

    }


    public static InternetSpeeds newInstance() {
        InternetSpeeds fragment = new InternetSpeeds();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_internet_speeds, container, false);
        textView1 = v.findViewById(R.id.tv_mobile);
        textView2 = v.findViewById(R.id.tv_wifi);
        Utils utils = new Utils();
        //SHARED PREFERENCES

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String dateString=formatter.format(date);


        WifiManager wifiMgr = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
         //verific daca este conn la wifi sau mobile
        if (wifiMgr.isWifiEnabled()) {

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo.getNetworkId() == -1) {
                //e conn la wifi
                //SHARED PREFERENCES
                SharedPreferences prefs = getContext().getSharedPreferences(SHARED_PREF_FILE,0);
                String res=utils.getInternetSpeed(getContext());


                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("DATE", dateString);
                editor.putString("CONNECTION", "WiFi");
                editor.putString("SPEED", res);
                editor.apply();



                textView2.setText("\n\n" +utils.getInternetSpeed(getContext()));
                textView1.setText("\n\n Currently on WiFi..." );
            }

        } else
        {
            //SHARED PREFS
            String res=utils.getInternetSpeed(getContext());
            SharedPreferences prefs = getContext().getSharedPreferences(SHARED_PREF_FILE,0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("DATE", dateString);
            editor.putString("CONNECTION", "MOBILE");
            editor.putString("SPEED", res);
            editor.apply();

            textView1.setText("\n\n" + utils.getInternetSpeed(getContext()));
            textView2.setText("\n\n Currently on Mobile..." );

        }

        return v;
    }
}
