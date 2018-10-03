package com.scb.s2bx.nextgen.solace.subscriber;

import com.solacesystems.jms.SolConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jms.MessageListener;
import java.util.HashMap;
import java.util.Map;

@Component
public class PriceSubscriber implements Subscribeable {

    private static Logger log = LoggerFactory.getLogger(PriceSubscriber.class);

    private SolConnectionFactory connectionFactory;
    private Map<Integer, Subscription> subscriptions = new HashMap<>();

    public PriceSubscriber(@Qualifier("subscriberConnectionFactory") SolConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void subscribe(String topic, Integer sessionId, MessageListener messageListener) {
        log.warn("subscribing to topic {}, sessionId {}", topic, sessionId);
        Subscription subscription = new Subscription(topic, messageListener, connectionFactory);

        subscriptions.put(sessionId, subscription);
    }

    public void unsubscribe(String topic, Integer sessionId) {
        subscriptions.get(sessionId).close();
    }

}