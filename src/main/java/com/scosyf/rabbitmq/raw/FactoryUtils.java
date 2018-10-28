package com.scosyf.rabbitmq.raw;

import com.rabbitmq.client.ConnectionFactory;

public class FactoryUtils {

    public static ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);//默认端口5672
        factory.setUsername("guest");
        factory.setPassword("guest");
//        factory.setVirtualHost("vhost_study");
        return factory;
    }
    
}
