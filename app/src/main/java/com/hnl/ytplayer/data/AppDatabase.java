package com.hnl.ytplayer.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.hnl.ytplayer.data.dao.HistoryDao;
import com.hnl.ytplayer.data.dao.FavoritesDao;
import com.hnl.ytplayer.data.dao.SettingsDao;
import com.hnl.ytplayer.data.entity.VideoHistory;
import com.hnl.ytplayer.data.entity.Favorite;
import com.hnl.ytplayer.data.entity.Settings;

/**
 * Room Database for YouTube Player
 * Manages video history, favorites, and settings
 */
@Database(
    entities = {VideoHistory.class, Favorite.class, Settings.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HistoryDao historyDao();
    public abstract FavoritesDao favoritesDao();
    public abstract SettingsDao settingsDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "ytplayer_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return instance;
    }
}
