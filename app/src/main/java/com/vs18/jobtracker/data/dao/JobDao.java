package com.vs18.jobtracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vs18.jobtracker.data.entity.JobEntity;

import java.util.List;

@Dao
public interface JobDao {

    @Insert
    void insert(JobEntity job);

    @Update
    void update(JobEntity job);

    @Delete
    void delete(JobEntity job);

    @Query("SELECT * FROM jobs ORDER BY createdAt DESC")
    LiveData<List<JobEntity>> getAllJobs();

    @Query("SELECT * FROM jobs WHERE id = :id")
    LiveData<JobEntity> getJobById(int id);

    @Query("SELECT * FROM jobs WHERE favorite = 1")
    LiveData<List<JobEntity>> getFavoriteJobs();

    @Query("""
    SELECT * FROM jobs
    WHERE title LIKE '%' || :query || '%'
    OR company LIKE '%' || :query || '%'
    ORDER BY createdAt DESC
    """)
    LiveData<List<JobEntity>> searchJobs(String query);

    @Query("SELECT COUNT(*) FROM jobs")
    LiveData<Integer> getJobsCount();

    @Query("SELECT COUNT(*) FROM jobs WHERE status = :status")
    LiveData<Integer> getCountByStatus(String status);

    @Query("SELECT COUNT(*) FROM jobs WHERE favorite = 1")
    LiveData<Integer> getFavoriteJobsCount();

    @Query("SELECT * FROM jobs ORDER BY createdAt DESC")
    LiveData<List<JobEntity>> getNewestJobs();

    @Query("SELECT * FROM jobs ORDER BY createdAt ASC")
    LiveData<List<JobEntity>> getOldestJobs();

    @Query("SELECT * FROM jobs ORDER BY title ASC")
    LiveData<List<JobEntity>> getJobsByTitle();

    @Query("SELECT * FROM jobs ORDER BY company ASC")
    LiveData<List<JobEntity>> getJobsByCompany();

    @Query("SELECT * FROM jobs WHERE status = :status ORDER BY createdAt DESC")
    LiveData<List<JobEntity>> getJobsByStatus(String status);

    @Query("SELECT COUNT(*) FROM jobs WHERE status != :status")
    LiveData<Integer> getActiveJobsCount(String status);

    @Query("DELETE FROM jobs")
    void deleteAllJobs();

}
