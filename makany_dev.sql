-- phpMyAdmin SQL Dump
-- version 2.11.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 10, 2010 at 06:40 PM
-- Server version: 5.0.51
-- PHP Version: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `makany_dev`
--

-- --------------------------------------------------------

--
-- Table structure for table `map`
--

CREATE TABLE `map` (
  `Id` int(11) NOT NULL auto_increment,
  `Image_Url` varchar(1000) NOT NULL,
  `Place_id` int(11) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  `Width` int(11) NOT NULL,
  `Height` int(11) NOT NULL,
  `Scale` float NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `FK_PLACE_ID` (`Place_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `map`
--

INSERT INTO `map` (`Id`, `Image_Url`, `Place_id`, `Description`, `Width`, `Height`, `Scale`) VALUES
(1, 'images/floormap0-big.jpg', 1, 'City Stars First Floor', 1243, 931, 0.5);

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE `place` (
  `Id` int(11) NOT NULL auto_increment,
  `Place_type_id` int(11) NOT NULL,
  `Name` varchar(1000) NOT NULL,
  `Location` varchar(1000) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  `Image_Url` varchar(1000) NOT NULL,
  `Longitude` double NOT NULL,
  `Latitude` double NOT NULL,
  PRIMARY KEY  (`Id`),
  KEY `FK_PLACE_TYPE_ID` (`Place_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `place`
--

INSERT INTO `place` (`Id`, `Place_type_id`, `Name`, `Location`, `Description`, `Image_Url`, `Longitude`, `Latitude`) VALUES
(1, 1, 'City Stars', 'Nasr City', 'One of the biggest shopping malls in Egypt', 'images/CityStars.jpg', 31.345657, 30.073244),
(2, 2, 'American University in Cairo', 'New Cairo', 'Established in 1911', 'images/auc.jpg', 31.5003444, 30.019944);

-- --------------------------------------------------------

--
-- Table structure for table `place_type`
--

CREATE TABLE `place_type` (
  `Id` int(11) NOT NULL auto_increment,
  `Type` varchar(1000) NOT NULL,
  PRIMARY KEY  (`Id`)
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

CREATE TABLE `unit` (
  `Id` int(11) NOT NULL auto_increment,
  `Unit_type_id` int(11) NOT NULL,
  `Map_id` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Image_Url` varchar(1000) NOT NULL,
  `Description` mediumtext NOT NULL,
  `X` int(11) default NULL,
  `Y` int(11) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `FK_MAP_ID` (`Map_id`),
  KEY `FK_UNIT_TYPE_ID` (`Unit_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`Id`, `Unit_type_id`, `Map_id`, `Name`, `Image_Url`, `Description`, `X`, `Y`) VALUES
(1, 1, 1, 'Sfera', 'images/sfera.jpg', 'A very fashionable Department store for men, women and children', 500, 500),
(2, 2, 1, 'Apple', 'images/apple.jpg', 'Apple Store for selling iphones, ipods, ipads,...etc', 700, 600),
(3, 1, 1, 'Zara', 'images/zara.jpg', 'The most popular Zara shop for men, women, kids and babies clothes.', 250, 450);

-- --------------------------------------------------------

--
-- Table structure for table `unit_type`
--

CREATE TABLE `unit_type` (
  `Id` int(11) NOT NULL,
  `Name` varchar(1000) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  PRIMARY KEY  (`Id`)
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
