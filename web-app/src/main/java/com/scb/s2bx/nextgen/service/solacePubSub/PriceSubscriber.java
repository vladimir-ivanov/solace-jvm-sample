package com.scb.s2bx.nextgen.service.solacePubSub;

import com.solacesystems.jms.SolConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class PriceSubscriber implements Subscribeable {

    private static Logger log = LoggerFactory.getLogger(PriceSubscriber.class);

    private Session session;
    private MessageConsumer messageConsumer;
    private Connection connection;
    private SolConnectionFactory connectionFactory;

    public PriceSubscriber(@Qualifier("subscriberConnectionFactory") SolConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        connectWithSession();
    }

    public void subscribe(String topic, MessageListener messageListener) {
        try {
            connectWithSession();

            Topic t = session.createTopic(topic);
            log.info("subscribe to topic %", topic);

            messageConsumer = session.createConsumer(t);
            messageConsumer.setMessageListener(messageListener);

        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }

    public void unsubscribe(String topic) {
        try {
            log.info("unsubscribe from topic %", topic);

            messageConsumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }


    private void connectWithSession() {
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);

            connection.start();

        } catch (JMSException e) {
            log.error(e.getMessage());
        }
    }
}