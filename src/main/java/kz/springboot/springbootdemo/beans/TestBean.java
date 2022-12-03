package kz.springboot.springbootdemo.beans;


import org.springframework.stereotype.Component;

@Component
public class TestBean {
    private String name;
    public TestBean(){
        System.out.println("Start");
    }
}
