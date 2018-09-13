package com.scb.s2bx.nextgen.solace;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
@AutoConfigureBefore({JmsAutoConfiguration.class})
@ConditionalOnClass({ConnectionFactory.class, SolConnectionFactory.class})
@ConditionalOnMissingBean({ConnectionFactory.class})
@EnableConfigurationProperties({SolaceJmsProperties.class})
public class SolaceJmsAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SolaceJmsAutoConfiguration.class);

    @Bean
    public SolConnectionFactory solaceConnectionFactory(SolaceJmsProperties properties) {
        try {
            SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();

            connectionFactory.setHost(properties.getHost());
            connectionFactory.setUsername(properties.getClientUsername());
            connectionFactory.setPassword(properties.getClientPassword());
            connectionFactory.setVPN(properties.getMessageVpn());

            logger.info("connecting to solace on host {}", properties.getHost());
//
//            Connection connection = connectionFactory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            logger.debug("Connection established to " + session.toString());

            return connectionFactory;

        } catch (Exception e) {
            throw new SolaceJmsConfigurationException("Error occurred while creating the Solace Connection Factory", e);
        }
    }

}
