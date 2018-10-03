package com.scb.s2bx.nextgen.solace.subscriber;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({JmsAutoConfiguration.class})
@EnableConfigurationProperties({SolaceJmsSubscriberProperties.class})
public class SolaceJmsSubscriberAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SolaceJmsSubscriberAutoConfiguration.class);

    @Autowired
    private SolaceJmsSubscriberProperties properties;

    @Bean
    @Qualifier("subscriberConnectionFactory")
    public SolConnectionFactory solaceSubscriberConnectionFactory() {
        try {
            SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();

            connectionFactory.setHost(properties.getHost());
            connectionFactory.setUsername(properties.getClientUsername());
            connectionFactory.setPassword(properties.getClientPassword());
            connectionFactory.setVPN(properties.getMessageVpn());

            logger.info("[subscribe]: connecting to solace on host {}", properties.getHost());

            return connectionFactory;

        } catch (Exception e) {
            throw new SolaceJmsSubscriberConfigurationException("Error occurred while creating the Solace Connection Factory", e);
        }
    }

}
