package com.example.app_jewelry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;
import com.example.app_jewelry.entity.ProductVariant;

import java.util.ArrayList;
import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeViewHolder> {

    private final List<ProductVariant> sizeList = new ArrayList<>();
    private final Context context;
    private int selectedPosition = -1;

    public interface OnSizeSelectedListener {
        void onSizeSelected(ProductVariant variant, int position);
    }

    private OnSizeSelectedListener listener;

    public void setOnSizeSelectedListener(OnSizeSelectedListener listener) {
        this.listener = listener;
    }

    public SizeAdapter(Context context, List<ProductVariant> initialList) {
        this.context = context;
        this.sizeList.addAll(initialList);
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_size, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        ProductVariant variant = sizeList.get(position);
        holder.txtSize.setText(variant.getSize());

        if (selectedPosition == position) {
            holder.txtSize.setBackgroundResource(R.drawable.bg_size_selected);
        } else {
            holder.txtSize.setBackgroundResource(R.drawable.bg_size_unselected);
        }

        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedPosition);

            if (listener != null) {
                listener.onSizeSelected(variant, selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder {
        TextView txtSize;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSize = itemView.findViewById(R.id.txtSize);
        }
    }

    public void updateSizes(List<ProductVariant> newSizes) {
        sizeList.clear();
        sizeList.addAll(newSizes);
        selectedPosition = -1;
        notifyDataSetChanged();
    }
}
