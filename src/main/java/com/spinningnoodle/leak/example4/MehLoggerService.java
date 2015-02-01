package com.spinningnoodle.leak.example4;

import java.util.concurrent.*;

/**
 * Created by Freddy on 2/1/2015.
 */
public class MehLoggerService {
    BlockingQueue<FatMessage> loggingQueue = new LinkedBlockingQueue<>(100_000_000);
    public MehLoggerService() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread myThread = new Thread(r);
                myThread.setName("Logging Thread");
                myThread.setDaemon(true);
//                myThread.setPriority(Thread.MIN_PRIORITY);
                return myThread;
            }
        });
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                saveLog();
            }
        },1000,500,TimeUnit.MILLISECONDS);
    }

    long counter = 0;
    private void saveLog() {
        for (int i =0;i < 1000;i++) {
            if (loggingQueue.isEmpty()) return;
            loggingQueue.poll();
            counter++;
            if (counter % 100_000 == 0) System.out.println("Logged :" + counter);
        }
    }


    public void log(String s) {
        try {
            loggingQueue.put(new FatMessage(s));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class FatMessage {
        final String message;
        final byte[] additionalPadding = new byte[5000];

        private FatMessage(String message) {
            this.message = message;
        }
    }
}
