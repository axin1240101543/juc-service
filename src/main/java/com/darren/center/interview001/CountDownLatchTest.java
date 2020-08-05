package com.darren.center.interview001;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * CountDownLatch
 *
 * @author : Darren
 * @date : 2020年08月03日 16:23:26
 **/
public class CountDownLatchTest {

    List<Object> list = new ArrayList<>();

    public void add(Object obj){
        list.add(obj);
    }

    public int size(){
        return list.size();
    }

    static Thread t1 = null, t2 =null;

    public static void main(String[] args) {
        CountDownLatchTest test = new CountDownLatchTest();

        CountDownLatch c1 = new CountDownLatch(1);
        CountDownLatch c2 = new CountDownLatch(1);

        t2 = new Thread(() -> {
            System.out.println("t2启动");
            try {
                c1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2结束");
            c2.countDown();
        }, "t2");


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1 = new Thread(() -> {
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                test.add(new Object());
                System.out.println("add:" + i);
                if (test.size() == 5) {
                    c1.countDown();
                    try {
                        c2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1");
        t2.start();
        t1.start();
    }

}
