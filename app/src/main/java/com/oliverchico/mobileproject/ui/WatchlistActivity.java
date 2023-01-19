package com.oliverchico.mobileproject.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.oliverchico.mobileproject.R;
import com.oliverchico.mobileproject.db.Database;
import com.oliverchico.mobileproject.model.Status;
import com.oliverchico.mobileproject.model.Watchlist;

public class WatchlistActivity extends AppCompatActivity implements WatchlistAdapter.WatchlistAdapterListener {
    private final WatchlistAdapter adapter = new WatchlistAdapter(
            new WatchlistAdapter.WatchlistDiff(), this);
    private final Database db = new Database(this);
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);


        MaterialToolbar toolbar = findViewById(R.id.watchlist_top_app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        coordinatorLayout = findViewById(R.id.watchlist_coordinator_layout);


        RecyclerView rvWatchlist = findViewById(R.id.rv_watchlist);
        rvWatchlist.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvWatchlist.setAdapter(adapter);

        db.setListener(adapter::submitList);
    }

    @Override
    public void onEdit(Watchlist watchlist) {
        final String[] status = {"Completed", "Watching", "Plan to Watch"};
        final int[] checkedItem = {watchlist.getStatus().ordinal()};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Edit Watchlist")
                .setSingleChoiceItems(status, checkedItem[0],
                        (dialog, which) -> checkedItem[0] = which)
                .setPositiveButton("Save", (dialog, which) -> {
                    watchlist.setStatus(Status.values()[checkedItem[0]]);
                    db.updateStatus(watchlist);
                    Snackbar.make(coordinatorLayout, "Watchlist updated",
                            Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onDelete(Watchlist watchlist) {
        db.deleteWatchlist(watchlist.getId());
        Snackbar.make(coordinatorLayout,
                        "Watchlist with title: " + watchlist.getTitle() + " is deleted.",
                        Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> db.insertWatchlist(watchlist))
                .show();
    }
}