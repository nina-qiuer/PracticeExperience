USE quality;

ALTER TABLE qc_inner_resp_bill
ADD COLUMN `resp_manager_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任经理ID',
ADD  COLUMN `resp_manager_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任经理Name',
ADD COLUMN `manager_job_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任经理岗位ID',
ADD COLUMN `resp_general_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任总监ID',
ADD  COLUMN `resp_general_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任总监Name',
ADD COLUMN `general_job_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任总监岗位ID';
 
ALTER TABLE qc_outer_resp_bill
ADD COLUMN `resp_manager_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任经理ID',
ADD  COLUMN `resp_manager_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任经理Name',
ADD COLUMN `manager_job_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任经理岗位ID',
ADD COLUMN `resp_general_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任总监ID',
ADD  COLUMN `resp_general_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '责任总监Name',
ADD COLUMN `general_job_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任总监岗位ID';

ALTER TABLE qc_outer_resp_bill
ADD INDEX resp_manager_name(resp_manager_name),
ADD INDEX resp_general_name(resp_general_name);

ALTER TABLE qc_inner_resp_bill
ADD INDEX resp_manager_name(resp_manager_name),
ADD INDEX resp_general_name(resp_general_name);