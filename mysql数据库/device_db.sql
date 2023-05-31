/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : device_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-28 15:28:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_buyplan`
-- ----------------------------
DROP TABLE IF EXISTS `t_buyplan`;
CREATE TABLE `t_buyplan` (
  `planId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `deviceTypeObj` int(11) NOT NULL COMMENT '设备类型',
  `deviceName` varchar(20) NOT NULL COMMENT '设备名称',
  `buyNum` int(11) NOT NULL COMMENT '数量',
  `buyMoney` float NOT NULL COMMENT '金额',
  `guige` varchar(20) default NULL COMMENT '规格型号',
  `yongtu` varchar(30) default NULL COMMENT '用途',
  `sydw` varchar(20) NOT NULL COMMENT '使用单位',
  `jhsj` varchar(20) default NULL COMMENT '计划时间',
  `sfgz` varchar(20) NOT NULL COMMENT '是否购置',
  PRIMARY KEY  (`planId`),
  KEY `deviceTypeObj` (`deviceTypeObj`),
  CONSTRAINT `t_buyplan_ibfk_1` FOREIGN KEY (`deviceTypeObj`) REFERENCES `t_devicetype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_buyplan
-- ----------------------------
INSERT INTO `t_buyplan` VALUES ('1', '1', 'GPS系统', '1', '1', '', '', '电视机房', '2018-01-08 21:05:12', '是');

-- ----------------------------
-- Table structure for `t_datadraw`
-- ----------------------------
DROP TABLE IF EXISTS `t_datadraw`;
CREATE TABLE `t_datadraw` (
  `drawId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `ssdw` varchar(20) NOT NULL COMMENT '所属单位',
  `drawClass` varchar(20) NOT NULL COMMENT '图纸类别',
  `drawName` varchar(20) NOT NULL COMMENT '图纸名称',
  `drawDesc` varchar(2000) default NULL COMMENT '图纸描述',
  `drawFile` varchar(60) NOT NULL COMMENT '图纸文件',
  PRIMARY KEY  (`drawId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_datadraw
-- ----------------------------
INSERT INTO `t_datadraw` VALUES ('1', '北京', '国内图书', '电池电阻使用帮助.txt', '为了方便客户熟悉电网设备的电压，电池，电池组等', '');

-- ----------------------------
-- Table structure for `t_device`
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `deviceId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `deviceTypeObj` int(11) NOT NULL COMMENT '设备类型',
  `deviceName` varchar(30) NOT NULL COMMENT '设备名称',
  `devicePhoto` varchar(60) NOT NULL COMMENT '设备图片',
  `guige` varchar(20) NOT NULL COMMENT '规格型号',
  `deviceState` varchar(20) NOT NULL COMMENT '设备状态',
  `useDate` varchar(20) default NULL COMMENT '使用日期',
  `jxzq` varchar(20) NOT NULL COMMENT '检修周期',
  `ssdw` varchar(20) NOT NULL COMMENT '所属单位',
  `jhsj` varchar(20) default NULL COMMENT '计划时间',
  `deviceDesc` varchar(5000) default NULL COMMENT '设备描述',
  PRIMARY KEY  (`deviceId`),
  KEY `deviceTypeObj` (`deviceTypeObj`),
  CONSTRAINT `t_device_ibfk_1` FOREIGN KEY (`deviceTypeObj`) REFERENCES `t_devicetype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_device
-- ----------------------------
INSERT INTO `t_device` VALUES ('1', '1', 'UPS123', 'upload/NoImage.jpg', 'FR-UK 3140', '正常', '2018-01-02', '1月', '北京', '2018-01-10 21:02:52', '<p>这个设备很先进，用了很先进的技术</p>');

-- ----------------------------
-- Table structure for `t_devicetype`
-- ----------------------------
DROP TABLE IF EXISTS `t_devicetype`;
CREATE TABLE `t_devicetype` (
  `typeId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `typeName` varchar(20) NOT NULL COMMENT '类型名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_devicetype
-- ----------------------------
INSERT INTO `t_devicetype` VALUES ('1', '类型1');
INSERT INTO `t_devicetype` VALUES ('2', '类型2');

-- ----------------------------
-- Table structure for `t_maintain`
-- ----------------------------
DROP TABLE IF EXISTS `t_maintain`;
CREATE TABLE `t_maintain` (
  `maintainId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `ssdw` varchar(20) NOT NULL COMMENT '所属单位',
  `jhsj` varchar(20) default NULL COMMENT '计划时间',
  `whnr` varchar(2000) default NULL COMMENT '维护内容',
  PRIMARY KEY  (`maintainId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_maintain
-- ----------------------------
INSERT INTO `t_maintain` VALUES ('1', '北京', '2018-01-04', 'test');

-- ----------------------------
-- Table structure for `t_repair`
-- ----------------------------
DROP TABLE IF EXISTS `t_repair`;
CREATE TABLE `t_repair` (
  `repairId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `deviceTypeObj` int(11) NOT NULL COMMENT '设备类型',
  `deviceName` varchar(20) NOT NULL COMMENT '设备名称',
  `jyzq` varchar(20) NOT NULL COMMENT '校验周期',
  `useDate` varchar(20) default NULL COMMENT '使用日期',
  `jzzt` varchar(20) NOT NULL COMMENT '校准状态',
  `jzrq` varchar(20) default NULL COMMENT '校准日期',
  `ssdw` varchar(20) NOT NULL COMMENT '所属单位',
  PRIMARY KEY  (`repairId`),
  KEY `deviceTypeObj` (`deviceTypeObj`),
  CONSTRAINT `t_repair_ibfk_1` FOREIGN KEY (`deviceTypeObj`) REFERENCES `t_devicetype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_repair
-- ----------------------------
INSERT INTO `t_repair` VALUES ('1', '1', 'UPS123', '1年', '2018-01-02', '未校准', '2018-01-06', '北京');

-- ----------------------------
-- Table structure for `t_sitebase`
-- ----------------------------
DROP TABLE IF EXISTS `t_sitebase`;
CREATE TABLE `t_sitebase` (
  `siteBaseId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `ssdw` varchar(20) NOT NULL COMMENT '所属单位',
  `gsd` varchar(20) NOT NULL COMMENT '归属地',
  `zddh` varchar(20) NOT NULL COMMENT '站点代号',
  `zdmc` varchar(20) NOT NULL COMMENT '站点名称',
  `zdlb` varchar(20) NOT NULL COMMENT '站点类别',
  `txfs` varchar(20) NOT NULL COMMENT '通讯方式',
  `zzdd` varchar(20) NOT NULL COMMENT '安装地点',
  `longitude` float NOT NULL COMMENT '经度',
  `latitude` float NOT NULL COMMENT '纬度',
  PRIMARY KEY  (`siteBaseId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sitebase
-- ----------------------------
INSERT INTO `t_sitebase` VALUES ('1', '北京', 'maradona', 'maradona', 'maradona', '国内遥控站', 'maradona', 'maradona', '116.32', '39.9');

-- ----------------------------
-- Table structure for `t_siterun`
-- ----------------------------
DROP TABLE IF EXISTS `t_siterun`;
CREATE TABLE `t_siterun` (
  `siteRunId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `ssdw` varchar(20) NOT NULL COMMENT '所属单位',
  `zddh` varchar(20) NOT NULL COMMENT '站点代号',
  `zdlb` varchar(20) NOT NULL COMMENT '站点类别',
  `zdmc` varchar(20) NOT NULL COMMENT '站点名称',
  `gzlx` varchar(20) NOT NULL COMMENT '故障类型',
  `gzsj` varchar(20) default NULL COMMENT '故障时间',
  `gzsc` varchar(20) NOT NULL COMMENT '故障时长',
  `hfqk` varchar(2000) NOT NULL COMMENT '恢复情况',
  PRIMARY KEY  (`siteRunId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_siterun
-- ----------------------------
INSERT INTO `t_siterun` VALUES ('1', '厦门', 'dh123', 'leibie1', '宁化', '电机', '2018-01-09 21:15:56', '18989.80', '备注2');
