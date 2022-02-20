package dev.summer.gradledemo;

import org.springframework.stereotype.Component;

@Component //@Component annotation이 들어가면 bean 객체가 됨
public class TestComponent {
    private TestInterface testInterface;

    public TestComponent(TestInterface testInterface, int gradeBean) {
        this.testInterface = testInterface;
        System.out.println(gradeBean);
    }

    public void sayHello(){
        this.testInterface.sayHello();
    }
}
