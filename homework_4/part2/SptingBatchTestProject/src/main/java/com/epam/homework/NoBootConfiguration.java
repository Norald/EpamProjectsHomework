package com.epam.homework;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.batch.operations.JobRestartException;

@Configuration
@EnableBatchProcessing
@ComponentScan({"com.epam.homework"})
public class NoBootConfiguration {
    private static final Logger LOG = LogManager.getLogger(BatchConfig.class.getName());

    public static void main(String[] args) throws BeansException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(NoBootConfiguration.class);
        context.register(BatchConfig.class);

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean("exportUserJob", Job.class);

        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            LOG.warn(e.getMessage());
        } catch (JobRestartException e) {
            LOG.warn(e.getMessage());
        } catch (JobInstanceAlreadyCompleteException e) {
            LOG.warn(e.getMessage());
        } catch (JobParametersInvalidException e) {
            LOG.warn(e.getMessage());
        } catch (org.springframework.batch.core.repository.JobRestartException e) {
            LOG.warn(e.getMessage());
        }
    }

}
