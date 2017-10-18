USE quality;

DROP TABLE IF EXISTS uc_user;
CREATE TABLE `uc_user` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `userName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `realName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `email` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '邮件',
  `extension` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '分机号',
  `workNum` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '工号',
  `depId` INT(11) NOT NULL DEFAULT '0' COMMENT '主部门ID',
  `positionId` INT(11) NOT NULL DEFAULT '0' COMMENT '主部门职位ID',
  `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '主部门岗位ID',
  `roleId` INT(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='人员表';

DROP TABLE IF EXISTS uc_department_user;
CREATE TABLE `uc_department_user` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `depId` INT(11) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `userId` INT(11) NOT NULL DEFAULT '0' COMMENT '人员ID',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='部门人员关联表(只存兼任信息)';
CREATE INDEX depId ON uc_department_user (depId);
CREATE INDEX userId ON uc_department_user (userId);

DROP TABLE IF EXISTS uc_department_job;
CREATE TABLE `uc_department_job` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `depId` INT(11) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `jobId` INT(11) NOT NULL DEFAULT '0' COMMENT '岗位ID',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='部门岗位关联表';
CREATE INDEX depId ON uc_department_job (depId);
CREATE INDEX jobId ON uc_department_job (jobId);

DROP TABLE IF EXISTS uc_user_depjob;
CREATE TABLE `uc_user_depjob` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `userId` INT(11) NOT NULL DEFAULT '0' COMMENT '人员ID',
  `depjobId` INT(11) NOT NULL DEFAULT '0' COMMENT '部门岗位ID',
  `corType` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '关联类型，0：主部门岗位，1：兼任部门岗位',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='人员部门岗位表';
CREATE INDEX userId ON uc_user_depjob (userId);
CREATE INDEX depjobId ON uc_user_depjob (depjobId);

DROP TABLE IF EXISTS uc_department;
CREATE TABLE `uc_department` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `pid` INT(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '部门名称',
  `depth` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '部门层级',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='部门表';

DROP TABLE IF EXISTS uc_job;
CREATE TABLE `uc_job` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `name` VARCHAR(40) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='岗位（职务）表';

DROP TABLE IF EXISTS uc_position;
CREATE TABLE `uc_position` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `name` VARCHAR(40) NOT NULL DEFAULT '' COMMENT '职位名称',
  `level` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '职位等级',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='职位表';


DROP TABLE IF EXISTS cm_role;
CREATE TABLE `cm_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '角色类型，1：基础员工（可查看自己名下数据），2：经理（可查看本组织内人员数据），3：管理员（可查看所有数据）',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

