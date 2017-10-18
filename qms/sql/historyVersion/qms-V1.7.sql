USE quality;

TRUNCATE cm_complaint_bill;
TRUNCATE cm_complaint_sync_task;
TRUNCATE cm_mail_task;
TRUNCATE cm_order_agency;
TRUNCATE cm_order_bill;
TRUNCATE cm_order_operator;
TRUNCATE cm_order_sync_task;
TRUNCATE cm_product;
TRUNCATE cm_product_sync_task;
TRUNCATE cm_rtx_remind;
TRUNCATE qc_dev_fault;
TRUNCATE qc_dev_fault_attach;
TRUNCATE qc_dev_resp_bill;
TRUNCATE qc_influence_system;
TRUNCATE qc_inner_punish_basis;
TRUNCATE qc_inner_punish_bill;
TRUNCATE qc_inner_resp_bill;
TRUNCATE qc_jira;
TRUNCATE qc_outer_punish_basis;
TRUNCATE qc_outer_punish_bill;
TRUNCATE qc_outer_resp_bill;
TRUNCATE qc_qc_bill;
TRUNCATE qc_qc_note;
TRUNCATE qc_quality_problem;
TRUNCATE qc_quality_problem_attach;
TRUNCATE qc_person_dockdep_cmp;

DELETE FROM uc_user WHERE id IN (SELECT id FROM 
(SELECT id FROM uc_user WHERE realName IN 
(SELECT realName FROM uc_user GROUP BY realName HAVING COUNT(realName)>1) AND id NOT IN (
SELECT MAX(id) FROM uc_user GROUP BY realName HAVING COUNT(realName)>1)) t
);

#查看数据
select * from uc_user where realName in 
(select realName from (select realName,count(1) as num from uc_user group by realName having num>1) t) 
order by realName
