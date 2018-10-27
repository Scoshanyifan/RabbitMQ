package com.scosyf.rabbitmq.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息父类，用于设定消息通道，发送的消息请使用它的子类
 * 
 * @author haixiang
 *
 */
public abstract class MQMessageDO implements Serializable {
 
    private static final long serialVersionUID = 7012901608924135134L;

    private int                templateId;                             // 消息模板id
    private Map<String,String> templateDataMap;                        // 消息模板参数k/v
//    private MqChannelEnum      channel;                                // 消息通道
    private boolean            saveMsg          = true;                // 保存消息
    private String             requestId;                              // 消息唯一标识
//    private byte               receiverType = ReceiverTypeEnum.seller.getReceiverType(); // 消息接收角色,适用于ReceiverTypeEnum

//    public MqChannelEnum getChannel() {
//        return channel;
//    }
//
//    public void setChannel(MqChannelEnum channel) {
//        this.channel = channel;
//    }

    public boolean isSaveMsg() {
        return saveMsg;
    }

    public void setSaveMsg(boolean saveMsg) {
        this.saveMsg = saveMsg;
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public Map<String,String> getTemplateDataMap() {
		return templateDataMap;
	}

	public void setTemplateDataMap(Map<String,String> templateDataMap) {
		this.templateDataMap = templateDataMap;
	}

//	public byte getReceiverType() {
//		return receiverType;
//	}
//
//	public void setReceiverType(byte receiverType) {
//		this.receiverType = receiverType;
//	}
    
}
