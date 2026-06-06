package com.vs18.jobtracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vs18.jobtracker.R;
import com.vs18.jobtracker.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(
                inflater,
                container,
                false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        openJobsFragment();

        binding.bottomNavigation
                .setOnItemSelectedListener(item -> {

                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_jobs) {

                        openJobsFragment();

                    } else if (
                            itemId == R.id.nav_favorites
                    ) {

                        openFavoritesFragment();

                    } else if (
                            itemId == R.id.nav_statistics
                    ) {

                        openStatisticsFragment();

                    } else if (
                            itemId == R.id.nav_settings
                    ) {

                        openSettingsFragment();

                    }

                    return true;
                });
    }

    private void openJobsFragment() {

        getChildFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.contentContainer,
                        new JobsFragment()
                )
                .commit();
    }

    private void openFavoritesFragment() {

        getChildFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.contentContainer,
                        new FavoritesFragment()
                )
                .commit();
    }

    private void openStatisticsFragment() {

        getChildFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.contentContainer,
                        new StatisticsFragment()
                )
                .commit();
    }

    private void openSettingsFragment() {

        getChildFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.contentContainer,
                        new SettingsFragment()
                )
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}