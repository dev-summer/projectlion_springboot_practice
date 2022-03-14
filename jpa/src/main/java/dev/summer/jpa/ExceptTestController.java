package dev.summer.jpa;

import dev.summer.jpa.exception.BaseException;
import dev.summer.jpa.exception.ErrorResponseDto;
import dev.summer.jpa.exception.PostNotInBoardException;
import dev.summer.jpa.exception.PostNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("except")
public class ExceptTestController {
    @GetMapping("{id}")
    public void throwException(@PathVariable int id){
        switch (id) {
            case 1:
                throw new PostNotExistException();
            case 2:
                throw new PostNotInBoardException();
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(BaseException.class) // @RestController 안에서 발생하는 모든 BaseException에 대해 아래 함수로 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBaseException(BaseException exception){
        return new ErrorResponseDto(exception.getMessage());
    }

}
