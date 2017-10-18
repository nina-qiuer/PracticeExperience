USE quality;

DROP TABLE IF EXISTS `qc_punish_report`;
 CREATE TABLE `qc_punish_report` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单ID',
   `innerPunishId` INT(11) NOT NULL DEFAULT '0' COMMENT '内部处罚单ID',
   `scorePunish` INT(4) NOT NULL DEFAULT '0' COMMENT '记分处罚（分）',
   `economicPunish` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '经济处罚（元）',
   `firstQcTypeId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级质检类型ID',
   `firstQcTypeName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '一级质检类型',
   `secondQcTypeId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级质检类型ID',
   `secondQcTypeName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '二级质检类型',
   `thirdQcTypeId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级质检类型ID',
   `thirdQcTypeName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '三级质检类型',
   `firstDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级关联部门ID',
   `firstDepName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '一级关联部门',
   `twoDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级关联部门ID',
   `twoDepName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '二级关联部门',
   `threeDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级关联部门ID',
   `threeDepName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '三级关联部门',
   `firstQcGroupId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级质检组ID',
   `firstQcGroupName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '一级质检组',
   `twoQcGroupId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级质检组ID',
   `twoQcGroupName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '二级质检组',
   `threeQcGroupId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级质检组ID',
   `threeQcGroupName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '三级质检组',
   `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '关联岗位ID',
   `jobName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '关联岗位Name',
   `punPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '被处罚人ID',
   `punishPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '被处罚人Name',
   `qcPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检人ID',
   `qcPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检人Name',
   `year` INT(11) NOT NULL DEFAULT '0' COMMENT '年',
   `yearQuarter` INT(11) NOT NULL DEFAULT '0' COMMENT '季',
   `yearMonth` INT(11) NOT NULL DEFAULT '0' COMMENT '月',
   `yearWeek` INT(11) NOT NULL DEFAULT '0' COMMENT '周',
   `qcFinishTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '质检完成时间',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='处罚单数据切片表';
 
 DROP TABLE IF EXISTS `qc_punish_segment_task`;
 CREATE TABLE `qc_punish_segment_task` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `dataId` INT(11) NOT NULL DEFAULT '0' COMMENT '数据id',
   `taskType` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '任务类型  1：数据切片添加  2 ：数据切片删除',
   `failedTimes` INT(11) NOT NULL DEFAULT '0' COMMENT '失败次数',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='处罚单数据切片任务表';

 
INSERT INTO 
qc_punish_segment_task(dataId, taskType)
SELECT id, 1
FROM qc_qc_bill
WHERE state = 4
 AND finishTime != '0000-00-00 00:00:00'
  AND delFlag = 0;
 
 
