package com.example.app_jewelry.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app_jewelry.ProductDetailActivity;
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.ProductArrivalResponse;

import java.util.List;

public class ProductArrivalAdapter extends RecyclerView.Adapter<ProductArrivalAdapter.ProductViewHolder> {

    private final Context context;
    private List<ProductArrivalResponse> productList;
    private final OnFavoriteToggleListener favoriteToggleListener;

    public ProductArrivalAdapter(Context context, List<ProductArrivalResponse> productList,OnFavoriteToggleListener listener) {
        this.context = context;
        this.productList = productList;
        this.favoriteToggleListener = listener;
    }

    public void setData(List<ProductArrivalResponse> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_arrival, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductArrivalResponse product = productList.get(position);

        holder.txtProductName.setText(product.getName());
        holder.txtProductPrice.setText("đ" + product.getPrice());

        if (product.getOldPrice() != null && product.getOldPrice() > 0) {
            holder.txtProductOldPrice.setText("đ" + product.getOldPrice());
            holder.txtProductOldPrice.setPaintFlags(
                    holder.txtProductOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
            holder.txtProductOldPrice.setVisibility(View.VISIBLE);
        } else {
            holder.txtProductOldPrice.setText(" "); // giữ chỗ
            holder.txtProductOldPrice.setVisibility(View.INVISIBLE);
        }

        Glide.with(context)
                .load(product.getMainImage())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgProduct);

        holder.btnFavorite.setImageResource(
                product.isFavorite() ? R.drawable.ic_heart_fill : R.drawable.ic_heart_outline
        );

        holder.btnFavorite.setOnClickListener(v -> {
            boolean newState = !product.isFavorite();
            product.setFavorite(newState);
            notifyItemChanged(position);

            if (favoriteToggleListener != null) {
                favoriteToggleListener.onFavoriteToggled(product, newState);
            }
        });

        Integer currentUserId = 1;
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
            intent.putExtra("userId", currentUserId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnFavorite;
        TextView txtProductName, txtProductPrice, txtProductOldPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductOldPrice = itemView.findViewById(R.id.txtProductOldPrice);
        }
    }
    public interface OnFavoriteToggleListener {
        void onFavoriteToggled(ProductArrivalResponse product, boolean isFavorite);
    }
}
