USE quality;

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '投诉质检单列表', '', 'qc/qcBill/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '内部处罚单列表', '', 'qc/innerPunish/queryPunishBill', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '外部处罚单列表', '', 'qc/outerPunish/queryPunishBill', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '常规质检任务', '', 'qc/qcBill/listNormalQcJob', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '投诉质检自动分单配置', '', 'qc/assignConfigCmp/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson) (SELECT id, '返回质检中', 'BACK2PROCESSING',1, '姜野' FROM cm_resource WHERE NAME='投诉质检单列表');
INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson) (SELECT id, '质检单状态条件按钮', 'MANAGER_QC_STATE',1, '姜野' FROM cm_resource WHERE NAME='投诉质检单列表');
INSERT INTO cm_resource(pid,NAME,url,menuFlag,addPerson) (SELECT id, 'rtx提醒列表', 'common/rtxRemind/list',1, '姜野' FROM cm_resource WHERE NAME='系统管理');


DROP TABLE IF EXISTS cm_product;
CREATE TABLE `cm_product` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `prdName` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '产品名称',
  `cateId` INT(11) NOT NULL DEFAULT '0' COMMENT '品类ID',
  `cateName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '品类',
  `subCateId` INT(11) NOT NULL DEFAULT '0' COMMENT '子品类ID',
  `subCateName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '子品类',
  `brandId` INT(11) NOT NULL DEFAULT '0' COMMENT '品牌ID',
  `brandName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '品牌',
  `productLineId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品线ID',
  `prdLineDestName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '产品线目的地',
  `destCateId` INT(11) NOT NULL DEFAULT '0' COMMENT '目的地大类ID',
  `destCateName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目的地大类Name',
  `businessUnitId` INT(11) NOT NULL DEFAULT '0' COMMENT '事业部ID',
  `businessUnitName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '事业部Name',
  `prdDepId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品部ID',
  `prdDepName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '产品部Name',
  `prdTeamId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品组ID',
  `prdTeamName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '产品组Name',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '产品经理',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '产品专员',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='产品';

DROP TABLE IF EXISTS cm_product_sync_task;
CREATE TABLE `cm_product_sync_task` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `prdId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `failTimes` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '失败次数',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='添加产品数据任务表';


DROP TABLE IF EXISTS cm_order_bill;
CREATE TABLE `cm_order_bill` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `prdId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `departCity` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '出发城市',
  `prdAdultPrice` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '产品成人价',
  `flightPrice` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '机票价格',
  `adultNum` INT(4) NOT NULL DEFAULT '0' COMMENT '出游成人数',
  `childNum` INT(4) NOT NULL DEFAULT '0' COMMENT '出游儿童数',
  `departDate` DATE COMMENT '出游日期',
  `returnDate` DATE COMMENT '归来日期',
  `salerId` INT(11) NOT NULL DEFAULT '0' COMMENT '客服专员ID',
  `salerName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '客服专员姓名',
  `salerManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '客服经理ID',
  `salerManagerName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '客服经理姓名',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '产品专员',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '产品经理',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单';
CREATE INDEX prdId ON cm_order_bill (prdId);


DROP TABLE IF EXISTS cm_order_sync_task;
CREATE TABLE `cm_order_sync_task` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `failTimes` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '失败次数',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='添加订单数据任务表';


DROP TABLE IF EXISTS cm_order_operator;
CREATE TABLE `cm_order_operator` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `operId` INT(11) NOT NULL DEFAULT '0' COMMENT '运营专员ID',
  `operName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '运营专员姓名',
  `managerId` INT(11) NOT NULL DEFAULT '0' COMMENT '运营经理ID',
  `managerName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '运营经理姓名',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单关联运营人员';
CREATE INDEX ordId ON cm_order_operator (ordId);


DROP TABLE IF EXISTS cm_order_agency;
CREATE TABLE `cm_order_agency` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `agencyId` INT(11) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `agencyName` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '供应商名称',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单关联供应商';
CREATE INDEX ordId ON cm_order_agency (ordId);


