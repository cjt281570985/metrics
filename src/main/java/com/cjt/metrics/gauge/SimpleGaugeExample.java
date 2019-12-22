package com.cjt.metrics.gauge;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description: 2. 应用在统计上线,下线   多少访问量
 * 此处监测队列长度
 * @Date: Created 2019-10-05 15:40
 */
public class SimpleGaugeExample
{
    private static final MetricRegistry metricRegistry = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    private static final BlockingDeque<Long> queue = new LinkedBlockingDeque<>(1_000);

    public static void main(String[] args)
    {

        metricRegistry.register(MetricRegistry.name(SimpleGaugeExample.class, "queue-size"), (Gauge<Integer>) queue::size);

        reporter.start(1, TimeUnit.SECONDS);

        new Thread(() ->
        {
            for (; ; )
            {
                randomSleep();
                queue.add(System.nanoTime());
            }
        }).start();

        new Thread(() ->
        {
            for (; ; )
            {
                randomSleep();
                queue.poll();
            }
        }).start();
    }

    private static void randomSleep()
    {
        try
        {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(6));
        } catch (InterruptedException e)
        {
        }
    }
}
