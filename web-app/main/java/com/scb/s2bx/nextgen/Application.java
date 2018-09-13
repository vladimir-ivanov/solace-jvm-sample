package com.scb.s2bx.nextgen;

import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        try {
            final JCSMPProperties properties = new JCSMPProperties();
            properties.setProperty(JCSMPProperties.HOST,"tcp://vmr-mr8v6yiwid1f.messaging.solace.cloud:21216");
            properties.setProperty(JCSMPProperties.USERNAME,"solace-cloud-client");
            properties.setProperty(JCSMPProperties.PASSWORD,"dui6httte9qt6r4jiapn0s2bth");
            properties.setProperty(JCSMPProperties.VPN_NAME,"msgvpn-85s7yc3aep");
            final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);

            session.connect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        SpringApplication.run(Application.class, args);
    }

}
