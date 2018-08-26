package com.livesafemobile.android_places_demo_app;
/*
* The following activity is created to render maps, place name , place address details and also has favorite button feature.
* Favorite button to give user a choice to make a particular restaurant location as their favorite landmark.
* */

import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.livesafemobile.android_places_demo_app.app.Permissions;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    MapView mapView;
    GoogleMap googleMap;
    Bundle bundle;
    String place;
    String placeDet;
    ToggleButton favButton;
    TextView favorite;
    public static final String MY_GLOBAL_PREFS = "my_global_prefs";
    public static final int PERMISSION_CODE = 0;

    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Instantiating Shared preferences
        pref = getApplicationContext().getSharedPreferences(DetailActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);
        editor = pref.edit();

        //Retrieving Intent extras using bundle.
        bundle = getIntent().getExtras();
        place =bundle.getString("place");
        placeDet =bundle.getString("address");

        TextView placeName = findViewById(R.id.placeName);
        placeName.setText(place);
        TextView placeDetails = findViewById(R.id.placeDetails);
        placeDetails.setText(placeDet);
        favorite = findViewById(R.id.fav);
        placeDetails.setText(placeDet);

        favButton = findViewById(R.id.Favorite);
        ratingButton(favButton);

        // to set favorite button with staryellow drawable by checking if place name exists in Shared preferences.
        if(pref.getAll()!=null && pref.contains(place)){
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.staryellow));
        }

       // Triggering mapsView object
        mapView = findViewById(R.id.detail_maps);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    /*
    * to set favorite button with staryellow drawable by checking if place name exists in Shared preferences when resuming the activity.
    * */
    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getAll()!=null && pref.contains(place)){
            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.staryellow));
            favorite.setVisibility(View.VISIBLE);
        }
    }

    /*
    * Method to handle Togglebutton functionality, such that if star toggle button is clicked such that each click should toggle between
    * staryellow and stargrey drawable.
    * */
    public void ratingButton(ToggleButton button){
        button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.stargrey));
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.staryellow));
                    favorite.setVisibility(View.VISIBLE);
                    //commit the place into shared peferences
                    editor.putString(bundle.getString("place"), place);
                    editor.commit();

                } else {
                    button.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.stargrey));
                    favorite.setVisibility(View.GONE);

                    //if place already exists in Shared preferences, remove it
                    if (pref.getString(place, "").equals(place)) {
                        editor.remove(place);
                        editor.commit();
                    }
                }
            }

        });
    }

    /*
    * Following method is for triggering google map and marking the location on it.
    * Positioning the markers with the help of Latitude and Longitude values obtained from bundle
    * Enabling UI in maps for easy user accessibility.
    *
    * */
    @Override
    public void onMapReady(GoogleMap mMap) {

        if (Permissions.hasPermissionInManifest(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            Permissions.request(this,new String[]{ACCESS_FINE_LOCATION},PERMISSION_CODE);

           if(Permissions.areGranted(new int[]{PERMISSION_CODE})){
               googleMap = mMap;
               googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

               try {
                   googleMap.setMyLocationEnabled(true);
               } catch (SecurityException se) {
                          se.getMessage();
               }
               googleMap.setTrafficEnabled(true);
               googleMap.setIndoorEnabled(true);
               googleMap.setBuildingsEnabled(true);
               googleMap.getUiSettings().setZoomControlsEnabled(true);

               googleMap.setMinZoomPreference(15);
               LatLng location = new LatLng(bundle.getDouble("Lat"), bundle.getDouble("Long"));
               Marker marker=googleMap.addMarker(new MarkerOptions()
                       .position(location)
                       .title(bundle.getString("place"))
                       .visible(true));

               marker.showInfoWindow();
               googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
           }
        }
    }
}
