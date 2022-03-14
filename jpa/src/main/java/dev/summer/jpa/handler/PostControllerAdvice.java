package dev.summer.jpa.handler;

import dev.summer.jpa.exception.BaseException;
import dev.summer.jpa.exception.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PostControllerAdvice {
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponseDto handleException(BaseException exception){
        return new ErrorResponseDto(exception.getMessage());
    }
    // @RestControllerAdvice를 클래스 위에 달아주면 메소드에 @ResponseBody 어노테이션을 빼고 작성해도 됨

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorResponseDto handleValidException(MethodArgumentNotValidException exception){
        return new ErrorResponseDto(exception.getMessage());
    }
}
