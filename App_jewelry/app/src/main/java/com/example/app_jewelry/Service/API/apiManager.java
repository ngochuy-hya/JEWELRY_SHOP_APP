package com.example.app_jewelry.Service.API;

import com.example.app_jewelry.Service.API.Address.AddressService;
import com.example.app_jewelry.Service.API.Auth.AuthService;
import com.example.app_jewelry.Service.API.Brands.BrandService;
import com.example.app_jewelry.Service.API.Cart.CartService;
import com.example.app_jewelry.Service.API.Category.CategoryService;
import com.example.app_jewelry.Service.API.Conversation.ConversationService;
import com.example.app_jewelry.Service.API.Favorite.FavoriteService;
import com.example.app_jewelry.Service.API.FlashDeal.FlashDealService;
import com.example.app_jewelry.Service.API.Message.MessageService;
import com.example.app_jewelry.Service.API.Order.OrderService;
import com.example.app_jewelry.Service.API.Payment.PaymentService;
import com.example.app_jewelry.Service.API.Product.ProductService;
import com.example.app_jewelry.Service.API.Reviews.ReviewService;
import com.example.app_jewelry.Service.API.Slider.SliderService;
import com.example.app_jewelry.Service.API.User.UserService;
import com.example.app_jewelry.Service.API.Voucher.VoucherService;
import com.example.app_jewelry.Service.DTO.reponse.BestsellerProductResponse;
import com.example.app_jewelry.Service.DTO.reponse.FilterOptionsResponse;
import com.example.app_jewelry.Service.DTO.reponse.LoginResponse;
import com.example.app_jewelry.Service.DTO.reponse.MessageResponse;
import com.example.app_jewelry.Service.DTO.reponse.OrderResponse;
import com.example.app_jewelry.Service.DTO.reponse.PayOSResponse;
import com.example.app_jewelry.Service.DTO.reponse.PaymentStatusResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductArrivalResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductDetailResponse;
import com.example.app_jewelry.Service.DTO.reponse.ProductResponse;
import com.example.app_jewelry.Service.DTO.reponse.RegisterResponse;
import com.example.app_jewelry.Service.DTO.reponse.ResultSearchResponse;
import com.example.app_jewelry.Service.DTO.reponse.TodayOfferResponse;
import com.example.app_jewelry.Service.DTO.reponse.UserResponse;
import com.example.app_jewelry.Service.DTO.reponse.VoucherResponse;
import com.example.app_jewelry.Service.DTO.request.AddToCartRequest;
import com.example.app_jewelry.Service.DTO.request.BestsellerRequest;
import com.example.app_jewelry.Service.DTO.request.ChangePasswordRequest;
import com.example.app_jewelry.Service.DTO.request.CreateMessageRequest;
import com.example.app_jewelry.Service.DTO.request.CreateOrderRequest;
import com.example.app_jewelry.Service.DTO.request.CreateReviewRequest;
import com.example.app_jewelry.Service.DTO.request.LoginRequest;
import com.example.app_jewelry.Service.DTO.request.PayOSRequest;
import com.example.app_jewelry.Service.DTO.request.ProductArrivalRequest;
import com.example.app_jewelry.Service.DTO.request.RegisterRequest;
import com.example.app_jewelry.Service.DTO.request.UpdateUserRequest;
import com.example.app_jewelry.entity.Address;
import com.example.app_jewelry.entity.Brand;
import com.example.app_jewelry.entity.BulkDeals;
import com.example.app_jewelry.entity.Category;
import com.example.app_jewelry.entity.Conversation;
import com.example.app_jewelry.entity.Slider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class apiManager {
    private final SliderService sliderService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final FlashDealService flashDeal;
    private final ProductService productService;
    private final FavoriteService favoriteApi;
    private final AddressService addressService;
    private final CartService cartService;
    private final VoucherService voucherService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final MessageService messageService;
    private final AuthService authService;
    private final ConversationService conversationService;
    private final UserService userService;

    public apiManager() {
        this.sliderService = RetrofitClient.getInstance().create(SliderService.class);
        this.categoryService = RetrofitClient.getInstance().create(CategoryService.class);
        this.brandService = RetrofitClient.getInstance().create(BrandService.class);
        this.flashDeal = RetrofitClient.getInstance().create(FlashDealService.class);
        this.productService = RetrofitClient.getInstance().create(ProductService.class);
        this.favoriteApi = RetrofitClient.getInstance().create(FavoriteService.class);
        this.addressService = RetrofitClient.getInstance().create(AddressService.class);
        this.cartService = RetrofitClient.getInstance().create(CartService.class);
        this.voucherService = RetrofitClient.getInstance().create(VoucherService.class);
        this.paymentService = RetrofitClient.getInstance().create(PaymentService.class);
        this.orderService = RetrofitClient.getInstance().create(OrderService.class);
        this.reviewService = RetrofitClient.getInstance().create(ReviewService.class);
        this.messageService = RetrofitClient.getInstance().create(MessageService.class);
        this.authService = RetrofitClient.getInstance().create(AuthService.class);
        this.conversationService = RetrofitClient.getInstance().create(ConversationService.class);
        this.userService = RetrofitClient.getInstance().create(UserService.class);
    }

    public void getSliders(Callback<List<Slider>> callback) {
        Call<List<Slider>> call = sliderService.getSliders();
        call.enqueue(callback);
    }
    public void getCategories(Callback<List<Category>> callback) {
        Call<List<Category>> call = categoryService.getAllCategories();
        call.enqueue(callback);
    }
    public void getBrands(Callback<List<Brand>> callback) {
        Call<List<Brand>> call = brandService.getAllBrands();
        call.enqueue(callback);
    }
    public void getTodayOffers(Callback<List<TodayOfferResponse>> callback) {
        Call<List<TodayOfferResponse>> call = flashDeal.getTodayOffers();
        call.enqueue(callback);
    }

    public void getNewArrivalProducts(Integer userId, Callback<List<ProductArrivalResponse>> callback) {
        ProductArrivalRequest request = new ProductArrivalRequest(userId);
        Call<List<ProductArrivalResponse>> call = productService.getProductArrivals(request);
        call.enqueue(callback);
    }
    public void getBestSellerProducts(Integer userId, Callback<List<BestsellerProductResponse>> callback) {
        BestsellerRequest request = new BestsellerRequest(userId);
        Call<List<BestsellerProductResponse>> call = productService.getBestsellerProducts(request);
        call.enqueue(callback);
    }
    public void getProductsByBrand(int brandId, Integer userId, String sortOption, Callback<List<ProductResponse>> callback) {
        Call<List<ProductResponse>> call = productService.getProductsByBrand(brandId, userId, sortOption);
        call.enqueue(callback);
    }

    public void getProductsByCategory(int categoryId, Integer userId, String sortOption, Callback<List<ProductResponse>> callback) {
        Call<List<ProductResponse>> call = productService.getProductsByCategory(categoryId, userId, sortOption);
        call.enqueue(callback);
    }

    public void getProductsByType(String type, Integer userId, String sortOption, Callback<List<ProductResponse>> callback) {
        Call<List<ProductResponse>> call = productService.getProductsByType(type, userId, sortOption);
        call.enqueue(callback);
    }
    public void getProductsByIds(List<Integer> ids, int userId, Callback<List<ProductResponse>> callback) {
        Call<List<ProductResponse>> call = productService.getProductsByIds(ids, userId);
        call.enqueue(callback);
    }
    public void searchProducts(String query, Callback<List<ResultSearchResponse>> callback) {
        Call<List<ResultSearchResponse>> call = productService.searchProducts(query);
        call.enqueue(callback);
    }

    public void getFavoriteProducts(Integer userId, Callback<List<ProductResponse>> callback) {
        Call<List<ProductResponse>> call = favoriteApi.getFavorites(userId);
        call.enqueue(callback);
    }
    public void getProductDetail(int productId, Integer userId, Callback<ProductDetailResponse> callback) {
        Call<ProductDetailResponse> call = productService.getProductDetail(productId, userId);
        call.enqueue(callback);
    }
    public void getFilterOptions(Callback<FilterOptionsResponse> callback) {
        Call<FilterOptionsResponse> call = productService.getFilterOptions();
        call.enqueue(callback);
    }
    public void getUserAddresses(int userId, Callback<List<Address>> callback) {
        Call<List<Address>> call = addressService.getUserAddresses(userId);
        call.enqueue(callback);
    }
    // Thêm địa chỉ
    public void addAddress(int userId, Address address, Callback<Address> callback) {
        Call<Address> call = addressService.addAddress(userId, address);
        call.enqueue(callback);
    }

    // Cập nhật địa chỉ
    public void updateAddress(int addressId, Address address, Callback<Address> callback) {
        Call<Address> call = addressService.updateAddress(addressId, address);
        call.enqueue(callback);
    }

    // Xoá địa chỉ
    public void deleteAddress(int addressId, Callback<Void> callback) {
        Call<Void> call = addressService.deleteAddress(addressId);
        call.enqueue(callback);
    }
    public void getCartItemsByUserId(int userId, Callback<List<com.example.app_jewelry.Service.DTO.reponse.CartItemResponse>> callback) {
        Call<List<com.example.app_jewelry.Service.DTO.reponse.CartItemResponse>> call = cartService.getMyCartItems(userId);
        call.enqueue(callback);
    }
    public void updateCartItemQuantity(int userId, int cartItemId, int quantity, Callback<Void> callback) {
        Call<Void> call = cartService.updateCartItemQuantity(userId, cartItemId, quantity);
        call.enqueue(callback);
    }
    public void deleteCartItem(int userId, int cartItemId, Callback<Void> callback) {
        Call<Void> call = cartService.deleteCartItem(userId, cartItemId);
        call.enqueue(callback);
    }
    public void getCartItemCount(int userId, Callback<Integer> callback) {
        Call<Integer> call = cartService.getCartItemCount(userId);
        call.enqueue(callback);
    }
    public void addToCart(int userId, int variantId, int quantity, Callback<Void> callback) {
        AddToCartRequest request = new AddToCartRequest(userId, variantId, quantity);
        Call<Void> call = cartService.addToCart(request);
        call.enqueue(callback);
    }
    public void getAvailableVouchers(int userId, Callback<List<VoucherResponse>> callback) {
        Call<List<VoucherResponse>> call = voucherService.getAvailableVouchers(userId);
        call.enqueue(callback);
    }
    public void setDefaultAddress(int userId, int addressId, Callback<Void> callback) {
        Call<Void> call = addressService.setDefaultAddress(userId, addressId);
        call.enqueue(callback);
    }


    public void filterProducts(
            List<String> brands,
            List<String> categories,
            List<String> sizes,
            Float minPrice,
            Float maxPrice,
            String sortOption,
            Integer userId,
            Callback<List<ProductResponse>> callback
    ) {
        Call<List<ProductResponse>> call = productService.filterProducts(
                brands,
                categories,
                sizes,
                minPrice,
                maxPrice,
                sortOption,
                userId
        );
        call.enqueue(callback);
    }
    public void createPayment(PayOSRequest request, Callback<PayOSResponse> callback) {
        Call<PayOSResponse> call = paymentService.createPayment(request);
        call.enqueue(callback);
    }
    public void getPaymentStatus(long orderId, Callback<PaymentStatusResponse> callback) {
        Call<PaymentStatusResponse> call = paymentService.getPaymentStatus(orderId);
        call.enqueue(callback);
    }
    public void getMyOrders(int userId, Callback<List<OrderResponse>> callback) {
        Call<List<OrderResponse>> call = orderService.getMyOrders(userId);
        call.enqueue(callback);
    }
    public void getOrderDetail(int orderId, int userId, Callback<OrderResponse> callback) {
        Call<OrderResponse> call = orderService.getOrderDetail(orderId, userId);
        call.enqueue(callback);
    }
    public void checkUserBoughtProduct(int userId, int productId, Callback<Boolean> callback) {
        Call<Boolean> call = orderService.hasUserBoughtProduct(userId, productId);
        call.enqueue(callback);
    }
    public void submitReview(CreateReviewRequest request, Callback<Void> callback) {
        reviewService.submitReview(request).enqueue(callback);
    }

    public void createOrder(CreateOrderRequest request, Callback<Void> callback) {
        Call<Void> call = orderService.createOrder(request);
        call.enqueue(callback);
    }
    public void addFavorite(int userId, int productId, Callback<Void> callback) {
        favoriteApi.addFavorite(userId, productId).enqueue(callback);
    }

    public void removeFavorite(int userId, int productId, Callback<Void> callback) {
        favoriteApi.removeFavorite(userId, productId).enqueue(callback);
    }

    public void getMessages(int conversationId, Callback<List<MessageResponse>> callback) {
        messageService.getMessages(conversationId).enqueue(callback);
    }
    public void sendMessage(CreateMessageRequest request, Callback<MessageResponse> callback) {
        messageService.sendMessage(request).enqueue(callback);
    }

    public void login(LoginRequest request, Callback<LoginResponse> callback) {
        authService.login(request).enqueue(callback);
    }

    public void register(RegisterRequest request, Callback<RegisterResponse> callback) {
        authService.register(request).enqueue(callback);
    }
    public void getConversationIdByUserId(int userId, Callback<Integer> callback) {
        conversationService.getConversationIdByUserId(userId).enqueue(callback);
    }
    public void verifyOtp(String email, String otp, Callback<RegisterResponse> callback) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("otp", otp);
        authService.verifyOtp(body).enqueue(callback);
    }

    public void forgotPassword(String email, Callback<RegisterResponse> callback) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        authService.forgotPassword(body).enqueue(callback);
    }

    public void resetPassword(String email, String otp, String newPassword, String confirmPassword,
                              Callback<RegisterResponse> callback) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("otp", otp);
        body.put("newPassword", newPassword);
        body.put("confirmPassword", confirmPassword);
        authService.resetPassword(body).enqueue(callback);
    }
    public void getUserById(int userId, Callback<UserResponse> callback) {
        userService.getUserById(userId).enqueue(callback);
    }
    public void updateUser(int userId, UpdateUserRequest request, Callback<Void> callback) {
        userService.updateUser(userId, request).enqueue(callback);
    }
    public void changePassword(int userId, ChangePasswordRequest request, Callback<Void> callback) {
        userService.changePassword(userId, request).enqueue(callback);
    }
}
