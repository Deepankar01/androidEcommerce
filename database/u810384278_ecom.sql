-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 21, 2015 at 10:50 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u810384278_ecom`
--

-- --------------------------------------------------------

--
-- Table structure for table `buy_product`
--

CREATE TABLE IF NOT EXISTS `buy_product` (
  `sno` int(11) NOT NULL,
  `thumbnail` varchar(10000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'images/thumb/default.png',
  `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `category_code` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `seller` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Buyer` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` varchar(200) COLLATE utf8_unicode_ci DEFAULT 'Pending'
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `buy_product`
--

INSERT INTO `buy_product` (`sno`, `thumbnail`, `name`, `price`, `category_code`, `description`, `seller`, `Buyer`, `Time`, `Status`) VALUES
(1, 'images/thumb/1.png', 'Nokia X2 Dual Sim', 4499, '100.100', 'An Awsome Phone', 'Wolverin@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-20 17:05:43', 'Pending'),
(2, 'images/thumb/2.png', 'Motorola Droid', 41999, '100.100', 'An Awsome Phone', 'ProfessorXavier@gmail.com', 'oct94abhi@gmail.com', '2015-07-20 17:05:43', 'Pending'),
(3, 'images/thumb/3.png', 'Apple iPhone 6', 63000, '100.100', 'An Awsome Phone', 'Hulk@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-20 17:39:42', 'Pending'),
(4, 'images/thumb/4.png', 'Microsoft Lumia 535 DS', 7245, '100.100', 'An Awsome Phone', 'SpiderReddy@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-20 17:43:27', 'Pending'),
(7, 'images/thumb/7.png', 'Asus Zenfone 2', 12999, '100.100', 'An Awsome Phone', 'BatmanGoel@gmail.com', 'oct94abhi@gmail.com', '2015-07-21 15:45:03', 'Pending'),
(8, 'images/thumb/8.png', 'Mi 4', 19999, '100.100', 'An Awsome Phone', 'IronMan@gmail.com', 'oct94abhi@gmail.com', '2015-07-21 15:47:08', 'Pending'),
(22, 'images/thumb/17.png', 'pendrive', 100, '100.300', 'PD', 'oct94abhi@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-21 17:22:32', 'Complete'),
(12, 'images/thumb/12.png', 'Tata Truck 320XL', 1500000, '300.500', 'A Very Nice Truck', 'chipdale@gmail.com', 'oct94abhi@gmail.com', '2015-07-21 18:07:50', 'Pending'),
(26, 'images/thumb/17.png', 'wallet', 2, '100.100', 'bc', 'oct94abhi@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-21 19:54:03', 'Complete'),
(27, 'images/thumb/17.png', 'lh', 20, '100.100', 'lh', 'oct94abhi@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-21 19:57:38', 'Complete'),
(30, 'images/thumb/30.png', 'pooh', 4, '100.100', 'fhhh', 'oct94abhi@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-21 20:07:25', 'Complete'),
(29, 'images/thumb/29.png', 'bottle', 1334, '100.100', 'nice bottle', 'deepankar01agrawal@gmail.com', 'oct94abhi@gmail.com', '2015-07-21 20:10:31', 'Complete'),
(28, 'images/thumb/17.png', 'abhinav', 9, '100.200', 'land k', 'oct94abhi@gmail.com', 'deepankar01agrawal@gmail.com', '2015-07-21 20:16:56', 'Complete');

-- --------------------------------------------------------

--
-- Table structure for table `category_list`
--

