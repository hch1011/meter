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

/*Data for the table `camera_info` */

/*Data for the table `device_data` */

/*Data for the table `device_info` */

insert  into `device_info`(`id`,`code`,`input_num`,`path`,`type`,`name`,`picture_local_path`,`snap_data_id`,`snap_data`,`snap_status`,`snap_time`,`snap_fail_begin_time`,`snap_fail_count`,`change_rate`,`frequency`,`warning_reason`,`monitor_enable`,`monitor_page_flag`,`monitor_page_sort`,`camera_serial`,`camera_bind_time`,`x`,`y`,`w`,`h`,`create_time`,`update_time`,`description`) values (1000001,'M-1',1,'1号主变A相',1,'密度计#1','2016_12\\16\\20161216230000_1_1000001_573604530.jpg',984241449386016,0,0,'2016-12-16 23:00:00','2019-05-11 18:30:00',8,NULL,NULL,'设备抓拍图片异常:设备不在线',1,1,1,'573604530','2016-12-11 10:17:16',301,92,72,70,NULL,'2019-05-11 22:00:00',NULL),(1000002,'M-2',1,'1号主变B相',1,'密度计#2',NULL,984241455456288,0,0,'2016-12-16 23:00:03','2019-05-11 18:30:00',8,NULL,NULL,'未找到摄像头',1,1,2,'virtual_2','2016-10-27 01:01:07',NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:00:00',NULL),(1000003,'M-3',1,'1号主变C相',1,'密度计#3','2016_12\\16\\20161216230003_1_1000003_664690108.jpg',984241482924064,0.79,0,'2016-12-16 23:00:03','2019-05-11 18:30:00',8,NULL,NULL,'设备抓拍图片异常:设备不在线',1,1,3,'664690108','2016-12-11 23:48:24',184,70,199,192,NULL,'2019-05-11 22:00:00',NULL),(2000001,'B-1',2,'2号主变A相',2,'避雷器#1','2016_12\\13\\20161213000501_2_2000001_573604832.jpg',981442245312544,0,0,'2016-12-13 00:05:00','2019-05-11 18:05:00',51,NULL,NULL,'设备抓拍图片异常:设备不在线',1,1,1,'573604832','2016-12-11 20:50:49',84,155,455,183,NULL,'2019-05-11 22:25:00',NULL),(2000002,'B-2',2,'2号主变B相',2,'避雷器#2','2016_12\\13\\20161213000503_2_2000002_664690436.jpg',981442256527392,0,0,'2016-12-13 00:05:03','2019-05-11 18:05:01',51,NULL,NULL,'设备抓拍图片异常:设备不在线',1,1,2,'664690436','2016-11-27 12:14:57',173,128,185,112,NULL,'2019-05-11 22:25:01',NULL),(2000003,'B-3',2,'2号主变C相',2,'避雷器#3',NULL,984236540854304,0,0,'2016-12-16 22:50:03','2019-05-11 18:05:02',51,NULL,NULL,'未找到摄像头',1,1,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:01',NULL),(2000004,'B-4',3,'3号主变A相',2,'避雷器#4',NULL,984236546031648,0,0,'2016-12-16 22:50:03','2019-05-11 18:05:02',51,NULL,NULL,'未找到摄像头',1,1,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:02',NULL),(2000005,'B-5',3,'3号主变B相',2,'避雷器#5',NULL,984236550357024,0,0,'2016-12-16 22:50:04','2019-05-11 18:05:02',51,NULL,NULL,'未找到摄像头',1,1,5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:02',NULL),(2000006,'B-6',3,'3号主变C相',2,'避雷器#6',NULL,984236554928160,0,0,'2016-12-16 22:50:05','2019-05-11 18:05:03',51,NULL,NULL,'未找到摄像头',1,1,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:02',NULL),(2000007,'B-7',4,'4号主变A相',2,'避雷器#7',NULL,984236559024160,0,0,'2016-12-16 22:50:05','2019-05-11 18:05:03',51,NULL,NULL,'未找到摄像头',1,1,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:03',NULL),(2000008,'B-8',4,'4号主变B相',2,'避雷器#8',NULL,984236565135392,0,0,'2016-12-16 22:50:06','2019-05-11 18:05:04',51,NULL,NULL,'未找到摄像头',1,1,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:03',NULL),(2000009,'B-9',5,'4号主变C相',2,'避雷器#9',NULL,984236569411616,0,0,'2016-12-16 22:50:06','2019-05-11 18:05:04',51,NULL,NULL,'未找到摄像头',1,1,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-05-11 22:25:03',NULL);

/*Data for the table `device_type` */

insert  into `device_type`(`type`,`type_name`,`data_name`,`data_for_warning`,`data_for_alarm`,`data_warning_strategy`,`data_unit`,`change_rate_for_warning`,`change_rate_for_alarm`,`change_rate_warning_strategy`,`change_rate_unit`,`frequency_for_warning`,`frequency_for_alarm`,`frequency_warning_strategy`,`snap_times`,`monitor_page_flag`,`monitor_page_sort`,`recognition_program_path`,`description`) values (1,'SF6密度','SF6密度',2,1.3,'<','单位',4,3.4,'<','单位/H',6,5,'>','00,30',1,1,'C:\\D\\data\\meter\\tools\\halcondot_ss\\11月28日双色表命令行.exe',NULL),(2,'氧化锌避雷器','接地电流',2,4,'>','单位',1,2,'>','mA/6H',2,5,'>','00,05,10,15,20,25,30,35,40,45,50,55',1,2,'C:\\D\\data\\meter\\tools\\halcondot_f\\11月8日电流表加数码管.exe',NULL);

/*Data for the table `system_account` */

insert  into `system_account`(`id`,`login_name`,`salt`,`password`,`nickname`,`real_name`,`role`,`status`,`create_time`,`update_time`) values (1,'admin',NULL,'admin123','admin','admin','manager',1,NULL,'2019-05-11 19:20:26'),(2,'user',NULL,'user123','user','user','user',NULL,NULL,NULL);

/*Data for the table `system_property` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
