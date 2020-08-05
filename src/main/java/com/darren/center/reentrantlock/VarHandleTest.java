package com.darren.center.reentrantlock;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * <h3>juc-service</h3>
 * <p>变量句柄 -> jdk1.9</p>
 *
 * VarHandle源码分析
 *
 * @author : Darren
 * @date : 2020年08月04日 09:53:32
 **/
public class VarHandleTest {

    int x = 8;

    private static VarHandle handle;

    static {
        try {
            handle = MethodHandles.lookup().findVarHandle(VarHandleTest.class, "x", int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VarHandleTest test = new VarHandleTest();
        Object o = handle.get(test);
        System.out.println(o);

        handle.set(test, 9);
        System.out.println(test.x);

        //通过这个handle可以对x的值进行CAS操作，完成原子性的线程安全操作。
        handle.compareAndSet(test, 9, 10);
        System.out.println(test.x);

        handle.getAndAdd(test, 10);
        System.out.println(test.x);
    }
}
