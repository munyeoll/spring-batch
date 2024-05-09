package com.mun.batch.job.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ExampleFlowStep {

    private String result = "COMPLETED";
//    private String result = "FAIL";
//    private String result = "UNKNOWN";

    @Bean
    public Job flowStepJob(
            JobRepository jobRepository,
            Step flowStep,
            Step failOverStep,
            Step processStep,
            Step writeStep
    ) {
        return new JobBuilder("flowStepJob", jobRepository)
               .start(flowStep)
                    .on("FAILED") //startStep의 ExitStatus가 FAILED일 경우
                    .to(failOverStep) //failOver Step을 실행 시킨다.
                    .on("*") //failOver Step의 결과와 상관없이
                    .to(writeStep) //write Step을 실행 시킨다.
                    .on("*") //write Step의 결과와 상관없 이
                    .end() //Flow를 종료시킨다.

                .from(flowStep) //startStep이 FAILED가 아니고
                    .on("COMPLETED") //COMPLETED일 경우
                    .to(processStep) //process Step을 실행 시킨다
                    .on("*") //process Step의 결과와 상관없이
                    .to(writeStep) // write Step을 실행 시킨다.
                    .on("*") //wrtie Step의 결과와 상관없이
                    .end() //Flow를 종료 시킨다.

                .from(flowStep) //startStep의 결과가 FAILED, COMPLETED가 아닌
                    .on("*") //모든 경우
                    .to(writeStep) //write Step을 실행시킨다.
                    .on("*") //write Step의 결과와 상관없이
                    .end() //Flow를 종료시킨다.
                .end()
                .build();
    }

    @Bean
    public Step flowStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ){
        return new StepBuilder("flowStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> This is flowStep");

                    // Flow에서 on은 RepeatStatus가 아닌 ExitStatus를 바라본다.
                    if(result.equals("COMPLETED"))
                        contribution.setExitStatus(ExitStatus.COMPLETED);
                    else if(result.equals("FAIL"))
                        contribution.setExitStatus(ExitStatus.FAILED);
                    else if(result.equals("UNKNOWN"))
                        contribution.setExitStatus(ExitStatus.UNKNOWN);

                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step failOverStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("failOverStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> FailOver Step");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step processStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("processStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> Process Step");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step writeStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        return new StepBuilder("writeStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> Write Step");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }
}
