package com.livesafemobile.android_places_demo_app.model;


import com.google.gson.annotations.SerializedName;
import com.livesafemobile.android_places_demo_app.model.sub.Geometry;

/**
 * Created by jeremyshelly on 4/6/15.
 */
public class Place  {
    public Geometry geometry;
    public String icon;
    @SerializedName("place_id")public String id;
    public String name;
    public Double rating;

    //Changed Serialized name to vicinity to retrieve address values  as it matches with the retrieved JSON content.
    @SerializedName("vicinity") public String address;


    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }


}
