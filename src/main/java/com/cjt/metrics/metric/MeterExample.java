package com.cjt.metrics.metric;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-10-05 1:47
 */
public class MeterExample {

    private final static MetricRegistry registry = new MetricRegistry();
    private final static Meter requestMeter = registry.meter("tqs");
    private final static Meter sizeMeter = registry.meter("volume");


    public static void main(String[] args) {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.MINUTES) //将速率转换为给定的时间单位
                .convertDurationsTo(TimeUnit.MINUTES).build(); //将持续时间转换为给定的时间单位。
        reporter.start(10, TimeUnit.SECONDS);

        for (; ; ) {
            handleRequest(new byte[ThreadLocalRandom.current().nextInt(1000)]);
            randomSleep();
        }
    }

    private static void handleRequest(byte[] request) {
        requestMeter.mark();
        sizeMeter.mark(request.length);
        randomSleep();
    }

    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
        }
    }

}

