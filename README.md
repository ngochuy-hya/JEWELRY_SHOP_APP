# 💎 Jewelry Shop - E-Commerce Mobile Application

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)](https://www.mysql.com/)

> A complete e-commerce mobile application for jewelry shopping with Android frontend and Spring Boot backend.

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Technologies](#-technologies)
- [System Architecture](#-system-architecture)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Screenshots](#-screenshots)
- [Contributors](#-contributors)

---

## 🎯 Overview

**Jewelry Shop** is a full-stack mobile e-commerce application designed for buying and selling jewelry products. The system consists of:

- **Android Mobile App**: User-friendly interface for customers
- **RESTful API Backend**: Built with Spring Boot for business logic
- **MySQL Database**: Robust data storage and management

The application provides a complete shopping experience from browsing products to payment processing with real-time chat support.

---

## ✨ Features

### 👤 User Features

#### Authentication & Security
- ✅ User registration with email verification
- ✅ Secure login with JWT authentication
- ✅ Password recovery with OTP via email
- ✅ Change password functionality

#### Shopping Experience
- ✅ Browse products by categories and brands
- ✅ Advanced search with filters (price, rating, brand, category)
- ✅ Sort products (newest, price, bestseller, rating)
- ✅ Product details with multiple images and variants (color/size)
- ✅ Add to cart with quantity selection
- ✅ Wishlist (favorite products)
- ✅ Flash deals with time-limited discounts

#### Checkout & Payment
- ✅ Multiple shipping addresses management
- ✅ Apply discount vouchers
- ✅ QR code payment via PayOS gateway
- ✅ Order tracking (Pending, Confirmed, Shipped, Completed, Cancelled)
- ✅ Order history with detailed information

#### Reviews & Ratings
- ✅ Rate products with star ratings (1-5)
- ✅ Write reviews with comments
- ✅ Upload multiple images for reviews
- ✅ Like other reviews

#### Customer Support
- ✅ Real-time chat with admin
- ✅ AI chatbot support
- ✅ Send images in chat
- ✅ Conversation history

### 🔧 Admin Features
- ✅ Product management (CRUD operations)
- ✅ Category and brand management
- ✅ Order management and status updates
- ✅ Flash deals creation
- ✅ Voucher system management
- ✅ Customer chat support
- ✅ Inventory tracking with stock history
- ✅ Banner/slider management

---

## 🛠 Technologies

### Backend (API)
- **Framework**: Spring Boot 3.5.3
- **Language**: Java 21
- **Database**: MySQL 8
- **Security**: Spring Security + JWT
- **ORM**: Hibernate/JPA
- **Payment Gateway**: PayOS SDK
- **Email Service**: Spring Mail (Gmail SMTP)
- **Build Tool**: Maven

### Frontend (Mobile)
- **Platform**: Android (Java)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Networking**: Retrofit 3.0.0
- **Image Loading**: Glide 4.16.0
- **UI Framework**: Material Design 1.12.0
- **Cloud Storage**: Cloudinary 2.3.1
- **QR Scanner**: ZXing 4.3.0
- **Build Tool**: Gradle

---

## 🏗 System Architecture

```
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│                 │         │                 │         │                 │
│  Android App    │◄───────►│  Spring Boot    │◄───────►│  MySQL Database │
│  (Java)         │  REST   │  API Server     │  JDBC   │  (jewelry_shop) │
│                 │  API    │  (Java 21)      │         │                 │
└─────────────────┘         └─────────────────┘         └─────────────────┘
                                    │
                                    │
                            ┌───────┴───────┐
                            │               │
                    ┌───────▼──────┐ ┌─────▼────────┐
                    │   PayOS      │ │  Cloudinary  │
                    │   Payment    │ │  Image CDN   │
                    └──────────────┘ └──────────────┘
```

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

### For Backend:
- Java JDK 21 or higher
- Maven 3.8+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### For Mobile App:
- Android Studio (latest version)
- Android SDK 24+
- JDK 11 or higher

---

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd DoAn
```

### 2. Database Setup

```sql
-- Create database
CREATE DATABASE jewelry_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Import schema and sample data
mysql -u root -p jewelry_shop < jewelry_shop.sql
```

### 3. Backend Setup

```bash
cd API

# Update application.properties with your MySQL credentials
# Located at: src/main/resources/application.properties

# Build and run
mvn clean install
mvn spring-boot:run
```

The API will start at `http://localhost:8080`

### 4. Android App Setup

1. Open `App_jewelry` folder in Android Studio
2. Wait for Gradle sync to complete
3. Update API base URL in `ApiClient.java`:
```java
private static final String BASE_URL = "http://10.0.2.2:8080/"; // For emulator
// or
private static final String BASE_URL = "http://YOUR_LOCAL_IP:8080/"; // For physical device
```
4. Build and run on emulator or physical device

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | User login |
| POST | `/auth/forgot-password` | Request OTP for password reset |
| POST | `/auth/verify-otp` | Verify OTP code |
| POST | `/auth/reset-password` | Reset password with token |

### Product Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/products` | Get all products with filters |
| GET | `/products/{id}` | Get product details |
| GET | `/products/category/{id}` | Get products by category |
| GET | `/products/brand/{id}` | Get products by brand |
| GET | `/products/search` | Search products |
| GET | `/products/bestsellers` | Get bestseller products |
| GET | `/products/new-arrivals` | Get new arrival products |

### Cart Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/cart/{userId}` | Get user cart |
| POST | `/cart/add` | Add item to cart |
| PUT | `/cart/update/{cartItemId}` | Update cart item quantity |
| DELETE | `/cart/remove/{cartItemId}` | Remove item from cart |

### Order Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders/create` | Create new order |
| GET | `/orders/user/{userId}` | Get user orders |
| GET | `/orders/{orderId}` | Get order details |
| PUT | `/orders/{orderId}/status` | Update order status |

### Payment Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/payment/create` | Create payment link (PayOS) |
| GET | `/payment/status/{orderId}` | Get payment status |

### Review Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/reviews/create` | Create product review |
| GET | `/reviews/product/{productId}` | Get product reviews |
| POST | `/reviews/{reviewId}/like` | Like a review |

### Other Endpoints

- **Categories**: `/categories`
- **Brands**: `/brands`
- **Vouchers**: `/vouchers`
- **Flash Deals**: `/flash-deals`
- **Favorites**: `/favorites`
- **Addresses**: `/addresses`
- **Chat**: `/conversations`, `/messages`
- **Users**: `/users`

---

## 🗄 Database Schema

The system uses **22 main tables**:

### Core Tables
- `users` - User accounts
- `roles` - User roles (USER, ADMIN)
- `addresses` - Shipping addresses

### Product Management
- `products` - Product information
- `product_variants` - Product variants (color, size, price, stock)
- `product_images` - Product images
- `categories` - Product categories
- `brands` - Product brands

### Shopping
- `carts` - Shopping carts
- `cart_items` - Items in cart
- `orders` - Customer orders
- `order_items` - Items in orders
- `favorites` - Wishlist items

### Marketing
- `vouchers` - Discount vouchers
- `voucher_usage` - Voucher usage tracking
- `flash_deals` - Time-limited deals
- `sliders` - Homepage banners

### Customer Interaction
- `reviews` - Product reviews
- `review_images` - Review images
- `comment_likes` - Review likes
- `conversations` - Chat conversations
- `messages` - Chat messages
- `message_images` - Images in chat

### Inventory
- `stock_history` - Inventory tracking
- `suppliers` - Supplier information
- `product_imports` - Import records

---

## 📱 Screenshots

> Add your app screenshots here

```
[Home Screen]  [Product List]  [Product Detail]  [Cart]
[Checkout]     [Payment]       [Orders]          [Chat]
```

---

## ⚙️ Configuration

### Backend Configuration

**Database** (`application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jewelry_shop
spring.datasource.username=root
spring.datasource.password=your_password
```

**Email Service**:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**PayOS Credentials** (`PAYMENT.txt`):
```
CLIENT ID: your-client-id
APIKEY: your-api-key
CHECKSUM KEY: your-checksum-key
```

### Android Configuration

**Cloudinary** (`CloudinaryConfig.java`):
```java
cloudName = "your-cloud-name"
apiKey = "your-api-key"
apiSecret = "your-api-secret"
```

---

## 🔐 Security

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Controlled cross-origin requests
- **Input Validation**: Server-side validation for all inputs
- **SQL Injection Prevention**: JPA/Hibernate parameterized queries

---

## 🧪 Testing

### Backend Tests
```bash
cd API
mvn test
```

### Android Tests
```bash
cd App_jewelry
./gradlew test
./gradlew connectedAndroidTest
```

---

## 📈 Future Enhancements

- [ ] Admin web dashboard
- [ ] Push notifications for order updates
- [ ] Social media login (Google, Facebook)
- [ ] Multiple payment methods (Momo, ZaloPay)
- [ ] Product recommendations with AI
- [ ] Virtual try-on with AR
- [ ] Multi-language support
- [ ] Dark mode theme

---

## 🤝 Contributors

- **Developer**: [Your Name]
- **Email**: [Your Email]
- **University**: [Your University]
- **Project**: Graduation Thesis / Final Year Project

---

## 📄 License

This project is developed for educational purposes as a graduation thesis.

---

## 📞 Contact & Support

For any questions or support, please contact:
- Email: [Your Email]
- GitHub: [Your GitHub Profile]

---

<div align="center">

**Made with ❤️ for Graduation Thesis**

⭐ Star this repo if you find it helpful!

</div>

