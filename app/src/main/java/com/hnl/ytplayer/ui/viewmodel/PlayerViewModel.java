package com.hnl.ytplayer.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.hnl.ytplayer.data.repository.VideoRepository;
import com.hnl.ytplayer.data.entity.VideoHistory;

/**
 * ViewModel for the player screen
 */
public class PlayerViewModel extends AndroidViewModel {

    private final VideoRepository videoRepository;
    private final MutableLiveData<VideoHistory> currentVideo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPlaying = new MutableLiveData<>(false);
    private final MutableLiveData<Long> currentPosition = new MutableLiveData<>(0L);
    private final MutableLiveData<Long> duration = new MutableLiveData<>(0L);
    private final MutableLiveData<Float> playbackSpeed = new MutableLiveData<>(1.0f);
    private final MutableLiveData<String> subtitlePath = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFullscreen = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> brightness = new MutableLiveData<>(50);
    private final MutableLiveData<Integer> volume = new MutableLiveData<>(50);

    public PlayerViewModel(Application application) {
        super(application);
        this.videoRepository = new VideoRepository(application);
    }

    public MutableLiveData<VideoHistory> getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(VideoHistory video) {
        currentVideo.setValue(video);
    }

    public MutableLiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean playing) {
        isPlaying.setValue(playing);
    }

    public MutableLiveData<Long> getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(long position) {
        currentPosition.setValue(position);
    }

    public MutableLiveData<Long> getDuration() {
        return duration;
    }

    public void setDuration(long dur) {
        duration.setValue(dur);
    }

    public MutableLiveData<Float> getPlaybackSpeed() {
        return playbackSpeed;
    }

    public void setPlaybackSpeed(float speed) {
        playbackSpeed.setValue(speed);
    }

    public MutableLiveData<Boolean> getIsFullscreen() {
        return isFullscreen;
    }

    public void setIsFullscreen(boolean fullscreen) {
        isFullscreen.setValue(fullscreen);
    }

    public MutableLiveData<String> getSubtitlePath() {
        return subtitlePath;
    }

    public void setSubtitlePath(String path) {
        subtitlePath.setValue(path);
    }

    public void savePlaybackProgress() {
        VideoHistory video = currentVideo.getValue();
        if (video != null) {
            video.watchedTime = currentPosition.getValue() != null ? currentPosition.getValue() : 0;
            videoRepository.insertOrUpdateHistory(video);
        }
    }

    public MutableLiveData<Integer> getBrightness() {
        return brightness;
    }

    public void setBrightness(int val) {
        brightness.setValue(val);
    }

    public MutableLiveData<Integer> getVolume() {
        return volume;
    }

    public void setVolume(int val) {
        volume.setValue(val);
    }
}
