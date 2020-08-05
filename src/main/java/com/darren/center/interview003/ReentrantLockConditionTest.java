package com.darren.center.interview003;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 要求用线程顺序打印A1B2C3....Z26
 *
 * ReentrantLock + Condition
 *
 * @author : Darren
 * @date : 2020年08月04日 09:13:45
 **/
public class ReentrantLockConditionTest {


    public static void main(String[] args) {
        char[] c1 = "1234567".toCharArray();
        char[] c2 = "ABCDEFG".toCharArray();

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();


        new Thread(()->{
            lock.lock();
            try {
                for (char c : c2) {
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                for (char c : c1) {
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t2").start();

    }

}
