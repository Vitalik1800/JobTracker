package com.vs18.jobtracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.vs18.jobtracker.data.dao.JobDao;
import com.vs18.jobtracker.data.database.AppDatabase;
import com.vs18.jobtracker.data.entity.JobEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobRepository {

    private final JobDao jobDao;

    private final ExecutorService executorService;

    public JobRepository(Application application) {

        AppDatabase database =
                AppDatabase.getInstance(application);

        jobDao = database.jobDao();

        executorService =
                Executors.newSingleThreadExecutor();
    }

    public LiveData<List<JobEntity>> getAllJobs() {
        return jobDao.getAllJobs();
    }

    public LiveData<List<JobEntity>> getFavoriteJobs() {
        return jobDao.getFavoriteJobs();
    }

    public LiveData<JobEntity> getJobById(int id) {
        return jobDao.getJobById(id);
    }

    public void insert(JobEntity job) {

        executorService.execute(() ->
                jobDao.insert(job)
        );
    }

    public void update(JobEntity job) {

        executorService.execute(() ->
                jobDao.update(job)
        );
    }

    public void delete(JobEntity job) {

        executorService.execute(() ->
                jobDao.delete(job)
        );
    }

    public LiveData<List<JobEntity>> searchJobs(String query) {
        return jobDao.searchJobs(query);
    }

    public LiveData<Integer> getJobsCount() {
        return jobDao.getJobsCount();
    }

    public LiveData<Integer> getFavoriteJobsCount() {
        return jobDao.getFavoriteJobsCount();
    }

    public LiveData<Integer> getCountByStatus(String status) {
        return jobDao.getCountByStatus(status);
    }

    public LiveData<List<JobEntity>> getNewestJobs() {
        return jobDao.getNewestJobs();
    }

    public LiveData<List<JobEntity>> getOldestJobs() {
        return jobDao.getOldestJobs();
    }

    public LiveData<List<JobEntity>> getJobsByTitle() {
        return jobDao.getJobsByTitle();
    }

    public LiveData<List<JobEntity>> getJobsByCompany() {
        return jobDao.getJobsByCompany();
    }

    public LiveData<List<JobEntity>> getJobsByStatus(String status) {
        return jobDao.getJobsByStatus(status);
    }

    public LiveData<Integer> getActiveJobsCount(String status) {
        return jobDao.getActiveJobsCount(status);
    }

    public void deleteAllJobs() {

        executorService.execute(jobDao::deleteAllJobs);
    }

}
