package com.scb.s2bx.nextgen.service.uiSolace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class UiPublisher implements Publishable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private JmsTemplate jmsTemplate;

    public UiPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void next(int price) {
        String destination = "prices";

        log.info("Publishing JMS message to destination: {}, payload: {}", destination, price);
        jmsTemplate.convertAndSend(destination, price);
    }
}
