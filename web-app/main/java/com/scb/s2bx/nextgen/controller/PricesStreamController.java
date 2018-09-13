package com.scb.s2bx.nextgen.controller;

import com.scb.s2bx.nextgen.service.PricesStream;
import com.scb.s2bx.nextgen.service.uiSolace.Publishable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ConfigurationException;

import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController()
@CrossOrigin(origins = "*", maxAge = 3600)
public class PricesStreamController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final int HUNDRED = 100;

    private PricesStream pricesStream;
    private Publishable uiPublisher;

    @Autowired
    public PricesStreamController(PricesStream pricesStream, Publishable publisher) {
        this.pricesStream = pricesStream;
        this.uiPublisher = publisher;
    }

    @RequestMapping(value = "/start/{interval}", method = GET)
    public String start(@PathVariable("interval") final int interval) {
        log.info("Starting prices stream with interval {}", interval);
        pricesStream.start(interval, new TimerTask() {
            @Override
            public void run() {
                log.info("prices coming");

                try {
                    double random = ThreadLocalRandom.current().nextDouble(10, 11);
                    uiPublisher.next(random);

                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        });

        return "started streaming prices with interval: " + interval;
    }


    @RequestMapping("/cancel")
    public String cancel() {
        log.info("Cancelling prices stream");
        pricesStream.cancel();
        return "cancelled prices stream";
    }

}
