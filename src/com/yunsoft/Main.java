package com.yunsoft;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {


    public static void main(String[] args) {

        System.out.println("Hello Mqtt");
        String topic = "MQTT APNC";
        String content = "Message from MqttPublish";
        int qos = 2;
        String broker = "tcp://iot.eclipse.org:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try{
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connectOptions);
            System.out.println("Connected");
        }catch (MqttException e){
            e.printStackTrace();
        }

    }
}
