/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: ThreadDemo
 * Author:   clp
 * Date:     2019/8/12 11:58
 * Description: 通过继承Thread来创建线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.javaThread;

/**
 * 〈一句话功能简述〉<br> 
 * 〈通过继承Thread来创建线程〉
 *
 * @author clp
 * @create 2019/8/12
 * @since 1.0.0
 */
public class ThreadDemo extends Thread{
    private Thread t;
    private String threadName;

    ThreadDemo( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}


