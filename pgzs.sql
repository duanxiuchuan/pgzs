/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : localhost:3306
 Source Schema         : pgzs

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 10/04/2020 16:24:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for busi_sn
-- ----------------------------
DROP TABLE IF EXISTS `busi_sn`;
CREATE TABLE `busi_sn`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `modify_date` datetime NOT NULL COMMENT '修改日期',
  `version` bigint(20) NOT NULL COMMENT '版本号',
  `last_value` bigint(20) NOT NULL COMMENT '末值',
  `type` int(11) NOT NULL COMMENT '类型',
  `creator` bigint(20) NOT NULL,
  `modifier` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_sn_type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '编号' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of busi_sn
-- ----------------------------
INSERT INTO `busi_sn` VALUES (1, '2015-10-19 00:57:35', '2015-10-19 00:57:35', 0, 1104, 0, 1, 1);
INSERT INTO `busi_sn` VALUES (2, '2015-10-19 00:57:35', '2015-10-19 00:57:35', 0, 1000, 1, 1, 1);
INSERT INTO `busi_sn` VALUES (3, '2015-10-19 00:57:35', '2015-10-19 00:57:35', 0, 1000, 2, 1, 1);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `cust_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `layout` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户型',
  `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地区',
  `house_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '楼盘名称',
  `area` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '面积',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `designer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`cust_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('5533efb38c0448c8b87668f4336df7f1', '王二', '13344556688', '456@qq.com', '2', '4', '1', '58e9a7337d164c298d1d9847942017a6', '288mm', '1', '2020-04-08 17:54:54', 'c129c1277fe3437d9cd08f4e18df4d8b');
INSERT INTO `customer` VALUES ('606afd445bbc415e98e778b2e563ef16', '张三', '12345678900', '123@qq.com', '1', '1', '1', '1', '122cm', '1', '2020-04-02 16:31:11', NULL);
INSERT INTO `customer` VALUES ('75fa414c6d0a4365920a7b938e600a08', '323', '12344556677', '', '', '', '', '', '', '0', '2020-04-10 15:41:15', '');
INSERT INTO `customer` VALUES ('8147e21be17741ef923d559cd927a739', '12312', '12333334444', '', NULL, '', '', '', '', '0', '2020-04-03 15:00:59', NULL);
INSERT INTO `customer` VALUES ('8af3e29deb674644961ef9e22834ba13', '王五', '13355558888', NULL, NULL, NULL, NULL, NULL, NULL, '0', '2020-04-10 11:30:23', NULL);
INSERT INTO `customer` VALUES ('fb6489b1de48477caf4da1432bfed6c3', '1321', '12345678909', '', NULL, '', '', '', '', '0', '2020-04-03 10:56:51', NULL);

-- ----------------------------
-- Table structure for designer
-- ----------------------------
DROP TABLE IF EXISTS `designer`;
CREATE TABLE `designer`  (
  `designer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `years` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `style` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `idea` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设计理念',
  `education` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `works` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '代表作品',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `appointment` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '预约数',
  `clicks` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '点击数',
  `cases` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`designer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of designer
-- ----------------------------
INSERT INTO `designer` VALUES ('1977fcf13c294205af41e4d90fec1ae2', '1', '大萨达', '大神', '1,2', '打', '大神', '大神', '0', '', NULL, NULL, NULL);
INSERT INTO `designer` VALUES ('2a619cf56ceb4eb1b9dc296bfad29107', '2', '托尼张', '20年', '2,3,4', '撒', '杨美', '17511ddb2d8a4e0eaa0611df18c3d3a7', '1', 'http://localhost/files/image/36654d5cf20f42559233aec69f5d2a15.jpg', '2000', '8888', '1111');
INSERT INTO `designer` VALUES ('40ec205e5d9c4ec1a8c8d7d8594fb7cf', '2', '托尼美', '10年', '1,2', '什么都会', '国美', '676218f9bc2a473995c077dd5b459212', '1', 'http://localhost/files/image/6a499aba754a4512bfa00ce6bc8b1584.jpg', '888', '888', '888');
INSERT INTO `designer` VALUES ('72b1735e731146a2911ae00a4a735568', '2', '托尼王', '20年', '3', '天马行空', '想得美', '无', '0', 'http://localhost/files/image/b94878e9074348b68f33742985880c72.jpg', NULL, NULL, NULL);
INSERT INTO `designer` VALUES ('9acce53f7e1c40d8ac550de6f961335d', '1', '打', '大神', '2', '大神', '打', '打', '0', '', NULL, NULL, NULL);
INSERT INTO `designer` VALUES ('c129c1277fe3437d9cd08f4e18df4d8b', '1', '托尼段', '15年', '什么都会', '想啥来啥', '国美', '什么都有', '0', 'http://localhost/files/image/23c4bfd2f96848c39f4fb9e2258cf6b6.jpg', NULL, NULL, NULL);
INSERT INTO `designer` VALUES ('ef125e9c2fd04ede9dadbb5631a8c1c3', '1', '托尼皮', '10年', '现代，简约，欧美风', '做最好看的房子给最好的你', '川美', '最美的家', '0', 'http://localhost/files/image/d1dbe6faf510493eb9fc1897906fa7f9.jpg', NULL, NULL, NULL);
INSERT INTO `designer` VALUES ('fdb72d4f1f2a4a8da3cf10b141796fea', '1', '托尼赵', '10年', '2,3', '打', '川美', 'c19953e0fd754969bc4f3928536f7846', '1', 'http://localhost/files/image/5133ac0fd08540a8a123f8e1f8020324.jpg', '1111', '999', '333');

-- ----------------------------
-- Table structure for exquisite_case
-- ----------------------------
DROP TABLE IF EXISTS `exquisite_case`;
CREATE TABLE `exquisite_case`  (
  `case_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `area` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '面积',
  `style` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '风格',
  `layout` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户型',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
  `detail` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详情',
  `areas_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '小区id',
  `designer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`case_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of exquisite_case
-- ----------------------------
INSERT INTO `exquisite_case` VALUES ('0056251139464d4b97936739768d37fa', '打', '大神', '大神', '大飒飒', '<p>大神</p><p><img src=\"/files/image/a9d20b333d994a8fa3da28232987ef84.jpg\" alt=\"0-0-0-0.jpg\"><br></p>', '打', '大神', '0');
INSERT INTO `exquisite_case` VALUES ('17511ddb2d8a4e0eaa0611df18c3d3a7', '288mm', '1', '5', '完美的家', '<p>实打实</p><p><img src=\"/files/image/64076edb2c21411ea7efc97ed158b788.png\" alt=\"1559201382735381.png\"><br></p>', '3c347d73b4ed42928a2f4f498fcb847e', '2a619cf56ceb4eb1b9dc296bfad29107', '1');
INSERT INTO `exquisite_case` VALUES ('6056e3c6757f426087268fd49119d5a1', '288mm', '现代', '别墅', '阳光花园大别墅288mm', NULL, '阳光花园', '托尼皮', '0');
INSERT INTO `exquisite_case` VALUES ('676218f9bc2a473995c077dd5b459212', '288', '3', '4', '最好的家', '<p>阿打算</p><p><img src=\"/files/image/c28dc79c12aa4d93948c9adcfb872841.png\" alt=\"1559201382735381.png\"><br></p>', '3c347d73b4ed42928a2f4f498fcb847e', '2a619cf56ceb4eb1b9dc296bfad29107', '1');
INSERT INTO `exquisite_case` VALUES ('979968490d9e4145aa794cc1fbf62a3c', '188', '中国风', '别墅', '最好的家', '啦啦啦啦啦', NULL, NULL, '0');
INSERT INTO `exquisite_case` VALUES ('9bff69e890104b8496dc5c92c60e2caf', '312321', '321312', '31231', '322432', NULL, '14231', '3123', '0');
INSERT INTO `exquisite_case` VALUES ('aacc3ba4cb0f4a83975954439c956e4f', '22', '222', '111', '大萨达', '<p>333</p><p><img src=\"/files/image/977bff0503dd426fad0af408be179c48.jpg\" alt=\"0-0-0-0.jpg\"><br></p>', '都', '333', '0');
INSERT INTO `exquisite_case` VALUES ('bcca98d8408c4bf384408c4a18281e9c', '414', '41412', '411', '2额饿', '<p>41241</p><p><img src=\"/files/image/4b3d556815034bbfa3a5dacb419e8a95.jpg\" alt=\"0-0-0-0.jpg\"><br></p><p>大大大</p><p><img src=\"/files/image/884be04f39bb4d469541c90bf48e2d90.jpg\" alt=\"0-0-0-0.jpg\"><br></p>', '段', '段', '0');
INSERT INTO `exquisite_case` VALUES ('c19953e0fd754969bc4f3928536f7846', '140', '1', '3', '重庆融景城现代简约装修140㎡', '<p><span>装修风格属于现代简约但是很时尚。</span></p><p><span><img src=\"/files/image/c2ca0121260448b093e9b87d98ddd597.png\" alt=\"1558839288880900.png\"><br></span></p>', 'e7f2210e8bbf4eba8ce335a56545d19d', '2a619cf56ceb4eb1b9dc296bfad29107', '1');
INSERT INTO `exquisite_case` VALUES ('c6fa29420dc04acb98095501d426702f', '阿萨德打', '大神 打的', ' 大神 ', '大萨达', '<p>大神</p><p><img src=\"/files/image/2ecaa5eebaa24e9ead2b5b5fe8ca81bd.jpg\" alt=\"0-0-0-0.jpg\"><br></p>', '大所说的asd', '大神的as', '0');

-- ----------------------------
-- Table structure for heat_areas
-- ----------------------------
DROP TABLE IF EXISTS `heat_areas`;
CREATE TABLE `heat_areas`  (
  `areas_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `detail` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`areas_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of heat_areas
-- ----------------------------
INSERT INTO `heat_areas` VALUES ('3c347d73b4ed42928a2f4f498fcb847e', '龙湖大城小院388mm，嗯哼', '环境优雅，宜家，舒适，交通便利', '2', '1', '<p>来来看看</p><p><img src=\"/files/image/14b28d90d6884b62a9d42f7a8b89cc15.png\" alt=\"1559201382735381.png\"><br></p>', '大城小院');
INSERT INTO `heat_areas` VALUES ('58e9a7337d164c298d1d9847942017a6', '保利香雪122mm', '啊，环境好，密度小，宜家新居', '重庆江北', '0', '<p>来一来看一看</p><p><img src=\"/files/image/8918ff57caa64aab8e8e53668db370c6.jpg\" alt=\"0-0-0-0.jpg\"><br></p>', '保利香雪');
INSERT INTO `heat_areas` VALUES ('e7f2210e8bbf4eba8ce335a56545d19d', '融景城 188㎡', '交通便利', '1', '1', '<p>大萨达</p><p><img src=\"/files/image/2092dab8ebe241e59a30f23006cb63dd.png\" alt=\"1558686448240411.png\"><br></p>', '融景城');

-- ----------------------------
-- Table structure for job_task
-- ----------------------------
DROP TABLE IF EXISTS `job_task`;
CREATE TABLE `job_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cron` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `date` datetime NOT NULL,
  `type` int(1) DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `startime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `runtime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `info` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `prefix` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_valid` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for style
