USE quality;


INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量监管', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='ROOT');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '监控标准配置', '', 'qs/destcateStandard/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '销售期长监控', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '销售期长不合格产品列表', '', 'qs/substdProductSnapshot/listSubstdSpl', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='销售期长监控');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '销售期长不合格问题发生率', '', 'qs/substdProductSnapshot/showSubstdSplRate', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='销售期长监控');


INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '团期丰富度监控', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '团期丰富度不合格产品列表', '', 'qs/substdProductSnapshot/listSubstdGr', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='团期丰富度监控');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '团期丰富度不合格问题发生率', '', 'qs/substdProductSnapshot/showSubstdGrRate', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='团期丰富度监控');


INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '非独立成团牛人专线监控', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '非独立成团牛人专线列表', '', 'qs/substdProductSnapshot/listUnAloneGroup', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='非独立成团牛人专线监控');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '非独立成团牛人专线问题发生率', '', 'qs/substdProductSnapshot/showUnAloneGroupRate', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='非独立成团牛人专线监控');


INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '出团通知发送及时性监控', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '出团通知超时订单列表', '', 'qs/substdOrderSnapshot/listNoticeTimeout', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='出团通知发送及时性监控');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '出团通知超时率', '', 'qs/substdOrderSnapshot/showNoticeTimeoutRate', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='出团通知发送及时性监控');


DROP TABLE IF EXISTS cm_mail_task;
CREATE TABLE `cm_mail_task` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `reAddrs` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '收件人',
  `ccAddrs` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '抄送人',
  `subject` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '邮件主题',
  `content` TEXT NOT NULL COMMENT '邮件内容',
  `sendTimes` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '发送次数',
  `addPersonRoleId` INT(11) NOT NULL DEFAULT '0' COMMENT '添加人角色ID',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='邮件任务表';


DROP TABLE IF EXISTS qs_destcate_standard;
CREATE TABLE `qs_destcate_standard` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `destCate` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目的地大类',
  `groupRichness` DECIMAL(10,3) NOT NULL DEFAULT '0.000' COMMENT '团期丰富度',
  `noticeTimeLimit` INT(4) NOT NULL DEFAULT '0' COMMENT '出团通知时限（天）',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='目的地大类相关监控标准';

INSERT INTO qs_destcate_standard (destCate, groupRichness, noticeTimeLimit) VALUES ('出境长线', 0.09, 2);
INSERT INTO qs_destcate_standard (destCate, groupRichness, noticeTimeLimit) VALUES ('出境短线', 0.28, 2);
INSERT INTO qs_destcate_standard (destCate, groupRichness, noticeTimeLimit) VALUES ('出境当地参团', 0.64, 2);
INSERT INTO qs_destcate_standard (destCate, groupRichness, noticeTimeLimit) VALUES ('国内长线', 0.61, 1);
INSERT INTO qs_destcate_standard (destCate, groupRichness, noticeTimeLimit) VALUES ('周边', 0.75, 1);
INSERT INTO qs_destcate_standard (destCate, groupRichness, noticeTimeLimit) VALUES ('国内当地参团', 0.69, 1);


DROP TABLE IF EXISTS qs_substd_product_snapshot;
CREATE TABLE `qs_substd_product_snapshot` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `prdId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `prdName` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '产品名称',
  `destCate` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目的地大类',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品经理',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品专员',
  `statisticDate` DATE COMMENT '统计日期',
  `furthestGroupDate` DATE COMMENT '最远团期',
  `salesPeriodLength` INT(4) NOT NULL DEFAULT '0' COMMENT '销售期长（天）',
  `stdSalesPeriodLength` INT(4) NOT NULL DEFAULT '0' COMMENT '标准销售期长（天）',
  `splDifValue` INT(4) NOT NULL DEFAULT '0' COMMENT '销售期长差值（天）',
  `surplusGroupNum` INT(4) NOT NULL DEFAULT '0' COMMENT '当前剩余团期数量',
  `groupRichness` DECIMAL(10,3) NOT NULL DEFAULT '0.000' COMMENT '团期丰富度',
  `stdGroupRichness` DECIMAL(10,3) NOT NULL DEFAULT '0.000' COMMENT '标准团期丰富度',
  `grDifValue` DECIMAL(10,3) NOT NULL DEFAULT '0.000' COMMENT '团期丰富度差值',
  `aloneGroupFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '独立成团标识位，0：否，1：是',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='不合格产品快照表';


