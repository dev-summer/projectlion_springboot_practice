package dev.summer.jpa.exception;

public class PostNotExistException extends BaseException{
    public PostNotExistException(){
        super("Target post does not exist");
    }
}
