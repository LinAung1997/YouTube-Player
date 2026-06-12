package com.hnl.ytplayer.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.media3.session.MediaSession;

/**
 * Service for managing media session and notifications
 */
public class MediaSessionService extends Service {

    private MediaSession mediaSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeMediaSession();
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSession.Builder(this, new androidx.media3.session.MediaSession.Callback() {}).build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mediaSession != null) {
            mediaSession.release();
        }
        super.onDestroy();
    }
}
