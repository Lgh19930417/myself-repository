package com.itfxp.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class HeaderSonsume {
    //定义队列名
    private static final String QUEUE_SMS="QUEUE_SMS";
    private static final String QUEUE_EMAIL="QUEUE_EMAIL";
    //定义交换机名
    private static final String EXCHANGE_TYPE_HEADER="EXCHANGE_TYPE_HEADER";

    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;
        ConnectionFactory factory = new ConnectionFactory();
        //设置参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        //获取连接
        try {
            connection = factory.newConnection();
            //获取频道
            channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_TYPE_HEADER, BuiltinExchangeType.HEADERS);
            //定义hashMap
            HashMap<String ,Object> header_email=new HashMap<String, Object>();
            header_email.put("inform_type","queue_email");
            //声明队列
            channel.queueDeclare(QUEUE_SMS,true,false,false,header_email);
            //指定消费者
            channel.basicConsume(QUEUE_EMAIL,true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException
                {
                    System.out.println("传递到消息为:"+new String(body));
                }
            });



        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
