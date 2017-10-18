USE quality;


DROP TABLE IF EXISTS qs_punish_prd;
CREATE TABLE `qs_punish_prd` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `triggerTime` DATE DEFAULT NULL COMMENT '触发时间',
   `orderId` INT(20) NOT NULL DEFAULT '0' COMMENT '订单号',
   `routeName` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '线路名',
   `routeId` BIGINT(30) NOT NULL DEFAULT '0' COMMENT '线路编号',
   `businessUnit` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '事业部',
   `prdManager` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '产品经理',
   `prdOfficer` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '产品专员',
   `supplier` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '供应商',
   `offlineType` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '下线类型 1:触红 2：低满意度 3：D类',
   `offlineCount` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '下线次数',
   `offlineTime` DATE DEFAULT NULL COMMENT '下线时间',
   `onlineTime` DATE DEFAULT NULL COMMENT '上线时间',
   `status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '状态 1：待整改 2：整改中 3：已整改  4:永久下线',
   `onlineOperTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上线操作时间',
   `onlineOperPerson` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '上线操作人',
   `offlineOperTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下线操作时间',
   `offlineOperPerson` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '下线操作人',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标志位，0：正常，1：删除',
   `travelDateBgn` DATE DEFAULT NULL COMMENT '出游日期',
   `qcId` INT(20) NOT NULL DEFAULT '0' COMMENT '质检单id：触红使用字段',
   `cmpId` INT(11) NOT NULL DEFAULT '0' COMMENT '投诉单id：qms系统触红字段使用',
    KEY `routeId` (`routeId`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='处罚产品列表';
 

DROP TABLE IF EXISTS qc_send_mail_config;
CREATE TABLE `qc_send_mail_config` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `mailType` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '配置类型',
   `sendAddrs` VARCHAR(1000) NOT NULL DEFAULT ''  COMMENT '发送人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='发送邮件配置';
 
INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '发送人邮件配置', '', 'qc/sendMailConfig/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='系统管理');

INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson)
(SELECT id, '新增导入处罚单', 'addPunish',1, '陈海涛' FROM cm_resource WHERE NAME='处罚单管理列表');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '产品自动下线管理', '', 'qs/punishPrd/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量监管');


ALTER TABLE cm_rtx_remind  MODIFY COLUMN uid  TEXT COMMENT '接收人ID';
ALTER TABLE qs_substd_purchase_amt ADD groupDate date DEFAULT NULL COMMENT '出游日期';
ALTER TABLE qs_substd_purchase_amt ADD routeId bigint(20) DEFAULT 0 COMMENT '线路编号';
ALTER TABLE qs_substd_order_amt ADD routeId bigint(20) DEFAULT 0 COMMENT '线路编号';
