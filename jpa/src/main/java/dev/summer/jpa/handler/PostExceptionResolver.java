package dev.summer.jpa.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.summer.jpa.exception.BaseException;
import dev.summer.jpa.exception.ErrorResponseDto;
import org.dom4j.rule.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PostExceptionResolver extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        logger.debug(ex.getClass()); // getClass를 찍는 이유
        if (ex instanceof BaseException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try{
                response.getOutputStream().print(
                        new ObjectMapper().writeValueAsString(
                                new ErrorResponseDto("in resolver, message: " + ex.getMessage())
                        )
                );
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                return new ModelAndView();
            } catch (IOException e) {
                logger.warn("Handling exception caused by exception: {}", e);
            }
        }
        return null;
    }
}
