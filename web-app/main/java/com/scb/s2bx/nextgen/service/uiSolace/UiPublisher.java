package com.scb.s2bx.nextgen.service.uiSolace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class UiPublisher implements Publishable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private JmsTemplate jmsTemplate;

    public UiPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.jmsTemplate.setPubSubDomain(true);
    }

    @Override
    public void next(double price) {
        String destination = "prices";

        DecimalFormat df = new DecimalFormat("###.####");

        String formattedPrice = df.format(price);

        log.info("Publishing JMS message to destination: {}, payload: {}", destination, formattedPrice);

        jmsTemplate.convertAndSend(destination, formattedPrice);
    }
}
