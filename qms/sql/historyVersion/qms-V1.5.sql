USE quality;


INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '采购单添加超时监控', '', 'qs/substdPurchaseAmt/addTimeout', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '跨月添加采购单监控', '', 'qs/substdPurchaseAmt/addDiffMonth', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '非现付采购单监控', '', 'qs/substdPurchaseAmt/substdNonpay', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '订单负采购监控', '', 'qs/substdPurchaseAmt/negativePurchase', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '订单负利润监控', '', 'qs/substdPurchaseAmt/negativeProfit', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '跨月添加合同增补单监控', '', 'qs/substdContractAppendAmt/addDiffMonth', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '合同价异常监控', '', 'qs/substdOrderAmt/contractAmountAbnormal', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '订单收款超时监控', '', 'qs/substdOrderAmt/collectTimeout', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '订单超额退款监控', '', 'qs/substdRefundAmt/beyondRefund', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量监管');


DROP TABLE IF EXISTS qs_substd_purchase_amt;
CREATE TABLE `qs_substd_purchase_amt` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `purchaseId` INT(11) NOT NULL DEFAULT '0' COMMENT '采购单ID',
  `addDate` DATE COMMENT '添加日期',
  `price` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '金额',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '类型',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `signDate` DATE COMMENT '签约日期',
  `backDate` DATE COMMENT '出游归来日期',
  `payFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否现付，0：否，1：是',
  `monthTotalAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '当月添加采购单总额',
  `totalAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '采购单总额',
  `contractAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '合同总额',
  `timeOutFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否超时，0：否，1：是',
  `diffMonthFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否跨月，0：否，1：是',
  `businessUnit` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '事业部',
  `prdDep` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品部',
  `prdTeam` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品组',
  `prdManagerId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品经理ID',
  `prdManager` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品经理',
  `producterId` INT(11) NOT NULL DEFAULT '0' COMMENT '产品专员ID',
  `producter` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '产品专员',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='不合格采购单快照表';


DROP TABLE IF EXISTS qs_substd_contract_append_amt;
CREATE TABLE `qs_substd_contract_append_amt` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `caId` INT(11) NOT NULL DEFAULT '0' COMMENT '合同增补单ID',
  `signDate` DATE COMMENT '订单签约日期',
  `addDate` DATE COMMENT '合同增补单添加日期',
  `price` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '合同增补单金额',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '合同增补单添加人',
  `monthTotalAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '当月添加合同增补单总额',
  `diffMonthFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否跨月，0：否，1：是',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
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
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='不合格合同增补单快照表';


DROP TABLE IF EXISTS qs_substd_order_amt;
CREATE TABLE `qs_substd_order_amt` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `statisticDate` DATE COMMENT '统计日期',
  `signDate` DATE COMMENT '签约日期',
  `backDate` DATE COMMENT '归来日期',
  `collectionAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '实收总额',
  `validAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '应收总额',
  `nonCollectionAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '未收总额',
  `contractAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '合同总额',
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
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='不合格订单快照表';


DROP TABLE IF EXISTS qs_substd_refund_amt;
CREATE TABLE `qs_substd_refund_amt` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `refundId` INT(11) NOT NULL DEFAULT '0' COMMENT '退款单ID',
  `refundNum` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '退款单编号',
  `addDate` DATE COMMENT '退款单添加日期',
  `refundType` INT(11) NOT NULL DEFAULT '0' COMMENT '退款类型，1：现金，2：旅游券',
  `refundAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '退款金额',
  `ordId` INT(11) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `cashRefundAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '现金退款总额',
  `cashCollectionAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '现金收款总额',
  `cashBeyondAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '现金超退总额',
  `orderRefundAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '订单退款总额',
  `orderCollectionAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '订单收款总额',
  `orderBeyondAmount` FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '订单超退总额',
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
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='不合格退款单快照表';

ALTER TABLE qc_inner_punish_bill ADD `relatedFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '连带责任标识位，0：否，1：是';

ALTER TABLE qc_qc_note 
ADD dockingPeople VARCHAR(20) NOT NULL DEFAULT '' COMMENT '对接人',
ADD dockingDep VARCHAR(100) NOT NULL DEFAULT ''  COMMENT '对接部门';

DROP TABLE IF EXISTS cm_talk_config;
CREATE TABLE `cm_talk_config` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `addPerson` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '添加人姓名',
   `type` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '话术类型',
   `content` TEXT NOT NULL COMMENT '话术详情',
   `addTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='话术配置表';

INSERT INTO cm_talk_config(id,TYPE,content) VALUES(1,'执行问题总结','执行问题总结话术');
INSERT INTO cm_talk_config(id,TYPE,content) VALUES(2,'供应商问题总结','供应商问题总结话术');
INSERT INTO cm_talk_config(id,TYPE,content) VALUES(3,'处罚单总结','处罚单总结话术');