DROP TABLE IF EXISTS cm_complaint_bill;
CREATE TABLE `cm_complaint_bill` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `cmpLevel` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '投诉级别',
  `state` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '投诉处理状态',
  `stateName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '投诉处理状态名称',
  `finishTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '处理完成时间',
  `dealPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '处理人id',
  `dealPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '处理人',
  `indemnifyAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '对客赔偿总额',
  `claimAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '向供应商索赔总额',  
  `qualityToolAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '质量工具承担总额',
  `repVoucherAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '抵用券赔付总额',
  `giftAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '礼品赔付总额',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='投诉单';
CREATE INDEX ordId ON cm_complaint_bill (ordId);


DROP TABLE IF EXISTS cm_complaint_sync_task;
CREATE TABLE `cm_complaint_sync_task` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `cmpId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `failTimes` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '失败次数',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='添加投诉单数据任务表';


DROP TABLE IF EXISTS qc_qc_bill;
CREATE TABLE `qc_qc_bill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `prdId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品id',
  `groupDate` DATE COMMENT '团期',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单id',
  `cmpId` INT(11) NOT NULL DEFAULT '0' COMMENT '投诉单id',
  `jiraNum` VARCHAR(20) NOT NULL DEFAULT '' COMMENT 'JIRA单号',
  `groupFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '使用方（组别），1：投诉质检组，2：运营质检组，3：研发质检组',
  `qcTypeId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检类型id',
  `qcAffairSummary` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '质检事宜概述',
  `qcAffairDesc` TEXT NOT NULL COMMENT '质检事宜详述',
  `remark` TEXT NOT NULL COMMENT '备注',
  `lossAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '损失金额',
  `state` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '状态标志位，1：发起中，2：待分配，3：质检中，4：已完成，5：已撤销',
  `qcPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检员id',
  `qcPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检员姓名',
  `distribTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '分配时间',
  `finishTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '完成时间',
  `addPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '申请人id',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '申请人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检单';
CREATE INDEX prdId_groupDate ON qc_qc_bill (prdId, groupDate);
CREATE INDEX ordId ON qc_qc_bill (ordId);
CREATE INDEX cmpId ON qc_qc_bill (cmpId);
CREATE INDEX jiraNum ON qc_qc_bill (jiraNum);


DROP TABLE IF EXISTS qc_quality_problem;
CREATE TABLE `qc_quality_problem` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单id',
  `qptId` INT(11) NOT NULL DEFAULT '0' COMMENT '质量问题类型id',
  `description` TEXT NOT NULL COMMENT '问题描述',
  `verifyBasis` TEXT NOT NULL COMMENT '核实依据',
  `impAdvice` TEXT NOT NULL COMMENT '改进建议',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质量问题';
CREATE INDEX qcId ON qc_quality_problem (qcId);

DROP TABLE IF EXISTS qc_inner_resp_bill;
CREATE TABLE `qc_inner_resp_bill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qpId` INT(11) NOT NULL DEFAULT '0' COMMENT '质量问题id',
  `respPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任人ID',
  `respPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任人Name',
  `depId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任部门ID',
  `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '责任岗位ID',
  `respReason` TEXT NOT NULL COMMENT '责任事由',
  `impPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '改进人ID',
  `impPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '改进人Name',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='内部责任单';
CREATE INDEX qpId ON qc_inner_resp_bill (qpId);

DROP TABLE IF EXISTS qc_outer_resp_bill;
CREATE TABLE `qc_outer_resp_bill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qpId` INT(11) NOT NULL DEFAULT '0' COMMENT '质量问题id',
  `agencyId` INT(11) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `agencyName` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '供应商名称',
  `respReason` TEXT NOT NULL COMMENT '责任事由',
  `impPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '改进人ID',
  `impPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '改进人Name',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='外部责任单';
CREATE INDEX qpId ON qc_outer_resp_bill (qpId);


DROP TABLE IF EXISTS qc_inner_punish_bill;
CREATE TABLE `qc_inner_punish_bill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单id',
  `punishPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '被处罚人ID',
  `punishPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '被处罚人Name',
  `depId` INT(11) NOT NULL DEFAULT '0' COMMENT '关联部门ID',
  `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '关联岗位ID',
  `punishReason` TEXT NOT NULL COMMENT '处罚事由',
  `scorePunish` INT(4) NOT NULL DEFAULT '0' COMMENT '记分处罚（分）',
  `economicPunish` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '经济处罚（元）',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='内部处罚单';
