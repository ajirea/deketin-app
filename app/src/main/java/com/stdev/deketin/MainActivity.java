package com.stdev.deketin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.stdev.deketin.database.AppDatabase;
import com.stdev.deketin.databinding.ActivityMainBinding;
import com.stdev.deketin.dialogs.CategoryBottomSheetDialog;
import com.stdev.deketin.dialogs.UpdateLocationDialog;
import com.stdev.deketin.models.LocationModel;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;
import com.stdev.deketin.presenters.MainViewPresenterImpl;
import com.stdev.deketin.utils.Preferences;
import com.stdev.deketin.utils.UserLocation;
import com.stdev.deketin.views.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainView {

    private final int REQUEST_LOCATION_PERMISSION = 1;

    private final List<PlaceModel> recommendedPlaces = new ArrayList<>();
    private final List<PlaceVisitHistoryModel> lastVisitedPlaces = new ArrayList<>();
    private static MainViewPresenterImpl mainViewPresenter;
    private RecommendedPlacesAdapter recommendedPlacesAdapter;
    private LastVisitedPlacesAdapter lastVisitedPlacesAdapter;
    private ActivityMainBinding binding;
    private UpdateLocationDialog updateLocationDialog;
    private AppDatabase db;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        requestPermission();

        // init
        db = AppDatabase.getDatabase(this);
        mainViewPresenter = new MainViewPresenterImpl(this, db.visitHistoryDao());

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

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewPresenter.load();
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewPresenter.load();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_LOCATION_PERMISSION);
        } else {
            getLastKnownLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        UserLocation.getLastKnownLocation(this, new OnSuccessListener<Address>() {
            @Override
            public void onSuccess(Address address) {
                binding.address
                        .setText(Preferences.getCurrentAddress(MainActivity.this, true));

                // load the data after current location identified
                mainViewPresenter.load();
            }
        });
    }

    @Override
    public void onLoadRecommendedPlaces(List<PlaceModel> recommendedPlaces) {
        this.recommendedPlaces.clear();
        this.recommendedPlaces.addAll(recommendedPlaces);
        recommendedPlacesAdapter.notifyDataSetChanged();

        if(this.recommendedPlaces.size() < 1) {
            binding.recommendedLoadingText.setText(R.string.no_place);
        }
    }

    @Override
    public void onLoadLastVisitedPlaces(List<PlaceVisitHistoryModel> lastVisitedPlaces) {
        this.lastVisitedPlaces.clear();
        this.lastVisitedPlaces.addAll(lastVisitedPlaces);
        lastVisitedPlacesAdapter.notifyDataSetChanged();

        if(this.lastVisitedPlaces.size() < 1) {
            binding.historyLoadingText.setText(R.string.no_place);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            } else {
                requestPermission();
            }
        }
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

            if (v.getId() == R.id.catHospital) {
                intent.putExtra("keyword", "Rumah Sakit");
                intent.putExtra("type", "hospital");
            } else if (v.getId() == R.id.catSchool) {
                intent.putExtra("keyword", "Sekolah");
                intent.putExtra("type", "school");
            } else if (v.getId() == R.id.catRestaurant) {
                intent.putExtra("keyword", "Restoran");
                intent.putExtra("type", "restaurant");
            }

            startActivity(intent);
        }
    };
}