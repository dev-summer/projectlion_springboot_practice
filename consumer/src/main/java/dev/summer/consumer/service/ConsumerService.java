package dev.summer.consumer.service;

import com.google.gson.Gson;
import dev.summer.consumer.model.JobProcess;
import dev.summer.consumer.model.JobRequest;
import dev.summer.consumer.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "boot.amqp.worker-queue")
public class ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private final RedisRepository redisRepository;
    private final Gson gson;

    public ConsumerService(@Autowired RedisRepository redisRepository,
                           @Autowired Gson gson) {
        this.redisRepository = redisRepository;
        this.gson = gson;
    }

    @RabbitHandler
    public void receive(String message) throws InterruptedException {
        logger.info("Received: {}", message);
        String jobId;
        try {
            JobRequest newJob = gson.fromJson(message, JobRequest.class);
            jobId = newJob.getJobId();

            JobProcess jobProcess = new JobProcess();
            jobProcess.setId(newJob.getJobId());
            jobProcess.setMessage("Job being processed");
            jobProcess.setStatus(1);
            jobProcess.setResult("");
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
        Thread.sleep(5000);
        JobProcess jobProcess = new JobProcess();
        jobProcess.setId(jobId);
        jobProcess.setMessage("Finished");
        jobProcess.setStatus(0);
        jobProcess.setResult("Success");
        redisRepository.save(jobProcess);
    }
}
