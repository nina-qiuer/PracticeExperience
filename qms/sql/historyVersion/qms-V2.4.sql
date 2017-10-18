USE quality;

DROP TABLE IF EXISTS qs_quality_cost_ledger;
CREATE TABLE `qs_quality_cost_ledger` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ispId` INT(11) NOT NULL DEFAULT '0' COMMENT '内部责任单ID',
  `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单ID',
  `cmpId` INT(11) NOT NULL DEFAULT '0' COMMENT '投诉单ID',
  `firstCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '一级成本科目',
  `twoCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '二级成本科目',
  `threeCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '三级成本科目',
  `fourCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '四级成本科目(预留)',
  `firstDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级责任部门ID',
  `firstDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '一级责任部门',
  `twoDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级责任部门ID',
  `twoDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '二级责任部门',
  `threeDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级责任部门ID',
  `threeDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '三级责任部门',
  `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任岗位ID',
  `jobName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任岗位Name',
  `respPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任人ID',
  `respPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任人Name',
  `respRate` FLOAT(18,2) NOT NULL DEFAULT '0.00' COMMENT '责任占比',
  `qualityCost` FLOAT(18,2) NOT NULL DEFAULT '0.00' COMMENT '质量成本',
  `dealPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '处理人ID',
  `dealPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '处理人',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质量成本台账';


DROP TABLE IF EXISTS qs_quality_cost_auxaccount;
CREATE TABLE `qs_quality_cost_auxaccount` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ispId` INT(11) NOT NULL DEFAULT '0' COMMENT '内部责任单ID',
  `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单ID',
  `cmpId` INT(11) NOT NULL DEFAULT '0' COMMENT '投诉单ID',
  `firstCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '一级成本科目',
  `twoCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '二级成本科目',
  `threeCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '三级成本科目',
  `fourCostAccount` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '四级成本科目(预留)',
  `firstDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级责任部门ID',
  `firstDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '一级责任部门',
  `twoDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级责任部门ID',
  `twoDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '二级责任部门',
  `threeDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级责任部门ID',
  `threeDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '三级责任部门',
  `respRate` FLOAT(18,2) NOT NULL DEFAULT '0.00' COMMENT '责任占比',
  `qualityCost` FLOAT(18,2) NOT NULL DEFAULT '0.00' COMMENT '质量成本',
  `dealPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '处理人ID',
  `dealPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '处理人',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
    KEY `dep` (`firstDepId`,`twoDepId`,`threeDepId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质量成本辅助账';


DROP TABLE IF EXISTS qs_resp_department;
CREATE TABLE `qs_resp_department` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `firstDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '一级责任部门ID',
  `firstDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '一级责任部门',
  `twoDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '二级责任部门ID',
  `twoDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '二级责任部门',
  `threeDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '三级责任部门ID',
  `threeDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '三级责任部门',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
  KEY `dep` (`firstDepId`,`twoDepId`,`threeDepId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='责任部门辅助表';

ALTER TABLE cm_mail_task MODIFY `subject` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '邮件主题';

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量成本管理', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量成本台账', '', 'qs/costLedger/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量成本管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量成本辅助账', '', 'qs/costAuxAccount/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量成本管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '责任部门维护', '', 'qs/respDep/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量成本管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '研发质检单查询列表', '', 'qc/resDevQcBill/devList', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '运营质检', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '运营质检列表', '', 'qc/operateQcBill/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='运营质检');


