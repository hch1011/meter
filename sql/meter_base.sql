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

/*Table structure for table `camera_info` */

DROP TABLE IF EXISTS `camera_info`;

CREATE TABLE `camera_info` (
  `device_serial` varchar(128) NOT NULL COMMENT '参考摄像头信息对象CameraInfo',
  `device_name` varchar(128) DEFAULT NULL,
  `model` varchar(128) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `defence` varchar(32) DEFAULT NULL,
  `is_encrypt` varchar(32) DEFAULT NULL,
  `channel_no` varchar(128) DEFAULT NULL,
  `channel_name` varchar(128) DEFAULT NULL,
  `is_shared` varchar(32) DEFAULT NULL,
  `pic_url` varchar(128) DEFAULT NULL,
  `video_level` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`device_serial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `camera_info` */

/*Table structure for table `device_info` */

DROP TABLE IF EXISTS `device_info`;

CREATE TABLE `device_info` (
  `id` bigint(20) NOT NULL COMMENT 'PK',
  `code` varchar(64) DEFAULT NULL COMMENT '设备编号： A-1,A-2,A-3...A-9,B-1,B-2',
  `input_num` int(11) DEFAULT NULL COMMENT '进线名称',
  `path` varchar(64) DEFAULT NULL COMMENT '路劲终端位置          1号主变A相， 1号主变B相， 1号主变C相， 2号主变A相...',
  `type` bigint(20) NOT NULL COMMENT '类别',
  `name` varchar(64) DEFAULT NULL COMMENT '名称:避雷针1，避雷针2，SF6密度计1，SF6密度计2',
  `ip` varchar(32) DEFAULT NULL COMMENT '终端IP',
  `picture_local_path` varchar(256) DEFAULT NULL COMMENT '本地原始图片相对路径',
  `snap_data_id` bigint(20) DEFAULT NULL COMMENT '最新预读数据ID',
  `snap_data` float DEFAULT NULL COMMENT '最新预读数据',
  `snap_status` int(11) DEFAULT '0' COMMENT '最新预读状态:0失败;1正常,2预警,3报警',
  `snap_time` datetime DEFAULT NULL COMMENT '最新预读时间',
  `change_rate` float DEFAULT NULL COMMENT '变化率',
  `frequency` int(11) DEFAULT NULL COMMENT '动作次数',
  `warning_reason` varchar(128) DEFAULT NULL COMMENT '报警原因',
  `monitor_page_flag` int(11) DEFAULT '1' COMMENT '1出现在监控首页,其它不出现',
  `monitor_page_sort` float DEFAULT '0' COMMENT '出现在监控首页顺序,在同一类别内排序,默认按编号',
  `camera_serial` varchar(128) DEFAULT NULL COMMENT '摄像头序列号',
  `camera_bind_time` datetime DEFAULT NULL COMMENT '摄像头绑定时间',
  `x` int(11) DEFAULT NULL COMMENT '图片切片坐标 X轴起点',
  `y` int(11) DEFAULT NULL COMMENT '图片切片坐标 Y轴起点',
  `w` int(11) DEFAULT NULL COMMENT '图片切片宽度',
  `h` int(11) DEFAULT NULL COMMENT '图片切片高度',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`camera_serial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_info` */

insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000001,'M-1',1,'1号主变A相',1,'密度计#1',NULL,'2016_12\\16\\20161216230000_1_1000001_573604530.jpg',984241449386016,0,3,'2016-12-16 23:00:00',NULL,NULL,'识别程序报错;SF6密度<报警值1.3',1,1,'573604530','2016-12-11 10:17:16',301,92,72,70,NULL,'2016-12-11 22:33:42',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000002,'M-2',1,'1号主变B相',1,'密度计#2',NULL,NULL,984241455456288,0,3,'2016-12-16 23:00:03',NULL,NULL,'未找到摄像头;SF6密度<报警值1.3',1,2,'virtual_2','2016-10-27 01:01:07',NULL,NULL,NULL,NULL,NULL,'2016-10-27 01:01:07',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000003,'M-3',1,'1号主变C相',1,'密度计#3',NULL,'2016_12\\16\\20161216230003_1_1000003_664690108.jpg',984241482924064,0.79,3,'2016-12-16 23:00:03',NULL,NULL,'SF6密度<报警值1.3',1,3,'664690108','2016-12-11 23:48:24',184,70,199,192,NULL,'2016-12-11 23:48:26',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000001,'B-1',2,'2号主变A相',2,'避雷器#1',NULL,'2016_12\\13\\20161213000501_2_2000001_573604832.jpg',981442245312544,0,0,'2016-12-13 00:05:00',NULL,NULL,'保存图片失败',1,1,'573604832','2016-12-11 20:50:49',84,155,455,183,NULL,'2016-12-16 22:53:11',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000002,'B-2',2,'2号主变B相',2,'避雷器#2',NULL,'2016_12\\13\\20161213000503_2_2000002_664690436.jpg',981442256527392,0,0,'2016-12-13 00:05:03',NULL,NULL,'保存图片失败',1,2,'664690436','2016-11-27 12:14:57',173,128,185,112,NULL,'2016-12-11 22:46:44',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000003,'B-3',2,'2号主变C相',2,'避雷器#3',NULL,NULL,984236540854304,0,1,'2016-12-16 22:50:03',NULL,NULL,'未找到摄像头',1,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000004,'B-4',3,'3号主变A相',2,'避雷器#4',NULL,NULL,984236546031648,0,1,'2016-12-16 22:50:03',NULL,NULL,'未找到摄像头',1,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000005,'B-5',3,'3号主变B相',2,'避雷器#5',NULL,NULL,984236550357024,0,1,'2016-12-16 22:50:04',NULL,NULL,'未找到摄像头',1,5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000006,'B-6',3,'3号主变C相',2,'避雷器#6',NULL,NULL,984236554928160,0,1,'2016-12-16 22:50:05',NULL,NULL,'未找到摄像头',1,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000007,'B-7',4,'4号主变A相',2,'避雷器#7',NULL,NULL,984236559024160,0,1,'2016-12-16 22:50:05',NULL,NULL,'未找到摄像头',1,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000008,'B-8',4,'4号主变B相',2,'避雷器#8',NULL,NULL,984236565135392,0,1,'2016-12-16 22:50:06',NULL,NULL,'未找到摄像头',1,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000009,'B-9',5,'4号主变C相',2,'避雷器#9',NULL,NULL,984236569411616,0,1,'2016-12-16 22:50:06',NULL,NULL,'未找到摄像头',1,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `device_type` */

DROP TABLE IF EXISTS `device_type`;

CREATE TABLE `device_type` (
  `type` bigint(20) NOT NULL COMMENT '类型编号,人工维护',
  `type_name` varchar(128) DEFAULT NULL COMMENT '类别名:避雷针; SF6密度',
  `data_name` varchar(128) DEFAULT NULL COMMENT '设备数据名称如:电流,SF6密度;用于组合页面label显示',
  `data_for_warning` float DEFAULT NULL COMMENT '预警阈值(mA)',
  `data_for_alarm` float DEFAULT NULL COMMENT '报警阈值(mA)',
  `data_warning_strategy` varchar(8) DEFAULT NULL COMMENT '四种报警/预警策略(<,<=,>,>=);其他符号表示不做判断,做正常处理',
  `data_unit` varchar(32) DEFAULT NULL COMMENT '单位mA',
  `change_rate_for_warning` float DEFAULT NULL COMMENT '变化率频预警阈值',
  `change_rate_for_alarm` float DEFAULT NULL COMMENT '变化频率报警阈值',
  `change_rate_warning_strategy` varchar(8) DEFAULT NULL COMMENT '报警策略<,<=,>,>=',
  `change_rate_unit` varchar(32) DEFAULT NULL COMMENT '单位mA/6h',
  `frequency_for_warning` float DEFAULT NULL COMMENT '动作次数预警阈值',
  `frequency_for_alarm` float DEFAULT NULL COMMENT '动作次数报警阈值',
  `frequency_warning_strategy` varchar(8) DEFAULT NULL COMMENT '报警策略<,<=,>,>=',
  `snap_times` varchar(128) DEFAULT NULL COMMENT '每日巡查时间(08:00,23:00 )',
  `monitor_page_flag` int(11) DEFAULT NULL COMMENT '1出现在监控首页,其它不出现',
  `monitor_page_sort` int(11) DEFAULT NULL COMMENT '顺序',
  `recognition_program_path` varchar(128) DEFAULT NULL COMMENT '识别程序完整路径',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备类型配置';

/*Data for the table `device_type` */

insert  into `device_type`(`type`,`type_name`,`data_name`,`data_for_warning`,`data_for_alarm`,`data_warning_strategy`,`data_unit`,`change_rate_for_warning`,`change_rate_for_alarm`,`change_rate_warning_strategy`,`change_rate_unit`,`frequency_for_warning`,`frequency_for_alarm`,`frequency_warning_strategy`,`snap_times`,`monitor_page_flag`,`monitor_page_sort`,`recognition_program_path`,`description`) values (1,'SF6密度','SF6密度',2,1.3,'<','单位',4,3.4,'<','单位/H',6,5,'>','00,30',1,1,'C:\\D\\data\\meter\\tools\\halcondot_ss\\11月28日双色表命令行.exe',NULL);
insert  into `device_type`(`type`,`type_name`,`data_name`,`data_for_warning`,`data_for_alarm`,`data_warning_strategy`,`data_unit`,`change_rate_for_warning`,`change_rate_for_alarm`,`change_rate_warning_strategy`,`change_rate_unit`,`frequency_for_warning`,`frequency_for_alarm`,`frequency_warning_strategy`,`snap_times`,`monitor_page_flag`,`monitor_page_sort`,`recognition_program_path`,`description`) values (2,'氧化锌避雷器','接地电流',2,4,'>','单位',1,2,'>','mA/6H',2,5,'>','00,05,10,15,20,25,30,35,40,45,50,55',1,2,'C:\\D\\data\\meter\\tools\\halcondot_f\\11月8日电流表加数码管.exe',NULL);

/*Table structure for table `system_account` */

DROP TABLE IF EXISTS `system_account`;

CREATE TABLE `system_account` (
  `id` int(11) NOT NULL COMMENT 'pk',
  `login_name` varchar(128) NOT NULL COMMENT '登录名',
  `salt` varchar(128) DEFAULT NULL COMMENT '加密方式,空表示明文密码',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(128) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(128) DEFAULT NULL COMMENT '真实姓名',
  `role` varchar(32) DEFAULT NULL COMMENT '角色:systemAdmin,manager,user',
  `status` int(11) DEFAULT NULL COMMENT '状态0不可用1正常9锁定',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_account` */

insert  into `system_account`(`id`,`login_name`,`salt`,`password`,`nickname`,`real_name`,`role`,`status`,`create_time`,`update_time`) values (1,'admin','','admin123','admin','admin','manager',1,NULL,'2016-12-16 22:44:38');
insert  into `system_account`(`id`,`login_name`,`salt`,`password`,`nickname`,`real_name`,`role`,`status`,`create_time`,`update_time`) values (2,'user',NULL,'user123','user','user','user',NULL,NULL,NULL);

/*Table structure for table `system_property` */

DROP TABLE IF EXISTS `system_property`;

CREATE TABLE `system_property` (
  `code` varchar(128) NOT NULL COMMENT '键',
  `value` varchar(1024) DEFAULT NULL COMMENT '值',
  `group` varchar(128) DEFAULT NULL COMMENT '分组',
  `status` int(11) DEFAULT '1' COMMENT '状态1可用0不可用',
  `notes` varchar(256) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `system_property` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
