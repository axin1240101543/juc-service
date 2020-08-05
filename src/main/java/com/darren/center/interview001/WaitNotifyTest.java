package com.darren.center.interview001;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * wait + notify
 *
 * @author : Darren
 * @date : 2020年08月03日 16:23:26
 **/
public class WaitNotifyTest {

    List<Object> list = new ArrayList<>();

    public void add(Object obj){
        list.add(obj);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        WaitNotifyTest test = new WaitNotifyTest();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock){
                System.out.println("t2启动");
                if (test.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println("t1启动");
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    test.add(new Object());
                    System.out.println("add:" + i);
                    if (test.size() == 5){
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"t1").start();
    }

}
