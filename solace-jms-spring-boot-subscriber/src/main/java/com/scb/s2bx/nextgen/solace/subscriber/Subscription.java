package com.scb.s2bx.nextgen.solace.subscriber;

import com.solacesystems.jms.SolConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.Objects;

public class Subscription {

    private static Logger log = LoggerFactory.getLogger(PriceSubscriber.class);

    private String topic;
    private MessageListener messageListener;

    private MessageConsumer messageConsumer;
    private Session session;
    private Connection connection;

    public Subscription(String topic, MessageListener messageListener, SolConnectionFactory connectionFactory) {
        this.topic = topic;
        this.messageListener = messageListener;

        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);

            Topic t = session.createTopic(topic);
            log.info("subscribe to topic {} messageListener {}", topic, messageListener);

            messageConsumer = session.createConsumer(t);
            messageConsumer.setMessageListener(messageListener);

            connection.start();

        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }

    public void close() {
        try {
            log.info("closing subscription");

            messageConsumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, messageListener);
    }

}
