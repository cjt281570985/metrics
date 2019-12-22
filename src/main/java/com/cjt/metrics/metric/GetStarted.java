package com.cjt.metrics.metric;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-10-05 15:40
 */
import com.codahale.metrics.*;
import java.util.concurrent.TimeUnit;

//  https://metrics.dropwizard.io/4.1.0/getting-started.html
//  https://metrics.dropwizard.io/4.1.0/manual/core.html
public class GetStarted {
    private static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String args[]) {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();
        wait5Seconds();
    }

    private static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS) //将速率转换为给定的时间单位
                .convertDurationsTo(TimeUnit.MILLISECONDS) //将持续时间转换为给定的时间单位。
                .build();
        reporter.start(3, TimeUnit.SECONDS);
    }

    private static void wait5Seconds() {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
        }
    }
}
