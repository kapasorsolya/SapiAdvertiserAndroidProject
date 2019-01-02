package com.example.orsolya.sapiadveriserandroidproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.orsolya.sapiadveriserandroidproject.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private  List<Advertisement> list;

//    Advertisement currentItem;

    public RecyclerViewAdapter(List<Advertisement> list ) {
        this.list = list;
    }


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
        //currentItem = list.get( position );
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.image);


        holder.imageName.setText(list.get(position).getTitle());
       // holder.imageDescription.setText( list.get( position ).getShortDescription() );
       // holder.longDescription.setText( list.get( position ).getLongDescription() );
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.adImage);
    }


    private	Advertisement getItem(final int position) {
        return list.get(position);
    }


    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        TextView someDetail;
        ImageView adImage;
        //Advertisement ad;


        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            someDetail = itemView.findViewById( R.id.detail );
            adImage=itemView.findViewById( R.id.adimageView );

            itemView.setOnClickListener(this);


        }




        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick " + getPosition() + " " );

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Fragment myFragment = new DetailAdvertisementFragment();

            // building the message that I will send to the DetaulAdvertismentPage
            Bundle bundle = new Bundle();

            bundle.putString("title",list.get(getPosition()).getTitle() );
            bundle.putString("longDescription",list.get( getPosition() ).getLongDescription());
            bundle.putString("shortDescription",list.get( getPosition()).getShortDescription() );
            bundle.putString("phoneNumber",list.get( getPosition()).getPhoneNumber() );
            bundle.putString("location",list.get( getPosition()).getLocation() );

            // set Fragmentclass Arguments
            myFragment.setArguments(bundle);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, myFragment)
                    .addToBackStack(null)
                    .commit();


        }
    }
}
