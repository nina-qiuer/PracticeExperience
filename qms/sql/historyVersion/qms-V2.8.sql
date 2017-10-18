USE quality;
ALTER TABLE cm_mail_task CHANGE COLUMN reAddrs  reAddrs TEXT  COMMENT '收件人';
ALTER TABLE cm_mail_task CHANGE COLUMN ccAddrs  ccAddrs TEXT  COMMENT '抄送人';

  
   