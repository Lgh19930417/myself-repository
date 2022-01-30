package com.itfxp.Producer;

import com.itfxp.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RestTemplate restTemplate;
    @Test
    public void topicQueue(){
        String msg="hahhah";
        rabbitTemplate.convertAndSend(Config.EXCHANGE_TYPE,"sms",msg);
        System.out.println("消息已经发送");
    }
    @Test
    public void remoteTest(){
        String result = restTemplate.getForObject("http://localhost:8080/first/method", String.class);
        System.out.println(result);
    }
}
