package com.darren.center.interview003;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 要求用线程顺序打印A1B2C3....Z26
 *
 * wait + notify
 *
 * @author : Darren
 * @date : 2020年08月04日 09:13:45
 **/
public class WaitNotifyTest {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        char[] c1 = "1234567".toCharArray();
        char[] c2 = "ABCDEFG".toCharArray();

        final Object lock = new Object();

        t1 = new Thread(()->{
            t2.start();
            synchronized (lock){
                for (char c : c2) {
                    System.out.println(c);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        },"t1");

        t2 = new Thread(()->{
            synchronized (lock){
                for (char c : c1) {
                    System.out.println(c);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                lock.notify();
            }
        },"t2");

        t1.start();
    }

}
