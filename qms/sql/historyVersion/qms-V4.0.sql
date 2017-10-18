USE quality;

ALTER TABLE qc_qc_bill ADD INDEX state_groupFlag(state, groupFlag);

 ALTER TABLE  qc_point_attach
 ADD COLUMN `billType` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '关联单据类型，1：质检单，2：投诉改进报告';

DROP TABLE IF EXISTS qs_cmp_improve;
CREATE TABLE `qs_cmp_improve` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `cmpId` int(11) NOT NULL DEFAULT '0' COMMENT '投诉单id',
   `ordId` int(11) NOT NULL DEFAULT '0' COMMENT '订单id',
   `prdId` int(11) NOT NULL DEFAULT '0' COMMENT '产品id',
   `impPersonId` int(11) NOT NULL DEFAULT '0' COMMENT '改进人id',
   `impPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '改进人',
   `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态标志位，1: 待定责,2: 处理中,3:待确认 ,4: 已完结,',
   `cmpAffair` text COMMENT '投诉事宜',
   `improvePoint` text COMMENT '改进点',
   `isRespFlag` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否有责标志位， 0：有责，1：无责， 2：空',
   `otherPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '其他改进人',
   `otherAgencyName` varchar(50) NOT NULL DEFAULT '' COMMENT '有责任的供应商名',
   `improveMethod` text COMMENT '改进措施',
   `improveFinTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预计改进完成时间',
   `improveResult` text COMMENT '预计改进效果',
   `remark` text COMMENT '备注',
   `handleEndTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '流程处理到期时间(添加时间+5工作日)',
   `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   `handlePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '处理人',
   `addPerson` varchar(20) NOT NULL DEFAULT '' COMMENT '申请人',
   `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` varchar(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `firstDepId` int(11) NOT NULL DEFAULT '0' COMMENT '改进人一级部门id',
   `firstDep` varchar(50) NOT NULL DEFAULT '' COMMENT '改进人一级部门',
   `secondDepId` int(11) NOT NULL DEFAULT '0' COMMENT '改进人二级部门id',
   `secondDep` varchar(50) NOT NULL DEFAULT '' COMMENT '改进人二级部门',
   `oaId` varchar(20) NOT NULL DEFAULT '' COMMENT 'oa 表单流程ID',
   PRIMARY KEY (`id`),
   KEY `cmpId` (`cmpId`),
   KEY `prdId` (`prdId`),
   KEY `ordId` (`ordId`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投诉改进报告单';