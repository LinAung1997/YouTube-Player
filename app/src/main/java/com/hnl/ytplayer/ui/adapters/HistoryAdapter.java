package com.hnl.ytplayer.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hnl.ytplayer.R;
import com.hnl.ytplayer.data.entity.VideoHistory;
import com.hnl.ytplayer.databinding.ItemHistoryBinding;

/**
 * Adapter for displaying video history in RecyclerView
 */
public class HistoryAdapter extends ListAdapter<VideoHistory, HistoryAdapter.HistoryViewHolder> {

    private final OnVideoClickListener onVideoClickListener;

    public interface OnVideoClickListener {
        void onVideoClick(VideoHistory video);
    }

    public HistoryAdapter(OnVideoClickListener onVideoClickListener) {
        super(new DiffCallback());
        this.onVideoClickListener = onVideoClickListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        VideoHistory video = getItem(position);
        holder.bind(video, onVideoClickListener);
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding binding;

        HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(VideoHistory video, OnVideoClickListener listener) {
            binding.textViewTitle.setText(video.title);
            binding.textViewChannel.setText(video.channelName);
            
            // Format watch time
            String watchTime = formatTime(video.watchedTime);
            String duration = formatTime(video.totalDuration);
            binding.textViewDuration.setText(watchTime + " / " + duration);
            
            // Set progress bar
            float progress = video.getWatchProgress();
            binding.progressBar.setProgress((int) (progress * 100));
            
            // Load thumbnail with Glide
            if (video.thumbnail != null && !video.thumbnail.isEmpty()) {
                Glide.with(itemView.getContext())
                    .load(video.thumbnail)
                    .placeholder(R.drawable.ic_video_placeholder)
                    .error(R.drawable.ic_video_placeholder)
                    .into(binding.imageViewThumbnail);
            }
            
            // Set click listener
            binding.getRoot().setOnClickListener(v -> listener.onVideoClick(video));
        }

        private String formatTime(long milliseconds) {
            long seconds = milliseconds / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            
            seconds %= 60;
            minutes %= 60;
            
            if (hours > 0) {
                return String.format("%d:%02d:%02d", hours, minutes, seconds);
            } else {
                return String.format("%d:%02d", minutes, seconds);
            }
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<VideoHistory> {
        @Override
        public boolean areItemsTheSame(@NonNull VideoHistory oldItem, @NonNull VideoHistory newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull VideoHistory oldItem, @NonNull VideoHistory newItem) {
            return oldItem.equals(newItem);
        }
    }
}
