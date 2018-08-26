package com.livesafemobile.android_places_demo_app;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import com.livesafemobile.android_places_demo_app.adapter.PlaceAdapter;
import com.livesafemobile.android_places_demo_app.app.App;
import com.livesafemobile.android_places_demo_app.databinding.ActivityPlacesListBinding;
import com.livesafemobile.android_places_demo_app.services.results.GetPlacesResult;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class PlacesListActivity extends AppCompatActivity{
    public static final String TAG = PlacesListActivity.class.getSimpleName();

    PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPlacesListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_places_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvPlaces.setLayoutManager(layoutManager);

        placeAdapter = new PlaceAdapter(this, new ArrayList<>());
        binding.rvPlaces.setAdapter(placeAdapter);
        getPlaces();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPlaces() {
        App.getInstance().api.getPlaces().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GetPlacesResult>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "Completed in main.");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error in main.", e);

            }

            @Override
            public void onNext(GetPlacesResult getPlacesResult) {
                Log.i(TAG, "Next in main: " + getPlacesResult.results.size());
                placeAdapter.setData(getPlacesResult.results);
            }
        });

        }
/*
* Repopulate places when activity is resumed
* */
    @Override
    protected void onResume() {
        super.onResume();
        getPlaces();
    }
}
