/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.6.33 : Database - meter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`meter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `meter`;

/*Table structure for table `device_data` */

DROP TABLE IF EXISTS `device_data`;

CREATE TABLE `device_data` (
  `id` bigint(20) NOT NULL COMMENT 'PK',
  `device_id` bigint(20) DEFAULT NULL COMMENT '设备ID',
  `device_code` varchar(64) DEFAULT NULL COMMENT '设备编号： A-1,A-2,A-3...A-9,B-1,B-2 ',
  `snap_data` float DEFAULT NULL COMMENT '最新预读数据',
  `snap_status` int(11) DEFAULT '1' COMMENT '最新预读状态(汉字):1正常,2预警,3报警,9失败',
  `snap_time` datetime DEFAULT NULL COMMENT '最新预读时间',
  `change_rate` float DEFAULT NULL COMMENT '变化率',
  `frequency` int(11) DEFAULT NULL COMMENT '动作次数',
  `data_type` varchar(32) DEFAULT NULL COMMENT '数据类型:schedule.launch正常监控(任务触发), manual.launch人工触发监控, manual.edit人工修改 ',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `picture_local_path` varchar(512) DEFAULT NULL COMMENT '本地原图存储路径',
  `picture_url` varchar(512) DEFAULT NULL COMMENT '图片路径',
  `meta_data` varchar(1024) DEFAULT NULL COMMENT '元数据，如果有，json格式',
  `warning_reason` varchar(512) DEFAULT NULL COMMENT '报警原因',
  `sync_success_time` datetime DEFAULT NULL COMMENT '同步成功时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
