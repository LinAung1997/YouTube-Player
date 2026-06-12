package com.hnl.ytplayer.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Database entity for application settings
 */
@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey
    public String key;
    public String value;

    public Settings() {}

    public Settings(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // Common setting keys
    public static final String KEY_THEME_MODE = "theme_mode";
    public static final String KEY_DEFAULT_SPEED = "default_speed";
    public static final String KEY_AUTO_RESUME = "auto_resume";
    public static final String KEY_AUTO_FULLSCREEN = "auto_fullscreen";
    public static final String KEY_BRIGHTNESS_GESTURE = "brightness_gesture";
    public static final String KEY_VOLUME_GESTURE = "volume_gesture";
    public static final String KEY_GESTURE_SENSITIVITY = "gesture_sensitivity";
    public static final String KEY_SUBTITLE_SIZE = "subtitle_size";
    public static final String KEY_SUBTITLE_COLOR = "subtitle_color";
    public static final String KEY_SUBTITLE_ENCODING = "subtitle_encoding";
    public static final String KEY_CACHE_SIZE = "cache_size";
}
