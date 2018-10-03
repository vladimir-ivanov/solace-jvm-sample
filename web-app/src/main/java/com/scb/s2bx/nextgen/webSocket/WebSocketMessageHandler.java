package com.scb.s2bx.nextgen.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.s2bx.nextgen.solace.publisher.PricesStreamPublisher;
import com.scb.s2bx.nextgen.solace.subscriber.Price;
import com.scb.s2bx.nextgen.solace.subscriber.Subscribeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

public class WebSocketMessageHandler extends TextWebSocketHandler {

    private static final int JMS_PUBLISH_INTERVAL = 5000;
    private static final String JMS_TOPIC = "prices";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Subscribeable priceSubscriber;
    private PricesStreamPublisher pricesStreamPublisher;

    public WebSocketMessageHandler(Subscribeable priceSubscriber, PricesStreamPublisher pricesStreamPublisher) {
        this.priceSubscriber = priceSubscriber;
        this.pricesStreamPublisher = pricesStreamPublisher;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        cleanup(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // The WebSocket has been opened
        // I might save this session object so that I can send messages to it outside of this method

        subscribe(session);
        publish();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        // A message has been received
        log.info("Message received: " + textMessage.getPayload());
    }

    private void subscribe(WebSocketSession session) {
        priceSubscriber.subscribe(JMS_TOPIC, session.hashCode(), new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof javax.jms.TextMessage) {
                        log.info("TextMessage received: {}", ((javax.jms.TextMessage) message).getText());
                        ObjectMapper objectMapper = new ObjectMapper();

                        try {
                            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new Price(((javax.jms.TextMessage) message).getText()))));

                        } catch (Exception e) {
                            log.error(e.getMessage());
                            cleanup(session);
                        }

                    } else {
                        log.info("Message received.");
                    }

                } catch (JMSException ex) {
                    log.info("Error processing incoming message {}", ex.getMessage());
                }
            }
        });
    }

    private void publish() {
        pricesStreamPublisher.start(JMS_PUBLISH_INTERVAL);
    }

    private void cleanup(WebSocketSession session) {
        try {
            session.close();

        } catch (IOException e) {
            log.error("unable to close session {}, reason: {}", session.hashCode(), e.getMessage());
        }

        priceSubscriber.unsubscribe(JMS_TOPIC, session.hashCode());
    }

}