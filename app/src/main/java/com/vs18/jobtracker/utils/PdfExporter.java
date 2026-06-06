package com.vs18.jobtracker.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vs18.jobtracker.data.entity.JobEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfExporter {

    @NonNull
    public static File exportJobs(
            Context context,
            @NonNull List<JobEntity> jobs
    ) {

        PdfDocument document =
                new PdfDocument();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo
                        .Builder(595, 842, 1)
                        .create();

        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();

        int y = 50;

        canvas.drawText(
                "Звіт вакансій роботи",
                40,
                y,
                paint
        );

        y += 40;

        for (JobEntity job : jobs) {

            canvas.drawText(
                    job.getTitle()
                        + " | "
                        + job.getCompany(),
                    40,
                    y,
                    paint
            );

            y += 30;
        }

        document.finishPage(page);

        File file =
                new File(
                        context.getExternalFilesDir(null),
                        "jobs.pdf"
                );

        try {

            document.writeTo(
                    new FileOutputStream(file)
            );

        } catch (Exception e) {

            Log.e("PDF_EXPORTER", "PDF EXPORT ERROR: " + e.getMessage());

        }

        document.close();

        return file;
    }

}
