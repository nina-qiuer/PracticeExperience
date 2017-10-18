USE quality;

TRUNCATE qs_substd_purchase_amt;
TRUNCATE qs_substd_contract_append_amt;
TRUNCATE qs_substd_order_amt;
TRUNCATE qs_substd_refund_amt;

ALTER TABLE qc_qc_bill
ADD evidence TEXT NOT NULL COMMENT '证据',
ADD userFlag TINYINT(4) NOT NULL DEFAULT '0' COMMENT '人工发起，1：人工发起，0：非人工发起';

DROP TABLE IF EXISTS qc_point_attach;
CREATE TABLE `qc_point_attach` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单ID',
   `path` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '附件路径',
   `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '附件名称',
   `type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '类型',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='质检点附件表';
