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
import com.example.app_jewelry.Service.DTO.reponse.OrderItemResponse;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private final Context context;
    private final List<OrderItemResponse> itemList;
    private String imgURL;

    public OrderItemAdapter(Context context, List<OrderItemResponse> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvVariantName, tvUnitPrice, tvQuantity, tvTotalPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvVariantName = itemView.findViewById(R.id.tvVariantName);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }

        public void bind(OrderItemResponse item, Context context) {
            tvProductName.setText(item.getProductName());
            tvUnitPrice.setText("₫" + String.format("%,.0f", item.getUnitPrice()));
            tvQuantity.setText("x" + item.getQuantity());
            double total = item.getUnitPrice() * item.getQuantity();
            tvTotalPrice.setText("₫" + String.format("%,.0f", total));

            Glide.with(context)
                    .load(item.getImageUrl())
                    .transform(new CenterCrop(), new RoundedCorners(16))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imgProduct);
        }
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        holder.bind(itemList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
