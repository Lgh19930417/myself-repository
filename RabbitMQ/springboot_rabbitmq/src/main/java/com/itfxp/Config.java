package com.itfxp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    public static final String QUEUE_EMAIL="email";
    public static final String QUEUE_SMS="sms";
    public static final String EXCHANGE_TYPE="topic";
    @Bean(EXCHANGE_TYPE)
    public Exchange getExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TYPE).durable(true).build();
    }
    @Bean(QUEUE_EMAIL)
    public Queue getEmailQueue(){
        return new Queue(QUEUE_EMAIL);
    }
    @Bean(QUEUE_SMS)
    public Queue getSmsQueue(){
        return new Queue(QUEUE_SMS);
    }
    @Bean
    public Binding getBindingBetweenExchangeAndEmailQueue(@Qualifier(QUEUE_EMAIL)Queue queue
            ,@Qualifier(EXCHANGE_TYPE)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("#.email.#").noargs();
    }
    @Bean
    public Binding getBindingBetweenExchangeAndSmsQueue(@Qualifier(QUEUE_SMS)Queue queue
            ,@Qualifier(EXCHANGE_TYPE)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("#.sms.#").noargs();
    }
}
