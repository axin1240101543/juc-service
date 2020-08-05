package com.darren.center.interview002;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * <h3>juc-service</h3>
 * <p></p>
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * wait + notifyAll
 *
 * @author : Darren
 * @date : 2020年08月03日 16:56:43
 **/
public class WaitNotifyAllTest<T> {

    final LinkedList<T> lists = new LinkedList();

    final int MAX = 10;

    int count = 0;

    public synchronized void put(T o){
        //如果容量达到最大值就等待消费者消费
        while (lists.size() == MAX){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(o);
        ++count;
        //通知所有的消费者线程可以消费了
        //TODO 也会通知另外的生产者线程
        notifyAll();
    }

    public synchronized T get(){
        //如果容量中没有元素就等待生产者生产
        while (lists.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T t = lists.removeFirst();
        count--;
        //通知所有的生产者线程可以生产了
        //TODO 也会通知另外的消费者线程
        notifyAll();
        return t;
    }


    public static void main(String[] args) {
        WaitNotifyAllTest<String> test = new WaitNotifyAllTest();
        //10个消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println("消费者：" + Thread.currentThread().getName() + ":" + test.get());
                }
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //2个生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    String str = Thread.currentThread().getName() + ":" + j;
                    test.put(str);
                    System.out.println("生产者：" + str);
                }
            }, "p" + i).start();
        }
    }
}
