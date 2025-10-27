package com.example.app_jewelry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;

public class SearchHeaderAdapter extends RecyclerView.Adapter<SearchHeaderAdapter.HeaderViewHolder> {

    private final View.OnClickListener onClearAllClick;

    public SearchHeaderAdapter(View.OnClickListener onClearAllClick) {
        this.onClearAllClick = onClearAllClick;
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
        holder.btnClearAll.setOnClickListener(onClearAllClick); // gán callback
    }

    @Override
    public int getItemCount() {
        return 1; // chỉ có 1 header
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView btnClearAll;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            btnClearAll = itemView.findViewById(R.id.btnClearAll);
        }
    }
}

