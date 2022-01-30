package com.itfxp.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class HelloConsumer {
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) throws IOException, TimeoutException {

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/");
           Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE,true,false,false,null);
            channel.basicConsume(QUEUE, true, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException
                {
                    System.out.println("接受到的消息为："+new String(body));
                }

            });


    }
}
