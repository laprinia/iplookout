package com.example.iplookout;


import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iplookout.ui.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.whois.WhoisClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class WhoIs extends Fragment {

URL url;
InputStream is;
Button button;
TextView textView;
EditText editText;
ProgressDialog p;
String domainInfo;

    public WhoIs() {
        // Required empty public constructor
    }

    public static WhoIs newInstance() {
        WhoIs fragment = new WhoIs();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_who_is, container, false);
        final Utils utils=new Utils();
        //setare layout pentru tastatura

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        button= v.findViewById(R.id.goto_button);
        textView=v.findViewById(R.id.whois_tv);
        editText=v.findViewById(R.id.website_et);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WhoIsAsyncTask task = new WhoIsAsyncTask();
               task.execute("https://"+editText.getText());
                //ADAUGARE LA ROOM DB A DATELOR CAUTATE CU WHOIS




            }
        });




        return v;
    }
    private class WhoIsAsyncTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getContext());
            p.setMessage("Please wait for info...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.connect();

                String result="Connection estabilished!";

                //Se adauga whoIS folosind libraria de la Apache
                WhoisClient whois;

                whois = new WhoisClient();

                try {
                    whois.connect(WhoisClient.DEFAULT_HOST);
                    String key=(strings[0].split("https://www."))[1];

                    result=(whois.query("=" + key));

                    whois.disconnect();

                } catch(IOException e) {
                    result=("Error I/O exception: " + e.getMessage());

                }

                //de la com.example.iplookout.Domain Status incolo e prohibited si nu obtin info asa ca ii dau split



                return result.split("Domain Status")[0];
            } catch (Exception ex) {
                ex.printStackTrace();
            }
           return"Error accessing the website! Enter another one";
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            p.dismiss();
            textView.setText(string);
            domainInfo=string;
            if(domainInfo!=null)
            {

                //operatii de string
                String name= StringUtils.substringBetween(domainInfo,"Domain Name: ","\n");
                String registrar=StringUtils.substringBetween(domainInfo,"Registrar:","Registrar IANA");



                //op de db
                try {
                    DomainDB db = DomainDB.getInstance(getActivity().getApplicationContext());
                    Domain domain = new Domain(name, registrar);
                      //insert in domain
                    db.getDomainDao().insertDomain(domain);

                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm");
                    Date date = new Date(System.currentTimeMillis());
                    DomainLookup domainLookup=new DomainLookup(formatter.format(date),name);
                    //insert in lookup
                    db.getDomainDao().insertDomainLookups(domainLookup);



                }catch(Exception ex)
                {
                    Toast.makeText(getContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
                }


            }




        }



    }




}
