package com.yunsoft;

public interface MessageBrokerTestConstants {
    //public static final String BROKER_URL           = "tcp://localhost:1883";
    public static final String BROKER_URL           = "tcp://mqtt.eclipse.org:1883";
    public static final String SECURE_BROKER_URL    = "tcp://localhost:8883";
    public static final String CLIENT_ID            = "apnc-client-id";
    public static final int TIMEOUT                 = 500;
    public static final int CONNECTION_TIMEOUT      = 2000;
    public static final String TMP_DIR              = System.getProperty("java.io.tmpdir");
    public static final String MB_TOPIC             = "APNC/Core";
    public static final String MB_MESSAGE           = "CMD:APP_EVENT:Body";

}
