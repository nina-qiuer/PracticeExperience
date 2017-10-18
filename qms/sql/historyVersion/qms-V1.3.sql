USE quality;

ALTER TABLE qc_assign_config_cmp ADD nooAssignTime TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '无订单分单时间';
