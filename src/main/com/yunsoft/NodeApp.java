package com.yunsoft;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;

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

    public void connect() {
        System.out.println("NodeApp: " + mName + "::connect");
        try{
            MqttConnectOptions options = new MqttConnectOptions();
            //options.setUserName("sabioguru");
            //options.setPassword("5tkatjd!".toCharArray());
            mMemoryPersistence = new MemoryPersistence();
            String serverURI = "tcp://mqtt.eclipse.org:1883";

            mClientId = MqttAsyncClient.generateClientId();
            mClient = new MqttAsyncClient(serverURI, mClientId, mMemoryPersistence);
            // I want to use this instance as the callback
            mClient.setCallback(this);
            mConnectToken = mClient.connect(options, null, this);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return ((mClient!=null) && (mClient.isConnected()));
    }

    public MessageActionListener publishTextMessage(String messageText){
        byte[] bytesMessage;
        try{
            bytesMessage = messageText.getBytes(ENCODING);
            MqttMessage message;
            message = new MqttMessage(bytesMessage);
            String userContext = "ListeningMessage";
            MessageActionListener actionListener = new MessageActionListener(TOPIC, messageText, userContext);
            mClient.publish(TOPIC, message, userContext, actionListener);
            return actionListener;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }catch (MqttException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        if(asyncActionToken.equals(mConnectToken)){
            System.out.println( String.format("<MQTT> %s successfully connected", mName));

            try{
                mSubscribeToken = mClient.subscribe(TOPIC, QoS, null, this);
            } catch (MqttException e){
                e.printStackTrace();
            }
        }else if(asyncActionToken.equals(mSubscribeToken)){
            System.out.println(String.format("<MQTT> subscribed to the %s topic", mName, TOPIC));
            publishTextMessage(String.format("<MQTT> %s is listening", mName));
        }
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public void connectionLost(Throwable cause) {
        // The MQTT client lost the connection
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
