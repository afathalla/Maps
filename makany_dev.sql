-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 03, 2010 at 11:09 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `makany_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `map`
--

CREATE TABLE IF NOT EXISTS `map` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Image_Url` varchar(1000) NOT NULL,
  `Place_id` int(11) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  `Width` int(11) NOT NULL,
  `Height` int(11) NOT NULL,
  `Scale` float NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_PLACE_ID` (`Place_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `map`
--

INSERT INTO `map` (`Id`, `Image_Url`, `Place_id`, `Description`, `Width`, `Height`, `Scale`) VALUES
(1, 'images/floormap0-big.jpg', 1, 'City Stars First Floor', 1243, 931, 0.5),
(2, 'images/floormap1-big.jpg', 1, 'City Stars Second Floor', 1243, 931, 0.5);

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE IF NOT EXISTS `place` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Place_type_id` int(11) NOT NULL,
  `Name` varchar(1000) NOT NULL,
  `Location` varchar(1000) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  `Image_Url` varchar(1000) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_PLACE_TYPE_ID` (`Place_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `place`
--

INSERT INTO `place` (`Id`, `Place_type_id`, `Name`, `Location`, `Description`, `Image_Url`) VALUES
(1, 1, 'City Stars', 'Nasr City', 'One of the biggest shopping malls in Egypt', 'images/CityStars.jpg'),
(2, 2, 'American University in Cairo', 'Eltagamo'' elkhamees, New Cairo', 'Established in 1911', 'images/auc.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `place_type`
--

CREATE TABLE IF NOT EXISTS `place_type` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(1000) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `place_type`
--

INSERT INTO `place_type` (`Id`, `Type`) VALUES
(1, 'Shopping Mall'),
(2, 'University');

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE IF NOT EXISTS `unit` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Unit_type_id` int(11) NOT NULL,
  `Map_id` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Image_Url` varchar(1000) NOT NULL,
  `Description` mediumtext NOT NULL,
  `X` int(11) DEFAULT NULL,
  `Y` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_MAP_ID` (`Map_id`),
  KEY `FK_UNIT_TYPE_ID` (`Unit_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`Id`, `Unit_type_id`, `Map_id`, `Name`, `Image_Url`, `Description`, `X`, `Y`) VALUES
(1, 1, 1, 'Sfera', 'images/sfera.jpg', 'A very fashionable Department store for men, women and children', 500, 500),
(2, 2, 2, 'Apple', 'images/apple.jpg', 'The famous Apple Store', 400, 400),
(3, 1, 1, 'Zara', 'images/zara.jpg', 'The most popular Zara shop for men, women, kids and babies clothes.', 250, 450);

-- --------------------------------------------------------

--
-- Table structure for table `unit_type`
--

CREATE TABLE IF NOT EXISTS `unit_type` (
  `Id` int(11) NOT NULL,
  `Name` varchar(1000) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `unit_type`
--

INSERT INTO `unit_type` (`Id`, `Name`, `Description`) VALUES
(1, 'Departmental Stores', 'Stores for Men, Women and Children.'),
(2, 'Electronics & Appliances', 'Stores for selling TVs, Mobiles Phones, ..etc');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `map`
--
ALTER TABLE `map`
  ADD CONSTRAINT `FK_PLACE_ID` FOREIGN KEY (`Place_id`) REFERENCES `place` (`Id`);

--
-- Constraints for table `place`
--
ALTER TABLE `place`
  ADD CONSTRAINT `FK_PLACE_TYPE_ID` FOREIGN KEY (`Place_type_id`) REFERENCES `place_type` (`Id`);

--
-- Constraints for table `unit`
--
ALTER TABLE `unit`
  ADD CONSTRAINT `FK_MAP_ID` FOREIGN KEY (`Map_id`) REFERENCES `map` (`Id`),
  ADD CONSTRAINT `FK_UNIT_TYPE_ID` FOREIGN KEY (`Unit_type_id`) REFERENCES `unit_type` (`Id`);
