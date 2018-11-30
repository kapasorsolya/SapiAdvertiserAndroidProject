package com.example.orsolya.sapiadveriserandroidproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class ListingAdsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {


    private  BottomNavigationView mOnNavigationItemSelectedListener;
    private  BottomNavigationView mOnNavigationItemSelectedListener1;


    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public ListingAdsFragment() {
    }

    public  void setImage(String url, String name) {
        mImageUrls.add( url );
        mNames.add( name );

        mImageUrls.add("gs://sapiadveriser.appspot.com/imageName1.jpg");
        mNames.add( "alma" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_advertisment_list, container, false );

        mOnNavigationItemSelectedListener = (BottomNavigationView) rootView.findViewById( R.id.navigation );
        mOnNavigationItemSelectedListener.setOnNavigationItemSelectedListener( this );

        initializeRecyclerView(rootView);

        return rootView;

    }

    private void initializeRecyclerView(View rootView) {
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById( R.id.recyclerv_view );

        // 2. set layoutManger
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        // this is data fro recycler view
        Log.d( TAG, "initImageBitmaps: preparing bitmaps." );

        setImage( "https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg" ,"Havasu Falls" );
        setImage(  "https://i.redd.it/tpsnoz5bzo501.jpg", "Trondheim" );
        setImage( "https://i.redd.it/qn7f9oqu7o501.jpg" , "Portugal" );
        setImage(  "https://i.redd.it/j6myfqglup501.jpg" , "Rocky Mountain National Park" );
        setImage( "https://i.redd.it/0h2gm1ix6p501.jpg" , "Mahahual" );
        setImage( "https://i.redd.it/k98uzl68eh501.jpg" , "Frozen Lake" );
        setImage( "https://i.redd.it/glin0nwndo501.jpg" , "White Sands Desert" );
        setImage( "https://i.redd.it/obx4zydshg601.jpg" , "Austrailia" );
        setImage( "https://i.imgur.com/ZcLLrkY.jpg" , "Washington" );



        // 3. create an adapter
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter( mNames, mImageUrls );
        // 4. set adapter
        recyclerView.setAdapter( mAdapter );
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
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
                Toast.makeText( getContext(),"ADD button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_home:
                fragment = new AddAdvertisementFragment();
                Toast.makeText( getContext(),"HOME button",Toast.LENGTH_SHORT).show();
                 break;
            case R.id.navigation_users:
                fragment=new AddAdvertisementFragment();
                Toast.makeText( getContext(),"USERS button",Toast.LENGTH_SHORT).show();
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            if(ft!=null)
            {
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }

        }




    }
}


