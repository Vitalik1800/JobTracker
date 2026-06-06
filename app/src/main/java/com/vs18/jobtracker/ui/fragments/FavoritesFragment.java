package com.vs18.jobtracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vs18.jobtracker.JobDetailsActivity;
import com.vs18.jobtracker.databinding.FragmentFavoritesBinding;
import com.vs18.jobtracker.ui.adapters.JobAdapter;
import com.vs18.jobtracker.viewmodel.JobViewModel;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    private JobViewModel viewModel;

    private JobAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentFavoritesBinding.inflate(
                inflater,
                container,
                false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity())
                .get(JobViewModel.class);

        setupRecyclerView();

        observeFavorites();

        setupListeners();
    }

    private void setupRecyclerView() {

        adapter = new JobAdapter();

        binding.rvFavorites.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        binding.rvFavorites.setAdapter(adapter);
    }

    private void observeFavorites() {

        viewModel.getFavoriteJobs()
                .observe(getViewLifecycleOwner(), jobs -> {

                    adapter.setJobs(jobs);

                    binding.tvEmpty.setVisibility(
                            jobs.isEmpty()
                                ? View.VISIBLE
                                : View.GONE
                    );

                });
    }

    private void setupListeners() {

        adapter.setOnJobClickListener(job -> {

            Intent intent =
                    new Intent(
                            requireContext(),
                            JobDetailsActivity.class
                    );

            intent.putExtra(
                    "JOB_ID",
                    job.getId()
            );

            startActivity(intent);

        });

        adapter.setOnFavoriteClickListener(job -> {

            job.setFavorite(false);

            viewModel.update(job);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}