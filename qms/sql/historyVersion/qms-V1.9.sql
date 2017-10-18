USE quality;
ALTER TABLE qc_quality_problem ADD `qptLv1Id` INT(11) NOT NULL DEFAULT '0' COMMENT '一级质量问题类型id';

ALTER TABLE qc_dev_fault
ADD useFlag TINYINT(4) NOT NULL DEFAULT '0' COMMENT '故障单类型，0：开发故障单，1：测试故障单';

INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试问题', 0, 1 FROM qc_quality_problem_type WHERE NAME='ROOT');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试设计遗漏', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试用例遗漏', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试执行遗漏', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试结果检查遗漏', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试安排不合理', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试受阻', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');
INSERT INTO qc_quality_problem_type (pid, NAME, touchRedFlag, devQcUse)
(SELECT id, '测试环境受限', 0, 1 FROM qc_quality_problem_type WHERE NAME='测试问题');