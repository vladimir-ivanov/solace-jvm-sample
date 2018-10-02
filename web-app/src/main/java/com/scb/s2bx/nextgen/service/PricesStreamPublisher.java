package com.scb.s2bx.nextgen.service;

import com.scb.s2bx.nextgen.service.solacePubSub.PricePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PricesStreamPublisher {

    private static final int ORIGIN = 10;
    private static final int BOUND = 11;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private PricePublisher pricePublisher;

    public PricesStreamPublisher(PricePublisher pricePublisher) {
        this.pricePublisher = pricePublisher;
    }

    public void start(int interval) {
        log.info("starting timer task with interval %", interval);

        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    double random = ThreadLocalRandom.current().nextDouble(ORIGIN, BOUND);
                    pricePublisher.next(random);

                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, interval);
    }

    public void cancel() {
        log.info("cancelling prices");
        timerTask.cancel();
    }

}
