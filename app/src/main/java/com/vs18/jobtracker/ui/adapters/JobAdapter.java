package com.vs18.jobtracker.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.vs18.jobtracker.R;
import com.vs18.jobtracker.data.entity.JobEntity;
import com.vs18.jobtracker.utils.JobStatus;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<JobEntity> jobs = new ArrayList<>();

    private OnJobClickListener listener;

    private OnFavoriteClickListener favoriteClickListener;

    public interface OnJobClickListener {
        void onJobClick(JobEntity job);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(JobEntity job);
    }

    public void setOnFavoriteClickListener(
            OnFavoriteClickListener listener
    ) {
        this.favoriteClickListener = listener;
    }

    public void setOnJobClickListener(
            OnJobClickListener listener
    ) {
        this.listener = listener;
    }

    public void setJobs(List<JobEntity> jobs) {

        if (jobs == null) {
            this.jobs = new ArrayList<>();
        } else {
            this.jobs = jobs;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_job,
                                parent,
                                false
                        );

        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobEntity job = jobs.get(position);

        holder.tvTitle.setText(job.getTitle());
        holder.tvCompany.setText(job.getCompany());
        holder.tvCity.setText(job.getCity());
        holder.tvSalary.setText(job.getSalary());

        setupStatusChip(
                holder.chipStatus,
                job.getStatus()
        );

        if (job.isFavorite()) {

            holder.btnFavorite.setImageResource(
                    android.R.drawable.btn_star_big_on
            );

        } else {

            holder.btnFavorite.setImageResource(
                    android.R.drawable.btn_star_big_off
            );
        }

        holder.itemView.setOnClickListener(v -> {

            if (listener != null) {
                listener.onJobClick(job);
            }

        });

        holder.btnFavorite.setOnClickListener(v -> {

            if (favoriteClickListener != null) {
                favoriteClickListener.onFavoriteClick(job);
            }
        });


    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    private void setupStatusChip(
            @NonNull Chip chip,
            String status
    ) {

        chip.setText(status);

        chip.setTextColor(
                ContextCompat.getColor(
                        chip.getContext(),
                        android.R.color.white
                )
        );

        switch (status) {

            case JobStatus.SAVED:

                chip.setChipBackgroundColorResource(
                        R.color.status_saved
                );

                break;

            case JobStatus.APPLIED:

                chip.setChipBackgroundColorResource(
                        R.color.status_applied
                );

                break;

            case JobStatus.INTERVIEW:

                chip.setChipBackgroundColorResource(
                        R.color.status_interview
                );

                break;

            case JobStatus.TECHNICAL:

                chip.setChipBackgroundColorResource(
                        R.color.status_technical
                );

                break;

            case JobStatus.OFFER:

                chip.setChipBackgroundColorResource(
                        R.color.status_offer
                );

                break;

            case JobStatus.REJECTED:

                chip.setChipBackgroundColorResource(
                        R.color.status_rejected
                );

                break;
        }

    }

    static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvCompany;
        TextView tvCity;
        TextView tvSalary;
        ImageButton btnFavorite;
        Chip chipStatus;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);

            tvCompany = itemView.findViewById(R.id.tvCompany);

            tvCity = itemView.findViewById(R.id.tvCity);

            tvSalary = itemView.findViewById(R.id.tvSalary);

            btnFavorite = itemView.findViewById(R.id.btnFavorite);

            chipStatus = itemView.findViewById(R.id.chipStatus);
        }

    }
}
