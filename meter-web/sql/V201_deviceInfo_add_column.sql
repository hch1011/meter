alter table `meter`.`device_info` 
   add column `snap_fail_begin_time` datetime NULL COMMENT '最近连续识别失败开始时间' after `snap_time`, 
   add column `snap_fail_count` int NULL COMMENT '最近连续识别失败次数' after `snap_fail_begin_time`,
   add column `monitor_enable` int DEFAULT '1' NULL COMMENT '是否监控;1是0否' after `warning_reason`;
   
alter table `meter`.`device_info` drop column `ip`;



