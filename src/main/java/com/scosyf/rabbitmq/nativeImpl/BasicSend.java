package com.scosyf.rabbitmq.nativeImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * queue的声明需要在消费者那先声明，也就是说先启动recv，再启动send，不然会收不到消息，原因未知
 *
 */
public class BasicSend {
    
    public static final String BASIC_QUEUE_NAME = "baisc_type_queue";
    
    public static final String DIRECT_EXCHANGE = "direct_exchange";
    public static final String DIRECT_ROUTING_KEY = "routing.key.direct";
    public static final String DIRECT_QUEUE_NAME = "direct_type_queue";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        
        testDirect();
//        testBasic();
    }
    
    /**
     * 用direct形式，通过routing_key精确匹配队列来转发
     * @throws IOException
     * @throws TimeoutException
     */
    public static void testDirect() throws IOException, TimeoutException {
        ConnectionFactory factory = FactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //durable设置Exchange是否持久化，即服务重启后Exchange是否仍存在，默认是非持久化的
        channel.exchangeDeclare(DIRECT_EXCHANGE, ExchangeTypes.DIRECT);
        
        String message = "Direct Message";
        channel.basicPublish(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY, null, message.getBytes());
        System.out.println("send msg is:'" + message + "'");
        
        channel.close();
        connection.close();
    }
    
    /**
     * 基础收发消息，无exchange
     * @throws IOException
     * @throws TimeoutException
     */
    public static void testBasic() throws IOException, TimeoutException {
        ConnectionFactory factory = FactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(BASIC_QUEUE_NAME, true, false, false, null);
        // 分发信息
        for (int i = 0; i < 10; i++) {
            String message = "Hello RabbitMQ" + i;
            channel.basicPublish("", BASIC_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("NewTask send '" + message + "'");
        }
        
        channel.close();
        connection.close();
    }
}
