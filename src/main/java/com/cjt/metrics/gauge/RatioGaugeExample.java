package com.cjt.metrics.gauge;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenjt
 * @Description:  3. 计算成功率 失败率
 * @Date: Created 2019-10-05 23:33
 */
public class RatioGaugeExample
{
    private final static MetricRegistry register = new MetricRegistry();

    private final static Meter totalMeter = new Meter();
    private final static Meter successMeter = new Meter();

    public static void main(String[] args)
    {
        startReport();
        //############################################################################
        register.gauge("success-rate", () -> new RatioGauge()
        {
            @Override
            protected Ratio getRatio()
            {
                return Ratio.of(successMeter.getCount(), totalMeter.getCount());
            }
        });
        //############################################################################
        for (; ; )
        {
            shortSleep();
            business();
        }
    }

    private static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(register)
                .convertRatesTo(TimeUnit.SECONDS) //将速率转换为给定的时间单位
                .convertDurationsTo(TimeUnit.MILLISECONDS) //将持续时间转换为给定的时间单位。
                .build();
        reporter.start(10, TimeUnit.SECONDS);
    }

    private static void business()
    {
        //total inc
        totalMeter.mark();
        try
        {
            int x = 10 / ThreadLocalRandom.current().nextInt(6);
            successMeter.mark();
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
