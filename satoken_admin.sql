/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 127.0.0.1:3306
 Source Schema         : satoken_admin

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 09/12/2022 17:43:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '0', '总公司', 0, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (2, 1, '0,1', '长春分公司', 1, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (3, 1, '0,1', '北京分公司', 2, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (4, 2, '0,1,2', '研发部门', 1, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (5, 2, '0,1,2', '市场部门', 2, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (6, 2, '0,1,2', '财务部门', 3, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (7, 2, '0,1,2', '测试部门', 4, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (8, 2, '0,1,2', '运维部门', 5, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (9, 3, '0,1,3', '市场部门', 1, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (10, 3, '0,1,3', '财务部门', 2, 'admin', '15888888888', '123456789@qq.com', '0', 'admin', '2022-11-11 17:20:14');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` int(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` int(1) NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
  `status` int(1) NULL DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `project_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, NULL, 1, 0, 'M', 0, 0, NULL, '', 'CS001', 'admin', '2022-11-28 11:46:59', '');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, NULL, 1, 0, 'M', 0, 0, NULL, '', 'CS001', 'admin', '2022-11-28 11:48:01', '');
INSERT INTO `sys_menu` VALUES (2000, '用户管理', 1, 1, 'user', 'system/user/index', NULL, 1, 0, 'C', 0, 0, NULL, '#', 'CS001', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2002, '角色管理', 1, 2, 'role', 'system/role/index', NULL, 1, 0, 'C', 0, 0, NULL, '#', 'CS001', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2004, '菜单管理', 1, 3, 'menu', 'system/menu/index', NULL, 1, 0, 'C', 0, 0, NULL, '#', 'CS001', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '部门管理', 1, 4, 'dept', 'system/dept/index', NULL, 1, 0, 'C', 0, 0, NULL, '#', 'CS001', '', NULL, '');

-- ----------------------------
-- Table structure for sys_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_project`;
CREATE TABLE `sys_project`  (
  `project_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目ID',
  `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `project_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `status` int(10) NULL DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_project
-- ----------------------------
INSERT INTO `sys_project` VALUES ('CS001', '权限系统', '权限系统6', 'http://localhost:9003', 0, NULL, 'admin', '2022-11-28 14:39:00');
INSERT INTO `sys_project` VALUES ('CS002', '测试系统', '权限系统6', 'http://localhost:9055', 1, NULL, 'admin', '2022-11-28 14:39:00');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `project_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目ID',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(10) NOT NULL COMMENT '显示顺序',
  `status` int(10) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'CS001', 'super-admin', 1, 0, 'admin', '2022-11-11 17:20:14', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '管理员', 'CS001', 'admin', 2, 0, 'admin', '2022-11-11 17:20:14', '管理员');
INSERT INTO `sys_role` VALUES (3, '普通角色', 'CS001', 'common', 3, 0, 'admin', '2022-12-07 14:12:43', '普通角色');
INSERT INTO `sys_role` VALUES (4, '普通01', 'CS001', 'common', 4, 1, 'admin', '2022-12-07 09:09:04', NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` int(20) NOT NULL COMMENT '角色ID',
  `dept_id` int(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(20) NOT NULL COMMENT '角色ID',
  `menu_id` int(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 0);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2002);
INSERT INTO `sys_role_menu` VALUES (2, 2004);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id，自增主键',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `pw` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '明文密码',
  `sex` int(255) NULL DEFAULT NULL COMMENT '性别',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注\r\n',
  `status` int(255) NULL DEFAULT NULL COMMENT '帐号状态（0正常 1停用）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, 'super-admin', 'e10adc3949ba59abbe56e057f20f883e', '123456', 1, '15946823652', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 2, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '123456', 0, '15946823652', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, '2022-12-08 10:52:00', NULL);
INSERT INTO `sys_user` VALUES (3, 1, '桓一', 'e10adc3949ba59abbe56e057f20f883e', '123456', 0, '15948370464', NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (5, NULL, '测试01', 'f8a8d7997e870968f92748f3cc41cf90', '1232456', 0, NULL, NULL, 0, '2022-12-09 09:31:07', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(20) NOT NULL COMMENT '用户ID',
  `role_id` int(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (2, 3);
INSERT INTO `sys_user_role` VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;
