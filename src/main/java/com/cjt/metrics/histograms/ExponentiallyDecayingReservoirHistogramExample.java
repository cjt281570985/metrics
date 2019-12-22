package com.cjt.metrics.histograms;

import com.codahale.metrics.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * @Author: chenjt
 * @Description: 指数衰减的方式
 * @Date: Created 2019-10-06 21:25
 */
public class ExponentiallyDecayingReservoirHistogramExample
{
    private final static MetricRegistry registry = new MetricRegistry();
    private final static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private final static Histogram histogram = new Histogram(new ExponentiallyDecayingReservoir());

    public static void main(String[] args)
    {
        reporter.start(10, TimeUnit.SECONDS);
        registry.register("", histogram);
        while (true)
        {
            doSearch();
            randomSleep();
        }
    }

    private static void doSearch()
    {
        histogram.update(ThreadLocalRandom.current().nextInt(10));
    }

    private static void randomSleep()
    {
        try
        {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
