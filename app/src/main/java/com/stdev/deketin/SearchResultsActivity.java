package com.stdev.deketin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.stdev.deketin.adapters.SearchResultsAdapter;
import com.stdev.deketin.databinding.ActivitySearchResultsBinding;
import com.stdev.deketin.dialogs.UpdateLocationDialog;
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
    private UpdateLocationDialog updateLocationDialog;
    private String keyword;
    private String type;

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

        // get extras from previous intent
        Bundle bundleExtras = getIntent().getExtras();
        keyword = bundleExtras.getString("keyword");
        type = bundleExtras.getString("type");

        binding.searchField.setText(keyword);
        binding.getRoot().clearChildFocus(binding.searchField); // remove autofocus after setText

        updateLocationDialog = new UpdateLocationDialog(this);

        searchResultsPresenter = new SearchResultsPresenterImpl(this);
        searchResultsPresenter.setContext(this);

        // show loading
        binding.swipeRefresh.setRefreshing(true);
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchResultsPresenter.load();
            }
        });

        // recycler
        searchResultsAdapter = new SearchResultsAdapter(searchResults);
        binding.searchResultsRecycler.setAdapter(searchResultsAdapter);

        binding.searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(v.getText().toString().trim().length() < 1) return false;
                    if(v.getText().toString().trim().equals(keyword)) return false;

                    keyword = v.getText().toString();
                    type = null;
                    searchResultsPresenter.load();
                    binding.searchResultsRecycler.scrollToPosition(0);
                    return true;
                }
                return false;
            }
        });

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLocation) {
            updateLocationDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ActivitySearchResultsBinding getBinding() {
        return binding;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void onLoad(List<PlaceModel> places) {
        searchResults.clear();
        searchResults.addAll(places);
        searchResultsAdapter.notifyDataSetChanged();
        binding.swipeRefresh.setRefreshing(false);
    }
}