package com.scb.s2bx.nextgen.webSocket;

import com.scb.s2bx.nextgen.service.PricesStreamPublisher;
import com.scb.s2bx.nextgen.service.solacePubSub.Subscribeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private Subscribeable priceSubscriber;

    @Autowired
    private PricesStreamPublisher pricesStreamPublisher;

    @Bean
    public WebSocketHandler messageHandler() {
        return new WebSocketMessageHandler(priceSubscriber, pricesStreamPublisher);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler(), "/ws").setAllowedOrigins("*");
    }

}