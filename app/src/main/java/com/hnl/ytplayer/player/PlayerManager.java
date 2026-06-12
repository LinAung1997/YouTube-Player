package com.hnl.ytplayer.player;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;

/**
 * Manages ExoPlayer instance and playback control
 */
public class PlayerManager {

    private static final String TAG = "PlayerManager";
    private ExoPlayer exoPlayer;
    private final Context context;
    private PlaybackCallback callback;

    public PlayerManager(Context context) {
        this.context = context;
    }

    public void initialize() {
        if (exoPlayer == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(context);
            exoPlayer = new ExoPlayer.Builder(context)
                .setTrackSelector(trackSelector)
                .build();

            exoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (callback != null) {
                        callback.onPlaybackStateChanged(playbackState);
                    }
                }

                @Override
                public void onIsPlayingChanged(boolean isPlaying) {
                    if (callback != null) {
                        callback.onIsPlayingChanged(isPlaying);
                    }
                }

                @Override
                public void onPlayerError(@NonNull androidx.media3.common.PlaybackException error) {
                    Log.e(TAG, "Player error: " + error.getMessage());
                    if (callback != null) {
                        callback.onPlayerError(error);
                    }
                }
            });
        }
    }

    public void loadUrl(String url) {
        if (exoPlayer == null) {
            initialize();
        }
        MediaItem mediaItem = MediaItem.fromUri(url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
    }

    public void play() {
        if (exoPlayer != null) {
            exoPlayer.play();
        }
    }

    public void pause() {
        if (exoPlayer != null) {
            exoPlayer.pause();
        }
    }

    public void seekTo(long positionMs) {
        if (exoPlayer != null) {
            exoPlayer.seekTo(positionMs);
        }
    }

    public long getCurrentPosition() {
        return exoPlayer != null ? exoPlayer.getCurrentPosition() : 0;
    }

    public long getDuration() {
        return exoPlayer != null ? exoPlayer.getDuration() : 0;
    }

    public void setPlaybackSpeed(float speed) {
        if (exoPlayer != null) {
            exoPlayer.setPlaybackSpeed(speed);
        }
    }

    public boolean isPlaying() {
        return exoPlayer != null && exoPlayer.isPlaying();
    }

    public ExoPlayer getPlayer() {
        return exoPlayer;
    }

    public void release() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public void setCallback(PlaybackCallback callback) {
        this.callback = callback;
    }

    public interface PlaybackCallback {
        void onPlaybackStateChanged(int state);
        void onIsPlayingChanged(boolean isPlaying);
        void onPlayerError(androidx.media3.common.PlaybackException error);
    }
}
