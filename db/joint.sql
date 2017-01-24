-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 24, 2017 at 05:56 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `doths_lms`
--

-- --------------------------------------------------------

--
-- Table structure for table `joint`
--

CREATE TABLE `joint` (
  `id` int(11) NOT NULL,
  `red_value` int(11) NOT NULL,
  `green_value` int(11) NOT NULL,
  `blue_value` int(11) NOT NULL,
  `alpha_value` int(11) NOT NULL,
  `red_limit` int(11) NOT NULL,
  `green_limit` int(11) NOT NULL,
  `blue_limit` int(11) NOT NULL,
  `alpha_limit` int(11) NOT NULL,
  `min_width` int(11) DEFAULT '0',
  `min_height` int(11) DEFAULT '0',
  `max_width` int(11) DEFAULT '0',
  `max_height` int(11) DEFAULT '0',
  `hand_used` varchar(100) NOT NULL,
  `orientation` varchar(100) NOT NULL,
  `hand_part` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `joint`
--

INSERT INTO `joint` (`id`, `red_value`, `green_value`, `blue_value`, `alpha_value`, `red_limit`, `green_limit`, `blue_limit`, `alpha_limit`, `min_width`, `min_height`, `max_width`, `max_height`, `hand_used`, `orientation`, `hand_part`) VALUES
(4, 255, 0, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'front', 'palm'),
(5, 0, 255, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'back', 'palm'),
(6, 255, 0, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'front', 'palm'),
(7, 0, 255, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'back', 'palm'),
(8, 255, 83, 73, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'front', 'thumb'),
(10, 13, 152, 186, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'back', 'thumb'),
(12, 255, 83, 73, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'front', 'thumb'),
(14, 13, 152, 186, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'back', 'thumb'),
(16, 255, 165, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'front', 'index'),
(19, 0, 0, 255, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'back', 'index'),
(22, 255, 165, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'front', 'index'),
(25, 0, 0, 255, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'back', 'index'),
(28, 255, 174, 66, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'front', 'middle'),
(31, 138, 43, 226, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'back', 'middle'),
(34, 255, 174, 66, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'front', 'middle'),
(37, 138, 43, 226, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'back', 'middle'),
(40, 255, 255, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'front', 'ring'),
(43, 238, 130, 238, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'back', 'ring'),
(46, 255, 255, 0, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'front', 'ring'),
(49, 238, 130, 238, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'back', 'ring'),
(52, 154, 205, 50, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'front', 'pinky'),
(55, 199, 21, 133, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'right', 'back', 'pinky'),
(58, 154, 205, 50, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'front', 'pinky'),
(61, 199, 21, 133, 255, 30, 30, 30, 255, 10, 10, 0, 0, 'left', 'back', 'pinky');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `joint`
--
ALTER TABLE `joint`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `joint`
--
ALTER TABLE `joint`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
