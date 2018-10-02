package com.scb.s2bx.nextgen.service.solacePubSub;

import javax.jms.MessageListener;

public interface Subscribeable {

    void subscribe(String topic, MessageListener messageListener);
    void unsubscribe(String topic);

}
