USE quality;

DROP TABLE IF EXISTS qs_target_config;
CREATE TABLE `qs_target_config` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
   `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
   `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
   `oneTargetValue` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '第一季度目标值',
   `twoTargetValue` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '第二季度目标值',
   `threeTargetValue` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '第三季度目标值',
   `fourTargetValue` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '第四季度目标值',
   `year` INT(11) NOT NULL DEFAULT '0' COMMENT '年',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='目标值配置表';


DROP TABLE IF EXISTS qs_product_satisfy ;
CREATE TABLE `qs_product_satisfy` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
   `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
   `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
   `prdManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品经理',
   `ordNum` int(11) NOT NULL DEFAULT '0' COMMENT '订单数',
   `cmpNum` int(11) NOT NULL DEFAULT '0' COMMENT '投诉单数',
   `commentNum` int(11) NOT NULL DEFAULT '0' COMMENT '点评量',
   `commentScore` int(11) NOT NULL DEFAULT '0' COMMENT '点评分数和',
   `year` int(11) NOT NULL DEFAULT '0' COMMENT '年',
   `yearQuarter` int(11) NOT NULL DEFAULT '0' COMMENT '季',
   `yearMonth` int(11) NOT NULL DEFAULT '0' COMMENT '月',
   `statisticDate` date DEFAULT NULL COMMENT '统计日期',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='综合满意度达成率监控';

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '综合满意度达成率监控', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '目标值配置列表', '', 'qs/targetConfig/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='综合满意度达成率监控');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '综合满意度监控列表', '', 'qs/compSatisRate/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='综合满意度达成率监控');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '综合满意度监控', '', 'qs/compSatisRate/businessUnitTrend', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='综合满意度达成率监控');

INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson)
(SELECT id, '满意度预警', 'satisfyAlert',1, '陈海涛' FROM cm_resource WHERE NAME='综合满意度监控列表');

ALTER TABLE cm_order_bill ADD groundPrice DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '地接价格';
ALTER TABLE cm_order_bill ADD avlGroundPrice DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '平均地接价格';

ALTER TABLE cm_order_agency ADD agencyAccessTime varchar(100) NOT NULL DEFAULT '' COMMENT '供应商准入时间';
ALTER TABLE cm_order_agency ADD agencyOwner varchar(50) NOT NULL DEFAULT '' COMMENT '供应商owner';
ALTER TABLE cm_order_agency ADD signCompany varchar(100) NOT NULL DEFAULT '' COMMENT '签约公司';