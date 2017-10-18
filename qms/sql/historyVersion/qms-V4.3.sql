USE quality;

CREATE TABLE `qc_resp_punish_relation` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `resp_id` INT(11) NOT NULL DEFAULT '0' COMMENT '责任单ID',
   `resp_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '责任单类型，1：内部责任单，2：外部责任单',
   `punish_id` INT(11) NOT NULL DEFAULT '0' COMMENT '处罚单ID',
   `punish_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '处罚单类型，1：内部处罚单，2：外部处罚单',
   `add_person` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '申请人',
   `add_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `update_person` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `del_flag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   PRIMARY KEY (`id`),
   KEY `respId_respType` (`resp_id`, resp_type),
   KEY `punishId_punishType` (`punish_id`, punish_type)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='责任处罚关联单';
