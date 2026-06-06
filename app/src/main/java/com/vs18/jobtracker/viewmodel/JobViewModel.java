package com.vs18.jobtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vs18.jobtracker.data.entity.JobEntity;
import com.vs18.jobtracker.repository.JobRepository;

import java.util.List;

import kotlinx.coroutines.Job;

public class JobViewModel extends AndroidViewModel {

    private final JobRepository repository;

    private final LiveData<List<JobEntity>> allJobs;

    public JobViewModel(@NonNull Application application) {

        super(application);

        repository =
                new JobRepository(application);

        allJobs =
                repository.getAllJobs();
    }

    public LiveData<List<JobEntity>> getAllJobs() {
        return allJobs;
    }

    public LiveData<JobEntity> getJobById(int id) {
        return repository.getJobById(id);
    }

    public void insert(JobEntity job) {
        repository.insert(job);
    }

    public void update(JobEntity job) {
        repository.update(job);
    }

    public void delete(JobEntity job) {
        repository.delete(job);
    }

    public LiveData<List<JobEntity>> searchJobs(String query) {
        return repository.searchJobs(query);
    }

    public LiveData<List<JobEntity>> getFavoriteJobs() {
        return repository.getFavoriteJobs();
    }

    public LiveData<Integer> getJobsCount() {
        return repository.getJobsCount();
    }

    public LiveData<Integer> getFavoriteJobsCount() {
        return repository.getFavoriteJobsCount();
    }

    public LiveData<Integer> getCountByStatus(String status) {
        return repository.getCountByStatus(status);
    }

    public LiveData<List<JobEntity>> getNewestJobs() {
        return repository.getNewestJobs();
    }

    public LiveData<List<JobEntity>> getOldestJobs() {
        return repository.getOldestJobs();
    }

    public LiveData<List<JobEntity>> getJobsByTitle() {
        return repository.getJobsByTitle();
    }

    public LiveData<List<JobEntity>> getJobsByCompany() {
        return repository.getJobsByCompany();
    }

    public LiveData<List<JobEntity>> getJobsByStatus(String status) {
        return repository.getJobsByStatus(status);
    }

    public LiveData<Integer> getActiveJobsCount(String status) {
        return repository.getActiveJobsCount(status);
    }

    public void deleteAllJobs() {
        repository.deleteAllJobs();
    }

}
