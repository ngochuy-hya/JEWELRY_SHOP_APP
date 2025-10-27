package com.example.app_jewelry.adapter;

import android.content.Context;
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
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.CartItemResponse;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartItemListener {
        void onQuantityChanged(CartItemResponse item, int newQuantity);
        void onItemRemoved(CartItemResponse removedItem);
        void onSelectionChanged();
    }

    private final Context context;
    private final List<CartItemResponse> cartItems;
    private final CartItemListener listener;

    public CartAdapter(Context context, List<CartItemResponse> cartItems, CartItemListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemResponse item = cartItems.get(position);

        holder.tvSku.setText("SKU: " + item.getSku());
        holder.tvProductName.setText(item.getProductName());
        holder.tvPrice.setText(item.getPrice() + " ₫");
        holder.tvOldPrice.setText(item.getOldPrice() + " ₫");
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvPercentOff.setText("(" + item.getDiscountPercent() + "%)");
        holder.tvSpecialDeal.setText("Special discount: " + item.getDealDiscount() + " ₫");

        double total = item.getQuantity() * item.getPrice();
        holder.tvTotalItemPrice.setText("Total: " + total + " ₫");

        if (item.getPrice() < item.getOldPrice()) {
            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvPercentOff.setVisibility(View.VISIBLE);
            holder.tvSpecialDeal.setVisibility(View.VISIBLE);
        } else {
            holder.tvOldPrice.setPaintFlags(0);
            holder.tvPercentOff.setVisibility(View.GONE);
            holder.tvSpecialDeal.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(item.getImageUrl())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.imgProduct);

        // Increase
        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            if (currentQuantity < item.getStock()) {
                int newQuantity = currentQuantity + 1;
                item.setQuantity(newQuantity);
                notifyItemChanged(position);
                listener.onQuantityChanged(item, newQuantity);
            } else {
                Toast.makeText(context, "Không thể vượt quá tồn kho (" + item.getStock() + ")", Toast.LENGTH_SHORT).show();
            }
        });

        // Decrease
        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            if (currentQuantity > 1) {
                int newQuantity = currentQuantity - 1;
                item.setQuantity(newQuantity);
                notifyItemChanged(position);
                listener.onQuantityChanged(item, newQuantity);
            } else {
                Toast.makeText(context, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete
        holder.btnDelete.setOnClickListener(v -> {
            CartItemResponse removed = cartItems.remove(position);
            notifyItemRemoved(position);
            listener.onItemRemoved(removed);
        });

        // Toggle RadioButton (chọn & bỏ chọn)
        holder.checkboxSelect.setChecked(item.isSelected());
        holder.checkboxSelect.setOnClickListener(v -> {
            boolean current = item.isSelected();
            item.setSelected(!current);
            notifyItemChanged(position);
            listener.onSelectionChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void removeItem(CartItemResponse item) {
        int index = cartItems.indexOf(item);
        if (index != -1) {
            cartItems.remove(index);
            notifyItemRemoved(index);
        }
    }

    public List<CartItemResponse> getSelectedItems() {
        List<CartItemResponse> selected = new ArrayList<>();
        for (CartItemResponse item : cartItems) {
            if (item.isSelected()) {
                selected.add(item);
            }
        }
        return selected;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnIncrease, btnDecrease, btnDelete;
        TextView tvSku, tvProductName, tvPrice, tvOldPrice, tvPercentOff,
                tvSpecialDeal, tvQuantity, tvTotalItemPrice;
        android.widget.RadioButton checkboxSelect;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvPercentOff = itemView.findViewById(R.id.tvPercentOff);
            tvSpecialDeal = itemView.findViewById(R.id.tvSpecialDeal);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalItemPrice = itemView.findViewById(R.id.tvTotalItemPrice);
            checkboxSelect = itemView.findViewById(R.id.checkboxSelect);
        }
    }
}
