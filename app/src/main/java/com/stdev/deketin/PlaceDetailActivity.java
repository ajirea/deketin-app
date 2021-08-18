package com.stdev.deketin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stdev.deketin.adapters.JadwalListAdapter;
import com.stdev.deketin.adapters.PhotosAdapter;
import com.stdev.deketin.database.AppDatabase;
import com.stdev.deketin.database.dao.VisitHistoryDao;
import com.stdev.deketin.databinding.ActivityPlaceDetailBinding;
import com.stdev.deketin.models.JadwalModel;
import com.stdev.deketin.models.OpeningHourModel;
import com.stdev.deketin.models.PhotoModel;
import com.stdev.deketin.models.PlaceModel;
import com.stdev.deketin.models.PlaceVisitHistoryModel;
import com.stdev.deketin.presenters.PlaceDetailPresenterImpl;
import com.stdev.deketin.views.PlaceDetailView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlaceDetailActivity extends AppCompatActivity implements PlaceDetailView {
    private ActivityPlaceDetailBinding binding;
    private PlaceDetailPresenterImpl presenter;
    private PhotosAdapter photosAdapter;
    private JadwalListAdapter jadwalAdapter;
    private AppDatabase db;

    private ArrayList<String> jadwal = new ArrayList<>();
    private ArrayList<String> photos = new ArrayList<>();

    private String placeId;
    private String placeName;
    private String placePhotoUrl;
    private PlaceModel place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityPlaceDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = AppDatabase.getDatabase(this);
        final VisitHistoryDao visitHistoryDao = db.visitHistoryDao();

        Bundle bundle = getIntent().getExtras();
        placeId = bundle.getString("place_id");
        placeName = bundle.getString("place_name");
        placePhotoUrl = bundle.getString("place_photo_url");

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(placeName);

            if (placePhotoUrl != null) {
                Picasso.get()
                        .load(placePhotoUrl)
                        .placeholder(R.color.orange_500)
                        .into(binding.placePhoto);
            } else {
                binding.placePhoto.setImageResource(R.drawable.bg_photo_default);
            }
        }

        presenter = new PlaceDetailPresenterImpl(this);

        // data jadwal
        jadwalAdapter = new JadwalListAdapter(this, jadwal);
        binding.listJadwal.setDivider(null);
        binding.listJadwal.setAdapter(jadwalAdapter);

        // data photos
        photosAdapter = new PhotosAdapter(photos);
        binding.listPhotos.setAdapter(photosAdapter);

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place != null) {
                    if (place.getFormattedPhoneNumber() != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(
                                String.format("tel:%s", place.getFormattedPhoneNumber())
                        ));

                        startActivity(intent);
                    } else {
                        new AlertDialog.Builder(PlaceDetailActivity.this)
                                .setTitle("Informasi")
                                .setMessage("Tempat ini tidak memiliki nomor telepon.")
                                .setPositiveButton("Ok", null)
                                .create().show();
                    }
                } else {
                    Toast.makeText(PlaceDetailActivity.this, R.string.loading, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        binding.btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place != null) {
                    if (place.getGeometry() != null) {
                        Uri gmnUri = Uri.parse(
                                String.format("google.navigation:q=%s,%s",
                                        place.getGeometry().getLocation().getLat(),
                                        place.getGeometry().getLocation().getLng())
                        );
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmnUri);
                        mapIntent.setPackage("com.google.android.apps.maps");

                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            // store to db
                            PlaceVisitHistoryModel visitHistory = visitHistoryDao.findByPlaceId(place.getPlaceId());

                            if(visitHistory == null) {
                                PlaceVisitHistoryModel newVisitHistory = new PlaceVisitHistoryModel();
                                newVisitHistory.setPlaceId(place.getPlaceId());
                                newVisitHistory.setPlaceName(place.getPlaceName());
                                newVisitHistory.setFormattedAddress(place.getFormattedAddress());
                                newVisitHistory.setVicinity(place.getVicinity());
                                newVisitHistory.setCreatedAt(new Date());
                                newVisitHistory.setUpdatedAt(new Date());

                                if(place.getPhotos() != null && place.getPhotos().length > 0) {
                                    newVisitHistory.setPhotoUrl(place.getPhotos()[0].getPhotoUrl());
                                }

                                visitHistoryDao.storeVisitHistory(newVisitHistory);
                            } else {
                                visitHistory.setUpdatedAt(new Date());
                                visitHistoryDao.updateVisitHistory(visitHistory);
                            }
                            // end store to db

                            startActivity(mapIntent);
                        } else {
                            new AlertDialog.Builder(PlaceDetailActivity.this)
                                    .setTitle("Informasi")
                                    .setMessage("Aplikasi Google Maps tidak terinstall")
                                    .setPositiveButton("Ok", null)
                                    .create().show();
                        }
                    }
                } else {
                    Toast.makeText(PlaceDetailActivity.this, R.string.loading, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        presenter.load();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getJadwalData() {
        if (place.getOpeningHours() != null) {
            jadwal.addAll(Arrays.asList(place.getOpeningHours().getWeekdayText()));
            jadwalAdapter.notifyDataSetChanged();
        } else {
            binding.titleJadwal.setVisibility(View.GONE);
            binding.listJadwal.setVisibility(View.GONE);
        }
    }

    private void parsePhotos() {
        if (place.getPhotos() == null) return;

        photos.clear();

        for (PhotoModel photo : place.getPhotos()) {
            photo.setMaxWidth(400);
            photos.add(photo.getPhotoUrl());
        }

        if (photos.size() > 0) binding.noPhoto.setVisibility(View.GONE);

        photosAdapter.notifyDataSetChanged();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onLoad(PlaceModel placeData) {
        place = placeData;
        Log.d("info", place.getFormattedAddress());
        parsePhotos();
        getJadwalData();

        binding.address.setText(place.getFormattedAddress());
        binding.placeDistance.setText(
                String.format("%.1fkm dari tempat anda", place.getDistance(this) / 1000)
        );
    }

    @Override
    public ActivityPlaceDetailBinding getBinding() {
        return binding;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }
}