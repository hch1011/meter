/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.26-log : Database - meter
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

/*Table structure for table `device_info` */

DROP TABLE IF EXISTS `device_info`;

CREATE TABLE `device_info` (
  `id` bigint(20) NOT NULL COMMENT 'PK',
  `code` varchar(64) DEFAULT NULL COMMENT '设备编号： A-1,A-2,A-3...A-9,B-1,B-2',
  `input_num` int(11) DEFAULT NULL COMMENT '进线名称',
  `path` varchar(64) DEFAULT NULL COMMENT '路劲终端位置          1号主变A相， 1号主变B相， 1号主变C相， 2号主变A相...',
  `type` bigint(20) NOT NULL COMMENT '类别',
  `name` varchar(64) DEFAULT NULL COMMENT '名称:避雷针1，避雷针2，SF6密度计1，SF6密度计2',
  `picture_local_path` varchar(256) DEFAULT NULL COMMENT '本地原始图片相对路径',
  `snap_data_id` bigint(20) DEFAULT NULL COMMENT '最新预读数据ID',
  `snap_data` float DEFAULT NULL COMMENT '最新预读数据',
  `snap_status` int(11) DEFAULT '0' COMMENT '最新预读状态:0失败;1正常,2预警,3报警',
  `snap_time` datetime DEFAULT NULL COMMENT '最新预读时间',
  `snap_fail_begin_time` datetime DEFAULT NULL COMMENT '最近连续识别失败开始时间',
  `snap_fail_count` int(11) DEFAULT NULL COMMENT '最近连续识别失败次数',
  `change_rate` float DEFAULT NULL COMMENT '变化率',
  `frequency` int(11) DEFAULT NULL COMMENT '动作次数',
  `warning_reason` varchar(128) DEFAULT NULL COMMENT '报警原因',
  `monitor_enable` int(11) DEFAULT '1' COMMENT '是否监控;1是0否',
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
