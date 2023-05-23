package com.ciklum.threads;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Work {

    public void runVirtual() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }
    }

    public void run() {
        try (var executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }
    }

    public void runCached() {
        try (var executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }
    }

    public void runVirtualCached() {
        try (var executor = Executors.newCachedThreadPool(Thread.ofVirtual().factory())) {
            IntStream.range(0, 100000).forEach(i -> {
                executor.submit(() -> Work.isPrime(i));
            });
        }
    }

    public static void main(String[] args) {
        long milis = System.currentTimeMillis();
        new Work().runVirtual();
        System.out.println(System.currentTimeMillis() - milis);
        //milis = System.currentTimeMillis();
        //new Work().run();
        //System.out.println(System.currentTimeMillis() - milis);
        milis = System.currentTimeMillis();
        new Work().runCached();
        System.out.println(System.currentTimeMillis() - milis);
        //milis = System.currentTimeMillis();
        //new Work().runVirtualCached();
        //System.out.println(System.currentTimeMillis() - milis);

        System.out.println(Integer.valueOf(12) == Integer.valueOf(12));
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
