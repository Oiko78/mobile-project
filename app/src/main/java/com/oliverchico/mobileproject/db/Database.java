package com.oliverchico.mobileproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.oliverchico.mobileproject.model.Status;
import com.oliverchico.mobileproject.model.Watchlist;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mobileproject.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WATCHLIST = "watchlist";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_STATUS = "status";

    private SQLiteDatabase db;
    private OnWatchlistChangeListener listener = allWatchlist -> {
        // No implementation
    };

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void setListener(OnWatchlistChangeListener listener) {
        this.listener = listener;
        listener.onWatchlistChange(getAllWatchlist());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_WATCHLIST +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_STATUS + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHLIST);
        onCreate(sqLiteDatabase);
    }

    public void insertWatchlist(Watchlist watchlist) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, watchlist.getId());
        cv.put(COLUMN_TITLE, watchlist.getTitle());
        cv.put(COLUMN_STATUS, watchlist.getStatus()
                .name());

        db.insertWithOnConflict(TABLE_WATCHLIST, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        listener.onWatchlistChange(getAllWatchlist());
    }

    public void updateStatus(Integer id, Status status) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, status.name());

        db.update(TABLE_WATCHLIST, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        listener.onWatchlistChange(getAllWatchlist());
    }

    public void updateStatus(Watchlist watchlist) {
        updateStatus(watchlist.getId(), watchlist.getStatus());
    }

    public void deleteWatchlist(Integer id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_WATCHLIST, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        listener.onWatchlistChange(getAllWatchlist());
    }

    public Boolean isWatchlistExist(Integer id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WATCHLIST + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        Boolean isExist = cursor.getCount() > 0;
        cursor.close();
        return isExist;
    }

    public List<Watchlist> getAllWatchlist() {
        db = this.getReadableDatabase();
        List<Watchlist> watchlists = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WATCHLIST, null);
        if (cursor.moveToFirst()) {
            do {
                Watchlist watchlist = new Watchlist();
                watchlist.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                watchlist.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                watchlist.setStatus(Enum.valueOf(Status.class, cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))));
                watchlists.add(watchlist);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return watchlists;
    }

    public interface OnWatchlistChangeListener {
        void onWatchlistChange(final List<Watchlist> allWatchlist);
    }
}
