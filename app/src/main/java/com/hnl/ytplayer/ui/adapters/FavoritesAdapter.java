package com.hnl.ytplayer.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hnl.ytplayer.R;
import com.hnl.ytplayer.data.entity.Favorite;
import com.hnl.ytplayer.databinding.ItemFavoriteBinding;

/**
 * Adapter for displaying favorite videos in RecyclerView
 */
public class FavoritesAdapter extends ListAdapter<Favorite, FavoritesAdapter.FavoriteViewHolder> {

    private final OnFavoriteActionListener listener;

    public interface OnFavoriteActionListener {
        void onAction(Favorite favorite, String action);
    }

    public FavoritesAdapter(OnFavoriteActionListener listener) {
        super(new DiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteBinding binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite favorite = getItem(position);
        holder.bind(favorite, listener);
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoriteBinding binding;

        FavoriteViewHolder(ItemFavoriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Favorite favorite, OnFavoriteActionListener listener) {
            binding.textViewTitle.setText(favorite.title);
            binding.textViewChannel.setText(favorite.channelName);
            
            // Load thumbnail with Glide
            if (favorite.thumbnail != null && !favorite.thumbnail.isEmpty()) {
                Glide.with(itemView.getContext())
                    .load(favorite.thumbnail)
                    .placeholder(R.drawable.ic_video_placeholder)
                    .error(R.drawable.ic_video_placeholder)
                    .into(binding.imageViewThumbnail);
            }
            
            // Set click listeners
            binding.getRoot().setOnClickListener(v -> listener.onAction(favorite, "play"));
            binding.buttonRemove.setOnClickListener(v -> listener.onAction(favorite, "remove"));
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<Favorite> {
        @Override
        public boolean areItemsTheSame(@NonNull Favorite oldItem, @NonNull Favorite newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Favorite oldItem, @NonNull Favorite newItem) {
            return oldItem.equals(newItem);
        }
    }
}
