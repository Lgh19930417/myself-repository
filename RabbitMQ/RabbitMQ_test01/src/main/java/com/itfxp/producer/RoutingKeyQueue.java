package com.itfxp.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingKeyQueue {
    //定义交换机
    private static final String EXCHANGE_TYPE_DIRECT="direct";
    //定义队列一
    private static final String QUEUE_A="queue-a";
    //定义队列二
    private static final String QUEUE_B="queue_b";

    public static void main(String[] args) {
        try {
            //创建连接工厂
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
            //获取连接对象
            Connection connection = factory.newConnection();
            //获取频道
            Channel channel = connection.createChannel();
            //交换机声明
            channel.exchangeDeclare(EXCHANGE_TYPE_DIRECT, BuiltinExchangeType.DIRECT);
            //队列声明
            channel.queueDeclare(QUEUE_A,true,false,false,null);
            channel.queueDeclare(QUEUE_B,true,false,false,null);
            //将交换机和队列绑定
            channel.queueBind(QUEUE_A,EXCHANGE_TYPE_DIRECT,QUEUE_A);
            channel.queueBind(QUEUE_B,EXCHANGE_TYPE_DIRECT,QUEUE_B);
            //发送消息
            channel.basicPublish(EXCHANGE_TYPE_DIRECT,QUEUE_A,null,"A队列发送的消息".getBytes());
            channel.basicPublish(EXCHANGE_TYPE_DIRECT,QUEUE_B,null,"B队列发送的消息".getBytes());
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
