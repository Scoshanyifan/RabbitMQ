package com.scosyf.rabbitmq.nativeImpl;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class TopicSend {

    public final static String QUEUE_DIRECT                     = "directQueue";
    public final static String QUEUE_TOPIC                      = "topicQueue";

    public final static String EXCHANGE_NAME_FANOUT             = "exchange_fanout";
    public final static String EXCHANGE_NAME_DIRECT             = "exchange_direct";
    public final static String EXCHANGE_NAME_TOPIC              = "exchange_topic";

    public final static String[] ROUTING_KEYS_DIRECT            = { "INFO", "ERROR", "DEBUG" };
    
    public final static String[] ROUTING_KEYS_PATTERN           = { "*.key.*" };
    
    public final static String[] ROUTING_KEYS_TOPIC             = { "routing.key.study", 
                                                                    "routing.key.learn",
                                                                    "routing.lazy.study",
                                                                    "routing.lazy.learn"};
    
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = FactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        // 理解为建立在生产者/消费者和RabbitMQ服务器之间的TCP连接上的虚拟连接
        Channel channel = connection.createChannel();
        
        // 声明交换机，durable设true表示下次重启保留
        channel.exchangeDeclare(TopicSend.EXCHANGE_NAME_TOPIC, ExchangeTypes.TOPIC, true);

        for (String rk : ROUTING_KEYS_TOPIC) {
            String routingKey = rk;
            for (int i = 0; i < 1; i++) {
                String msg = "CONTENT-" + routingKey + "-" + i;
                channel.basicPublish(EXCHANGE_NAME_TOPIC, routingKey, null, msg.getBytes("UTF-8"));
                System.out.println("send message: " + msg);
            }
        }

        channel.close();
        connection.close();
    }
}
