package com.stdev.deketin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.stdev.deketin.adapters.SearchResultsAdapter;
import com.stdev.deketin.adapters.VisitHistoryAdapter;
import com.stdev.deketin.database.AppDatabase;
import com.stdev.deketin.databinding.ActivityPlaceDetailBinding;
import com.stdev.deketin.databinding.ActivityVisitHistoryBinding;
import com.stdev.deketin.dialogs.UpdateLocationDialog;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;
import com.stdev.deketin.presenters.SearchResultsPresenterImpl;
import com.stdev.deketin.presenters.VisitHistoryPresenterImpl;
import com.stdev.deketin.views.VisitHistoryView;

import java.util.ArrayList;
import java.util.List;

public class VisitHistoryActivity extends AppCompatActivity implements VisitHistoryView {

    private ActivityVisitHistoryBinding binding;
    private VisitHistoryPresenterImpl presenter;
    private VisitHistoryAdapter adapter;
    private UpdateLocationDialog locationDialog;
    private ArrayList<PlaceVisitHistoryModel> places = new ArrayList<>();
    private AppDatabase db;

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

        db = AppDatabase.getDatabase(this);
        presenter = new VisitHistoryPresenterImpl(this, db.visitHistoryDao());
        locationDialog = new UpdateLocationDialog(this);

        // recycler
        adapter = new VisitHistoryAdapter(places);
        binding.visitHistoryRecycler.setAdapter(adapter);

        binding.swipeRefresh.setRefreshing(true);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
            }
        });

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLocation) {
            locationDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStore() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onLoad(List<PlaceVisitHistoryModel> places) {
        this.places.clear();
        this.places.addAll(places);
        adapter.notifyDataSetChanged();
        binding.swipeRefresh.setRefreshing(false);
    }
}