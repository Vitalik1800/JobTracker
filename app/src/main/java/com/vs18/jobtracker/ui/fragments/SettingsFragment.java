package com.vs18.jobtracker.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vs18.jobtracker.data.entity.JobEntity;
import com.vs18.jobtracker.databinding.FragmentSettingsBinding;
import com.vs18.jobtracker.utils.CsvExporter;
import com.vs18.jobtracker.utils.DatabaseBackupUtil;
import com.vs18.jobtracker.utils.PdfExporter;
import com.vs18.jobtracker.viewmodel.JobViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private JobViewModel viewModel;

    private List<JobEntity> jobs = new ArrayList<>();

    private File lastExportedFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(
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

        viewModel.getAllJobs()
                        .observe(
                                getViewLifecycleOwner(),
                                result -> jobs = result
                        );

        setupButtons();
    }

    private void setupButtons() {

        binding.btnExportPdf.setOnClickListener(v -> {

            lastExportedFile =
                    PdfExporter.exportJobs(
                            requireContext(),
                            jobs
                    );

            Toast.makeText(
                    requireContext(),
                    "PDF успішно створено",
                    Toast.LENGTH_SHORT
            ).show();

        });

        binding.btnExportCsv.setOnClickListener(v -> {

            try {

                lastExportedFile =
                        CsvExporter.exportJobs(
                                requireContext(),
                                jobs
                        );

                Toast.makeText(
                        requireContext(),
                        "CSV успішно створено",
                        Toast.LENGTH_SHORT
                ).show();

            } catch (Exception e) {

                Toast.makeText(
                        requireContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }

        });

        binding.btnClearDatabase.setOnClickListener(v -> {

            new MaterialAlertDialogBuilder(
                    requireContext()
            )
                    .setTitle("Очистити базу")
                    .setMessage(
                            "Видалити всі вакансії?"
                    )
                    .setPositiveButton(
                            "Так",
                            (dialog, which) -> {

                                viewModel.deleteAllJobs();

                                Toast.makeText(
                                        requireContext(),
                                        "Базу очищено",
                                        Toast.LENGTH_SHORT
                                ).show();

                            }
                    )
                    .setNegativeButton(
                            "Ні",
                            null
                    )
                    .show();

        });

        binding.btnShareFile.setOnClickListener(v -> {

            if (lastExportedFile == null) {

                Toast.makeText(
                        requireContext(),
                        "Спочатку експортуйте файл",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            shareFile(lastExportedFile);

        });

        binding.btnBackupDatabase.setOnClickListener(v -> {

            try {

                lastExportedFile =
                        DatabaseBackupUtil
                                .backupDatabase(
                                        requireContext()
                                );

                Toast.makeText(
                        requireContext(),
                        "Backup створено",
                        Toast.LENGTH_SHORT
                ).show();

            } catch (Exception e) {

                Toast.makeText(
                        requireContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }

        });

        binding.btnAbout.setOnClickListener(v -> {

            new MaterialAlertDialogBuilder(
                    requireContext()
            )
                    .setTitle("Job Tracker")
                    .setMessage(
                            "Версія 1.0\n\n" +
                            "Додаток для обліку вакансій, " +
                            "статистики пошуку роботи та експорту даних."
                    )
                    .setPositiveButton(
                            "OK",
                            null
                    )
                    .show();

        });

    }

    private void shareFile(File file) {

        Uri uri =
                FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().getPackageName() + ".provider",
                        file
                );

        Intent intent =
                new Intent(Intent.ACTION_SEND);

        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
        );

        startActivity(
                Intent.createChooser(
                        intent,
                        "Поділитися файлом"
                )
        );

    }
}
