insert into batch_master (batch_no, batch_name, cron_exp, job_class_path, created_date, modified_date)
values('BATCH_TEST_01', '테스트배치', '0/5 * * * * ?', 'com.mun.batch.job.TestScheduleJob', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())
;
