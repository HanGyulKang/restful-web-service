package com.inflearn.restfulwebservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET
    // URI : /hello-world (endpoint)
    // 기존 방식 : @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // opt + enter : Class 자동 생성
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        // RestController Annotation을 선언해두었다면 response body에 따로 넣어 반환하지 않아도 자동으로 JSON 타입으로 반환한다.
        return new HelloWorldBean("Hello World");
    }
}
