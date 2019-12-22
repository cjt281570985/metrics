package com.cjt.metrics.gauge;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-10-05 23:00
 */
public class QueueManager {
    /*private final Queue queue;

    public QueueManager(MetricRegistry metrics, String name) {
        this.queue = new Queue();
        metrics.register(MetricRegistry.name(QueueManager.class, name, "size"),
                new Gauge<Integer>() {
                    @Override
                    public Integer getValue() {
                        return queue.size();
                    }
                });
    }*/
}

