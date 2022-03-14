package dev.summer.jpa.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;

@Component
public class HeaderLoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HeaderLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션관리 또는 요청이 들어온 헤더에서 세션 아이디를 확인하고 ModelAndView의 모델이나 데이터베이스의 모델에 접근 가능
        // application-specific 기능을 구현
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        logger.info("start processing of {}", handlerMethod.getMethod().getName());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.trace("{}: {}", headerName, request.getHeader(headerName));
        }
        // 들어온 요청에 대한 모든 헤더값들을 조사
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName: headerNames) {
            logger.trace("{}: {}", headerName, response.getHeader(headerName));
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        logger.info("start processing of {}", handlerMethod.getMethod().getName());
        if (ex != null) logger.error("Exception occurred while processing", ex);
    }
}
