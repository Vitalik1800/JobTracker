package com.vs18.jobtracker.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vs18.jobtracker.data.dao.JobDao;
import com.vs18.jobtracker.data.entity.JobEntity;

@Database(
        entities = {JobEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract JobDao jobDao();

    public static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (AppDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "job_tracker_db"
                    )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
