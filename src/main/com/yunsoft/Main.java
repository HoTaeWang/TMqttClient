package com.yunsoft;

public class Main {

    public static void main(String[] args) {
        drawTitle("Mqtt example");
        Publisher.invoke();

        drawTitle("Multiple connection Test");
        NodeApp app = new NodeApp("sabio");
        app.connect();

    }

    public static void drawTitle(String title){
        System.out.println("================================================================");
        System.out.println("                   " + title );
    }
}
