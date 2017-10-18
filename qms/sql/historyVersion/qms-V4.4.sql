USE quality;

ALTER TABLE qs_pre_sale_rv
ADD COLUMN `allPersonPhone` VARCHAR(150) NOT NULL DEFAULT '' COMMENT '订单中所有联系人手机号',
ADD INDEX visitDate_ordId(returnVisitDate, ordId);
