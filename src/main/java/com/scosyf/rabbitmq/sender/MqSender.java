package com.scosyf.rabbitmq.sender;

import com.scosyf.rabbitmq.common.MQMessageDO;

public interface MqSender {

    public void send(MQMessageDO content);

    public void send(MQMessageDO content, String routingKey);

    public void send(MQMessageDO content, String exchange, String routingKey);

    public void send(MQMessageDO content, String exchange, String routingKey, String requestId);
}
