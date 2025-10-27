package com.example.app_jewelry.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;
import com.example.app_jewelry.entity.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    public interface OnAddressActionListener {
        void onEdit(Address address);
        void onRemove(Address address);
    }

    private final List<Address> addressList;
    private final OnAddressActionListener listener;

    public AddressAdapter(List<Address> addressList, OnAddressActionListener listener) {
        this.addressList = addressList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);

        if (address.isDefault()) {
            holder.tvName.setText(address.getReceiverName() + " (Default)");
            holder.tvName.setTextColor(Color.BLUE);
        } else {
            holder.tvName.setText(address.getReceiverName());
            holder.tvName.setTextColor(Color.BLACK);
        }

        holder.tvPhone.setText(address.getPhone());
        holder.tvEmail.setText(address.getEmail());

        StringBuilder fullAddr = new StringBuilder(address.getAddressLine());

        if (!TextUtils.isEmpty(address.getArea())) {
            fullAddr.append(", ").append(address.getArea());
        }
        if (!TextUtils.isEmpty(address.getLandmark())) {
            fullAddr.append(", ").append(address.getLandmark());
        }
        fullAddr.append(", ").append(address.getCity()).append(", ").append(address.getState());

        holder.tvFullAddress.setText(fullAddr.toString());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(address));
        holder.btnRemove.setOnClickListener(v -> listener.onRemove(address));
    }


    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvEmail, tvFullAddress;
        Button btnEdit, btnRemove;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvFullAddress = itemView.findViewById(R.id.tvFullAddress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
