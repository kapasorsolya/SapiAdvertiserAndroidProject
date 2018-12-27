package com.example.orsolya.sapiadveriserandroidproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.example.orsolya.sapiadveriserandroidproject.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

import static android.support.constraint.Constraints.TAG;

public class AddAdvertisementFragment extends Fragment {

    private static final int RESULT_OK =-1 ;
    private EditText Title;
    private EditText ShortDescription;
    private EditText LongDescription;
    private EditText PhoneNumber;
    private EditText Location;
    private Button AddAdvertisementButton;

    private String mTitle;
    private String  mShortDescription;
    private String mLongDescription;
    private String mPhoneNumber;
    private String mLocation;

    private Button btnChoose;
    private ImageView imageView;

    private Uri filePath;
    private  Uri downloadUri;

    private final int PICK_IMAGE_REQUEST = 71;

    private Advertisement newAd;
    Long PKeyCurrentTime;

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
        btnChoose =  v.findViewById(R.id.btnChoose);
        imageView =  v.findViewById(R.id.imgView);

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
                uploadImage();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                //DatabaseReference newPostRef = ref.push();

               /* newAd=new Advertisement(currentFirebaseUser.getUid(),mLocation,mLongDescription,mShortDescription,
                        mPhoneNumber, false,mTitle,"-",currentFirebaseUser.getUid()  );

                newPostRef.setValue(newAd);*/
                Map<String, Object> adsDataUpdates = new HashMap<>();
                PKeyCurrentTime = System.currentTimeMillis();
                adsDataUpdates.put(PKeyCurrentTime.toString(),new Advertisement(PKeyCurrentTime.toString(),mLocation,mLongDescription,mShortDescription,
                        mPhoneNumber, false,mTitle,"-",currentFirebaseUser.getUid()  ));

                ref.updateChildren(adsDataUpdates);


            }
        } );

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("filepathdata",data.toString() + ' ' + data.getData().toString() );
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



    private void uploadImage() {
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference =  storage.getReferenceFromUrl("gs://sapiadveriser.appspot.com/");;
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child( UUID.randomUUID().toString());
            final StorageReference ref = storageReference.child( UUID.randomUUID().toString());

            ref.putFile(filePath)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   progressDialog.dismiss();
                                   Toast.makeText(getContext(), "Uploaded " + filePath, Toast.LENGTH_SHORT).show();
                                   Log.d(TAG, "onSuccess: uri= "+ uri.toString());
                                   downloadUri = uri;
                                   updateChild(downloadUri.toString());
                               }

                               private void updateChild(final String s) {
                                   final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                   final DatabaseReference ref = database.getReference("advertisement");

                                   final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                                   Log.d("TIMESTAMP----", String.valueOf( PKeyCurrentTime ) );

                                   ref.child(PKeyCurrentTime.toString()).child("image").setValue(s);

                               }
                           });
                       }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });


        }

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
            imageView.getDrawable();
            if(!mTitle.isEmpty() && !mShortDescription.isEmpty() && !mLongDescription.isEmpty() && !mPhoneNumber.isEmpty() &&
                    !mLocation.isEmpty())
            {
                AddAdvertisementButton.setEnabled(true);

            }
        }
    };



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }
}
