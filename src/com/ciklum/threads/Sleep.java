package com.ciklum.threads;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Sleep {

    public void sleepVirtual() {
        long millis = System.currentTimeMillis();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }

        System.out.printf("Virtual: %d%n", System.currentTimeMillis() - millis);
    }

    public void sleepPlatform() {
        long millis = System.currentTimeMillis();

        try (var executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }

        System.out.printf("Platform: %d%n", System.currentTimeMillis() - millis);
    }

    public void sleepPlatformPooled() {
        long millis = System.currentTimeMillis();

        try (var executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }

        System.out.printf("Platform pooled: %d%n", System.currentTimeMillis() - millis);
    }

    public static void main(String[] args) {
        Sleep worker = new Sleep();

        worker.sleepVirtual();
        worker.sleepPlatform();
        //worker.sleepPlatformPooled();
    }
}
