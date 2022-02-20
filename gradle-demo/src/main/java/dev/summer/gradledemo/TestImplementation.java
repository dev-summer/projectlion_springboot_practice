package dev.summer.gradledemo;

import org.springframework.stereotype.Component;

@Component
public class TestImplementation implements TestInterface{
    @Override
    public void sayHello() {
        System.out.println("hello, i'm a bean");
    }
}
