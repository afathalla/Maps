-- phpMyAdmin SQL Dump
-- version 2.11.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 14, 2010 at 06:31 AM
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
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `Id` int(11) NOT NULL auto_increment,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `Name` varchar(50) default NULL,
  `Email` varchar(50) default NULL,
  `Type` varchar(50) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Description` mediumtext NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`Id`, `Date`, `Time`, `Name`, `Email`, `Type`, `Title`, `Description`) VALUES
(1, '2010-09-13', '00:00:00', '', '', '', '', ''),
(2, '2010-09-13', '00:00:00', '', '', '', '', ''),
(3, '2010-09-13', '00:00:00', 'as', 'asdsad', 'Suggestion', 'asdsa', 'asdsa'),
(4, '2010-09-13', '00:00:00', 'Ahmed Farouk', 'ahmed.farouk@hotmail.com', 'Suggestion', 'Add Reviews/Recommendation Section', 'It would be great if you can add a reviews/recommendation section for every place.'),
(5, '2010-09-13', '00:00:00', '', '', 'Suggestion', 'Add Sliding Maps', ''),
(6, '2010-09-13', '00:00:00', '', '', 'Suggestion', 'Add better Maps', '');

-- --------------------------------------------------------

--
-- Table structure for table `feedback_type`
--

CREATE TABLE `feedback_type` (
  `Id` int(11) NOT NULL auto_increment,
  `Type` varchar(100) NOT NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `feedback_type`
--

INSERT INTO `feedback_type` (`Id`, `Type`) VALUES
(1, 'Suggestion'),
(2, 'Question'),
(3, 'Praise'),
(4, 'Concern'),
(5, 'Problem');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `map`
--

INSERT INTO `map` (`Id`, `Image_Url`, `Place_id`, `Description`, `Width`, `Height`, `Scale`) VALUES
(1, 'maps/floormap0-big.jpg', 1, 'City Stars First Floor', 1243, 931, 0.5),
(2, 'maps/floormap1-big.jpg', 1, 'City Stars Second Floor Map', 1243, 931, 0.5),
(3, 'maps/AUC-campus.jpg', 2, 'AUC Campus Map', 887, 512, 0.1);

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE `place` (
  `Id` int(11) NOT NULL auto_increment,
  `Place_type_id` int(11) NOT NULL,
  `Name` varchar(1000) NOT NULL,
  `Location` varchar(1000) NOT NULL,
  `Description` mediumtext NOT NULL,
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
(1, 1, 'City Stars', 'Nasr City', 'City Stars is a commercial development in Egypt. The complex is located between Nasr City and Heliopolis, in Cairo. It is considered the first integrated urban development project of its kind in the Middle East and Europe.  Built with an Ancient Egyptian theme, it consists of three pyramids (partially glass), surrounded by 11 towers, that make up the complex, plus a separate building for the shopping mall in its two phases.  The complex is equipped with some of the most advanced infrastructure and multi-media networks which complement the project’s design elements and customer services, which include an easily accessible indoor parking facility for over 6,000 vehicles. City Stars offers an unparalleled choice in retail and entertainment and brings together an unbeatable combination of leading brand names in a single destination.  City Stars consists of:\r\n* Three international hotels that provide over 1,500 rooms and suites.\r\n* Shopping and entertainment centre.\r\n* Medical centre.\r\n    * Residential Towers; offering 266 apartments, duplexes and penthouses.\r\n    * Office Towers; offering 70,000 m2 of office space.\r\n    * An International Exhibition Centre (20,000 m2)', 'images/CityStars.jpg', 31.345657, 30.073244),
(2, 2, 'American University in Cairo', 'New Cairo', 'The American University in Cairo (AUC) is an independent, nonprofit, apolitical, non-sectarian institution of higher learning located in Cairo, Egypt. The university’s mission is to to provide an American liberal arts education to students from all socio-economic backgrounds in Egypt and other nations around the world, and to make substantial contributions to Egypt''s intellectual life and culture.\r\n\r\nThe university offers American-style education at the undergraduate, graduate and professional levels, and it provides an extensive continuing education program. The university promotes the ideals of American education, professional education, and life-long learning.\r\n\r\nThe AUC student body represents over 100 countries and includes over 300 North American study abroad students. AUC''s faculty members, adjunct teaching staff and visiting lecturers are internationally diverse as well and include academics, business professionals, diplomats, journalists, writers and others from the United States, Egypt and other countries.', 'images/auc.jpg', 31.5003444, 30.019944);

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
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `Id` int(11) NOT NULL auto_increment,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `Name` varchar(100) default NULL,
  `Email` varchar(100) default NULL,
  `Phone` varchar(100) default NULL,
  `Unit_Id` int(11) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Rate` int(1) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`Id`, `Date`, `Time`, `Name`, `Email`, `Phone`, `Unit_Id`, `Description`, `Rate`) VALUES
(1, '2010-09-14', '04:45:56', 'Ahmed Farouk', 'ahmed@boslla.com', '002 0123456789', 4, 'This is the best place where you can buy cheap clothes from. You can buy blouses, shirts, t-shirts, jeans and shoes.', 1);

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE `unit` (
  `Id` int(11) NOT NULL auto_increment,
  `Place_id` int(11) NOT NULL,
  `Unit_type_id` int(11) NOT NULL,
  `Map_id` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Image_Url` varchar(1000) NOT NULL,
  `Description` mediumtext NOT NULL,
  `X` int(11) default NULL,
  `Y` int(11) default NULL,
  PRIMARY KEY  (`Id`),
  KEY `FK_MAP_ID` (`Map_id`),
  KEY `FK_UNIT_TYPE_ID` (`Unit_type_id`),
  KEY `FK_UNIT_PLACE_ID` (`Place_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`Id`, `Place_id`, `Unit_type_id`, `Map_id`, `Name`, `Image_Url`, `Description`, `X`, `Y`) VALUES
(1, 1, 1, 1, 'Sfera', 'images/sfera.jpg', 'A very fashionable Department store for men, women and children', 500, 500),
(2, 1, 2, 1, 'Apple', 'images/apple.jpg', 'Apple Store for selling iphones, ipods, ipads,...etc', 700, 600),
(3, 1, 1, 1, 'Zara', 'images/zara.jpg', 'The most popular Zara shop for men, women, kids and babies clothes.', 250, 450),
(4, 1, 1, 1, 'H & M', 'images/H&M.jpg', 'In June 2008 H&M opened a store in the Egyptian capital, Cairo. The store is in Cairo’s exclusive Stars Center mall in the new CityStars development and covers an impressive 3,500 square metres. The expansion is a result of H&M’s extended partnership with M.H. Alshaya. Franchising is an establishment strategy that is not part of H&M’s way of working outside of the Middle East.', 625, 440);

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
  ADD CONSTRAINT `FK_UNIT_PLACE_ID` FOREIGN KEY (`Place_id`) REFERENCES `place` (`Id`),
  ADD CONSTRAINT `FK_MAP_ID` FOREIGN KEY (`Map_id`) REFERENCES `map` (`Id`),
  ADD CONSTRAINT `FK_UNIT_TYPE_ID` FOREIGN KEY (`Unit_type_id`) REFERENCES `unit_type` (`Id`);
