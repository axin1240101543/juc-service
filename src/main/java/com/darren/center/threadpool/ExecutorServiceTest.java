package com.darren.center.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h3>juc-service</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2020年08月05日 09:16:02
 **/
public class ExecutorServiceTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        ExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ExecutorService executorService3 = Executors.newWorkStealingPool();

    }

}
