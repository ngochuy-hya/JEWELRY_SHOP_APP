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
import com.example.app_jewelry.entity.Order;
import com.example.app_jewelry.entity.OrderItem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    private final List<Order> orders;
    private final Context context;
    private final OnOrderClickListener listener;

    public OrderAdapter(Context context, List<Order> orders, OnOrderClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.listener = listener;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreatedAt, tvStatus, tvProductName, tvMoreItems, tvTotalAmount;
        ImageView imgProduct;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvMoreItems = itemView.findViewById(R.id.tvMoreItems);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }

        public void bind(Order order, OnOrderClickListener listener) {
            // Format ngày (nếu createdAt là dạng yyyy-MM-dd HH:mm:ss hoặc ISO)
            String createdAt = order.getCreatedAt();
            if (createdAt != null && createdAt.length() >= 10) {
                tvCreatedAt.setText(createdAt.substring(0, 10)); // chỉ lấy yyyy-MM-dd
            } else {
                tvCreatedAt.setText("N/A");
            }

            // Trạng thái đơn
            tvStatus.setText(order.getStatus());

            List<OrderItem> items = order.getItems();
            if (items != null && !items.isEmpty()) {
                OrderItem firstItem = items.get(0);
                tvProductName.setText(firstItem.getProductName());

                Glide.with(itemView.getContext())
                        .load(firstItem.getImageUrl())
                        .transform(new CenterCrop(), new RoundedCorners(20))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imgProduct);

                // Hiển thị số sản phẩm thêm
                if (items.size() > 1) {
                    tvMoreItems.setVisibility(View.VISIBLE);
                    tvMoreItems.setText("and " + (items.size() - 1) + " more items");
                } else {
                    tvMoreItems.setVisibility(View.GONE);
                }

                // Tổng tiền
                double total = 0;
                for (OrderItem item : items) {
                    total += item.getUnitPrice() * item.getQuantity();
                }
                tvTotalAmount.setText("Total: ₫" + String.format(Locale.getDefault(), "%,.0f", total));
            } else {
                tvProductName.setText("No items");
                tvMoreItems.setVisibility(View.GONE);
                tvTotalAmount.setText("Total: ₫0");
            }

            itemView.setOnClickListener(v -> listener.onOrderClick(order));
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(orders.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
