package com.vs18.jobtracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vs18.jobtracker.data.entity.JobEntity;
import com.vs18.jobtracker.databinding.ActivityJobDetailsBinding;
import com.vs18.jobtracker.utils.JobStatus;
import com.vs18.jobtracker.viewmodel.JobViewModel;

public class JobDetailsActivity extends AppCompatActivity {

    private ActivityJobDetailsBinding binding;

    private JobViewModel viewModel;

    private JobEntity currentJob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =
                ActivityJobDetailsBinding.inflate(
                        getLayoutInflater()
                );

        setContentView(binding.getRoot());

        int id =
                getIntent().getIntExtra(
                        "JOB_ID",
                        -1
                );

        viewModel =
                new ViewModelProvider(this)
                        .get(JobViewModel.class);

        viewModel.getJobById(id)
                .observe(this, job -> {

                    currentJob = job;

                    if (job == null) {
                        finish();
                        return;
                    }

                    binding.tvTitle.setText(job.getTitle());
                    binding.tvCompany.setText(job.getCompany());
                    binding.tvCity.setText(job.getCity());
                    binding.tvSalary.setText(job.getSalary());
                    binding.tvDescription.setText(job.getDescription());

                    binding.tvStatus.setText(
                            "Статус: " + currentJob.getStatus()
                    );
                });



        binding.btnEdit.setOnClickListener(v -> {

            if (currentJob == null) {
                return;
            }

            Intent intent =
                    new Intent(
                            JobDetailsActivity.this,
                            AddJobActivity.class
                    );

            intent.putExtra(
                    "JOB_ID",
                    currentJob.getId()
            );

            startActivity(intent);
        });

        binding.btnChangeStatus.setOnClickListener(v -> {

            if (currentJob == null) {
                return;
            }

            String[] statuses = {
                    JobStatus.SAVED,
                    JobStatus.APPLIED,
                    JobStatus.INTERVIEW,
                    JobStatus.TECHNICAL,
                    JobStatus.OFFER,
                    JobStatus.REJECTED
            };

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Оберіть статус")
                    .setItems(statuses, (dialog, which) -> {

                        currentJob.setStatus(
                                statuses[which]
                        );

                        viewModel.update(currentJob);

                    })
                    .show();

        });

        binding.btnDelete.setOnClickListener(v -> {

            if (currentJob == null) {
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Видалення")
                    .setMessage("Видалити вакансію?")
                    .setPositiveButton("Так",
                            (dialog, which) -> {

                                viewModel.delete(
                                        currentJob
                                );

                                finish();

                            })
                    .setNegativeButton(
                            "Ні",
                            null
                    )
                    .show();

        });
    }
}