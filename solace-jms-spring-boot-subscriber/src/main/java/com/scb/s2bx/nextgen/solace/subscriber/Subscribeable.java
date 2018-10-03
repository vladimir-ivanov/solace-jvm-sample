package com.scb.s2bx.nextgen.solace.subscriber;

import javax.jms.MessageListener;

public interface Subscribeable {

    void subscribe(String topic, Integer sessionId, MessageListener messageListener);

    void unsubscribe(String topic, Integer sessionId);

}
