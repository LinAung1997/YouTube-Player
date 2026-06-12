package com.hnl.ytplayer.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Global exception handler for crash logging
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private final Application application;
    private final Thread.UncaughtExceptionHandler defaultHandler;

    public CrashHandler(Application application) {
        this.application = application;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            logCrash(thread, throwable);
        } catch (Exception e) {
            Log.e(TAG, "Error logging crash", e);
        }

        // Call default handler
        if (defaultHandler != null) {
            defaultHandler.uncaughtException(thread, throwable);
        } else {
            System.exit(1);
        }
    }

    private void logCrash(Thread thread, Throwable throwable) {
        try {
            File crashDir = new File(application.getCacheDir(), "crashes");
            if (!crashDir.exists()) {
                crashDir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US)
                .format(new Date());
            File crashFile = new File(crashDir, "crash_" + timestamp + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(crashFile))) {
                writer.write("=== YouTube Player Crash Report ===");
                writer.newLine();
                writer.write("Timestamp: " + new Date());
                writer.newLine();
                writer.write("Thread: " + thread.getName());
                writer.newLine();
                writer.write("Exception: " + throwable.getClass().getSimpleName());
                writer.newLine();
                writer.write("Message: " + throwable.getMessage());
                writer.newLine();
                writer.write("\n=== Stack Trace ===");
                writer.newLine();

                throwable.printStackTrace(new PrintWriter(writer));
            }

            Log.e(TAG, "Crash logged to: " + crashFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "Failed to log crash", e);
        }
    }
}
