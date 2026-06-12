package com.hnl.ytplayer.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hnl.ytplayer.data.entity.Favorite;
import com.hnl.ytplayer.data.repository.FavoriteRepository;
import java.util.List;

/**
 * ViewModel for managing favorite videos
 */
public class FavoritesViewModel extends AndroidViewModel {

    private final FavoriteRepository favoriteRepository;
    private final LiveData<List<Favorite>> allFavorites;
    private final MutableLiveData<List<Favorite>> searchResults = new MutableLiveData<>();

    public FavoritesViewModel(Application application) {
        super(application);
        this.favoriteRepository = new FavoriteRepository(application);
        this.allFavorites = favoriteRepository.getAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    public LiveData<List<Favorite>> getSearchResults() {
        return searchResults;
    }

    public void addFavorite(Favorite favorite) {
        favoriteRepository.addFavorite(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favoriteRepository.removeFavorite(favorite);
    }

    public void searchFavorites(String query) {
        if (query == null || query.isEmpty()) {
            searchResults.setValue(null);
        } else {
            favoriteRepository.searchFavorites(query).observeForever(results ->
                searchResults.setValue(results)
            );
        }
    }

    public void clearAllFavorites() {
        favoriteRepository.clearAllFavorites();
    }

    public boolean isFavorite(String videoId) {
        return favoriteRepository.isFavorite(videoId);
    }
}
