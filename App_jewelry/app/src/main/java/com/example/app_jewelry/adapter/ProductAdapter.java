package com.example.app_jewelry.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app_jewelry.ProductDetailActivity;
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductResponse> productList;
    private final Context context;
    private final OnFavoriteClickListener favoriteClickListener;
    private final Integer userId;

    public ProductAdapter(List<ProductResponse> productList, Context context, OnFavoriteClickListener favoriteClickListener) {
        this.productList = productList != null ? productList : new ArrayList<>();
        this.context = context;
        this.favoriteClickListener = favoriteClickListener;
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        this.userId = prefs.getInt("userId", -1);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductResponse product = productList.get(position);

        holder.textName.setText(product.getName());
        holder.textRating.setText(String.format("★ %.1f (%d)", product.getRating(), product.getReviewCount()));

        double currentPrice = product.getCurrentPrice();
        double originalPrice = product.getOriginalPrice();

        holder.textCurrentPrice.setText(String.format("₫%.0f", currentPrice));

        if (originalPrice > currentPrice) {
            holder.textOriginalPrice.setVisibility(View.VISIBLE);
            holder.textOriginalPrice.setText(String.format("₫%.0f", originalPrice));
            holder.textOriginalPrice.setPaintFlags(
                    holder.textOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            holder.textOriginalPrice.setVisibility(View.GONE);
        }

        // Load hình ảnh
        Glide.with(context)
                .load(product.getMainImage())
                .placeholder(R.drawable.ic_launcher_background)
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.imageProduct);

        // Yêu thích
        holder.btnFavorite.setImageResource(
                product.isFavorite() ? R.drawable.ic_heart_fill : R.drawable.ic_heart_outline
        );
        holder.btnFavorite.setOnClickListener(v -> {
            if (favoriteClickListener != null) {
                v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100)
                        .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(100).start())
                        .start();
                favoriteClickListener.onFavoriteClick(product, holder.getAdapterPosition());
            }
        });

        // Điều hướng đến ProductDetail
        View.OnClickListener detailIntent = v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductID());
            intent.putExtra("userId", userId);
            context.startActivity(intent);
        };

        holder.btnAddToCart.setOnClickListener(detailIntent);
        holder.itemView.setOnClickListener(detailIntent);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void updateList(List<ProductResponse> newList) {
        this.productList.clear();
        this.productList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct, btnFavorite;
        TextView textName, textCurrentPrice, textOriginalPrice, textRating;
        Button btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            textName = itemView.findViewById(R.id.textName);
            textCurrentPrice = itemView.findViewById(R.id.textCurrentPrice);
            textOriginalPrice = itemView.findViewById(R.id.textOriginalPrice);
            textRating = itemView.findViewById(R.id.textRating);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(ProductResponse product, int position);
    }
}
