# MenuItem Table
# NOTE: To make sure the enum constraint does not get violated put line below in database
# SET sql_mode = "traditional";
CREATE TABLE `menuItem` (
  `id`            INT(5)                            NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(20)                       NOT NULL,
  `price`         FLOAT(4, 2)                       NOT NULL,
  `imageFileName` VARCHAR(24),
  `category`      ENUM ('FOOD', 'DRINK', 'DESSERT') NOT NULL,
  PRIMARY KEY (`id`)
);

# Customer Table
# TODO: Add Email,...
CREATE TABLE `customer` (
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `fullName`    VARCHAR(20) NOT NULL,
  `phoneNumber` CHAR(12)    NOT NULL,
  PRIMARY KEY (`id`)
);

# Restaurant's Table Table :D
CREATE TABLE `restaurantTable` (
  `id`            INT(5) NOT NULL AUTO_INCREMENT,
  `numberOfSeats` INT(2),
  CHECK (`numberOfSeats` > 1),
  PRIMARY KEY (`id`)
);

# CheckRequestDTO Table
CREATE TABLE `checkRequestDTO` (
  `id`                INT(10)      NOT NULL AUTO_INCREMENT,
  `date`              DATE         NOT NULL,
  `numberOfSeats`     INT(2)       NOT NULL,
  `customerId`        INT(11)      NOT NULL,
  `start`             CHAR(4)      NOT NULL,
  `end`               CHAR(4)      NOT NULL,
  `otherRequirements` VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (`customerId`) REFERENCES `customer` (`id`),
  CHECK (`numberOfSeats` > 2)
);

# Reservation Table
CREATE TABLE `reservation` (
  `id`              INT(10) NOT NULL AUTO_INCREMENT,
  `submissionDate`  DATE    NOT NULL,
  `reservationDate` DATE    NOT NULL,
  `start`           CHAR(4),
  `end`             CHAR(4),
  `customerId`      INT(11) NOT NULL,
  `tableId`         INT(5)  NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customerId`) REFERENCES `customer` (`id`),
  FOREIGN KEY (`tableId`) REFERENCES `restaurantTable` (`id`)
);

# Food Order Table -> Many to Many Extra Table
CREATE TABLE `foodOrder` (
  `id`            INT(10) NOT NULL AUTO_INCREMENT,
  `totalCost`     FLOAT(4, 2),
  `reservationId` INT(10) NOT NULL,
  `status`        VARCHAR(15),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`reservationId`) REFERENCES `reservation` (`id`)
);

# (MenuItem, Order) Pair Table
CREATE TABLE `menuItemOrder` (
  `orderId`    INT(10)     NOT NULL,
  `menuItemId` INT(5)      NOT NULL,
  `number`     INT(3)      NOT NULL,
  `name`       VARCHAR(20) NOT NULL,
  `price`      FLOAT(4, 2) NOT NULL,
  PRIMARY KEY (`orderId`, `menuItemId`),
  FOREIGN KEY (`orderId`) REFERENCES `foodOrder` (`id`),
  FOREIGN KEY (`menuItemId`) REFERENCES `menuItem` (`id`)
);

# Creating Stored Procedures...
# NOTE: be careful of delimiter change

DELIMITER //
# Total Sale Per Day Procedure
CREATE PROCEDURE `totalSalePerDay`()
  BEGIN
    SELECT
      `reservationDate`,
      sum(`totalCost`)
    FROM `reservation`
      JOIN `foodOrder`
        ON `reservation`.`id` = `reservationId`
    GROUP BY `reservationDate`;
  END;
//

DELIMITER //
# Total Sale Between Given Dates
CREATE PROCEDURE `totalSaleGroupByDate`(IN startDate DATE, IN endDate DATE)
  BEGIN
    SELECT
      `reservationDate`,
      sum(`totalCost`)
    FROM `reservation`
      JOIN `foodOrder`
        ON `reservation`.`id` = `foodOrder`.`reservationId`
    WHERE
      `reservation`.`reservationDate` <= `endDate` AND
      `reservation`.`reservationDate` >= `startDate`
    GROUP BY `reservationDate`;
  END;
//

DELIMITER //
# Total Income
CREATE PROCEDURE `totalIncome`()
  BEGIN
    SELECT sum(`totalCost`) AS `totalIncome`
    FROM `reservation`
      JOIN `foodOrder`
        ON `reservation`.id = `reservationId`;
  END;
//

DELIMITER ;

