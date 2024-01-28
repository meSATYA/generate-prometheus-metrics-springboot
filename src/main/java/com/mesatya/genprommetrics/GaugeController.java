package com.mesatya.genprommetrics;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GaugeController {

    private final Gauge orderQueueSize;
    public GaugeController(CollectorRegistry collectorRegistry) {
        orderQueueSize = Gauge.build()
                .name("order_queue_size")
                .help("Queue Size of the Orders")
                .register(collectorRegistry);
    }

    @GetMapping(value = "/addtocart")
    public String addToCart() {
        orderQueueSize.inc(1);
        return ("Item Added Successfully. Current value of order_queue_size is: " + orderQueueSize.get() ) ;
    }

    @GetMapping(value = "/removefromcart")
    public String removeFromCart() {
        orderQueueSize.dec(1);
        return ("Item Removed Successfully. Current value of order_queue_size is: " + orderQueueSize.get() ) ;
    }
}
