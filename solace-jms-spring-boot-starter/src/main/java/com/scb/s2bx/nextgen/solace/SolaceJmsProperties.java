package com.scb.s2bx.nextgen.solace;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("solace.jms")
public class SolaceJmsProperties {
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMessageVpn() {
        return messageVpn;
    }

    public void setMessageVpn(String messageVpn) {
        this.messageVpn = messageVpn;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    /**
     * Single [Protocol:]Host[:Port] definition or a list of appliances as documented at:
     * https://docs.solace.com/API-Developer-Online-Ref-Documentation/jms/com/solacesystems/jms/SolConnectionFactory.html#setHost(java.lang.String)
     * (see the section "Configuring Multiple Hosts for Redundancy and Failover")
     */
    private String host;

    private String messageVpn;
    private String clientUsername;
    private String clientPassword;

}
