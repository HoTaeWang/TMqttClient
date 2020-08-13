package com.yunsoft;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class MessageActionListener implements IMqttActionListener {
    protected final String mMessageText;
    protected final String mTopic;
    protected final String mUserContext;

    public MessageActionListener(String topic, String messageText, String userContext){
        mMessageText = messageText;
        mTopic = topic;
        mUserContext = userContext;
    }

    public void onSuccess(IMqttToken asyncActionToken) {
        if((asyncActionToken != null) && asyncActionToken.getUserContext().equals(mUserContext)){
            System.out.println(String.format("Message '%s' published to topic '%s'", mMessageText, mTopic));
        }
    }

    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        exception.printStackTrace();
    }
}
