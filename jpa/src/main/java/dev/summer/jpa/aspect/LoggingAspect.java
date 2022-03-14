package dev.summer.jpa.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around(value = "@annotation(LogExecutionTime)") // Pointcut 지정
    // Advice로 작동할 함수
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long execTime = System.currentTimeMillis() - startTime;
        logger.trace("method excuted in {}", execTime);
        return proceed;
    }

    @Before(value = "@annotation(LogArguments)")
    public void logParameter(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.trace("method description: [{}]", methodSignature.getMethod());
        logger.trace("method name: [{}]", methodSignature.getName());
        logger.trace("declaring class: [{}]", methodSignature.getDeclaringType());

        Object[] arguments = joinPoint.getArgs();
        if (arguments.length == 0){
            logger.trace("no arguments");
        }
        for (Object argument: arguments){
            logger.trace("argument [{}]", arguments);
        }
    }

    @AfterReturning(value = "@annotation(LogReturn)", returning = "returnValue") // 반환값이 존재하는 함수에 대해 pointcut 지정 가능. 반환값이 없어도(void)여도 가능하지만, 주로 반환값을 받아오기 위해 사용
    public void logResults(JoinPoint joinPoint, Object returnValue){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.trace("method name: [{}]", methodSignature.getName());
        logger.trace("method type: [{}]", methodSignature.getReturnType());
        logger.trace("return value: [{}]", returnValue);
    }

}
