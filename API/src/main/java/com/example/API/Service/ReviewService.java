package com.example.API.Service;

import com.example.API.DTO.Request.CreateReviewRequest;
import com.example.API.Entity.*;
import com.example.API.Repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public String submitReview(CreateReviewRequest request) {
        if (reviewRepository.existsByUser_UserIdAndProduct_ProductId(request.getUserId(), request.getProductId())) {
            throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.getRating())
                .title(request.getTitle())
                .comment(request.getComment())
                .build();

        List<ReviewImage> imageList = new ArrayList<>();
        if (request.getImageUrls() != null) {
            for (String url : request.getImageUrls()) {
                ReviewImage img = ReviewImage.builder()
                        .review(review)
                        .imageUrl(url)
                        .build();
                imageList.add(img);
            }
        }

        review.setImageList(imageList);
        reviewRepository.save(review);

        return "Đánh giá thành công.";
    }
}
