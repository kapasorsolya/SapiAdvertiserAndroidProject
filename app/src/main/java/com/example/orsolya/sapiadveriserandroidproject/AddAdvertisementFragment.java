package com.example.orsolya.sapiadveriserandroidproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;


public class AddAdvertisementFragment extends Fragment {

    private EditText Title;
    private EditText ShortDescription;
    private EditText LongDescription;
    private EditText PhoneNumber;
    private EditText Location;


    //TODO
    //images is meg atvenni es beszurni

    private static final String MY_PREFS_NAME ="AddAdvertisement" ;


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    public AddAdvertisementFragment() {
        // Required empty public constructor
    }

    private void getNewData(View v){

        Title =v.findViewById( R.id.txt_title );
        ShortDescription = v.findViewById( R.id.txt_short_description );
        LongDescription = v.findViewById( R.id.txt_long_description );
        PhoneNumber = v.findViewById( R.id.txt_phone_number );
        Location = v.findViewById( R.id.txt_location_text );

    }

    private void addToSharedPreferences(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("title", Title.getText().toString());
        editor.putString("shortDescription",ShortDescription.getText().toString());
        editor.putString("longDescription",LongDescription.getText().toString());
        editor.putString("phoneNumber",PhoneNumber.getText().toString());
        editor.putString("location",Location.getText().toString());
        editor.apply();
    }

    private void getSharedPreferences() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String titleValue = prefs.getString("title", null);
            String  shortDesciptionValue= prefs.getString("shortDescription", null);
            String longDesciptionVallue=prefs.getString("longDescription", null);
            String phoneNumberValue=prefs.getString("phoneNumber", null);
            String locationValue = prefs.getString( "location",null );

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate( R.layout.fragment_add_advertisement, container, false );
        getNewData( rootView );
        addToSharedPreferences();
        return rootView;

    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
