package com.example.app_jewelry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;
import com.example.app_jewelry.entity.Review;
import com.example.app_jewelry.entity.ReviewImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private final List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);

        // Tên người dùng
        String name = review.getReviewerName();
        holder.txtReviewerName.setText(name);
        holder.avatarText.setText(review.getAvatarText());


        // Ngày đánh giá
        holder.txtReviewDate.setText(formatDate(review.getCreatedAt()));

        // Nội dung đánh giá
        String title = review.getTitle();
        if (title != null && !title.trim().isEmpty()) {
            holder.txtReviewTitle.setText(title.trim());
            holder.txtReviewTitle.setVisibility(View.VISIBLE);
        } else {
            holder.txtReviewTitle.setVisibility(View.GONE);
        }
        holder.txtReviewContent.setText(review.getComment() != null ? review.getComment() : "");
        holder.ratingBar.setRating(review.getRating());

        // Ảnh kèm đánh giá
        setupReviewImages(holder.recyclerReviewImages, review.getImageList());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // ====================== Helper ======================
    private String formatDate(String isoDate) {
        if (isoDate == null) return "N/A";
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = isoFormat.parse(isoDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "N/A";
        }
    }

    private void setupReviewImages(RecyclerView recyclerView, List<ReviewImage> images) {
        if (images == null || images.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            return;
        }

        ReviewImageAdapter adapter = new ReviewImageAdapter(context, images);
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            );
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }


    // ====================== ViewHolder ======================
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView avatarText, txtReviewerName, txtReviewDate, txtReviewTitle, txtReviewContent;
        RatingBar ratingBar;
        RecyclerView recyclerReviewImages;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarText = itemView.findViewById(R.id.avatarText);
            txtReviewerName = itemView.findViewById(R.id.txtReviewerName);
            txtReviewDate = itemView.findViewById(R.id.txtReviewDate);
            txtReviewTitle = itemView.findViewById(R.id.txtReviewTitle);
            txtReviewContent = itemView.findViewById(R.id.txtReviewContent);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            recyclerReviewImages = itemView.findViewById(R.id.recyclerReviewImages);
        }
    }
}
