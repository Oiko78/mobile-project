package com.oliverchico.mobileproject.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.oliverchico.mobileproject.R;
import com.oliverchico.mobileproject.model.Watchlist;

public class WatchlistAdapter extends ListAdapter<Watchlist, WatchlistAdapter.ViewHolder> {
    private WatchlistAdapterListener listener;

    protected WatchlistAdapter(@NonNull DiffUtil.ItemCallback<Watchlist> diffCallback, WatchlistAdapterListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListener(WatchlistAdapterListener listener) {
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_watchlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Watchlist watchlist = getItem(position);
        holder.bind(watchlist);
    }

    public interface WatchlistAdapterListener {
        void onEdit(Watchlist watchlist);

        void onDelete(Watchlist watchlist);
    }

    static class WatchlistDiff extends DiffUtil.ItemCallback<Watchlist> {
        @Override
        public boolean areItemsTheSame(@NonNull Watchlist oldItem, @NonNull Watchlist newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Watchlist oldItem, @NonNull Watchlist newItem) {
            return oldItem.getTitle()
                    .equals(newItem.getTitle()) &&
                    oldItem.getStatus()
                            .equals(newItem.getStatus());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus;
        MaterialButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_watchlist_title);
            tvStatus = itemView.findViewById(R.id.tv_watchlist_status);
            btnEdit = itemView.findViewById(R.id.btn_watchlist_edit);
            btnDelete = itemView.findViewById(R.id.btn_watchlist_delete);
        }

        public void bind(Watchlist watchlist) {
            tvTitle.setText(watchlist.getTitle());
            tvStatus.setText(watchlist.getStatus()
                    .toString());
            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEdit(watchlist);
                }
            });
            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDelete(watchlist);
                }
            });
        }
    }
}
