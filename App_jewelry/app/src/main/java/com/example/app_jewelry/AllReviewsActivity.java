package com.example.app_jewelry;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.adapter.ReviewAdapter;
import com.example.app_jewelry.entity.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllReviewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private List<Review> fullReviewList;
    private TextView txtAverageRating, txtTotalReviews;
    private ImageView btnBack;

    private TextView filterAll, filter5, filter4, filter3, filter2, filter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_reviews);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        recyclerView = findViewById(R.id.recyclerReviews);
        txtAverageRating = findViewById(R.id.txtAverageRating);
        txtTotalReviews = findViewById(R.id.txtTotalReviews);
        RatingBar ratingStars = findViewById(R.id.ratingStars);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Nhận dữ liệu từ Intent
        String jsonReviews = getIntent().getStringExtra("reviews");
        String jsonStarStats = getIntent().getStringExtra("starStats");
        float avgRating = getIntent().getFloatExtra("avgRating", 0f);
        int reviewCount = getIntent().getIntExtra("reviewCount", 0);

        // Parse JSON dùng Gson
        Gson gson = new Gson();
        Type reviewListType = new TypeToken<List<Review>>() {}.getType();
        Type starMapType = new TypeToken<Map<Integer, Integer>>() {}.getType();

        fullReviewList = gson.fromJson(jsonReviews, reviewListType);
        Map<Integer, Integer> starStats = gson.fromJson(jsonStarStats, starMapType);

        // Setup rating tổng
        txtAverageRating.setText(String.format("%.2f", avgRating));
        ratingStars.setRating(avgRating);
        txtTotalReviews.setText(reviewCount + " Reviews");

        // Biểu đồ sao
        int[] starLevels = {5, 4, 3, 2, 1};
        for (int star : starLevels) {
            int rowId = getResources().getIdentifier("starRow" + star, "id", getPackageName());
            View row = findViewById(rowId);
            if (row == null) continue;

            int count = starStats.getOrDefault(star, 0);
            int percent = (reviewCount > 0) ? count * 100 / reviewCount : 0;

            ((TextView) row.findViewById(R.id.txtStarLabel)).setText(star + " ★");
            ((ProgressBar) row.findViewById(R.id.progressStar)).setProgress(percent);
            ((TextView) row.findViewById(R.id.txtStarCount)).setText(String.valueOf(count));
        }

        // RecyclerView
        adapter = new ReviewAdapter(this, fullReviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setupFilterButtons(fullReviewList);
    }


    private void updateSummary(List<Review> list) {
        if (list == null || list.isEmpty()) {
            txtAverageRating.setText("0.00");
            txtTotalReviews.setText("0 Reviews");
            ((RatingBar) findViewById(R.id.ratingStars)).setRating(0f);
            return;
        }

        float totalRating = 0f;
        for (Review r : list) {
            totalRating += r.getRating();
        }

        float avg = totalRating / list.size();
        txtAverageRating.setText(String.format("%.2f", avg));
        txtTotalReviews.setText(list.size() + " Reviews");
        ((RatingBar) findViewById(R.id.ratingStars)).setRating(avg);
    }

    private void setupStarRow(int star, int rowId) {
        View row = findViewById(rowId);
        if (row == null) return;

        TextView txtStar = row.findViewById(R.id.txtStarLabel);
        ProgressBar progressBar = row.findViewById(R.id.progressStar);
        TextView txtCount = row.findViewById(R.id.txtStarCount);

        int count = 0;
        for (Review r : fullReviewList) {
            if (r.getRating() == star) {
                count++;
            }
        }

        int total = fullReviewList != null ? fullReviewList.size() : 0;
        int percent = total > 0 ? (count * 100 / total) : 0;

        txtStar.setText(star + " ★");
        progressBar.setProgress(percent);
        txtCount.setText(String.valueOf(count));

        // Màu cho progress
        progressBar.setProgressTintList(getColorStateList(
                (star == 0) ? R.color.red : R.color.green
        ));
    }


    private void setupFilterButtons(List<Review> fullReviewList) {
        filterAll = findViewById(R.id.filterAll);
        filter5 = findViewById(R.id.filter5);
        filter4 = findViewById(R.id.filter4);
        filter3 = findViewById(R.id.filter3);
        filter2 = findViewById(R.id.filter2);
        filter1 = findViewById(R.id.filter1);

        View.OnClickListener listener = v -> {
            resetFilterUI();
            v.setBackgroundTintList(getColorStateList(R.color.main_yellow));
            ((TextView) v).setTextColor(getColor(R.color.white));

            int selectedStar = -1;

            if (v == filter1) selectedStar = 1;
            else if (v == filter2) selectedStar = 2;
            else if (v == filter3) selectedStar = 3;
            else if (v == filter4) selectedStar = 4;
            else if (v == filter5) selectedStar = 5;

            List<Review> filtered;
            if (selectedStar == -1) {
                filtered = fullReviewList;
            } else {
                filtered = new ArrayList<>();
                for (Review r : fullReviewList) {
                    if (r.getRating() == selectedStar) {
                        filtered.add(r);
                    }
                }
            }

            adapter = new ReviewAdapter(this, filtered);
            recyclerView.setAdapter(adapter);

            updateSummary(filtered);
            setupStarRow(5, R.id.starRow5);
            setupStarRow(4, R.id.starRow4);
            setupStarRow(3, R.id.starRow3);
            setupStarRow(2, R.id.starRow2);
            setupStarRow(1, R.id.starRow1);

        };

        filterAll.setOnClickListener(listener);
        filter5.setOnClickListener(listener);
        filter4.setOnClickListener(listener);
        filter3.setOnClickListener(listener);
        filter2.setOnClickListener(listener);
        filter1.setOnClickListener(listener);
    }


    private void resetFilterUI() {
        int defaultText = ContextCompat.getColor(this, R.color.black);

        filterAll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.filter_background));
        filter1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.filter_background));
        filter2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.filter_background));
        filter3.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.filter_background));
        filter4.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.filter_background));
        filter5.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.filter_background));

        filterAll.setTextColor(defaultText);
        filter1.setTextColor(defaultText);
        filter2.setTextColor(defaultText);
        filter3.setTextColor(defaultText);
        filter4.setTextColor(defaultText);
        filter5.setTextColor(defaultText);
    }
}
