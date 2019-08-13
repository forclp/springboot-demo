/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: ThreadPoolConfig
 * Author:   clp
 * Date:     2019/8/12 20:35
 * Description: springboot 整合线程池
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.springbootThread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 〈一句话功能简述〉<br> 
 * 〈springboot 整合线程池〉
 *
 * @author clp
 * @create 2019/8/12
 * @since 1.0.0
 */

@Configuration    //springboot  启动 认为这个类是配置
@EnableAsync   //并发编程
public class ThreadPoolConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(100);
        // 设置最大线程数
        executor.setMaxPoolSize(150);
        // 设置队列容量
        executor.setQueueCapacity(100000);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("thread-");
        //是否允许核心线程数空闲超时关闭  默认关闭超过核心线程数
        executor.setAllowCoreThreadTimeOut(true);
        // 设置拒绝策略rejection-policy：当pool已经达到max size的时候，如何处理新任务 CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }




}
