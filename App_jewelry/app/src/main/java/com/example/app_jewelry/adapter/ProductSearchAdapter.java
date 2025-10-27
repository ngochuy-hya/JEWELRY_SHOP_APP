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
import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.ResultSearchResponse;

import java.util.List;

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ProductViewHolder> {

    private Context context;
    private List<ResultSearchResponse> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(ResultSearchResponse product);
    }

    public ProductSearchAdapter(Context context, List<ResultSearchResponse> productList, OnItemClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ResultSearchResponse product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("%,.0fđ", product.getCurrentPrice()));

        // Load ảnh từ URL
        Glide.with(context)
                .load(product.getMainImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> listener.onClick(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
