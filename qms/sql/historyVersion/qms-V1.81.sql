USE quality;

ALTER TABLE cm_complaint_bill
ADD comeFrom varchar(100) NOT NULL DEFAULT '' COMMENT '投诉来源';

ALTER TABLE qc_qc_bill  MODIFY COLUMN evidence  VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '证据';
