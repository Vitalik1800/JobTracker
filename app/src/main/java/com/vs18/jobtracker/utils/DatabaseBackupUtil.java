package com.vs18.jobtracker.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseBackupUtil {

    public static File backupDatabase(
            @NonNull Context context
    ) throws IOException {

        File dbFile =
                context.getDatabasePath(
                        "job_tracker_db"
                );

        File backupFile =
                new File(
                        context.getExternalFilesDir(null),
                        "job_tracker_backup.db"
                );

        FileInputStream in =
                new FileInputStream(dbFile);

        FileOutputStream out =
                new FileOutputStream(backupFile);

        byte[] buffer =
                new byte[1024];

        int length;

        while ((length = in.read(buffer)) > 0) {

            out.write(
                    buffer,
                    0,
                    length
            );
        }

        in.close();
        out.close();

        return backupFile;
    }

}
