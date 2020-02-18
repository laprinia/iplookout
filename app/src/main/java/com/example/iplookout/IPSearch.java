package com.example.iplookout;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.iplookout.ui.Utils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
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


public class IPSearch extends Fragment {
 TextView textView;
 TextView textView2;
 ListView listView;
 String addressPassed;
String localIP="";
Button bttn;
String longitude=" ";
String latitude=" ";

//FIREBASE

DatabaseReference databaseReference;



    public IPSearch() {

        // Required empty public constructor
    }

    public static IPSearch newInstance() {

        IPSearch fragment = new IPSearch();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_ipsearch, container, false);
        //FIREBASE
        databaseReference=FirebaseDatabase.getInstance().getReference();

        //preluare date din Main

        Bundle b=getActivity().getIntent().getExtras();
        addressPassed= b.getString("addr");

        //si setare tv din IPSearch

        textView=v.findViewById(R.id.searched_ip_tv);
        textView.setText(addressPassed);
        textView2=v.findViewById(R.id.looking_tv);
        //prelucrare addressPassed
        String dbloc="GeoLite2-City.mmdb";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(dbloc);
        //bttn
        bttn=v.findViewById(R.id.gmaps_bttn);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude",latitude);
                startActivity(intent);
            }
        });


        //retr
        listView=v.findViewById(R.id.ipsearch_lv);
        Utils utils=new Utils();
          Log.e("addrpassed",addressPassed);
        //http://api.ipstack.com/86.121.208.206?access_key=23bee54f4476f4702d64c187095196e1
        if (addressPassed.equals("localhost")){
            Log.e("altceva","in block de lhost");

            new GetLocalIP().execute("http://checkip.amazonaws.com/");
            Log.e("ceva",localIP);


        }else {
            new GetUrlContentTask().execute("http://api.ipstack.com/" + addressPassed + "?access_key=23bee54f4476f4702d64c187095196e1","http://checkip.amazonaws.com/");

        }


        //lv
        listView=v.findViewById(R.id.ipsearch_lv);


        return v;
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
            String country=" ";
            String city=" ";

            String postal=" ";
            String continent=" ";
            String flag="";
            try{
                ObjectMapper mapper = new ObjectMapper();
                JsonNode ipDeets = mapper.readTree(result);
                country= ipDeets.path("country_name").asText();
                city=ipDeets.path("city").asText();
                postal=ipDeets.path("zip").asText();
                latitude=ipDeets.path("latitude").asText();
                longitude=ipDeets.path("longitude").asText();
                continent=ipDeets.path("continent_name").asText();
                int flagOffset = 0x1F1E6;
                int asciiOffset = 0x41;

                String code= ipDeets.path("country_code").asText();

                int firstChar = Character.codePointAt(code, 0) - asciiOffset + flagOffset;
                int secondChar = Character.codePointAt(code, 1) - asciiOffset + flagOffset;

                flag = new String(Character.toChars(firstChar))
                        + new String(Character.toChars(secondChar));

                textView2.append(" "+flag);





            } catch(Exception ex)
            {
                Log.e("json parse err",ex.getMessage());
            }

             //FIREBASE
            String id=databaseReference.push().getKey();
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            LookoutFirebaseDB db=new LookoutFirebaseDB(id,addressPassed,country,flag,formatter.format(date));
            databaseReference.child(id).setValue(db);

            Toast.makeText(getContext(),"Entry added to firebase with id: "+id, Toast.LENGTH_LONG).show();



            //adaugare la lv
            LinkedHashMap<String,String> mapDetails=new LinkedHashMap<>();
            mapDetails.put("Continent:",continent);
            mapDetails.put("Country:",country);
            mapDetails.put("City:",city);
            mapDetails.put("Lattitude:",latitude);
            mapDetails.put("Longitude:",longitude);
            mapDetails.put("Postal:",postal);

            List<HashMap<String,String>> listHash= new ArrayList<>();
            SimpleAdapter adapter=new SimpleAdapter(getContext(),listHash,R.layout.ipsearch_listview,
                    new String[]{"First line","Second line"},new int[]{R.id.description_lv,R.id.element_lv});
            Iterator it=mapDetails.entrySet().iterator();
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

    private class GetLocalIP extends AsyncTask<String, Integer, String> {
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
            localHandle(result);
            Log.e("demm",result);


        }
    }
    private void localHandle(String val) {
        new GetUrlContentTask().execute("http://api.ipstack.com/"+val+"?access_key=23bee54f4476f4702d64c187095196e1");


    }



}
