package com.scosyf.rabbitmq.common;

public class MarketMQMessageDO extends MQMessageDO{
    /**
     * create by kunbu
     */
    private static final long serialVersionUID = 701913783386034258L;
   
    private String goodId;
    private String msg;
    private Byte   type = 0;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MarketMessageDO [goodId=" + goodId + ", msg=" + msg + ", type=" + type + "]";
    }

}
