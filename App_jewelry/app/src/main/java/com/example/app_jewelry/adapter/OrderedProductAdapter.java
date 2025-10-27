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

public class OrderedProductAdapter extends RecyclerView.Adapter<OrderedProductAdapter.OrderedViewHolder> {

    private Context context;
    private List<CartItemResponse> cartItems;

    public OrderedProductAdapter(Context context, List<CartItemResponse> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public OrderedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordered_product, parent, false);
        return new OrderedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedViewHolder holder, int position) {
        CartItemResponse item = cartItems.get(position);

        holder.tvSku.setText("SKU: " + item.getSku());
        holder.tvProductName.setText(item.getProductName());
        holder.tvPrice.setText(String.format("%,.0f đ", item.getPrice()));
        holder.tvOldPrice.setText(String.format("%,.0f đ", item.getOldPrice()));
        holder.tvPercentOff.setText("(" + item.getDiscountPercent() + "% off)");
        holder.tvSpecialDeal.setText("Extra discount: " + String.format("%,.0f đ", item.getDealDiscount()));
        holder.tvQuantity.setText("Quantity: " + item.getQuantity());

        double total = item.getQuantity() * item.getPrice();
        holder.tvTotalItemPrice.setText(String.format("Total: %,.0f đ", total));

        Glide.with(context)
                .load(item.getImageUrl())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.imgProduct);
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class OrderedViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvSku, tvProductName, tvPrice, tvOldPrice, tvPercentOff,
                tvSpecialDeal, tvQuantity, tvTotalItemPrice;

        public OrderedViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvPercentOff = itemView.findViewById(R.id.tvPercentOff);
            tvSpecialDeal = itemView.findViewById(R.id.tvSpecialDeal);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalItemPrice = itemView.findViewById(R.id.tvTotalItemPrice);
        }
    }
}
