package com.scb.s2bx.nextgen.solace.publisher;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@AutoConfigureBefore({JmsAutoConfiguration.class})
@ConditionalOnClass({ConnectionFactory.class, SolConnectionFactory.class})
@ConditionalOnMissingBean({ConnectionFactory.class})
@EnableConfigurationProperties({SolaceJmsPublisherProperties.class})
public class SolaceJmsPublisherAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SolaceJmsPublisherAutoConfiguration.class);

    @Autowired
    private SolaceJmsPublisherProperties properties;

    @Bean
    @Primary
    public SolConnectionFactory solacePublisherConnectionFactory() {
        try {
            SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();

            connectionFactory.setHost(properties.getHost());
            connectionFactory.setUsername(properties.getClientUsername());
            connectionFactory.setPassword(properties.getClientPassword());
            connectionFactory.setVPN(properties.getMessageVpn());

            logger.info("[publish]: connecting to solace on host {}", properties.getHost());

            return connectionFactory;

        } catch (Exception e) {
            throw new SolaceJmsPublisherConfigurationException("Error occurred while creating the Solace Connection Factory", e);
        }
    }

    @Bean
    public JmsTemplate publisherTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(solacePublisherConnectionFactory());
        return jmsTemplate;
    }

}
