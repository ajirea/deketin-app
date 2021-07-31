package com.stdev.deketin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.stdev.deketin.adapters.LastVisitedPlacesAdapter;
import com.stdev.deketin.adapters.RecommendedPlacesAdapter;
import com.stdev.deketin.databinding.ActivityMainBinding;
import com.stdev.deketin.dialogs.CategoryBottomSheetDialog;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.presenters.MainViewPresenterImpl;
import com.stdev.deketin.views.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private final List<PlaceModel> recommendedPlaces = new ArrayList<>();
    private final List<PlaceModel> lastVisitedPlaces = new ArrayList<>();
    private static MainViewPresenterImpl mainViewPresenter;
    private RecommendedPlacesAdapter recommendedPlacesAdapter;
    private LastVisitedPlacesAdapter lastVisitedPlacesAdapter;
    private ActivityMainBinding binding;

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

        binding.visitHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VisitHistoryActivity.class);
                startActivity(intent);
            }
        });

        mainViewPresenter.load();
    }

    @Override
    public void onLoad(List<PlaceModel> recommendedPlaces, List<PlaceModel> lastVisitedPlaces) {
        this.recommendedPlaces.clear();
        this.lastVisitedPlaces.clear();
        this.recommendedPlaces.addAll(recommendedPlaces);
        this.lastVisitedPlaces.addAll(lastVisitedPlaces);
        recommendedPlacesAdapter.notifyDataSetChanged();
        lastVisitedPlacesAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener showBottomSheet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CategoryBottomSheetDialog dialog = new CategoryBottomSheetDialog();
            dialog.show(getSupportFragmentManager(), "CategoryBottomSheetDialog");
        }
    };
}