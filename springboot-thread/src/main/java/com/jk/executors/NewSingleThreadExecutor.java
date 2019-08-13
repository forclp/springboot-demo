/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: NewSingleThreadExecutor
 * Author:   clp
 * Date:     2019/8/12 20:27
 * Description: 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，  * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，  * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。〉
 *
 * @author clp
 * @create 2019/8/12
 * @since 1.0.0
 */
public class NewSingleThreadExecutor {

    public static void main(String[] args) {

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            /*singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("newSingleThreadExecutor: " + index);
                        Thread.sleep(2*1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/


            singleThreadExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("newSingleThreadExecutor: " + index);
                        Thread.sleep(2*1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        singleThreadExecutor.shutdown();
    }




}
