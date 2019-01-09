package com.example.orsolya.sapiadveriserandroidproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.orsolya.sapiadveriserandroidproject.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    EditText editTextLastName;
    EditText editTextFirstName;
    EditText editPhoneNumberText;
    EditText editEmailText;
    EditText editAddressText;

    private Uri filePath;
    private  Uri downloadUri;
    private final int PICK_IMAGE_REQUEST_USER = 75;

    private static final String TAG = "MyAdvertisementFragment";

    private CircleImageView profileImage;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_profile, container, false);
        rootview.findViewById( R.id.UserProfileImage ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "Clicked on UserProfileImage", Toast.LENGTH_LONG).show();
                chooseImage();
            }
        } );
        rootview.findViewById(R.id.modifyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Toast.makeText(getContext(),
                        "Modify was Successfull", Toast.LENGTH_LONG).show();
            }
        });

        rootview.findViewById(R.id.signOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFirebaseUser!=null) {
                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(getContext(), SplashScreenActivity.class));
                    getActivity().finish();
                }
            }
        });

        rootview.findViewById(R.id.detailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFirebaseUser!=null) {

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    if(ft!=null)
                    {
                        ft.replace(R.id.fragment_container, new MyAdvertismentFragment());
                        ft.commit();
                    }

                }
            }
        });

        editTextFirstName = rootview.findViewById(R.id.editTextFirstName);
        editTextLastName = rootview.findViewById(R.id.editTextLastName);
        editPhoneNumberText = rootview.findViewById(R.id.editPhoneNumberText);
        editEmailText = rootview.findViewById(R.id.editEmailText);
        editAddressText = rootview.findViewById(R.id.editAddressText);
        profileImage  = rootview.findViewById( R.id.UserProfileImage );


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference myRef = database.getReference("users");



       // Toast.makeText(getContext(),currentFirebaseUser.getPhoneNumber().toString(),Toast.LENGTH_SHORT).show();

        Log.d("pushed",myRef.getKey());

        if(currentFirebaseUser == null){
            Toast.makeText(getContext(), "Kerem jelentkezzen be", Toast.LENGTH_SHORT).show();
        }
        else {
            myRef.child(currentFirebaseUser.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User users = dataSnapshot.getValue(User.class);
                    editTextFirstName.setText(users.getFirstName());
                    Log.d("USER", users.getPhoneNumber().toString());
                    editTextLastName.setText(users.getLastName());
                    Log.d("USER", users.getLastName().toString());
                    editPhoneNumberText.setText(users.getPhoneNumber());
                    Log.d("USER", users.getFirstName().toString());
                    editEmailText.setText(users.getEmail());
                    editAddressText.setText(users.getAddress());


                    Glide.with( getActivity().getBaseContext() )
                            .load(users.getImage())
                            .into( (ImageView) profileImage );
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("tmz", "Failed to read value.", error.toException());
                }
            });
        }

        return rootview;
    }

    private void updateData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef= database.getReference("users");

        String firstNameText = editTextFirstName.getText().toString();
        String lastNameText = editTextLastName.getText().toString();
        String phoneNumberText = editPhoneNumberText.getText().toString();
        String emailText = editEmailText.getText().toString();
        String addressText = editAddressText.getText().toString();

        if (currentFirebaseUser == null) {
            Toast.makeText(getContext(), "Kerem jelentkezzen be", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> userDataUpdates = new HashMap<>();
            userDataUpdates.put(currentFirebaseUser.getPhoneNumber(), new User(firstNameText, lastNameText, phoneNumberText, emailText, addressText));

            usersRef.updateChildren(userDataUpdates);
        }


    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_USER);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback
        super.onActivityResult( requestCode, resultCode, data );
        Log.d( "filepathdataUserProfile", data.toString() + ' ' + data.getData().toString() );
        if (requestCode == PICK_IMAGE_REQUEST_USER && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Glide.with( getActivity().getBaseContext() )
                    .load( filePath.toString() )
                    .into( (ImageView) getView().findViewById( R.id.UserProfileImage ) );
            uploadImage();
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
                                    final DatabaseReference ref = database.getReference("users");

                                    final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;


                                    ref.child(currentFirebaseUser.getPhoneNumber()).child("image").setValue(s);

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


}

