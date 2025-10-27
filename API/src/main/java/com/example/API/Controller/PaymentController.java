package com.example.API.Controller;

import com.example.API.DTO.Request.PayOSRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PayOS payOS;

    public PaymentController(PayOS payOS) {
        this.payOS = payOS;
    }

    @PostMapping("/create")
    public ObjectNode createQRCode(@RequestBody PayOSRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        try {
            long orderCode = System.currentTimeMillis(); // hoặc logic sinh code khác

            // Tạo item đơn giản (bắt buộc)
            ItemData item = ItemData.builder()
                    .name("Thanh toán đơn hàng") // hoặc lấy từ request nếu muốn
                    .price(request.getAmount())
                    .quantity(1)
                    .build();

            // Tạo payment data
            PaymentData payment = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(request.getAmount())
                    .description(request.getDescription())
                    .returnUrl(request.getReturnUrl())
                    .cancelUrl(request.getCancelUrl())
                    .item(item)
                    .build();

            // Gọi SDK để tạo QR/payment link
            CheckoutResponseData result = payOS.createPaymentLink(payment);

            response.put("error", 0);
            response.put("message", "success");
            response.set("data", mapper.valueToTree(result));
            response.put("orderCode", orderCode); // Trả thêm mã orderCode để client lưu lại dùng check trạng thái
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @GetMapping("/status/{orderCode}")
    public ObjectNode getPaymentStatus(@PathVariable("orderCode") long orderCode) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        try {
            vn.payos.type.PaymentLinkData paymentLinkData = payOS.getPaymentLinkInformation(orderCode);

            String status = paymentLinkData.getStatus();

            response.put("error", 0);
            response.put("message", "success");
            response.put("status", status);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("status", null);
            return response;
        }
    }


}
