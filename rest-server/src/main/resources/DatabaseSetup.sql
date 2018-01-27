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

# CheckRequest Table
CREATE TABLE `checkRequest` (
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

CREATE TABLE `foodOrder` (
  `id`            INT(10) NOT NULL AUTO_INCREMENT,
  `totalCost`     FLOAT(4, 2),
  `reservationId` INT(10) NOT NULL,
  `status`        VARCHAR(15),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`reservationId`) REFERENCES `reservation` (`id`)
);

CREATE TABLE `menuItemOrder` (
  `orderId`    INT(10) NOT NULL,
  `menuItemId` INT(5)  NOT NULL,
  `number`     INT(3)  NOT NULL,
  PRIMARY KEY (`orderId`, `menuItemId`),
  FOREIGN KEY (`orderId`) REFERENCES `foodOrder` (`id`),
  FOREIGN KEY (`menuItemId`) REFERENCES `menuItem` (`id`)
);


INSERT INTO reservation
(submissionDate, reservationDate, start, end, customerId, tableId)
VALUES
  ("2018-03-04", "2018-03-07", "1400", "1700", 10, 2);

INSERT INTO `order` (totalCost, reservationId, status) VALUES (22.50f, 6, "New");

INSERT INTO `menuItemOrder` (orderId, menuItemId, number) VALUES (1, 3, 3);
INSERT INTO `menuItemOrder` (orderId, menuItemId, number) VALUES (1, 2, 3);
INSERT INTO `menuItemOrder` (orderId, menuItemId, number) VALUES (1, 7, 1);

SELECT *
FROM `order`, `menuItemOrder`, `menuItem`
WHERE `order`.id = orderId AND `menuItem`.id = `menuItemId`;

