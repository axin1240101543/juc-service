package com.darren.center.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <h3>juc-service</h3>
 * <p></p>
 *
 * ReentrantLock源码分析
 *
 * @author : Darren
 * @date : 2020年08月04日 09:46:08
 **/
public class ReentrantLockTest {


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println();
        }finally {
            lock.unlock();
        }
    }

}
