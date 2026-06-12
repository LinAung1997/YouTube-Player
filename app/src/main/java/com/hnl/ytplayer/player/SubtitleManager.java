package com.hnl.ytplayer.player;

import android.content.Context;
import android.util.Log;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.Tracks;
import java.io.File;

/**
 * Manages subtitle loading and display
 * Supports: SRT, VTT, ASS, SSA formats
 */
public class SubtitleManager {

    private static final String TAG = "SubtitleManager";

    private String currentSubtitlePath;
    private int subtitleSize = 20;
    private int subtitleColor = 0xFFFFFFFF;
    private String encoding = "UTF-8";

    public void loadSubtitle(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            Log.w(TAG, "Invalid subtitle path");
            return;
        }

        File subtitleFile = new File(filePath);
        if (!subtitleFile.exists()) {
            Log.w(TAG, "Subtitle file not found: " + filePath);
            return;
        }

        this.currentSubtitlePath = filePath;
        Log.d(TAG, "Subtitle loaded: " + filePath);
    }

    public void autoDetectSubtitle(String videoTitle, Context context) {
        if (videoTitle == null || videoTitle.isEmpty()) {
            return;
        }

        // Search for subtitle files with same name
        String[] subtitleExtensions = {"srt", "vtt", "ass", "ssa"};
        String[] searchDirs = {
            context.getExternalFilesDir(null).getAbsolutePath(),
            context.getExternalCacheDir().getAbsolutePath()
        };

        for (String dir : searchDirs) {
            for (String ext : subtitleExtensions) {
                String subtitlePath = dir + File.separator + videoTitle + "." + ext;
                File file = new File(subtitlePath);
                if (file.exists()) {
                    loadSubtitle(subtitlePath);
                    return;
                }
            }
        }
    }

    public void setSubtitleSize(int size) {
        this.subtitleSize = Math.max(10, Math.min(50, size)); // Clamp between 10 and 50
    }

    public void setSubtitleColor(int color) {
        this.subtitleColor = color;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getCurrentSubtitlePath() {
        return currentSubtitlePath;
    }

    public int getSubtitleSize() {
        return subtitleSize;
    }

    public int getSubtitleColor() {
        return subtitleColor;
    }

    public String getEncoding() {
        return encoding;
    }

    public boolean hasSubtitle() {
        return currentSubtitlePath != null && !currentSubtitlePath.isEmpty();
    }

    public void clearSubtitle() {
        currentSubtitlePath = null;
    }
}
