package com.hnl.ytplayer.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

/**
 * Database entity for favorite videos
 */
@Entity(tableName = "favorites")
public class Favorite implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String videoId;
    public String url;
    public String thumbnail;
    public String channelName;
    public long createdAt;

    public Favorite() {}

    public Favorite(String title, String videoId, String url, String thumbnail,
                   String channelName) {
        this.title = title;
        this.videoId = videoId;
        this.url = url;
        this.thumbnail = thumbnail;
        this.channelName = channelName;
        this.createdAt = System.currentTimeMillis();
    }
}
