package com.example.app_jewelry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_jewelry.R;
import com.example.app_jewelry.entity.ProductVariant;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private final List<ProductVariant> variants;
    private final Context context;
    private int selectedPosition = -1;

    public interface OnColorSelectedListener {
        void onColorSelected(ProductVariant variant, int position);
    }

    private OnColorSelectedListener listener;

    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.listener = listener;
    }

    public ColorAdapter(Context context, List<ProductVariant> variants) {
        this.context = context;
        this.variants = variants;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        ProductVariant variant = variants.get(position);

        Glide.with(context)
                .load(variant.getColor())
                .circleCrop()
                .into(holder.imgColor);

        holder.imgSelected.setVisibility(position == selectedPosition ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            int old = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(old);
            notifyItemChanged(selectedPosition);

            if (listener != null) {
                listener.onColorSelected(variant, selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return variants.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgColor, imgSelected;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColor = itemView.findViewById(R.id.imgColor);
            imgSelected = itemView.findViewById(R.id.imgSelected);
        }
    }
}