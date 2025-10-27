package com.example.API.Service;

import com.example.API.DTO.Reponse.OrderItemResponse;
import com.example.API.DTO.Reponse.OrderResponse;
import com.example.API.DTO.Request.CreateOrderRequest;
import com.example.API.Entity.*;
import com.example.API.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherUsageRepository voucherUsageRepository;
    private final FlashDealRepository flashDealRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ProductVariantRepository productVariantRepository,
                        AddressRepository addressRepository,
                        UserRepository userRepository,
                        VoucherRepository voucherRepository,
                        VoucherUsageRepository voucherUsageRepository,
                        FlashDealRepository flashDealRepository,
                        StockHistoryRepository stockHistoryRepository) {
        this.flashDealRepository = flashDealRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.voucherRepository = voucherRepository;
        this.voucherUsageRepository = voucherUsageRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) throws Exception {
        // 1. Lấy user & địa chỉ
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        var address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new Exception("Address not found"));

        // 2. Kiểm tra tồn kho
        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            var variant = productVariantRepository.findById(itemReq.getVariantId())
                    .orElseThrow(() -> new Exception("Variant not found: " + itemReq.getVariantId()));
            if (variant.getStock() < itemReq.getQuantity()) {
                throw new Exception("Insufficient stock for variant " + variant.getVariantId());
            }
        }

        // 3. Tính tổng tiền với giá flash deal nếu có
        BigDecimal totalAmount = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        // Tạm lưu variant và giá thực để tái sử dụng ở bước 6
        record ItemInfo(ProductVariant variant, BigDecimal unitPrice, int quantity) {}
        List<ItemInfo> itemInfos = new java.util.ArrayList<>();

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            var variant = productVariantRepository.findById(itemReq.getVariantId()).orElseThrow(() -> new Exception("Variant not found: " + itemReq.getVariantId()));

            AtomicReference<BigDecimal> priceRef = new AtomicReference<>(BigDecimal.valueOf(variant.getPrice()));

            flashDealRepository.findActiveDealForProduct(variant.getProduct().getProductId(), now)
                    .ifPresent(deal -> priceRef.set(BigDecimal.valueOf(deal.getDiscountPrice())));

            BigDecimal price = priceRef.get();
            BigDecimal quantity = BigDecimal.valueOf(itemReq.getQuantity());
            totalAmount = totalAmount.add(price.multiply(quantity));

            itemInfos.add(new ItemInfo(variant, price, itemReq.getQuantity()));
        }


        // 4. Áp dụng voucher
        BigDecimal discount = BigDecimal.ZERO;
        Voucher voucher = null;

        if (request.getVoucherCode() != null && !request.getVoucherCode().isEmpty()) {
            voucher = voucherRepository.findByCodeAndIsActiveTrue(request.getVoucherCode())
                    .orElseThrow(() -> new Exception("Voucher invalid or expired"));

            if (voucher.getMinOrderAmount() != null && totalAmount.compareTo(voucher.getMinOrderAmount()) < 0) {
                throw new Exception("Order amount does not meet voucher minimum requirement");
            }

            if (voucher.getDiscountType() == DiscountType.percent) {
                discount = totalAmount.multiply(voucher.getDiscountValue().divide(BigDecimal.valueOf(100)));
                if (voucher.getMaxDiscountValue() != null && discount.compareTo(voucher.getMaxDiscountValue()) > 0) {
                    discount = voucher.getMaxDiscountValue();
                }
            } else {
                discount = voucher.getDiscountValue();
            }
        }

        BigDecimal finalAmount = totalAmount.subtract(discount);

        // 5. Lưu Order
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setVoucherCode(request.getVoucherCode());
        order.setTotalAmount(finalAmount.doubleValue());
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        // 6. Tạo order items + giảm tồn kho
        for (ItemInfo info : itemInfos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVariant(info.variant());
            orderItem.setProductName(info.variant().getProduct().getName());
            orderItem.setVariantName(info.variant().getColor() + " / " + info.variant().getSize());
            orderItem.setUnitPrice(info.unitPrice().doubleValue());
            orderItem.setQuantity(info.quantity());

            orderItemRepository.save(orderItem);

            // Giảm tồn kho
            info.variant().setStock(info.variant().getStock() - info.quantity());
            productVariantRepository.save(info.variant());

            StockHistory history = new StockHistory();
            history.setVariant(info.variant());
            history.setType("order");
            history.setQuantity(info.quantity());
            history.setUnitPrice(info.unitPrice().doubleValue());
            history.setCreatedAt(now);
            stockHistoryRepository.save(history);

        }

        // 7. Lưu voucher usage
        if (voucher != null) {
            VoucherUsage usage = new VoucherUsage();
            usage.setUser(user);
            usage.setVoucher(voucher);
            usage.setOrder(order);
            usage.setUsedAt(now);
            voucherUsageRepository.save(usage);
        }

        return order;
    }
    public List<OrderResponse> getOrdersByUser(Integer userId) {
        List<Order> orders = orderRepository.findByUser_UserIdOrderByCreatedAtDesc(userId);
        return orders.stream().map(this::toOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderDetail(Integer orderId, Integer userId) {
        Order order = orderRepository.findByOrderIdAndUser_UserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return toOrderResponse(order);
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
            OrderItemResponse dto = new OrderItemResponse();
            dto.setVariantId(item.getVariant().getVariantId());
            dto.setProductName(item.getProductName());
            dto.setUnitPrice(item.getUnitPrice());
            dto.setQuantity(item.getQuantity());
            dto.setImageUrl(item.getVariant().getColor());
            return dto;
        }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt().toString());
        response.setTotalAmount(order.getTotalAmount());
        response.setVoucherCode(order.getVoucherCode());
        response.setItems(itemResponses);
        return response;
    }

}
