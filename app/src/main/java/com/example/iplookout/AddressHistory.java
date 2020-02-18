package com.example.iplookout;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.iplookout.ui.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class AddressHistory extends Fragment {
    ListView listView;

    public AddressHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_address_history, container, false);
       Utils utils=new Utils();
        ImageView iv=(ImageView)v.findViewById(R.id.flag_img);
        ImageView iv2=(ImageView)v.findViewById(R.id.flag_img2);
        Picasso.get()
                .load("https://wikitravel.org/upload/shared//thumb/1/18/Romania_Regions_map.png/449px-Romania_Regions_map.png")
                .into(iv);

        Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/EU-Romania_%28orthographic_projection%29.svg/290px-EU-Romania_%28orthographic_projection%29.svg.png")
                .into(iv2);


//
//        //PARSARE JSON
//        String jsonData=utils.getJsonData(getContext());
//        try{
//            ArrayList<HashMap<String, String>> addressList = new ArrayList<>();
//           ListView lv= v.findViewById(R.id.jsonaddss_lv);
//            JSONObject jObj = new JSONObject(jsonData);
//            JSONArray jsonArry = jObj.getJSONArray("addresses");
//            for(int i=0;i<jsonArry.length();i++) {
//                HashMap<String, String> address = new HashMap<>();
//                JSONObject obj = jsonArry.getJSONObject(i);
//                address.put("ip", obj.getString("ip"));
//                address.put("country", obj.getString("country"));
//                address.put("city", obj.getString("city"));
//                address.put("connectionType", "Mobile");
//                address.put("date", obj.getString("date"));
//                addressList.add(address);
//            }
//
//            SimpleAdapter adapter=new SimpleAdapter(getContext(),addressList,R.layout.addresshistory_layout,
//             new String[]{"ip","country","city","connectionType","date"},
//                    new int[]{R.id.listview_ip,R.id.listview_country,R.id.listview_city,R.id.listview_conntype,R.id.listview_date});
//            lv.setAdapter(adapter);

//
//
//
//        }catch(JSONException ex)
//        {
//           Log.e("eroare json",ex.getMessage());
//        }






     return v;
    }




}
