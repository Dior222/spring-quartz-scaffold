package com.example.job;

import com.example.config.ConfigureQuartz;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JobWithCronTrigger
 *
 * @author liufenglin
 * @email fenglin_liu@hansight.com
 * @date 17/1/23
 */
@Component
@DisallowConcurrentExecution
public class JobWithCronTrigger implements Job {
    private static final Logger logger = LoggerFactory.getLogger(JobWithCronTrigger.class);

    @Value("${cron.frequency.jobwithcrontrigger: */5 * * * * ?}")
    private String cron;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("trigger simple cron job...");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8999/ping");
        try {
            HttpResponse response = client.execute(httpGet);
            logger.info("visiting url : http://localhost:8999/pin, statusCode : {} ", response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            logger.error("Error visiting url , {}", e);
        }

        try {
            client.close();
            client = null;
        } catch (IOException e) {
            logger.error("Error shutting down http client ! {}", e);
        }

    }

    @Bean(name = "jobWithCronTriggerBean")
    public JobDetailFactoryBean sampleJob() {
        return ConfigureQuartz.createJobDetail(this.getClass());
    }

    @Bean(name = "jobWithCronTriggerBeanTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithCronTriggerBean") JobDetail jobDetail) {
//        return ConfigureQuartz.createTrigger(jobDetail,frequency);
        return ConfigureQuartz.createCronTrigger(jobDetail, cron);
    }
}
