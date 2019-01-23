package rsoi.lab2.gateway.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import rsoi.lab2.gateway.model.FlightInfo;
import rsoi.lab2.gateway.redis.model.Task;
import rsoi.lab2.gateway.redis.model.Topic;

import java.util.UUID;

@Service
public class RetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final MessagePublisher messagePublisher;

    @Autowired
    public RetryService(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    void retry(Task task, String nextTopicIfFailed) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.postForEntity(task.getUrl(), task.getRequestData(), String.class);

        } catch (Exception e) {
            logger.warn("Failed to execute task: " + task.getId());

            messagePublisher.publish(nextTopicIfFailed, task);
        }
    }
}