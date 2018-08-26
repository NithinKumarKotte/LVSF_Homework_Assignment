package com.livesafemobile.android_places_demo_app.services;

import android.util.Log;

import com.livesafemobile.android_places_demo_app.model.Place;
import com.livesafemobile.android_places_demo_app.services.results.GetPlacesResult;

import retrofit.RestAdapter;
import rx.Observable;

public class Api {

    private static final String TAG = Api.class.getSimpleName();

    MyServices myServices;

    //region SETUP
    public Api() {
        RestAdapter restAdapter = buildRestAdapter();
        myServices = restAdapter.create(MyServices.class);
    }

    private RestAdapter buildRestAdapter() {
        return new RestAdapter.Builder().setEndpoint(MyServices.API_URL).setLog(message -> Log.d("TAG", message)).setLogLevel(RestAdapter.LogLevel.FULL).build();
    }
    //endregion

    public Observable<GetPlacesResult> getPlaces() {
        return myServices.getPlaces();
    }

    public Observable<Place> getPlaceDetails(String placeId) {
        return myServices.getPlaceDetails(placeId, MyServices.API_KEY)
                .flatMap(result -> Observable.just(result.place));
    }

}
