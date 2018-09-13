package com.scb.s2bx.nextgen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class PricesStream {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Timer timer = new Timer();
    private TimerTask timerTask;

    public void start(int interval) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                log.info("prices coming");
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, interval);
    }

    public void cancel() {
        log.info("cancelling prices");
        timerTask.cancel();
    }

}
