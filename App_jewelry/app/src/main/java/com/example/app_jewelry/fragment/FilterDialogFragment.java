package com.example.app_jewelry.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.FilterOptionsResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDialogFragment extends BottomSheetDialogFragment {

    private RangeSlider priceSlider;
    private TextView tvMinPrice, tvMaxPrice;
    private ChipGroup chipGroupCategory, chipGroupBrand, chipGroupSize;
    private TextView btnClearAll;
    private MaterialButton btnCancel, btnApply;
    private TextView tvCategory, tvBrand;
    apiManager api = new apiManager();

    // Constants
    private static final String ARG_FILTER_CONTEXT = "filter_context"; // brand | category | all

    // Listener
    public interface FilterApplyListener {
        void onApplyFilter(List<String> categories, List<String> brands, List<String> sizes, List<Float> priceRange);
    }

    private FilterApplyListener filterApplyListener;

    public static FilterDialogFragment newInstance(String context) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILTER_CONTEXT, context);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FilterApplyListener) {
            filterApplyListener = (FilterApplyListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        priceSlider = view.findViewById(R.id.priceSlider);
        tvMinPrice = view.findViewById(R.id.tvMinPrice);
        tvMaxPrice = view.findViewById(R.id.tvMaxPrice);
        chipGroupCategory = view.findViewById(R.id.chipGroupCategory);
        chipGroupBrand = view.findViewById(R.id.chipGroupBrand);
        chipGroupSize = view.findViewById(R.id.chipGroupSize);
        btnClearAll = view.findViewById(R.id.btnClearAll);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnApply = view.findViewById(R.id.btnApply);
        tvCategory = view.findViewById(R.id.tvCategory);
        tvBrand = view.findViewById(R.id.tvBrand);

        // Load visibility
        String context = getArguments() != null ? getArguments().getString(ARG_FILTER_CONTEXT) : "all";
        updateVisibilityByContext(context);

        // Set default price
        priceSlider.setValues(0f, 5000000f);
        updatePriceLabels(priceSlider.getValues());

        priceSlider.addOnChangeListener((slider, value, fromUser) -> updatePriceLabels(slider.getValues()));

        // Clear All
        btnClearAll.setOnClickListener(v -> {
            priceSlider.setValues(0f, 5000000f);
            chipGroupCategory.clearCheck();
            chipGroupBrand.clearCheck();
            chipGroupSize.clearCheck();
            updatePriceLabels(priceSlider.getValues());
        });

        // Cancel
        btnCancel.setOnClickListener(v -> dismiss());

        // Apply
        btnApply.setOnClickListener(v -> {
            if (filterApplyListener != null) {
                filterApplyListener.onApplyFilter(
                        getCheckedChips(chipGroupCategory),
                        getCheckedChips(chipGroupBrand),
                        getCheckedChips(chipGroupSize),
                        priceSlider.getValues()
                );
            }
            dismiss();
        });

        // Apply chip effect for all
        applyChipClickEffect(chipGroupCategory);
        applyChipClickEffect(chipGroupBrand);
        applyChipClickEffect(chipGroupSize);
        loadFilterOptions();
    }

    private void updateVisibilityByContext(String context) {
        boolean showCategory = context.equals("brand") || context.equals("all");
        boolean showBrand = context.equals("category") || context.equals("all");

        tvCategory.setVisibility(showCategory ? View.VISIBLE : View.GONE);
        chipGroupCategory.setVisibility(showCategory ? View.VISIBLE : View.GONE);
        tvBrand.setVisibility(showBrand ? View.VISIBLE : View.GONE);
        chipGroupBrand.setVisibility(showBrand ? View.VISIBLE : View.GONE);
    }

    private void updatePriceLabels(List<Float> values) {
        if (values != null && values.size() >= 2) {
            tvMinPrice.setText(String.format("VND %,d", Math.round(values.get(0))));
            tvMaxPrice.setText(String.format("VND %,d", Math.round(values.get(1))));
        }
    }

    private List<String> getCheckedChips(ChipGroup group) {
        List<String> selected = new ArrayList<>();
        for (int i = 0; i < group.getChildCount(); i++) {
            Chip chip = (Chip) group.getChildAt(i);
            if (chip.isChecked()) {
                selected.add(chip.getText().toString());
            }
        }
        return selected;
    }

    private void applyChipClickEffect(ChipGroup chipGroup) {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    chip.setChipBackgroundColorResource(R.color.gold);
                    chip.setTextColor(getResources().getColor(android.R.color.white));
                } else {
                    chip.setChipBackgroundColorResource(R.color.white);
                    chip.setTextColor(getResources().getColor(R.color.black));
                }
            });
        }
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
    private void loadFilterOptions() {
        api.getFilterOptions(new Callback<FilterOptionsResponse>() {
            @Override
            public void onResponse(Call<FilterOptionsResponse> call, Response<FilterOptionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FilterOptionsResponse data = response.body();

                    addChips(chipGroupBrand, data.getBrands());
                    addChips(chipGroupCategory, data.getCategories());
                    addChips(chipGroupSize, data.getSizes());

                    // Re-apply chip click style (vì chip tạo động)
                    applyChipClickEffect(chipGroupBrand);
                    applyChipClickEffect(chipGroupCategory);
                    applyChipClickEffect(chipGroupSize);
                }
            }

            @Override
            public void onFailure(Call<FilterOptionsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi tải bộ lọc", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addChips(ChipGroup group, List<String> items) {
        group.removeAllViews();
        for (String item : items) {
            Chip chip = new Chip(getContext());
            chip.setText(item);
            chip.setCheckable(true);
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(getResources().getColor(R.color.black));
            group.addView(chip);
        }
    }
    public void setFilterApplyListener(FilterApplyListener listener) {
        this.filterApplyListener = listener;
    }
}
