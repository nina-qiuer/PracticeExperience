USE quality;

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '事业部投诉质检量统计', '', 'report/cmpQcBUStat/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, 'KCP申请', '', 'qs/kcp/apply', 1, 0, 0, '王明方' FROM cm_resource WHERE NAME='KCP管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, 'KCP列表', '', 'qs/kcp/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='KCP管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '列表', '', '', 0, 0, 1, '王明方' FROM cm_resource WHERE NAME='KCP列表');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '初审按钮', 'KCP_FIRST_AUDIT', '', 0, 0, 1, '王明方' FROM cm_resource WHERE NAME='KCP列表');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '终审按钮', 'KCP_LAST_AUDIT', '', 0, 0, 1, '王明方' FROM cm_resource WHERE NAME='KCP列表');

DROP TABLE IF EXISTS qs_kcp;
CREATE TABLE `qs_kcp` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `name` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '名称',
  `description` TEXT NOT NULL COMMENT '问题描述',
  `example` TEXT NOT NULL COMMENT '举例说明',
  `analysis` TEXT NOT NULL COMMENT '问题分析',
  `kcpTypeId` INT(11) NOT NULL DEFAULT '0' COMMENT 'kcp类型ID',
  `kcpSourceId` INT(11) NOT NULL DEFAULT '0' COMMENT 'kcp来源ID',
  `importantFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '重要程度标识位，1：普通，2：重要，3：非常重要',
  `state` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '状态，0：发起中，1：待初审，2：待终审，3：审核通过',
  `audit1Id` INT(11) NOT NULL DEFAULT '0' COMMENT '初审人ID',
  `audit1Name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '初审人姓名',
  `audit2Id` INT(11) NOT NULL DEFAULT '0' COMMENT '终审人ID',
  `audit2Name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '终审人姓名',
  `auditTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核通过时间',
  `addPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '申请人ID',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '申请人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='KCP';
