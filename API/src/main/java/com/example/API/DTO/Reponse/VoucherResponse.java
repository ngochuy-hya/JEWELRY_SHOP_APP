package com.example.API.DTO.Reponse;

import com.example.API.Entity.Voucher;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherResponse {
    private String code;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscountValue;
    private BigDecimal minOrderAmount;
    private String expiryDate;
    public static VoucherResponse fromEntity(Voucher v) {
        return VoucherResponse.builder()
                .code(v.getCode())
                .description(v.getDescription())
                .discountType(v.getDiscountType().name().toLowerCase())
                .discountValue(v.getDiscountValue())
                .maxDiscountValue(v.getMaxDiscountValue())
                .minOrderAmount(v.getMinOrderAmount() != null ? v.getMinOrderAmount() : BigDecimal.ZERO)
                .expiryDate(v.getExpiryDate().toString())
                .build();
    }

}
