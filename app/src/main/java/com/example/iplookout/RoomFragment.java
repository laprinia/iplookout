package com.example.iplookout;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iplookout.ui.Utils;

import java.util.List;


public class RoomFragment extends Fragment {

    TextView tv;
    Button bttn1;
    Button bttn2;
    DomainDB db;

    public RoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_room, container, false);

       tv=v.findViewById(R.id.tv_room);
       try {String DBres="";

            db = DomainDB.getInstance(getActivity().getApplicationContext());

           List<DomainWithLookups> dlookup=db.getDomainDao().getDomainWithLookups();
           for (DomainWithLookups dl : dlookup)
           {
               tv.append(dl.toString());

           }
           DBres+=tv.getText();
           Utils utils=new Utils();
           if((utils.writeLookup(DBres,getContext()))==true)
           {
               Toast.makeText(getContext(),"Data loaded and saved accordingly!", Toast.LENGTH_LONG).show();
           }




       }catch(Exception ex)
       {
           tv.append("Unable to retrieve now. Use the WhoIs tab first");
       }
        //CLEAR HISTORY BTTN
        bttn1=v.findViewById(R.id.save_bttn);
        bttn2=v.findViewById(R.id.clearhistory_bttn);

        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.getDomainDao().deleteAllLookups();
                db.getDomainDao().deleteAllDomains();
                Toast.makeText(getContext(),"History deleted!", Toast.LENGTH_LONG).show();
                tv.setText("");
            }
        });
        //SAVE BTTN

        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DBres="";


                //SELECT *
           List<Domain> alldomains = db.getDomainDao().getAllDomains();
           tv.setText("\nHERE ARE THE TABLES SAVED:\n");
           tv.append("\n---------------DOMAIN TABLE:\n");
           tv.append("\nDOMAIN NAME===REGISTRAR\n");
           for (Domain d : alldomains) {

               tv.append(d.toString()+"\n");

           }
           tv.append("\n\n");
           tv.append("\n---------DOMAIN LOOKUP TABLE:\n");
                tv.append("\nID=DATE & TIME==DOMAIN NAME\n");
           List<DomainLookup> alldomainlookups = db.getDomainDao().getAllDomainLookups();
           for (DomainLookup d : alldomainlookups) {
               tv.append(d.toString()+"\n");
           }
           DBres+=tv.getText();
           Utils utils=new Utils();
           if(  (utils.writeTables(DBres,getContext()) )==true)
           {
               Toast.makeText(getContext(),"DATA SAVED SUCCESFULLY!", Toast.LENGTH_LONG).show();
           }


            }
        });

       return v;
    }

}