-- ----------------------------
DROP TABLE IF EXISTS `style`;
CREATE TABLE `style`  (
  `style_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `style` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `space` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `fuca` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `detail` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`style_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of style
-- ----------------------------
INSERT INTO `style` VALUES ('13a66f446fca4254b89fb29b288b4d4d', '现代', '客厅', '吊顶', '<p>爽的一批</p><p><img src=\"/files/image/177c20b09fee47e9b9c2678235c3ffcc.jpg\" alt=\"0-0-0-0.jpg\"><br></p>', '0', '现代风格30米客厅', '舒适，宜家，暖色调');
INSERT INTO `style` VALUES ('463e58fe4cb34cb69c2530a8c300ad9c', '1,4', '1', '1', '<p><a target=\"_blank\" href=\"http://www.cqyoule.com/index.php?s=fege&amp;c=show&amp;id=10\">年轻人喜欢的现代简约</a></p><p><img src=\"/files/image/60d19ae6ad5140d59324c6aeeaff1ec0.jpg\" alt=\"0-0-0-0 (1).jpg\"><br></p>', '1', '年轻人喜欢的现代简约', '简单而不简单');
INSERT INTO `style` VALUES ('9badfd25df344c7d81b125a79dce195b', '2,3', '1', '2', '<p>22撒</p><p><img src=\"/files/image/3666245bb16a42829587820a644cc594.png\" alt=\"1559201382735381.png\"><br></p>', '1', '现代田园背景墙', '美不胜收');

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `modify_date` datetime NOT NULL,
  `modifier` bigint(20) NOT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'1',
  `version` bigint(20) NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `is_enable` int(1) DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` int(1) DEFAULT NULL,
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dept_id` bigint(20) NOT NULL,
  `is_built_in` int(1) NOT NULL DEFAULT 1 COMMENT '是否内置 0 内置 1 非内置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, '2018-08-16 16:12:20', 1, '2018-09-28 16:38:23', 1, b'1', 51, 'admin', 'a66abb5684c45962d887564f08346e8d', '超级管理员', '2018-08-16', 0, '17378301507', 1, '', '17378301507@163.coms', '/6fde9327ad43425592a98febcc029bf0.png', 1, 1);
INSERT INTO `sys_admin` VALUES (2, '2020-04-02 11:10:23', 1, '2020-04-02 11:10:23', 1, b'1', 0, 'sc001', 'bc1560765d780c15db5150ec36d1aa48', '李四', NULL, 0, '12345678901', NULL, NULL, '123@123.com', NULL, 2, 0);

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`  (
  `admin_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admin_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role` VALUES (1, 10);
INSERT INTO `sys_admin_role` VALUES (2, 18);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `modify_date` datetime NOT NULL,
  `modifier` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '2018-08-29 14:15:51', 1, '2018-08-29 14:15:55', 1, 0, '品冠装饰', 0);
INSERT INTO `sys_dept` VALUES (2, '2018-11-30 13:42:06', 1, '2018-11-30 13:42:06', 1, 0, '市场部', 1);
INSERT INTO `sys_dept` VALUES (3, '2019-01-08 17:42:15', 1, '2019-01-09 17:42:20', 1, 0, '策划部', 1);
INSERT INTO `sys_dept` VALUES (5, '2019-01-15 17:51:45', 1, '2019-01-15 17:51:45', 1, 0, '财务部', 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型名称  如：用户类型',
  `del_flag` int(1) DEFAULT NULL COMMENT '删除标志',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典标识',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '男', '管理员性别', 0, '男', '0', 'admin_gender_type');
INSERT INTO `sys_dict` VALUES (2, '女', '管理员性别', 0, '女', '1', 'admin_gender_type');
INSERT INTO `sys_dict` VALUES (9, '江北区', '地区', 1, NULL, '1', 'admin_areas_type');
INSERT INTO `sys_dict` VALUES (10, '渝北区', '地区', 1, NULL, '2', 'admin_areas_type');
INSERT INTO `sys_dict` VALUES (11, '渝中区', '地区', 1, NULL, '3', 'admin_areas_type');
INSERT INTO `sys_dict` VALUES (12, '咨询价格', '预约类型', 1, NULL, '1', 'admin_type_type');
INSERT INTO `sys_dict` VALUES (13, '预约设计', '预约类型', 1, NULL, '2', 'admin_type_type');
INSERT INTO `sys_dict` VALUES (14, '查询预算', '预约类型', 1, NULL, '3', 'admin_type_type');
INSERT INTO `sys_dict` VALUES (15, '一室一厅', '户型', 0, NULL, '1', 'admin_layout_type');
INSERT INTO `sys_dict` VALUES (16, '二室一厅', '户型', 1, NULL, '2', 'admin_layout_type');
INSERT INTO `sys_dict` VALUES (17, '三室二厅', '户型', 1, NULL, '3', 'admin_layout_type');
INSERT INTO `sys_dict` VALUES (18, '四室三厅', '户型', 1, NULL, '4', 'admin_layout_type');
INSERT INTO `sys_dict` VALUES (19, '别墅', '户型', 1, NULL, '5', 'admin_layout_type');
INSERT INTO `sys_dict` VALUES (20, '现代风', '装修风格', 1, NULL, '1', 'admin_style_type');
INSERT INTO `sys_dict` VALUES (21, '欧美风', '装修风格', 1, NULL, '2', 'admin_style_type');
INSERT INTO `sys_dict` VALUES (22, '高级设计师', '设计师类型', 1, NULL, '1', 'admin_designer_type');
INSERT INTO `sys_dict` VALUES (23, '设计总监', '设计师类型', 1, NULL, '2', 'admin_designer_type');
INSERT INTO `sys_dict` VALUES (24, '设计经理', '设计师类型', 1, NULL, '3', 'admin_designer_type');
INSERT INTO `sys_dict` VALUES (25, '现代田园', '装修风格', 1, NULL, '3', 'admin_style_type');
INSERT INTO `sys_dict` VALUES (26, '简约时尚', '装修风格', 1, NULL, '4', 'admin_style_type');
INSERT INTO `sys_dict` VALUES (27, '客厅', '风格空间', 1, NULL, '1', 'admin_space_type');
INSERT INTO `sys_dict` VALUES (28, '餐厅', '风格空间', 1, NULL, '2', 'admin_space_type');
INSERT INTO `sys_dict` VALUES (29, '电视墙', '风格功能', 1, NULL, '1', 'admin_fuca_type');
INSERT INTO `sys_dict` VALUES (30, '背景墙', '风格功能', 1, NULL, '2', 'admin_fuca_type');
INSERT INTO `sys_dict` VALUES (31, '公司动态', '百科类型', 1, NULL, '1', 'admin_wiki_type');
INSERT INTO `sys_dict` VALUES (32, '工地直播', '百科类型', 1, NULL, '2', 'admin_wiki_type');
INSERT INTO `sys_dict` VALUES (33, '装修攻略', '百科类型', 1, NULL, '3', 'admin_wiki_type');

-- ----------------------------
-- Table structure for sys_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `modify_date` datetime NOT NULL,
  `modifier` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` int(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `is_enable` int(1) NOT NULL,
  `sort` int(2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 289 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_resources
-- ----------------------------
INSERT INTO `sys_resources` VALUES (1, '2018-08-22 16:13:06', 1, '2019-01-29 16:48:39', 1, 0, '系统管理', '#', 0, 0, 1, 10);
INSERT INTO `sys_resources` VALUES (2, '2018-08-22 16:13:06', 1, '2018-10-24 09:32:09', 1, 0, '角色管理', '/manage/role/setRoleResources', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (3, '2018-08-22 16:13:06', 1, '2018-08-22 16:13:10', 1, 0, '新增角色', '/manage/role/save', 2, 2, 1, 1);
INSERT INTO `sys_resources` VALUES (4, '2018-08-22 16:13:06', 1, '2018-08-22 16:13:10', 1, 0, '删除角色', '/manage/role/delete', 2, 2, 1, 1);
INSERT INTO `sys_resources` VALUES (5, '2018-08-22 16:13:06', 1, '2018-08-22 16:13:10', 1, 0, '修改角色', '/manage/role/update', 2, 2, 1, 1);
INSERT INTO `sys_resources` VALUES (6, '2018-08-22 16:13:06', 1, '2018-10-19 14:14:19', 1, 0, '权限管理', '/manage/res/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (7, '2018-08-22 16:13:06', 1, '2018-08-22 16:13:10', 1, 0, '新增权限', '/manage/res/addcategory', 2, 6, 1, 1);
INSERT INTO `sys_resources` VALUES (8, '2018-08-22 16:13:06', 1, '2018-08-22 16:13:10', 1, 0, '删除权限', '/manage/res/delete', 2, 6, 1, 1);
INSERT INTO `sys_resources` VALUES (9, '2018-08-22 16:13:06', 1, '2018-08-22 16:13:10', 1, 0, '修改权限', '/manage/res/editPage', 2, 6, 1, 1);
INSERT INTO `sys_resources` VALUES (10, '2018-10-24 09:30:30', 1, '2018-10-24 09:30:30', 1, 0, '新增子权限', '/manage/res/addsubord', 2, 6, 1, 1);
INSERT INTO `sys_resources` VALUES (11, '2018-10-22 09:24:55', 1, '2020-04-03 15:34:07', 1, 0, '用户管理', '/manage/dept/index', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (12, '2018-10-22 09:24:55', 1, '2018-10-22 09:49:18', 1, 0, '新增部门', '/manage/dept/save', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (13, '2018-10-22 09:24:55', 1, '2018-10-22 09:49:18', 1, 0, '编辑部门', '/manage/dept/update', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (14, '2018-10-22 09:24:55', 1, '2018-10-22 09:49:18', 1, 0, '删除部门', '/manage/dept/delete', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (15, '2018-10-22 09:24:55', 1, '2020-04-03 15:34:33', 1, 0, '人员管理', '/manage/manage/list', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (16, '2018-10-22 09:24:55', 1, '2018-10-22 09:49:18', 1, 0, '新增用户', '/manage/manage/addPage', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (17, '2018-10-22 09:24:55', 1, '2018-10-22 09:49:18', 1, 0, '修改用户', '/manage/manage/editPage', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (18, '2018-10-22 09:24:55', 1, '2018-10-22 09:49:18', 1, 0, '删除用户', '/manage/manage/delete', 2, 11, 1, 1);
INSERT INTO `sys_resources` VALUES (19, '2020-04-02 14:03:13', 1, '2020-04-02 14:59:56', 1, 0, '客户管理', '/manage/cust/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (271, '2020-04-02 15:41:32', 1, '2020-04-02 15:41:32', 1, 0, '新增', '/manage/cust/add', 2, 19, 1, 1);
INSERT INTO `sys_resources` VALUES (274, '2020-04-03 14:23:54', 1, '2020-04-03 14:26:17', 1, 0, '修改', '/manage/cust/editPage', 2, 19, 1, 1);
INSERT INTO `sys_resources` VALUES (275, '2020-04-03 14:24:14', 1, '2020-04-03 14:24:14', 1, 0, '删除', '/manage/cust/delete', 2, 19, 1, 1);
INSERT INTO `sys_resources` VALUES (276, '2020-04-03 15:35:29', 1, '2020-04-03 15:35:29', 1, 0, '精品案例', '/manage/case/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (277, '2020-04-03 15:35:56', 1, '2020-04-03 15:35:56', 1, 0, '金牌设计', '/manage/design/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (278, '2020-04-03 15:36:42', 1, '2020-04-03 15:36:42', 1, 0, '流行风格', '/manage/style/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (279, '2020-04-03 15:37:28', 1, '2020-04-03 15:37:28', 1, 0, '热门小区', '/manage/heat/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (280, '2020-04-03 15:38:05', 1, '2020-04-03 15:38:05', 1, 0, '装修百科', '/manage/wiki/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (281, '2020-04-03 17:06:04', 1, '2020-04-03 17:08:30', 1, 0, '新增', '/manage/case/add', 2, 276, 1, 1);
INSERT INTO `sys_resources` VALUES (282, '2020-04-07 16:43:49', 1, '2020-04-07 16:43:49', 1, 0, '新增', '/manage/design/add', 2, 277, 1, 1);
INSERT INTO `sys_resources` VALUES (283, '2020-04-08 14:34:28', 1, '2020-04-08 14:34:28', 1, 0, '字典管理', '/manage/dict/list', 1, 1, 1, 1);
INSERT INTO `sys_resources` VALUES (285, '2020-04-08 15:12:21', 1, '2020-04-08 15:12:21', 1, 0, '新增字典', '/manage/dict/add', 2, 283, 1, 1);
INSERT INTO `sys_resources` VALUES (286, '2020-04-08 15:19:43', 1, '2020-04-08 15:19:43', 1, 0, '新增', '/manage/wiki/add', 2, 280, 1, 1);
INSERT INTO `sys_resources` VALUES (287, '2020-04-08 15:21:00', 1, '2020-04-08 15:21:00', 1, 0, '新增', '/manage/heat/add', 2, 279, 1, 1);
INSERT INTO `sys_resources` VALUES (288, '2020-04-08 15:22:38', 1, '2020-04-08 15:22:38', 1, 0, '新增', '/manage/style/add', 2, 278, 1, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `modify_date` datetime NOT NULL,
  `modifier` bigint(20) NOT NULL,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_built_in` int(1) NOT NULL DEFAULT 1 COMMENT '是否内置 0 内置 1 非内置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (10, '2018-08-22 16:12:51', 1, '2018-08-22 16:12:54', 1, 0, '超级管理员', 0);
INSERT INTO `sys_role` VALUES (17, '2020-04-02 10:14:16', 1, '2020-04-08 14:56:25', 1, 0, '市场部客户经理', 1);
INSERT INTO `sys_role` VALUES (18, '2020-04-02 10:14:30', 1, '2020-04-08 14:56:33', 1, 0, '市场部部门经理', 1);

-- ----------------------------
-- Table structure for sys_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resources`;
CREATE TABLE `sys_role_resources`  (
  `role_id` bigint(20) NOT NULL,
  `res_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `res_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_resources
-- ----------------------------
INSERT INTO `sys_role_resources` VALUES (10, 1);
INSERT INTO `sys_role_resources` VALUES (10, 2);
INSERT INTO `sys_role_resources` VALUES (10, 3);
INSERT INTO `sys_role_resources` VALUES (10, 4);
INSERT INTO `sys_role_resources` VALUES (10, 5);
INSERT INTO `sys_role_resources` VALUES (10, 6);
INSERT INTO `sys_role_resources` VALUES (10, 7);
INSERT INTO `sys_role_resources` VALUES (10, 8);
INSERT INTO `sys_role_resources` VALUES (10, 9);
INSERT INTO `sys_role_resources` VALUES (10, 10);
INSERT INTO `sys_role_resources` VALUES (10, 11);
INSERT INTO `sys_role_resources` VALUES (10, 12);
INSERT INTO `sys_role_resources` VALUES (10, 13);
INSERT INTO `sys_role_resources` VALUES (10, 14);
INSERT INTO `sys_role_resources` VALUES (10, 15);
INSERT INTO `sys_role_resources` VALUES (10, 16);
INSERT INTO `sys_role_resources` VALUES (10, 17);
INSERT INTO `sys_role_resources` VALUES (10, 18);
INSERT INTO `sys_role_resources` VALUES (10, 19);
INSERT INTO `sys_role_resources` VALUES (10, 271);
INSERT INTO `sys_role_resources` VALUES (10, 272);
INSERT INTO `sys_role_resources` VALUES (10, 273);
INSERT INTO `sys_role_resources` VALUES (10, 274);
INSERT INTO `sys_role_resources` VALUES (10, 275);
INSERT INTO `sys_role_resources` VALUES (10, 276);
INSERT INTO `sys_role_resources` VALUES (10, 277);
INSERT INTO `sys_role_resources` VALUES (10, 278);
INSERT INTO `sys_role_resources` VALUES (10, 279);
INSERT INTO `sys_role_resources` VALUES (10, 280);
INSERT INTO `sys_role_resources` VALUES (10, 281);
INSERT INTO `sys_role_resources` VALUES (10, 282);
INSERT INTO `sys_role_resources` VALUES (10, 283);
INSERT INTO `sys_role_resources` VALUES (10, 284);
INSERT INTO `sys_role_resources` VALUES (10, 285);
INSERT INTO `sys_role_resources` VALUES (10, 286);
INSERT INTO `sys_role_resources` VALUES (10, 287);
INSERT INTO `sys_role_resources` VALUES (10, 288);
INSERT INTO `sys_role_resources` VALUES (18, 11);
INSERT INTO `sys_role_resources` VALUES (18, 15);
INSERT INTO `sys_role_resources` VALUES (18, 16);
INSERT INTO `sys_role_resources` VALUES (18, 17);
INSERT INTO `sys_role_resources` VALUES (18, 18);
INSERT INTO `sys_role_resources` VALUES (18, 19);
INSERT INTO `sys_role_resources` VALUES (18, 271);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `loginName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wiki
-- ----------------------------
DROP TABLE IF EXISTS `wiki`;
CREATE TABLE `wiki`  (
  `wiki_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `source` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `clicks` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '点击数',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `detail` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`wiki_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wiki
-- ----------------------------
INSERT INTO `wiki` VALUES ('7b4d7ddf1b294668be602aa9d3279b1f', '装修有哪些步骤', '品冠装饰', '99999', '3', '1', '<p><span>众所周知，新房装修是一个复杂又繁琐的大工程。要想把新房装修好必须要考虑到方方面面，这样装出来的效果才美观又实用，但是对于外行人来说是一个不小的挑战。那么装修房子的步骤有哪些呢?新房装修注意事项有哪些呢?下面就跟重庆优乐装饰小编一起来看看吧。</span></p><p><span><img src=\"/files/image/62a7fcd8a2e04ae69af32602902e67d2.png\" alt=\"1559201382735381.png\"><br></span></p>');
INSERT INTO `wiki` VALUES ('c5f76ba248114584a6b3ade8890182dc', '壁纸要如何挑选', '品冠装饰', '888', '3', '1', '<p><span>墙纸在装修市场上很受消费者的喜好，它的款式、色彩多种多样，大家总能找到自己喜欢的一款墙纸，如今大家都在追求绿色环保，那么什么墙纸好看又环保呢?它们的价格又是多少?接下来小编就给大家简单的介绍一下什么墙纸好看又环保及其价格是多少。</span></p><p><span><img src=\"/files/image/2d083ed263fb446d95ad9a0b78809e26.png\" alt=\"1558686448240411.png\"><br></span></p>');

SET FOREIGN_KEY_CHECKS = 1;
