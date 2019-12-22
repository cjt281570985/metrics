package com.cjt.metrics.counter;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description: 6.
 * @Date: Created 2019-10-06 13:55
 */
public class CounterExample
{
    private static final MetricRegistry metricRegistry = new MetricRegistry();
    private static final BlockingDeque<Long> queue = new LinkedBlockingDeque<>(1_000);

    private static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS) //将速率转换为给定的时间单位
                .convertDurationsTo(TimeUnit.SECONDS) //将持续时间转换为给定的时间单位。
                .build();
        reporter.start(2, TimeUnit.SECONDS);
    }

    public static void main(String[] args)
    {
        startReport();
        Counter counter = metricRegistry.counter("queue-count", Counter::new);

        new Thread(() ->
        {
            for (; ; )
            {
                randomSleep();
                queue.add(System.nanoTime());
                counter.inc();
            }
        }).start();

        new Thread(() ->
        {
            for (; ; )
            {
                randomSleep();
                if (queue.poll() != null) {
                    counter.dec();
                }
            }
        }).start();
    }

    private static void randomSleep()
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
        } catch (InterruptedException e)
        {
        }
    }
}
