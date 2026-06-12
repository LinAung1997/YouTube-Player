package com.hnl.ytplayer.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.hnl.ytplayer.data.entity.Settings;
import com.hnl.ytplayer.App;
import com.hnl.ytplayer.data.dao.SettingsDao;

/**
 * ViewModel for managing application settings
 */
public class SettingsViewModel extends AndroidViewModel {

    private final SettingsDao settingsDao;
    private final MutableLiveData<String> themeMode = new MutableLiveData<>("system");
    private final MutableLiveData<Float> defaultPlaybackSpeed = new MutableLiveData<>(1.0f);
    private final MutableLiveData<Boolean> autoResume = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> autoFullscreen = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> brightnessGestureEnabled = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> volumeGestureEnabled = new MutableLiveData<>(true);
    private final MutableLiveData<Integer> gestureSensitivity = new MutableLiveData<>(50);
    private final MutableLiveData<Integer> subtitleSize = new MutableLiveData<>(20);
    private final MutableLiveData<Integer> subtitleColor = new MutableLiveData<>(0xFFFFFFFF);
    private final MutableLiveData<String> subtitleEncoding = new MutableLiveData<>("UTF-8");

    public SettingsViewModel(Application application) {
        super(application);
        this.settingsDao = App.getInstance().getDatabase().settingsDao();
        loadSettings();
    }

    private void loadSettings() {
        // Load settings from database
        // This would typically be done on a background thread
    }

    public MutableLiveData<String> getThemeMode() {
        return themeMode;
    }

    public void setThemeMode(String mode) {
        themeMode.setValue(mode);
        saveSetting(Settings.KEY_THEME_MODE, mode);
    }

    public MutableLiveData<Float> getDefaultPlaybackSpeed() {
        return defaultPlaybackSpeed;
    }

    public void setDefaultPlaybackSpeed(float speed) {
        defaultPlaybackSpeed.setValue(speed);
        saveSetting(Settings.KEY_DEFAULT_SPEED, String.valueOf(speed));
    }

    public MutableLiveData<Boolean> getAutoResume() {
        return autoResume;
    }

    public void setAutoResume(boolean enabled) {
        autoResume.setValue(enabled);
        saveSetting(Settings.KEY_AUTO_RESUME, String.valueOf(enabled));
    }

    public MutableLiveData<Integer> getSubtitleSize() {
        return subtitleSize;
    }

    public void setSubtitleSize(int size) {
        subtitleSize.setValue(size);
        saveSetting(Settings.KEY_SUBTITLE_SIZE, String.valueOf(size));
    }

    private void saveSetting(String key, String value) {
        // Save setting to database on background thread
    }
}
