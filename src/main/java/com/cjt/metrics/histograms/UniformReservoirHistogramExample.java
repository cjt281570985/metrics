package com.cjt.metrics.histograms;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.UniformReservoir;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * @Author: chenjt
 * @Description: 统一水库
 * @Date: Created 2019-10-06 19:55
 *
 * UniformReservoir-Histogram
 *              count = 40
 *                min = 0
 *                max = 9
 *               mean = 5.40
 *             stddev = 2.92
 *             median = 6.00
 *               75% <= 8.00
 *               95% <= 9.00
 *               98% <= 9.00
 *               99% <= 9.00
 *             99.9% <= 9.00
 */
public class UniformReservoirHistogramExample
{
    private final static MetricRegistry registry = new MetricRegistry();
    private final static Histogram histogram = new Histogram(new UniformReservoir()); //水库默认存 DEFAULT_SIZE = 1028;

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
        registry.register("UniformReservoir-Histogram", histogram); //传进一个水库..
        while (true)
        {
            doSearch();
            randomSleep();
        }
    }

    private static void doSearch()
    {
        histogram.update(ThreadLocalRandom.current().nextInt(10)); //拿到一些随机值,放进水库里.
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
