package com.example.orsolya.sapiadveriserandroidproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static com.google.firebase.storage.StorageReference.*;


public class AddAdvertisementFragment extends Fragment {

    private static final int RESULT_OK =1 ;
    private EditText Title;
    private EditText ShortDescription;
    private EditText LongDescription;
    private EditText PhoneNumber;
    private EditText Location;
    private ImageView mImageView;

    private String mTitle;
    private String  mShortDescription;
    private String mLongDescription;
    private String mPhoneNumber;
    private String mLocation;

    private Uri filePath;
    //TODO
    //images name


    private Button AddAdvertisementButton;

    private static final int PICK_IMAGE_REQUEST = 71;




    //TODO
    //images is meg atvenni es beszurni



    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    public AddAdvertisementFragment() {
        // Required empty public constructor
    }

    private void getNewData(View v){

        Title =v.findViewById( R.id.txt_title );
        ShortDescription = v.findViewById( R.id.txt_short_description );
        LongDescription = v.findViewById( R.id.txt_long_description );
        PhoneNumber = v.findViewById( R.id.txt_phone_number );
        Location = v.findViewById( R.id.txt_location_text );
        AddAdvertisementButton = v.findViewById( R.id.add_new_ad_button );
        mImageView = v.findViewById( R.id.imageView );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate( R.layout.fragment_add_advertisement, container, false );
        getNewData( rootView );
        setListeners();
        AddAdvertisementButton.setEnabled(false);
        return rootView;

    }


    public void setListeners(){
        AddAdvertisementButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(),"Beszuras megtortent az adatbazisban",Toast.LENGTH_SHORT ).show();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("advertisement");
                //DatabaseReference newref = database.getReference("sapiadveriser");

                DatabaseReference newPostRef = ref.push();
                ArrayList<String> images = new ArrayList<>( );
                images.add(null);
                Advertisement post=new Advertisement("1",mLocation,mLongDescription,mShortDescription,
                        mPhoneNumber,false,mTitle,images );
                newPostRef.setValue(post);

            }
        } );

        Title.addTextChangedListener(inputTextWatcher);
        PhoneNumber.addTextChangedListener( inputTextWatcher);
        LongDescription.addTextChangedListener( inputTextWatcher);
        Location.addTextChangedListener( inputTextWatcher );
        ShortDescription.addTextChangedListener( inputTextWatcher);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }




    TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
             mTitle=Title.getText().toString();
             mShortDescription=ShortDescription.getText().toString();
             mLongDescription= LongDescription.getText().toString();
             mPhoneNumber=PhoneNumber.getText().toString();
             mLocation=Location.getText().toString();

            if(!mTitle.isEmpty() && !mShortDescription.isEmpty() && !mLongDescription.isEmpty() && !mPhoneNumber.isEmpty() &&
                    !mLocation.isEmpty())
            {
                AddAdvertisementButton.setEnabled(true);

            }
        }
    };



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
