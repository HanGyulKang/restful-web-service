package com.inflearn.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.*;

@SpringBootApplication
public class RestfulWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulWebServiceApplication.class, args);
    }

    // 다국어 처리 : 언어 기본 설정
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

    // spring4shell 취약점 임시 조치 (2022 04 05 : KHG) ** 추후에 springboot 2.6.6으로 upgrade해야 함
    @Bean
    public WebMvcRegistrations mvcRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
                return new ExtendedRequestMappingHandlerAdapter();
            }
        };
    }


    private static class ExtendedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

        @Override
        protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> methods) {

            return new ServletRequestDataBinderFactory(methods, getWebBindingInitializer()) {

                @Override
                protected ServletRequestDataBinder createBinderInstance(
                        Object target, String name, NativeWebRequest request) throws Exception {

                    ServletRequestDataBinder binder = super.createBinderInstance(target, name, request);
                    String[] fields = binder.getDisallowedFields();
                    List<String> fieldList = new ArrayList<>(fields != null ? Arrays.asList(fields) : Collections.emptyList());
                    fieldList.addAll(Arrays.asList("class.*", "Class.*", "*.class.*", "*.Class.*"));
                    binder.setDisallowedFields(fieldList.toArray(new String[] {}));
                    return binder;
                }
            };
        }
    }

}
