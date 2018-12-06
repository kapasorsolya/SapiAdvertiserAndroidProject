package com.example.orsolya.sapiadveriserandroidproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class ListingAdsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {


    private  BottomNavigationView mOnNavigationItemSelectedListener;

    FirebaseDatabase database ;
    DatabaseReference  myRef;

    ArrayList<Advertisement> list;

    public ListingAdsFragment() {
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

        //getImagesForTheList();


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = new ArrayList<Advertisement>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Advertisement value = dataSnapshot1.getValue(Advertisement.class);
                    Advertisement ads = new Advertisement();
                    String title = value.getTitle();
                  //  mNames.add( title );
                    String image = value.getImage();
                   // ads.setTitle(title);
                    //ads.setImage( (ArrayList<String>) image );
                    //mImageUrls.add(image.get( 0 ));
                   // Log.d("mUmageUrl", mImageUrls.get(mImageUrls.size()));
                    //Log.d("mNames", mNames.get( mNames.size() ));
                    //list.add(ads);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });





        list = new ArrayList<Advertisement>();
        setImages();
        // 3. create an adapter
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter( list);
        // 4. set adapter
        recyclerView.setAdapter( mAdapter );
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator( new DefaultItemAnimator() );

    }

    private void setImages(){
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
        list.add(  new Advertisement(  "Trondheim","https://i.redd.it/tpsnoz5bzo501.jpg"));
    }
    private void getImagesForTheList() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myFirebaseRef = database.getReference( "advertisement" );

        Query queryRef = myFirebaseRef.orderByChild( "image" );


        queryRef.addChildEventListener( new ChildEventListener() {
            String imageUrl = null;
            String Name = null;


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println( dataSnapshot.getValue() );
                Map<String, Object> mapValue = (Map<String, Object>) dataSnapshot.getValue();
/*
                DataSnapshot contactSnapshot = dataSnapshot.child( "advertisement" );
                Iterable<DataSnapshot> contactChildren = contactSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot contact : contactChildren) {
                    Advertisement c = contact.getValue( Advertisement.class );
                    Toast.makeText( getContext(), "advertisement" + i + ' ' + c, Toast.LENGTH_SHORT );

                    setImage( c.getFirstImage(), c.getTitle() );
                    i++;
                }*/

                for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
                    if (entry.getKey().equals( "image" )) {
                        System.out.println( "Key = " + entry.getKey() + ", Value = " + entry.getValue() );

                        System.out.println( "kep:" + entry.getValue().toString() );
                        imageUrl = entry.getValue().toString();
                    }

                    if (entry.getKey().equals( "title" )) {
                        System.out.println( "Title Key = " + entry.getKey() + ", Title Value = " + entry.getValue() );
                        Name = (String) entry.getValue();

                    }


                }
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
                fragment = new ListingAdsFragment();
                Toast.makeText( getContext(),"HOME button",Toast.LENGTH_SHORT).show();
                 break;
            case R.id.navigation_users:
                fragment=new UserProfileFragment();
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


