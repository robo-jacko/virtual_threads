package com.ciklum.threads;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Sleep {

    public void runVirtual() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
    }

    public void run() {
        try (var executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
    }

    public void runCached() {
        try (var executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
    }

    public static void main(String[] args) {
        long milis = System.currentTimeMillis();
        new Sleep().runVirtual();
        System.out.println(System.currentTimeMillis() - milis);
        milis = System.currentTimeMillis();
        new Sleep().run();
        System.out.println(System.currentTimeMillis() - milis);
        //milis = System.currentTimeMillis();
        //new Sleep().runCached();
        //System.out.println(System.currentTimeMillis() - milis);
    }
}
