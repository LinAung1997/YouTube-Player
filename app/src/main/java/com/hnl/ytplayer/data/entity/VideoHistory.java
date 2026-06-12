package com.hnl.ytplayer.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

/**
 * Database entity for video history
 * Stores information about watched videos
 */
@Entity(tableName = "video_history")
public class VideoHistory implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String videoId;
    public String url;
    public String thumbnail;
    public String channelName;
    public long watchedTime;  // Current position in ms
    public long totalDuration;  // Total duration in ms
    public int watchCount;
    public boolean isFavorite;
    public long createdAt;
    public long lastWatchedAt;

    public VideoHistory() {}

    public VideoHistory(String title, String videoId, String url, String thumbnail,
                       String channelName, long watchedTime, long totalDuration) {
        this.title = title;
        this.videoId = videoId;
        this.url = url;
        this.thumbnail = thumbnail;
        this.channelName = channelName;
        this.watchedTime = watchedTime;
        this.totalDuration = totalDuration;
        this.watchCount = 1;
        this.isFavorite = false;
        this.createdAt = System.currentTimeMillis();
        this.lastWatchedAt = System.currentTimeMillis();
    }

    public float getWatchProgress() {
        if (totalDuration <= 0) return 0;
        return (float) watchedTime / totalDuration;
    }
}
