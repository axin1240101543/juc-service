package com.darren.center.interview004;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * <h3>juc-service</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2020年08月05日 21:02:26
 **/
public class ForkJoinPoolTest {

    /**
     * 100W个数字的数组
     */
    static int[] nums = new int[1000000];
    static Random random = new Random();
    /**
     * 每个分叉的最大值
     */
    static int MAX_NUM = 50000;

    long sum = 0L;

    static {

        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(100);
        }

        System.out.println("---" + Arrays.stream(nums).sum());
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool forkJoinPool1 = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        forkJoinPool1.execute(task);

        ForkJoinPool forkJoinPool2 = new ForkJoinPool();
        AddTaskRet addTaskRet = new AddTaskRet(0, nums.length);
        forkJoinPool2.execute(addTaskRet);
        Long result = addTaskRet.join();
        System.out.println(result);

        System.in.read();
    }

    /**
     * 无返回值
     */
    static class AddTask extends RecursiveAction{

        /**
         * 每个分叉开始值，结束值
         */
        int start, end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            //TODO compute data
            if (end - start <= MAX_NUM){
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println("from:" + start + " to: " + end + " = " + sum);
            }else {

                int middle = start + (end -start)/2;

                AddTask addTask1 = new AddTask(start, middle);
                AddTask addTask2 = new AddTask(middle, end);
                addTask1.fork();
                addTask2.fork();
            }
        }
    }

    /**
     * 有返回值
     */
    static class AddTaskRet extends RecursiveTask<Long>{

        int start, end;

        public AddTaskRet(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM){
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }

            int middle = start + (end -start)/2;

            AddTaskRet addTask1 = new AddTaskRet(start, middle);
            AddTaskRet addTask2 = new AddTaskRet(middle, end);
            addTask1.fork();
            addTask2.fork();

            return addTask1.join() + addTask2.join();
        }
    }

}

