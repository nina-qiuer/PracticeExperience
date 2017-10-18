USE quality;
--处罚产品列表修改  QMS-378
ALTER TABLE  qs_punish_prd 
ADD COLUMN `realOffLineCount` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '实际下线次数',
ADD COLUMN `passOperPerson` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '放过操作人',
ADD COLUMN `passOperTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '放过操作时间',
ADD COLUMN `remark` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '情况说明';

-- 投诉质检单表索引创建    QMS-373
ALTER TABLE qc_qc_bill 
ADD INDEX qcPersonId(qcPersonId);

