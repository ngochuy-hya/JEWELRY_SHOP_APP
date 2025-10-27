package com.example.API.Service;

import com.example.API.DTO.Reponse.*;
import com.example.API.Entity.FlashDeal;
import com.example.API.Entity.Product;
import com.example.API.Entity.ProductVariant;
import com.example.API.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.API.Entity.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProductVariantRepository productVariantRepository;
    private final FlashDealRepository flashDealRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final ReviewRepository reviewRepository;
    private final ProductImageRepository productImageRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;


    public List<ProductArrivalResponse> getNewArrivalProducts(Integer userId) {
        List<Integer> productIds = stockHistoryRepository.findTop10RecentlyImportedProductIds();
        List<Product> products = productRepository.findAllById(productIds);

        return products.stream().map(product -> {
            boolean isFavorite = userId != null && favoriteRepository.existsByUser_UserIdAndProduct_ProductId(userId, product.getProductId());

            Optional<ProductVariant> variantOpt = productVariantRepository
                    .findTopByProduct_ProductIdAndStockGreaterThanOrderByPriceAsc(product.getProductId(), 0);

            if (variantOpt.isEmpty()) return null;

            double price = variantOpt.get().getPrice();
            Double oldPrice = null;

            Optional<FlashDeal> flashDealOpt = flashDealRepository
                    .findTopByProduct_ProductIdAndIsActiveTrueOrderByStartTimeDesc(product.getProductId());

            if (flashDealOpt.isPresent()) {
                FlashDeal deal = flashDealOpt.get();
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(deal.getStartTime()) && now.isBefore(deal.getEndTime())) {
                    oldPrice = price;
                    price = deal.getDiscountPrice();
                }
            }

            return new ProductArrivalResponse(
                    product.getProductId(),
                    product.getName(),
                    product.getMainImage(),
                    price,
                    oldPrice,
                    isFavorite
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<BestsellerProductResponse> getBestsellerProducts(Integer userId) {
        return productRepository.findBestsellerProducts().stream().map(item -> {
            boolean isFavorite = userId != null &&
                    favoriteRepository.existsByUser_UserIdAndProduct_ProductId(userId, item.getProductId());

            Optional<ProductVariant> variantOpt = productVariantRepository
                    .findTopByProduct_ProductIdAndStockGreaterThanOrderByPriceAsc(item.getProductId(), 0);

            if (variantOpt.isEmpty()) return null;

            double price = variantOpt.get().getPrice();
            Double oldPrice = null;

            Optional<FlashDeal> flashDealOpt = flashDealRepository
                    .findTopByProduct_ProductIdAndIsActiveTrueOrderByStartTimeDesc(item.getProductId());

            if (flashDealOpt.isPresent()) {
                FlashDeal deal = flashDealOpt.get();
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(deal.getStartTime()) && now.isBefore(deal.getEndTime())) {
                    oldPrice = price;
                    price = deal.getDiscountPrice();
                }
            }

            return BestsellerProductResponse.builder()
                    .productId(item.getProductId())
                    .productName(item.getProductName())
                    .mainImage(item.getMainImage())
                    .totalSold(item.getTotalSold())
                    .avgRating(item.getAvgRating())
                    .reviewCount(item.getReviewCount())
                    .favorite(isFavorite)
                    .price(price)
                    .oldPrice(oldPrice)
                    .build();

        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByBrandId(int brandId, Integer userId, String sortOption) {
        List<Product> products = productRepository.findByBrandId(brandId).stream()
                .filter(p -> productVariantRepository.existsByProduct_ProductIdAndStockGreaterThan(p.getProductId(), 0))
                .toList();
        List<ProductResponse> list = mapToProductResponses(products, userId);
        return applySortOption(list, sortOption);
    }

    public List<ProductResponse> getProductsByCategoryId(int categoryId, Integer userId, String sortOption) {
        List<Product> products = productRepository.findByCategoryId(categoryId).stream()
                .filter(p -> productVariantRepository.existsByProduct_ProductIdAndStockGreaterThan(p.getProductId(), 0))
                .toList();
        List<ProductResponse> list = mapToProductResponses(products, userId);
        return applySortOption(list, sortOption);
    }
    private List<ProductResponse> applySortOption(List<ProductResponse> list, String sortOption) {
        if (sortOption != null && !sortOption.isBlank()) {
            switch (sortOption) {
                case "LowToHigh" -> list.sort(Comparator.comparing(ProductResponse::getCurrentPrice));
                case "HighToLow" -> list.sort(Comparator.comparing(ProductResponse::getCurrentPrice).reversed());
                case "Discount" -> list.sort(Comparator.comparingDouble((ProductResponse p) ->
                        (p.getOriginalPrice() - p.getCurrentPrice()) / p.getOriginalPrice()).reversed());
            }
        }
        return list;
    }


    public List<ProductResponse> getProductsByType(String type, Integer userId, String sortOption) {
        List<ProductResponse> list = new ArrayList<>();

        if ("today_offer".equals(type)) {
            list = flashDealRepository.getAllTodayOffers().stream()
                    .map(offer -> new ProductResponse(
                            offer.getProductId(),
                            offer.getName(),
                            offer.getMainImage(),
                            offer.getOriginalPrice(),
                            offer.getCurrentPrice(),
                            offer.getRating(),
                            offer.getReviewCount(),
                            isFavorite(userId, offer.getProductId())
                    )).collect(Collectors.toList());
        } else if ("new_arrival".equals(type)) {
            list = getNewArrivalProducts(userId).stream()
                    .map(p -> new ProductResponse(
                            p.getProductId(),
                            p.getName(),
                            p.getMainImage(),
                            p.getOldPrice() != null ? p.getOldPrice() : p.getPrice(),
                            p.getPrice(),
                            0f,
                            0,
                            p.isFavorite()
                    )).collect(Collectors.toList());
        } else if ("best_seller".equals(type)) {
            list = getBestsellerProducts(userId).stream()
                    .map(p -> new ProductResponse(
                            p.getProductId(),
                            p.getProductName(),
                            p.getMainImage(),
                            p.getOldPrice() != null ? p.getOldPrice() : p.getPrice(),
                            p.getPrice(),
                            p.getAvgRating().floatValue(),
                            p.getReviewCount(),
                            p.getFavorite()
                    )).collect(Collectors.toList());
        }

        if (sortOption != null && !sortOption.isBlank()) {
            switch (sortOption) {
                case "LowToHigh" -> list.sort(Comparator.comparing(ProductResponse::getCurrentPrice));
                case "HighToLow" -> list.sort(Comparator.comparing(ProductResponse::getCurrentPrice).reversed());
                case "Discount" -> list.sort(Comparator.comparingDouble((ProductResponse p) ->
                        (p.getOriginalPrice() - p.getCurrentPrice()) / p.getOriginalPrice()).reversed());
            }
        }


        return list;
    }




    private List<ProductResponse> mapToProductResponses(List<Product> products, Integer userId) {
        return products.stream().map(product -> {
            Optional<ProductVariant> variantOpt = productVariantRepository
                    .findTopByProduct_ProductIdAndStockGreaterThanOrderByPriceAsc(product.getProductId(), 0);

            if (variantOpt.isEmpty()) return null;

            double originalPrice = variantOpt.get().getPrice();
            double finalPrice = originalPrice;

            Optional<FlashDeal> flashDealOpt = flashDealRepository
                    .findTopByProduct_ProductIdAndIsActiveTrueOrderByStartTimeDesc(product.getProductId());

            if (flashDealOpt.isPresent()) {
                FlashDeal deal = flashDealOpt.get();
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(deal.getStartTime()) && now.isBefore(deal.getEndTime())) {
                    finalPrice = deal.getDiscountPrice();
                }
            }

            float avgRating = reviewRepository.getAverageRatingByProductId(product.getProductId());
            int reviewCount = reviewRepository.countByProductProductId(product.getProductId());

            return new ProductResponse(
                    product.getProductId(),
                    product.getName(),
                    product.getMainImage(),
                    originalPrice,
                    finalPrice,
                    avgRating,
                    reviewCount,
                    isFavorite(userId, product.getProductId())
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    private boolean isFavorite(Integer userId, int productId) {
        return userId != null && favoriteRepository.existsByUser_UserIdAndProduct_ProductId(userId, productId);
    }


    public ProductImageResponse getProductImages(int productId) {
        String mainImage = productRepository.findMainImageByProductId(productId);
        List<String> additionalImages = productImageRepository.findImageUrlsByProductId(productId);

        return new ProductImageResponse(mainImage, additionalImages);
    }
    public ProductDetailResponse getProductDetail(Integer productId, Integer userId) {
        Product product = productRepository.findProductWithBrandAndCategory(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Ảnh phụ
        List<ProductImage> additionalImages = productImageRepository.findByProduct_ProductId(productId);

        // Biến thể
        List<ProductVariant> variants = productVariantRepository.findByProduct_ProductIdAndStockGreaterThan(productId, 0);
        List<ProductVariantResponse> variantResponses = variants.stream()
                .map(v -> ProductVariantResponse.builder()
                        .variantId(v.getVariantId())
                        .color(v.getColor())
                        .size(v.getSize())
                        .price(v.getPrice())
                        .stock(v.getStock())
                        .build())
                .toList();

        // Đánh giá + ảnh
        List<Review> reviews = reviewRepository.findReviewsWithImagesByProductId(productId);
        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(r -> ReviewResponse.builder()
                        .userName(r.getUser().getFullName())
                        .comment(r.getComment())
                        .rating(r.getRating())
                        .title(r.getTitle())
                        .createdAt(r.getCreatedAt())
                        .imageUrls(r.getImageList().stream()
                                .map(ReviewImage::getImageUrl)
                                .toList())
                        .build())
                .toList();

        // Trung bình và số đánh giá
        Double avgRating = reviewRepository.getAverageRating(productId);
        Long reviewCount = reviewRepository.getReviewCount(productId);

        // Yêu thích
        boolean isFavorite = userId != null && favoriteRepository.existsByUser_UserIdAndProduct_ProductId(userId, productId);

        // Lấy giá từ biến thể đầu tiên
        double originalPrice = variants.stream().findFirst().map(ProductVariant::getPrice).orElse(0.0);
        double finalPrice = originalPrice;
        double discountPercent = 0.0;
        Optional<FlashDeal> flashDealOpt = flashDealRepository.findTopByProduct_ProductIdAndIsActiveTrueOrderByStartTimeDesc(productId);
        if (flashDealOpt.isPresent()) {
            FlashDeal deal = flashDealOpt.get();
            finalPrice = deal.getDiscountPrice();
            if (originalPrice > 0) {
                discountPercent = 100.0 * (originalPrice - finalPrice) / originalPrice;
            }
        }
        // Thống kê số sao
        List<Object[]> starStats = reviewRepository.countStarsByRating(productId);
        Map<Integer, Integer> starCount = new HashMap<>();
        for (Object[] row : starStats) {
            Integer rating = (Integer) row[0];
            Long count = (Long) row[1];
            starCount.put(rating, count.intValue());
        }
        for (int i = 1; i <= 5; i++) {
            starCount.putIfAbsent(i, 0);
        }


        return ProductDetailResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .mainImage(product.getMainImage())
                .additionalImages(additionalImages.stream().map(ProductImage::getImageUrl).toList())
                .price(finalPrice)
                .originalPrice(originalPrice)
                .discountPercent(discountPercent)
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .categoryDescription(product.getCategory() != null ? product.getCategory().getDescription() : null)
                .averageRating(avgRating != null ? avgRating.floatValue() : 0f)
                .reviewCount(reviewCount != null ? reviewCount.intValue() : 0)
                .isFavorite(isFavorite)
                .variants(variantResponses)
                .reviews(reviewResponses)
                .starCount(starCount)
                .build();
    }
    public FilterOptionsResponse getFilterOptions() {
        List<String> brands = brandRepository.findAllBrandNames();
        List<String> categories = categoryRepository.findAllCategoryNames();
        List<String> colors = productVariantRepository.findDistinctColors();
        List<String> sizes = productVariantRepository.findDistinctSizes();

        return new FilterOptionsResponse(brands, categories, colors, sizes);
    }
    public List<ProductResponse> getFilteredProducts(List<String> brandNames, List<String> categoryNames,
                                                     List<String> sizes, Float minPrice, Float maxPrice,
                                                     Integer userId, String sortOption) {
        Specification<Product> spec = ProductSpecification.withFilters(
                brandNames, categoryNames, sizes, minPrice, maxPrice
        );

        List<Product> filteredProducts = productRepository.findAll(spec).stream()
                .filter(p -> productVariantRepository.existsByProduct_ProductIdAndStockGreaterThan(p.getProductId(), 0))
                .toList();
        List<ProductResponse> list = mapToProductResponses(filteredProducts, userId);
        return applySortOption(list, sortOption);
    }
    public List<ProductResponse> getProductsByIds(List<Integer> productIds, Integer userId) {
        if (productIds == null || productIds.isEmpty()) return Collections.emptyList();

        List<Product> products = productRepository.findAllById(productIds).stream()
                .filter(p -> productVariantRepository.existsByProduct_ProductIdAndStockGreaterThan(p.getProductId(), 0))
                .toList();

        List<ProductResponse> responseList = mapToProductResponses(products, userId);
        Map<Integer, ProductResponse> responseMap = responseList.stream()
                .collect(Collectors.toMap(r -> r.getProductID(), r -> r));

        return productIds.stream()
                .map(responseMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public List<ResultSearchResponse> searchProductsByName(String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);

        return products.stream()
                .filter(p -> p.getVariants().stream().anyMatch(v -> v.getStock() > 0))
                .map(p -> {
                    Optional<ProductVariant> availableVariant = p.getVariants().stream()
                            .filter(v -> v.getStock() > 0)
                            .findFirst();

                    double price = availableVariant.map(ProductVariant::getPrice).orElse(0.0);

                    return ResultSearchResponse.builder()
                            .productID(p.getProductId())
                            .name(p.getName())
                            .currentPrice(price)
                            .mainImage(p.getMainImage())
                            .build();
                })
                .collect(Collectors.toList());
    }


}
