package com.hnl.ytplayer.player;

import android.view.MotionEvent;
import android.view.View;

/**
 * Handles gesture controls for the player
 * - Double tap to seek (±10 seconds)
 * - Vertical swipe left for brightness
 * - Vertical swipe right for volume
 */
public class GestureController implements View.OnTouchListener {

    private static final long DOUBLE_TAP_THRESHOLD = 300; // ms
    private static final float SWIPE_THRESHOLD = 100; // pixels
    private static final float SWIPE_VELOCITY_THRESHOLD = 100;

    private long lastTapTime = 0;
    private float lastX = 0;
    private float lastY = 0;
    private GestureCallback callback;

    public GestureController(GestureCallback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                return true;

            case MotionEvent.ACTION_UP:
                handleGesture(event);
                return true;
        }
        return false;
    }

    private void handleGesture(MotionEvent event) {
        float deltaX = event.getX() - lastX;
        float deltaY = event.getY() - lastY;
        long currentTime = System.currentTimeMillis();

        // Check for double tap
        if (currentTime - lastTapTime < DOUBLE_TAP_THRESHOLD) {
            if (Math.abs(deltaX) < SWIPE_THRESHOLD && Math.abs(deltaY) < SWIPE_THRESHOLD) {
                handleDoubleTap(event.getX());
            }
            lastTapTime = 0;
        } else if (Math.abs(deltaY) > SWIPE_THRESHOLD) {
            // Vertical swipe - check left or right side
            if (event.getX() < event.getRawDisplayMetrics().widthPixels / 3) {
                // Left side - brightness
                handleBrightnessSwipe(deltaY);
            } else if (event.getX() > event.getRawDisplayMetrics().widthPixels * 2 / 3) {
                // Right side - volume
                handleVolumeSwipe(deltaY);
            }
        } else if (Math.abs(deltaX) < SWIPE_THRESHOLD && Math.abs(deltaY) < SWIPE_THRESHOLD) {
            // Single tap - toggle controls
            lastTapTime = currentTime;
            if (callback != null) {
                callback.onSingleTap();
            }
        }
    }

    private void handleDoubleTap(float x) {
        if (callback == null) return;

        if (x < 100) {
            // Left side - rewind 10 seconds
            callback.onDoubleTapSeek(-10000);
        } else {
            // Right side - forward 10 seconds
            callback.onDoubleTapSeek(10000);
        }
    }

    private void handleBrightnessSwipe(float deltaY) {
        if (callback != null) {
            float change = deltaY / 10; // Adjust divisor for sensitivity
            callback.onBrightnessChange((int) change);
        }
    }

    private void handleVolumeSwipe(float deltaY) {
        if (callback != null) {
            float change = deltaY / 10; // Adjust divisor for sensitivity
            callback.onVolumeChange((int) change);
        }
    }

    public interface GestureCallback {
        void onSingleTap();
        void onDoubleTapSeek(long milliseconds);
        void onBrightnessChange(int change);
        void onVolumeChange(int change);
    }
}
