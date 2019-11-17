# 成都中医药大学温江门诊部
CREATE DATABASE IF NOT EXISTS `register`;
USE register;
/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : register

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 16/02/2019 20:03:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_info
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info` (
  `admin_id`    VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci NOT NULL
  COMMENT '管理员ID',
  `admin_name`  VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci NOT NULL
  COMMENT '管理员姓名',
  `admin_sex`   TINYINT(1)   NOT NULL
  COMMENT '管理员性别 0男、1女',
  `admin_phone` VARCHAR(16) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci NOT NULL
  COMMENT '管理员手机号',
  `admin_type`  TINYINT(1)   NOT NULL
  COMMENT '管理员类型 0系统管理员、1医院管理员',
  `username`    VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci NOT NULL
  COMMENT '用户名',
  `password`    VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci NOT NULL
  COMMENT '密码',
  `create_time` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`admin_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '用户信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for clinic_info
-- ----------------------------
DROP TABLE IF EXISTS `clinic_info`;
CREATE TABLE `clinic_info` (
  `clinic_id`       TINYINT(3)   NOT NULL
  COMMENT '医院ID',
  `clinic_name`     VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '医院名称',
  `clinic_synopsis` TEXT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '医院简介',
  `floor_synopsis`  TEXT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '楼层简介',
  `clinic_position` VARCHAR(256) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '医院位置',
  `create_time`     TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`     TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`clinic_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '医院表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for department_info
-- ----------------------------
DROP TABLE IF EXISTS `department_info`;
CREATE TABLE `department_info` (
  `department_id`   TINYINT(3)    NOT NULL
  COMMENT '科室ID',
  `department_name` VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci      NOT NULL
  COMMENT '科室名称',
  `principal`       VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci      NOT NULL
  COMMENT '科室负责人',
  `principal_phone` DECIMAL(8, 2) NOT NULL
  COMMENT '负责人电话',
  `create_time`     TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`     TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`department_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '科室表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for doctor_info
-- ----------------------------
DROP TABLE IF EXISTS `doctor_info`;
CREATE TABLE `doctor_info` (
  `doctor_id`     VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '医生ID',
  `doctor_photo`  VARCHAR(255) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL
  COMMENT '医生照片',
  `doctor_name`   VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '医生姓名',
  `doctor_sex`    TINYINT(1)   NULL     DEFAULT NULL
  COMMENT '医生性别 0男、1女',
  `doctor_card`   VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '医生身份证号',
  `department_id` TINYINT(3)   NOT NULL
  COMMENT '科室',
  `doctor_phone`  VARCHAR(16) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL
  COMMENT '医生手机号',
  `doctor_mail`   VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL
  COMMENT '医生邮箱',
  `doctor_post`   VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL
  COMMENT '职务/职称',
  `doctor_adept`  VARCHAR(256) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL
  COMMENT '擅长',
  `doctor_detail` TEXT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NULL
  COMMENT '介绍',
  `username`      VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '用户名',
  `password`      VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '密码',
  `create_time`   TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`   TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`doctor_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '医生表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id`      VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci    NOT NULL
  COMMENT '挂号ID',
  `schedule_time` VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci    NOT NULL
  COMMENT '问诊时间',
  `order_money`   DECIMAL(8, 2) NOT NULL
  COMMENT '金额',
  `order_status`  TINYINT(1)    NOT NULL
  COMMENT '状态 0待缴费、1缴费成功',
  `pay_time`      TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '支付时间',
  `patient_id`    VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci    NOT NULL
  COMMENT '患者ID',
  `doctor_id`     VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci    NOT NULL
  COMMENT '医生ID',
  `openid`        VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci    NOT NULL
  COMMENT '绑定微信号openid',
  `create_time`   TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`   TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`order_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '挂号记录表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for patient_info
-- ----------------------------
DROP TABLE IF EXISTS `patient_info`;
CREATE TABLE `patient_info` (
  `patient_id`    VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '患者ID',
  `patient_name`  VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '患者姓名',
  `patient_card`  VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '患者身份证号',
  `patient_phone` VARCHAR(16) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '患者手机号',
  `patient_mail`  VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '患者邮箱',
  `openid`        VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci   NOT NULL
  COMMENT '绑定微信号openid',
  `violate_count` TINYINT(1)   NULL     DEFAULT 3
  COMMENT '失约3次冻结',
  `status`        TINYINT(3)   NOT NULL DEFAULT 0,
  `create_time`   TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`   TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`patient_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '患者信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for schedule_info
-- ----------------------------
DROP TABLE IF EXISTS `schedule_info`;
CREATE TABLE `schedule_info` (
  `schedule_id`    VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '排班ID',
  `schedule_time`  VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '排班时间',
  `doctor_id`      VARCHAR(32) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '医生ID',
  `schedule_money` DECIMAL(8, 2) NOT NULL
  COMMENT '金额',
  `source_number`  VARCHAR(64) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci     NOT NULL
  COMMENT '号源',
  `create_time`    TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`    TIMESTAMP(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0)
  COMMENT '更新时间',
  PRIMARY KEY (`schedule_id`) USING BTREE
)
  ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '排班表'
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