DROP TABLE IF EXISTS qs_substd_order_snapshot;
CREATE TABLE `qs_substd_order_snapshot` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `prdId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `prdName` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '产品名称',
  `destCate` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目的地大类',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品经理',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品专员',
  `customerManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '客服经理ID',
  `customerManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '客服经理',
  `customerId` INT(11) NOT NULL DEFAULT '0' COMMENT '客服专员ID',
  `customer` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '客服专员',
  `statisticDate` DATE COMMENT '统计日期',
  `departDate` DATE COMMENT '出游日期',
  `noticeSendDate` DATE COMMENT '出团通知发送日期',
  `timeOutDays` INT(4) NOT NULL DEFAULT '0' COMMENT '超时天数',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='不合格订单快照表';


DROP TABLE IF EXISTS qs_dep_prdnum_snapshot;
CREATE TABLE `qs_dep_prdnum_snapshot` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `statisticDate` DATE COMMENT '统计日期',
  `substdSplPrdNum` INT(4) NOT NULL DEFAULT '0' COMMENT '销售期长不合格产品数量',
  `substdGrPrdNum` INT(4) NOT NULL DEFAULT '0' COMMENT '团期丰富度不合格产品数量',
  `unAgPrdNum` INT(4) NOT NULL DEFAULT '0' COMMENT '非独立成团牛人专线产品数量',
  `onsellPrdNum` INT(4) NOT NULL DEFAULT '0' COMMENT '在售产品数量',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='部门产品数量快照表';


DROP TABLE IF EXISTS qs_dep_ordnum_snapshot;
CREATE TABLE `qs_dep_ordnum_snapshot` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `statisticDate` DATE COMMENT '统计日期',
  `noticeTimeOutOrdNum` INT(4) NOT NULL DEFAULT '0' COMMENT '出团通知发送超时订单数量',
  `signedOrdNum` INT(4) NOT NULL DEFAULT '0' COMMENT '已签约未出游订单数量',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='部门订单数量快照表';


DROP TABLE IF EXISTS qs_product_ext;
CREATE TABLE `qs_product_ext` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `prdName` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '产品名称',
  `destCate` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目的地大类',
  `aloneGroupFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '独立成团标识位，0：否，1：是',
  `surplusGroupNum` INT(4) NOT NULL DEFAULT '0' COMMENT '当前剩余团期数量',
  `furthestGroupDate` DATE COMMENT '最远团期',
  `salesPeriodLength` INT(4) NOT NULL DEFAULT '0' COMMENT '销售期长（天）',
  `stdSalesPeriodLength` INT(4) NOT NULL DEFAULT '0' COMMENT '标准销售期长（天）',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品经理',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品专员',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='产品扩展信息表（由BI每日同步数据）';


DROP TABLE IF EXISTS qs_order_bill_ext;
CREATE TABLE `qs_order_bill_ext` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `prdId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `prdName` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '产品名称',
  `destCate` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '目的地大类',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品经理',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品专员',
  `customerManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '客服经理ID',
  `customerManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '客服经理',
  `customerId` INT(11) NOT NULL DEFAULT '0' COMMENT '客服专员ID',
  `customer` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '客服专员',
  `departDate` DATE COMMENT '出游日期',
  `noticeSendDate` DATE COMMENT '出团通知发送日期',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单扩展信息表（由BI每日同步数据）';




