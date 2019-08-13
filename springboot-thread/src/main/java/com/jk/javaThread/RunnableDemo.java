/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: RunnableDemo
 * Author:   clp
 * Date:     2019/8/12 11:30
 * Description: 通过实现 Runnable 接口来创建线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.javaThread;

/**
 * 〈一句话功能简述〉<br> 
 * 〈通过实现 Runnable 接口来创建线程〉
 *
 * @author clp
 * @create 2019/8/12
 * @since 1.0.0
 */
public class RunnableDemo implements Runnable{

    private Thread t;

    private String threadName;


    RunnableDemo(String name){
        threadName=name;
        System.out.println("Creating"+threadName);
    }

    @Override
    public void run() {
        System.out.println("Running " +  threadName );


        try {for(int i=4;i>0;i--){
            System.out.println("Thread: " + threadName + ", " + i);
            // 让线程睡眠一会
            Thread.sleep(50);
        }

        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");//异常输出 这个
        }

        System.out.println("Thread " +  threadName + " exiting.");//结束
    }

    public void start(){
        System.out.println("Starting " +  threadName );
        if(t==null){
            t=new Thread(this,threadName);
            t.start();
        }
    }




}
