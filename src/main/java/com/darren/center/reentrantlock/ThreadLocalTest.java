package com.darren.center.reentrantlock;

/**
 * <h3>juc-service</h3>
 * <p></p>
 *
 * ThreadLocal源码分析
 *
 * @author : Darren
 * @date : 2020年08月04日 10:03:49
 **/
public class ThreadLocalTest {

    static ThreadLocal tl = new ThreadLocal();

    public static void main(String[] args) {
        tl.set(new Object());
        tl.remove();
    }

}
