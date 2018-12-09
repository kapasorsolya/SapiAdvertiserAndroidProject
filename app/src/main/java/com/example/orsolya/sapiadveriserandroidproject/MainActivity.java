package com.example.orsolya.sapiadveriserandroidproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity  implements FragmentChangeOnListener, BottomNavigationView.OnNavigationItemSelectedListener  {

    private static final String TAG = "MainActivity";

    private  BottomNavigationView mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

         mOnNavigationItemSelectedListener = findViewById( R.id.navigation );
         mOnNavigationItemSelectedListener.setOnNavigationItemSelectedListener(this);

        //initalize with start fragment
        loadFragment( new ListingAdsFragment() );

    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.fragment_container, fragment, fragment.toString() );
        fragmentTransaction.commit();
    }



    private void setImage(Advertisement ad){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_name);
        name.setText(ad.getTitle());

        ImageView image = findViewById(R.id.image);

        Glide.with(this)
                .asBitmap()
                .load(ad.getImage())
                .into(image);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected( item );
    }

    public void displayView(int viewId) {

        Fragment fragment = null;

        switch (viewId) {
            case R.id.navigation_add:
                fragment = new AddAdvertisementFragment();
                Toast.makeText( getBaseContext(),"ADD button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_home:
                fragment = new ListingAdsFragment();
                Toast.makeText( getBaseContext(),"HOME button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_users:
                fragment=new UserProfileFragment();
                Toast.makeText( getBaseContext(),"USERS button",Toast.LENGTH_SHORT).show();
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(ft!=null)
            {
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }

        }

    }
}




