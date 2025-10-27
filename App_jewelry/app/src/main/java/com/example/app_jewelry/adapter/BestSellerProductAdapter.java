package com.example.app_jewelry.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app_jewelry.ProductDetailActivity;
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.BestsellerProductResponse;

import java.util.List;

public class BestSellerProductAdapter extends RecyclerView.Adapter<BestSellerProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<BestsellerProductResponse> productList;
    private final OnFavoriteToggleListener favoriteToggleListener;
    Integer currentUserId;

    public BestSellerProductAdapter(Context context, List<BestsellerProductResponse> productList, OnFavoriteToggleListener listener) {
        this.context = context;
        this.productList = productList;
        this.favoriteToggleListener = listener;
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        this.currentUserId = prefs.getInt("userId", -1);
    }

    public void setData(List<BestsellerProductResponse> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_best_seller, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        BestsellerProductResponse product = productList.get(position);

        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText(String.format("₫%.0f", product.getPrice()));

        // Gán giá cũ nếu có
        if (product.getOldPrice() != null && product.getOldPrice() > product.getPrice()) {
            holder.txtProductOldPrice.setVisibility(View.VISIBLE);
            holder.txtProductOldPrice.setText(String.format("₫%.0f", product.getOldPrice()));
            holder.txtProductOldPrice.setPaintFlags(
                    holder.txtProductOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            holder.txtProductOldPrice.setVisibility(View.GONE);
        }

        holder.txtProductRating.setText(
                String.format("★ %.1f (%d)", product.getAvgRating(), product.getReviewCount())
        );

        Glide.with(context)
                .load(product.getMainImage())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgProduct);

        holder.btnFavorite.setImageResource(
                Boolean.TRUE.equals(product.getFavorite()) ? R.drawable.ic_heart_fill : R.drawable.ic_heart_outline
        );

        holder.btnFavorite.setOnClickListener(v -> {
            boolean newState = !Boolean.TRUE.equals(product.getFavorite());
            product.setFavorite(newState);
            notifyItemChanged(position);

            if (favoriteToggleListener != null) {
                favoriteToggleListener.onFavoriteToggled(product, newState);
            }
        });

        View.OnClickListener detailIntent = v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
            intent.putExtra("userId", currentUserId);
            context.startActivity(intent);
        };

        holder.btnAddToCart.setOnClickListener(detailIntent);
        holder.itemView.setOnClickListener(detailIntent);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnFavorite;
        TextView txtProductName, txtProductPrice, txtProductOldPrice, txtProductRating;
        Button btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imageProduct);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            txtProductName = itemView.findViewById(R.id.textName);
            txtProductPrice = itemView.findViewById(R.id.textPrice);
            txtProductOldPrice = itemView.findViewById(R.id.textOldPrice);
            txtProductRating = itemView.findViewById(R.id.textRating);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public interface OnFavoriteToggleListener {
        void onFavoriteToggled(BestsellerProductResponse product, boolean isFavorite);
    }
}
