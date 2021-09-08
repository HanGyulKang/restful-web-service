package com.inflearn.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data               // lombok 'Data' Annotation : getter, setter, toString 등의 VO 구조(기능) 역할을 해 줌
@AllArgsConstructor // lombok 'AllArgsConstructor' Annotation : 모든 변수들(arguments)에 해당하는 생성자를 만들어 줌
@NoArgsConstructor  // lombok 'NoArgsConstructor' Annotation : 기본 생성자를 만들어 줌
public class HelloWorldBean {
    private String message;

    /**
     * AllArgsConstructor Annotation으로 사자지게 되는 소스
     */
    /*
    public HelloWorldBean(String message) {
        this.message = message;
    }
     */

    /**
     * Data Annotation으로 사라지게 되는 소스
     */
    /*
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "message : " + this.message;
    }

    등
    */
}

