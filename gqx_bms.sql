/*
 Navicat Premium Data Transfer

 Source Server         : queenie_mysql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : gqx_bms

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 11/01/2019 08:01:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `book_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pno` int(11) UNSIGNED NULL DEFAULT NULL,
  `book_name` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `book_author` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `book_total_inventory` int(11) UNSIGNED NOT NULL,
  `book_now_inventory` int(11) UNSIGNED NOT NULL,
  `book_location` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `create_time` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE,
  INDEX `FK_book_published`(`pno`) USING BTREE,
  CONSTRAINT `FK_book_published` FOREIGN KEY (`pno`) REFERENCES `book_publish` (`pno`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = 'ͼ��' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES (1, 1, 'apple', 'gqx', 3, 3, 'D2', '2019-01-11 04:30:06');
INSERT INTO `book_info` VALUES (2, 1, 'boy', 'gqx', 2, 2, 'D1', '2019-01-08 06:20:21');
INSERT INTO `book_info` VALUES (3, 1, 'cat', 'gqx', 4, 4, '', '2019-01-11 05:08:40');
INSERT INTO `book_info` VALUES (4, 1, 'dog', 'gqx', 4, 4, '', '2019-01-11 05:23:06');

-- ----------------------------
-- Table structure for book_manage
-- ----------------------------
DROP TABLE IF EXISTS `book_manage`;
CREATE TABLE `book_manage`  (
  `book_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`book_id`, `uname`) USING BTREE,
  INDEX `FK_manage_user`(`uname`) USING BTREE,
  CONSTRAINT `FK_manage_book` FOREIGN KEY (`book_id`) REFERENCES `book_info` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_manage_user` FOREIGN KEY (`uname`) REFERENCES `user_info` (`uname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '����' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for book_publish
-- ----------------------------
DROP TABLE IF EXISTS `book_publish`;
CREATE TABLE `book_publish`  (
  `pno` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pname` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `plocation` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `pphone` varchar(8) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '������' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_publish
-- ----------------------------
INSERT INTO `book_publish` VALUES (1, 'B', '3', '33333333');

-- ----------------------------
-- Table structure for borrow_detail
-- ----------------------------
DROP TABLE IF EXISTS `borrow_detail`;
CREATE TABLE `borrow_detail`  (
  `borrow_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` int(11) UNSIGNED NOT NULL,
  `book_name` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `student_id` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `borrow_time` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `return_time` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `return_condition` bit(1) NULL DEFAULT NULL,
  `return_ifovertime` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`borrow_id`) USING BTREE,
  INDEX `FK_book_borrowed`(`book_id`) USING BTREE,
  CONSTRAINT `FK_book_borrowed` FOREIGN KEY (`book_id`) REFERENCES `book_info` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '�������' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow_detail
-- ----------------------------
INSERT INTO `borrow_detail` VALUES (3, 2, 'boy', '3215009633', '2019-01-08 10:52:52', '2019-01-08 10:53:10', b'1', b'0');
INSERT INTO `borrow_detail` VALUES (4, 1, 'apple', '3215009633', '2019-01-11 04:37:12', '2019-01-11 04:45:40', b'1', b'0');
INSERT INTO `borrow_detail` VALUES (5, 2, 'boy', '3215009633', '2019-01-11 05:11:02', '2019-01-11 05:15:18', b'1', b'1');
INSERT INTO `borrow_detail` VALUES (6, 2, 'boy', '3215009633', '2019-01-11 05:11:12', '2019-01-11 05:15:22', b'1', b'1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `uname` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `pwd` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uname`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '������' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('3215009633', '');

SET FOREIGN_KEY_CHECKS = 1;
