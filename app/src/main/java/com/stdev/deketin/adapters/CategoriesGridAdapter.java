package com.stdev.deketin.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.stdev.deketin.MainActivity;
import com.stdev.deketin.R;
import com.stdev.deketin.SearchResultsActivity;
import com.stdev.deketin.databinding.MenuItemBinding;
import com.stdev.deketin.models.MenuModel;

import java.sql.Array;

public class CategoriesGridAdapter extends BaseAdapter {
    private MenuItemBinding binding;
    private Context context;
    private String[] menuText;
    private int[] icons;

    public CategoriesGridAdapter(Context context) {
        this.context = context;

        menuText = new String[]{"Akuntan", "ATM", "Balai Kota", "Bengkel Mobil", "Bandara", "Tempat Camping",
            "Dealer Mobil", "Galeri Seni", "Gereja", "Hotel", "Kafe", "Masjid", "Polisi", "Pemakaman", "Rental Mobil",
            "Restoran", "Rumah Sakit", "Salon", "Sekolah", "Steam Mobil", "Taman Hiburan", "Terminal Bus", "Toko Buku", "Warung"};

        icons = new int[]{
                R.drawable.ic_cat_accountant, R.drawable.ic_cat_atm, R.drawable.ic_cat_city_hall,
                R.drawable.ic_cat_car_repair, R.drawable.ic_cat_airport, R.drawable.ic_cat_camp_ground,
                R.drawable.ic_cat_car_dealer, R.drawable.ic_cat_art_gallery, R.drawable.ic_cat_church,
                R.drawable.ic_cat_hotel, R.drawable.ic_cat_cafe, R.drawable.ic_cat_mosque, R.drawable.ic_cat_police,
                R.drawable.ic_cat_cemetery, R.drawable.ic_cat_car_rental, R.drawable.ic_cat_restaurant,
                R.drawable.ic_cat_hospital, R.drawable.ic_cat_beauty_salon, R.drawable.ic_cat_school,
                R.drawable.ic_cat_car_wash, R.drawable.ic_cat_amusement_park, R.drawable.ic_cat_book_store,
                R.drawable.ic_cat_bus_station, R.drawable.ic_cat_store
        };
    }

    @Override
    public int getCount() {
        return menuText.length;
    }

    @Override
    public Object getItem(int position) {
        return new MenuModel(menuText[position], icons[position]);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater  layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.menu_item, null);
        binding = MenuItemBinding.bind(view);

        binding.icon.setImageResource(icons[position]);
        binding.menuText.setText(menuText[position]);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("category_clicked", menuText[position] + " Clicked");
                Intent intent = new Intent(context, SearchResultsActivity.class);
                context.startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}
