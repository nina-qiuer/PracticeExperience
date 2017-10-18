USE quality;

 
ALTER TABLE qc_qc_bill ADD cancelFlag TINYINT(4) NOT NULL DEFAULT '0' COMMENT '撤销标识，1:已撤销，0：未撤销 ';

 INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '已撤销质检单公司损失列表', '', 'qc/qcBill/qcCancelList', 1, 0, 1, '王明方' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '处罚单管理', '', '', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='质量检验');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '处罚单管理列表', '', 'qc/scoreRecord/list', 1, 0, 1, '陈海涛' FROM cm_resource WHERE NAME='处罚单管理');

INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson)
(SELECT id, '删除处罚单', 'deletePunish',1, '陈海涛' FROM cm_resource WHERE NAME='处罚单管理列表');

INSERT INTO cm_resource (pid, NAME, widgetCode, url, menuFlag, operType, chkAuthFlag, addPerson)
(SELECT id, '我的处罚单', '', 'qc/scoreRecord/myList', 1, 0, 0, '陈海涛' FROM cm_resource WHERE NAME='处罚单管理');
