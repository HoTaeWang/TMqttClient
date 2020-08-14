package com.yunsoft;

import junit.framework.TestCase;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.junit.After;
import org.junit.Before;

public class MessageBrokerTest extends TestCase implements MessageBrokerTestConstants, MqttCallback {
    private static final MqttDefaultFilePersistence DATA_STORE = new MqttDefaultFilePersistence(TMP_DIR);
    private MqttClient mClient;
    private String mExpectedResult;
    private String mResult;

    @Before
    public void setUp() throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        mClient = new MqttClient(BROKER_URL, CLIENT_ID, DATA_STORE);
        mClient.setCallback(this);
        mClient.connect(options);
    }

    @After
    public void tearDown() throws Exception{
        if(mClient != null && mClient.isConnected()){
            mClient.disconnect();
            while (mClient.isConnected()){
                Thread.sleep(TIMEOUT);
            }
        }
    }

    public void testBasics() throws Exception {
        System.out.println("<><> Check basic pub/sub on QOS 0");
        mExpectedResult = "Hello Message Broker on QOS 0";
        mResult = null;
        mClient.subscribe("1/2/3");
        mClient.publish("1/2/3", mExpectedResult.getBytes(), 0, false);
        Thread.sleep(TIMEOUT);
        assertEquals(mExpectedResult, mResult);
        assertTrue(mClient.getPendingDeliveryTokens().length == 0);
        mClient.unsubscribe("1/2/3");

        System.out.println("<><> Check basic pub/sub on QOS 1");
        mExpectedResult = "Hello Message Broker on QOS 1";
        mResult = null;
        mClient.subscribe("a/b/c");
        mClient.publish("a/b/c", mExpectedResult.getBytes(), 1, false);
        Thread.sleep(TIMEOUT);
        assertEquals(mExpectedResult, mResult);
        assertTrue(mClient.getPendingDeliveryTokens().length == 0);
        mClient.unsubscribe("a/b/c");

        System.out.println("<><> Check basic pub/sub on QOS 2");
        mExpectedResult = "Hello Message Broker on QOS 2";
        mResult = null;
        mClient.subscribe("C/B/A");
        mClient.publish("C/B/A", mExpectedResult.getBytes(), 2, false);
        Thread.sleep(TIMEOUT);
        assertEquals(mExpectedResult, mResult);
        assertTrue(mClient.getPendingDeliveryTokens().length == 0);
        mClient.unsubscribe(("C/B/A"));
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.err.println("<Message Broker> Connection Lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.err.println("Message Arrived on <" + topic + "> with "+ new String(message.getPayload()));
        mResult = new String(message.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.err.println("Delivery Complete: " + token.getMessageId());
    }
}
