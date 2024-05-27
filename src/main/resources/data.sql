insert into batch_master(id, batch_no, batch_name, cron_exp, job_bean_name, job_class_path, created_date, modified_date)
values(1,'BATCH_TEST_01', '테스트배치', '0/15 * * * * ?', 'jobEx', 'com.mun.batch.job.TestScheduleJob', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())
;

insert into batch_param(batch_master_id, param_name, param_value, use_yn)
values(1, 'param1', 'value1', 'Y')
;
insert into batch_param(batch_master_id, param_name, param_value, use_yn)
values(1, 'param2', 'value2', 'Y')
;
