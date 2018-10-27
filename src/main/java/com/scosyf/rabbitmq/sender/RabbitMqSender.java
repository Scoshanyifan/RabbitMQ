package com.scosyf.rabbitmq.sender;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scosyf.rabbitmq.common.MQMessageDO;


@Component("mqSender")
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback, MqSender {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置1次
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.warn("callback succ,correlationData=" + correlationData);
        } else {
            log.warn("callback fail,correlationData=" + correlationData + ",cause=" + cause);
        }
    }

    @Override
    public void send(MQMessageDO content) {
//        MqChannelEnum channel = content.getChannel();
//        send(content, channel.getExchange(), channel.getRouteKey());
    }

    @Override
    public void send(MQMessageDO content, String routingKey) {
//        MqChannelEnum channel = content.getChannel();
//        if (channel != null) {
//            send(content, channel.getExchange(), routingKey);
//        } else {
//            sendByRouting(content, routingKey);
//        }
    }

    @Override
    public void send(MQMessageDO content, String exchange, String routingKey) {
        send(content, exchange, routingKey, UUID.randomUUID().toString());
    }

    @Override
    public void send(MQMessageDO content, String exchange, String routingKey, String requestId) {
        CorrelationData correlationId = new CorrelationData(requestId);
        content.setRequestId(requestId);

        log.warn(String.format(">>>start to send,exchange=%s,routingKey=%s,correlationId=%s", exchange, routingKey, correlationId));
        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationId);
    }

//    private void sendByRouting(MQMessageDO content, String routingKey) {
//        String id = UUID.randomUUID().toString();
//        CorrelationData correlationId = new CorrelationData(id);
//        content.setRequestId(id);
//
//        log.warn(String.format(">>>start to send,routingKey=%s,correlationId=%s", routingKey, correlationId));
//        rabbitTemplate.convertAndSend(routingKey, content, correlationId);
//    }
}
