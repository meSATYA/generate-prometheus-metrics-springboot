package com.mesatya.genprommetrics;

import io.prometheus.client.Counter;
import io.prometheus.client.CollectorRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
    private final Counter ordersCount;

    public CounterController(CollectorRegistry collectorRegistry) {
        ordersCount = Counter.build()
                .name("order_count")
                .help("Number of Orders Placed")
                .register(collectorRegistry);
    }

    @GetMapping(value = "/orders")
    public String orders() {
        ordersCount.inc(1);
        System.out.println(ordersCount.get());
        return ("Order Placed Successfully. Current value of order_count is: " + ordersCount.get() ) ;
    }

}