DROP TABLE IF EXISTS cm_role_resource_rel;
CREATE TABLE `cm_role_resource_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `roleId` INT(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `resourceId` INT(11) NOT NULL DEFAULT '0' COMMENT '资源ID',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='角色-资源关联表';

DROP TABLE IF EXISTS cm_resource;
CREATE TABLE `cm_resource` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `pid` INT(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '资源名称，既操作释义',
  `widgetCode` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '界面控件编码，用于控制界面控件的显示，规则：控件类型_操作',
  `url` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '关联访问地址（相对）',
  `menuFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '菜单标识位，0：非菜单，1：菜单',
  `operType` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '操作类型，0：查询，1：增删改',
  `chkAuthFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '鉴权标志位，0：不鉴权，1：鉴权',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统资源表';
INSERT INTO cm_resource (pid, NAME) VALUES (0, 'ROOT');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '系统管理', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='ROOT');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '资源管理', '', 'common/resource/management', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='系统管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '角色管理', '', 'common/role/management', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='系统管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '授权管理', '', 'common/user/authManagement', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='系统管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '缓存管理', '', 'common/cache/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='系统管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '业务操作日志', '', 'common/businessLog/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='系统管理');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量检验', '', '', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='ROOT');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '处罚标准配置', '', 'qc/punishStandard/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质量问题类型配置', '', 'qc/qualityProblemType/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '质检类型配置', '', 'qc/qcType/list', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');


DROP TABLE IF EXISTS cm_business_log;
CREATE TABLE `cm_business_log` (
  `id` INT(11) NOT NULL PRIMARY KEY COMMENT 'PK',
  `clientIP` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '客户端IP',
  `operatorId` INT(11) NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `operatorName` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `resName` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '资源名称',
  `requestUri` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '访问地址',
  `requestArgs` TEXT NOT NULL COMMENT '访问参数',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='业务操作日志表';


DROP TABLE IF EXISTS qc_punish_standard;
CREATE TABLE `qc_punish_standard` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `level` VARCHAR(30) NOT NULL COMMENT '处罚等级',
  `redLineFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '红线标识位，0：非红线，1：红线',
  `description` TEXT NOT NULL COMMENT '分级标准描述',
  `punishObj` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '处罚对象，1：内部员工，2：供应商',
  `economicPunish` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '经济处罚（元）',
  `scorePunish` INT(4) NOT NULL DEFAULT '0' COMMENT '记分处罚（分）',
  `source` TEXT NOT NULL COMMENT '出处',
  `cmpQcUse` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '投诉质检是否使用，0：否，1：是',
  `operQcUse` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '运营质检是否使用，0：否，1：是',
  `devQcUse` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '研发质检是否使用，0：否，1：是',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='处罚标准配置表';

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级甲等', 0, '公司损失金额超过2万元以上的投诉', 1, 1000, 10, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级甲等', 0, '涉及旅游行政处罚等合规相关的内外部投诉/反馈', 1, 1000, 10, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级甲等', 0, '出境订单出现客人滞留不归，经鉴定为我司在执行过程中出现违反流程标准操作的情况。', 1, 1000, 10, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级甲等', 0, '签约后的服务态度投诉（负面评价客人，辱骂客人）', 1, 1000, 10, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级乙等', 0, '公司损失金额1万元~2万元（含）的投诉', 1, 500, 5, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级乙等', 0, '价值观类问题投诉', 1, 500, 5, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级乙等', 0, '签约前服务态度类问题投诉（负面评价客人，辱骂客人）', 1, 500, 5, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级乙等', 0, '签约后的服务态度投诉（反问、不耐烦、催挂、顶撞、争吵、抢话、推脱责任、厌烦、不耐烦、轻蔑、与旁人说话）', 1, 500, 5, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '紧急事件投诉', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '客人通过第三方执法机构投诉等', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '采用网站（微博/途牛论坛除外）、媒体投诉等影响较大的投诉途径的投诉', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '客户上门市采取过激行为的投诉', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '接待社不一致问题投诉', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '签约前的服务态度投诉（反问、不耐烦、催挂、顶撞、争吵、抢话、推脱责任、厌烦、不耐烦、轻蔑、与旁人说话）', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '对客户正常出游影响较严重的航班问题和证件问题（护照、港澳通行证等）引起的投诉，且公司损失金额超过3000元以上', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '公司损失金额超过5000元（含）的投诉', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('一级丙等', 0, '牛人专线客户或五星会员投诉且公司损失金额超过3000元以上', 1, 200, 3, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '因途牛原因导致丢失客人证件', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '二次投诉（客户第一次投诉后我司未及时处理导致客户再次来电投诉）', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '通过微博提起的投诉', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '公司损失金额超过2000元（含）的投诉', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '牛人专线客户或五星会员投诉且公司损失金额3000元（含）以下', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '对客户正常出游影响较严重的航班问题和证件问题（护照、港澳通行证等）引起的投诉，且公司损失金额3000元（含）以下', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('二级', 0, '未在规定时限内为客户购买保险', 1, 100, 2, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('三级', 0, '未在一级、二级处罚中列明的情形（若是签约后的回呼不及时引发的客人投诉，升级至二级的处罚标准）', 1, 50, 1, '《执行问题处罚分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 1, '供应商责任的交通事故', 1, 0, 5, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 1, '签署旅游合同时明确有领队陪同，但实际未派领队/导游提供服务', 1, 0, 5, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 1, '导游/领队增加或强制购物或自费', 1, 0, 5, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 1, '司机危险驾驶，包括闯红灯、飙车、超速、开车打电话、开车抽烟等违反交通法的危险行为；以客人投诉为准，司机不认可的，需要自己举证证明', 1, 0, 5, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 1, '全团3个不同订单的客人一起投诉或5名（含）以上客人因供应商提供的餐厅就餐导致食物中毒', 1, 0, 5, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 1, '领队或导游带团时终止服务活动', 1, 0, 5, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 0, '因为旅游服务质量对旅游团客户造成影响或利益受损，发生或可能发生同团中三名或三名以上客户就相同的问题进行投诉。', 1, 0, 3, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 0, '签署旅游合同时明确有领队陪同，但派遣的领队或导游无/未佩戴领队证或导游证', 1, 0, 3, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 0, '导游/领队向客人索要小费', 1, 0, 3, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 0, '导游擅自缩减行程时间、遗漏景点（情节较为严重）', 1, 0, 3, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类）', 0, '供应商工作人员危害游客人身安全', 1, 0, 3, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 1, '供应商责任的交通事故', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 1, '签署旅游合同时明确有领队陪同，但实际未派领队/导游提供服务', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 1, '导游/领队增加或强制购物或自费', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 1, '司机危险驾驶，包括闯红灯、飙车、超速、开车打电话、开车抽烟等违反交通法的危险行为；以客人投诉为准，司机不认可的，需要自己举证证明', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 1, '全团3个不同订单的客人一起投诉或5名（含）以上客人因供应商提供的餐厅就餐导致食物中毒', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 1, '领队或导游带团时终止服务活动', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 0, '因为旅游服务质量对旅游团客户造成影响或利益受损，发生或可能发生同团中三名或三名以上客户就相同的问题进行投诉。', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 0, '签署旅游合同时明确有领队陪同，但派遣的领队或导游无/未佩戴领队证或导游证', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 0, '导游/领队向客人索要小费', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 0, '导游擅自缩减行程时间、遗漏景点（情节较为严重）', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（D类）', 0, '供应商工作人员危害游客人身安全', 1, 100, 3, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（A类）', 0, '因供应商原因导致丢失客人证件', 1, 0, 2, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（A类）', 0, '二次投诉：客户对第一次投诉处理的结果不满意而再次来电投诉。（投诉点与第一次一样）', 1, 0, 2, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（A类）', 0, '多次不成团引起的投诉', 1, 0, 2, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（A类）', 0, '因直接或变相向客户收取附加费（如：儿童附加费、老人附加费，地域附加费等）而引起的投诉。', 1, 0, 2, '《投诉分级标准》《记分标准细则》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（D类）', 0, '因供应商原因导致丢失客人证件', 1, 50, 2, '《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（D类）', 0, '二次投诉：客户对第一次投诉处理的结果不满意而再次来电投诉。（投诉点与第一次一样）', 1, 50, 2, '《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（D类）', 0, '多次不成团引起的投诉', 1, 50, 2, '《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题二级投诉（D类）', 0, '因直接或变相向客户收取附加费（如：儿童附加费、老人附加费，地域附加费等）而引起的投诉。', 1, 50, 2, '《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题三级投诉（A类、D类）', 0, '未在一级、二级投诉中列明情形的其他供应商问题', 1, 0, 1, '《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题三级投诉（A类、D类）', 0, '点评满意度小于等于50%且其中存在供应商问题的投诉', 1, 0, 1, '《点评流程》《责任界定作业管理规范》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题三级投诉（A类、D类）', 0, '供应商私自处理有关甲方旅游者的相关事宜，包括行程变更、发送出团通知、处理投诉、退款、赔款等；供应商在旅游结束后私自联系旅游者，要求旅游者修改出游点评或以各种方式影响旅游者的生活。', 1, 0, 1, '《旅游者（团）委托接待合同》《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类、D类）', 1, '供应商责任的交通事故', 2, 1000, 0, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类、D类）', 1, '签署旅游合同时明确有领队陪同，但实际未派领队/导游提供服务', 2, 1000, 0, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类、D类）', 1, '导游/领队增加或强制购物或自费', 2, 1000, 0, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类、D类）', 1, '司机危险驾驶，包括闯红灯、飙车、超速、开车打电话、开车抽烟等违反交通法的危险行为；以客人投诉为准，司机不认可的，需要自己举证证明', 2, 1000, 0, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类、D类）', 1, '全团3个不同订单的客人一起投诉或5名（含）以上客人因供应商提供的餐厅就餐导致食物中毒', 2, 1000, 0, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题一级投诉（A类、D类）', 1, '领队或导游带团时终止服务活动', 2, 1000, 0, '《投诉分级标准》《供应商服务“红线”问题处理标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);

INSERT INTO qc_punish_standard (LEVEL, redLineFlag, description, punishObj, economicPunish, scorePunish, source, cmpQcUse)
VALUES ('供应商问题三投诉（A类、D类）', 0, '供应商私自处理有关甲方旅游者的相关事宜，包括行程变更、发送出团通知、处理投诉、退款、赔款等；供应商在旅游结束后私自联系旅游者，要求旅游者修改出游点评或以各种方式影响旅游者的生活。', 2, 100, 0, '《旅游者（团）委托接待合同》《投诉分级标准》《记分标准细则》《D类产品投诉质检处罚相关规定》', 1);


DROP TABLE IF EXISTS qc_quality_problem_type;
CREATE TABLE `qc_quality_problem_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `pid` INT(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '问题类型名称',
  `touchRedFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '触红标识位，0：未触红，1：触红',
  `cmpQcUse` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '投诉质检是否使用，0：否，1：是',
  `operQcUse` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '运营质检是否使用，0：否，1：是',
  `devQcUse` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '研发质检是否使用，0：否，1：是',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质量问题类型配置表';
INSERT INTO qc_quality_problem_type (pid, NAME) VALUES (0, 'ROOT');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '执行问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '出游需求确认', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '出境订单出游条件了解', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '客户信息获取核对', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '费用相关', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '行程相关', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '保险', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '发票', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '签约', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '出团通知', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '信息传递/确认', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '跟进不及时', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '服务态度', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '产品添加/披露', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '订单变更', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '价格问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '确认单问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '上线标准', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '团期及库存问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '询位问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '占位问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '资源定错', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '系统故障/BUG', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '损失分摊到订单（价值观）', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '废单利用（价值观）', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '订单联系方式修改为自己号码（价值观）', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '与订单客户无关号码备注订单跟进（价值观）', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '订单交予VIP下单，分享订单提成（价值观）', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '低星级会员当高星级会员享受权益（价值观）', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '其它', 0, 1 FROM qc_quality_problem_type WHERE NAME='执行问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '流程/系统问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '标准缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='流程/系统问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '流程缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='流程/系统问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '系统缺陷', 0, 1 FROM qc_quality_problem_type WHERE NAME='流程/系统问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '其它', 0, 1 FROM qc_quality_problem_type WHERE NAME='流程/系统问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '供应商问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '导游/领队问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '服务态度问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '不作为', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '协调问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '迟到', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '不讲解或讲解少', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '调整行程', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '无领队或导游', 1, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '导游强制购物或自费', 1, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '导游或领队甩团', 1, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '导游或领队不佩戴相关证件', 1, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '导游擅自调整行程', 1, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '导游或领队索要小费', 0, 1 FROM qc_quality_problem_type WHERE NAME='导游/领队问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '餐饮', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '不卫生', 0, 1 FROM qc_quality_problem_type WHERE NAME='餐饮');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '分量少', 0, 1 FROM qc_quality_problem_type WHERE NAME='餐饮');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '未按行程中安排', 0, 1 FROM qc_quality_problem_type WHERE NAME='餐饮');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '食物中毒（全团3单以上）', 1, 1 FROM qc_quality_problem_type WHERE NAME='餐饮');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '酒店', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '卫生环境', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '硬件设施', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '与标准不符', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '未按行程中安排', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '位置偏僻', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '存在安全隐患', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '酒店服务差', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '没有客人入住信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '地址不对', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '含餐信息有误', 0, 1 FROM qc_quality_problem_type WHERE NAME='酒店');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '交通', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '车辆出现故障', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '车辆卫生/设施', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '车辆与预定不符', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '车辆资质不全', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '机票未出', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '火车票未出', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '机票出错', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '火车票出错', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '衔接问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='交通');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '司机', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '服务差', 0, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '不认识路', 0, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '甩客', 0, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '危害游客人身安全', 0, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '迟到', 0, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '交通事故', 1, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '司机危险驾驶', 1, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '司机强制购物或自费', 1, 1 FROM qc_quality_problem_type WHERE NAME='司机');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '行程', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '行程变更', 0, 1 FROM qc_quality_problem_type WHERE NAME='行程');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '行程压缩', 0, 1 FROM qc_quality_problem_type WHERE NAME='行程');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '行程不符', 0, 1 FROM qc_quality_problem_type WHERE NAME='行程');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '签证', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '迟送签', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '签证出错', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '签证信息传递问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '材料作假', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '遗失材料', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '收、退材料问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '销签', 0, 1 FROM qc_quality_problem_type WHERE NAME='签证');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '信息传递/确认', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '门票', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '门票定错/漏定', 0, 1 FROM qc_quality_problem_type WHERE NAME='门票');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '无法取票', 0, 1 FROM qc_quality_problem_type WHERE NAME='门票');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '验证码没有收到', 0, 1 FROM qc_quality_problem_type WHERE NAME='门票');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '景区衔接', 0, 1 FROM qc_quality_problem_type WHERE NAME='门票');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '系统对接失败', 0, 1 FROM qc_quality_problem_type WHERE NAME='门票');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '不成团', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '二次（或以上）不成团', 0, 1 FROM qc_quality_problem_type WHERE NAME='不成团');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '超售', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '私自联系客人', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '处理投诉', 0, 1 FROM qc_quality_problem_type WHERE NAME='私自联系客人');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '骚扰客人', 0, 1 FROM qc_quality_problem_type WHERE NAME='私自联系客人');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '要求客人撤诉', 0, 1 FROM qc_quality_problem_type WHERE NAME='私自联系客人');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '要求客人修改点评', 0, 1 FROM qc_quality_problem_type WHERE NAME='私自联系客人');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '其它', 0, 1 FROM qc_quality_problem_type WHERE NAME='供应商问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '非质量问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '不可抗力', 0, 1 FROM qc_quality_problem_type WHERE NAME='非质量问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '自然原因', 0, 1 FROM qc_quality_problem_type WHERE NAME='不可抗力');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '社会原因', 0, 1 FROM qc_quality_problem_type WHERE NAME='不可抗力');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '航空公司/铁道部原因', 0, 1 FROM qc_quality_problem_type WHERE NAME='不可抗力');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '意外事件', 0, 1 FROM qc_quality_problem_type WHERE NAME='不可抗力');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '服务类问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='非质量问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询交通信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询住宿信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询餐饮信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询工作人员信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询行程信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询费用信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询门票、景区相关信息', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '咨询系统操作', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '质量工具', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '加订、退改签', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, cmpQcUse)
(SELECT id, '其它', 0, 1 FROM qc_quality_problem_type WHERE NAME='服务类问题');


