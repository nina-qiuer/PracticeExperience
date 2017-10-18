USE quality;

CREATE TABLE `cm_sync_task` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `data_id` INT(11) NOT NULL DEFAULT '0' COMMENT '数据ID',
   `task_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '任务类型标识',
   `fail_times` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '失败次数',
   `add_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `del_flag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   PRIMARY KEY (`id`),
   KEY `task_type_data_id` ( `task_type`, `data_id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='通用数据任务表';
 
 
 ALTER TABLE `cre`.`route_remark_satisfaction` 
ADD INDEX `year_week` (`year`, `week`), ADD INDEX `route_key` (`route_key`);

CREATE TABLE qs_supplier_resp(
	id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	qc_id INT(11) NOT NULL DEFAULT '0' COMMENT '质检单id',
	complaint_id INT(11) NOT NULL DEFAULT '0' COMMENT '投诉单id',
	product_id INT(11) NOT NULL DEFAULT '0' COMMENT '产品id',
	order_id INT(11) NOT NULL DEFAULT '0' COMMENT '订单id',
	agency_name VARCHAR(100) NOT NULL DEFAULT '' COMMENT '供应商名称',
	quality_problem VARCHAR(30) NOT NULL DEFAULT '' COMMENT '质量问题',
	share_amount DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '分担总额（元）',
	PRIMARY KEY (`id`),
	KEY `qc_id` (`qc_id`)
)ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='供应商责任赔付信息';