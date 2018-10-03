package com.scb.s2bx.nextgen.solace.publisher;

import com.scb.s2bx.nextgen.solace.publisher.PricePublisher;
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

    private Timer timer;
    private PricePublisher pricePublisher;

    public PricesStreamPublisher(PricePublisher pricePublisher) {
        this.pricePublisher = pricePublisher;
    }

    public void start(int interval) {
        if (timer == null) {
            log.info("starting timer task with interval {}", interval);

            timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        double random = ThreadLocalRandom.current().nextDouble(ORIGIN, BOUND);
                        pricePublisher.next(random);

                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }, 0, interval);
        }
    }

}
