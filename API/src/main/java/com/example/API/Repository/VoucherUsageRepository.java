package com.example.API.Repository;

import com.example.API.Entity.VoucherUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherUsageRepository extends JpaRepository<VoucherUsage, Integer> {
    long countByUser_UserIdAndVoucher_VoucherId(Integer userId, Integer voucherId);

}