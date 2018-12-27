package com.example.orsolya.sapiadveriserandroidproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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





        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.image);


        holder.imageName.setText(list.get(position).getTitle());
        holder.imageDescription.setText( list.get( position ).getShortDescription() );
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.adImage);


    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        TextView imageDescription;
        ImageView adImage;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            imageDescription = itemView.findViewById( R.id.image_title );
            adImage=itemView.findViewById( R.id.adimageView );
        }
    }
}
