package com.example.iplookout;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;



public class RawDB extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    TextView tv;


    public RawDB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_raw_db, container, false);
       tv=v.findViewById(R.id.rawdb_tv);

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectData((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

       return v;
    }


    private void collectData(Map<String,Object> lookups) {
        tv.append("\n\n\n\n");


        Log.e("coll","in collect");

        for (Map.Entry<String, Object> entry : lookups.entrySet()) {


            Map singleLookup = (Map) entry.getValue();
            String lookupIP=(String) singleLookup.get("lookupIP");
            String flagCode=(String) singleLookup.get("flagCode");
            String country=(String) singleLookup.get("country");
            String city=(String) singleLookup.get("lookupDate");

            tv.append(lookupIP+" "+flagCode+" "+country+" "+city+"\n");
        }



    }
}
