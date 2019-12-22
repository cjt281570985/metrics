package com.cjt.metrics.histograms;

import com.codahale.metrics.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * @Author: chenjt
 * @Description: 滑动窗口, 更适合做最近一段时间
 * @Date: Created 2019-10-06 22:25
 */
public class SlidingTimeWindowReservoirHistogramExample
{
    private final static MetricRegistry registry = new MetricRegistry();
    private final static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private final static Histogram histogram = new Histogram(new SlidingTimeWindowReservoir(30, TimeUnit.SECONDS));

    public static void main(String[] args)
    {
        reporter.start(10, TimeUnit.SECONDS);
        registry.register("SlidingTimeWindowReservoir-Histogram", histogram);
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
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
