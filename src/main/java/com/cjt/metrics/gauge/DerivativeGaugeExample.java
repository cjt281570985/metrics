package com.cjt.metrics.gauge;

import com.codahale.metrics.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description: 5.
 * @Date: Created 2019-10-06 09:25
 */
public class DerivativeGaugeExample //派生出来的
{
    private final static LoadingCache<String, String> cache = CacheBuilder
            .newBuilder().maximumSize(10) //最大长度放10个cache
            .expireAfterAccess(5, TimeUnit.SECONDS) //过期时间
            .recordStats()
            .build(new CacheLoader<String, String>() //cache没数据的时候执行懒加载, 加build方法...
            {
                @Override
                public String load(String key) throws Exception
                {
                    return key.toUpperCase();
                }
            });

    private final static MetricRegistry registry = new MetricRegistry();
    private final static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();

    //第一次拿cache是不存在的,有丢失率,后面拿才有值
    //value = CacheStats{hitCount=9, missCount=1, loadSuccessCount=1, loadExceptionCount=0, totalLoadTime=2479038, evictionCount=0}
    //missCount=1, loadSuccessCount=1, loadExceptionCount=0  前2个正常情况要相等
    public static void main(String[] args) throws InterruptedException
    {
        reporter.start(10, TimeUnit.SECONDS);
        Gauge<CacheStats> cacheGauge = registry.gauge("cache-stats", () -> cache::stats);

        //由cacheGauge派生以下2个gauge
        registry.register("missCount", new DerivativeGauge<CacheStats, Long>(cacheGauge)
        {
            @Override
            protected Long transform(CacheStats stats)
            {
                return stats.missCount();
            }
        });

        registry.register("loadExceptionCount", new DerivativeGauge<CacheStats, Long>(cacheGauge)
        {
            @Override
            protected Long transform(CacheStats stats)
            {
                return stats.loadExceptionCount();
            }
        });

        while (true)
        {
            business();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void business()
    {
        cache.getUnchecked("alex");
    }
}
