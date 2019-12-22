package com.cjt.metrics.histograms;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description: 直方图  算出最小,最大,平均,中位数....
 * @Date: Created 2019-10-06 18:40
 * search-result
 *              count = 14
 *                min = 1
 *                max = 9
 *               mean = 4.13
 *             stddev = 2.56
 *             median = 3.00
 *               75% <= 6.00
 *               95% <= 9.00
 *               98% <= 9.00
 *               99% <= 9.00
 *             99.9% <= 9.00
 */
public class HistogramExample
{
    private final static MetricRegistry registry = new MetricRegistry();
    private final static Histogram histogram = registry.histogram("search-result");

    private static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS) //将速率转换为给定的时间单位
                .convertDurationsTo(TimeUnit.SECONDS) //将持续时间转换为给定的时间单位。
                .build();
        reporter.start(10, TimeUnit.SECONDS);
    }

    public static void main(String[] args)
    {
        startReport();

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