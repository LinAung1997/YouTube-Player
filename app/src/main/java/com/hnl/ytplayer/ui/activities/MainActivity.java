package com.hnl.ytplayer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.hnl.ytplayer.R;
import com.hnl.ytplayer.databinding.ActivityMainBinding;
import com.hnl.ytplayer.ui.adapters.HistoryAdapter;
import com.hnl.ytplayer.ui.viewmodel.HistoryViewModel;
import com.hnl.ytplayer.utils.ClipboardHelper;
import com.hnl.ytplayer.utils.PermissionManager;
import com.hnl.ytplayer.utils.YouTubeExtractor;

/**
 * MainActivity - Home screen with URL input and recent videos
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HistoryViewModel historyViewModel;
    private HistoryAdapter recentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestPermissions();
        initializeViewModel();
        setupUI();
        monitorClipboard();
    }

    private void requestPermissions() {
        String[] permissions = PermissionManager.getRequiredPermissions();
        // Runtime permission handling would go here
    }

    private void initializeViewModel() {
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
    }

    private void setupUI() {
        // Setup RecyclerView for recent videos
        recentAdapter = new HistoryAdapter(video -> playVideo(video.url));
        binding.recyclerViewRecent.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRecent.setAdapter(recentAdapter);

        // Observe recent history
        historyViewModel.getRecentHistory(10).observe(this, videos -> {
            recentAdapter.submitList(videos);
        });

        // Setup buttons
        binding.buttonPaste.setOnClickListener(v -> pasteFromClipboard());
        binding.buttonPlay.setOnClickListener(v -> playFromInput());
        binding.buttonHistory.setOnClickListener(v -> navigateToHistory());
        binding.buttonFavorites.setOnClickListener(v -> navigateToFavorites());
        binding.buttonSettings.setOnClickListener(v -> navigateToSettings());

        // Setup URL input listener
        binding.editTextUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable/disable play button based on input
                binding.buttonPlay.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void pasteFromClipboard() {
        String clipboard = ClipboardHelper.getFromClipboard(this);
        if (clipboard != null && YouTubeExtractor.isValidYouTubeUrl(clipboard)) {
            binding.editTextUrl.setText(clipboard);
            Toast.makeText(this, "YouTube URL pasted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No valid YouTube URL in clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    private void playFromInput() {
        String url = binding.editTextUrl.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!YouTubeExtractor.isValidYouTubeUrl(url)) {
            Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        playVideo(url);
    }

    private void playVideo(String url) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("video_url", url);
        startActivity(intent);
    }

    private void navigateToHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    private void navigateToFavorites() {
        startActivity(new Intent(this, FavoritesActivity.class));
    }

    private void navigateToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void monitorClipboard() {
        // Check clipboard periodically for YouTube URLs
        // This would typically be implemented with a background service
    }
}
