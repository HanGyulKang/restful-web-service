package com.inflearn.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// @RestController : @Controller + @ResponseBody
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
        // RestController Annotation을 선언해두었다면 ResponseBody에 포함하지 않아도 자동으로 JSON 타입으로 반환한다.
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        // RestController Annotation을 선언해두었다면 ResponseBody에 포함하지 않아도 자동으로 JSON 타입으로 반환한다.
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
