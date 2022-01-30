package com.itfxp.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
public class HeaderQueue {
    //定义队列名
    private static final String QUEUE_SMS="QUEUE_SMS";
    private static final String QUEUE_EMAIL="QUEUE_EMAIL";
    //定义交换机名
    private static final String EXCHANGE_TYPE_HEADER="EXCHANGE_TYPE_HEADER";
    //创建连接工厂
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
        try {
            //获取连接
           connection = factory.newConnection();
            //获取频道
            channel = connection.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_TYPE_HEADER, BuiltinExchangeType.HEADERS);
            //声明队列
            channel.queueDeclare(QUEUE_SMS,true,false,false,null);
            channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);
            //创建map集合

            HashMap<String, Object> header_email = new HashMap<String, Object>();
            header_email.put("inform_type","queue_email");
            HashMap<String ,Object> header_sms=new HashMap<String, Object>();
            header_sms.put("inform_type","queue_sms");
            //队列绑定
            channel.queueBind(QUEUE_EMAIL,EXCHANGE_TYPE_HEADER,"",header_email);
            channel.queueBind(QUEUE_SMS,EXCHANGE_TYPE_HEADER,"",header_sms);
            //创建BasicProperties对象
            AMQP.BasicProperties.Builder builder=new AMQP.BasicProperties.Builder();
            AMQP.BasicProperties email_properties = builder.headers(header_email).build();
            AMQP.BasicProperties.Builder builder1 = new AMQP.BasicProperties.Builder();
            AMQP.BasicProperties sms_properties = builder1.headers(header_sms).build();
            //通知
            String msg_sms="sms message";
            String msg_email="email message";
            //发送消息
            channel.basicPublish(EXCHANGE_TYPE_HEADER,"",email_properties,msg_email.getBytes());
            channel.basicPublish(EXCHANGE_TYPE_HEADER,"",sms_properties,msg_sms.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            //断开连接
            if (channel!=null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
