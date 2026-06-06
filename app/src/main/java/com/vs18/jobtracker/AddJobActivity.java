package com.vs18.jobtracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.vs18.jobtracker.data.entity.JobEntity;
import com.vs18.jobtracker.databinding.ActivityAddJobBinding;
import com.vs18.jobtracker.utils.JobStatus;
import com.vs18.jobtracker.viewmodel.JobViewModel;

public class AddJobActivity extends AppCompatActivity {

    private ActivityAddJobBinding binding;

    private JobViewModel viewModel;

    private int jobId = -1;

    private JobEntity editingJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =
                ActivityAddJobBinding.inflate(
                        getLayoutInflater()
                );

        setContentView(binding.getRoot());

        viewModel =
                new ViewModelProvider(this)
                        .get(JobViewModel.class);

        jobId =
                getIntent()
                        .getIntExtra(
                                "JOB_ID",
                                -1
                        );

        if (jobId != -1) {

            setTitle("Редагування вакансії");

            viewModel.getJobById(jobId)
                    .observe(this, job -> {

                        if (job == null) {
                            return;
                        }

                        editingJob = job;

                        binding.etTitle.setText(job.getTitle());
                        binding.etCompany.setText(job.getCompany());
                        binding.etCity.setText(job.getCity());
                        binding.etSalary.setText(job.getSalary());
                        binding.etDescription.setText(job.getDescription());

                    });
        }

        binding.btnSave.setOnClickListener(v -> {

            String title =
                    binding.etTitle.getText()
                            .toString()
                            .trim();

            String company =
                    binding.etCompany.getText()
                            .toString()
                            .trim();

            String city =
                    binding.etCity.getText()
                            .toString()
                            .trim();

            String salary =
                    binding.etSalary.getText()
                            .toString()
                            .trim();

            String description =
                    binding.etDescription.getText()
                            .toString()
                            .trim();

            if (title.isEmpty()) {
                binding.etTitle.setError("Вкажіть назву");
                return;
            }

            if (company.isEmpty()) {
                binding.etCompany.setError("Вкажіть компанію");
                return;
            }

            if (salary.isEmpty()) {

                binding.etSalary.setError(
                        "Вкажіть зарплату"
                );

                return;
            }

            if (editingJob == null) {

                JobEntity job =
                        new JobEntity(
                                title,
                                company,
                                city,
                                salary,
                                description,
                                JobStatus.SAVED,
                                false,
                                System.currentTimeMillis()
                        );

                viewModel.insert(job);

                Toast.makeText(
                        this,
                        "Вакансію додано",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                editingJob.setTitle(title);
                editingJob.setCompany(company);
                editingJob.setCity(city);
                editingJob.setSalary(salary);
                editingJob.setDescription(description);

                viewModel.update(editingJob);

                Toast.makeText(
                        this,
                        "Вакансію оновлено",
                        Toast.LENGTH_SHORT
                ).show();
            }

            finish();
        });
    }
}