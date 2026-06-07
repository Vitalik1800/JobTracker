package com.vs18.jobtracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vs18.jobtracker.AddJobActivity;
import com.vs18.jobtracker.JobDetailsActivity;
import com.vs18.jobtracker.R;
import com.vs18.jobtracker.databinding.FragmentJobsBinding;
import com.vs18.jobtracker.ui.adapters.JobAdapter;
import com.vs18.jobtracker.utils.JobStatus;
import com.vs18.jobtracker.utils.SortingUtils;
import com.vs18.jobtracker.viewmodel.JobViewModel;

public class JobsFragment extends Fragment {

    private FragmentJobsBinding binding;

    private JobViewModel viewModel;

    private JobAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentJobsBinding.inflate(
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

        setupObservers();

        setupSearch();

        setupListeners();

        setupToolbar();

    }

    private void setupRecyclerView() {

        adapter = new JobAdapter();

        binding.rvJobs.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        binding.rvJobs.setAdapter(adapter);
    }

    private void setupObservers() {

        viewModel.getAllJobs()
                .observe(getViewLifecycleOwner(),
                        jobs -> {

                            adapter.setJobs(jobs);

                            binding.tvEmpty.setVisibility(
                                    jobs.isEmpty()
                                        ? View.VISIBLE
                                        : View.GONE
                            );
                        }
                );
    }

    private void setupSearch() {

        binding.searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText == null ||
                                newText.trim().isEmpty()) {

                            viewModel.getAllJobs()
                                    .observe(
                                            getViewLifecycleOwner(),
                                            jobs -> adapter.setJobs(jobs)
                                    );

                        } else {

                            viewModel.searchJobs(newText)
                                    .observe(
                                            getViewLifecycleOwner(),
                                            jobs -> adapter.setJobs(jobs)
                                    );
                        }

                        return true;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                }
        );
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

            job.setFavorite(
                    !job.isFavorite()
            );

            viewModel.update(job);
        });

        binding.fabAddJob.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            requireContext(),
                            AddJobActivity.class
                    );

            startActivity(intent);

        });
    }

    private void setupToolbar() {

        binding.toolbar.setOnMenuItemClickListener(item -> {

            int id = item.getItemId();

            if (id == R.id.action_sort) {

                showSortDialog();
                return true;
            }

            if (id == R.id.action_filter) {

                showFilterDialog();
                return true;
            }

            return false;
        });
    }

    private void showSortDialog() {

        String[] options = {
                SortingUtils.NEWEST,
                SortingUtils.OLDEST,
                SortingUtils.TITLE,
                SortingUtils.COMPANY
        };

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Сортування")
                .setItems(options, (dialog, which) -> {

                    switch (which) {

                        case 0:

                            viewModel.getNewestJobs()
                                    .observe(
                                            getViewLifecycleOwner(),
                                            jobs -> adapter.setJobs(jobs)
                                    );
                            break;

                        case 1:

                            viewModel.getOldestJobs()
                                    .observe(
                                            getViewLifecycleOwner(),
                                            jobs -> adapter.setJobs(jobs)
                                    );
                            break;

                        case 2:

                            viewModel.getJobsByTitle()
                                    .observe(
                                            getViewLifecycleOwner(),
                                            jobs -> adapter.setJobs(jobs)
                                    );
                            break;

                        case 3:

                            viewModel.getJobsByCompany()
                                    .observe(
                                            getViewLifecycleOwner(),
                                            jobs -> adapter.setJobs(jobs)
                                    );
                            break;
                    }

                })
                .show();
    }

    private void showFilterDialog() {

        String[] options = {
                JobStatus.ALL,
                JobStatus.SAVED,
                JobStatus.APPLIED,
                JobStatus.INTERVIEW,
                JobStatus.TECHNICAL,
                JobStatus.OFFER,
                JobStatus.REJECTED
        };

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Фільтр")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {

                        viewModel.getAllJobs()
                                .observe(
                                        getViewLifecycleOwner(),
                                        jobs -> adapter.setJobs(jobs)
                                );

                        return;
                    }

                    filterByStatus(options[which]);

                })
                .show();
    }

    private void filterByStatus(String status) {

        viewModel.getJobsByStatus(status)
                .observe(
                        getViewLifecycleOwner(),
                        jobs -> adapter.setJobs(jobs)
                );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}