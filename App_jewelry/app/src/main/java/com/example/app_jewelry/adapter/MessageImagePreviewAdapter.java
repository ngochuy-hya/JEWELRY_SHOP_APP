package com.example.app_jewelry.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_jewelry.R;

import java.util.List;

public class MessageImagePreviewAdapter extends RecyclerView.Adapter<MessageImagePreviewAdapter.ImageViewHolder> {

    private final Context context;
    private final List<Uri> imageUriList;
    private final OnRemoveClickListener onRemoveClickListener;

    public interface OnRemoveClickListener {
        void onRemove(int position);
    }

    public MessageImagePreviewAdapter(Context context, List<Uri> imageUriList, OnRemoveClickListener listener) {
        this.context = context;
        this.imageUriList = imageUriList;
        this.onRemoveClickListener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_preview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri uri = imageUriList.get(position);
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.bg_image_placeholder)
                .into(holder.imgPreview);

        holder.btnRemove.setOnClickListener(v -> {
            if (onRemoveClickListener != null) {
                onRemoveClickListener.onRemove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUriList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPreview;
        ImageButton btnRemove;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPreview = itemView.findViewById(R.id.imgPreview);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
