package com.hnl.ytplayer.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.hnl.ytplayer.data.entity.Settings;

/**
 * Data Access Object for Settings
 */
@Dao
public interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Delete
    void delete(Settings settings);

    @Query("SELECT * FROM settings WHERE key = :key")
    Settings getSettingByKey(String key);

    @Query("SELECT * FROM settings WHERE key = :key")
    LiveData<Settings> getSettingLiveDataByKey(String key);

    @Query("SELECT value FROM settings WHERE key = :key LIMIT 1")
    String getSettingValue(String key);
}
