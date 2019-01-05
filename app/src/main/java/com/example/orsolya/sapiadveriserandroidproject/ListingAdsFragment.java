package com.example.orsolya.sapiadveriserandroidproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class ListingAdsFragment extends Fragment {


    //private  BottomNavigationView mOnNavigationItemSelectedListener;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ArrayList<Advertisement> list;

    //private TextView someDetail;
    public ListingAdsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_advertisment_list, container, false );

        initializeRecyclerView( rootView );

        return rootView;

    }

    private void initializeRecyclerView(View rootView) {
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById( R.id.recyclerv_view );

        // 2. set layoutManger
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );


        // this is data fro recycler view
        Log.d( TAG, "initImageBitmaps: preparing bitmaps." );

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference( "advertisement" );
        list = new ArrayList<Advertisement>();
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Advertisement value = dataSnapshot1.getValue( Advertisement.class );
                    // Advertisement ads = new Advertisement();
                   /* String title = value.getTitle();
                    String image = value.getImage();
                    String shortDescription = value.getShortDescription();*/
                    list.add( value );

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( "Hello", "Failed to read value.", error.toException() );
            }
        } );


        // 3. create an adapter

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter( list );
        //getImagesForTheList();
        mAdapter.notifyDataSetChanged();
        // 4. set adapter
        recyclerView.setAdapter( mAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator( new DefaultItemAnimator() );


    }
}



