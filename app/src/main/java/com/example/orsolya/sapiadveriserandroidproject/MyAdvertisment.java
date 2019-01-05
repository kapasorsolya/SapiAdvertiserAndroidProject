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
import android.widget.Toast;

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
    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    private ArrayList<Advertisement> list;

    public MyAdvertisment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_advertisment_list, container, false );

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

        //database = FirebaseDatabase.getInstance();

        list = new ArrayList<Advertisement>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("advertisement").orderByChild("uploader").equalTo(currentFirebaseUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Advertisement ad = issue.getValue(Advertisement.class);
                        list.add(ad);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //list.clear();

                String  currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Advertisement value = dataSnapshot1.getValue(Advertisement.class);
                    // Advertisement ads = new Advertisement();

                    Log.d("User IDENTIFIER",currentUid);
                   list.add(value);
                    //list.add(value);
                   /* Toast.makeText(getContext(),value.getUploader(),Toast.LENGTH_LONG).show();

                    if("rGLKFX7MTGailZnXpZT06oJHSY52" == value.getUploader())
                    {
                        list.add(value);
                        Log.d("User IDENTIFIER benne", currentUid);
                    }*/
                    /*if(currentUid!=null)
                    {
                        if (value != null && value.getUploader() == FirebaseAuth.getInstance().getCurrentUser().getUid()) {
                            Log.d("User IDENTIFIER", currentUid);
                            list.add(value);
                        }
                    }*/


                /*}

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });

*/
        // 3. create an adapter
        RecyclerViewAdapter mAdapter= new RecyclerViewAdapter( list );

        mAdapter.notifyDataSetChanged();
        // 4. set adapter
        recyclerView.setAdapter( mAdapter );

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator( new DefaultItemAnimator() );



    }





}
