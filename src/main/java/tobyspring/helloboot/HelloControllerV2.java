package tobyspring.helloboot;

import java.util.Objects;

public class HelloControllerV2 {

    private final HelloService helloService;

    public HelloControllerV2(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) {

        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
