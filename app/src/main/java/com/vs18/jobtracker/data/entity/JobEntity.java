package com.vs18.jobtracker.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jobs")
public class JobEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String company;

    private String city;

    private String salary;

    private String description;

    private String status;

    private boolean favorite;

    private long createdAt;

    public JobEntity(
            String title,
            String company,
            String city,
            String salary,
            String description,
            String status,
            boolean favorite,
            long createdAt
    ) {
        this.title = title;
        this.company = company;
        this.city = city;
        this.salary = salary;
        this.description = description;
        this.status = status;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "JobEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", salary='" + salary + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", favorite=" + favorite +
                ", createdAt=" + createdAt +
                '}';
    }
}
