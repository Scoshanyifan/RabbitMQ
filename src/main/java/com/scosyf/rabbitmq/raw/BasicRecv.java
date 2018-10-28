package com.scosyf.rabbitmq.raw;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class BasicRecv {
    
    public static void main(String[] args) throws IOException, TimeoutException {
        testDirect();
    }
    
    public static void testDirect() throws IOException, TimeoutException {
        ConnectionFactory factory = FactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //TODO 第一次声明队列时使用
        channel.queueDeclare(BasicSend.DIRECT_QUEUE_NAME, true, false, false, null);
        channel.exchangeDeclare(BasicSend.DIRECT_EXCHANGE, ExchangeTypes.DIRECT);
        channel.queueBind(BasicSend.DIRECT_QUEUE_NAME, BasicSend.DIRECT_EXCHANGE, BasicSend.DIRECT_ROUTING_KEY);
        System.out.println("Waiting for messages...");
        
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        //ack必须true
        channel.basicConsume(BasicSend.DIRECT_QUEUE_NAME, true, consumer);
    }
    
    public static void testBasic() throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(BasicSend.BASIC_QUEUE_NAME, true, false, false, null);
        System.out.println("Worker1  Waiting for messages");

        //每次从队列获取的数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Worker1  Received '" + message + "'");
//                try {
//                    throw  new Exception();
//                    //doWork(message);
//                }catch (Exception e){
//                    channel.abort();
//                }finally {
//                    System.out.println("Worker1 Done");
//                    channel.basicAck(envelope.getDeliveryTag(),false);
//                }
            }
        };
        boolean autoAck = true;
        //消息消费完成确认
        channel.basicConsume(BasicSend.BASIC_QUEUE_NAME, autoAck, consumer);
    }
}
