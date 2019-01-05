package com.example.orsolya.sapiadveriserandroidproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orsolya.sapiadveriserandroidproject.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    EditText editTextLastName;
    EditText editTextFirstName;
    EditText editPhoneNumberText;
    EditText editEmailText;
    EditText editAddressText;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

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

}