CREATE INDEX qcId ON qc_inner_punish_bill (qcId);


DROP TABLE IF EXISTS qc_inner_punish_basis;
CREATE TABLE `qc_inner_punish_basis` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ipbId` INT(11) NOT NULL DEFAULT '0' COMMENT '内部处罚单id',
  `psId` INT(11) NOT NULL DEFAULT '0' COMMENT '处罚标准ID',
  `execFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '执行标记，0：否，1：是',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='内部处罚依据';
CREATE INDEX ipbId ON qc_inner_punish_basis (ipbId);


DROP TABLE IF EXISTS qc_outer_punish_bill;
CREATE TABLE `qc_outer_punish_bill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单id',
  `agencyId` INT(11) NOT NULL DEFAULT '0' COMMENT '供应商ID',
  `agencyName` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '供应商名称',
  `punishReason` TEXT NOT NULL COMMENT '处罚事由',
  `scorePunish` INT(4) NOT NULL DEFAULT '0' COMMENT '记分处罚（分）',
  `economicPunish` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '经济处罚（元）',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='外部处罚单';
CREATE INDEX qcId ON qc_outer_punish_bill (qcId);


DROP TABLE IF EXISTS qc_outer_punish_basis;
CREATE TABLE `qc_outer_punish_basis` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `opbId` INT(11) NOT NULL DEFAULT '0' COMMENT '外部处罚单id',
  `psId` INT(11) NOT NULL DEFAULT '0' COMMENT '处罚标准ID',
  `execFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '执行标记，0：否，1：是',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='外部处罚依据';
CREATE INDEX opbId ON qc_outer_punish_basis (opbId);


DROP TABLE IF EXISTS `qc_quality_problem_attach`;
CREATE TABLE `qc_quality_problem_attach` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qpId` INT(11) NOT NULL DEFAULT '0' COMMENT '质量问题ID',
  `path` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '附件路径',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '附件名称',
  `type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '类型',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质量问题附件表';

DROP TABLE IF EXISTS qc_qc_note;
CREATE TABLE `qc_qc_note` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `qcBillId` INT(11) NOT NULL COMMENT '关联质检单id',
   `addPerson` VARCHAR(10) NOT NULL COMMENT '添加人姓名',
   `content` TEXT NOT NULL COMMENT '备忘详情',
   `addTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检备注表';

DROP TABLE IF EXISTS cm_rtx_remind;
CREATE TABLE `cm_rtx_remind` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `uid` SMALLINT(5) NOT NULL DEFAULT '0' COMMENT '接收人ID',
   `title` VARCHAR(80) NOT NULL DEFAULT '无'  COMMENT '标题',
   `content` VARCHAR(2000) DEFAULT '无' COMMENT '内容',
   `sendTime` DATETIME DEFAULT '0000-00-00 00:00:00'  COMMENT '发送时间',
   `failTimes` SMALLINT(5) NOT NULL DEFAULT '0' COMMENT '失败次数',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='rtx提醒表';

DROP TABLE IF EXISTS qc_mail_config;
CREATE TABLE `qc_mail_config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `reAddrs` VARCHAR(800) NOT NULL DEFAULT '' COMMENT '发送人',
  `ccAddrs` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '抄送人',
  `respType` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '质量问题级别',
  `cmpLevel` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '投诉级别',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检邮件发送范围配置表';

ALTER TABLE uc_department ADD COLUMN `cmpQcUseFlag` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '投诉质检使用标识，0：不使用，1：使用';

DROP TABLE IF EXISTS qc_assign_config_cmp;
CREATE TABLE `qc_assign_config_cmp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qcPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检员id',
  `qcPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检员姓名',
  `noOrderFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否处理无订单质检，0：否，1：是',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='投诉质检自动分单配置';


DROP TABLE IF EXISTS qc_person_dockdep_cmp;
CREATE TABLE `qc_person_dockdep_cmp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `qcPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检员id',
  `qcPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检员姓名',
  `dockdepId` INT(11) NOT NULL DEFAULT '0' COMMENT '对接部门id',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='投诉质检员对接部门配置（用于自动分单）';
