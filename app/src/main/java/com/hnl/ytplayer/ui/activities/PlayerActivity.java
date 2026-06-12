package com.hnl.ytplayer.ui.activities;

import android.app.PictureInPictureParams;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.Player;
import androidx.media3.ui.PlayerControlView;
import com.hnl.ytplayer.R;
import com.hnl.ytplayer.databinding.ActivityPlayerBinding;
import com.hnl.ytplayer.data.entity.VideoHistory;
import com.hnl.ytplayer.player.GestureController;
import com.hnl.ytplayer.player.PlayerManager;
import com.hnl.ytplayer.player.SubtitleManager;
import com.hnl.ytplayer.ui.viewmodel.PlayerViewModel;
import com.hnl.ytplayer.utils.YouTubeExtractor;

/**
 * PlayerActivity - Main video player screen
 */
public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private PlayerViewModel playerViewModel;
    private PlayerManager playerManager;
    private SubtitleManager subtitleManager;
    private GestureController gestureController;

    private String videoUrl;
    private String videoId;
    private boolean isFullscreen = false;
    private float currentBrightness = 0.5f;
    private boolean screenLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get video URL from intent
        videoUrl = getIntent().getStringExtra("video_url");
        if (videoUrl == null || videoUrl.isEmpty()) {
            Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        videoId = YouTubeExtractor.extractVideoId(videoUrl);

        initializeViewModel();
        initializePlayer();
        initializeSubtitles();
        initializeGestures();
        setupUI();
        loadVideo();
    }

    private void initializeViewModel() {
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
    }

    private void initializePlayer() {
        playerManager = new PlayerManager(this);
        playerManager.initialize();
        playerManager.setCallback(new PlayerManager.PlaybackCallback() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    playerViewModel.savePlaybackProgress();
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                playerViewModel.setIsPlaying(isPlaying);
            }

            @Override
            public void onPlayerError(androidx.media3.common.PlaybackException error) {
                Toast.makeText(PlayerActivity.this, "Playback error: " + error.getMessage(),
                    Toast.LENGTH_SHORT).show();
            }
        });
        binding.playerView.setPlayer(playerManager.getPlayer());
    }

    private void initializeSubtitles() {
        subtitleManager = new SubtitleManager();
    }

    private void initializeGestures() {
        gestureController = new GestureController(new GestureController.GestureCallback() {
            @Override
            public void onSingleTap() {
                toggleControlsVisibility();
            }

            @Override
            public void onDoubleTapSeek(long milliseconds) {
                long newPosition = playerManager.getCurrentPosition() + milliseconds;
                playerManager.seekTo(Math.max(0, newPosition));
            }

            @Override
            public void onBrightnessChange(int change) {
                adjustBrightness(change);
            }

            @Override
            public void onVolumeChange(int change) {
                adjustVolume(change);
            }
        });
        binding.playerView.setOnTouchListener(gestureController);
    }

    private void setupUI() {
        // Setup player controls
        binding.playerView.setControllerShowTimeoutMs(5000);
        binding.playerView.setControllerHideTimeoutMs(5000);

        // Setup playback speed button
        binding.buttonSpeed.setOnClickListener(v -> showSpeedDialog());

        // Setup fullscreen button
        binding.buttonFullscreen.setOnClickListener(v -> toggleFullscreen());

        // Setup subtitle button
        binding.buttonSubtitle.setOnClickListener(v -> showSubtitleDialog());

        // Setup screen lock button
        binding.buttonLock.setOnClickListener(v -> toggleScreenLock());

        // Setup sleep timer button
        binding.buttonSleepTimer.setOnClickListener(v -> showSleepTimerDialog());

        // Observe player state
        playerViewModel.getIsPlaying().observe(this, isPlaying -> {
            if (isPlaying) {
                playerManager.play();
            } else {
                playerManager.pause();
            }
        });
    }

    private void loadVideo() {
        // In production, you would extract the actual playable URL from YouTube
        // For now, we'll attempt to load the URL directly
        playerManager.loadUrl(videoUrl);
    }

    private void toggleControlsVisibility() {
        if (binding.playerView.isControllerVisible()) {
            binding.playerView.hideController();
        } else {
            binding.playerView.showController();
        }
    }

    private void toggleFullscreen() {
        isFullscreen = !isFullscreen;
        if (isFullscreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        playerViewModel.setIsFullscreen(isFullscreen);
    }

    private void toggleScreenLock() {
        screenLocked = !screenLocked;
        binding.playerView.setEnabled(!screenLocked);
        Toast.makeText(this, screenLocked ? "Screen locked" : "Screen unlocked", Toast.LENGTH_SHORT).show();
    }

    private void adjustBrightness(int change) {
        currentBrightness += change / 100f;
        currentBrightness = Math.max(0, Math.min(1, currentBrightness));
        
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = currentBrightness;
        getWindow().setAttributes(params);
        
        playerViewModel.setBrightness((int) (currentBrightness * 100));
    }

    private void adjustVolume(int change) {
        // Volume adjustment would be handled through AudioManager
        playerViewModel.setVolume(playerViewModel.getVolume().getValue() != null ? 
            playerViewModel.getVolume().getValue() + change : 50);
    }

    private void showSpeedDialog() {
        // Show speed selection dialog
        float[] speeds = {0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f, 3.0f};
        Toast.makeText(this, "Speed dialog would appear here", Toast.LENGTH_SHORT).show();
    }

    private void showSubtitleDialog() {
        // Show subtitle selection dialog
        Toast.makeText(this, "Subtitle dialog would appear here", Toast.LENGTH_SHORT).show();
    }

    private void showSleepTimerDialog() {
        // Show sleep timer selection dialog
        Toast.makeText(this, "Sleep timer dialog would appear here", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPictureInPictureRequested() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Rational aspectRatio = new Rational(16, 9);
            PictureInPictureParams params = new PictureInPictureParams.Builder()
                .setAspectRatio(aspectRatio)
                .build();
            enterPictureInPictureMode(params);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerViewModel.savePlaybackProgress();
        playerManager.pause();
    }

    @Override
    protected void onDestroy() {
        playerManager.release();
        super.onDestroy();
    }
}
