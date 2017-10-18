USE quality;

DROP TABLE IF EXISTS qc_influence_system;
CREATE TABLE `qc_influence_system` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '研发质检单号',
   `influenceSystem` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '影响系统',
   `addPerson` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '添加人姓名',
   `addTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检单关联影响系统';