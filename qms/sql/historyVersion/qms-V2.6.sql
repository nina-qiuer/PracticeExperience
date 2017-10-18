USE quality;

DROP TABLE IF EXISTS qs_quality_problem_report;
CREATE TABLE `qs_quality_problem_report` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单ID',
  `qpFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '内外问题标识，0：无，1：内部，2：外部',
  `touchRedFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '触红标识，0：不触红，1：触红',
  `firstQpTypeId`  INT(11) NOT NULL DEFAULT '0' COMMENT '一级问题类型ID',
  `firstQpTypeName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '一级问题类型',
  `secondQpTypeId`  INT(11) NOT NULL DEFAULT '0' COMMENT '二级问题类型ID',
  `secondQpTypeName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '二级问题类型',
  `thirdQpTypeId`  INT(11) NOT NULL DEFAULT '0' COMMENT '三级问题类型ID',
  `thirdQpTypeName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '三级问题类型',
  `firstDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级责任部门ID',
  `firstDepName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '一级责任部门',
  `twoDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级责任部门ID',
  `twoDepName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '二级责任部门',
  `threeDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级责任部门ID',
  `threeDepName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '三级责任部门',
  `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任岗位ID',
  `jobName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任岗位Name',
  `respPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任人ID',
  `respPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任人Name',
  `agencyId` INT(11) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `agencyName` VARCHAR(50) NOT NULL DEFAULT ''  COMMENT '供应商',
  `year`   INT(11) NOT NULL DEFAULT '0' COMMENT '年',
  `quarter`  INT(11) NOT NULL DEFAULT '0' COMMENT '季',
  `month`  INT(11) NOT NULL DEFAULT '0' COMMENT '月',
  `week`  INT(11) NOT NULL DEFAULT '0' COMMENT '周',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质量问题报表';



ALTER TABLE qs_substd_order_amt 
ADD productType VARCHAR(50) NOT NULL DEFAULT '' COMMENT '产品类型',
ADD destName VARCHAR(50) NOT NULL DEFAULT '' COMMENT '目的地大类',
ADD prdId INT(11) NOT NULL DEFAULT '0' COMMENT '产品ID';



ALTER TABLE qs_quality_cost_ledger 
ADD `prdLineId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品线ID',
ADD `year` INT(11) NOT NULL DEFAULT '0' COMMENT '年',
ADD `yearQuarter` INT(11) NOT NULL DEFAULT '0' COMMENT '季',
ADD `yearMonth` INT(11) NOT NULL DEFAULT '0' COMMENT '月',
ADD `yearWeek` INT(11) NOT NULL DEFAULT '0' COMMENT '周';

ALTER TABLE qs_quality_cost_auxaccount 
ADD `prdLineId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品线ID',
ADD `year` INT(11) NOT NULL DEFAULT '0' COMMENT '年',
ADD `yearQuarter` INT(11) NOT NULL DEFAULT '0' COMMENT '季',
ADD `yearMonth` INT(11) NOT NULL DEFAULT '0' COMMENT '月',
ADD `yearWeek` INT(11) NOT NULL DEFAULT '0' COMMENT '周';

ALTER TABLE qc_qc_bill 
ADD `faultSource` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:内部转单  2:高频问题 3:紧急上线 4:RT群反馈 5:微信群反馈 6:其他';


INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量问题报表', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');
 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量问题列表', '', 'report/qualityProblemReport/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量问题报表');
 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量问题占比', '', 'report/qualityProblemReport/qualityProblemProportion', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量问题报表');
 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量问题趋势', '', 'report/qualityProblemReport/qualityProblemTrend', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量问题报表');
 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量问题排名', '', 'report/qualityProblemReport/qualityProblemRank', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量问题报表');

