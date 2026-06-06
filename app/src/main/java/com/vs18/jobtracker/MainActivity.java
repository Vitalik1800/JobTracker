package com.vs18.jobtracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vs18.jobtracker.databinding.ActivityMainBinding;
import com.vs18.jobtracker.ui.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragmentContainer,
                            new MainFragment()
                    )
                    .commit();
        }
    }
}