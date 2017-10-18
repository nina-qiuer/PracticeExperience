USE quality;
ALTER TABLE cm_order_bill 
ADD salerBusinessUnit varchar(300) NOT NULL DEFAULT '' COMMENT '客服事业部',
ADD salerDep varchar(300) NOT NULL DEFAULT '' COMMENT '客服部门',
ADD salerTeam varchar(300) NOT NULL DEFAULT '' COMMENT '客服组';


  
   