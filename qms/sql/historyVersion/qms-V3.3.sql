USE quality;

DROP TABLE IF EXISTS qc_relation_bill;
CREATE TABLE `qc_relation_bill` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `qcId` INT(11) NOT NULL DEFAULT '0'  COMMENT '投诉质检单号',
   `devId` INT(11) NOT NULL DEFAULT '0' COMMENT '研发质检单号',
   `cmpId` INT(11) NOT NULL DEFAULT '0' COMMENT '投诉单ID',
   `qcPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检员',
   `cmpTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '投诉时间',
   `indemnifyAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '对客赔偿',   
   `claimAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '供应商赔偿',   
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `flag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '关联标识位，0：未关联，1：已关联，2：已关闭',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
 )  ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检关联单';
 
 
 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '投诉单研发质检关联列表', '', 'qc/qcBillRelation/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

ALTER TABLE cm_complaint_bill ADD qcFlag TINYINT(4) NOT NULL DEFAULT '1' COMMENT '质检标识，1:投诉质检 2：研发质检 3：双方质检';