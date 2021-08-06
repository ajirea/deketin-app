package com.stdev.deketin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.stdev.deketin.adapters.WalkthroughViewPagerAdapter;
import com.stdev.deketin.databinding.ActivitySplashBinding;
import com.stdev.deketin.databinding.ActivityWalkthroughBinding;
import com.stdev.deketin.models.WalkthroughModel;
import com.stdev.deketin.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class WalkthroughActivity extends AppCompatActivity {
    private final List<WalkthroughModel> walkthroughs = new ArrayList<>();
    private ActivityWalkthroughBinding binding;
    private TabLayoutMediator tabMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivityWalkthroughBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        WalkthroughViewPagerAdapter adapter = new WalkthroughViewPagerAdapter(walkthroughs);
        binding.wtViewPager.setAdapter(adapter);

        tabMediator = new TabLayoutMediator(binding.wtTab, binding.wtViewPager, (tab, position) -> {});

        binding.wtBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWalk();
            }
        });

        binding.wtViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.wtBtnPrev.setClickable(position > 0);
                binding.wtBtnSkip.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }
        });

        binding.wtBtnNext.setOnClickListener(v -> {
            if(binding.wtViewPager.getCurrentItem()+1 < walkthroughs.size()) {
                binding.wtViewPager.setCurrentItem(binding.wtViewPager.getCurrentItem() + 1);
            } else {
                finishWalk();
            }
        });

        binding.wtBtnPrev.setOnClickListener(v -> {
            if(binding.wtViewPager.getCurrentItem() > 0) {
                binding.wtViewPager.setCurrentItem(binding.wtViewPager.getCurrentItem() - 1);
            }
        });

        load();
    }

    public void finishWalk() {
        Preferences.setWalkedStatus(this, true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void load() {
        this.walkthroughs.clear();

        WalkthroughModel model1 = new WalkthroughModel();
        model1.setTitle("Deketin Tempat");
        model1.setDescription("Cari tempat terdekat dengan anda berdasarkan kategori tempat yang anda inginkan.");
        model1.setImageDrawable(R.drawable.illust_map_category);

        WalkthroughModel model2 = new WalkthroughModel();
        model2.setTitle("Pencarian Tempat");
        model2.setDescription("Cari tempat terdekat dengan anda berdasarkan kata kunci yang anda masukkan.");
        model2.setImageDrawable(R.drawable.illust_map_search);

        WalkthroughModel model3 = new WalkthroughModel();
        model3.setTitle("Riwayat Kunjungan");
        model3.setDescription("Catat riwayat kunjungan anda ke berbagai tempat. Riwayat tercatat langsung di dalam ponsel anda.");
        model3.setImageDrawable(R.drawable.illust_map_visited);

        walkthroughs.add(model1);
        walkthroughs.add(model2);
        walkthroughs.add(model3);
        tabMediator.attach();
    }
}