package com.hnl.ytplayer.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.hnl.ytplayer.R;
import com.hnl.ytplayer.databinding.ActivityHistoryBinding;
import com.hnl.ytplayer.ui.adapters.HistoryAdapter;
import com.hnl.ytplayer.ui.viewmodel.HistoryViewModel;

/**
 * HistoryActivity - Display and manage video history
 */
public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryViewModel historyViewModel;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Watch History");

        initializeViewModel();
        setupUI();
    }

    private void initializeViewModel() {
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
    }

    private void setupUI() {
        adapter = new HistoryAdapter(video -> playVideo(video.url));
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewHistory.setAdapter(adapter);

        historyViewModel.getAllHistory().observe(this, videos -> {
            adapter.submitList(videos);
        });

        binding.buttonClearHistory.setOnClickListener(v -> {
            historyViewModel.clearAllHistory();
        });
    }

    private void playVideo(String url) {
        // Navigate to player
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                historyViewModel.searchHistory(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Reset to all history
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
