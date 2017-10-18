USE quality;


 ## 投诉质检关联研发质检列表索引
  ALTER TABLE qc_relation_bill ADD INDEX qcId(qcId), ADD INDEX cmpId(cmpId);
  
  ## 质检单重要标记
ALTER TABLE qc_qc_bill MODIFY importantFlag TINYINT(4) NOT NULL DEFAULT '0'  COMMENT '重要标记 0：普通 1：重要 2: 非常重要';