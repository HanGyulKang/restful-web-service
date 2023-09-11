package com.inflearn.restfulwebservice.user.controller.dto;

import lombok.NonNull;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import java.util.Objects;

public class UserDto {

    public static class UserRequest {
        @NonNull
        private String name;
        @Email
        @NonNull
        private String email;
        @NonNull
        private String phoneNumber;

        public UserRequest(String name, String email, String phoneNumber) {
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        private boolean validate(String name, String email, String phoneNumber) {
            return StringUtils.hasText(name)
                    && StringUtils.hasText(email)
                    && StringUtils.hasText(phoneNumber);
        }

        public UserRequest of(String name, String email, String phoneNumber) throws Exception {
            if(Boolean.FALSE.equals(validate(name, email, phoneNumber))) {
                throw new Exception("some message");
            }

            return new UserRequest(name, email, phoneNumber);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRequest that = (UserRequest) o;
            return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, email, phoneNumber);
        }
    }

    public static class UserResponse {
        private String code;
    }
}
