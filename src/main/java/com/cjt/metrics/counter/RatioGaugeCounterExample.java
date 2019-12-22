package com.cjt.metrics.counter;

import com.codahale.metrics.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description: 7.
 * @Date: Created 2019-10-06 13:58
 */
public class RatioGaugeCounterExample
{
    private final static MetricRegistry register = new MetricRegistry();
    private final static Counter totalCounter = new Counter();
    private final static Counter successCounter = new Counter();


    private static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(register)
                .convertRatesTo(TimeUnit.SECONDS) //将速率转换为给定的时间单位
                .convertDurationsTo(TimeUnit.SECONDS) //将持续时间转换为给定的时间单位。
                .build();
        reporter.start(10, TimeUnit.SECONDS);
    }

    public static void main(String[] args)
    {
        startReport();
        register.gauge("success-rate", () -> new RatioGauge()
        {
            @Override
            protected Ratio getRatio()
            {
                return Ratio.of(successCounter.getCount(), totalCounter.getCount());
            }
        });

        for (; ; )
        {
            shortSleep();
            business();
        }
    }

    private static void business()
    {
        //total inc
        totalCounter.inc();
        try
        {
            int x = 10 / ThreadLocalRandom.current().nextInt(6);
            successCounter.inc();
            //success inc
        } catch (Exception e)
        {
            System.out.println("ERROR");
        }
    }

    private static void shortSleep()
    {
        try
        {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(6));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
