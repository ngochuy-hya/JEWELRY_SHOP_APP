package com.example.app_jewelry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;

import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<String> recentSearches;
    private OnSearchClickListener listener;
    private View.OnClickListener onClearAllClick;

    public interface OnSearchClickListener {
        void onClick(String query);
    }

    public RecentSearchAdapter(List<String> recentSearches,
                               OnSearchClickListener listener,
                               View.OnClickListener onClearAllClick) {
        this.recentSearches = recentSearches;
        this.listener = listener;
        this.onClearAllClick = onClearAllClick;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return recentSearches.size() + 1; // +1 cho header
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).btnClearAll.setOnClickListener(onClearAllClick);
        } else if (holder instanceof ItemViewHolder) {
            String query = recentSearches.get(position - 1); // -1 vì header
            ((ItemViewHolder) holder).tvQuery.setText(query);
            holder.itemView.setOnClickListener(v -> listener.onClick(query));
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView btnClearAll;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            btnClearAll = itemView.findViewById(R.id.btnClearAll);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuery;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuery = itemView.findViewById(R.id.tvQuery);
        }
    }
}
