//package com.cjt.metrics.gauge;
//
//import com.codahale.metrics.ConsoleReporter;
//import com.codahale.metrics.JmxAttributeGauge;
//import com.codahale.metrics.MetricRegistry;
//
//import javax.management.MalformedObjectNameException;
//import javax.management.ObjectName;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author: chenjt
// * @Description:  取消了
// * @Date: Created 2019-10-05 23:00
// */
//public class JmxAttributeGaugeExample
//{
//
//    private final static MetricRegistry registry = new MetricRegistry();
//    private final static ConsoleReporter reporter = ConsoleReporter
//            .forRegistry(registry)
//            .convertRatesTo(TimeUnit.SECONDS)
//            .convertDurationsTo(TimeUnit.SECONDS)
//            .build();
//
//    public static void main(String[] args) throws MalformedObjectNameException, InterruptedException
//    {
//        reporter.start(10, TimeUnit.SECONDS);
//        registry.register(MetricRegistry.name(JmxAttributeGaugeExample.class, "HeapMemory"), new JmxAttributeGauge(
//                new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage"
//        ));
//
//        registry.register(MetricRegistry.name(JmxAttributeGaugeExample.class, "NonHeapMemoryUsage"), new JmxAttributeGauge(
//                new ObjectName("java.lang:type=Memory"), "NonHeapMemoryUsage"
//        ));
//
//        Thread.currentThread().join();
//    }
//}