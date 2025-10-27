package com.example.app_jewelry.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.app_jewelry.Service.DTO.reponse.TodayOfferResponse;

import java.util.List;

public class TodayOfferAdapter extends RecyclerView.Adapter<TodayOfferAdapter.ViewHolder> {

    private Context context;
    private List<TodayOfferResponse> offerList;
    private OnAddToCartClickListener listener;
    private Integer currentUserId = 1;

    public TodayOfferAdapter(Context context, List<TodayOfferResponse> offerList, OnAddToCartClickListener listener) {
        this.context = context;
        this.offerList = offerList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodayOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_today_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayOfferAdapter.ViewHolder holder, int position) {
        TodayOfferResponse product = offerList.get(position);

        // Tên sản phẩm
        holder.textName.setText(product.getName());

        // Giá khuyến mãi và gốc (null-safe)
        double currentPrice = product.getCurrentPrice();
        double originalPrice = product.getOriginalPrice();

        holder.textCurrentPrice.setText("đ" + (int) currentPrice);
        holder.textOriginalPrice.setText("đ" + (int) originalPrice);
        holder.textOriginalPrice.setPaintFlags(holder.textOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // Badge
        holder.textBadge.setText("Special Deal");

        // Load ảnh sản phẩm
        Glide.with(context)
                .load(product.getMainImage())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .placeholder(R.drawable.ic_launcher_background)  // optional placeholder
                .error(R.drawable.ic_launcher_background)              // optional fallback
                .into(holder.imageProduct);

        // Rating & đánh giá
        holder.textRating.setText(String.format("%.1f", product.getRating()));
        holder.textReviewCount.setText("(" + product.getReviewCount() + ")");

        // Xử lý click Add to Cart
        holder.btnAddToCart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClicked(product);
            }
        });
        View.OnClickListener detailIntent = v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
            intent.putExtra("userId", currentUserId);
            context.startActivity(intent);
        };

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
            intent.putExtra("userId", currentUserId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return offerList != null ? offerList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textName, textCurrentPrice, textOriginalPrice, textRating, textReviewCount, textBadge;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textName = itemView.findViewById(R.id.textName);
            textCurrentPrice = itemView.findViewById(R.id.textCurrentPrice);
            textOriginalPrice = itemView.findViewById(R.id.textOriginalPrice);
            textRating = itemView.findViewById(R.id.textRating);
            textReviewCount = itemView.findViewById(R.id.textReviewCount);
            textBadge = itemView.findViewById(R.id.textBadge);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    // Callback interface
    public interface OnAddToCartClickListener {
        void onAddToCartClicked(TodayOfferResponse product);
    }
}
