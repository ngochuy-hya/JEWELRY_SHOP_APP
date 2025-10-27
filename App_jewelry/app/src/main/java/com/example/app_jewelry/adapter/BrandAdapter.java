package com.example.app_jewelry.adapter;

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
import com.example.app_jewelry.entity.Brand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<Brand> brandList;
    private OnBrandClickListener listener;

    public interface OnBrandClickListener {
        void onBrandClick(Brand brand);
    }

    public BrandAdapter(List<Brand> brandList, OnBrandClickListener listener) {
        this.brandList = brandList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.textName.setText(brand.getName());

        Glide.with(holder.itemView.getContext())
                .load(brand.getLogoUrl())
                .circleCrop()
                .into(holder.imageLogo);

        // Sự kiện click vào brand
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBrandClick(brand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandList != null ? brandList.size() : 0;
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLogo;
        TextView textName;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLogo = itemView.findViewById(R.id.imageLogo);
            textName = itemView.findViewById(R.id.textName);
        }
    }
}


