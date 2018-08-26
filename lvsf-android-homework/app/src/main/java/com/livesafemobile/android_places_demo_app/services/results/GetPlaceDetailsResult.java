package com.livesafemobile.android_places_demo_app.services.results;

import com.google.gson.annotations.SerializedName;
import com.livesafemobile.android_places_demo_app.model.Place;

public class GetPlaceDetailsResult {
    @SerializedName("result") public Place place;
}
