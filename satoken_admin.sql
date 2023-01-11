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

 Date: 11/01/2023 14:30:25
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
  `status` int(1) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '0', '总公司', 0, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (2, 1, '0,1', '长春分公司', 1, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (3, 1, '0,1', '北京分公司', 2, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (4, 2, '0,1,2', '研发部门', 1, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (5, 2, '0,1,2', '市场部门', 2, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (6, 2, '0,1,2', '财务部门', 3, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (7, 2, '0,1,2', '测试部门', 4, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (8, 2, '0,1,2', '运维部门', 5, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (9, 3, '0,1,3', '市场部门', 1, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (10, 3, '0,1,3', '财务部门', 2, 'admin', '15888888888', '123456789@qq.com', 0, 'admin', '2022-11-11 17:20:14');
INSERT INTO `sys_dept` VALUES (12, 1, '0,1,1', '新疆分公司', 2, 'super-admin', '15968523695', '', 0, 'super-admin', '2023-01-11 05:27:00');
INSERT INTO `sys_dept` VALUES (18, 12, '0,1,1,12', '研发中心', 1, NULL, NULL, NULL, 0, 'super-admin', '2023-01-11 05:48:00');

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
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `project_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2038 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, NULL, 1, 0, 'M', 0, 0, NULL, 'setting', 'CS001', 'admin', '2022-11-28 11:46:00', '');
INSERT INTO `sys_menu` VALUES (2000, '用户管理', 1, 1, 'user', 'system/user/index', NULL, 1, 0, 'C', 0, 0, 'system:user:list', 'user', 'CS001', 'admin', '2022-11-28 11:48:00', '');
INSERT INTO `sys_menu` VALUES (2002, '角色管理', 1, 2, 'role', 'system/role/index', NULL, 1, 0, 'C', 0, 0, 'system:role:list', 'role', 'CS001', 'admin', '2022-11-28 11:48:00', '');
INSERT INTO `sys_menu` VALUES (2004, '菜单管理', 1, 3, 'menu', 'system/menu/index', NULL, 1, 0, 'C', 0, 0, 'system:menu:list', 'menu', 'CS001', 'admin', '2022-11-28 11:48:00', '');
INSERT INTO `sys_menu` VALUES (2005, '部门管理', 1, 4, 'dept', 'system/dept/index', NULL, 1, 0, 'C', 0, 0, 'system:dept:list', 'dept', 'CS001', 'admin', '2022-11-28 11:48:00', '');
INSERT INTO `sys_menu` VALUES (2010, '项目管理', 1, 0, 'project', 'system/project/index', NULL, 1, 0, 'C', 0, 0, 'system:project:list', 'project', 'CS001', 'admin', '2022-12-27 03:15:00', '');
INSERT INTO `sys_menu` VALUES (2011, '项目查询', 2010, 1, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:project:query', NULL, 'CS001', 'admin', '2022-12-27 07:01:17', '');
INSERT INTO `sys_menu` VALUES (2012, '项目新增', 2010, 2, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:project:add', NULL, 'CS001', 'admin', '2022-12-27 07:02:58', '');
INSERT INTO `sys_menu` VALUES (2013, '项目修改', 2010, 3, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:project:edit', NULL, 'CS001', 'admin', '2022-12-27 07:04:17', '');
INSERT INTO `sys_menu` VALUES (2014, '项目删除', 2010, 4, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:project:delete', NULL, 'CS001', 'admin', '2022-12-27 07:06:12', '');
INSERT INTO `sys_menu` VALUES (2015, '用户查询', 2000, 1, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:query', NULL, 'CS001', 'admin', '2022-12-27 07:08:43', '');
INSERT INTO `sys_menu` VALUES (2016, '用户新增', 2000, 2, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:add', NULL, 'CS001', 'admin', '2022-12-27 07:09:17', '');
INSERT INTO `sys_menu` VALUES (2017, '用户修改', 2000, 3, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:edit', NULL, 'CS001', 'admin', '2022-12-27 07:09:33', '');
INSERT INTO `sys_menu` VALUES (2018, '用户删除', 2000, 4, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:delete', NULL, 'CS001', 'admin', '2022-12-27 07:10:01', '');
INSERT INTO `sys_menu` VALUES (2019, '角色查询', 2002, 1, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:role:query', NULL, 'CS001', 'admin', '2022-12-27 07:10:36', '');
INSERT INTO `sys_menu` VALUES (2020, '角色新增', 2002, 2, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:role:add', NULL, 'CS001', 'admin', '2022-12-27 07:11:18', '');
INSERT INTO `sys_menu` VALUES (2021, '角色修改', 2002, 3, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:role:edit', NULL, 'CS001', 'admin', '2022-12-27 07:11:47', '');
INSERT INTO `sys_menu` VALUES (2025, '角色删除', 2002, 4, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:role:delete', NULL, 'CS001', 'admin', '2022-12-27 07:29:01', '');
INSERT INTO `sys_menu` VALUES (2026, '用户导出', 2000, 5, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:export', NULL, 'CS001', 'admin', '2022-12-28 09:10:27', '');
INSERT INTO `sys_menu` VALUES (2027, '用户导入', 2000, 6, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:import', NULL, 'CS001', 'admin', '2022-12-28 09:11:00', '');
INSERT INTO `sys_menu` VALUES (2028, '重置密码', 2000, 7, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:resetPwd', NULL, 'CS001', 'admin', '2022-12-28 09:13:08', '');
INSERT INTO `sys_menu` VALUES (2029, '分配角色', 2000, 8, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:user:authRole', NULL, 'CS001', 'admin', '2022-12-28 09:15:20', '');
INSERT INTO `sys_menu` VALUES (2030, '数据权限', 2002, 5, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:role:dataScope', NULL, 'CS001', 'admin', '2022-12-28 09:25:39', '');
INSERT INTO `sys_menu` VALUES (2031, '分配用户', 2002, 6, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:role:authUser', NULL, 'CS001', 'admin', '2022-12-28 09:26:28', '');
INSERT INTO `sys_menu` VALUES (2032, '菜单查询', 2004, 1, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:menu:query', NULL, 'CS001', 'admin', '2022-12-28 09:29:52', '');
INSERT INTO `sys_menu` VALUES (2033, '菜单新增', 2004, 2, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:menu:add', NULL, 'CS001', 'admin', '2022-12-28 09:30:07', '');
INSERT INTO `sys_menu` VALUES (2034, '菜单修改', 2004, 3, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:menu:edit', NULL, 'CS001', 'admin', '2022-12-28 09:30:27', '');
INSERT INTO `sys_menu` VALUES (2035, '菜单删除', 2004, 4, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:menu:delete', NULL, 'CS001', 'admin', '2022-12-28 09:30:51', '');
INSERT INTO `sys_menu` VALUES (2036, '测试', 0, 1, 'test', NULL, NULL, 1, 0, 'M', 0, 0, NULL, 'test', 'CS002', 'super-admin', '2022-12-29 02:03:37', '');
INSERT INTO `sys_menu` VALUES (2038, '部门查询', 2005, 1, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:dept:query', NULL, 'CS001', 'super-admin', '2023-01-11 06:13:00', '');
INSERT INTO `sys_menu` VALUES (2039, '部门新增', 2005, 2, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:dept:add', NULL, 'CS001', 'super-admin', '2023-01-11 06:14:39', '');
INSERT INTO `sys_menu` VALUES (2040, '部门修改', 2005, 3, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:dept:edit', NULL, 'CS001', 'super-admin', '2023-01-11 06:15:09', '');
INSERT INTO `sys_menu` VALUES (2041, '部门删除', 2005, 4, '', NULL, NULL, 1, 0, 'F', 0, 0, 'system:dept:delete', NULL, 'CS001', 'super-admin', '2023-01-11 06:15:31', '');

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
INSERT INTO `sys_project` VALUES ('CS002', '测试系统', '权限系统99', 'http://localhost:9055', 1, NULL, 'admin', '2022-11-28 14:39:00');

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'CS001', 'super-admin', 1, 0, 'admin', '2022-11-11 17:20:14', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '管理员', 'CS001', 'admin', 2, 0, 'admin', '2022-11-11 17:20:14', '管理员');
INSERT INTO `sys_role` VALUES (3, '普通角色', 'CS001', 'common', 3, 0, 'admin', '2022-12-07 14:12:00', '普通');
INSERT INTO `sys_role` VALUES (5, '管理员', 'CS002', 'admin', 0, 0, 'admin', '2022-12-12 05:13:13', NULL);
INSERT INTO `sys_role` VALUES (6, '测试管理员', 'CS002', 'admin', 1, 0, 'super-admin', '2022-12-29 01:59:10', NULL);

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
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2002);
INSERT INTO `sys_role_menu` VALUES (2, 2004);
INSERT INTO `sys_role_menu` VALUES (2, 2005);
INSERT INTO `sys_role_menu` VALUES (2, 2010);
INSERT INTO `sys_role_menu` VALUES (2, 2011);
INSERT INTO `sys_role_menu` VALUES (2, 2012);
INSERT INTO `sys_role_menu` VALUES (2, 2015);
INSERT INTO `sys_role_menu` VALUES (2, 2016);
INSERT INTO `sys_role_menu` VALUES (2, 2019);
INSERT INTO `sys_role_menu` VALUES (2, 2020);
INSERT INTO `sys_role_menu` VALUES (2, 2028);
INSERT INTO `sys_role_menu` VALUES (2, 2029);
INSERT INTO `sys_role_menu` VALUES (2, 2030);
INSERT INTO `sys_role_menu` VALUES (2, 2031);
INSERT INTO `sys_role_menu` VALUES (2, 2032);
INSERT INTO `sys_role_menu` VALUES (2, 2033);
INSERT INTO `sys_role_menu` VALUES (2, 2038);
INSERT INTO `sys_role_menu` VALUES (2, 2039);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2000);
INSERT INTO `sys_role_menu` VALUES (5, 2036);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id，自增主键',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `pw` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '明文密码',
  `sex` int(255) NULL DEFAULT NULL COMMENT '性别',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注\r\n',
  `status` int(255) NULL DEFAULT NULL COMMENT '帐号状态（0正常 1停用）',
  `user_sort` int(10) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, 'super-admin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '123456', 1, '15946823652', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 2, 'admin', '管理员', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 0, '15946823652', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, 0, '2022-12-08 10:52:00', NULL);
INSERT INTO `sys_user` VALUES (3, 2, 'huanyi', '桓一', 'e10adc3949ba59abbe56e057f20f883e', '123456', 0, '15948370464', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_user` VALUES (5, 4, 'test', '测试', 'e10adc3949ba59abbe56e057f20f883e', '123456', 0, '15554896265', NULL, 0, 3, '2022-12-09 09:31:00', NULL);
INSERT INTO `sys_user` VALUES (6, 1, 'zero', '零', 'e10adc3949ba59abbe56e057f20f883e', '123456', 0, NULL, '2', 0, 5, '2022-12-29 03:43:00', NULL);

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
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (5, 3);
INSERT INTO `sys_user_role` VALUES (5, 5);

SET FOREIGN_KEY_CHECKS = 1;
