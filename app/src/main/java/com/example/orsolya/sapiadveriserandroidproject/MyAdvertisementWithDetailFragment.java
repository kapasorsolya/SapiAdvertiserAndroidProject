package com.example.orsolya.sapiadveriserandroidproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.orsolya.sapiadveriserandroidproject.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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
    private ImageButton mDeleteButton;
    private ImageButton mShareButton;
    private String mIdentifier;


    private Advertisement currentAdvertisement;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("advertisment");


    public MyAdvertisementWithDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=  inflater.inflate( R.layout.fragment_my_advertisement_with_detail, container, false );

        //bottomnavigation set invisible
        ((MainActivity)getActivity()).findViewById(R.id.navigation).setVisibility(View.INVISIBLE);

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
        mDeleteButton = view.findViewById(R.id.deleteButton);
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


        // setLISTENERES
        mShareButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_SEND );
                intent.setType( "text/plain" ) ;
                String shareBody = currentAdvertisement.toString();
                String shareSubject = "Your Subject here";
                intent.putExtra( Intent.EXTRA_SUBJECT,shareSubject );
                intent.putExtra( Intent.EXTRA_TEXT,shareBody );
                startActivity( Intent.createChooser( intent,"Share using " ) );
            }
        } );

        mDeleteButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText( getContext(),"clicked on DELETE BUTTON Your item is deleted",Toast.LENGTH_LONG ).show();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new MyAdvertismentFragment();

                DeleteAdvertisement(currentAdvertisement);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace( R.id.fragment_container, myFragment, myFragment.getTag() )
                        .addToBackStack( null )
                        .commit();

            }

            private void DeleteAdvertisement(Advertisement currentAdvertisement) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference ref = database.getReference("advertisement");

                ref.child(currentAdvertisement.getIdentifier()).child("deleted").setValue(true);
            }
        } );

        view.findViewById(R.id.modifyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Toast.makeText(getContext(),
                        "Modify was Successfull", Toast.LENGTH_LONG).show();
            }
        });
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

    private void updateData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref= database.getReference("advertisement/"+mIdentifier);

        String longDescription = mLongDescription.getText().toString();
        String shortDescription = mShortDescription.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String location = mLocation.getText().toString();

        ref.child("longDescription").setValue(longDescription);
        ref.child("shortDescription").setValue(shortDescription);
        ref.child("location").setValue(location);
        ref.child("phoneNumber").setValue(phoneNumber);

    }


}
