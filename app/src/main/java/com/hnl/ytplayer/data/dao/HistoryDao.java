package com.hnl.ytplayer.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.hnl.ytplayer.data.entity.VideoHistory;
import java.util.List;

/**
 * Data Access Object for VideoHistory
 */
@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(VideoHistory video);

    @Update
    void update(VideoHistory video);

    @Delete
    void delete(VideoHistory video);

    @Query("DELETE FROM video_history")
    void deleteAll();

    @Query("SELECT * FROM video_history ORDER BY lastWatchedAt DESC")
    LiveData<List<VideoHistory>> getAllHistory();

    @Query("SELECT * FROM video_history WHERE videoId = :videoId LIMIT 1")
    VideoHistory getByVideoId(String videoId);

    @Query("SELECT * FROM video_history WHERE title LIKE '%' || :query || '%' ORDER BY lastWatchedAt DESC")
    LiveData<List<VideoHistory>> searchHistory(String query);

    @Query("SELECT * FROM video_history ORDER BY lastWatchedAt DESC LIMIT :limit")
    LiveData<List<VideoHistory>> getRecentHistory(int limit);

    @Query("SELECT * FROM video_history ORDER BY watchCount DESC")
    LiveData<List<VideoHistory>> getMostWatchedHistory();

    @Query("SELECT * FROM video_history WHERE isFavorite = 1 ORDER BY lastWatchedAt DESC")
    LiveData<List<VideoHistory>> getFavoriteHistoryItems();
}
