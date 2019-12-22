package com.cjt.metrics.gauge;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description: 4. 在指定的时间内,直接从缓存中拿数据,超过后再查库
 * @Date: Created 2019-10-05 23:55
 */
public class CachedGaugeExample
{
    private final static MetricRegistry registry = new MetricRegistry();
    private final static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    public static void main(String[] args) throws InterruptedException
    {
        reporter.start(3, TimeUnit.SECONDS);
        registry.gauge("cached-db-size", () -> new CachedGauge<Long>(10, TimeUnit.SECONDS)
        {
            @Override
            protected Long loadValue()
            {
                return queryFromDB();
            }
        });

        System.out.println("============" + new Date());
        Thread.currentThread().join();
    }

    private static long queryFromDB()
    {
        System.out.println("====queryFromDB=====");
        return System.currentTimeMillis();
    }
}