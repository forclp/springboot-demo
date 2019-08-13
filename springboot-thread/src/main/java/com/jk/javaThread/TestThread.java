/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: TestThread
 * Author:   clp
 * Date:     2019/8/12 11:53
 * Description: 线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.javaThread;

/**
 * 〈一句话功能简述〉<br> 
 * 〈线程〉
 *
 * @author clp
 * @create 2019/8/12
 * @since 1.0.0
 */
public class TestThread {
    public static void main(String[] args) {
        /*RunnableDemo R1=new RunnableDemo("Thread-1");
        R1.start();

        RunnableDemo R2=new RunnableDemo("Thread-2");
        R2.start();
*/
        ThreadDemo T1=new ThreadDemo("Thread-3");
        T1.start();

        ThreadDemo T2=new ThreadDemo("Thread-4");
        T2.start();
    }
}
