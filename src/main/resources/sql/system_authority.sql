/*
Navicat MySQL Data Transfer

Source Server         : 192.168.28.1
Source Server Version : 50720
Source Host           : 192.168.28.1:3306
Source Database       : system_authority

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-09 14:48:38
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理主键，自增长',
  `authority_code` varchar(20) NOT NULL COMMENT '权限编号',
  `authority_name` varchar(20) NOT NULL COMMENT '权限名称',
  `authority_desc` varchar(200) DEFAULT NULL COMMENT '权限描述',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_authority_code` (`authority_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='权限';


-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理主键，自增长',
  `role_code` varchar(20) NOT NULL COMMENT '角色编号',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';


-- ----------------------------
-- Table structure for `role_authoritys`
-- ----------------------------
DROP TABLE IF EXISTS `role_authoritys`;
CREATE TABLE `role_authoritys` (
  `role_code` varchar(20) NOT NULL COMMENT '角色编号',
  `authority_code` varchar(20) NOT NULL COMMENT '权限编号',
  UNIQUE KEY `uk_role_authority_code` (`role_code`,`authority_code`),
  KEY `fk_authority_code` (`authority_code`),
  CONSTRAINT `fk_authority_code` FOREIGN KEY (`authority_code`) REFERENCES `authority` (`authority_code`),
  CONSTRAINT `fk_role_code_1` FOREIGN KEY (`role_code`) REFERENCES `role` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理主键，自增长',
  `user_code` varchar(20) NOT NULL COMMENT '用户编号（通常为身份证号码）',
  `user_name` varchar(20) NOT NULL COMMENT '用户名称',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `user_nick_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `user_desc` varchar(20) DEFAULT NULL COMMENT '用户描述',
  `id_number` varchar(20) NOT NULL COMMENT '证件号码（通常为身份证号码）',
  `tele_phone` varchar(20) NOT NULL COMMENT '电话号码',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '用户状态',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本',
  `supper` char(1) NOT NULL DEFAULT '0' COMMENT '超级管理员 （0：否，1：是）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_code` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', '超级管理员账号', '411025199010240531', '15237459086', '1', '1', '1');

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_code` varchar(20) NOT NULL COMMENT '用户编号',
  `role_code` varchar(20) NOT NULL COMMENT '角色编号',
  UNIQUE KEY `uk_user_role_code` (`user_code`,`role_code`),
  KEY `fk_role_code` (`role_code`),
  CONSTRAINT `fk_role_code` FOREIGN KEY (`role_code`) REFERENCES `role` (`role_code`),
  CONSTRAINT `fk_user_code` FOREIGN KEY (`user_code`) REFERENCES `user` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

