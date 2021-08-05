package com.stdev.deketin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.stdev.deketin.adapters.LastVisitedPlacesAdapter;
import com.stdev.deketin.adapters.RecommendedPlacesAdapter;
import com.stdev.deketin.databinding.ActivityMainBinding;
import com.stdev.deketin.dialogs.CategoryBottomSheetDialog;
import com.stdev.deketin.dialogs.UpdateLocationDialog;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.presenters.MainViewPresenterImpl;
import com.stdev.deketin.views.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainView {

    private final int REQUEST_LOCATION_PERMISSION = 1;
    private final int REQUEST_INTERNET_PERMISSION = 2;

    private final List<PlaceModel> recommendedPlaces = new ArrayList<>();
    private final List<PlaceModel> lastVisitedPlaces = new ArrayList<>();
    private static MainViewPresenterImpl mainViewPresenter;
    private RecommendedPlacesAdapter recommendedPlacesAdapter;
    private LastVisitedPlacesAdapter lastVisitedPlacesAdapter;
    private ActivityMainBinding binding;
    private UpdateLocationDialog updateLocationDialog;
    private Location mLastLocation;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // init presenter
        mainViewPresenter = new MainViewPresenterImpl(this);

        // recycler
        recommendedPlacesAdapter = new RecommendedPlacesAdapter(recommendedPlaces);
        lastVisitedPlacesAdapter = new LastVisitedPlacesAdapter(lastVisitedPlaces);
        binding.recommendedPlaceRecycler.setAdapter(recommendedPlacesAdapter);
        binding.lastVisitedPlaceRecycler.setAdapter(lastVisitedPlacesAdapter);


        // event for showing bottom sheet category
        binding.searchNearestPlace.setOnClickListener(showBottomSheet);
        binding.catAll.setOnClickListener(showBottomSheet);

        // event for showing visit history activity
        binding.visitHistory.setOnClickListener(runVisitHistoryActivity);
        binding.visitHistoryMore.setOnClickListener(runVisitHistoryActivity);

        // event for menu
        binding.catHospital.setOnClickListener(placeCategory);
        binding.catRestaurant.setOnClickListener(placeCategory);
        binding.catSchool.setOnClickListener(placeCategory);

        // dialog
        updateLocationDialog = new UpdateLocationDialog(this);
        binding.currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocationDialog.show();
            }
        });

        requestPermission();

        FusedLocationProviderClient flpClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkIfNotPermitted(Manifest.permission.ACCESS_FINE_LOCATION) && !checkIfNotPermitted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            flpClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null) {
                        mLastLocation = location;
                        Locale locale = new Locale("id", "ID");
                        Geocoder geocoder = new Geocoder(MainActivity.this, locale);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            Log.d("addressnow", addresses.get(0).getLocality() + ", " + addresses.get(0).getSubAdminArea());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        mainViewPresenter.load();
    }

    @Override
    public void onLoadRecommendedPlaces(List<PlaceModel> recommendedPlaces) {
        this.recommendedPlaces.clear();
        for(PlaceModel place : recommendedPlaces) {
            Log.d("prerec", place.getPlaceName());
        }
        this.recommendedPlaces.addAll(recommendedPlaces);
        recommendedPlacesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadLastVisitedPlaces(List<PlaceModel> lastVisitedPlaces) {
        this.lastVisitedPlaces.clear();
        this.lastVisitedPlaces.addAll(lastVisitedPlaces);
        lastVisitedPlacesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            //
        }
    }

    private void requestPermission() {

        // permission, request code
        ArrayMap<String, Integer> permissions = new ArrayMap<>();

        permissions.put(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_LOCATION_PERMISSION);
        permissions.put(Manifest.permission.INTERNET, REQUEST_INTERNET_PERMISSION);
        permissions.put(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_LOCATION_PERMISSION);

        for (Map.Entry<String, Integer> entry : permissions.entrySet()) {
            if (checkIfNotPermitted(entry.getKey())) {
                ActivityCompat.requestPermissions(this, new String[]{entry.getKey()}, entry.getValue());
            } else {
                Log.d("permissionsresults", "permissions granted");
            }
        }


    }

    private boolean checkIfNotPermitted(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public ActivityMainBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }

    private final View.OnClickListener showBottomSheet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CategoryBottomSheetDialog dialog = new CategoryBottomSheetDialog();
            dialog.show(getSupportFragmentManager(), "CategoryBottomSheetDialog");
        }
    };

    private final View.OnClickListener runVisitHistoryActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, VisitHistoryActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener placeCategory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);

            if(v.getId() == R.id.catHospital) {
                intent.putExtra("keyword", "Rumah Sakit");
                intent.putExtra("type", "hospital");
            } else if(v.getId() == R.id.catSchool) {
                intent.putExtra("keyword", "Sekolah");
                intent.putExtra("type", "school");
            } else if(v.getId() == R.id.catRestaurant) {
                intent.putExtra("keyword", "Restoran");
                intent.putExtra("type", "restaurant");
            }

            startActivity(intent);
        }
    };
}