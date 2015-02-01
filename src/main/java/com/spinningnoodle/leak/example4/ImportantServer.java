package com.spinningnoodle.leak.example4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Freddy on 2/1/2015.
 */

public class ImportantServer {
    private final MehLoggerService loggerService;

    public ImportantServer () {
        loggerService = new MehLoggerService();
    }
    public void start() {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        int numberOfCores = Runtime.getRuntime().availableProcessors() /2;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(numberOfCores, numberOfCores, 1000, TimeUnit.MILLISECONDS, workQueue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread myThread = new Thread(r);
                myThread.setName("Very Important Thread");
                return myThread;
            }
        });
        for (int i =0;i < numberOfCores;i++) {
            workQueue.add(new Runnable() {
                @Override
                public void run() {
                    int counter = 0;
                    while (true) {
                        counter ++;
                        // Do SOMETHING IMPORTANT
                        // and then log.
                        if (counter % 100_000 == 0) loggerService.log("There is an event at "+System.currentTimeMillis());
                    }
                }
            });
        }
        executor.prestartAllCoreThreads();

    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        ImportantServer server = new ImportantServer();
        server.start();

    }
}
