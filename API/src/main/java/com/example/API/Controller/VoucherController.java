package com.example.API.Controller;


import com.example.API.DTO.Reponse.VoucherResponse;
import com.example.API.Entity.Voucher;
import com.example.API.Service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/available")
    public List<VoucherResponse> getAvailableVouchers(@RequestParam Integer userId) {
        List<Voucher> vouchers = voucherService.getAvailableVouchersForUser(userId);
        return vouchers.stream().map(VoucherResponse::fromEntity).toList();
    }

}