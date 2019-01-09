package com.example.orsolya.sapiadveriserandroidproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orsolya.sapiadveriserandroidproject.Models.Advertisement;
import com.example.orsolya.sapiadveriserandroidproject.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private  List<Advertisement> list;

    public RecyclerViewAdapter(List<Advertisement> list ) {
        this.list = list;
    }

    public Bundle bundle ;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_advertisment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final Advertisement item = getItem(position);
        Log.d("ADVERTISEMENT ITEM",item.toString());

        getCurrentUploaderImage( list.get(position).getUploaderPhoneNumber(),position,holder );

        holder.advertisementTitle.setText(list.get(position).getTitle());
        holder.counter.setText( String.valueOf(  list.get( position ).getViewersNumber() ));
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.adImage);
    }


    private	Advertisement getItem(final int position) {
        return list.get(position);
    }


    private void getCurrentUploaderImage(String uploaderPhoneNumber, final int position, final ViewHolder holder){
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference myRef = database.getReference("users/");

        myRef.child(uploaderPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User users = dataSnapshot.getValue(User.class);


                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(users.getImage())
                        .into(holder.image);
                bundle=new Bundle();
                bundle.putString( "uploaderImageName", users.getImage() );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tmz", "Failed to read value.", error.toException());
            }
        });



    }
    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView image;
        TextView advertisementTitle;
        RelativeLayout parentLayout;
        TextView someDetail;
        ImageView adImage;
        TextView counter;


        ViewHolder(View itemView) {
            super( itemView );
            image = itemView.findViewById( R.id.image );
            advertisementTitle = itemView.findViewById( R.id.ad_title );
            parentLayout = itemView.findViewById( R.id.parent_layout );
            someDetail = itemView.findViewById( R.id.detail );
            adImage = itemView.findViewById( R.id.adimageView );
            counter = itemView.findViewById( R.id.counter );
            itemView.setOnClickListener( this );


        }


        @Override
        public void onClick(View v) {
            Log.d( TAG, "onClick " + getPosition() + " " );

            updateViewersNumber( list,v );
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment myFragment = new DetailAdvertisementFragment();
            /*if (((AppCompatActivity) v.getContext()).getSupportFragmentManager().getBackStackEntryCount() > 1) {
                Fragment f = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (f instanceof ListingAdsFragment) {
                    // Do something
                    Log.d( TAG,"EZ egy LISTING FRAGMENT" );
                }
                else
                {
                    if (f instanceof MyAdvertismentFragment) {
                        // Do something
                        Log.d( TAG,"EZ egy My LISTING FRAGMENT" );
                    }
                }
            }*/

            // building the message that I will send to the DetailAdvertismentPage

            bundle.putString( "title", list.get( getPosition() ).getTitle() );
            bundle.putString( "longDescription", list.get( getPosition() ).getLongDescription() );
            bundle.putString( "shortDescription", list.get( getPosition() ).getShortDescription() );
            bundle.putString( "phoneNumber", list.get( getPosition() ).getPhoneNumber() );
            bundle.putString( "location", list.get( getPosition() ).getLocation() );
            bundle.putString( "imageName", list.get( getPosition() ).getImage() );
            bundle.putString( "identifier", list.get( getPosition() ).getIdentifier() );


            // set Fragmentclass Arguments
            myFragment.setArguments( bundle );

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.fragment_container, myFragment, myFragment.getTag() )
                    .addToBackStack( null )
                    .commit();


        }
        private void updateViewersNumber(final List<Advertisement> list, View view) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference( "advertisement/" );

            final TextView counter = view.findViewById( R.id.counter );

            myRef.child( list.get( getPosition() ).getIdentifier()).addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Advertisement ad = dataSnapshot.getValue( Advertisement.class );

                    ad.setViewersNumber( ad.getViewersNumber()+1 );

                    int followingViewersNumber=ad.getViewersNumber();
                    myRef.child(ad.getIdentifier()).child("viewersNumber").setValue(followingViewersNumber);
                    CharSequence s =String.valueOf(followingViewersNumber);
                    counter.setText(s );

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w( "tmz", "Failed to read value.", error.toException() );
                }
            } );

        }



    }
}

