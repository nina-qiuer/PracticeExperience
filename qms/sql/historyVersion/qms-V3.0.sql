USE quality;

ALTER TABLE qc_qc_bill 
ADD subject VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '邮件主题',
ADD reAddrs TEXT COMMENT '收件人',
ADD ccAddrs TEXT COMMENT '抄送人',
ADD returnFlag tinyint(4) NOT NULL DEFAULT '0' COMMENT '退回标识位，0：未退回，1：已退回';

ALTER TABLE qc_qc_bill MODIFY COLUMN remark TEXT COMMENT '备注';

ALTER TABLE qc_qc_bill MODIFY COLUMN qcAffairDesc  TEXT  COMMENT '质检事宜详述';

ALTER TABLE qc_qc_bill MODIFY COLUMN  verification TEXT COMMENT '核实情况';

INSERT INTO cm_resource(pid,NAME,widgetCode,chkAuthFlag,addPerson) (SELECT id, '质检单审核状态按钮', 'AUDIT_QC_STATE',1, '陈海涛' FROM cm_resource WHERE NAME='投诉质检单列表');
