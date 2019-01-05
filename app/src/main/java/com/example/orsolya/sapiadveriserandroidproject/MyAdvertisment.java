package com.example.orsolya.sapiadveriserandroidproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdvertisment extends Fragment {

    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    private ArrayList<Advertisement> list;

    public MyAdvertisment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_my__advertisment, container, false );

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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("advertisement");
        list = new ArrayList<Advertisement>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Advertisement value = dataSnapshot1.getValue(Advertisement.class);
                    // Advertisement ads = new Advertisement();
                    //if(value.getUploader() == currentFirebaseUser.getUid()) {
                        String title = value.getTitle();
                        String image = value.getImage();
                        String shortDescription = value.getShortDescription();
                       // Log.d("ImageUrl", image);
                       // Log.d("ImageTitle", title);
                        list.add(new Advertisement(title, image, shortDescription));
                   // }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });


        // 3. create an adapter
        RecyclerViewAdapter mAdapter= new RecyclerViewAdapter( list );

        mAdapter.notifyDataSetChanged();
        // 4. set adapter
        recyclerView.setAdapter( mAdapter );

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator( new DefaultItemAnimator() );



    }


    private void getImagesForTheList() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myFirebaseRef = database.getReference( "advertisement" );

        Query queryRef = myFirebaseRef.orderByChild( "advertisement" );


        queryRef.addChildEventListener( new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                Advertisement ad = dataSnapshot.getValue( Advertisement.class );
               // if(ad.getUploader() == currentFirebaseUser.getUid()) {

                    list.add(ad);
                //}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }






}
