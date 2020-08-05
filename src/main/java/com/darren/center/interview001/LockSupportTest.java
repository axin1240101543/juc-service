package com.darren.center.interview001;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * LockSupport
 *
 * @author : Darren
 * @date : 2020年08月03日 16:23:26
 **/
public class LockSupportTest {

    List<Object> list = new ArrayList<>();

    public void add(Object obj){
        list.add(obj);
    }

    public int size(){
        return list.size();
    }

    static Thread t1 = null, t2 =null;

    public static void main(String[] args) {
        LockSupportTest test = new LockSupportTest();

        t2 = new Thread(() -> {
            System.out.println("t2启动");
            LockSupport.park();
            System.out.println("t2结束");
            LockSupport.unpark(t1);
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
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        }, "t1");
        t2.start();
        t1.start();
    }

}
