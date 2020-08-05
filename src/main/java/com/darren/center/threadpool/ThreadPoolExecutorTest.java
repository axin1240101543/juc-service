package com.darren.center.threadpool;

import java.util.concurrent.*;

/**
 * <h3>juc-service</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2020年08月05日 09:17:39
 **/
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        threadPoolExecutor.execute(()->{
            System.out.println("my threadPoolExecutor……");
        });


        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return "My callable……";
            }
        };

        Future submit = threadPoolExecutor.submit(callable);
        System.out.println(submit.get());

        threadPoolExecutor.shutdown();
    }

}
