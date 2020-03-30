package chenjiajin.rocketmqdemo.aop;


import cn.hutool.core.date.DateUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class LogAop {

    /**
     * 进入方法时间戳
     */
    private Long startTime;
    /**
     * 方法结束时间戳(计时)
     */
    private Long endTime;

    private final String operateLogPoint = "execution(* chenjiajin.rocketmqdemo.controller.*.*(..))";

    @Pointcut(operateLogPoint)//切点
    public void webLog() {

    }

    //在方法
    @Before(value = "webLog()")//这个方法横向的插入到切点方法执行前
    public void beforeControll(JoinPoint joinPoint) throws Exception {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //打印请求的内容
        startTime = System.currentTimeMillis();
        log.info("请求开始时间 ： " + DateUtil.now());
        log.info("请求Url : " + request.getRequestURL().toString());
        log.info("请求方式 : " + request.getMethod());
        log.info("请求ip : " + request.getRemoteAddr());
        log.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
        // 系统信息
        log.info("浏览器 ： " + userAgent.getBrowser().toString());
        log.info("浏览器版本 ： "+userAgent.getBrowserVersion());
        log.info("操作系统 : "+userAgent.getOperatingSystem().toString());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        log.info("请求结束时间 ： " + DateUtil.now());
        log.info("请求耗时 ： " + (endTime - startTime));
        // 处理完请求，返回内容
        log.info("请求返回 : " + ret);
    }
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 保存异常日志记录
        log.error("发生异常时间 ： " + LocalDateTime.now());
        log.error("抛出异常 ： " + throwable.getMessage());
    }
}
