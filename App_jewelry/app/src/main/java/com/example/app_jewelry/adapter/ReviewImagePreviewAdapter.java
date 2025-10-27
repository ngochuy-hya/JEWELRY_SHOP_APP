package com.example.app_jewelry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app_jewelry.R;
import com.example.app_jewelry.utils.Cloudinary.ReviewMessage;

import java.util.List;

public class ReviewImagePreviewAdapter extends RecyclerView.Adapter<ReviewImagePreviewAdapter.ImageViewHolder> {

    private Context context;
    private List<ReviewMessage> messageList;

    public interface OnImageRemoveListener {
        void onRemove(int position);
    }

    private OnImageRemoveListener removeListener;

    public void setOnRemoveClickListener(OnImageRemoveListener listener) {
        this.removeListener = listener;
    }

    public ReviewImagePreviewAdapter(Context context, List<ReviewMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review_image_preview, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ReviewMessage message = messageList.get(position);
        String loadUrl = message.isUploading() ? message.getLocalUri().toString() : message.getUploadedUrl();

        Glide.with(context)
                .load(loadUrl)
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.imageView);

        holder.progressBar.setVisibility(message.isUploading() ? View.VISIBLE : View.GONE);
        holder.overlay.setVisibility(message.isUploading() ? View.VISIBLE : View.GONE);

        holder.btnRemove.setOnClickListener(v -> {
            if (removeListener != null) removeListener.onRemove(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, btnRemove;
        View overlay;
        View progressBar;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgReview);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            overlay = itemView.findViewById(R.id.overlayView);
            progressBar = itemView.findViewById(R.id.loadingIndicator);
        }
    }
}
