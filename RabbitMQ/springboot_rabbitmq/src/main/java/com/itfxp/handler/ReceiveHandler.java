package com.itfxp.handler;

import com.itfxp.Config;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class ReceiveHandler {

    @RabbitListener(queues = {Config.QUEUE_EMAIL})
    public void receiveEmail(String msg, Message message, Channel channel){
        System.out.println("email 消费端接收到消息："+msg);
    }

    @RabbitListener(queues = {Config.QUEUE_SMS})
    public void receiveSms(String msg, Message message, Channel channel){
        System.out.println("sms 消费端接收到消息："+msg);
    }
}