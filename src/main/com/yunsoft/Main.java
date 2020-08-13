package com.yunsoft;

import org.eclipse.paho.client.mqttv3.MqttException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        drawTitle("Mqtt example");
        Publisher.invoke();

        MultiMessaging();
    }

    public static void drawTitle(String title){
        System.out.println("================================================================");
        System.out.println("                   " + title );
    }

    public static void MultiMessaging(){
        drawTitle("Multiple connection Test");
        NodeApp master = new NodeApp("sabio");
        master.connect();
        NodeApp app1 = new NodeApp("Camera#1");
        app1.connect();
        NodeApp app2 = new NodeApp("Camera#2");
        app2.connect();

        try{
            while(true){
                try{
                    Thread.sleep(5000);
                    int r = ThreadLocalRandom.current().nextInt(1, 11);
                    if((r<5) && app1.isConnected()){
                        master.publishCommand("AppEvent", app1.getName());
                    }else if(app2.isConnected()){
                        master.publishCommand("AppEvent", app2.getName());
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(app1.isConnected()){
                try{
                    app1.mClient.disconnect();
                }catch (MqttException e){
                    e.printStackTrace();
                }
            }
            if(app2.isConnected()){
                try{
                    app2.mClient.disconnect();
                }catch (MqttException e){
                    e.printStackTrace();
                }
            }
            if(master.isConnected()){
                try{
                    master.mClient.disconnect();
                }catch (MqttException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
