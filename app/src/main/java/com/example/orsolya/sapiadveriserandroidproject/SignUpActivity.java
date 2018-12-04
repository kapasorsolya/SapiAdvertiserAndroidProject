package com.example.orsolya.sapiadveriserandroidproject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends Activity {
     EditText firstName, lastName, phoneNumber;
    FirebaseAuth mAuth;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        phoneNumber = findViewById(R.id.PhoneNumber);
        signUpButton = findViewById(R.id.SignUpButton);

        findViewById(R.id.SignUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingData();
                Toast.makeText(getApplicationContext(),
                        "Registration Successfull", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setEnabled(false);
        firstName.addTextChangedListener(inputTextWatcher);
        lastName.addTextChangedListener(inputTextWatcher);
        phoneNumber.addTextChangedListener(inputTextWatcher);


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
            String firstNameText = firstName.getText().toString();
            String lastNameText = lastName.getText().toString();
            String phoneNumberText = phoneNumber.getText().toString();

            if(!firstNameText.isEmpty() && !lastNameText.isEmpty() && !phoneNumberText.isEmpty())
            {
                signUpButton.setEnabled(true);

            }
        }
    };

    private void savingData(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();
        String phoneNumberText = phoneNumber.getText().toString();

        DatabaseReference newPostRef = ref.push();
       // Map<String, User> users = new HashMap<>();
        newPostRef.setValue(new User(firstNameText, lastNameText, phoneNumberText));
        //users.put("gracehop", new User("Straff", "Barbara", "+40746581468"));
        newPostRef.push();


    }
}
