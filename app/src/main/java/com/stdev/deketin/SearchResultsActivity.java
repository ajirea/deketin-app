package com.stdev.deketin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.stdev.deketin.adapters.SearchResultsAdapter;
import com.stdev.deketin.databinding.ActivitySearchResultsBinding;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.presenters.SearchResultsPresenterImpl;
import com.stdev.deketin.views.SearchResultsView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements SearchResultsView {

    private final List<PlaceModel> searchResults = new ArrayList<>();
    private ActivitySearchResultsBinding binding;
    private SearchResultsPresenterImpl searchResultsPresenter;
    private SearchResultsAdapter searchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivitySearchResultsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        searchResultsPresenter = new SearchResultsPresenterImpl(this);

        // recycler
        searchResultsAdapter = new SearchResultsAdapter(searchResults);
        binding.searchResultsRecycler.setAdapter(searchResultsAdapter);

        searchResultsPresenter.load();
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
    public void onLoad(List<PlaceModel> places) {
        searchResults.clear();
        searchResults.addAll(places);
        searchResultsAdapter.notifyDataSetChanged();
    }
}