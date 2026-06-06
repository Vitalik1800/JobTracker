package com.vs18.jobtracker.utils;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.vs18.jobtracker.data.entity.JobEntity;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class CsvExporter {

    @NonNull
    public static File exportJobs(
            @NonNull Context context,
            @NonNull List<JobEntity> jobs
    ) throws Exception {

        File file =
                new File(
                        context.getExternalFilesDir(
                                Environment.DIRECTORY_DOCUMENTS
                        ),
                        "jobs.csv"
                );

        FileWriter writer =
                new FileWriter(file);

        writer.append(
                "Title,Company,City,Salary,Status,Favorite\n"
        );

        for (JobEntity job : jobs) {

            writer.append(
                    escape(job.getTitle())
            ).append(",");

            writer.append(
                    escape(job.getCompany())
            ).append(",");

            writer.append(
                    escape(job.getCity())
            ).append(",");

            writer.append(
                    escape(job.getSalary())
            ).append(",");

            writer.append(
                    escape(job.getStatus())
            ).append(",");

            writer.append(
                    String.valueOf(job.isFavorite())
            );

            writer.append("\n");
        }

        writer.flush();
        writer.close();

        return file;
    }

    private static String escape(String value) {

        if (value == null) {
            return "";
        }

        return "\"" +
                value.replace("\"", "\"\"")
                + "\"";
    }
}