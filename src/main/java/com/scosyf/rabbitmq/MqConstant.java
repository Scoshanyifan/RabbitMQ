package com.scosyf.rabbitmq;

public interface MqConstant {

    public static final String EXCHANGE_MARKET              = "exchange_market";
    
    public static final String ROUTING_KEY_PREFIX           = "routing.key.market.";
    public static final String ROUTING_KEY_MARKET           = ROUTING_KEY_PREFIX + "*";
    public static final String ROUTING_KEY_MARKET_GOOD      = ROUTING_KEY_PREFIX + "good";
    public static final String ROUTING_KEY_MARKET_STOCK     = ROUTING_KEY_PREFIX + "stock";
    
    public static final String QUEUE_GOOD                   = "queue_good";
    public static final String QUEUE_STOCK                  = "queue_stock";
    public static final String QUEUE_MARKET                 = "queue_market";
}
