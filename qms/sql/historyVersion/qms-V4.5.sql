USE quality;

ALTER TABLE qc_inner_resp_bill
ADD COLUMN `appeal_person_id` INT(11) NOT NULL DEFAULT '0' COMMENT '异议提出人ID',
ADD COLUMN `appeal_person_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '异议提出人Name',
ADD COLUMN `imp_job_id` INT(11) NOT NULL DEFAULT '0' COMMENT '改进岗位ID',
ADD COLUMN `appeal_job_id` INT(11) NOT NULL DEFAULT '0' COMMENT '异议提出人岗位ID';

ALTER TABLE qc_outer_resp_bill
ADD COLUMN `appeal_person_id` INT(11) NOT NULL DEFAULT '0' COMMENT '异议提出人ID',
ADD COLUMN `appeal_person_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '异议提出人Name';

ALTER TABLE qc_qc_bill
ADD COLUMN cancel_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '撤销时间';

UPDATE qc_qc_bill
SET cancel_time = updateTime
WHERE delFlag = 0 AND state = 5;

DELETE FROM cm_complaint_sync_task WHERE failTimes = 3; 
DELETE FROM cm_product_sync_task WHERE failTimes = 3;
DELETE FROM cm_order_sync_task WHERE failTimes = 3;

ALTER TABLE cm_order_sync_task
ADD INDEX order_id(ordId);

ALTER TABLE cm_product_sync_task
ADD INDEX prd_id(prdId);

ALTER TABLE qs_substd_purchase_amt
ADD INDEX add_date(`addDate`);

ALTER TABLE qs_substd_product_snapshot
ADD INDEX statistic_date(`statisticdate`);

ALTER TABLE qs_substd_order_amt
ADD INDEX statistic_date(`statisticdate`);

ALTER TABLE qs_substd_refund_amt
ADD INDEX add_date(`addDate`);
