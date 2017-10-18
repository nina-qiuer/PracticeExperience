USE quality;

## 质检关联表返回和关闭功能
ALTER TABLE qc_relation_bill
MODIFY `flag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '关联标识位，0：未关联，1：已关联，2：已关闭，3：已返回',
ADD COLUMN closeType TINYINT(4) NOT NULL DEFAULT '0' COMMENT '关闭原因类型',
ADD COLUMN remark TEXT COMMENT '备注' ;

CREATE TABLE `cm_type` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
   `pid` INT(11) NOT NULL DEFAULT '0' COMMENT 'ParentID',
   `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '类型名称',
   `typeFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '通用类型标识位：1：投诉关联关闭原因  2：其他',
   `addPerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '添加人',
   `addTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
   `updatePerson` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '修改人',
   `updateTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
   `delFlag` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '删除标识位，0：未删除，1：已删除',
   PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='通用类型配置表';
  
insert into cm_type (  pid,  name,  typeFlag)  values( '0',  'ROOT', ' 0');