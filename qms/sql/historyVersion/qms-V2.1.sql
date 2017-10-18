USE quality;
INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, 'KCP管理', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, 'KCP类型配置', '', 'qs/kcpType/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='KCP管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, 'KCP来源配置', '', 'qs/kcpSource/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='KCP管理');


DROP TABLE IF EXISTS qs_kcp_type;
CREATE TABLE `qs_kcp_type` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '名称',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='KCP类型配置表';


DROP TABLE IF EXISTS qs_kcp_source;
CREATE TABLE `qs_kcp_source` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '名称',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='KCP来源配置表';
	       		 
