package com.itfxp.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SubscribeQueue {
    //定义交换机
private static final String Exchange="exchange_fanout_inform";
//定义队列一
    private static final String QUEUE_SMS="queue-sms";
    //定义队列二
    private static final String QUEUE_EMAIL="queue_email";
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
            //声明交换机
            channel.exchangeDeclare(Exchange, BuiltinExchangeType.FANOUT);
            //声明队列
            channel.queueDeclare(QUEUE_SMS,true,false,false,null);
            channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);
            //队列绑定
            channel.queueBind(QUEUE_SMS,Exchange,"");
            channel.queueBind(QUEUE_EMAIL,Exchange,"");
            //定义要发送的消息
            String msg="家人们好";

            //消息发送
            channel.basicPublish(Exchange,"",null,msg.getBytes());

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
