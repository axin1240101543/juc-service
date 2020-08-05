package com.darren.center.interview003;

import java.util.concurrent.locks.LockSupport;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 要求用线程顺序打印A1B2C3....Z26
 *
 * LockSupport
 *
 * @author : Darren
 * @date : 2020年08月04日 09:13:45
 **/
public class LockSupportTest {

    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        char[] c1 = "1234567".toCharArray();
        char[] c2 = "ABCDEFG".toCharArray();


        t1 = new Thread(()->{
            for (char c : c1) {
                //上来先打印，然后唤醒t2打印，嘴周阻塞自己
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(t2);
            }
        },"t1");

        t2 = new Thread(()->{
            for (char c : c2) {
                //上来先阻塞自己，等待t1唤醒，然后打印，最后去唤醒t1
                System.out.println(c);
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        },"t1");

        t1.start();
        t2.start();
    }

}
