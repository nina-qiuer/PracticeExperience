USE quality;

 
 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '周期完成率报表', '', 'report/completionRate/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

 ALTER TABLE qc_outer_resp_bill 
 ADD respPersonId int(11) NOT NULL DEFAULT '0' COMMENT '责任人ID',
 ADD respPersonName varchar(20) NOT NULL DEFAULT '' COMMENT '责任人Name',
 ADD depId int(11) NOT NULL DEFAULT '0' COMMENT '责任部门ID';
 

DROP TABLE IF EXISTS qc_operation_log;
CREATE TABLE `qc_operation_log` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '关联质检单号',
   `dealPeople` INT(11) NOT NULL DEFAULT '0' COMMENT '操作人人',
   `dealPeopleName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '操作人姓名',
   `flowName` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '事件',
   `dealDepart` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '处理岗位',
   `content` VARCHAR(3000) NOT NULL DEFAULT '' COMMENT '操作信息',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   KEY `qcId` (`qcId`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检操作日志表';