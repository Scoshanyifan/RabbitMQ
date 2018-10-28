package com.scosyf.rabbitmq.nativeImpl;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TopicRecv {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = FactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //获取随机队列名，但貌似因为是amq.开头会报错，不建议使用
        String queueName = TopicSend.QUEUE_TOPIC; // channel.queueDeclare().getQueue();
        
        //TODO 第一次必须先启动消费者recv，且开启这句，用于创建队列，然后再去生产者sned那发送消息
        //     测试的时候，可能会因为收发太快导致，马上被消费掉使得打印信息没有，启动recv后慢点来
//        channel.queueDeclare(queueName, true, false, false, null);
        
        //声明交换机
        channel.exchangeDeclare(TopicSend.EXCHANGE_NAME_TOPIC, ExchangeTypes.TOPIC, true);
        
        //TODO topic下，recv消费者声明routing_key用正则模糊，sned生产者必须是完整的
        for (int idx = 0; idx < TopicSend.ROUTING_KEYS_PATTERN.length; idx++) {
            String rk = TopicSend.ROUTING_KEYS_PATTERN[idx];
            channel.queueBind(queueName, TopicSend.EXCHANGE_NAME_TOPIC, rk);
            System.out.println("Waiting for msg, " + " queue: " + queueName + ", bind rk: " + rk);
        }
        
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, 
                    AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("recv, routing key: " + envelope.getRoutingKey() + ", Received: " + message);
                //手动确认
                //getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //TODO 必须为true
        boolean autoAck = true; 
        channel.basicConsume(queueName, autoAck, consumer);
    }
}
