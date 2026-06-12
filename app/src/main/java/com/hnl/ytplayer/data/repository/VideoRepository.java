package com.hnl.ytplayer.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.hnl.ytplayer.App;
import com.hnl.ytplayer.data.dao.HistoryDao;
import com.hnl.ytplayer.data.entity.VideoHistory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository for video history operations
 */
public class VideoRepository {

    private final HistoryDao historyDao;
    private final ExecutorService executorService;

    public VideoRepository(Application application) {
        this.historyDao = App.getInstance().getDatabase().historyDao();
        this.executorService = Executors.newFixedThreadPool(2);
    }

    // Insert or update video history
    public void insertOrUpdateHistory(VideoHistory video) {
        executorService.execute(() -> {
            VideoHistory existing = historyDao.getByVideoId(video.videoId);
            if (existing != null) {
                existing.watchedTime = video.watchedTime;
                existing.totalDuration = video.totalDuration;
                existing.watchCount++;
                existing.lastWatchedAt = System.currentTimeMillis();
                historyDao.update(existing);
            } else {
                historyDao.insert(video);
            }
        });
    }

    // Get all history
    public LiveData<List<VideoHistory>> getAllHistory() {
        return historyDao.getAllHistory();
    }

    // Search history
    public LiveData<List<VideoHistory>> searchHistory(String query) {
        return historyDao.searchHistory(query);
    }

    // Get recent history
    public LiveData<List<VideoHistory>> getRecentHistory(int limit) {
        return historyDao.getRecentHistory(limit);
    }

    // Get by video ID
    public VideoHistory getByVideoId(String videoId) {
        return historyDao.getByVideoId(videoId);
    }

    // Delete history item
    public void deleteHistory(VideoHistory video) {
        executorService.execute(() -> historyDao.delete(video));
    }

    // Clear all history
    public void clearAllHistory() {
        executorService.execute(historyDao::deleteAll);
    }

    // Update history
    public void updateHistory(VideoHistory video) {
        executorService.execute(() -> historyDao.update(video));
    }
}
