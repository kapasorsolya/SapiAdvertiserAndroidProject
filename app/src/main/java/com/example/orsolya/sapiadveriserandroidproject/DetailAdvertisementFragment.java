package com.example.orsolya.sapiadveriserandroidproject;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailAdvertisementFragment extends Fragment {

    private int currentApiVersion;

    public DetailAdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view=inflater.inflate( R.layout.fragment_detail_advertisement, container, false );
      //  getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_detail_advertisement, container, false );
    }






}
