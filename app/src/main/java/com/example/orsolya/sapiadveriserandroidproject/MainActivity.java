package com.example.orsolya.sapiadveriserandroidproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity  implements  FragmentChangeOnListener {

    private static final String TAG = "MainActivity";
    //private NAaddNewItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //initalize with start fragment
        loadFragment( new ListingAdsFragment() );


       // addNewItem =(MenuItem.)  findViewById( R.id.navigation_add );

        /*addNewItem.
        addNewItem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( "Listing fragment", "clicked on ADD NEW ADVERTISEMENT Button" );
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace( R.id.fragment_container, new AddAdvertisementFragment() )
                        .commit();
            }
        } );*/


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.navigation_add:
                loadFragment( new AddAdvertisementFragment() );
                return true;
            case R.id.navigation_home:
                 return true;
            case R.id.navigation_users:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // ListingAdsFragment hello = new ListingAdsFragment();
        fragmentTransaction.add( R.id.fragment_container, fragment, fragment.toString() );
        fragmentTransaction.commit();
    }

    private void setImage(String imageUrl, String imageName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_name);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}




