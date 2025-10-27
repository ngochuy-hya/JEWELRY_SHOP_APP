package com.example.API.Controller;

import com.example.API.DTO.Reponse.OrderResponse;
import com.example.API.DTO.Request.CreateOrderRequest;
import com.example.API.Entity.Order;
import com.example.API.Repository.OrderRepository;
import com.example.API.Repository.ReviewRepository;
import com.example.API.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository,ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.reviewRepository = reviewRepository;
    }


    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            orderService.createOrder(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(@RequestParam Integer userId) {
        List<OrderResponse> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderDetail(@PathVariable Integer id,
                                                        @RequestParam Integer userId) {
        OrderResponse order = orderService.getOrderDetail(id, userId);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/has-bought")
    public boolean hasUserBoughtProduct(@RequestParam int userId, @RequestParam int productId) {
        boolean hasBought = orderRepository.hasUserBoughtProduct(userId, productId);
        boolean hasReviewed = reviewRepository.hasUserReviewedProduct(userId, productId);
        return hasBought && !hasReviewed;
    }

}
