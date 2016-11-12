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
  `data_type` int(11) DEFAULT '1' COMMENT '数值类型:1预读,2重新识别,3人工修正 ',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `picture_local_path` varchar(512) DEFAULT NULL COMMENT '本地原图存储路径',
  `picture_url` varchar(512) DEFAULT NULL COMMENT '图片路径',
  `meta_data` varchar(1024) DEFAULT NULL COMMENT '元数据，如果有，json格式',
  `warning_reason` varchar(512) DEFAULT NULL COMMENT '报警原因',
  `sync_success_time` datetime DEFAULT NULL COMMENT '同步成功时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_data` */

insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (1,1,'deviceCode',1.2,NULL,'2016-09-04 07:51:53',NULL,NULL,1,'2016-09-04 07:51:53',NULL,NULL,'pictureUrl','metaData','warningReason',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (872320156454016,1000001,NULL,1,3,'2016-07-11 19:55:35',1,2,1,'2016-07-11 19:55:35','2016-07-11 19:55:35',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度<报警值1.3;SF6密度<预警值1.3;SF6密度<预警值1.3',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (872326270125792,1000002,'B-1',100,3,'2016-07-11 20:08:02',0.3,3,1,'2016-07-11 20:08:02','2016-07-11 20:08:02',NULL,'/resources/images/imginfo.png',NULL,'电流>报警值4.0;电流>报警值4.0;电流>报警值4.0;电流>预警值4.0;电流>预警值4.0;电流>预警值4.0',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910859974782144,1000001,'M-1',1,3,'2016-09-04 06:45:03',1,2,1,'2016-09-04 06:45:03','2016-09-04 06:45:03',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4;SF6密度<报警值1.3;SF6密度变化率<报警值3.4','2016-09-04 06:45:04');
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910864400469184,1000001,'M-1',1,3,'2016-09-04 06:54:03',1,2,1,'2016-09-04 06:54:03','2016-09-04 06:54:03',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4;SF6密度<报警值1.3;SF6密度变化率<报警值3.4','2016-09-04 06:54:05');
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910865441180864,1000001,'M-1',1,3,'2016-09-04 06:56:10',1,2,1,'2016-09-04 06:56:10','2016-09-04 06:56:10',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910867044068544,1000001,'M-1',1,3,'2016-09-04 06:59:26',1,2,1,'2016-09-04 06:59:26','2016-09-04 06:59:26',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4;SF6密度<报警值1.3;SF6密度变化率<报警值3.4','2016-09-04 07:03:20');
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910876797193408,1000001,'M-1',1,3,'2016-09-04 07:19:16',1,2,1,'2016-09-04 07:19:16','2016-09-04 07:19:16',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910877142256832,1000001,'M-1',1,3,'2016-09-04 07:19:59',1,2,1,'2016-09-04 07:19:59','2016-09-04 07:19:59',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910878817406144,1000001,'M-1',1,3,'2016-09-04 07:23:23',1,2,1,'2016-09-04 07:23:23','2016-09-04 07:23:23',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910879818443968,1000001,'M-1',1,3,'2016-09-04 07:25:25',1,2,1,'2016-09-04 07:25:25','2016-09-04 07:25:25',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910880989015232,1000001,'M-1',1,3,'2016-09-04 07:27:48',1,2,1,'2016-09-04 07:27:48','2016-09-04 07:27:48',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910885626309824,1000001,'M-1',1,3,'2016-09-04 07:37:14',1,2,1,'2016-09-04 07:37:14','2016-09-04 07:37:14',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',NULL);
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910889033764032,1000001,'M-1',1,3,'2016-09-04 07:44:10',1,2,1,'2016-09-04 07:44:10','2016-09-04 07:44:10',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4','2016-09-04 07:44:14');
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910891431972032,1000001,'M-1',1,3,'2016-09-04 07:49:03',1,2,1,'2016-09-04 07:49:03','2016-09-04 07:49:03',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4','2016-09-04 07:49:03');
insert  into `device_data`(`id`,`device_id`,`device_code`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`data_type`,`create_time`,`update_time`,`picture_local_path`,`picture_url`,`meta_data`,`warning_reason`,`sync_success_time`) values (910900871974080,1000001,'M-1',1,3,'2016-09-04 08:08:15',1,2,1,'2016-09-04 08:08:15','2016-09-04 08:08:15',NULL,'/resources/images/imginfo.png',NULL,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4','2016-09-04 08:08:40');

/*Table structure for table `device_info` */

DROP TABLE IF EXISTS `device_info`;

CREATE TABLE `device_info` (
  `id` bigint(20) NOT NULL COMMENT 'PK',
  `code` varchar(64) DEFAULT NULL COMMENT '设备编号： A-1,A-2,A-3...A-9,B-1,B-2',
  `input_num` int(11) DEFAULT NULL COMMENT '进线名称',
  `path` varchar(64) DEFAULT NULL COMMENT '路劲终端位置          1号主变A相， 1号主变B相， 1号主变C相， 2号主变A相...',
  `type` bigint(20) DEFAULT NULL COMMENT '类别',
  `name` varchar(64) DEFAULT NULL COMMENT '名称:避雷针1，避雷针2，SF6密度计1，SF6密度计2',
  `ip` varchar(32) DEFAULT NULL COMMENT '终端IP',
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

insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000001,'M-1',1,'1号主变A相',1,'密度计#1',NULL,910900871974080,1,3,'2016-09-04 08:08:15',1,2,'SF6密度<报警值1.3;SF6密度变化率<报警值3.4',1,1,'630782726','2016-11-05 21:15:53',NULL,NULL,NULL,NULL,NULL,'2016-11-05 21:15:53',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000002,'M-2',1,'1号主变B相',1,'密度计#2',NULL,NULL,1.2,1,'2016-06-30 11:29:21',0.3,3,NULL,1,2,'virtual_2','2016-10-27 01:01:07',NULL,NULL,NULL,NULL,NULL,'2016-10-27 01:01:07',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000003,'M-3',1,'1号主变C相',1,'密度计#3',NULL,NULL,1.2,1,'2016-06-30 11:29:21',NULL,NULL,NULL,1,3,'573604832','2016-11-05 22:07:47',68,211,600,235,NULL,'2016-11-06 21:53:36',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000001,'B-1',2,'2号主变A相',2,'避雷器#1',NULL,872326270125792,100,3,'2016-07-11 20:08:02',10,10,'电流>报警值4.0;电流>报警值4.0;电流>报警值4.0;电流>预警值4.0;电流>预警值4.0;电流>预警值4.0',1,1,'573604832','2016-11-05 22:49:42',NULL,NULL,NULL,NULL,NULL,'2016-11-05 22:49:42',NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000002,'B-2',2,'2号主变B相',2,'避雷器#2',NULL,NULL,1.3,2,'2016-06-30 11:29:21',NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000003,'B-3',2,'2号主变C相',2,'避雷器#3',NULL,NULL,1.3,3,'2016-06-30 11:29:21',NULL,NULL,NULL,1,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000004,'B-4',3,'3号主变A相',2,'避雷器#4',NULL,NULL,1.3,0,'2016-06-30 11:29:21',NULL,NULL,NULL,1,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000005,'B-5',3,'3号主变B相',2,'避雷器#5',NULL,NULL,1.3,0,'2016-06-30 11:29:21',NULL,NULL,NULL,1,5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000006,'B-6',3,'3号主变C相',2,'避雷器#6',NULL,NULL,1.3,1,'2016-06-30 11:29:21',NULL,NULL,NULL,1,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000007,'B-7',4,'4号主变A相',2,'避雷器#7',NULL,NULL,1.3,1,'2016-06-30 11:29:21',NULL,NULL,NULL,1,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000008,'B-8',4,'4号主变B相',2,'避雷器#8',NULL,NULL,1.3,1,'2016-06-30 11:29:21',NULL,NULL,NULL,1,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`ip`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`change_rate`,`frequency`,`warning_reason`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (2000009,'B-9',4,'4号主变C相',2,'避雷器#9',NULL,NULL,1.3,1,'2016-06-30 11:29:21',NULL,NULL,NULL,1,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

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

insert  into `device_type`(`type`,`type_name`,`data_name`,`data_for_warning`,`data_for_alarm`,`data_warning_strategy`,`data_unit`,`change_rate_for_warning`,`change_rate_for_alarm`,`change_rate_warning_strategy`,`change_rate_unit`,`frequency_for_warning`,`frequency_for_alarm`,`frequency_warning_strategy`,`snap_times`,`monitor_page_flag`,`monitor_page_sort`,`recognition_program_path`,`description`) values (1,'SF6密度','SF6密度',2,1.3,'<','密度单位',4,3.4,'<','密度单位/H',6,5,'>','08:00',1,1,'C:\\D\\data\\meter\\tools\\halcondot_f\\f32.exe',NULL);
insert  into `device_type`(`type`,`type_name`,`data_name`,`data_for_warning`,`data_for_alarm`,`data_warning_strategy`,`data_unit`,`change_rate_for_warning`,`change_rate_for_alarm`,`change_rate_warning_strategy`,`change_rate_unit`,`frequency_for_warning`,`frequency_for_alarm`,`frequency_warning_strategy`,`snap_times`,`monitor_page_flag`,`monitor_page_sort`,`recognition_program_path`,`description`) values (2,'氧化锌避雷器','电流',2,4,'>','避雷器单位',1,2,'>','mA/6H',2,5,'>','09:00',1,2,NULL,NULL);

/*Table structure for table `system_property` */

DROP TABLE IF EXISTS `system_property`;

CREATE TABLE `system_property` (
  `code` varchar(128) NOT NULL,
  `value` varchar(1024) DEFAULT NULL,
  `notes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `system_property` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