DROP TABLE IF EXISTS qc_qc_type;
CREATE TABLE `qc_qc_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  `pid` INT(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '质检类型名称',
  `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
  `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='质检类型配置表';
INSERT INTO qc_qc_type (pid, NAME) VALUES (0, 'ROOT');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '内外部客户反馈、投诉' FROM qc_qc_type WHERE NAME='ROOT');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '研发质检' FROM qc_qc_type WHERE NAME='内外部客户反馈、投诉');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '投诉质检' FROM qc_qc_type WHERE NAME='内外部客户反馈、投诉');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '内部申请质检' FROM qc_qc_type WHERE NAME='内外部客户反馈、投诉');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '其它' FROM qc_qc_type WHERE NAME='内外部客户反馈、投诉');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '流程执行' FROM qc_qc_type WHERE NAME='ROOT');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '尾款收取质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '采购单准确性质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '签证担保金收取质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '签约后服务态度质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '出团通知书质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '对客合同模板质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '零负团费和不合理低价产品质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '促销活动质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '保险购买质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '线路点评质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '供应商触红执行质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '济州岛产品预订受理质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '区域签证评分质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '线路目的地关联错误质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '投诉单填写规范质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '牛人专线产品质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '产品调价质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '团队产品添加质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '库存报损逾期质检' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '其它' FROM qc_qc_type WHERE NAME='流程执行');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '指标达成' FROM qc_qc_type WHERE NAME='ROOT');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '售前客服话务质检' FROM qc_qc_type WHERE NAME='指标达成');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '回呼及时率' FROM qc_qc_type WHERE NAME='指标达成');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '首呼及时率' FROM qc_qc_type WHERE NAME='指标达成');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '供应商申诉处理的及时性质检' FROM qc_qc_type WHERE NAME='指标达成');

INSERT INTO qc_qc_type (pid, NAME)
(SELECT id, '其它' FROM qc_qc_type WHERE NAME='指标达成');


#同步UC组织架构














