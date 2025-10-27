package com.example.app_jewelry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    public interface OnApplyClickListener {
        void onApply(VoucherResponse voucher);
    }

    private final List<VoucherResponse> voucherList;
    private final OnApplyClickListener listener;

    public VoucherAdapter(List<VoucherResponse> voucherList, OnApplyClickListener listener) {
        this.voucherList = voucherList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        VoucherResponse voucher = voucherList.get(position);

        // Set voucher code
        holder.tvVoucherCode.setText(voucher.getCode());

        // Build discount text
        String discountText;
        if ("percent".equalsIgnoreCase(voucher.getDiscountType())) {
            discountText = "Get " + (int) voucher.getDiscountValue() + "% off";
            if (voucher.getMaxDiscountValue() != null) {
                discountText += " (max " + formatCurrency(voucher.getMaxDiscountValue()) + ")";
            }
        } else {
            discountText = "Save " + formatCurrency(voucher.getDiscountValue());
        }

        // Add minimum order condition
        String conditionText = discountText;
        if (voucher.getMinOrderAmount() > 0) {
            conditionText += " on orders over " + formatCurrency(voucher.getMinOrderAmount());
        }

        holder.tvVoucherCondition.setText(conditionText);
        holder.tvVoucherExpiry.setText("Valid until: " + voucher.getExpiryDate());

    }

    @Override
    public int getItemCount() {
        return voucherList != null ? voucherList.size() : 0;
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView tvVoucherCode, tvVoucherCondition, tvVoucherExpiry;
        Button btnUseVoucher;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVoucherCode = itemView.findViewById(R.id.tvVoucherCode);
            tvVoucherCondition = itemView.findViewById(R.id.tvVoucherCondition);
            tvVoucherExpiry = itemView.findViewById(R.id.tvVoucherExpiry);
        }
    }

    private String formatCurrency(double value) {
        return String.format("%,.0f", value).replace(",", ".") + "đ";
    }
}
