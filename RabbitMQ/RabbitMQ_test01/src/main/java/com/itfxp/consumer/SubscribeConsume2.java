package com.itfxp.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SubscribeConsume2 {
    //定义队列一
    private static final String QUEUE_EMAIL="queue_email";
   //定义交换机
    private static final String Exchange="exchange_fanout_inform";

    public static void main(String[] args) {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置工厂主机
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
            channel.exchangeDeclare(Exchange, BuiltinExchangeType.FANOUT);
            //队列声明
            channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);
            //将交换机和队列进行绑定
//            channel.queueBind(QUEUE_SMS,Exchange,"");
            //定义消费方法
            channel.basicConsume(QUEUE_EMAIL,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException
                {
                    System.out.println("接收到的消息为："+new String(body));
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
