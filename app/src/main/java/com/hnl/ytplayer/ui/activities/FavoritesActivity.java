package com.hnl.ytplayer.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.hnl.ytplayer.R;
import com.hnl.ytplayer.databinding.ActivityFavoritesBinding;
import com.hnl.ytplayer.ui.adapters.FavoritesAdapter;
import com.hnl.ytplayer.ui.viewmodel.FavoritesViewModel;

/**
 * FavoritesActivity - Manage favorite videos
 */
public class FavoritesActivity extends AppCompatActivity {

    private ActivityFavoritesBinding binding;
    private FavoritesViewModel favoritesViewModel;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favorites");

        initializeViewModel();
        setupUI();
    }

    private void initializeViewModel() {
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
    }

    private void setupUI() {
        adapter = new FavoritesAdapter((favorite, action) -> {
            switch (action) {
                case "play":
                    playVideo(favorite.url);
                    break;
                case "remove":
                    favoritesViewModel.removeFavorite(favorite);
                    break;
            }
        });

        binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewFavorites.setAdapter(adapter);

        favoritesViewModel.getAllFavorites().observe(this, favorites -> {
            adapter.submitList(favorites);
            binding.emptyState.setVisibility(favorites.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
        });

        binding.buttonClearFavorites.setOnClickListener(v -> {
            favoritesViewModel.clearAllFavorites();
        });
    }

    private void playVideo(String url) {
        // Navigate to player
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
