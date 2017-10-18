USE quality;
INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '内部质检申请', '', 'qc/innerQcBill/launchList', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='运营质检');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '内部质检列表', '', 'qc/innerQcBill/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='运营质检');

ALTER TABLE qc_qc_bill 
ADD submitTime timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '提交时间';

ALTER TABLE qc_quality_problem_type
ADD innerQcUse TINYINT(4) NOT NULL DEFAULT '0' COMMENT '内部质检是否使用，0：否，1：是';

ALTER TABLE qc_punish_standard
ADD innerQcUse TINYINT(4) NOT NULL DEFAULT '0' COMMENT '内部质检是否使用，0：否，1：是';



INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '内部质检问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '下错单', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '重复下单', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '机票错漏', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '点评修改', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '订单负利润', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '供应商超售', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '供应商逾越我司联系客人', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '未使用测试账号下单', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, innerQcUse)
(SELECT id, '天猫订单问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='内部质检问题');

