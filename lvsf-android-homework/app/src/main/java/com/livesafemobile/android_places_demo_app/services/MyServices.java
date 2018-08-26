package com.livesafemobile.android_places_demo_app.services;

import com.livesafemobile.android_places_demo_app.app.App;
import com.livesafemobile.android_places_demo_app.services.results.GetPlaceDetailsResult;
import com.livesafemobile.android_places_demo_app.services.results.GetPlacesResult;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface MyServices {

    public static final String API_URL = "https://maps.googleapis.com/maps/api/place/";
    public static final String API_KEY = "AIzaSyBvdWxikZ09GUYXPG6_4dd6hvqOn5d6lRQ";

    public static final String PLACE_ID = "placeid";

    @GET("/search/json?&location=" + App.MY_LAT + "," + App.MY_LNG + "&radius=10000&types=restaurant&key=" + API_KEY)
    Observable<GetPlacesResult> getPlaces();

    @GET("/details/json")
    Observable<GetPlaceDetailsResult> getPlaceDetails(@Query(PLACE_ID) String placeId, @Query("key") String key);
}
