package com.scosyf.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scosyf.rabbitmq.common.MarketMQMessageDO;
import com.scosyf.rabbitmq.sender.MqSender;


@RestController
@RequestMapping("/mq")
public class MqController {

    private static Logger logger            = LoggerFactory.getLogger(MqController.class);
    
    @Autowired
    private MqSender mqSender;
    
    @GetMapping(value = "/good", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String mdfGoodMQ(@RequestParam(value = "name") String name) {
        logger.info("request received... ... >>> " + name);
        
        MarketMQMessageDO messageDO = new MarketMQMessageDO();
        messageDO.setMsg("good >>> " + name);
        messageDO.setType((byte) 1);
        mqSender.send(messageDO, MqConstant.EXCHANGE_MARKET, MqConstant.ROUTING_KEY_MARKET_GOOD);
        
        return "send [good mdf] success";
    }
    
    @GetMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String plusStockMQ() {
        
        MarketMQMessageDO messageDO = new MarketMQMessageDO();
        messageDO.setMsg("add stock");
        messageDO.setType((byte) 2);
        mqSender.send(messageDO, MqConstant.EXCHANGE_MARKET, MqConstant.ROUTING_KEY_MARKET_STOCK);
        
        return "send [add stock] success";
    }
}
