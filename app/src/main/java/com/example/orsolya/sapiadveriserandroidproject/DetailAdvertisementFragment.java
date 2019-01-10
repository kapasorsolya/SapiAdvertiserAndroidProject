package com.example.orsolya.sapiadveriserandroidproject;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.example.orsolya.sapiadveriserandroidproject.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailAdvertisementFragment extends Fragment {

    private TextView mTitle;
    private TextView mLongDescription;
    private TextView mShortDescription;
    private TextView mPhoneNumber;
    private TextView mLocation;
    private ImageView mImage;
    private ImageButton mReportButton;
    private ImageButton mShareButton;
    private String mIdentifier;
    private CircleImageView mUploader;
    private  TextView mCreatorName;
    private static final String TAG = "DetailAdvertisementFragment";

    private Advertisement currentAdvertisement;

    public DetailAdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_detail_advertisement, container, false );

        ((MainActivity)getActivity()).findViewById(R.id.navigation).setVisibility(View.INVISIBLE);

        //get the passed data
        String titleText = getArguments().getString("title");
        String longDescriptionText = getArguments().getString("longDescription");
        String shortDescriptionText = getArguments().getString("shortDescription");
        String locationText = getArguments().getString("location");
        String phoneNumberText = getArguments().getString("phoneNumber");
        String imageNameText = getArguments().getString( "imageName" ) ;
        String uploaderImageText= getArguments().getString( "uploaderImageName");
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
        mUploader = view.findViewById( R.id.userProfileImage2 );
        mCreatorName = view.findViewById( R.id.CreatorTextView );


        updateCreatorField(getArguments().getString("uploaderPhoneNumber"));
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

        //set image
        Glide.with(getContext())
                .asBitmap()
                .load(uploaderImageText)
                .into( (ImageView)mUploader);




        //setListeners
       mReportButton.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("advertisement");

                ref.child(mIdentifier).child("reported").setValue(true);
                currentAdvertisement.setReported( true );

                Toast.makeText( getContext(),"The Advertisement has been reported",Toast.LENGTH_LONG).show();

           }
       } );

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
        // Inflate the layout for this fragment
        return view;
    }

    private void updateCreatorField(String uploaderPhoneNumber) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference( "users/" );

        myRef.child( uploaderPhoneNumber ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue( User.class );

                mCreatorName.setText(user.getFirstName() + ' ' + user.getLastName());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "tmz", "Failed to read value.", error.toException() );
            }
        } );
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



    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).findViewById(R.id.navigation).setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).findViewById(R.id.navigation).setVisibility(View.VISIBLE);
    }


    public static String getTAG() {
        return TAG;
    }
}
