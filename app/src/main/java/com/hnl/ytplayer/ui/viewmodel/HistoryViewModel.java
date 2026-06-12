package com.hnl.ytplayer.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hnl.ytplayer.data.entity.VideoHistory;
import com.hnl.ytplayer.data.repository.VideoRepository;
import java.util.List;

/**
 * ViewModel for managing video history
 */
public class HistoryViewModel extends AndroidViewModel {

    private final VideoRepository videoRepository;
    private final LiveData<List<VideoHistory>> allHistory;
    private final MutableLiveData<List<VideoHistory>> searchResults = new MutableLiveData<>();
    private final MutableLiveData<String> filterType = new MutableLiveData<>("recent");

    public HistoryViewModel(Application application) {
        super(application);
        this.videoRepository = new VideoRepository(application);
        this.allHistory = videoRepository.getAllHistory();
    }

    public LiveData<List<VideoHistory>> getAllHistory() {
        return allHistory;
    }

    public LiveData<List<VideoHistory>> getSearchResults() {
        return searchResults;
    }

    public void searchHistory(String query) {
        if (query == null || query.isEmpty()) {
            searchResults.setValue(null);
        } else {
            videoRepository.searchHistory(query).observeForever(results -> 
                searchResults.setValue(results)
            );
        }
    }

    public void deleteHistory(VideoHistory video) {
        videoRepository.deleteHistory(video);
    }

    public void clearAllHistory() {
        videoRepository.clearAllHistory();
    }

    public void updateWatchProgress(VideoHistory video, long currentPosition) {
        video.watchedTime = currentPosition;
        video.lastWatchedAt = System.currentTimeMillis();
        videoRepository.updateHistory(video);
    }

    public void setFilterType(String type) {
        filterType.setValue(type);
    }
}
