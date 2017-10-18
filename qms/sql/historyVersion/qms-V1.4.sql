USE quality;

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '研发质检单列表', '', 'qc/resDevQcBill/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '列表', '', '', 0, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='研发质检单列表');

INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson) 
(SELECT id, '研发质检报告权限', 'DEV_REPORT',1, '陈海涛' FROM cm_resource WHERE NAME='研发质检单列表');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, 'JIRA单列表', '', 'qc/jiraRelation/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '研发质检分单配置', '', 'qc/assignConfigDev/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

ALTER TABLE qc_qc_bill 
ADD influenceTime INT(11) NOT NULL DEFAULT 0 COMMENT '影响时长',
ADD influenceRange TEXT  COMMENT '影响范围',
ADD influenceResult TEXT  COMMENT '影响结果',
ADD qualityEventClass VARCHAR(11) NOT NULL DEFAULT '' COMMENT '质量问题等级';

DROP TABLE IF EXISTS qc_jira;
CREATE TABLE `qc_jira` (
  `id` INT(11) NOT NULL AUTO_INCREMENT  PRIMARY KEY COMMENT 'PK',
  `qcId`  INT(11) NOT NULL DEFAULT '0'  COMMENT '研发质检单号',
  `jiraId`  INT(11) NOT NULL DEFAULT '0'  COMMENT 'jiraId',
  `jiraName`  VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'jira单号',
  `project`  INT(11) NOT NULL DEFAULT '0'  COMMENT '系统编号',
  `statusNum`  INT(11) NOT NULL DEFAULT '0' COMMENT 'jira单状态',
  `statusName` VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'jira单状态名称',
  `typeNum` INT(11) NOT NULL DEFAULT '0' COMMENT 'jira单类型',
  `typeName` VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'jira单类型名称',
  `summary` VARCHAR(300) NOT NULL DEFAULT '' COMMENT 'jira单标题',
  `description` TEXT COMMENT '描述',
  `created` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00'  COMMENT '更新时间',
  `mianReason` TEXT COMMENT '问题主要原因',
  `reasonDetail` TEXT COMMENT '问题原因明细',
  `solution` TEXT COMMENT '解决方案',
  `eventClass`  VARCHAR(20) NOT NULL DEFAULT '' COMMENT '严重等级',
  `devProPeople` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '研发处理人',
  `applicationPeople` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '需求提出人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `flag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '关联标识位，0：未关联，1：已关联，2：已关闭'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='jira单';


DROP TABLE IF EXISTS qc_assign_config_dev;
CREATE TABLE `qc_assign_config_dev` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT 'PK',
   `qcPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检员id',
   `qcPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检员姓名',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='研发质检分单配置';


DROP TABLE IF EXISTS qc_person_project_dev;
CREATE TABLE `qc_person_project_dev` (
   `id` INT(11) NOT NULL AUTO_INCREMENT  PRIMARY KEY COMMENT 'PK',
   `qcPersonId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检员id',
   `qcPersonName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '质检员姓名',
   `projectId` INT(11) NOT NULL DEFAULT '0' COMMENT '对接系统id',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='研发质检员对接系统配置';

DROP TABLE IF EXISTS qc_dev_fault;
CREATE TABLE `qc_dev_fault` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `qcId` INT(11) NOT NULL DEFAULT '0' COMMENT '质检单id',
   `qptId` INT(11) NOT NULL DEFAULT '0' COMMENT '研发故障类型id',
   `causeAnalysis` TEXT NOT NULL COMMENT '原因分析',
   `treMeasures` TEXT NOT NULL COMMENT '处理措施',
   `impMeasures` TEXT NOT NULL COMMENT '改进措施',
   `online` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否为上线（含变更）引起 0：是，1：否',
   `jiraAddress` TEXT NOT NULL COMMENT 'jira单地址或单号',
   `influenceSystem` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '影响系统',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='研发故障单';
CREATE INDEX qcId ON qc_dev_fault (qcId);

DROP TABLE IF EXISTS qc_dev_resp_bill ;
CREATE TABLE `qc_dev_resp_bill` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   `devId` INT(11) NOT NULL DEFAULT '0' COMMENT '故障单id',
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
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='研发责任单';
CREATE INDEX devId ON qc_dev_resp_bill (devId);

DROP TABLE IF EXISTS qc_dev_fault_attach ;
CREATE TABLE `qc_dev_fault_attach` (
   `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY  COMMENT 'PK',
   `devId` INT(11) NOT NULL DEFAULT '0' COMMENT '故障问题ID',
   `path` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '附件路径',
   `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '附件名称',
   `type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '类型',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='研发故障附件表';

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '需求缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '需求不完整', 0, 1 FROM qc_quality_problem_type WHERE NAME='需求缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '需求错误', 0, 1 FROM qc_quality_problem_type WHERE NAME='需求缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '需求有歧义', 0, 1 FROM qc_quality_problem_type WHERE NAME='需求缺陷');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '设计缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '用户界面设计缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='设计缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '文字提示缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='设计缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '数据库相关', 0, 1 FROM qc_quality_problem_type WHERE NAME='设计缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '功能缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='设计缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '流程缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='设计缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '接口缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='设计缺陷');


INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '编码缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '兼容性', 0, 1 FROM qc_quality_problem_type WHERE NAME='编码缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '代码规范', 0, 1 FROM qc_quality_problem_type WHERE NAME='编码缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '代码逻辑缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='编码缺陷');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '代码健壮性不足', 0, 1 FROM qc_quality_problem_type WHERE NAME='编码缺陷');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '内部网络环境问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '配置缺失', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部网络环境问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '配置错误', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部网络环境问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '网络丢包', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部网络环境问题');


INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '安全类问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '安全类问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='安全类问题');


INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '性能问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '吞吐量大', 0, 1 FROM qc_quality_problem_type WHERE NAME='性能问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, 'SQL慢查', 0, 1 FROM qc_quality_problem_type WHERE NAME='性能问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '资源使用率过高', 0, 1 FROM qc_quality_problem_type WHERE NAME='性能问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '用户并发数高', 0, 1 FROM qc_quality_problem_type WHERE NAME='性能问题');


INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '不符合流程规范', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '不符合流程规范', 0, 1 FROM qc_quality_problem_type WHERE NAME='不符合流程规范');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '外部故障', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '供应商故障', 0, 1 FROM qc_quality_problem_type WHERE NAME='外部故障');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '非研发故障', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '人为操作问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='非研发故障');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '系统不支持', 0, 1 FROM qc_quality_problem_type WHERE NAME='非研发故障');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '重复问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='非研发故障');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '权限问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='非研发故障');


ALTER TABLE qc_qc_bill ADD COLUMN importantFlag TINYINT(4) NOT NULL DEFAULT 0 COMMENT '重要标记 0：普通  1：重要';

ALTER TABLE qc_qc_bill ADD COLUMN verification  TEXT NOT NULL COMMENT '核实情况';

ALTER TABLE qc_quality_problem ADD lowSatisDegree TINYINT(4) NOT NULL DEFAULT '0' COMMENT '低满意度（满意度小于等于50%）1：是选中';

insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级甲等','0','对公司业务收益、客户体验产生极端恶劣的影响，导致业务端口关闭、合作中止等重大商务影响的故障，其外部影响详见表下注释。','1','1000.00','10','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级甲等','0','损失金额>2万元','1','1000.00','10','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('一级乙等','0','违反流程标准，1万元＜损失金额≤2万元','1','500.00','5','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级乙等','0','执行不到位，1万元＜损失金额≤2万元','1','200.00','5','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('一级乙等','0','违反流程标准，N≥10000','1','500.00','5','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级乙等','0','执行不到位，N≥10000','1','200.00','5','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('一级丙等','0','违反流程标准，5000＜N＜10000','1','200.00','3','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级丙等','0','执行不到位，5000＜N＜10000','1','100.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级丙等','0','违反流程标准，5000≤损失金额＜10000元','1','200.00','3','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级丙等','0','执行不到位，5000≤损失金额＜10000元','1','100.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级丙等','0','违反流程标准，3单（含）以上客人投诉','1','200.00','3','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('一级丙等','0','执行不到位，3单（含）以上客人投诉','1','100.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('二级','0','违反流程标准，500＜N≤5000','1','100.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('二级','0','执行不到位，500＜N≤5000','1','50.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('二级','0','违反流程标准，2000≤损失金额＜5000元','1','100.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('二级','0','执行不到位，2000≤损失金额＜5000元','1','50.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('二级','0','违反流程标准，3单以下客人投诉','1','100.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('二级','0','执行不到位，3单以下客人投诉','1','50.00','2','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('三级','0','违反流程标准，N≤500','1','100.00','1','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('三级','0','执行不到位，N≤500','1','0.00','1','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('三级','0','违反流程标准，损失金额＜2000元','1','100.00','1','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('三级','0','执行不到位，损失金额＜2000元','1','0.00','1','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('三级','0','未投诉，但违反流程标准','1','100.00','1','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('三级','0','未投诉，但执行不到位','1','0.00','1','《研发故障定级和处罚分级标准》','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('研发红线','1','禁止一切未经授权的线上数据库、环境的操作行为','1','0.00','0','质量中心流程规范-研发高压线','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('研发红线','1','禁止一切未经授权私自连接线上TSP注册中心','1','0.00','0','质量中心流程规范-研发高压线','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('研发红线','1','禁止在非授权时间擅自发布','1','0.00','0','质量中心流程规范-研发高压线','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('研发红线','1','禁止研发人员擅自响应各渠道反馈来的导数据相关需求','1','0.00','0','质量中心流程规范-研发高压线','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`)
values('研发红线','1','禁止使用非测试账号对线上环境、功能的验证操作','1','0.00','0','质量中心流程规范-研发高压线 ','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('研发红线','1','不得擅自修改非本系统的代码','1','0.00','0','质量中心流程规范-研发高压线 ','1');
insert into `qc_punish_standard` (`level`, `redLineFlag`, `description`, `punishObj`, `economicPunish`, `scorePunish`, `source`, `devQcUse`) 
values('研发红线','1','禁止内部系统之间的服务请求通过外网进行调用','1','0.00','0','质量中心流程规范-研发高压线','1');

ALTER TABLE cm_order_bill  
ADD COLUMN custId INT(11) NOT NULL DEFAULT '0' COMMENT '会员ID',
ADD COLUMN custLevel  VARCHAR(100) NOT NULL DEFAULT '' COMMENT '会员星级',
ADD COLUMN guideId VARCHAR(100) NOT NULL DEFAULT '' COMMENT '导游编号',
ADD COLUMN guideName VARCHAR(100) NOT NULL DEFAULT ''  COMMENT '导游姓名',
ADD COLUMN guideCall VARCHAR(50) NOT NULL DEFAULT '' COMMENT '导游电话' ;


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