CREATE TABLE IF NOT EXISTS `category_list` (
  `categoryName` varchar(200) NOT NULL,
  `categoryId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category_list`
--

INSERT INTO `category_list` (`categoryName`, `categoryId`) VALUES
('Electronics', 100),
('Vehicles', 300);

-- --------------------------------------------------------

--
-- Table structure for table `product_list`
--

CREATE TABLE IF NOT EXISTS `product_list` (
  `sno` int(11) NOT NULL,
  `thumbnail` varchar(10000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'images/thumb/default.png',
  `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `category_code` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `seller` varchar(200) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product_list`
--

INSERT INTO `product_list` (`sno`, `thumbnail`, `name`, `price`, `category_code`, `description`, `seller`) VALUES
(5, 'images/thumb/5.png', 'Microsoft LifeChat LX-6000 for Business', 2600, '100.300', 'An Awsome Phone', 'TomHanks@gmail.com'),
(6, 'images/thumb/6.png', 'Microsoft DC-21 Power Bank 6000 mAh', 2599, '100.800', 'An Awsome Phone', 'JackSparrow@gmail.com'),
(13, 'images/thumb/13.png', 'Ford Truck XL', 1234567, '300.500', 'A very nice truck', 'bullu@gmail.com'),
(9, 'images/thumb/9.png', 'Ferrari Spider', 1100000, '300.400', 'Really Nice Car', 'mickeymouse@gmail.com'),
(10, 'images/thumb/10.png', 'BMW 320di', 600000, '300.400', 'Really Nice Car', 'donaldduck@gmail.com'),
(11, 'images/thumb/11.png', 'Man Truck xcv400', 3000000, '300.500', 'Very Nice Truck', 'minnimouse@gmail.com'),
(14, 'images/thumb/14.png', 'Hero Honda Hunk', 45000, '300.600', 'A very nice bike', 'rahul@gmail.com'),
(15, 'images/thumb/15.png', 'Yahama FZS', 575336, '300.600', 'A very nice bike', 'yahama@gmail.com'),
(16, 'images/thumb/16.png', 'Honda Unicorn', 123400, '300.600', 'A very old bike', 'hero@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `subcategory_list`
--

CREATE TABLE IF NOT EXISTS `subcategory_list` (
  `CategoryID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `SubCategoryID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subcategory_list`
--

INSERT INTO `subcategory_list` (`CategoryID`, `name`, `SubCategoryID`) VALUES
(100, 'Mobile Phone', 100),
(100, 'Laptop', 200),
(100, 'Headphones', 300),
(300, 'Car', 400),
(300, 'Truck', 500),
(300, 'Motorbike', 600),
(300, 'Aeroplane', 700),
(100, 'Power Banks', 800);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES
(16, '557041977e1a17.49584133', 'Abhinav Anand', 'oct94abhi@gmail.com', 'prnJp/dZvuwuZLd8+AtmzUA0HsIyOWU3NzRiNzlj', '29e774b79c', '2015-06-04 17:46:23', NULL),
(17, '5575667d39a207.68416115', 'Abhinav Anand', 'oct4abhi@gmail.com', 'B0+VI9cre51Ku69LeiiXDxE7eE5jZTMxMGFiMDM3', 'ce310ab037', '2015-06-08 15:25:09', NULL),
(18, '55ad0779d930e5.28981006', '', '', 'tWN/+LsG3KYL9SEOieUp2t4XLUBhOGJhNGZjNGIz', 'a8ba4fc4b3', '2015-07-20 14:39:47', NULL),
(19, '55ad08a2566c94.28126498', 'Abhinav', 'oct94abhi@gmail.co', 'kNNIEJC3sndROOslXCXrWTHEAec2NGMwODVmMTBi', '64c085f10b', '2015-07-20 14:44:44', NULL),
(20, '55ae7ed7b54509.75922207', 'Deepankar', 'deepankar01agrawal@gmail.com', 'lXSTREETJOVSBzcj0h6QPi8lFUg4YmNjMmRkZjAx', '8bcc2ddf01', '2015-07-21 22:48:15', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buy_product`
--
ALTER TABLE `buy_product`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `product_list`
--
ALTER TABLE `product_list`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `subcategory_list`
--
ALTER TABLE `subcategory_list`
  ADD UNIQUE KEY `SubCategoryID` (`SubCategoryID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`), ADD UNIQUE KEY `unique_id` (`unique_id`), ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `buy_product`
--
ALTER TABLE `buy_product`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `product_list`
--
ALTER TABLE `product_list`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
