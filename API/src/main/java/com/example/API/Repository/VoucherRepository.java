package com.example.API.Repository;

import com.example.API.Entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    List<Voucher> findByIsActiveTrueAndExpiryDateAfter(java.time.LocalDate today);
    Optional<Voucher> findByCodeAndIsActiveTrue(String code);
}
