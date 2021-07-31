package com.stdev.deketin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.stdev.deketin.adapters.JadwalListAdapter;
import com.stdev.deketin.adapters.PhotosAdapter;
import com.stdev.deketin.databinding.ActivityPlaceDetailBinding;
import com.stdev.deketin.models.JadwalModel;

import java.util.ArrayList;

public class PlaceDetailActivity extends AppCompatActivity {
    private ActivityPlaceDetailBinding binding;
    private ArrayList<JadwalModel> jadwal = new ArrayList<>();
    private String[] photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityPlaceDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // data jadwal
        getJadwalData();
        JadwalListAdapter jadwalAdapter = new JadwalListAdapter(this, jadwal);
        binding.listJadwal.setDivider(null);
        binding.listJadwal.setAdapter(jadwalAdapter);

        // data photos
        getPhotosData();
        PhotosAdapter photosAdapter = new PhotosAdapter(photos);
        binding.listPhotos.setAdapter(photosAdapter);
    }

    private void getJadwalData() {
        JadwalModel jadwal1 = new JadwalModel("Senin", new String[]{"07.00", "23.00"});
        JadwalModel jadwal2 = new JadwalModel("Selasa", new String[]{"07.00", "23.00"});
        JadwalModel jadwal3 = new JadwalModel("Rabu", new String[]{"07.00", "23.00"});
        JadwalModel jadwal4 = new JadwalModel("Kamis", new String[]{"07.00", "23.00"});
        JadwalModel jadwal5 = new JadwalModel("Jumat", new String[]{"07.00", "23.00"});
        JadwalModel jadwal6 = new JadwalModel("Sabtu", new String[]{"07.00", "23.00"});
        JadwalModel jadwal7 = new JadwalModel("Minggu", new String[]{});

        jadwal.clear();
        jadwal.add(jadwal1);
        jadwal.add(jadwal2);
        jadwal.add(jadwal3);
        jadwal.add(jadwal4);
        jadwal.add(jadwal5);
        jadwal.add(jadwal6);
        jadwal.add(jadwal7);
    }

    private void getPhotosData() {
        photos = new String[]{
                "https://images.unsplash.com/photo-1538108149393-fbbd81895907?auto=format&fit=crop&w=400&q=80",
                "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?auto=format&fit=crop&w=400&q=80",
                "https://images.unsplash.com/photo-1551076805-e1869033e561?auto=format&fit=crop&w=400&q=80",
                "https://images.unsplash.com/photo-1579684385127-1ef15d508118?auto=format&fit=crop&w=400&q=80",
                "https://images.unsplash.com/photo-1584820927498-cfe5211fd8bf?auto=format&fit=crop&w=400&q=80"
        };
    }
}