package com.example.app_jewelry.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;

import java.util.List;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.ViewHolder> {

    private final Context context;
    private final List<CartItemResponse> productList;

    public CheckoutProductAdapter(Context context, List<CartItemResponse> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CheckoutProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_checkout_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutProductAdapter.ViewHolder holder, int position) {
        CartItemResponse item = productList.get(position);

        holder.tvProductName.setText(item.getProductName());
        holder.tvSku.setText("SKU: " + item.getSku());
        holder.tvQuantity.setText("Quantity: x" + item.getQuantity());
        holder.tvPrice.setText("₫" + String.format("%,.0f", item.getPrice() * item.getQuantity()));

        Glide.with(context)
                .load(item.getImageUrl())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvSku, tvQuantity, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
