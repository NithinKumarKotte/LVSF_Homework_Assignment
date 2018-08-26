package com.livesafemobile.android_places_demo_app.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livesafemobile.android_places_demo_app.DetailActivity;
import com.livesafemobile.android_places_demo_app.R;
import com.livesafemobile.android_places_demo_app.databinding.CellPlaceBinding;
import com.livesafemobile.android_places_demo_app.model.Place;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>  {

    private List<Place> placeList;
    private Context mContext;
    private static final String URL_TO_HERE = "http://maps.google.com/maps/m?daddr=%1s,%2s&saddr=%3s,%4s";

    public PlaceAdapter(Context context, List<Place> placeList) {
        this.mContext = context;
        this.placeList = placeList;
    }

    public void setData(List<Place> placeList) {
        this.placeList = placeList;
        this.notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CellPlaceBinding binding;
        public View mView;

        public ViewHolder(View  v) {
            super(v);
            binding = DataBindingUtil.bind(v);
            mView = v;
        }

        public CellPlaceBinding getBinding() {
            return binding;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cell_place, viewGroup, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        //To sort places alphabetically
        Collections.sort(placeList, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Place place = placeList.get(position);
        viewHolder.getBinding().tvName.setText(place.name);
        Double Lat = place.getGeometry().location.lat;
        Double Lng = place.getGeometry().location.lng;
        Picasso.with(mContext).load(place.icon).into(viewHolder.binding.ivIcon);

        //Instantiating the DetailActivity using implicit intent switching and moving values in the form of extras.
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) mContext;
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("place",place.getName());
                intent.putExtra("address",place.getAddress());
                intent.putExtra("Lat",Lat);
                intent.putExtra("Long",Lng);
                (activity).startActivity(intent);

            }
        });

        /*
        * Retrieving Shared preferences values such that if place name is stored , turn particular Cardview to yellow.
        * else keep it transparent
        *
        * */

        SharedPreferences settings = mContext.getSharedPreferences(DetailActivity.MY_GLOBAL_PREFS, 0);
        if(settings.getAll()!=null && settings.contains(place.name)){
           viewHolder.getBinding().card.setCardBackgroundColor(mContext.getResources().getColor(R.color.YellowColor));
        }else{
            viewHolder.getBinding().card.setCardBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
