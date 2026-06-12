package com.hnl.ytplayer.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.hnl.ytplayer.App;
import com.hnl.ytplayer.data.dao.FavoritesDao;
import com.hnl.ytplayer.data.entity.Favorite;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository for favorite videos operations
 */
public class FavoriteRepository {

    private final FavoritesDao favoritesDao;
    private final ExecutorService executorService;

    public FavoriteRepository(Application application) {
        this.favoritesDao = App.getInstance().getDatabase().favoritesDao();
        this.executorService = Executors.newFixedThreadPool(2);
    }

    // Add favorite
    public void addFavorite(Favorite favorite) {
        executorService.execute(() -> favoritesDao.insert(favorite));
    }

    // Remove favorite
    public void removeFavorite(Favorite favorite) {
        executorService.execute(() -> favoritesDao.delete(favorite));
    }

    // Get all favorites
    public LiveData<List<Favorite>> getAllFavorites() {
        return favoritesDao.getAllFavorites();
    }

    // Search favorites
    public LiveData<List<Favorite>> searchFavorites(String query) {
        return favoritesDao.searchFavorites(query);
    }

    // Get by video ID
    public Favorite getByVideoId(String videoId) {
        return favoritesDao.getByVideoId(videoId);
    }

    // Check if favorite
    public boolean isFavorite(String videoId) {
        return favoritesDao.isFavorite(videoId) > 0;
    }

    // Clear all favorites
    public void clearAllFavorites() {
        executorService.execute(favoritesDao::deleteAll);
    }
}
