package ru.meleshin;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamingThreadsInPool implements ThreadFactory {

    private final String pattern;

    public NamingThreadsInPool(String pattern) {
        this.pattern = pattern;
    }

    private final AtomicInteger counter = new AtomicInteger(1);
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, pattern + " ¹ " + (counter.getAndIncrement()));
    }
}
