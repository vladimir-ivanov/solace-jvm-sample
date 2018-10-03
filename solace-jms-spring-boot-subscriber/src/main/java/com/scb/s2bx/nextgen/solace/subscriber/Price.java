package com.scb.s2bx.nextgen.solace.subscriber;

public class Price {

    public Price(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

}
