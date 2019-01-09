package com.example.orsolya.sapiadveriserandroidproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdvertisementWithDetailFragment extends Fragment {

    private static final String TAG = "MyAdvertisementWithDetailFragment";
    private TextView mTitle;
    private TextView mLongDescription;
    private TextView mShortDescription;
    private TextView mPhoneNumber;
    private TextView mLocation;
    private ImageView mImage;
    private ImageButton mReportButton;
    private ImageButton mShareButton;
    private String mIdentifier;


    private Advertisement currentAdvertisement;


    public MyAdvertisementWithDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=  inflater.inflate( R.layout.fragment_my_advertisement_with_detail, container, false );

        //get the passed data
        String titleText = getArguments().getString("title");
        String longDescriptionText = getArguments().getString("longDescription");
        String shortDescriptionText = getArguments().getString("shortDescription");
        String locationText = getArguments().getString("location");
        String phoneNumberText = getArguments().getString("phoneNumber");
        String imageNameText = getArguments().getString( "imageName" ) ;

        this.mIdentifier = getArguments().getString( "identifier" );

        //find the references
        mTitle = view.findViewById(R.id.TitleView2);
        mLongDescription = view.findViewById( R.id.txt_long_description2 );
        mShortDescription=view.findViewById( R.id.txt_short_description2 );
        mPhoneNumber = view.findViewById( R.id.txt_phone_number2 );
        mLocation = view.findViewById( R.id.txt_location2 );
        mImage = view.findViewById(R.id.adImage);
        mReportButton = view.findViewById(R.id.reportButton);
        mShareButton = view.findViewById( R.id.shareButton );



        //create a new instance and building an advertisement model
        currentAdvertisement = new Advertisement();

        setCurrentAdvertisementItem(titleText,longDescriptionText,shortDescriptionText,locationText,phoneNumberText,imageNameText,mIdentifier);

        //set fields with data
        mTitle.setText( titleText );
        mLongDescription.setText(  longDescriptionText);
        mShortDescription.setText( shortDescriptionText );
        mPhoneNumber.setText( phoneNumberText );
        mLocation.setText( locationText );

        //set image
        Glide.with(getContext())
                .load(imageNameText)
                .into(mImage);


        // Inflate the layout for this fragment
        return view;
    }

    private void setCurrentAdvertisementItem(String titleText, String longDescriptionText, String shortDescriptionText, String locationText,
                                             String phoneNumberText, String imageNameText, String mIdentifier) {

        currentAdvertisement.setImage( imageNameText );
        currentAdvertisement.setTitle( titleText );
        currentAdvertisement.setLongDescription(longDescriptionText);
        currentAdvertisement.setShortDescription( shortDescriptionText );
        currentAdvertisement.setLocation( locationText );
        currentAdvertisement.setPhoneNumber( phoneNumberText );
        currentAdvertisement.setImage( imageNameText );
        currentAdvertisement.setIdentifier(mIdentifier);


    }


    public static String getTAG() {
        return TAG;
    }
}
