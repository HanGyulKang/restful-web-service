package com.inflearn.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 예외처리 용도 클래스
@Data               // setter, getter등 생성
@AllArgsConstructor // 전체 매개변수를 가지고있는 생성자 생성
@NoArgsConstructor  // 기본 생성자 생성
public class ExceptionResponse {
    // 예외가 발생한 시간
    private Date timestamp;
    // 예외가 발생한 메시지
    private String message;
    // 예외 상세정보
    private String details;

    // AOP : 스프링 부트 어플리케이션 내에서 공통적으로 처리되어야하는 로직 및 메소드 등
}
