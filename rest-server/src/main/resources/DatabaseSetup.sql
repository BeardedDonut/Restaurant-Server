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
  `numberOfSeats` INT,
  CHECK (`numberOfSeats` > 1),
  PRIMARY KEY (`id`)
);