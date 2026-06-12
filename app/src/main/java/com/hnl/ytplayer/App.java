package com.hnl.ytplayer;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.hnl.ytplayer.data.AppDatabase;
import com.hnl.ytplayer.utils.CrashHandler;

/**
 * Main Application class for YouTube Player
 * Initializes app-level configurations and crash handling
 */
public class App extends Application {

    private static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Initialize crash handler
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));

        // Initialize database
        initializeDatabase();

        // Set theme mode from preferences
        applyThemePreference();
    }

    private void initializeDatabase() {
        if (database == null) {
            database = AppDatabase.getInstance(this);
        }
    }

    private void applyThemePreference() {
        // Apply theme preference - will be managed in SettingsViewModel
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        if (database == null) {
            database = AppDatabase.getInstance(this);
        }
        return database;
    }
}
