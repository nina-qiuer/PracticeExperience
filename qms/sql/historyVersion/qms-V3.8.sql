USE quality;
DROP TABLE IF EXISTS qs_pre_sale_rv;
CREATE TABLE `qs_pre_sale_rv` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `ordId` INT(20) NOT NULL DEFAULT '0' COMMENT '订单号',
   `prdName` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '产品名称',
   `customer` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '售前客服专员',
   `customerLeader` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '售前客服经理',
   `extension` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '坐席号',
   `tel` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '回访电话',
   `businessUnitName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '客服所在事业部',
   `departmentName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '客服所在二级部门',
   `groupName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '客服所在组',
   `score` TINYINT(4) NOT NULL DEFAULT '-1' COMMENT '售前综合服务评分 满意3分，一般2分，不满意0分',
   `unsatisfyReason` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '不满意原因 1.业务熟练度2. 解决问题及时性3.服务态度4. 工作责任心',
   `returnVisitDate` DATE NOT NULL DEFAULT '0000-00-00' COMMENT '回访日期',
   `beginDate` DATE NOT NULL DEFAULT '0000-00-00' COMMENT '出游日期',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
   `sendTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发送时间',
   `orderTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下单时间',
   `signDate` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '签约时间',
   `isSign` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '回访标志位，0：未回访，1：已回访',
   `isCancel` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '订单标志位，1：未取消，2：已取消',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标志位，0：正常，1：删除'
 )  ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='售前客服短信回访';

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '售前短信回访', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量监管');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '售前短信回访列表', '', 'qs/returnVisit/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='售前短信回访');