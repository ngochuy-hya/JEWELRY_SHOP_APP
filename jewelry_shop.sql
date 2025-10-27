-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 12, 2025 at 07:14 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jewelry_shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
  `address_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `receiver_name` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address_line` varchar(255) NOT NULL,
  `area` varchar(100) DEFAULT NULL,
  `landmark` varchar(100) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `is_default` tinyint(1) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `addresses`
--

INSERT INTO `addresses` (`address_id`, `user_id`, `receiver_name`, `phone`, `email`, `address_line`, `area`, `landmark`, `city`, `state`, `is_default`, `created_at`) VALUES
(1, 1, 'Nguyễn Văn A', '0912345678', 'vana@example.com', '123 Đường Lê Lợi', 'Phường 5', 'Gần chợ Bến Thành', 'Quận 1', 'TP.HCM', 0, '2024-06-01 08:00:00'),
(2, 1, 'Nguyễn Văn A', '0912345678', 'vana@example.com', '456 Đường Nguyễn Trãi', 'Phường 7', 'Gần trường học', 'Quận 5', 'TP.HCM', 1, '2024-06-02 09:30:00'),
(3, 1, 'Nguyễn Văn A', '0912345678', 'vana@example.com', '789 Đường Hai Bà Trưng', 'Phường 3', NULL, 'Quận 3', 'TP.HCM', 0, '2024-06-03 14:15:00'),
(4, 1, 'q', '23424234', '24324234', '2334', 'Phường Phúc Xá', '234324', 'Quận Ba Đình', 'Thành phố Hà Nội', 0, '2025-07-10 17:41:49'),
(6, 7, 'MK', '123434354', '123213', '1231', 'Phường Phúc Tân', '123213', 'Quận Hoàn Kiếm', 'Thành phố Hà Nội', 0, '2025-07-12 16:41:16'),
(7, 7, 'dasd', '212313', '2asdasd', 'dsadas', 'Phường Quang Trung', 'dasdasd', 'Thành phố Hà Giang', 'Tỉnh Hà Giang', 1, '2025-07-12 16:42:07'),
(8, 7, 'aaa', '12313', 'dasdas', 'ds', 'Phường Quyết Thắng', 'ssss', 'Thành phố Lai Châu', 'Tỉnh Lai Châu', 0, '2025-07-12 16:44:28'),
(9, 7, '1', '1', '1', '1', 'Phường Phúc Xá', '1', 'Quận Ba Đình', 'Thành phố Hà Nội', 0, '2025-07-12 16:45:06'),
(10, 7, '1setResult(RESULT_OK);1', '1', '1', '1', 'Phường Phúc Xá', '1', 'Quận Ba Đình', 'Thành phố Hà Nội', 0, '2025-07-12 16:46:35');

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `brand_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `logo_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`brand_id`, `name`, `logo_url`) VALUES
(1, 'PNJ', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png'),
(2, 'DOJI', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png'),
(3, 'SJC', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png'),
(4, 'Skymond', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png'),
(5, 'Precita', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png'),
(6, 'Cartier', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png'),
(7, 'Tiffany & Co.', 'https://webadmin.beeart.vn/upload/image/20220618/6379113714907065559830131.png');

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `cart_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`cart_id`, `user_id`) VALUES
(1, 1),
(2, 7);

-- --------------------------------------------------------

--
-- Table structure for table `cart_items`
--

CREATE TABLE `cart_items` (
  `cart_item_id` int(11) NOT NULL,
  `cart_id` int(11) DEFAULT NULL,
  `variant_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cart_items`
--

INSERT INTO `cart_items` (`cart_item_id`, `cart_id`, `variant_id`, `quantity`) VALUES
(10, 2, 1, 14);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `name`, `description`, `image_url`) VALUES
(1, 'Ring', 'Wide selection of silver, gold, and gemstone rings', 'https://tse4.mm.bing.net/th/id/OIP.G4ApWyUkB-oZjE0-muS2uwHaHa?w=474&h=474&c=7'),
(2, 'Necklace', 'Elegant necklaces including pearl, diamond, and gold chains', 'https://tse3.mm.bing.net/th/id/OIP.yFOvpiydj1Jodr9aRuFX8QHaHa?w=474&h=474&c=7'),
(3, 'Bracelet', 'Handcrafted bracelets: charm, bangle, and beaded styles', 'https://tse1.mm.bing.net/th/id/OIP.BPRNpugx_b1wamZMOP7OJAHaHa?w=474&h=474&c=7'),
(4, 'Earrings', 'Stud, drop, and hoop earrings with a variety of gemstones', 'https://tse2.mm.bing.net/th/id/OIP.p7eqgYRzreat_asBly92gAHaG3?w=439&h=439&c=7'),
(5, 'Wedding Jewelry', 'Bridal jewelry sets: engagement and wedding rings', 'https://tse2.mm.bing.net/th/id/OIP.p7eqgYRzreat_asBly92gAHaG3?w=439&h=439&c=7'),
(6, 'Men\'s Jewelry', 'Men’s accessories: rings, bracelets, chains, cufflinks', 'https://tse2.mm.bing.net/th/id/OIP.p7eqgYRzreat_asBly92gAHaG3?w=439&h=439&c=7');

-- --------------------------------------------------------

--
-- Table structure for table `comment_likes`
--

CREATE TABLE `comment_likes` (
  `like_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `review_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `conversations`
--

CREATE TABLE `conversations` (
  `conversation_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_ai_enabled` tinyint(1) DEFAULT 1,
  `status` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `conversations`
--

INSERT INTO `conversations` (`conversation_id`, `user_id`, `is_ai_enabled`, `status`, `created_at`) VALUES
(1, 1, 1, 'active', '2025-07-12 18:18:53'),
(2, 7, 0, 'active', '2025-07-12 14:19:38');

-- --------------------------------------------------------

--
-- Table structure for table `favorites`
--

CREATE TABLE `favorites` (
  `favorite_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `favorites`
--

INSERT INTO `favorites` (`favorite_id`, `user_id`, `product_id`) VALUES
(1, 1, 100),
(3, 2, 2),
(10, 1, 9),
(12, 1, 2),
(13, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `flash_deals`
--

CREATE TABLE `flash_deals` (
  `deal_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `discount_price` double NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `is_active` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `flash_deals`
--

INSERT INTO `flash_deals` (`deal_id`, `product_id`, `discount_price`, `start_time`, `end_time`, `is_active`) VALUES
(1, 1, 150, '2025-07-01 00:00:00', '2025-12-31 23:59:59', 1),
(2, 2, 300, '2025-07-05 00:00:00', '2025-08-05 23:59:59', 1),
(3, 1, 999000, '2025-07-09 20:18:07', '2025-07-10 21:18:07', 1),
(4, 1, 4000, '2025-07-10 00:02:38', '2025-07-11 00:02:38', 1),
(5, 100, 450, '2025-07-10 13:27:47', '2025-07-11 13:27:47', 1);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `message_id` int(11) NOT NULL,
  `conversation_id` int(11) NOT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `sent_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`message_id`, `conversation_id`, `sender`, `content`, `sent_at`) VALUES
(1, 1, 'User', 'Shop ơi còn hàng không ạ?', '2025-07-12 18:23:21'),
(2, 1, 'Admin', 'Dạ còn nha bạn 💎', '2025-07-12 18:23:21'),
(3, 1, 'User', 'Em gửi mẫu nè:', '2025-07-12 18:23:21'),
(4, 1, 'User', 'Em gửi ảnh nè 😊', '2025-07-12 11:46:49'),
(5, 1, 'User', 'aaa', '2025-07-12 12:04:54');

-- --------------------------------------------------------

--
-- Table structure for table `message_images`
--

CREATE TABLE `message_images` (
  `image_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `message_images`
--

INSERT INTO `message_images` (`image_id`, `message_id`, `image_url`, `created_at`) VALUES
(1, 3, 'https://img5.thuthuatphanmem.vn/uploads/2021/12/29/anh-nhan-cuoi-dinh-kim-cuong_030458179.jpg', '2025-07-12 18:23:47'),
(2, 3, 'https://img5.thuthuatphanmem.vn/uploads/2021/12/29/anh-nhan-cuoi-dinh-kim-cuong_030458179.jpg', '2025-07-12 18:23:47'),
(3, 4, 'https://tse1.explicit.bing.net/th/id/OIP.C15JVVBaVBP3QR_qMRMfdQHaEj?r=0&rs=1&pid=ImgDetMain&o=7&rm=3', '2025-07-12 11:46:49'),
(4, 4, 'https://tse1.explicit.bing.net/th/id/OIP.C15JVVBaVBP3QR_qMRMfdQHaEj?r=0&rs=1&pid=ImgDetMain&o=7&rm=3', '2025-07-12 11:46:49'),
(5, 5, 'https://res.cloudinary.com/dn4l1otfz/image/upload/v1752321893/cjauqpgdzpw72fkkkpzh.jpg', '2025-07-12 12:04:54');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `voucher_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `address_id`, `total_amount`, `status`, `created_at`, `voucher_code`) VALUES
(1, 1, NULL, 5000, 'Completed', '2025-07-10 00:02:15', NULL),
(2, 2, NULL, 2400, 'Completed', '2025-07-10 00:02:15', NULL),
(3, 1, 1, 600, 'Pending', '2025-07-11 16:30:47', ''),
(4, 1, 2, 2800, 'Pending', '2025-07-11 16:38:52', NULL),
(5, 1, 1, 450, 'Pending', '2025-07-11 16:52:06', ''),
(6, 1, 2, 2100, 'Pending', '2025-07-11 16:53:23', NULL),
(7, 1, 2, 2100, 'Pending', '2025-07-11 17:07:59', NULL),
(8, 1, 2, 2100, 'Pending', '2025-07-11 17:11:47', NULL),
(9, 1, 2, 2100, 'Pending', '2025-07-11 17:16:46', NULL),
(10, 1, 2, 2150, 'Pending', '2025-07-12 05:18:00', 'SAVE50'),
(11, 1, 2, 2600, 'Pending', '2025-07-12 06:40:18', NULL),
(12, 1, 2, 2600, 'Pending', '2025-07-12 06:42:10', NULL),
(13, 1, 2, 4100, 'Pending', '2025-07-12 07:09:27', NULL),
(14, 1, 2, 2750, 'Pending', '2025-07-12 07:23:43', NULL),
(15, 7, NULL, 2100, 'Pending', '2025-07-12 16:32:12', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_item_id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `variant_id` int(11) DEFAULT NULL,
  `product_name` varchar(100) NOT NULL,
  `variant_name` varchar(250) DEFAULT NULL,
  `unit_price` double NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`order_item_id`, `order_id`, `variant_id`, `product_name`, `variant_name`, `unit_price`, `quantity`) VALUES
(1, 1, 1, 'Diamond Ring', 'Gold - M', 5000, 2),
(2, 2, 2, 'Silver Necklace', 'Silver - L', 1200, 3),
(3, 3, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 200, 1),
(4, 3, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 200, 2),
(5, 4, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 200, 14),
(6, 5, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 1),
(7, 5, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 2),
(8, 6, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14),
(9, 7, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14),
(10, 8, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14),
(11, 9, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14),
(12, 10, 1, 'Silver Diamond Ring', 'a', 150, 13),
(13, 10, 10, 'Nhẫn cưới vàng 18K', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 500, 1),
(14, 11, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14),
(15, 11, 10, 'Nhẫn cưới vàng 18K', 'https://burst.shopifycdn.com/photos/gold-ring-copy / S', 500, 1),
(16, 12, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14),
(17, 12, 10, 'Nhẫn cưới vàng 18K', 'https://burst.shopifycdn.com/photos/gold-ring-copy / S', 500, 1),
(18, 13, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 4),
(19, 13, 10, 'Nhẫn cưới vàng 18K', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 500, 7),
(20, 14, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 15),
(21, 14, 10, 'Nhẫn cưới vàng 18K', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 500, 1),
(22, 15, 1, 'Silver Diamond Ring', 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png / 7', 150, 14);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` tinytext DEFAULT NULL,
  `main_image` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `category_id`, `brand_id`, `name`, `description`, `main_image`, `is_active`) VALUES
(1, 1, 1, 'Silver Diamond Ring', 'Elegant ring with silver and diamond', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(2, 2, 1, 'Gold Pearl Necklace', 'Classic gold necklace with pearl pendant', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(3, 1, 1, 'Silver Diamond Ring', 'https://my-live-02.slatic.net/p/62f4807874c7a1a69d418b800262544e.jpg', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(4, 2, 1, 'Gold Pearl Necklace', 'Classic gold necklace with pearl pendant', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(5, 1, 1, 'Silver Diamond Ring', 'Elegant ring with silver and diamond', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(6, 2, 1, 'Gold Pearl Necklace', 'Classic gold necklace with pearl pendant', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(7, 1, 1, 'Dây chuyền bạc nữ', 'Sản phẩm bạc cao cấp', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(8, 1, 1, 'Vòng tay ngọc trai', 'Ngọc trai tự nhiên', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(9, 1, 1, 'Nhẫn cưới vàng 18K', 'Nhẫn cưới cao cấp', 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', 1),
(100, 1, 1, 'Nhẫn cưới vàng 18K', 'Được chế tác tinh xảo từ vàng 18K, sang trọng và bền bỉ.', 'https://daphongthuyvn.com/files/sanpham/1660/1/jpg/nhan-vang-trang-nu-da-quy-sapphire-xanh-hero-thien-nhien-cao-cap-nvdq1002.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `image_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `product_images`
--

INSERT INTO `product_images` (`image_id`, `product_id`, `image_url`) VALUES
(1, 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1xp79XGKoIg-gZeZiRg2G7mpp2A6kH-AWow&s'),
(2, 1, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png'),
(3, 1, 'https://lili.vn/wp-content/uploads/2021/11/Nhan-bac-nu-dinh-da-CZ-hinh-bong-hoa-dao-LILI_289467_1-400x400.jpg'),
(4, 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1xp79XGKoIg-gZeZiRg2G7mpp2A6kH-AWow&s'),
(5, 2, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png'),
(6, 2, 'https://lili.vn/wp-content/uploads/2021/11/Nhan-bac-nu-dinh-da-CZ-hinh-bong-hoa-dao-LILI_289467_1-400x400.jpg'),
(7, 3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1xp79XGKoIg-gZeZiRg2G7mpp2A6kH-AWow&s'),
(8, 3, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png'),
(9, 3, 'https://lili.vn/wp-content/uploads/2021/11/Nhan-bac-nu-dinh-da-CZ-hinh-bong-hoa-dao-LILI_289467_1-400x400.jpg'),
(10, 100, 'https://burst.shopifycdn.com/photos/top-view-ring.jpg'),
(11, 100, 'https://burst.shopifycdn.com/photos/diamond-rings.jpg'),
(12, 100, 'https://burst.shopifycdn.com/photos/top-view-ring.jpg'),
(13, 100, 'https://burst.shopifycdn.com/photos/diamond-rings.jpg'),
(14, 100, 'https://burst.shopifycdn.com/photos/top-view-ring.jpg'),
(15, 100, 'https://burst.shopifycdn.com/photos/diamond-rings.jpg'),
(16, 100, 'https://burst.shopifycdn.com/photos/top-view-ring.jpg'),
(17, 100, 'https://burst.shopifycdn.com/photos/diamond-rings.jpg'),
(18, 100, 'https://burst.shopifycdn.com/photos/top-view-ring.jpg'),
(19, 100, 'https://burst.shopifycdn.com/photos/diamond-rings.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `product_imports`
--

CREATE TABLE `product_imports` (
  `import_id` int(11) NOT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  `imported_at` datetime DEFAULT current_timestamp(),
  `note` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product_variants`
--

CREATE TABLE `product_variants` (
  `variant_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `price` double NOT NULL,
  `stock` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `product_variants`
--

INSERT INTO `product_variants` (`variant_id`, `product_id`, `color`, `size`, `price`, `stock`) VALUES
(1, 1, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', '7', 200, 39),
(2, 2, 'Gold', 'One Size', 350, 5),
(4, 2, 'Gold', 'One Size', 350, 5),
(5, 1, 'https://lili.vn/wp-content/uploads/2021/11/Nhan-bac-nu-dinh-da-CZ-hinh-bong-hoa-dao-LILI_289467_1-400x400.jpg', '6', 100, 20),
(6, 2, 'Gold', 'One Size', 350, 5),
(7, 1, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'Free', 1200000, 20),
(8, 2, 'Trắng', 'M', 890000, 15),
(9, 3, 'https://my-live-02.slatic.net/p/62f4807874c7a1a69d418b800262544e.jpg', 'S', 1500000, 5),
(10, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'S', 500, 99),
(11, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'M', 500, 5),
(12, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'S', 500, 10),
(13, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'M', 500, 5),
(14, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'S', 500, 10),
(15, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'M', 500, 5),
(16, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'S', 500, 10),
(17, 100, 'https://cdn.pnj.io/images/detailed/40/gnddddw001969-nhan-kim-cuong-vang-trang-14k-pnj-01.png', 'M', 500, 5);

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `review_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `comment` tinytext DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `title` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`review_id`, `user_id`, `product_id`, `rating`, `comment`, `created_at`, `title`) VALUES
(10, 2, 1, 4, 'Very nice', '2025-07-09 20:36:00', NULL),
(11, 3, 2, 4, 'Great pearl color', '2025-07-09 20:36:00', NULL),
(12, 4, 2, 5, 'Amazing necklace', '2025-07-09 20:36:00', NULL),
(14, 2, 2, 4, 'Lovely necklace!', '2025-07-10 00:02:26', NULL),
(15, 1, 100, 5, 'Sản phẩm rất đẹp, ưng ý vô cùng!', '2025-07-10 14:28:11', NULL),
(16, 1, 3, 5, 'Nhẫn đẹp, lấp lánh và đúng như hình ảnh!', '2025-07-12 08:26:45', 'Rất ưng ý');

-- --------------------------------------------------------

--
-- Table structure for table `review_images`
--

CREATE TABLE `review_images` (
  `image_id` int(11) NOT NULL,
  `review_id` int(11) DEFAULT NULL,
  `image_url` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `review_images`
--

INSERT INTO `review_images` (`image_id`, `review_id`, `image_url`, `created_at`) VALUES
(20, 10, 'https://burst.shopifycdn.com/photos/ring-in-hand.jpg', '2025-07-10 14:28:11'),
(21, 16, 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', '2025-07-12 08:26:45'),
(22, 16, 'https://lh5.googleusercontent.com/proxy/qNOJZywv0z4eda2YS6dnG4s42nE4pN6zBl8aoHG_Iadwf8UmzRYY-AdKCKY-feMD2VW6CXm1MWxPFgpbtxqyE3adawvx-J4Vt-yOu6xQbsAQkAGCK_-xMCxT6vJaq2e5WOCrhZLxdRT66658ODqQ4x3hvOla08JhM7UXrlhH', '2025-07-12 08:26:45');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_id`, `name`) VALUES
(1, 'USER'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `sliders`
--

CREATE TABLE `sliders` (
  `slider_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `display_order` int(11) DEFAULT 0,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `sliders`
--

INSERT INTO `sliders` (`slider_id`, `title`, `image_url`, `link_url`, `display_order`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'Ưu đãi mùa hè', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1xp79XGKoIg-gZeZiRg2G7mpp2A6kH-AWow&s', 'https://example.com/sale', 1, 1, '2025-07-09 19:56:31', '2025-07-09 19:56:31'),
(2, 'Bộ sưu tập mới', 'https://lili.vn/wp-content/uploads/2021/11/Nhan-bac-nu-dinh-da-CZ-hinh-bong-hoa-dao-LILI_289467_1-400x400.jpg', 'https://example.com/new', 2, 1, '2025-07-09 19:56:31', '2025-07-09 19:56:31'),
(3, 'Giảm giá đến 50%', 'https://tnj.vn/img/cms/nhan-nu-moi/NNM0010/nhan-kim-cuong-moissanite-nu-7mm-dep-NNM0010-3.jpg', 'https://example.com/discount', 3, 1, '2025-07-09 19:56:31', '2025-07-09 19:56:31'),
(4, 'Trang sức cao cấp', 'https://tnj.vn/img/cms/nhan-nu-moi/NNM0010/nhan-kim-cuong-moissanite-nu-7mm-dep-NNM0010-3.jpg', 'https://example.com/luxury', 4, 0, '2025-07-09 19:56:31', '2025-07-09 19:56:31'),
(5, 'Quà tặng cho nàng', 'https://tnj.vn/img/cms/nhan-nu-moi/NNM0010/nhan-kim-cuong-moissanite-nu-7mm-dep-NNM0010-3.jpg', 'https://example.com/gifts', 5, 1, '2025-07-09 19:56:31', '2025-07-09 19:56:31');

-- --------------------------------------------------------

--
-- Table structure for table `stock_history`
--

CREATE TABLE `stock_history` (
  `stock_id` int(11) NOT NULL,
  `variant_id` int(11) DEFAULT NULL,
  `import_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `type` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `stock_history`
--

INSERT INTO `stock_history` (`stock_id`, `variant_id`, `import_id`, `quantity`, `unit_price`, `type`, `created_at`) VALUES
(1, 1, NULL, 30, 1000000, 'import', '2025-07-09 21:17:58'),
(2, 2, NULL, 15, 800000, 'import', '2025-07-08 21:17:58'),
(4, 1, NULL, 13, 150, 'order', '2025-07-12 05:18:00'),
(5, 10, NULL, 1, 500, 'order', '2025-07-12 05:18:00'),
(6, 1, NULL, 14, 150, 'order', '2025-07-12 06:40:18'),
(7, 10, NULL, 1, 500, 'order', '2025-07-12 06:40:18'),
(8, 1, NULL, 14, 150, 'order', '2025-07-12 06:42:10'),
(9, 10, NULL, 1, 500, 'order', '2025-07-12 06:42:10'),
(10, 1, NULL, 4, 150, 'order', '2025-07-12 07:09:27'),
(11, 10, NULL, 7, 500, 'order', '2025-07-12 07:09:27'),
(12, 1, NULL, 15, 150, 'order', '2025-07-12 07:23:43'),
(13, 10, NULL, 1, 500, 'order', '2025-07-12 07:23:43'),
(14, 1, NULL, 14, 150, 'order', '2025-07-12 16:32:12');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `supplier_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `contact_info` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `system_configs`
--

CREATE TABLE `system_configs` (
  `config_key` varchar(50) NOT NULL,
  `config_value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `email`, `password_hash`, `phone`, `role_id`, `created_at`) VALUES
(1, 'Alice', 'alice@example.com', 'hashed_pass_1', '0900000001', 1, '2025-07-09 20:36:00'),
(2, 'Bob', 'bob@example.com', 'hashed_pass_2', '0900000002', 1, '2025-07-09 20:36:00'),
(3, 'Charlie', 'charlie@example.com', 'hashed_pass_3', '0900000003', 1, '2025-07-09 20:36:00'),
(4, 'Diana', 'diana@example.com', 'hashed_pass_4', '0900000004', 1, '2025-07-09 20:36:00'),
(5, 'Test User', 'test@example.com', 'hashed_pw', '0123456789', 1, '2025-07-09 21:18:15'),
(7, 'MK', '22110356@student.hcmute.edu.vn', '$2a$10$RJUUV6mqTOxfLTTUosIcvum/8wWf1N/fyFYlm/r/LuoDbGvpshGDG', '123456789', 1, '2025-07-12 14:19:38');

-- --------------------------------------------------------

--
-- Table structure for table `vouchers`
--

CREATE TABLE `vouchers` (
  `voucher_id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `discount_type` enum('fixed','percent') NOT NULL,
  `discount_value` decimal(38,2) NOT NULL,
  `max_discount_value` decimal(38,2) DEFAULT NULL,
  `min_order_amount` decimal(38,2) DEFAULT NULL,
  `usage_limit` int(11) DEFAULT NULL,
  `usage_limit_per_user` int(11) DEFAULT 1,
  `start_date` date DEFAULT NULL,
  `expiry_date` date NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `vouchers`
--

INSERT INTO `vouchers` (`voucher_id`, `code`, `description`, `discount_type`, `discount_value`, `max_discount_value`, `min_order_amount`, `usage_limit`, `usage_limit_per_user`, `start_date`, `expiry_date`, `is_active`, `created_at`) VALUES
(1, 'SAVE50', 'Save 50,000đ on orders from 300,000đ', 'fixed', 300.00, NULL, 300.00, 1000, 1, '2025-01-01', '2025-12-31', 1, '2025-07-11 14:48:48'),
(2, 'XMAS10', '10% off up to 100,000đ for orders from 500,000đ', 'percent', 10.00, 100000.00, 500000.00, 500, 1, '2025-11-01', '2025-12-25', 1, '2025-07-11 14:48:48'),
(3, 'FREESHIP', 'Discount 30,000đ shipping fee on orders over 100,000đ', 'fixed', 30000.00, NULL, 100000.00, NULL, 1, '2025-07-01', '2025-08-01', 1, '2025-07-11 14:48:48'),
(4, 'NEWUSER20', '20% off for new users, max 80,000đ', 'percent', 20.00, 80000.00, 0.00, 1000, 1, '2025-01-01', '2025-12-31', 1, '2025-07-11 14:48:48'),
(5, 'FLASH100K', 'Save 100,000đ today only!', 'fixed', 100000.00, NULL, 400000.00, 50, 1, '2025-07-11', '2025-07-11', 1, '2025-07-11 14:48:48');

-- --------------------------------------------------------

--
-- Table structure for table `voucher_usage`
--

CREATE TABLE `voucher_usage` (
  `usage_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `voucher_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `used_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `voucher_usage`
--

INSERT INTO `voucher_usage` (`usage_id`, `user_id`, `voucher_id`, `order_id`, `used_at`) VALUES
(1, 1, 1, 10, '2025-07-12 05:18:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`address_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`brand_id`);

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`cart_id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indexes for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`cart_item_id`),
  ADD KEY `cart_id` (`cart_id`),
  ADD KEY `variant_id` (`variant_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `comment_likes`
--
ALTER TABLE `comment_likes`
  ADD PRIMARY KEY (`like_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `review_id` (`review_id`);

--
-- Indexes for table `conversations`
--
ALTER TABLE `conversations`
  ADD PRIMARY KEY (`conversation_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `favorites`
--
ALTER TABLE `favorites`
  ADD PRIMARY KEY (`favorite_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `flash_deals`
--
ALTER TABLE `flash_deals`
  ADD PRIMARY KEY (`deal_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `conversation_id` (`conversation_id`);

--
-- Indexes for table `message_images`
--
ALTER TABLE `message_images`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `message_id` (`message_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `address_id` (`address_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `variant_id` (`variant_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `brand_id` (`brand_id`);

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `product_imports`
--
ALTER TABLE `product_imports`
  ADD PRIMARY KEY (`import_id`),
  ADD KEY `supplier_id` (`supplier_id`);

--
-- Indexes for table `product_variants`
--
ALTER TABLE `product_variants`
  ADD PRIMARY KEY (`variant_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `review_images`
--
ALTER TABLE `review_images`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `review_id` (`review_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `sliders`
--
ALTER TABLE `sliders`
  ADD PRIMARY KEY (`slider_id`);

--
-- Indexes for table `stock_history`
--
ALTER TABLE `stock_history`
  ADD PRIMARY KEY (`stock_id`),
  ADD KEY `variant_id` (`variant_id`),
  ADD KEY `import_id` (`import_id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`supplier_id`);

--
-- Indexes for table `system_configs`
--
ALTER TABLE `system_configs`
  ADD PRIMARY KEY (`config_key`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `role_id` (`role_id`);

--
-- Indexes for table `vouchers`
--
ALTER TABLE `vouchers`
  ADD PRIMARY KEY (`voucher_id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `voucher_usage`
--
ALTER TABLE `voucher_usage`
  ADD PRIMARY KEY (`usage_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `voucher_id` (`voucher_id`),
  ADD KEY `order_id` (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `address_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `brand_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `cart_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `cart_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `comment_likes`
--
ALTER TABLE `comment_likes`
  MODIFY `like_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `conversations`
--
ALTER TABLE `conversations`
  MODIFY `conversation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `favorites`
--
ALTER TABLE `favorites`
  MODIFY `favorite_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `flash_deals`
--
ALTER TABLE `flash_deals`
  MODIFY `deal_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `message_images`
--
ALTER TABLE `message_images`
  MODIFY `image_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `order_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `product_images`
--
ALTER TABLE `product_images`
  MODIFY `image_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `product_imports`
--
ALTER TABLE `product_imports`
  MODIFY `import_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product_variants`
--
ALTER TABLE `product_variants`
  MODIFY `variant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `review_images`
--
ALTER TABLE `review_images`
  MODIFY `image_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sliders`
--
ALTER TABLE `sliders`
  MODIFY `slider_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `stock_history`
--
ALTER TABLE `stock_history`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `supplier_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `vouchers`
--
ALTER TABLE `vouchers`
  MODIFY `voucher_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `voucher_usage`
--
ALTER TABLE `voucher_usage`
  MODIFY `usage_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`);

--
-- Constraints for table `comment_likes`
--
ALTER TABLE `comment_likes`
  ADD CONSTRAINT `comment_likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `comment_likes_ibfk_2` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`) ON DELETE CASCADE;

--
-- Constraints for table `conversations`
--
ALTER TABLE `conversations`
  ADD CONSTRAINT `conversations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `flash_deals`
--
ALTER TABLE `flash_deals`
  ADD CONSTRAINT `flash_deals_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`conversation_id`) ON DELETE CASCADE;

--
-- Constraints for table `message_images`
--
ALTER TABLE `message_images`
  ADD CONSTRAINT `message_images_ibfk_1` FOREIGN KEY (`message_id`) REFERENCES `messages` (`message_id`) ON DELETE CASCADE;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`address_id`) ON DELETE SET NULL;

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`brand_id`) ON DELETE SET NULL;

--
-- Constraints for table `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `product_imports`
--
ALTER TABLE `product_imports`
  ADD CONSTRAINT `product_imports_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`) ON DELETE SET NULL;

--
-- Constraints for table `product_variants`
--
ALTER TABLE `product_variants`
  ADD CONSTRAINT `product_variants_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `review_images`
--
ALTER TABLE `review_images`
  ADD CONSTRAINT `review_images_ibfk_1` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`) ON DELETE CASCADE;

--
-- Constraints for table `stock_history`
--
ALTER TABLE `stock_history`
  ADD CONSTRAINT `stock_history_ibfk_1` FOREIGN KEY (`variant_id`) REFERENCES `product_variants` (`variant_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `stock_history_ibfk_2` FOREIGN KEY (`import_id`) REFERENCES `product_imports` (`import_id`) ON DELETE SET NULL;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE SET NULL;

--
-- Constraints for table `voucher_usage`
--
ALTER TABLE `voucher_usage`
  ADD CONSTRAINT `voucher_usage_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `voucher_usage_ibfk_2` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`voucher_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `voucher_usage_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
