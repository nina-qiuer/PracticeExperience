
# 在quality所在库下建uc_temp;
# copy数据，如：
# FROM：10.10.30.34   3307  uc
# TO：10.10.30.34   3306  uc_temp
# 涉及如下表：
# uc_user
# uc_depuser_map
# uc_department_job
# uc_user_job
# uc_department
# uc_job
# uc_position

==================================================================================================================================

TRUNCATE quality.uc_user;
TRUNCATE quality.uc_department;
TRUNCATE quality.uc_job;
TRUNCATE quality.uc_position;
TRUNCATE quality.uc_department_user;
TRUNCATE quality.uc_department_job;
TRUNCATE quality.uc_user_depjob;


INSERT INTO quality.uc_user (id, userName, realName, email, extension, workNum, depId, positionId, jobId, ADDTIME, delFlag)
SELECT uid, user_name, real_name, email, extension, worknum, dep_id, position_id, job_id, add_time, mark FROM uc_temp.uc_user;

INSERT INTO quality.uc_department (id, pid, NAME, depth, delFlag)
SELECT id, father_id, dep_name, depth, mark FROM uc_temp.uc_department;

INSERT INTO quality.uc_job (id, NAME, delFlag)
SELECT id, job_name, mark FROM uc_temp.uc_job;

INSERT INTO quality.uc_position (id, NAME, LEVEL, ADDTIME, delFlag)
SELECT id, position_name, LEVEL, add_time, mark FROM uc_temp.uc_position;

INSERT INTO quality.uc_department_user (id, depId, userId)
SELECT id, dep_id, user_id FROM uc_temp.uc_depuser_map;

INSERT INTO quality.uc_department_job (id, depId, jobId, ADDTIME, delFlag)
SELECT id, dept_id, job_id, add_time, del_flag FROM uc_temp.uc_department_job;

INSERT INTO quality.uc_user_depjob (id, userId, depjobId, corType, ADDTIME, delFlag)
SELECT id, user_id, dept_job_id, cor_type, add_time, del_flag FROM uc_temp.uc_user_job;

