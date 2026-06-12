package com.hnl.ytplayer.utils;

import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for extracting video ID from YouTube URLs
 */
public class YouTubeExtractor {

    private static final String TAG = "YouTubeExtractor";

    private static final Pattern[] PATTERNS = {
        // Standard YouTube URL: https://www.youtube.com/watch?v=VIDEO_ID
        Pattern.compile("(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/|youtube\\.com\\/embed\\/)([^&\\n?#]+)"),
        // Shorts: https://www.youtube.com/shorts/VIDEO_ID
        Pattern.compile("youtube\\.com\\/shorts\\/([^&\\n?#]+)"),
        // Mobile: https://m.youtube.com/watch?v=VIDEO_ID
        Pattern.compile("m\\.youtube\\.com\\/watch\\?v=([^&\\n?#]+)")
    };

    /**
     * Extract video ID from YouTube URL
     * Supports formats:
     * - https://www.youtube.com/watch?v=dQw4w9WgXcQ
     * - https://youtu.be/dQw4w9WgXcQ
     * - https://youtube.com/shorts/dQw4w9WgXcQ
     * - https://m.youtube.com/watch?v=dQw4w9WgXcQ
     *
     * @param url YouTube URL
     * @return Video ID or null if not found
     */
    public static String extractVideoId(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        for (Pattern pattern : PATTERNS) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String videoId = matcher.group(1);
                Log.d(TAG, "Extracted video ID: " + videoId);
                return videoId;
            }
        }

        Log.w(TAG, "Could not extract video ID from URL: " + url);
        return null;
    }

    /**
     * Check if URL is a valid YouTube URL
     */
    public static boolean isValidYouTubeUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        return url.contains("youtube.com") || 
               url.contains("youtu.be") || 
               url.contains("m.youtube.com");
    }

    /**
     * Get standard YouTube URL from video ID
     */
    public static String getStandardUrl(String videoId) {
        if (videoId == null || videoId.isEmpty()) {
            return null;
        }
        return "https://www.youtube.com/watch?v=" + videoId;
    }
}
