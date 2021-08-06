package com.stdev.deketin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.stdev.deketin.databinding.ActivityMainBinding;
import com.stdev.deketin.databinding.ActivitySplashBinding;
import com.stdev.deketin.utils.Preferences;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String versionName = BuildConfig.VERSION_NAME;
        binding.textVersion.setText(String.format("Version %s", versionName));

        boolean walkedStatus = Preferences.getWalkedStatus(this);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, !walkedStatus ? WalkthroughActivity.class : MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 3000);
    }
}