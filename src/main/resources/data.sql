insert into batch_master(id, batch_no, batch_name, cron_exp, job_bean_name, job_class_path, use_yn, created_date, modified_date)
values(1,'BATCH_TEST_01', '테스트배치', '0/15 * * * * ?', 'jobEx', 'com.mun.batch.job.schedule.TestScheduleJob', 'N', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())
;
insert into batch_master(id, batch_no, batch_name, cron_exp, job_bean_name, job_class_path, use_yn, created_date, modified_date)
values(2,'BATCH_TEST_02', '테스트배치2', '0/5 * * * * ?', 'jobEx2', 'com.mun.batch.job.schedule.TestScheduleJob2', 'N', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())
;

insert into batch_param(batch_master_id, param_name, param_value, use_yn)
values(1, 'param1', 'value1', 'Y')
;
insert into batch_param(batch_master_id, param_name, param_value, use_yn)
values(1, 'param2', 'value2', 'Y')
;
insert into batch_param(batch_master_id, param_name, param_value, use_yn)
values(2, 'param2-1', 'value2-1', 'Y')
;
insert into batch_param(batch_master_id, param_name, param_value, use_yn)
values(2, 'param2-2', 'value2-2', 'Y')
;
