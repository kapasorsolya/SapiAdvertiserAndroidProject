package com.example.orsolya.sapiadveriserandroidproject;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailAdvertisementFragment extends Fragment {

    private TextView mTitle;
    private TextView mLongDescription;
    private TextView mShortDescription;
    private TextView mPhoneNumber;
    private TextView mLocation;



    public DetailAdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_detail_advertisement, container, false );

        //get the passed data
        String titleText = getArguments().getString("title");
        String longDescriptionText = getArguments().getString("longDescription");
        String shortDescriptionText = getArguments().getString("shortDescription");
        String locationText = getArguments().getString("location");
        String phoneNumberText = getArguments().getString("phoneNumber");


        //find the references
        mTitle = view.findViewById(R.id.TitleView2);
        mLongDescription = view.findViewById( R.id.txt_long_description2 );
        mShortDescription=view.findViewById( R.id.txt_short_description2 );
        mPhoneNumber = view.findViewById( R.id.txt_phone_number2 );
        mLocation = view.findViewById( R.id.txt_location2 );


        //set fields with data
        mTitle.setText( titleText );
        mLongDescription.setText(  longDescriptionText);
        mShortDescription.setText( shortDescriptionText );
        mPhoneNumber.setText( phoneNumberText );
        mLocation.setText( locationText );
        // Inflate the layout for this fragment
        return view;
    }






}
