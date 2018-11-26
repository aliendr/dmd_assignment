-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Ноя 26 2018 г., 21:15
-- Версия сервера: 5.7.17-log
-- Версия PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `dmd20`
--

-- --------------------------------------------------------

--
-- Структура таблицы `car`
--

CREATE TABLE `car` (
  `id` varchar(6) NOT NULL,
  `color` varchar(15) NOT NULL,
  `typeOfPlug` int(1) NOT NULL,
  `type` varchar(25) NOT NULL,
  `availability` tinyint(1) NOT NULL,
  `cost` int(5) NOT NULL,
  `location` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `car`
--

INSERT INTO `car` (`id`, `color`, `typeOfPlug`, `type`, `availability`, `cost`, `location`) VALUES
('2', 'red', 0, 'type', 1, 100, 'loc'),
('am', 'blue', 0, 'type', 0, 10, 'loc'),
('hu7i', 'blue', 1, 'hui', 1, 21, 'hui'),
('hui', 'blue', 1, 'hui', 1, 21, 'hui');

-- --------------------------------------------------------

--
-- Структура таблицы `car_part`
--

CREATE TABLE `car_part` (
  `owner_type` int(1) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `amount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `chargingstation`
--

CREATE TABLE `chargingstation` (
  `uid` int(3) NOT NULL,
  `location` varchar(21) NOT NULL,
  `price` int(2) NOT NULL,
  `time` int(2) NOT NULL,
  `amount` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE `customer` (
  `location` varchar(21) NOT NULL,
  `fullName` varchar(30) NOT NULL,
  `username` varchar(15) NOT NULL,
  `email` varchar(40) NOT NULL,
  `phone` int(11) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `history_man_car`
--

CREATE TABLE `history_man_car` (
  `username` varchar(15) NOT NULL,
  `car_id` varchar(6) NOT NULL,
  `start_time` datetime NOT NULL,
  `finish_time` datetime NOT NULL,
  `location_of_start` varchar(21) NOT NULL,
  `location_of_finish` varchar(21) NOT NULL,
  `payment` int(3) NOT NULL,
  `location_of_car` varchar(21) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `history_man_car`
--

INSERT INTO `history_man_car` (`username`, `car_id`, `start_time`, `finish_time`, `location_of_start`, `location_of_finish`, `payment`, `location_of_car`) VALUES
('username', 'am', '2018-11-25 22:22:22', '2018-11-25 23:23:23', 'hui1', 'hui2', 0, ''),
('username', 'hu7i', '2018-11-25 22:22:22', '2018-11-25 23:23:23', 'hui1', 'hui2', 0, ''),
('hui', '2', '2018-11-25 22:22:22', '2018-11-25 23:23:23', 'hui1', 'hui2', 0, ''),
('username', 'hu7i', '2018-11-25 22:22:22', '2018-11-25 23:23:23', 'hui1', 'hui2', 0, ''),
('hui', '2', '2018-11-25 22:22:22', '2018-11-25 23:23:23', 'hui1', 'hui2', 0, ''),
('hui', 'mui', '2018-11-25 05:21:30', '2018-11-25 17:59:59', '', '', 0, ''),
('fd', 'as', '2018-12-25 12:18:21', '2018-12-25 12:37:27', '', '', 0, ''),
('hui', 'mui', '2018-11-25 05:21:30', '2018-11-25 17:59:59', '', '', 0, ''),
('fd', 'as', '2018-12-25 12:18:21', '2018-12-25 12:37:27', '', '', 0, ''),
('kek', 'k', '2018-11-26 13:44:46', '2018-11-26 07:35:46', '', '', 0, ''),
('kek', 'k', '2018-11-26 13:44:46', '2018-11-26 07:35:46', '', '', 0, ''),
('pisy', '12324', '2018-11-25 07:29:29', '2018-11-25 15:29:30', '', '', 0, ''),
('pisy', '12324', '2018-11-25 07:29:29', '2018-11-25 15:29:30', '', '', 0, ''),
('blua', '1234', '2018-11-30 04:21:34', '0000-00-00 00:00:00', '12.2121212 12.2121212', '', 0, '10.2121212 09.1212121'),
('hui', '4321', '2018-11-30 12:37:39', '0000-00-00 00:00:00', '50.1234567 42.8765432', '', 0, '10.6273617 05.6274821'),
('blua', '1234', '2018-11-30 04:21:34', '0000-00-00 00:00:00', '12.2121212 12.2121212', '', 0, '10.2121212 09.1212121'),
('hui', '4321', '2018-11-30 12:37:39', '0000-00-00 00:00:00', '50.1234567 42.8765432', '', 0, '10.6273617 05.6274821'),
('as', 'ds', '2018-11-13 09:23:26', '2018-11-13 11:36:23', '12.2121212 12.2121212', '42.2121212 42.2121212', 0, '10.2121212 09.1212121'),
('as', 'ds', '2018-11-13 09:23:26', '2018-11-13 11:36:23', '12.2121212 12.2121212', '42.2121212 42.2121212', 0, '10.2121212 09.1212121'),
('dsa', 'qwe', '2018-11-01 01:03:04', '2018-11-01 02:06:06', '', '', 0, ''),
('dsa', 'qwe', '2018-11-01 01:03:04', '2018-11-01 02:06:06', '', '', 0, ''),
('ewq', 'qwe', '2018-11-25 00:00:00', '2018-11-25 00:00:00', '', '', 0, ''),
('ewq', 'qwe', '2018-11-25 00:00:00', '2018-11-25 00:00:00', '', '', 0, ''),
('username', 'qwe', '2018-11-26 08:19:34', '2018-11-26 12:19:47', '50.6253746 24.8264732', '15.6253746 22.8264732', 0, '5.36253746 20.8264732'),
('username', 'qwe', '2018-11-26 08:19:34', '2018-11-26 12:19:47', '50.6253746 24.8264732', '15.6253746 22.8264732', 0, '5.36253746 20.8264732'),
('pisy', 'qwe', '2018-11-25 07:29:29', '2018-11-25 15:29:30', '49.6253746 25.8264732', '', 0, ''),
('pisy', 'qwe', '2018-11-25 07:29:29', '2018-11-25 15:29:30', '49.6253746 25.8264732', '', 0, ''),
('username', 'hu7i', '2018-11-26 13:19:28', '0000-00-00 00:00:00', '12.2121212 12.2121212', '', 0, ''),
('username', 'hu7i', '2018-11-26 13:19:28', '0000-00-00 00:00:00', '12.2121212 12.2121212', '', 0, ''),
('pisy', 'hu7i', '2018-11-26 17:48:41', '0000-00-00 00:00:00', '50.6253746 24.8264732', '', 0, ''),
('hui', '2', '2018-11-26 18:38:21', '0000-00-00 00:00:00', '50.6253746 24.8264732', '', 0, ''),
('pisy', 'hu7i', '2018-11-26 17:48:41', '0000-00-00 00:00:00', '50.6253746 24.8264732', '', 0, ''),
('hui', '2', '2018-11-26 18:38:21', '0000-00-00 00:00:00', '50.6253746 24.8264732', '', 0, ''),
('I', 'qwe', '2018-11-25 07:29:29', '2018-11-30 11:36:23', '12.2121212 12.2121212', '15.6253746 22.8264732', 15, '10.2121212 09.1212121'),
('I', 'qwe', '2018-11-25 07:29:29', '2018-11-30 11:36:23', '12.2121212 12.2121212', '15.6253746 22.8264732', 15, '10.2121212 09.1212121'),
('I', 'qwe', '2018-11-25 07:29:29', '2018-11-30 11:36:23', '12.2121212 12.2121212', '15.6253746 22.8264732', 15, '10.2121212 09.1212121'),
('I', 'qwe', '2018-11-25 07:29:29', '2018-11-30 11:36:23', '12.2121212 12.2121212', '15.6253746 22.8264732', 15, '10.2121212 09.1212121'),
('s', 'qwe', '2018-11-26 08:28:31', '2018-11-26 09:29:59', '50.6253746 24.8264732', '50.6253746 24.8264732', 12, '50.6253746 24.8264732'),
('s', 'qwe', '2018-11-26 08:28:31', '2018-11-26 09:29:59', '50.6253746 24.8264732', '50.6253746 24.8264732', 12, '50.6253746 24.8264732'),
('s', 'qwe', '2018-11-26 08:28:31', '2018-11-26 09:29:59', '50.6253746 24.8264732', '50.6253746 24.8264732', 12, '50.6253746 24.8264732'),
('s', 'qwe', '2018-11-26 08:28:31', '2018-11-26 09:29:59', '50.6253746 24.8264732', '50.6253746 24.8264732', 12, '50.6253746 24.8264732');

-- --------------------------------------------------------

--
-- Структура таблицы `history_of_charging`
--

CREATE TABLE `history_of_charging` (
  `uid` int(3) NOT NULL,
  `car_id` varchar(6) NOT NULL,
  `time` datetime NOT NULL,
  `finish` datetime NOT NULL,
  `cost` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `history_of_charging`
--

INSERT INTO `history_of_charging` (`uid`, `car_id`, `time`, `finish`, `cost`) VALUES
(12, '32', '2018-11-25 06:25:21', '2018-11-25 06:48:51', 0),
(22, '2', '2018-11-25 05:26:31', '2018-11-25 11:33:42', 0),
(228, 'mui', '2018-11-26 13:21:35', '2018-11-26 19:49:44', 0),
(228, 'mui', '2018-11-26 13:21:35', '2018-11-26 19:49:44', 0),
(12, 'qwe', '2018-11-27 00:00:00', '2018-11-27 00:00:01', 100),
(12, 'qwe', '2018-11-27 00:00:00', '2018-11-27 00:00:01', 100),
(2321, 'ew', '2018-11-27 07:00:00', '2018-11-27 19:00:00', 50),
(2321, 'ew', '2018-11-27 07:00:00', '2018-11-27 19:00:00', 50);

-- --------------------------------------------------------

--
-- Структура таблицы `history_of_purchase`
--

CREATE TABLE `history_of_purchase` (
  `wid` int(3) NOT NULL,
  `pid` int(3) NOT NULL,
  `part_type` varchar(20) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `history_of_repairing`
--

CREATE TABLE `history_of_repairing` (
  `part_type` varchar(20) NOT NULL,
  `car_id` varchar(6) NOT NULL,
  `time` datetime NOT NULL,
  `wid` int(3) NOT NULL,
  `cost` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `history_of_repairing`
--

INSERT INTO `history_of_repairing` (`part_type`, `car_id`, `time`, `wid`, `cost`) VALUES
('wheel', 'mui', '2018-11-26 07:27:43', 228, 0),
('wheel', '2', '2018-11-26 12:23:37', 228, 0),
('door', '12', '2018-11-27 00:00:00', 228, 0),
('wheel', '2', '2018-11-26 12:23:37', 228, 0),
('door', '12', '2018-11-27 00:00:00', 228, 0),
('wheel', 'mui', '2018-11-29 12:39:47', 1488, 0),
('door', '2', '2018-11-20 10:40:42', 228, 0),
('wheel', 'mui', '2018-11-29 12:39:47', 1488, 0),
('door', '2', '2018-11-20 10:40:42', 228, 0),
('motor', 'qwe', '2018-11-26 07:37:32', 5, 0),
('motor', 'hu7i', '2018-11-26 13:21:35', 5, 0),
('motor', 'hu7i', '2018-11-26 13:21:35', 5, 0),
('motor', 'qwe', '2018-11-27 04:27:39', 5, 111),
('motor', 'qwe', '2018-11-27 04:27:39', 5, 111);

-- --------------------------------------------------------

--
-- Структура таблицы `provider`
--

CREATE TABLE `provider` (
  `pid` int(3) NOT NULL,
  `price` int(3) NOT NULL,
  `name` varchar(20) NOT NULL,
  `phone` int(11) NOT NULL,
  `address` varchar(21) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `typeofplug`
--

CREATE TABLE `typeofplug` (
  `uid` int(3) NOT NULL,
  `shape` int(1) NOT NULL,
  `size` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `workshop`
--

CREATE TABLE `workshop` (
  `wid` int(3) NOT NULL,
  `location` varchar(21) NOT NULL,
  `timing` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `chargingstation`
--
ALTER TABLE `chargingstation`
  ADD PRIMARY KEY (`uid`);

--
-- Индексы таблицы `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`username`);

--
-- Индексы таблицы `provider`
--
ALTER TABLE `provider`
  ADD PRIMARY KEY (`pid`);

--
-- Индексы таблицы `workshop`
--
ALTER TABLE `workshop`
  ADD PRIMARY KEY (`wid`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `chargingstation`
--
ALTER TABLE `chargingstation`
  MODIFY `uid` int(3) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT для таблицы `provider`
--
ALTER TABLE `provider`
  MODIFY `pid` int(3) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT для таблицы `workshop`
--
ALTER TABLE `workshop`
  MODIFY `wid` int(3) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
