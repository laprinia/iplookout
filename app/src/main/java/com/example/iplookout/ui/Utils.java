package com.example.iplookout.ui;

import android.annotation.TargetApi;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import android.util.Log;


import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Collections;

import java.util.List;

@TargetApi(23)
public class Utils {



    public String getLocalIP()
    {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();

                        boolean isIPv4 = sAddr.indexOf(':')<0;


                            if (isIPv4)
                                return sAddr;


                    }
                }
            }
        } catch (Exception ex) { }
        return "";
    }


    public static String getJsonData(Context context)
    {
        String json;
        try
        {
            InputStream is = context.getAssets().open("addresses.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        Log.e("date json",json);
        return json;
    }

    public String getInternetSpeed(Context context)
    {   String res;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            int downSpeed = nc.getLinkDownstreamBandwidthKbps();

            int upSpeed = nc.getLinkUpstreamBandwidthKbps();


            res = "DOWNLOAD SPEED: "+downSpeed/1000 + " mb/s, UPLOAD SPEED: " + upSpeed/1000+" mb/s";

        }catch(Exception ex)
        {
            res =ex.getMessage();
        }



        return res;
    }
    public boolean writeTables(String data,Context context) {
        boolean bool=false;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("tables.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            bool=true;
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return bool;
    }
    public boolean writeLookup(String data,Context context) {
        boolean bool=false;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("lookup.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            bool=true;
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return bool;
    }

}
