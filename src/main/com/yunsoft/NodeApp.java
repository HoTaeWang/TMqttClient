package com.yunsoft;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class NodeApp implements MqttCallback, IMqttActionListener {
    public static final String COMMAND_KEY = "CMD";
    public static final String COMMAND_SEPARATOR = ":";
    public static final String TOPIC = "APNC/core";
    public static final String ENCODING = "UTF-8";
    public static final int QoS = 2;

    protected String mName;
    protected String mClientId;
    protected MqttAsyncClient mClient;
    protected MemoryPersistence mMemoryPersistence;
    protected IMqttToken mConnectToken;
    protected IMqttToken mSubscribeToken;

    public NodeApp(String name){
        mName = name;
    }
    public String getName(){
        return mName;
    }

//    public void connect() {
//        try{
//
//        }catch (MqttException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {

    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
