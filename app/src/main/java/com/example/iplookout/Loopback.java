package com.example.iplookout;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.iplookout.ui.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.net.io.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.WIFI_SERVICE;


public class Loopback extends Fragment {

    TextView loopBackIP;
    ListView listView;
    TextView textView2;
    String ip;
    public Loopback() {
        // Required empty public constructor
    }
    public static Loopback newInstance() {
        Loopback fragment = new Loopback();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_loopback, container, false);

        //doar adresa IP
        textView2=v.findViewById(R.id.tv_abt);
        loopBackIP=(TextView) v.findViewById(R.id.loopaddress_tv);
        Utils utils=new Utils();
        new GetUrlContentTask().execute("http://checkip.amazonaws.com/");


        //loopBackIP.setText(utils.getLocalIP());
        //extra


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }
    private class GetDataTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String content = "", line;
                while ((line = rd.readLine()) != null) {
                    content += line;
                }

                return content;
            }catch(Exception ex)
            {
                Log.e("async ex",ex.getMessage());
            }


            return "problema mare";

        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            String country=" ";
            String city=" ";
            String longitude=" ";
            String latitude=" ";
            String postal=" ";
            String continent=" ";
            String geoname=" ";

            try{
                ObjectMapper mapper = new ObjectMapper();
                JsonNode ipDeets = mapper.readTree(result);
                country= ipDeets.path("country_name").asText();
                city=ipDeets.path("city").asText();
                postal=ipDeets.path("zip").asText();
                latitude=ipDeets.path("latitude").asText();
                longitude=ipDeets.path("longitude").asText();
                continent=ipDeets.path("continent_name").asText();
                geoname=ipDeets.path("location").path("geoname_id").asText();


                int flagOffset = 0x1F1E6;
                int asciiOffset = 0x41;

                String code= ipDeets.path("country_code").asText();

                int firstChar = Character.codePointAt(code, 0) - asciiOffset + flagOffset;
                int secondChar = Character.codePointAt(code, 1) - asciiOffset + flagOffset;

                String flag = new String(Character.toChars(firstChar))
                        + new String(Character.toChars(secondChar));

                textView2.append(" "+flag);





            } catch(Exception ex)
            {
                Log.e("json parse err",ex.getMessage());
            }


            Utils utils=new Utils();

            //adaugare la lv
            listView=(ListView) getView().findViewById(R.id.details_lv);
            LinkedHashMap<String,String> mapIPProps=new LinkedHashMap<>();
            mapIPProps.put("Internal IP:",utils.getLocalIP());
            mapIPProps.put("Continent:",continent);
            mapIPProps.put("Country:",country);
            mapIPProps.put("City:",city);
            mapIPProps.put("Latitude:",latitude);
            mapIPProps.put("Longitude:",longitude);
            mapIPProps.put("Postal:",postal);
            mapIPProps.put("Geoname ID:",geoname);
            mapIPProps.put(" "," ");
            mapIPProps.put(" "," ");
            mapIPProps.put(" "," ");



            List<HashMap<String,String>>listHash= new ArrayList<>();
            SimpleAdapter adapter=new SimpleAdapter(getContext(),listHash,R.layout.aboutyou_listview,
                    new String[]{"First line","Second line"},new int[]{R.id.listview_mainItem,R.id.listview_subItem});
            Iterator it=mapIPProps.entrySet().iterator();
            while(it.hasNext())
            {
                HashMap<String,String> resultMap=new HashMap<>();
                Map.Entry pair=(Map.Entry)it.next();
                resultMap.put("First line",pair.getKey().toString());
                resultMap.put("Second line",pair.getValue().toString());
                listHash.add(resultMap);
            }
            listView.setAdapter(adapter);


        }
    }
    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String content = "", line;
                while ((line = rd.readLine()) != null) {
                    content += line;
                }





                return content;
            }catch(Exception ex)
            {
                Log.e("async ex",ex.getMessage());
            }

            return "problema mare";

        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            ip=result;
            loopBackIP.setText(result);
            new GetDataTask().execute("http://api.ipstack.com/" + result + "?access_key=23bee54f4476f4702d64c187095196e1","http://checkip.amazonaws.com/");




        }
    }



}
