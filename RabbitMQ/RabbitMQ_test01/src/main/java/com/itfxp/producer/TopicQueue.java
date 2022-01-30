package com.itfxp.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicQueue {
    //定义队列
    private static final String QUEUE_ERROR="QUEUE_ERROR";
    private static final String QUEUE_INFO="QUEUE_INFO";

    //定义交换机
    private static final String EXCHANGE_TYPE_TOPIC="TOPIC";

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
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_TYPE_TOPIC, BuiltinExchangeType.TOPIC);
            //声明队列
            channel.queueDeclare(QUEUE_ERROR,true,false,false,null);
            channel.queueDeclare(QUEUE_INFO,true,false,false,null);

            //将交换机和队列绑定
            channel.queueBind(QUEUE_ERROR,EXCHANGE_TYPE_TOPIC,"inf.#.error.#");
            channel.queueBind(QUEUE_INFO,EXCHANGE_TYPE_TOPIC,"inf.#.info.#");

            //创建消息
            String error="error message";
            String info="info message";
            String error_info="error_info message";
            //发送消息
            channel.basicPublish(EXCHANGE_TYPE_TOPIC,"inf.#.error.#",null,error.getBytes());
            channel.basicPublish(EXCHANGE_TYPE_TOPIC,"inf.#.info.#",null,info.getBytes());
            channel.basicPublish(EXCHANGE_TYPE_TOPIC,"inf.#.error.#.info.#",null,error_info.getBytes());
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
