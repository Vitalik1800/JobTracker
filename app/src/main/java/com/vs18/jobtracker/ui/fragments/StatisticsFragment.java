package com.vs18.jobtracker.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vs18.jobtracker.databinding.FragmentStatisticsBinding;
import com.vs18.jobtracker.utils.JobStatus;
import com.vs18.jobtracker.viewmodel.JobViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;

    private JobViewModel viewModel;

    private int totalJobs = 0;
    private int applied = 0;
    private int interview = 0;
    private int technical = 0;
    private int offer = 0;
    private int rejected = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentStatisticsBinding.inflate(
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

        observeStatistics();
    }

    private void observeStatistics() {

        viewModel.getJobsCount()
                .observe(
                        getViewLifecycleOwner(),
                        count -> {

                            totalJobs = count == null ? 0 : count;

                            binding.tvTotalJobs.setText(
                                   "Усього вакансій:" + totalJobs
                            );

                            updateConversionStats();
                        }
                );

        viewModel.getFavoriteJobsCount()
                .observe(
                        getViewLifecycleOwner(),
                        count -> binding.tvFavoriteJobs.setText(
                                "Улюблених: " + count
                        )
                );

        viewModel.getCountByStatus(JobStatus.APPLIED)
                .observe(getViewLifecycleOwner(), value -> {

                    applied = value == null ? 0 : value;

                    updateChart();
                    updateConversionStats();

                    binding.tvApplied.setText(
                            "Відгуків: " + applied
                    );

                });

        viewModel.getCountByStatus(JobStatus.INTERVIEW)
                .observe(getViewLifecycleOwner(), value -> {

                    interview = value == null ? 0 : value;

                    updateChart();
                    updateConversionStats();

                    binding.tvInterview.setText(
                            "Співбесід: " + interview
                    );

                });

        viewModel.getCountByStatus(JobStatus.TECHNICAL)
                .observe(getViewLifecycleOwner(), value -> {

                    technical = value == null ? 0 : value;

                    updateChart();
                    updateConversionStats();

                    binding.tvTechnical.setText(
                            "Технічних етапів: " + technical
                    );

                });

        viewModel.getCountByStatus(JobStatus.OFFER)
                .observe(getViewLifecycleOwner(), value -> {

                    offer = value == null ? 0 : value;

                    updateChart();
                    updateConversionStats();

                    binding.tvOffer.setText(
                            "Оферів: " + offer
                    );

                });

        viewModel.getCountByStatus(JobStatus.REJECTED)
                .observe(getViewLifecycleOwner(), value -> {

                    rejected = value == null ? 0 : value;

                    updateChart();
                    updateConversionStats();

                    binding.tvRejected.setText(
                            "Відхилених: " + rejected
                    );

                });
    }

    private void updateChart() {

        ArrayList<PieEntry> entries =
                new ArrayList<>();

        if (applied > 0) {
            entries.add(new PieEntry(applied, "Відгуки"));
        }

        if (interview > 0) {
            entries.add(new PieEntry(interview, "Співбесіди"));
        }

        if (technical > 0) {
            entries.add(new PieEntry(technical, "Технічний етап"));
        }

        if (offer > 0) {
            entries.add(new PieEntry(offer, "Офери"));
        }

        if (rejected > 0) {
            entries.add(new PieEntry(rejected, "Відхилені"));
        }

        if (entries.isEmpty()) {

            binding.pieChart.clear();
            binding.pieChart.setNoDataText(
                    "Немає даних для статистики"
            );

            return;
        }

        PieDataSet dataSet =
                new PieDataSet(
                        entries,
                        "Статистика вакансій"
                );

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        PieData data =
                new PieData(dataSet);

        binding.pieChart.setData(data);

        binding.pieChart.setCenterText("Статистика");
        binding.pieChart.setHoleRadius(45f);
        binding.pieChart.setTransparentCircleRadius(50f);
        binding.pieChart.animateY(1000);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setDrawEntryLabels(false);

        binding.pieChart.invalidate();
    }

    private void updateConversionStats() {

        double successRate = 0;

        if (applied > 0) {
            successRate =
                    (offer * 100.0) / applied;
        }

        double appliedToInterview = 0;

        if (applied > 0) {
            appliedToInterview =
                    (interview * 100.0) / applied;
        }

        double interviewToOffer = 0;

        if (interview > 0) {
            interviewToOffer =
                    (offer * 100.0) / interview;
        }

        int activeJobs =
                totalJobs - rejected;

        binding.tvSuccessRate.setText(
                String.format(Locale.getDefault(), "%.1f%%", successRate)
        );

        binding.tvAppliedToInterview.setText(
                String.format(Locale.getDefault(), "%.1f%%", appliedToInterview)
        );

        binding.tvInterviewToOffer.setText(
                String.format(Locale.getDefault(), "%.1f%%", interviewToOffer)
        );

        binding.tvActiveJobs.setText(
                "Активних вакансій: " + activeJobs
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}