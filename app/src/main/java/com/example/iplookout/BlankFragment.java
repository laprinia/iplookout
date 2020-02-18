package com.example.iplookout;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }
    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);



        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.blank_frag, new Loopback())
                .commit();


        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.loopback_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
      @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          FragmentManager fragmentManager = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.opt_addr:
                Toast.makeText(getActivity(), "Viewing past IP addresses", Toast.LENGTH_SHORT).show();


                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new AddressHistory())
                        .commit();

                return true;
            case R.id.opt_speeds:
                Toast.makeText(getActivity(), "Monitoring Mobile and WiFi speeds", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new AddressHistory())
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new InternetSpeeds())
                        .commit();

                return true;
            case R.id.opt_compare:
                Toast.makeText(getActivity(), "Comparing past speeds", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new AddressHistory())
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new CompareSpeeds())
                        .commit();

                return true;
            case R.id.opt_roomdb:
                Toast.makeText(getActivity(), "Viewing search history for domains", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new AddressHistory())
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new RoomFragment())
                        .commit();

                return true;
            case R.id.opt_firebase:
                Toast.makeText(getActivity(), "Viewing most accessed Country IP's", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new AddressHistory())
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new FirebaseLookout())
                        .commit();

                return true;
            case R.id.opt_rawfirebase:
                Toast.makeText(getActivity(), "Viewing raw DB", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new AddressHistory())
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.blank_frag, new RawDB())
                        .commit();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
