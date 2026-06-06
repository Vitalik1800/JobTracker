package com.vs18.jobtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vs18.jobtracker.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        animateViews();

        new Handler(
                Looper.getMainLooper()
        ).postDelayed(() -> {

            startActivity(
                    new Intent(
                            this,
                            MainActivity.class
                    )
            );

            finish();

        }, 2000);
    }

    private void animateViews() {

        binding.ivLogo.setAlpha(0f);
        binding.ivLogo.setScaleX(0.7f);
        binding.ivLogo.setScaleY(0.7f);

        binding.tvAppName.setAlpha(0f);
        binding.tvTagline.setAlpha(0f);

        binding.progressIndicator.setAlpha(0f);

        binding.ivLogo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(800)
                .start();

        binding.tvAppName.animate()
                .alpha(1f)
                .setDuration(1000)
                .setStartDelay(250)
                .start();

        binding.tvTagline.animate()
                .alpha(1f)
                .setDuration(1000)
                .setStartDelay(500)
                .start();

        binding.progressIndicator.animate()
                .alpha(1f)
                .setDuration(800)
                .setStartDelay(700)
                .start();
    }
}