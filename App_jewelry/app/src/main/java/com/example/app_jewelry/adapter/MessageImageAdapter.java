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

import java.util.List;

public class MessageImageAdapter extends RecyclerView.Adapter<MessageImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<String> imageUrls;

    public MessageImageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_inline, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.bg_image_placeholder)
                .into(holder.imgInline);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgInline;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInline = itemView.findViewById(R.id.imgInline);
        }
    }
}
