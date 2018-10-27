package com.scosyf.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.scosyf.rabbitmq.MqConstant;
import com.scosyf.rabbitmq.common.MarketMQMessageDO;

/**
 * 原理和手动设置一样，这里作为消费者，设置exchange，routing_key，queue后自动生成
 * 
 * 这里模拟topic模式下接受到多个来源的消息，并处理
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                key = MqConstant.ROUTING_KEY_MARKET, 
                exchange = @Exchange(value = MqConstant.EXCHANGE_MARKET, durable = "true", type = ExchangeTypes.TOPIC), 
                value = @Queue(value = MqConstant.QUEUE_MARKET, durable = "true")))
public class MarketMQListener {
    
    private static Logger logger  = LoggerFactory.getLogger(MarketMQListener.class);

    @RabbitHandler
    public void onMassage(MarketMQMessageDO messageDO) {
        logger.info(">>> market received messageDO: [{}]", messageDO);
        byte type = messageDO.getType();
        if (type == 1) {
            logger.info(">>> 修改商品信息操作，msg：[{}]", messageDO.getMsg());
        } else if (type == 2) {
            logger.info(">>> 增删库存信息，msg：[{}]", messageDO.getMsg());
        } else {
            //
        }
        
    }
    
}
