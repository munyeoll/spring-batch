insert into batch_master (batch_no, batch_name, cron_string, jobClassName, created_date, modified_date)
values('BATCH_TEST_01', '테스트배치', '0 0/1 * 1/1 * ?', 'package com.mun.batch.job.BatchTestJob01' CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
