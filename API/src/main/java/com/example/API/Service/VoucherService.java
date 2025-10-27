package com.example.API.Service;

import com.example.API.Entity.Voucher;
import com.example.API.Repository.VoucherRepository;
import com.example.API.Repository.VoucherUsageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherUsageRepository voucherUsageRepository;

    public VoucherService(VoucherRepository voucherRepository, VoucherUsageRepository voucherUsageRepository) {
        this.voucherRepository = voucherRepository;
        this.voucherUsageRepository = voucherUsageRepository;
    }

    public List<Voucher> getAvailableVouchersForUser(Integer userId) {
        LocalDate today = LocalDate.now();
        List<Voucher> allAvailable = voucherRepository.findByIsActiveTrueAndExpiryDateAfter(today);

        return allAvailable.stream()
                .filter(voucher -> {
                    if (voucher.getUsageLimitPerUser() != null) {
                        long usedCount = voucherUsageRepository.countByUser_UserIdAndVoucher_VoucherId(userId, voucher.getVoucherId());
                        return usedCount < voucher.getUsageLimitPerUser();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
