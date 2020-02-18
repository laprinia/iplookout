package com.example.iplookout;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class FirebaseLookout extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    Button button;



    public FirebaseLookout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_firebase_lookout, container, false);


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
    private ArrayList<String > collectData(Map<String,Object> lookups) {

        ArrayList<String> flags=new ArrayList<>();
     Log.e("coll","in collect");

        for (Map.Entry<String, Object> entry : lookups.entrySet()) {


            Map singleLookup = (Map) entry.getValue();

            flags.add((String) singleLookup.get("flagCode"));
        }

        PieChartView pieChartView=getView().findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();




        HashMap<Integer,String> hm=new HashMap<Integer,String>();
        for(String str:flags)
        {
            hm.put(Collections.frequency(flags,str),str);

        }
        for(Map.Entry m:hm.entrySet())
        {  Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int nr=(int)m.getKey();
            pieData.add(new SliceValue(nr, Color.rgb(r,g,b)).setLabel((String)m.getValue()+": "+nr));

        }
        PieChartData pieChartData= new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);

        return flags;

    }

}
