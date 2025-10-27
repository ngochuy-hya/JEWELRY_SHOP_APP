package com.example.app_jewelry.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.app_jewelry.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortByDialogFragment extends BottomSheetDialogFragment {

    public interface OnSortOptionSelectedListener {
        void onSortSelected(String option);
    }

    private OnSortOptionSelectedListener listener;

    public SortByDialogFragment(OnSortOptionSelectedListener listener) {
        this.listener = listener;
    }

    private AppCompatRadioButton radioLatest, radioLowHigh, radioHighLow, radioDiscount;
    private AppCompatRadioButton[] allRadios;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sort_by_dialog, container, false);

        // Gán RadioButtons
        radioLatest = view.findViewById(R.id.radioLatest);
        radioLowHigh = view.findViewById(R.id.radioLowHigh);
        radioHighLow = view.findViewById(R.id.radioHighLow);
        radioDiscount = view.findViewById(R.id.radioDiscount);

        allRadios = new AppCompatRadioButton[]{radioLatest, radioLowHigh, radioHighLow, radioDiscount};

        // Gán các LinearLayout ngoài
        view.findViewById(R.id.itemLatest).setOnClickListener(v -> selectOnly(radioLatest, "Latest"));
        view.findViewById(R.id.itemLowHigh).setOnClickListener(v -> selectOnly(radioLowHigh, "LowToHigh"));
        view.findViewById(R.id.itemHighLow).setOnClickListener(v -> selectOnly(radioHighLow, "HighToLow"));
        view.findViewById(R.id.itemDiscount).setOnClickListener(v -> selectOnly(radioDiscount, "Discount"));

        // Nút đóng
        view.findViewById(R.id.ivClose).setOnClickListener(v -> dismiss());

        return view;
    }

    private void selectOnly(AppCompatRadioButton selected, String option) {
        for (AppCompatRadioButton radio : allRadios) {
            radio.setChecked(radio == selected);
        }

        // Delay 120ms để hiển thị tick xong rồi mới đóng
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (listener != null) listener.onSortSelected(option);
            dismiss();
        }, 120);
    }
}
