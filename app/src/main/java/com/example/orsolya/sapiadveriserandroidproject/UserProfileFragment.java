package com.example.orsolya.sapiadveriserandroidproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    EditText editTextLastName;
    EditText editTextFirstName;
    EditText editPhoneNumberText;
    EditText editEmailText;
    EditText editAddressText;

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
                //updateData();
                Toast.makeText(getContext(),
                        "Modify was Successfull", Toast.LENGTH_LONG).show();
            }
        });

        editTextFirstName = rootview.findViewById(R.id.editTextFirstName);
        editTextLastName = rootview.findViewById(R.id.editTextLastName);
        editPhoneNumberText = rootview.findViewById(R.id.editPhoneNumberText);
        editEmailText = rootview.findViewById(R.id.editEmailText);
        editAddressText = rootview.findViewById(R.id.editAddressText);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference myRef = database.getReference("users");

        Toast.makeText(getContext(),currentFirebaseUser.getPhoneNumber().toString(),Toast.LENGTH_SHORT).show();

        Log.d("pushed",myRef.getKey());


        myRef.child("-LT44plHbA-Ut54D4HZR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User users = dataSnapshot.getValue(User.class);
                editTextFirstName.setText(users.getFirstName());
                Log.d("USER",users.getPhoneNumber().toString());
                editTextLastName.setText(users.getLastName());
                Log.d("USER",users.getLastName().toString());
                editPhoneNumberText.setText(users.getPhoneNumber());
                Log.d("USER",users.getFirstName().toString());
                editEmailText.setText(users.getEmail());
                editAddressText.setText(users.getAddress());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tmz", "Failed to read value.", error.toException());
            }
        });

        return rootview;
    }

    private void updateData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        String firstNameText = editTextFirstName.getText().toString();
        String lastNameText = editTextLastName.getText().toString();
        String phoneNumberText = editPhoneNumberText.getText().toString();
        String emailText = editEmailText.getText().toString();
        String addressText = editAddressText.getText().toString();

       /* DatabaseReference newPostRef = ref.push();
        // Map<String, User> users = new HashMap<>();
        newPostRef.setValue(new User(firstNameText, lastNameText, phoneNumberText, emailText, addressText));
        //users.put("gracehop", new User("Straff", "Barbara", "+40746581468"));
        newPostRef.push();*/

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getContext(), "Kerem jelentkezzen be", Toast.LENGTH_SHORT).show();
        } else {
            String p = FirebaseAuth.getInstance().getUid();
            String p1 = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            try {
                //ref.;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}

