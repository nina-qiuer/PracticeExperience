USE quality;

ALTER TABLE qc_inner_resp_bill 
ADD respRate FLOAT(18, 2) NOT NULL DEFAULT '0' COMMENT '责任占比';


