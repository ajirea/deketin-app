package com.stdev.deketin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.stdev.deketin.adapters.SearchResultsAdapter;
import com.stdev.deketin.adapters.VisitHistoryAdapter;
import com.stdev.deketin.databinding.ActivityPlaceDetailBinding;
import com.stdev.deketin.databinding.ActivityVisitHistoryBinding;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.presenters.SearchResultsPresenterImpl;
import com.stdev.deketin.presenters.VisitHistoryPresenterImpl;
import com.stdev.deketin.views.VisitHistoryView;

import java.util.ArrayList;
import java.util.List;

public class VisitHistoryActivity extends AppCompatActivity implements VisitHistoryView {

    private ActivityVisitHistoryBinding binding;
    private VisitHistoryPresenterImpl presenter;
    private VisitHistoryAdapter adapter;
    private ArrayList<PlaceModel> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityVisitHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new VisitHistoryPresenterImpl(this);

        // recycler
        adapter = new VisitHistoryAdapter(places);
        binding.visitHistoryRecycler.setAdapter(adapter);

        presenter.load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onLoad(List<PlaceModel> visitedPlaces) {
        places.clear();
        places.addAll(visitedPlaces);
        adapter.notifyDataSetChanged();
    }
}