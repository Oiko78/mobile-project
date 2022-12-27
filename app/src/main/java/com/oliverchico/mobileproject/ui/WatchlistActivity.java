package com.oliverchico.mobileproject.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.oliverchico.mobileproject.R;
import com.oliverchico.mobileproject.model.Status;
import com.oliverchico.mobileproject.model.Watchlist;

import java.util.ArrayList;
import java.util.List;

public class WatchlistActivity extends AppCompatActivity implements WatchlistAdapter.WatchlistAdapterListener {
    private final WatchlistAdapter adapter = new WatchlistAdapter(new WatchlistAdapter.WatchlistDiff(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        MaterialToolbar toolbar = findViewById(R.id.watchlist_top_app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView rvWatchlist = findViewById(R.id.rv_watchlist);
        rvWatchlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvWatchlist.setAdapter(adapter);

        List<Watchlist> allWatchlist = new ArrayList<>();
        allWatchlist.add(new Watchlist(1, "Watchlist 1", Status.WATCHED));
        allWatchlist.add(new Watchlist(2, "Watchlist 2", Status.WATCHING));
        allWatchlist.add(new Watchlist(3, "Watchlist 3", Status.PLAN_TO_WATCH));
        adapter.submitList(allWatchlist);
    }

    @Override
    public void onEdit(Watchlist watchlist) {
        Toast.makeText(this, "Watchlist with title: " + watchlist.getTitle() + " is about to be updated.", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDelete(Watchlist watchlist) {
        Toast.makeText(this, "Watchlist with title: " + watchlist.getTitle() + " is about to be deleted.", Toast.LENGTH_SHORT)
                .show();
    }
}