package com.ciklum.threads;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Work {

    public void workVirtual() {
        long milis = System.currentTimeMillis();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }

        System.out.printf("Virtual: %d%n", System.currentTimeMillis() - milis);
    }

    public void workPlatform() {
        long milis = System.currentTimeMillis();

        try (var executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }

        System.out.printf("Platform: %d%n", System.currentTimeMillis() - milis);
    }

    public void workPlatformPooled() {
        long milis = System.currentTimeMillis();

        try (var executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }

        System.out.printf("Platform pooled: %d%n", System.currentTimeMillis() - milis);
    }

    public void workVirtualPooled() {
        long milis = System.currentTimeMillis();

        try (var executor = Executors.newCachedThreadPool(Thread.ofVirtual().factory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }

        System.out.printf("Virtual pooled: %d%n", System.currentTimeMillis() - milis);
    }

    public static void main(String[] args) {
        Work worker = new Work();

        worker.workVirtual();
        worker.workPlatform();
        //worker.workPlatformPooled();
        //worker.workVirtualPooled();
    }

    public static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                return true;
            }
        }

        return false;
    }

}
