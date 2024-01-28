package com.mesatya.genprommetrics;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@RestController
public class HistogramController {

    private final Histogram requestDuration;
    public HistogramController(CollectorRegistry collectorRegistry) {
        requestDuration = Histogram.build()
                .name("request_duration")
                .help("Time taken by the HTTP Request")
                .register(collectorRegistry);
    }

    @GetMapping(value = "/wait")
    public String httpWait() throws InterruptedException {
        Histogram.Timer timer = requestDuration.startTimer();

        long sleepDuration = Double.valueOf(Math.floor(Math.random() * 10 * 1000)).longValue();
        sleep(sleepDuration);

        timer.observeDuration();

        return String.format("Your current wait time is: " + sleepDuration + "ms") ;
    }
}
