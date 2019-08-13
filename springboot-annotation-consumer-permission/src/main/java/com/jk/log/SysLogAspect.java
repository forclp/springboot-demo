/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: SysLogAspect
 * Author:   clp
 * Date:     2019/8/10 10:59
 * Description: mongodb-aop--日志
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.log;

import com.jk.model.Log;
import com.jk.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;

/**
 * 〈一句话功能简述〉<br> 
 * 〈mongodb-aop--日志〉
 *
 * @author clp
 * @create 2019/8/10
 * @since 1.0.0
 */


@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Pointcut("execution(* com.jk.controller.UserController.*(..))")
    public void logPointCut(){

    }
    //前置通知
    @Before( value= "logPointCut()")
    public void myBefore(JoinPoint jp) throws UnknownHostException {//returningValue是返回值，但需要告诉spring
        if(jp.getSignature().getName().equals("zx")){
            System.out.println("《注解形式-前置通知》：目标对象："+jp.getTarget()+",方法名："+jp.getSignature().getName() +",参数列表："+  jp.getArgs().length);
            Log log = new Log();


            log.setLogip(getIp());

            String className = jp.getTarget().getClass().toString().substring(jp.getTarget().getClass().toString().lastIndexOf(".")+1);
            log.setLogname("注销日志");


            log.setLogtime(new Date());
            log.setRequerpath(jp.getTarget().getClass() +"/"+ jp.getSignature().getName());

            //log.setReturningValue(returningValue);
	    /*    //获取 目标 对象
	        jp.getTarget().getClass();

	        // 获取方法名

	        jp.getSignature().getName();

	        */
            //获取请求参数
            Object[] args = jp.getArgs();
            if (args != null) {
                StringBuffer requestParams = new StringBuffer();
                for (int i = 0; i < args.length; i++) {
                    requestParams.append("第"+i+"个参数=").append(args[i].toString());
                }
            }
            // 获取  request 对象  来获取  session 和
            HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            User user = (User) request.getSession().getAttribute("loginUser");

            if(user != null){
                log.setUsername(user.getUsername());
            }


            if(log != null){
                mongoTemplate.insert(log, "logdb");
            }
        }
    }


    //后置通知
    @AfterReturning( value= "logPointCut()" ,returning="returningValue" )
    public void myAfter(JoinPoint jp,Object returningValue) throws UnknownHostException {//returningValue是返回值，但需要告诉spring
        if(jp.getSignature().getName().equals("loginUser")){
            System.out.println("《注解形式-后置通知》：目标对象："+jp.getTarget()+",方法名："+jp.getSignature().getName() +",参数列表："+  jp.getArgs().length+",返回值："+returningValue );
            Log log = new Log();


            log.setLogip(getIp());

            String className = jp.getTarget().getClass().toString().substring(jp.getTarget().getClass().toString().lastIndexOf(".")+1);

            log.setLogname("登录日志");


            log.setLogtime(new Date());
            log.setRequerpath(jp.getTarget().getClass() +"/"+ jp.getSignature().getName());

            //log.setReturningValue(returningValue);
	    /*    //获取 目标 对象
	        jp.getTarget().getClass();

	        // 获取方法名

	        jp.getSignature().getName();

	        */
            //获取请求参数
            Object[] args = jp.getArgs();
            if (args != null) {
                StringBuffer requestParams = new StringBuffer();
                for (int i = 0; i < args.length; i++) {
                    requestParams.append("第"+i+"个参数=").append(args[i].toString());
                }
            }
            // 获取  request 对象  来获取  session 和
            HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            User user = (User) request.getSession().getAttribute("loginUser");

            if(user!=null){
                log.setUsername(user.getUsername());
            }


            if(log != null){
                mongoTemplate.insert(log, "logdb");
            }

        }
    }


    /**
     * 获取  ip 地址
     * @return
     * @throws UnknownHostException
     */
    private String getIp() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr.getHostAddress();
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress.getHostAddress();
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

}
