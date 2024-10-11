-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 11, 2024 at 07:40 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `deadline` date NOT NULL,
  `importance` varchar(255) NOT NULL,
  `priority` int(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `checkbox` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `name`, `deadline`, `importance`, `priority`, `description`, `checkbox`) VALUES
(7, 'study', '2023-02-13', 'Not Important', 101, 'yess', 0),
(9, 'plan parties', '2023-02-20', 'Not Important', 101, 'no', 0),
(10, 'exam', '2023-02-09', 'Very Important', 103, '!!', 0),
(12, 'project', '2023-02-16', 'Very Important', 103, 'oopproject', 0),
(13, 'idk', '2023-03-02', 'Not Important', 101, 'dasdasd', 0),
(26, 'dddddddddd', '2023-06-01', 'Important', 102, 'ddddddddddddd', 0),
(40, 'ddddddddddddddd', '2023-02-17', 'Important', 102, 'dddddddddddddddddd', 0),
(41, 'fffffff', '2023-02-17', 'Important', 102, 'dddddddddd', 0),
(42, 'dddd', '2023-02-17', 'Important', 102, 'aaaaaaa', 0),
(45, 'Hello', '2024-10-14', 'Important', 7, 'yea yea', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
