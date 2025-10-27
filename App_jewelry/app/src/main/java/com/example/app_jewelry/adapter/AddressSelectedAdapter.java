package com.example.app_jewelry.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;
import com.example.app_jewelry.entity.Address;

import java.util.List;

public class AddressSelectedAdapter extends RecyclerView.Adapter<AddressSelectedAdapter.AddressViewHolder> {

    private final Context context;
    private final List<Address> addressList;
    private int selectedPosition = -1;

    public interface OnAddressSelectListener {
        void onSelected(Address selectedAddress);
    }

    private final OnAddressSelectListener listener;

    public AddressSelectedAdapter(Context context, List<Address> addressList, OnAddressSelectListener listener) {
        this.context = context;
        this.addressList = addressList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_selected, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);

        holder.tvName.setText(address.getReceiverName());
        holder.tvPhone.setText(address.getPhone());

        // Email có thể null hoặc rỗng
        if (!TextUtils.isEmpty(address.getEmail())) {
            holder.tvEmail.setVisibility(View.VISIBLE);
            holder.tvEmail.setText(address.getEmail());
        } else {
            holder.tvEmail.setVisibility(View.GONE);
        }

        String fullAddress = address.getAddressLine();
        if (!TextUtils.isEmpty(address.getArea())) fullAddress += ", " + address.getArea();
        if (!TextUtils.isEmpty(address.getCity())) fullAddress += ", " + address.getCity();
        if (!TextUtils.isEmpty(address.getState())) fullAddress += ", " + address.getState();
        if (!TextUtils.isEmpty(address.getLandmark())) fullAddress += " (" + address.getLandmark() + ")";

        holder.tvFullAddress.setText(fullAddress);

        holder.radioSelect.setChecked(position == selectedPosition);

        View.OnClickListener selectListener = v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            if (previousPosition != -1) notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
            listener.onSelected(addressList.get(selectedPosition));
        };

        holder.radioSelect.setOnClickListener(selectListener);
        holder.itemView.setOnClickListener(selectListener);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public Address getSelectedAddress() {
        return selectedPosition != -1 ? addressList.get(selectedPosition) : null;
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvEmail, tvFullAddress;
        RadioButton radioSelect;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvFullAddress = itemView.findViewById(R.id.tvFullAddress);
            radioSelect = itemView.findViewById(R.id.radioSelect);
        }
    }
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyItemChanged(position);
    }
}
