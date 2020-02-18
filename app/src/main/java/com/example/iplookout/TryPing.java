package com.example.iplookout;



import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;


public class TryPing extends Fragment {

TextView textView;
TextView textView2;
Button bttn;

    public TryPing() {

    }
    public static TryPing newInstance() {
        TryPing fragment = new TryPing();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_try_ping, container, false);
        textView=v.findViewById(R.id.sendping_tv);
        textView2=v.findViewById(R.id.tv_ping);

        //setare layout pt tastatura
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //preluare din Main a adresei cautate

        Bundle b=getActivity().getIntent().getExtras();
        String addressPassed= b.getString("addr");
        textView.setText(addressPassed);
        bttn=v.findViewById(R.id.ping_bttn);


        bttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

             new GetUrlContentTask().execute("https://steakovercooked.com/api/ping/?host="+textView.getText());

            }
        });
        //algtm
//



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
                ex.getMessage();
            }

            return "Could not send any packages to address pinged";

        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            result.replaceAll("\"","");
            result.replaceAll("\n","\n\n");
            textView2.setText(result);




        }
    }

}
