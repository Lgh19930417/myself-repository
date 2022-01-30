package com.itfxp.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicConsume {
    //定义队列
    private static final String QUEUE_ERROR="QUEUE_ERROR";
    private static final String QUEUE_INFO="QUEUE_INFO";

    public static void main(String[] args) {
        try {
            //创建工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置工厂参数
            factory.setHost("localhost");
            //设置工厂端口号
            factory.setPort(5672);
            //设置工厂用户名
            factory.setUsername("guest");
            //设置密码
            factory.setPassword("guest");
            //设置虚拟主机
            factory.setVirtualHost("/");
            //创建连接
            Connection connection = factory.newConnection();
            //创建频道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_ERROR,true,false,false,null);
            channel.queueDeclare(QUEUE_INFO,true,false,false,null);
            //定义消费方法
            channel.basicConsume(QUEUE_ERROR,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException
                {
                    System.out.println("接受的消息为："+new String(body));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
