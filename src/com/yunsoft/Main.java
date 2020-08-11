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

        String broker = "tcp://mqtt.eclipse.org:1883";
        String secureBroker = "tcp://mqtt.eclipse.org:8883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try{
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            // set Mqtt connect options
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            // connect to broker
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connectOptions);
            System.out.println("Connected");
            // Publish a message
            System.out.println("Publish message: " + content );
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        }catch (MqttException e){
            System.out.println("Reason: " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }

    }
}
