USE quality;

ALTER TABLE qc_qc_bill ADD `qcPeriodBgnTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '质检期开始时间';
ALTER TABLE qc_qc_bill ADD `qcPeriodEndTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '质检期结束时间';
	       		 
